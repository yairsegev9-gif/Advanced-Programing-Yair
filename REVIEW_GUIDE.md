# Review Guide

## Main Classes

`graph.Message` represents immutable message data with text, numeric, byte-array, and creation-date views.

`graph.Topic` stores subscribers, publishers, and the latest published message. Publishing calls each subscriber's `callback`.

`graph.TopicManagerSingleton` owns the single topic registry and creates topics lazily.

`graph.ParallelAgent` wraps an `Agent` with a bounded active-object queue and worker thread. Callback and reset requests are queued; close stops the worker and closes the wrapped agent.

`configs.BinOpAgent` listens to two input topics, stores the latest valid numeric value from each, and publishes a binary operation result to an output topic.

`configs.Node` and `configs.Graph` model the computational graph for cycle checks and visualization.

`configs.GenericConfig` reads three-line agent definitions, constructs agents by reflection, wraps them in `ParallelAgent`, and wires them to topics.

`server.RequestParser` parses simple HTTP requests into command, URI, segments, query parameters, headers, and content.

`server.MyHTTPServer` accepts sockets on a background thread, dispatches requests to registered servlets with a bounded worker pool, and chooses servlets by longest URI prefix.

`servlets.TopicDisplayer` publishes a message to a topic and returns a table of latest topic values.

`servlets.ConfLoader` accepts uploaded configuration text, loads it through `GenericConfig`, rebuilds a `Graph`, and returns graph HTML.

`servlets.HtmlLoader` serves static HTML files from the configured directory.

`views.HtmlGraphWriter` creates simple SVG-based graph HTML.

`Main` wires the final app on port 8080.

## Key Execution Flows

### Publish/Subscribe

1. Code requests a `Topic` from `TopicManagerSingleton`.
2. Agents subscribe to input topics and register as publishers of output topics.
3. A published `Message` is stored as the topic's latest value.
4. Subscribers receive callbacks.
5. Computational agents publish derived values to downstream topics.

### ParallelAgent Concurrency

1. `ParallelAgent.callback` enqueues a callback task.
2. Its worker thread takes tasks in FIFO order.
3. Reset is queued like a callback, preserving ordering.
4. Close marks the wrapper closed, queues shutdown when possible, interrupts blocking waits, joins briefly, and closes the wrapped agent.

### Graph Model

Topics become nodes named `T:<topic>`. Agents become nodes named `A:<agent>`. Topic nodes point to subscriber agent nodes. Agent nodes point to topics they may publish to. Cycle detection uses per-call visited and recursion-stack sets.

### Configuration And Reflection

1. `GenericConfig.setConfFile` stores the file path.
2. `create` reads non-blank lines in groups of three: class name, subscribed topics, published topics.
3. It reflects a constructor with `(String[], String[])`.
4. The raw agent is closed to remove constructor registrations.
5. A `ParallelAgent` wrapper is registered to the configured topics.
6. `close` unregisters wrappers and closes them.

### HTTP Routing

1. `MyHTTPServer` parses each client request with `RequestParser`.
2. It chooses a servlet map by HTTP command.
3. It chooses the registered URI with the longest prefix matching the request path.
4. The servlet writes the full HTTP response.
5. Missing routes return 404; malformed requests return 400.

## Likely Oral-Exam Questions

**Why use `ConcurrentHashMap` in `TopicManagerSingleton`?**
It lets topics be created safely from multiple threads with `computeIfAbsent`.

**Why does `ParallelAgent` use a queue?**
It separates the publishing thread from the agent execution thread, so slow agents do not block topic publication longer than queue insertion.

**Why are graph nodes named with `T:` and `A:`?**
The first character satisfies the assignment requirement, and the separator avoids collisions between a topic and an agent with the same textual name.

**Why does `BinOpAgent` keep latest values after publishing?**
The assignment does not require clearing values. Keeping them lets a later update recompute using the other current input.

**Why preserve `Node.message` as `byte[]`?**
The PDFs do not specify an exact Java type, and the existing starter code already used `byte[]`. The implementation adds safe getter/setter methods around that existing type.

**How does `GenericConfig` make reflected agents parallel?**
The reflected constructor initially registers the raw agent. `GenericConfig` closes that raw registration, wraps the agent in `ParallelAgent`, and registers the wrapper instead.

**How is HTTP servlet matching done?**
For the request method, the server scans registered URI prefixes and chooses the longest prefix that matches the request path.

**What stops server tests from hanging?**
Socket read timeouts, bounded worker pools, close methods, daemon worker threads, and test-side bounded waits/joins.