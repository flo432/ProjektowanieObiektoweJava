package Strategy;

import Utils.ConfigReader;
import Utils.DatabaseConfig;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class SqliteHandler extends RDBMSManager {
    private static volatile SqliteHandler instance = null;

    private SqliteHandler(){}

    public static SqliteHandler getInstance(){
        if(instance == null){
            synchronized (SqliteHandler.class){
                if(instance == null){
                    instance = new SqliteHandler();
                }
            }
        }
        return instance;
    }


    @Override
    Connection getConnection() throws IOException, ParseException, SQLException {
        ConfigReader configReader = new ConfigReader();
        DatabaseConfig databaseConfig = configReader.getDatabaseConfig();
        Connection connection;
        connection = DriverManager.getConnection(databaseConfig.createConnectionUrl("sqlite"));

        return connection;
    }
}
