package Builder;

import Strategy.Data;
import Strategy.DatabaseHandler;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.List;

public class HomePageBuilder extends TemplateBuilder {
    private DatabaseHandler db;
    private String tmpTemplate;
    private JSONArray JSONData;

    public HomePageBuilder(DatabaseHandler db){
        this.db = db;
    }

    @Override
    void buildData() {
        JSONArray jsonObject = new JSONArray();
        List<Data> data = db.getAll("Articles");

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
    void buildPageContent() {
        final String SECTION_START = "\t\t\t\t\t<section>\n";
        final String SECTION_END = "\t\t\t\t\t</section>\n";
        StringBuilder pageContent = new StringBuilder();
        pageContent.append("<section class='article-list'>\n");


        for (int i=0; i<getJSONData().size(); i++) {
            JSONObject object = (JSONObject) getJSONData().get(i);

            pageContent.append("\t\t\t\t<section class='article-item' id='").append(object.get("_id")).append("'>\n");
            pageContent.append(SECTION_START);
            pageContent.append("\t\t\t\t\t\t<img src='").append(object.get("thumbinail")).append("'>\n");
            pageContent.append(SECTION_END);
            pageContent.append(SECTION_START);
            pageContent.append("\t\t\t\t\t\t<h1>").append(object.get("title")).append("</h1>\n");
            pageContent.append(SECTION_END);
            pageContent.append("\t\t\t\t\t<section class='desc'>\n");
            pageContent.append("\t\t\t\t\t\t<p>").append(object.get("description")).append("</p>\n");
            pageContent.append(SECTION_END);
            pageContent.append(SECTION_START);
            pageContent.append("\t\t\t\t\t\t<a style='float: right;' href='/article/").append(object.get("url")).append("'>Read more</a>\n");
            pageContent.append(SECTION_END);
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
