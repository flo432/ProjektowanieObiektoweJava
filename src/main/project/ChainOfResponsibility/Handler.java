package ChainOfResponsibility;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public abstract class Handler implements HttpHandler {
    protected HttpHandler next;

    public void setNext(HttpHandler httpHandler){
        this.next = httpHandler;
    }

    public void delegate(HttpExchange exchange) throws IOException {
        next.handle(exchange);
    }

    boolean canHandle(HttpExchange httpExchange, String handler) {
        String path = httpExchange.getRequestURI().getPath();
        String[] splitted = path.split("/");
        if (splitted[splitted.length - 1].matches(REGEX)) {
            StringBuilder newPath = new StringBuilder();
            splitted[splitted.length - 1] = ":title";
            for (int i = 1; i < splitted.length; i++) {
                newPath.append("/").append(splitted[i]);
            }
            path = newPath.toString();
        } else if (splitted.length < 2) {
            String newPath = "";
            newPath += "/" + splitted[0];
            path = newPath;
        }

        Object key;
        Object value;
        for (Map.Entry<String, String> stringStringEntry : endpoints.entrySet()) {
            Map.Entry entry = stringStringEntry;
            key = entry.getKey();
            value = entry.getValue();

            if (key.equals(path) && value.equals(handler)) {
                return true;
            }
        }
        return false;
    }

    public void addEndpoint(String endpoint, String handler) {
        endpoints.put(endpoint, handler);
    }

    private static final String REGEX = "(\\w+[-]\\w+[-]\\w+[-]\\w+[-]\\w+)|(\\w+[-]\\w+[-]\\w+[-]\\w+)|(\\w+[-]\\w+[-]\\w+)|(\\w+[-]\\w+)";
    private static final Map<String, String> endpoints = new TreeMap<>();
}
