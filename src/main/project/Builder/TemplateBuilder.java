package Builder;

import Strategy.Data;
import Utils.HTMLFindAndReplace;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public abstract class TemplateBuilder {
    HTMLTemplate htmlTemplate;
    private final String PROJECT_PATH = "/home/asdflo/UJ/PO-master/Zadania/src/main/resources/templates/";
    private final String TEMPLATE_PATH ="template.html";
    private final String REGEX = "\\@([^}]*)\\@";

    public HTMLTemplate getTemplate() {
        return htmlTemplate;
    }

    public void newHTMLTemplate(){

        htmlTemplate = new HTMLTemplate();
    }

    abstract void buildData();
    abstract void buildTemplate();
    abstract void buildHTMLPath();
    abstract void buildPageContent();

    private String getProjectPath(){
        return PROJECT_PATH;
    }

    String getTemplatePath(){
        return TEMPLATE_PATH;
    }

    String getRegex(){
        return REGEX;
    }

    String readTemplate() throws IOException {
        String tmp;
        byte[] encoded = Files.readAllBytes(Paths.get(getProjectPath() + getTemplatePath()));
        tmp = new String(encoded, StandardCharsets.UTF_8);

        return tmp;
    }

    JSONObject convertDataToJSON(Data d){
        JSONObject obj = new JSONObject();
        for (String key : d.data.keySet()) {
            Object value = d.data.get(key);
            obj.put(key, value.toString());
        }
        return obj;
    }

    String replaceBracket(String bracket, String template, String pageContent) {
        HTMLFindAndReplace finder = new HTMLFindAndReplace(template);

        return template.replaceFirst("@" + finder.getBracket(bracket) + "@", pageContent);
    }

    abstract String getTMPTemplate();
    abstract void setTMPTemplate(String template);
    abstract JSONArray getJSONData();
    abstract void setJSONData(JSONArray jsonArray);
}
