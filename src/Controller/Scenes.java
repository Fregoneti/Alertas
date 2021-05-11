package Controller;

public enum Scenes {

    LOGIN("login"),
    ALERTA("alertasHome"),
    TALERTASTOCK("alertaStockC"),
    ROOT("PRUYEBA");



    private String url;

    Scenes(String fxmlFile) {
        this.url = fxmlFile;
    }

    public String getUrl() {
        return url;
    }
}
