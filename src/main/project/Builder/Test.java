package Builder;

import Strategy.DatabaseHandler;
import Utils.ConfigReader;
import Utils.DatabaseConfig;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Objects;

public class Test {
    public static void main(String[] args){
        ConfigReader reader = new ConfigReader();

        DatabaseConfig databaseConfig = null;
        try {
            databaseConfig = reader.getDatabaseConfig();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        DatabaseHandler handler = new DatabaseHandler(Objects.requireNonNull(databaseConfig));
        Director director = new Director();
        TemplateBuilder builder = new HomePageBuilder(handler);
        director.setBuilder(builder);
        director.build();
        HTMLTemplate template = director.getTemplate();
        System.out.println(template.returnHTML());
    }
}
