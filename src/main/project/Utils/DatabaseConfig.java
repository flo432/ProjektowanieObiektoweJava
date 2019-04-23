package Utils;

public class DatabaseConfig {
    private String name;
    private String type;
    private String url;
    private Long port;
    private String user;
    private String pswd;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDatabaseType() {
        return type;
    }

    public void setDatabaseType(String type) {
        this.type = type;
    }

    public Long getPort() {
        return port;
    }

    public void setPort(Long port) {
        this.port = port;
    }

    public String getDatabaseName() {
        return name;
    }

    public void setDatabaseName(String name) {
        this.name = name;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return pswd;
    }

    public void setPassword(String pswd) {
        this.pswd = pswd;
    }

    public String createConnectionUrl(String ins){
        String connectionUrl = "";

        switch (ins){
            case "sqlite":
                //jdbc:sqlite:/home/asdflo/Jagiellonian University/PO/PO/Zadania/sqldb.sqlite
                connectionUrl = "jdbc:" + type + ":" + url + "" + name;
                break;
            case "mariadb":
                connectionUrl = "";
                break;
            case "postgresql":
                //jdbc:postgresql://localhost:5432/postgres
                connectionUrl = "jdbc:" + type + ":" + url + ":" + port + "/" + name;
                break;
            default:
                connectionUrl = "default case";
                break;
        }

        return connectionUrl;
    }
}