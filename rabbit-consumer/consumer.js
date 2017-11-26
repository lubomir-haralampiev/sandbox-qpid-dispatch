const container = require('rhea');

const options = {host: 'localhost', 'port': 5672};

module.exports = (address) => {
    container.on('connection_open', (context) => {
        context.connection.open_receiver(address);
    });
     
    container.on('message', (context) => {
        console.log(context.message.body.toString());
    });
    
    container.connect(options);    
};
