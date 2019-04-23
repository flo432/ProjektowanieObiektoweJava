package Strategy;

import Utils.DatabaseConfig;

import java.util.List;
import java.util.Map;

public class DatabaseHandler extends Data implements DBOptions {

    private DBOptions db;

    public DatabaseHandler(DatabaseConfig databaseConfig){
        switch (databaseConfig.getDatabaseType()) {
            case "sqlite":
                this.db = SqliteHandler.getInstance();
                break;
            case "postgresql":
                this.db = PostgresHandler.getInstane();
                break;
            case "mongo":
                this.db = MongoHandler.getInstance();
                break;
            default:
                break;
        }
    }

    @Override
    public List<Data> getOne(String source, String clause) {
        return this.db.getOne(source, clause);
    }

    @Override
    public List<Data> getAll(String source) {
        return this.db.getAll(source);
    }

    @Override
    public List<Data> getByKey(String source, String clause) {
        return this.db.getByKey(source, clause);
    }

    @Override
    public void delete(String source, String clause) {
        this.db.delete(source, clause);
    }

    @Override
    public void insert(String source, Map<String, Object> values) {
        this.db.insert(source, values);
    }

    @Override
    public void update(String source, Map<String, Object> values, String clause) {
        this.db.update(source, values, clause);
    }
}
