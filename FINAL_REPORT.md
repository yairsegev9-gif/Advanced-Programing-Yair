# Final Report

## Summary

The project now implements Exercises 1-6 in the existing repository, preserving the plain `src` layout and current package names. The implementation includes the publish/subscribe graph model, active-object `ParallelAgent`, computational graph and cycle detection, generic reflection-based configuration loading, a small HTTP server, servlets, static HTML files, graph HTML generation, `Main`, and a dependency-free regression suite.

## Files Added Or Changed

### Existing files changed

- `src/graph/Topic.java` - added latest-message tracking for the values display.
- `src/graph/ParallelAgent.java` - stabilized close/lifecycle behavior.
- `src/configs/BinOpAgent.java` - implemented latest-value, NaN rejection, reset-to-zero, and close symmetry behavior.
- `src/configs/Node.java` - added required accessors and reliable cycle detection.
- `src/configs/Graph.java` - retained existing graph construction and naming behavior.
- `src/test/GraphTest.java` - replaced print-only checks with assertions.
- `src/test/AllTests.java` - added full suite runner.

### New source files

- `src/Main.java`
- `src/configs/Config.java`
- `src/configs/GenericConfig.java`
- `src/configs/IncAgent.java`
- `src/configs/MathExampleConfig.java`
- `src/configs/PlusAgent.java`
- `src/server/HTTPServer.java`
- `src/server/MyHTTPServer.java`
- `src/server/RequestParser.java`
- `src/servlets/Servlet.java`
- `src/servlets/TopicDisplayer.java`
- `src/servlets/ConfLoader.java`
- `src/servlets/HtmlLoader.java`
- `src/views/HtmlGraphWriter.java`
- `src/test/CoreGraphTest.java`
- `src/test/ConfigTest.java`
- `src/test/HTTPServerTest.java`
- `src/test/WebAppTest.java`

### New project files

- `config_files/simple.conf`
- `html_files/index.html`
- `html_files/form.html`
- `html_files/temp.html`
- `README.md`
- `REQUIREMENTS_CHECKLIST.md`
- `REVIEW_GUIDE.md`
- `FINAL_REPORT.md`

## Assumptions

- Package names remain `graph`, `configs`, `server`, `servlets`, and `views` because the existing project used those packages and the user explicitly instructed not to change package names.
- `Node.message` remains `byte[]` because the PDFs do not declare an exact Java type and the existing code already used `byte[]`.
- Graph node names remain `T:<topic>` and `A:<agent>`. The assignment requires the first character to be `T` or `A`; the separator avoids collisions.
- `GenericConfig` transfers constructor-created raw-agent registrations to a `ParallelAgent` wrapper so the reflected agents actually run through the active-object path.
- The static upload form posts URL-encoded textarea content as `config=...`; `ConfLoader` also accepts raw config text and a simple multipart-style body.
- `TopicDisplayer` waits briefly after publishing so asynchronous `ParallelAgent` computations have time to update downstream topics before the values table is returned.

## Remaining Uncertainties

- The PDFs do not specify the exact Java type of `Node.message`.
- The final course packaging may require changing packages to `test` or `project_biu` for a specific submission system. This project intentionally preserves current package names per user instruction.
- The browser UI is intentionally minimal and dependency-free; it satisfies the required forms, iframes, static loading, deployment, topic publishing, and graph display.

## Compilation Commands And Results

Final clean compile command:

```powershell
javac --release 17 -d <temporary-output> src\*.java src\graph\*.java src\configs\*.java src\server\*.java src\servlets\*.java src\views\*.java src\test\*.java
```

Result: passed with exit code 0.

## Tests Executed And Results

Final full suite command:

```powershell
java -cp <temporary-output> test.AllTests
```

Run 1 result:

```text
CoreGraphTest passed
GraphTest passed
ConfigTest passed
HTTPServerTest passed
WebAppTest passed
AllTests passed
```

Run 2 result:

```text
CoreGraphTest passed
GraphTest passed
ConfigTest passed
HTTPServerTest passed
WebAppTest passed
AllTests passed
```

Run 3 result:

```text
CoreGraphTest passed
GraphTest passed
ConfigTest passed
HTTPServerTest passed
WebAppTest passed
AllTests passed
```

The `WebAppTest` suite includes a final localhost HTTP smoke test covering static HTML, upload, graph reconstruction, publish, topic values, and server shutdown.

## Additional Verification

- Required HTML/config/doc files exist.
- No absolute local Windows paths were found in source/docs scanned with `rg`.
- Generated compile output was written outside the repo and removed after verification.
- `.idea/` and `out/` are ignored.
- No external dependencies were added.

## Known Limitations

- HTTP parsing is intentionally course-level and supports the request shapes required by the assignment. It is not a production HTTP parser.
- Multipart upload support is minimal and intended for simple form content.
- The graph layout is simple SVG positioning, not a dynamic graph-layout algorithm.

## Git Commits Created

- `d13f137 Snapshot before Codex completion`
- `9b7c145 Complete Exercise 3 computational graph`
- `d8a5a18 Stabilize Exercises 1 and 2`
- `5433b99 Complete Exercise 4 generic configuration`
- `523407e Complete Exercise 5 HTTP server`
- `0baa69c Complete Exercise 6 web application`
- `Add final regression tests and documentation` - this documentation checkpoint

## IntelliJ Review Steps

1. Open the repository folder in IntelliJ.
2. Confirm the SDK is Java 17.
3. Confirm `src` is the source root.
4. Run `test.AllTests`.
5. Run `Main`.
6. Open `http://localhost:8080/app/index.html`.
7. Deploy the default form configuration.
8. Publish values to topics `A` and `B` and inspect the values iframe.
9. Press Enter in the `Main` run console to stop the server.