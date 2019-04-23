package Builder;

public class HTMLTemplate implements Template {
    private String path;
    private String template;

    public void setPath(String path){
        this.path = path;
    }

    public void setTemplate(String template){
        this.template = template;
    }


    @Override
    public String returnHTML() {
        return this.template;
    }

    public String getPath() {
        return path;
    }
}
