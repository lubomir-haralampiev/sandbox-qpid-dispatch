const container = require('rhea');

const address = 'events';

container.on('connection_open', (context) => {
    context.connection.open_sender(address);
});

container.once('sendable', (context) => {
    const message = 'Hello from Rhea ' + new Date().getTime();
    context.sender.send({body: new Buffer(message)});
    console.log(`Sent message "${message}" to "${address}"`);
    context.connection.close();
});

container.connect({host: 'localhost', 'port': 5672});
