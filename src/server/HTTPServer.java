package server;

import servlets.Servlet;

public interface HTTPServer extends Runnable {
    void addServlet(String httpCommanmd, String uri, Servlet s);
    void removeServlet(String httpCommanmd, String uri);
    void start();
    void close();
}