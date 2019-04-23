package Strategy;

import Utils.ConfigReader;
import Utils.DatabaseConfig;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class PostgresHandler extends RDBMSManager {
    private static volatile PostgresHandler instane = null;

    private PostgresHandler(){}

    public static PostgresHandler getInstane(){
        if(instane == null){
            synchronized (PostgresHandler.class){
                if(instane == null){
                    instane = new PostgresHandler();
                }
            }
        }
        return instane;
    }

    @Override
    Connection getConnection() throws IOException, ParseException, SQLException {
        ConfigReader configReader = new ConfigReader();
        DatabaseConfig databaseConfig = configReader.getDatabaseConfig();
        Connection connection;
        connection = DriverManager.getConnection(databaseConfig.createConnectionUrl("postgresql"));

        return connection;
    }
}
