package Builder;

public class Director {
    private TemplateBuilder builder;

    public void setBuilder(TemplateBuilder builder){
        this.builder = builder;
    }

    public HTMLTemplate getTemplate(){
        return builder.getTemplate();
    }

    public void build(){
        builder.newHTMLTemplate();
        builder.buildTemplate();
        builder.buildHTMLPath();
        builder.buildData();
        builder.buildPageContent();
        builder.getTemplate().returnHTML();
    }
}
