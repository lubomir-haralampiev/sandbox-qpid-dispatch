const container = require('rhea');

// const address = '/exchange/my-data-exchange/data.topic.*';
const address = '/queue/test-queue';

container.on('connection_open', (context) => {
    context.connection.open_receiver(address);
});
 
container.on('message', (context) => {
    console.log(context.message.body.toString());
});

container.connect({host: 'localhost', 'port': 5672});
