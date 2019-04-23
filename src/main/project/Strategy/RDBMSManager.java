package Strategy;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

abstract class RDBMSManager implements DBOptions {

    abstract Connection getConnection() throws IOException, ParseException, SQLException;

    private Data processData(ResultSet rs) throws SQLException {
        final Map<String, Object> result = new HashMap<>();
        final List<String> columns = new ArrayList<>();
        final ResultSetMetaData metaData = rs.getMetaData();
        final int columnCount = metaData.getColumnCount();

        for (int column = 1; column <= columnCount; ++column) {
            String columnName = metaData.getColumnName(column);
            columns.add(columnName);
        }

        for (String key : columns) {
            Object value = rs.getObject(key);
            result.put(key, value);
        }

        return new Data(result);
    }

    private void executeUpdate(String query) throws SQLException, IOException, ParseException {
        try (Connection connection = getConnection(); Statement statement = connection.createStatement()){
            statement.setQueryTimeout(10);
            statement.executeUpdate(query);
        }
    }

    private List<Data> executeQuery(String query) throws SQLException, IOException, ParseException {
        List<Data> data = new ArrayList<>();


        try (Connection connection = getConnection(); Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(query)) {
            statement.setQueryTimeout(10);
            while (resultSet.next()) {
                data.add(this.processData(resultSet));
            }
        }

        return data;

    }

    @Override
    public List<Data> getOne(String source, String clause) {
        String query = "SELECT * FROM "+ source + " WHERE url='" + clause + "';";
        try {
            return executeQuery(query);
        } catch (SQLException | IOException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Data> getAll(String source) {
        String query = "SELECT * FROM " + source;
        try {
            return executeQuery(query);
        } catch (SQLException | IOException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Data> getByKey(String source, String clause) {
        String query = "SELECT * FROM "+ source + " WHERE url='" + clause+"';";
        try {
            return executeQuery(query);
        } catch (SQLException | IOException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void delete(String source, String clause) {
        String query = "DELETE FROM "+ source + "" + " WHERE url ='" + clause+"';";
        try {
            executeUpdate(query);
        } catch (SQLException | IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insert(String source, Map<String, Object> values) {
        StringBuilder query1 = new StringBuilder();
        query1.append("INSERT INTO ").append(source).append("(");
        StringBuilder query2 = new StringBuilder();
        query2.append(") VALUES (");

        values.forEach((key, value) -> {
            query1.append(key).append(", ");
            query2.append("'").append(value).append("'").append(", ");
        });

        query1.replace(0, query1.length(), query1.substring(0, query1.length()-2));
        query2.replace(0, query2.length(), query2.substring(0, query2.length()-2));
        query2.append(");");
        String q1 = String.valueOf(query1);
        String q2 = String.valueOf(query2);

        try {
            executeUpdate(q1 + "" + q2);
        } catch (SQLException | IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(String source, Map<String, Object> values, String clause) {
        StringBuilder query = new StringBuilder();
        query.append("UPDATE ").append(source).append(" SET ");

        values.forEach((key, value) -> query.append(key).append("=").append("'").append(value).append("'").append(", "));

        query.replace(0, query.length(), query.substring(0, query.length()-2));
        query.append(" WHERE url='").append(clause).append("';");

        try {
            executeUpdate(String.valueOf(query));
        } catch (SQLException | IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}
