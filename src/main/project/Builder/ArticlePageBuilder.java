package Builder;

import Strategy.Data;
import Strategy.DatabaseHandler;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.List;

public class ArticlePageBuilder extends TemplateBuilder{
    private DatabaseHandler db;
    private String tmpTemplate;
    private JSONArray JSONData;
    private String articleTitle;

    public ArticlePageBuilder(DatabaseHandler db, String articleTitle){
        this.db = db;
        this.articleTitle = articleTitle;
    }

    @Override
    void buildData() {
        JSONArray jsonObject = new JSONArray();
        List<Data> data = db.getOne("Articles",articleTitle);

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
        StringBuilder pageContent = new StringBuilder();
        pageContent.append("<section class='article'>\n");
        pageContent.append("<a href='/home'>").append("< go back").append("</a>");

        for (int i=0; i<getJSONData().size(); i++) {
            JSONObject object = (JSONObject) getJSONData().get(i);
            pageContent.append("\t\t\t\t\t<section>\n");
            pageContent.append("\t\t\t\t\t\t<img src='").append(object.get("image")).append("'>\n");
            pageContent.append("\t\t\t\t\t</section>\n");
            pageContent.append("\t\t\t\t\t<section>\n");
            pageContent.append("\t\t\t\t\t<h1>").append(object.get("title")).append("</h1>\n");
            pageContent.append("\t\t\t\t</section>\n");
            pageContent.append("\t\t\t\t<section id='").append(object.get("_id")).append("'>\n");
            pageContent.append("\t\t\t\t\t<p>").append(object.get("paragraph")).append("</p>\n");
            pageContent.append("\t\t\t\t</section>\n");
            pageContent.append("\t\t\t</section>\n");
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
