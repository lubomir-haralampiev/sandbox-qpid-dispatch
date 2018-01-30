import org.apache.qpid.jms.JmsConnectionFactory
import javax.jms.*


fun main(args: Array<String>) {
    val connectionFactory = JmsConnectionFactory("admin", "admin", "amqp://127.0.0.1:5672")
    val connection = connectionFactory.createConnection()

    val session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE)

    val topicName = "events.topic-0"
    val topic = session.createTopic("events.topic-0")
    val producerTopic = session.createProducer(topic)
    val messageTopic = session.createTextMessage("Message to $topicName")
    producerTopic.send(messageTopic)
    println(messageTopic.text)

    val queueName = "events.queue-0"
    val queue = session.createQueue(queueName)
    val producerQueue = session.createProducer(queue)
    val message = session.createTextMessage("Message to $queueName")
    producerQueue.send(message)
    println(message.text)

    connection.close()
}
