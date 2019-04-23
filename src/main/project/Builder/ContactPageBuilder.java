package Builder;

import Strategy.Data;
import Strategy.DatabaseHandler;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.List;

public class ContactPageBuilder extends TemplateBuilder {
    private DatabaseHandler db;
    private String tmpTemplate;
    private JSONArray JSONData;

    public ContactPageBuilder(DatabaseHandler db){
        this.db = db;
    }

    @Override
    void buildData() {
        JSONArray jsonObject = new JSONArray();
        List<Data> data = db.getAll("Contact");
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
        final String H2_START = "\t\t\t\t\t<h2>";
        final String H2_END = "</h2>\n";

        StringBuilder pageContent = new StringBuilder();
        pageContent.append("<section class='contact'>\n");

        for (int i=0; i<getJSONData().size(); i++) {
            JSONObject object = (JSONObject) getJSONData().get(i);

            pageContent.append("\t\t\t\t<section>\n");
            pageContent.append(H2_START).append(object.get("fullname")).append(H2_END);
            pageContent.append(H2_START).append(object.get("email")).append(H2_END);
            pageContent.append(H2_START).append(object.get("phone")).append(H2_END);
            pageContent.append(H2_START).append(object.get("address")).append(H2_END);
            pageContent.append("\t\t\t\t</section>\n");
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
