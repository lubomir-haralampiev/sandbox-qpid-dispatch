
import org.apache.activemq.artemis.api.core.RoutingType
import org.apache.activemq.artemis.api.core.TransportConfiguration
import org.apache.activemq.artemis.api.core.client.*
import org.apache.activemq.artemis.core.buffers.impl.ResetLimitWrappedActiveMQBuffer


fun main(args: Array<String>) {
//     val locator = ActiveMQClient.createServerLocatorWithoutHA(TransportConfiguration(
  //          InVMConnectorFactory::class.java.name))
    val locator = ActiveMQClient.createServerLocator("tcp://localhost:61616")

    val factory = locator.createSessionFactory()
    val session = factory.createSession("admin", "admin", false, true, true, false, 10)
    // val session = factory.createSession()
//    session.createQueue("example", "example", true)
    val queueName = "telemetry"
    // session.createQueue("telemetry", RoutingType.ANYCAST, queueName, true)

    // val producer = session.createProducer(queueName)
    // val message = session.createMessage(false)
    // message.bodyBuffer.writeString("hello from java")

    val consumer: ClientConsumer = session.createConsumer(queueName)

    // producer.send(message)

    session.start()
    val msgReceived: ClientMessage = consumer.receive()
    println(msgReceived.bodyBuffer.readString())
    msgReceived.acknowledge()

    // NOTES:
    // * can't connect to the 5672(the amqp port)
    // * and not able to consume messages produced ba node js via AMQP 1.0
    // @TODO tryout the JMS client with "amqp://localhost:5672" as connection string
    // @https://github.com/apache/activemq-artemis/tree/master/examples/protocols/amqp/queue

    session.close()
//    println(args)
    println("666")
}
