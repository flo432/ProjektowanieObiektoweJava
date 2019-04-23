package Utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HTMLFindAndReplace {
    private static final String PATTERN = "\\{\\{([^}]*)}}";
    private String stringWithBrackets;
    private File jsonFile;

    public HTMLFindAndReplace(String stringWithBrackets) {
        this.stringWithBrackets = stringWithBrackets;
    }

    public HTMLFindAndReplace(String stringWithBrackets, File jsonFile) {
        this.jsonFile = jsonFile;
        this.stringWithBrackets = stringWithBrackets;
    }

    public List<String> getAllBrackets() {
        List<String> allMatches = new ArrayList<>();
        Pattern p = Pattern.compile(PATTERN);
        Matcher m = p.matcher(this.stringWithBrackets);
            while (m.find()) {
                allMatches.add(m.group(1));
            }

        return allMatches;
    }

    public String getBracket(String bracket){
        String match = "";
        Pattern p = Pattern.compile(bracket);
        Matcher m = p.matcher(this.stringWithBrackets);
            while(m.find()){
                match = m.group(1);
            }

        return match;
    }


    public String replaceBrackets() throws IOException, ParseException {

        FileReader reader = new FileReader(this.jsonFile);
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(reader);

        Set<Map.Entry<String, Object>> entry = jsonObject.entrySet();
        Map<String, Object> output = new HashMap<>();

            for (Map.Entry<String, Object> mapEntry : entry) {
                output.put(mapEntry.getKey(), mapEntry.getValue());
            }


        String newTemplate = this.stringWithBrackets;

            for (String bracket : this.getAllBrackets()) {
                newTemplate = newTemplate.replaceAll("\\{\\{"+ bracket +"}}", output.get(bracket).toString());
            }

        return newTemplate;
    }
}
