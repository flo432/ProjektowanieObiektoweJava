package Builder;

import Strategy.Data;
import Strategy.DatabaseHandler;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.List;

public class AboutPageBuilder extends TemplateBuilder {
    private DatabaseHandler db;
    private String tmpTemplate;
    private JSONArray JSONData;

    public AboutPageBuilder(DatabaseHandler db){
        this.db = db;
    }

    @Override
    void buildData() {
        JSONArray jsonObject = new JSONArray();
        List<Data> data = db.getAll("About");
        for (Data d : data) {
            jsonObject.add(convertDataToJSON(d));
        }

        setJSONData(jsonObject);
    }

    @Override
    void buildTemplate() {
        String readTemplate = null;
        try {
            readTemplate = readTemplate();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setTMPTemplate(readTemplate);
    }

    @Override
    void buildHTMLPath() {
        htmlTemplate.setPath(getTemplatePath());
    }

    @Override
    public void buildPageContent() {
        final String SECTION_END = "\t\t\t\t</section>\n";
        StringBuilder pageContent = new StringBuilder();
        pageContent.append("<section class='about'>\n");

        for (int i=0; i<getJSONData().size(); i++) {
            JSONObject object = (JSONObject) getJSONData().get(i);

            pageContent.append("\t\t\t\t<section class='name'>\n");
            pageContent.append("\t\t\t\t\t<h1>").append("Hi, my name is ").append(object.get("fullname")).append(".</h1>\n");
            pageContent.append(SECTION_END);
            pageContent.append("\t\t\t\t<section class='about-section'>\n");
            pageContent.append("\t\t\t\t\t<h2>").append(object.get("abouth1")).append("</h2>\n");
            pageContent.append("\t\t\t\t\t<p>").append(object.get("aboutp1")).append("</p>\n");
            pageContent.append(SECTION_END);
            pageContent.append("\t\t\t\t<section class='about-section'>\n");
            pageContent.append("\t\t\t\t\t<h2>").append(object.get("abouth2")).append("</h2>\n");
            pageContent.append("\t\t\t\t\t<p>").append(object.get("aboutp2")).append("</p>\n");
            pageContent.append(SECTION_END);
        }

        pageContent.append("\t\t\t</section>");
        String newTemplate;
        newTemplate = replaceBracket(getRegex(), getTMPTemplate(), String.valueOf(pageContent));

        htmlTemplate.setTemplate(newTemplate);
    }

    @Override
    String getTMPTemplate() {
        return tmpTemplate;
    }

    @Override
    void setTMPTemplate(String template) {
        this.tmpTemplate = template;
    }

    @Override
    JSONArray getJSONData() {
        return JSONData;
    }

    @Override
    void setJSONData(JSONArray jsonArray) {
        this.JSONData = jsonArray;
    }
}
