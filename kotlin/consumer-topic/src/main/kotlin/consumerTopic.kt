import javax.jms.*
import org.apache.qpid.jms.JmsConnectionFactory

fun main(args: Array<String>) {
    val connectionFactory = JmsConnectionFactory("admin", "admin", "amqp://127.0.0.1:5672")
    // connectionFactory.clientID = "my-unique-client-id-that-must-be-set-in-order-to-use-topic-subscriptions"
    val connection = connectionFactory.createConnection()
    val session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE)

    connection.start()

    val topic = session.createTopic("events.topic-0")

    // if we want to consume a multicast via a named queue
    // val consumer = session.createDurableSubscriber(topic, "events.topic-0.queue-0")
    // createDurableConsumer(topic, "events.topic-0.queue-1") is the same as createDurableSubscriber

    // This make a normal temporary queue attached to a multicast address
    val consumer = session.createConsumer(topic)



    val message = consumer.receive() as TextMessage
    println(message.text)


    connection.close()
}
