const container = require('rhea');

// const address = '/exchange/my-data-exchange/data.topic.rhea';
const address = '/queue/test-queue';

container.on('connection_open', (context) => {
    context.connection.open_sender(address);
});

container.once('sendable', (context) => {
    const message = 'Hello from RHEA ' + new Date().getTime();
    context.sender.send({body: new Buffer(message)});
    console.log(`Sent message "${message}" to "${address}"`);
    context.connection.close();
});

container.connect({host: 'localhost', 'port': 5672});
