const amqp = require('amqplib')


amqp.connect('amqp://localhost:5672')
    .then((connection) => {
        return connection.createChannel();
    })
    .then((channel) => {
        const exchange = 'my-data-exchange';
        const topic = 'data.topic.*';
        // const topic = '#';
        
        channel.assertExchange(exchange, 'topic', {durable: false});
        return channel.assertQueue('consumer-0-queue')
        .then((q) => {
            channel.bindQueue(q.queue, exchange, topic)
                .then(() => {
                    channel.consume(q.queue, (msg) => {
                        console.log(" [x] %s:'%s'", msg.fields.routingKey, msg.content.toString());
                      }, {noAck: true});
                });

            
        });
    })
    .catch(console.warn);
