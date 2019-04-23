package Decorator;

import Utils.HTMLFindAndReplace;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;

public class Decorator {
    private String template;
    private File jsonFile;

    public Decorator(String template, File jsonFile){
        this.template = template;
        this.jsonFile = jsonFile;
    }

    public String decorate() throws IOException, ParseException {
        HTMLFindAndReplace finder = new HTMLFindAndReplace(template, jsonFile);
        return finder.replaceBrackets();
    }
}
