#Sandbox for the Qpid Dispatch Router

### The problem
In a message-driven-architectures there can be a problem with the scalability and the load balancing 
if you use a single broker instance, just like the problem with the vertical scalability of a single database.

The [Eclipse Hono](https://www.eclipse.org/hono/) project deals with this topic(amongst some other things). 
The basic idea is to use a router which doesn't take any responsibility for the messages, 
instead it just routes them to the responsible components.  
Based on rules the messages are delivered to message brokers, directly to a corresponding application 
or to another router with own routing logic.

The Hono project uses the AMQP 1.0 protocol for it's messaging, 
the [Qpid Dispatch Router](https://qpid.apache.org/components/dispatch-router/index.html) for routing
and the [ActiveMQ Artemis](https://activemq.apache.org/artemis/) as the brokers.

### What is this repository for
In this repository I created a running example of splitting the handling of amqp messages over two brokers
based on the amqp receiver's address.

Assuming two message types 'telemetry' and generic 'events'. 
The telemetry data is handles by the first broker and the evens by the second.

### Running the examples
To run it you need *docker*, *docker-composer* and *node.js* version 8.

* run ```docker-compose up``` to start the Qpid Dispatch Router and the two Artemis instances. 
The two Artemis consoles are located under <http://localhost:18161/console> and <http://localhost:28161/console>.
The login credentials are admin/admin.
* Install the npm dependencies in the two modules amqp-producer and amqp-consumer.
* run ```node amqp-producer/produce-telemetry.js```.  
The first Artemis now has a queue named 'telemetry' with one message in it.
* run ```node amqp-producer/produce-events.js```.  
The second Artemis now has a queue named 'events' with one message in it.
* Dummy consumers which just print the messages to the console are ```node amqp-consumer/consume-telemetry.js``` and ```node amqp-consumer/consume-events.js```

### TODOs
* Place the node.js modules in a docker container to be able to run the examples without having node.js installed

### Important Notes
AMQP 1.0 is primary and only a messaging protocol, means it focuses only on the messaging layer. 
Messages are published/consumed only to/from addresses. All the message routing logic is part of the concrete broker.

ActiveMQ Artemis has the MULTICAST and ANYCAST routing types which can model the JMS concepts of topics and queues.

RabbitMQ has a naming convention for the AMQP 1.0 address, which is internally mapped to the corresponding exchanges direct, topic, fanout or to queues

This means an application can not declare topics/queues/exchanges over a pure AMQP 1.0 communication. 
Instead the Client/API of the concrete broker must be used. 

In the current example this means that we can't create queues and delivery logic vie the grs/rhea lib!
This must be done either in the Artemis configuration(disadvantage = application logic in the broker) or another client must be used. 
