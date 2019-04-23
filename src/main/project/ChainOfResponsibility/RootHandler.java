package ChainOfResponsibility;

import Builder.*;
import Decorator.Decorator;
import Strategy.DatabaseHandler;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.simple.parser.ParseException;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;

public class RootHandler extends Handler implements HttpHandler {

    private DatabaseHandler db;
    private Director director;
    private HTMLTemplate template;

    RootHandler(DatabaseHandler db){
        this.db = db;
        director = new Director();
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        if (canHandle(httpExchange, "RootHandler")) {
            String response = null;
            try {
                response = createResponse(httpExchange);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            httpExchange.sendResponseHeaders(200, Objects.requireNonNull(response).length());
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } else {
            delegate(httpExchange);
        }
    }

    private String createResponse(HttpExchange httpExchange) throws IOException, ParseException {
        final String path = httpExchange.getRequestURI().getPath();
        final String url = path.split("/")[1];

        switch (url) {
            case "home":
                return getHomePage();
            case "about":
                return getAboutPage();
            case "contact":
                return getContactPage();
            case "article":
                return getArticlePage(path.split("/")[2]);
            default:
                return "ups";
        }
    }

    private String getArticlePage(String title) throws IOException, ParseException {
        TemplateBuilder templateBuilder = new ArticlePageBuilder(db, title);
        director.setBuilder(templateBuilder);
        director.build();
        template = director.getTemplate();

        final String ARTICLE_CONFIG_JSON = "articlePage.json";
        Decorator decorator = new Decorator(template.returnHTML(), new File(ARTICLE_CONFIG_JSON));

        return decorator.decorate();
    }

    private String getContactPage() throws IOException, ParseException {
        TemplateBuilder templateBuilder = new ContactPageBuilder(db);
        director.setBuilder(templateBuilder);
        director.build();
        template = director.getTemplate();

        final String CONTACT_CONFIG_JSON = "contactPage.json";
        Decorator decorator = new Decorator(template.returnHTML(), new File(CONTACT_CONFIG_JSON));

        return decorator.decorate();
    }

    private String getAboutPage() throws IOException, ParseException {
        TemplateBuilder templateBuilder = new AboutPageBuilder(db);
        director.setBuilder(templateBuilder);
        director.build();
        template = director.getTemplate();

        final String ABOUT_CONFIG_JSON = "aboutPage.json";
        Decorator decorator = new Decorator(template.returnHTML(), new File(ABOUT_CONFIG_JSON));

        return decorator.decorate();
    }

    private String getHomePage() throws IOException, ParseException {
        TemplateBuilder templateBuilder = new HomePageBuilder(db);
        director.setBuilder(templateBuilder);
        director.build();
        template = director.getTemplate();

        final String HOME_CONFIG_JSON = "homePage.json";
        Decorator decorator = new Decorator(template.returnHTML(), new File(HOME_CONFIG_JSON));

        return decorator.decorate();
    }
}
