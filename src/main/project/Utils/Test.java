package Utils;

import org.json.simple.parser.ParseException;

import java.io.IOException;

public class Test {
    public static void main(String[] args){
        ConfigReader reader = new ConfigReader();
        try {
            System.out.println(reader.getDatabaseConfig().getUrl());
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}
