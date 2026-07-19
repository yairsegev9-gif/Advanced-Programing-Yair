# Requirements Checklist

## Exercise 1

| Requirement | Implementation | Verification | Status |
|---|---|---|---|
| Immutable-style `Message` with public final `data`, `asText`, `asDouble`, `date` | `graph.Message` | `CoreGraphTest.testMessageConstructors` | complete |
| Invalid numeric text gives `Double.NaN` | `Message(String)` | `CoreGraphTest.testMessageConstructors` | complete |
| `Agent` interface | `graph.Agent` | compilation and all agent tests | complete |
| `Topic` subscribe/unsubscribe/publish | `graph.Topic` | `CoreGraphTest.testTopicSubscribePublishAndPublisherRegistration` | complete |
| Publisher add/remove | `graph.Topic` | `CoreGraphTest.testTopicSubscribePublishAndPublisherRegistration` | complete |
| Singleton topic manager with unique topics and clear | `TopicManagerSingleton.TopicManager` | `CoreGraphTest.testTopicManagerUniquenessAndClear` | complete |
| Thread-safe topic storage | concurrent sets and map | concurrency/lifecycle tests | complete |

## Exercise 2

| Requirement | Implementation | Verification | Status |
|---|---|---|---|
| `ParallelAgent` decorates `Agent` | `graph.ParallelAgent` | `CoreGraphTest` | complete |
| Uses bounded blocking queue | `ArrayBlockingQueue` | compile/source review | complete |
| Callback work runs on worker thread in order | `ParallelAgent` worker loop | `CoreGraphTest.testParallelAgentOrderingAndReset` | complete |
| Reset ordering | queued reset task | `CoreGraphTest.testParallelAgentOrderingAndReset` | complete |
| Repeated close and close while work exists | bounded close path | `CoreGraphTest.testParallelAgentCloseLifecycle` | complete |
| No worker leak after close | worker joins and wrapped agent closes | `CoreGraphTest.testParallelAgentCloseLifecycle` | complete |

## Exercise 3

| Requirement | Implementation | Verification | Status |
|---|---|---|---|
| `Node` with `name`, `edges`, `message` | `configs.Node` | `GraphTest.testNodeAccessorsAndDefensiveCopies` | complete |
| Getters/setters for Node fields | `configs.Node` | `GraphTest` | complete |
| `addEdge` | `configs.Node.addEdge` | `GraphTest` | complete |
| Reliable cycle detection | per-call DFS sets | `GraphTest.testRepeatedCycleDetection`, `testAcyclicGraphDetection` | complete |
| `BinOpAgent` subscribes/publishes and computes | `configs.BinOpAgent` | `GraphTest` | complete |
| `BinOpAgent.reset()` sets inputs to zero | `configs.BinOpAgent.reset` | `GraphTest.testBinOpAgentResetToZero` | complete |
| Ignore NaN input | `BinOpAgent.callback` | `GraphTest.testBinOpAgentRejectsNaN` | complete |
| Preserve latest values | `BinOpAgent.callback` | `GraphTest.testBinOpAgentReusesLatestValues` | complete |
| `Graph extends ArrayList<Node>` and builds from topics | `configs.Graph` | `GraphTest.testGraphFromTopicsNamesEdgesAndNoDuplicates` | complete |
| Node names begin with `T` or `A` | `T:<topic>`, `A:<agent>` | `GraphTest` | complete |

## Exercise 4

| Requirement | Implementation | Verification | Status |
|---|---|---|---|
| `Config` includes create/name/version/close | `configs.Config` | compilation | complete |
| `MathExampleConfig` | `configs.MathExampleConfig` | `ConfigTest.testMathExampleConfig` | complete |
| `PlusAgent` and `IncAgent` constructors `(String[], String[])` | `configs.PlusAgent`, `configs.IncAgent` | `ConfigTest.testPlusAndIncAgentsDirectly` | complete |
| `GenericConfig.setConfFile` and reflection construction | `configs.GenericConfig` | `ConfigTest.testGenericConfigValidFile` | complete |
| Wrap reflected agents in `ParallelAgent` | `GenericConfig.create` | async config tests | complete |
| Close all loaded agents | `GenericConfig.close` | `ConfigTest.testGenericConfigCloseUnregistersAndStopsWorkers` | complete |
| Malformed config handling | `GenericConfig` validation | `ConfigTest.testGenericConfigMalformedFiles` | complete |
| Example `simple.conf` | `config_files/simple.conf` | source/file check | complete |

## Exercise 5

| Requirement | Implementation | Verification | Status |
|---|---|---|---|
| `RequestParser` and `RequestInfo` | `server.RequestParser` | `HTTPServerTest.testRequestParser` | complete |
| Parse URI segments, query params, headers, content | `RequestParser` | `HTTPServerTest` | complete |
| `Servlet` interface | `servlets.Servlet` | compilation | complete |
| `HTTPServer` interface | `server.HTTPServer` | compilation | complete |
| `MyHTTPServer` with worker pool | `server.MyHTTPServer` | `HTTPServerTest` | complete |
| GET/POST/DELETE servlet maps | `MyHTTPServer` | routing tests | complete |
| Longest URI prefix matching | `MyHTTPServer.findServlet` | `HTTPServerTest` | complete |
| Registration/removal/replacement | `addServlet`, `removeServlet` | `HTTPServerTest` | complete |
| Graceful shutdown | `close` | `HTTPServerTest` | complete |

## Exercise 6

| Requirement | Implementation | Verification | Status |
|---|---|---|---|
| `TopicDisplayer` | `servlets.TopicDisplayer` | `WebAppTest` | complete |
| `ConfLoader` | `servlets.ConfLoader` | `WebAppTest` | complete |
| `HtmlLoader` | `servlets.HtmlLoader` | `WebAppTest` | complete |
| `HtmlGraphWriter` | `views.HtmlGraphWriter` | `WebAppTest.testHtmlGraphWriter` | complete |
| `Main` starts server and registers servlets | `src/Main.java` | compile and README run path | complete |
| `html_files/index.html` with three iframes | `html_files/index.html` | `WebAppTest` and review | complete |
| `html_files/form.html` with upload/publish forms | `html_files/form.html` | end-to-end smoke | complete |
| `html_files/temp.html` placeholder | `html_files/temp.html` | static loader test | complete |
| End-to-end local smoke | server plus servlets | `WebAppTest.testEndToEndServerSmoke` | complete |

## Assumptions

| Assumption | Reason | Status |
|---|---|---|
| Keep current packages `graph`, `configs`, `server`, `servlets`, `views` | Existing repo and user instruction said not to change packages | assumption |
| Keep `Node.message` as `byte[]` | PDFs do not declare exact Java type; existing code used `byte[]` | assumption |
| `T:<topic>` and `A:<agent>` node names are acceptable | Assignment only requires first character `T` or `A`; this avoids collisions | assumption |
| Static HTML form posts textarea text as URL-encoded `config=` | Browser form behavior and no multipart parser requirement in PDF | assumption |
## Final Exercise 6 Repository And Submission Requirements

| Requirement | Implementation | Verification | Status |
|---|---|---|---|
| README with background, setup, run/test commands, demo placeholder, submitter placeholders | `README.md` | manual review | Complete |
| Javadoc for reusable APIs | source Javadocs and `docs/javadoc/` | `javadoc` generation | Complete |
| Demo video preparation | `submission_artifacts/video/` | manual review | Complete |
| Final `link.txt` template | `submission_artifacts/final/link.txt` | file exists | Complete |
| Separate Exercise 1-5 package-test submissions | `submission_artifacts/exercise*/` and ZIPs | independent compile of each package | Complete |
| No integrated package migration for final project | original `src` packages preserved | git diff/source review | Complete |
| Runtime upload file not versioned | `.gitignore` includes `config_files/uploaded.conf` | git status and ignored check | Complete |
| No absolute local paths in source/docs | scan with `rg` | final verification | Complete |
| No tracked IDE/output files | git file scan | final verification | Complete |
