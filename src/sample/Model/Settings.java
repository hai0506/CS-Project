package sample.Model;

public class Settings {
    private String tempUnit;
    private String windUnit;
    private String language;

    public Settings(String tempUnit, String windUnit, String language) {
        this.tempUnit = tempUnit;
        this.windUnit = windUnit;
        this.language = language;
    }

    public String getTempUnit() {
        return tempUnit;
    }

    public void setTempUnit(String tempUnit) {
        this.tempUnit = tempUnit;
    }

    public String getWindUnit() {
        return windUnit;
    }

    public void setWindUnit(String windUnit) {
        this.windUnit = windUnit;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
    public String toString() {
        return tempUnit+"\n"+windUnit+"\n"+language;
    }
}
