// publishing doesn't really work with promises @see http://www.squaremobius.net/amqp.node/channel_api.html#flowcontrol

const amqp = require('amqplib/callback_api');

amqp.connect('amqp://localhost:5672', (err, conn) => {
  conn.createChannel((err, ch) => {
    const ex = 'my-data-exchange';
    const key = 'data.topic.1';
    const msg = 'Hello World!' + new Date().getTime();

    ch.assertExchange(ex, 'topic', {durable: false});
    ch.publish(ex, key, new Buffer(msg));
    console.log(" [x] Sent %s: '%s'", key, msg);
  });

  setTimeout(() => { conn.close(); process.exit(0) }, 500);
});
