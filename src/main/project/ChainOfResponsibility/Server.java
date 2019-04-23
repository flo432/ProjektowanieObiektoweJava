package ChainOfResponsibility;

import Strategy.DatabaseHandler;
import Utils.CSVReader;
import Utils.ConfigReader;
import Utils.DatabaseConfig;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.Objects;


public class Server extends Handler {
    private RootHandler rootHandler;

    private void init() throws IOException {
        HttpServer server;
        server = HttpServer.create(new InetSocketAddress(9001), 0);

        getRoutes();
        Objects.requireNonNull(server).createContext("/", rootHandler);
        server.setExecutor(null);
        server.start();
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.init();
    }

    private void getRoutes() throws IOException {
        ConfigReader configReader = new ConfigReader();
        DatabaseConfig databaseConfig = null;
        try {
            databaseConfig = configReader.getDatabaseConfig();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        DatabaseHandler db = new DatabaseHandler(Objects.requireNonNull(databaseConfig));
        JSONHandler jsonHandler = new JSONHandler(db);
        ErrorHandler errorHandler = new ErrorHandler();
        rootHandler = new RootHandler(db);



        String csvRoutingPath = "routes.csv";
        String csvSeparator = ",";
        CSVReader reader = new CSVReader(csvRoutingPath, csvSeparator);
        List<List<String>> csvData;
        csvData = reader.read();

        for (List<String> mapping : Objects.requireNonNull(csvData)) {
            String endpoint = mapping.get(0);
            String getHandler = mapping.get(1);

            switch (getHandler) {
                case "RootHandler":
                    rootHandler.addEndpoint(endpoint, getHandler);
                    break;
                case "JSONHandler":
                    jsonHandler.addEndpoint(endpoint, getHandler);
                    break;
                default:
                    break;
            }
        }
        rootHandler.setNext(jsonHandler);
        jsonHandler.setNext(errorHandler);


    }

    @Override
    public void handle(HttpExchange httpExchange){}
}

