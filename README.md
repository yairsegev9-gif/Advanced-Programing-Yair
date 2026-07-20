# Advanced Programming Final Project

## 1. Project Background

This project implements the six-part Advanced Programming assignment. It begins with a publish/subscribe messaging model and develops into a configurable computational graph served through a custom HTTP server and browser-based interface.

Messages are published to topics, agents subscribe to topics, and agents publish computed results to other topics. A configuration file defines the graph dynamically, allowing the same application to execute different computational flows.

## 2. Architecture and Design

The implementation is divided into the following layers:

- Model: `Message`, `Topic`, `Agent`, `TopicManagerSingleton`
- Concurrency: `ParallelAgent`
- Configuration: `Config`, `GenericConfig`, `PlusAgent`, `IncAgent`, `BinOpAgent`
- Computational graph: `Node`, `Graph`
- HTTP server: `RequestParser`, `HTTPServer`, `MyHTTPServer`
- Web application: `Servlet`, `ConfLoader`, `TopicDisplayer`, `HtmlLoader`
- View: `HtmlGraphWriter` and static HTML files

The HTTP server uses longest-prefix URI matching, a bounded worker pool, and explicit shutdown.

Configuration loading uses reflection and agent constructors of the form:

```java
(String[] subs, String[] pubs)