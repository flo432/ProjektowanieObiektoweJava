package Strategy;

import Utils.ConfigReader;
import Utils.DatabaseConfig;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.json.simple.parser.ParseException;

import java.io.IOException;

class MongoHandler extends DODManager {
    private static volatile MongoHandler instance = null;

    private MongoHandler(){}

    public static MongoHandler getInstance(){
        if(instance == null){
            synchronized (MongoHandler.class){
                if(instance == null){
                    instance = new MongoHandler();
                }
            }
        }
        return instance;
    }

    @Override
    MongoDatabase getConnection() throws IOException, ParseException {
        ConfigReader configReader = new ConfigReader();
        DatabaseConfig databaseConfig = configReader.getDatabaseConfig();
        MongoClient mongo = new MongoClient( databaseConfig.getUrl() , Math.toIntExact(databaseConfig.getPort()));

        return mongo.getDatabase(databaseConfig.getDatabaseName());
    }
}
