# Advanced Programming Final Project

## 1. Project Background

This project implements the six-part Advanced Programming assignment. It starts with a small publish/subscribe model and grows into a configurable computational graph served through a custom HTTP server and browser UI.

The core idea is that messages are published to topics, agents listen to topics, and agents publish computed results to other topics. A configuration file wires the graph dynamically, so the same runtime can execute different computations.

## 2. Architecture And Design

The implementation is split into clear layers:

- Model: `Message`, `Topic`, `Agent`, `TopicManagerSingleton`
- Concurrency: `ParallelAgent` active-object wrapper
- Configuration: `Config`, `GenericConfig`, `PlusAgent`, `IncAgent`, `BinOpAgent`
- Computational graph: `Node`, `Graph`
- HTTP controller: `RequestParser`, `HTTPServer`, `MyHTTPServer`, `Servlet`
- Web application: `ConfLoader`, `TopicDisplayer`, `HtmlLoader`
- View: `HtmlGraphWriter` and static HTML files

The server uses longest-prefix URI matching, a bounded worker pool, and explicit shutdown. Configuration loading uses reflection with constructors of the form `(String[] subs, String[] pubs)`.

## 3. Package Structure

- `src/graph` - Exercises 1-2 graph messaging, topics, agents, and `ParallelAgent`
- `src/configs` - Exercises 3-4 graph and configuration classes
- `src/server` - Exercise 5 HTTP parser and server
- `src/servlets` - Exercise 5-6 servlet interface and application servlets
- `src/views` - Exercise 6 graph HTML generation
- `src/test` - dependency-free regression tests
- `config_files` - example configuration files
- `html_files` - static browser UI
- `docs/javadoc` - generated API documentation
- `submission_artifacts` - submission ZIPs, final link template, and video prep materials

## 4. Java Version

Use Java 17.

## 5. Installation

No external dependencies are required. Clone or open the repository, ensure Java 17 is installed, and compile with `javac` or IntelliJ.

## 6. IntelliJ Instructions

1. Open the repository folder in IntelliJ.
2. Keep plain `src` marked as the source root.
3. Select a Java 17 SDK.
4. Run `test.AllTests` to verify the project.
5. Run `Main` to start the web application.

## 7. Compile Command

From the project root:

```powershell
javac --release 17 -d out src\*.java src\graph\*.java src\configs\*.java src\server\*.java src\servlets\*.java src\views\*.java src\test\*.java
```

## 8. Test Command

```powershell
java -cp out test.AllTests
```

Expected final output:

```text
AllTests passed
```

## 9. Run Command

```powershell
java -cp out Main
```

Then open:

```text
http://localhost:8080/app/index.html
```

Press Enter in the terminal to stop the server gracefully.

## 10. Example Configuration

```text
configs.PlusAgent
A,B
C
configs.IncAgent
C
D
```

This computes `C = A + B` and then `D = C + 1`.

## 11. Main Flows

### Configuration Upload

The browser posts configuration text to `/upload`. `ConfLoader` saves it, loads it with `GenericConfig`, builds a `Graph`, and returns generated graph HTML.

### Graph Construction

`Graph.createFromTopics()` reads the current topics from `TopicManagerSingleton`. Topics become `T:<topic>` nodes and agents become `A:<agent>` nodes.

### Topic Publication

The browser sends `/publish?topic=A&message=5`. `TopicDisplayer` publishes the message and returns a table of latest topic values.

### Dynamic HTML Graph

`HtmlGraphWriter` converts a `Graph` instance to SVG-based HTML. Topics are rectangles, agents are circles, and directed edges show message flow.

## 12. HTTP Server Reuse Example

```java
HTTPServer server = new MyHTTPServer(8080, 5);
server.addServlet("GET", "/app/", new HtmlLoader("html_files"));
server.addServlet("GET", "/publish", new TopicDisplayer());
server.start();
// Later:
server.close();
```

The server can be reused with custom `Servlet` implementations. Each servlet receives a parsed `RequestInfo` and writes a complete HTTP response to the output stream.

## 13. Known Assumptions

- Integrated project packages remain `graph`, `configs`, `server`, `servlets`, and `views` to preserve the existing repository structure.
- Separate Exercises 1-5 submission copies are generated under `submission_artifacts` with `package test;`.
- `Node.message` remains `byte[]` because the PDFs do not declare an exact Java field type.
- Graph node names use `T:<topic>` and `A:<agent>`, satisfying the requirement that the first character identifies the type.
- The browser upload form sends URL-encoded text; `ConfLoader` also accepts raw configuration text.

## 14. Javadoc

Generated Javadoc is available under:

```text
docs/javadoc/index.html
```

To regenerate:

```powershell
javadoc -d docs\javadoc -sourcepath src src\graph\*.java src\configs\*.java src\server\*.java src\servlets\*.java src\views\*.java
```

## 15. Demo Video

Video URL:

```text
DEMO_VIDEO_URL_HERE
```

After recording and uploading the video, replace this placeholder with the accessible video link.

## 16. Submitters

Submitter 1:

- Name:
- ID:
- Email:

Submitter 2:

- Name:
- ID:
- Email:

If submitting alone, remove the Submitter 2 block.