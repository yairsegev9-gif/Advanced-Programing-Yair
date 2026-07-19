package servlets;

import configs.GenericConfig;
import configs.Graph;
import graph.TopicManagerSingleton;
import server.RequestParser.RequestInfo;
import views.HtmlGraphWriter;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;


public class ConfLoader implements Servlet {
    private GenericConfig currentConfig;
    private final Path uploadPath;

    public ConfLoader() {
        this(Path.of("config_files", "uploaded.conf"));
    }

    public ConfLoader(Path uploadPath) {
        this.uploadPath = uploadPath;
    }

    @Override
    public synchronized void handle(RequestInfo ri, OutputStream toClient) throws IOException {
        byte[] content = ri.getContent();
        String configText = extractConfigText(new String(content, StandardCharsets.UTF_8));
        if (configText.isBlank()) {
            TopicDisplayer.writeHtml(toClient, "<html><body>No configuration content supplied.</body></html>");
            return;
        }

        if (currentConfig != null) {
            currentConfig.close();
        }
        TopicManagerSingleton.get().clear();
        Files.createDirectories(uploadPath.getParent());
        Files.writeString(uploadPath, configText, StandardCharsets.UTF_8);

        GenericConfig config = new GenericConfig();
        config.setConfFile(uploadPath.toString());
        config.create();
        currentConfig = config;

        Graph graph = new Graph();
        graph.createFromTopics();
        List<String> html = HtmlGraphWriter.getGraphHTML(graph);
        TopicDisplayer.writeHtml(toClient, String.join("\n", html));
    }

    @Override
    public synchronized void close() {
        if (currentConfig != null) {
            currentConfig.close();
            currentConfig = null;
        }
    }

    private static String extractConfigText(String body) {
        if (body == null) {
            return "";
        }
        String trimmed = body.trim();
        if (trimmed.startsWith("config=")) {
            return URLDecoder.decode(trimmed.substring("config=".length()), StandardCharsets.UTF_8).trim() + "\n";
        }
        int marker = body.indexOf("\r\n\r\n");
        int offset = 4;
        if (marker < 0) {
            marker = body.indexOf("\n\n");
            offset = 2;
        }
        if (body.contains("Content-Disposition") && marker >= 0) {
            String multipartContent = body.substring(marker + offset);
            int boundary = multipartContent.lastIndexOf("\n--");
            if (boundary >= 0) {
                multipartContent = multipartContent.substring(0, boundary);
            }
            return multipartContent.trim() + "\n";
        }
        return trimmed + "\n";
    }
}