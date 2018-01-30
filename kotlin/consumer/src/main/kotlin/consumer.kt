import javax.jms.*
import org.apache.qpid.jms.JmsConnectionFactory

fun main(args: Array<String>) {
    val connectionFactory = JmsConnectionFactory("admin", "admin", "amqp://127.0.0.1:5672")
    val connection = connectionFactory.createConnection()
    val session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE)

    connection.start()

    val queue = session.createQueue("events.queue-0")
    val consumer = session.createConsumer(queue)
    val message = consumer.receive() as TextMessage
    println(message.text)


    connection.close()
}
