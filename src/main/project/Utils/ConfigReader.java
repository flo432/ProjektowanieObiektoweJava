package Utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class ConfigReader {
    private static final String CONFIG_FILE = "databaseConfig.json";

    public DatabaseConfig getDatabaseConfig() throws IOException, ParseException {
        JSONParser parser = new JSONParser();

        try (FileReader configFile = new FileReader(CONFIG_FILE)) {
            JSONObject obj = (JSONObject) parser.parse(configFile);
            DatabaseConfig config = new DatabaseConfig();
            config.setDatabaseType((String) obj.get("type"));
            config.setDatabaseName((String) obj.get("name"));
            config.setUrl((String) obj.get("url"));
            config.setPort((Long) obj.get("port"));
            config.setUser((String) obj.get("user"));
            config.setPassword((String) obj.get("pswd"));
            return config;
        }

    }
}
