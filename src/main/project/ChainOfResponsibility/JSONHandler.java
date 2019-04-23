package ChainOfResponsibility;

import Strategy.Data;
import Strategy.DatabaseHandler;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.*;

public class JSONHandler extends Handler implements HttpHandler {

    private static final String     HEADER_CONTENT_TYPE    = "Content-Type";
    private static final Charset    CHARSET                = StandardCharsets.UTF_8;
    private static final int        STATUS_OK              = 200;

    private DatabaseHandler db;
    private HttpHandler next;

    JSONHandler(DatabaseHandler db){
        this.db = db;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        if (canHandle(httpExchange, "JSONHandler")) {
            String response = null;
            try {
                response = createResponse(httpExchange);
            } catch (ParseException | SQLException e) {
                e.printStackTrace();
            }
            Headers headers = httpExchange.getResponseHeaders();
            headers.set(HEADER_CONTENT_TYPE, "application/json; charset="+CHARSET);
            httpExchange.sendResponseHeaders(STATUS_OK, Objects.requireNonNull(response).length());
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } else {
            delegate(httpExchange);
        }

    }

    private String createResponse(HttpExchange httpExchange) throws IOException, ParseException, SQLException {
        String path = httpExchange.getRequestURI().getPath();
        final String request = httpExchange.getRequestMethod().toUpperCase();
        final String[] url = path.split("/");
        String pageContent;

        switch (url[2]){
            case "home":
                switch (request){
                    case "GET":
                        pageContent = getArticlesJSON();
                        break;
                    case "POST":
                        pageContent = postArticleJSON(httpExchange);
                        break;
                    default:
                        pageContent = "default case home JSON";
                        break;
                }
                return pageContent;
            case "article":
                switch (request){
                    case "GET":
                        pageContent = getArticleJSON(httpExchange);
                        break;
                    case "PUT":
                        pageContent = putArticleJSON(httpExchange);
                        break;
                    case "DELETE":
                        pageContent = deleteArticleJSON(httpExchange);
                        break;
                    default:
                        pageContent = "default case article JSON";
                }
                return pageContent;
            case "about":
                switch (request){
                    case "GET":
                        pageContent = getAboutJSON();
                        break;
                    case "PUT":
                        pageContent = putAboutJSON(httpExchange);
                        break;
                    default:
                        pageContent = "default case about JSON";
                        break;
                }
                return pageContent;
            case "contact":
                switch (request){
                    case "GET":
                        pageContent = getContactJSON();
                        break;
                    case "PUT":
                        pageContent = putContactJSON(httpExchange);
                        break;
                    default:
                        pageContent = "default case contact JSON";
                        break;
                }
                return pageContent;
            default:
                return "{\"message\":\"cant find correct method.\"}";
        }
    }

    private static final String ARTICLE_TABLE = "Articles";

    private String getArticlesJSON() {
        return getData(ARTICLE_TABLE);
    }

    private String getArticleJSON(HttpExchange httpExchange) {
        return getData(httpExchange,ARTICLE_TABLE);
    }

    private String postArticleJSON(HttpExchange httpExchange) throws IOException, ParseException, SQLException {
        postData(httpExchange,ARTICLE_TABLE);
        return this.getArticlesJSON();
    }

    private String putArticleJSON(HttpExchange httpExchange) throws IOException, SQLException, ParseException {
        putData(httpExchange);
        return this.getArticlesJSON();
    }

    private String deleteArticleJSON(HttpExchange httpExchange) {
        deleteData(httpExchange);
        return "{\"status\":\"OK. DELETE COMPLETED.\"}";
    }

    private String getAboutJSON() {
        return getData("About");
    }

    private String putAboutJSON(HttpExchange httpExchange) throws IOException, SQLException, ParseException {
        putData(httpExchange, "About");
        return this.getAboutJSON();
    }

    private String getContactJSON() {
        return getData("Contact");
    }

    private String putContactJSON(HttpExchange httpExchange) throws IOException, ParseException, SQLException {
        putData(httpExchange, "Contact");
        return this.getContactJSON();
    }

    private String getData(String table) {
        JSONArray jsonObject = new JSONArray();
        List<Data> data = db.getAll(table);

        for (Data d : data) {
            jsonObject.add(convertDataToJSON(d));
        }

        return jsonObject.toJSONString();
    }

    private String getData(HttpExchange httpExchange, String table) {
        final String title = httpExchange.getRequestURI().getPath().split("/")[3];
        JSONArray jsonObject = new JSONArray();
        List<Data> data = db.getByKey(table, title);

        for (Data d : data) {
            jsonObject.add(convertDataToJSON(d));
        }

        return jsonObject.toJSONString();
    }

    private void postData(HttpExchange httpExchange, String table) throws IOException, ParseException {
        final String request = convertInputStreamToString(httpExchange.getRequestBody());
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject;
        jsonObject = (JSONObject) jsonParser.parse(request);

        this.db.insert(table, jsonToMap(jsonObject));
    }

    private void putData(HttpExchange httpExchange, String table) throws IOException, ParseException {
        final String request = convertInputStreamToString(httpExchange.getRequestBody());
        final String title = httpExchange.getRequestURI().getPath().split("/")[2];
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject;
        jsonObject = (JSONObject) jsonParser.parse(request);

        this.db.update(table, jsonToMap(jsonObject), title);
    }

    private void putData(HttpExchange httpExchange) throws IOException, ParseException {
        final String request = convertInputStreamToString(httpExchange.getRequestBody());
        final String title = httpExchange.getRequestURI().getPath().split("/")[3];
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject;
        jsonObject = (JSONObject) jsonParser.parse(request);

        this.db.update(ARTICLE_TABLE,jsonToMap(jsonObject),title);
    }

    private void deleteData(HttpExchange httpExchange) {
        final String title = httpExchange.getRequestURI().getPath().split("/")[3];

        this.db.delete(ARTICLE_TABLE,title);
    }

    private Map<String, Object> jsonToMap(JSONObject jsonObject){

        HashMap<String, Object> map = new HashMap<>();

        for(Object var:jsonObject.keySet()){
            String key      = (String) var;
            String value    = (String) jsonObject.get(key);
            map.put(key, value);
        }
        return map;
    }

    private String convertInputStreamToString(InputStream is) throws IOException {
        StringBuilder result = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
        }

        return result.toString();
    }

    private JSONObject convertDataToJSON(Data d){
        JSONObject obj = new JSONObject();
        for (String key : d.data.keySet()) {
            Object value = d.data.get(key);

            obj.put(key, value.toString());
        }
        return obj;
    }

}
