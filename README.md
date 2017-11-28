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
