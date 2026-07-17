# Advanced Programming Final Project

This project implements the six-part Advanced Programming assignment: a small publish/subscribe computational graph framework, generic configuration loading, a reusable HTTP server, and a minimal browser UI for deploying and testing graphs.

## Project Structure

- `src/graph` - Exercise 1-2 graph messaging, topics, agents, and `ParallelAgent`.
- `src/configs` - Exercise 3-4 graph/configuration classes and example agents.
- `src/server` - Exercise 5 HTTP parser and server.
- `src/servlets` - Exercise 5-6 servlet interface and web application servlets.
- `src/views` - Exercise 6 graph HTML generation.
- `src/test` - Dependency-free regression tests with `test.AllTests` as the suite runner.
- `config_files` - Example configuration files.
- `html_files` - Static browser UI files.

## Java Version

Use Java 17. The IntelliJ project is configured with `src` as the source root and language level 17.

## IntelliJ Setup

1. Open the repository folder in IntelliJ.
2. Keep `src` marked as the source root.
3. Use a Java 17 SDK.
4. Run `Main` to start the web application.
5. Run `test.AllTests` to execute the regression suite.

## Compile From Terminal

From the project root:

```powershell
javac --release 17 -d out src\*.java src\graph\*.java src\configs\*.java src\server\*.java src\servlets\*.java src\views\*.java src\test\*.java
```

## Run Tests

```powershell
java -cp out test.AllTests
```

Expected final line:

```text
AllTests passed
```

## Run The Web Application

```powershell
java -cp out Main
```

Open:

```text
http://localhost:8080/app/index.html
```

Use the left panel to deploy a configuration, then publish messages to topics. Example:

1. Deploy the default configuration in the form.
2. Publish `A = 4`.
3. Publish `B = 5`.
4. The values panel should show output topic `D` with value `10.0` for `(A+B)+1`.

Press Enter in the terminal to stop the server.