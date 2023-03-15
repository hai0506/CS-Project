package sample.Model;

import java.util.Date;

public class Forecast {
    private Date date;
    private long lowTemp, highTemp;
    private long lowHumid, highHumid;
    private long lowWindSpeed,highWindSpeed;
    private String weather;

    public Forecast(Date date, long lowTemp, long highTemp, long lowHumid, long highHumid, long lowWindSpeed, long highWindSpeed,String weather) {
        this.date = date;
        this.lowTemp = lowTemp;
        this.highTemp = highTemp;
        this.lowHumid = lowHumid;
        this.highHumid = highHumid;
        this.lowWindSpeed = lowWindSpeed;
        this.highWindSpeed = highWindSpeed;
        this.weather = weather;
    }

    public Date getDate() {
        return date;
    }

    public double getLowTemp() {
        return lowTemp;
    }

    public double getHighTemp() {
        return highTemp;
    }
    public double getLowTempF() {
        return lowTemp * 1.8 + 32;
    }

    public double getHighTempF() {
        return highTemp * 1.8 + 32;
    }
    public double getLowHumid() {
        return lowHumid;
    }

    public double getHighHumid() {
        return highHumid;
    }

    public long getLowWindSpeedms() { return lowWindSpeed; }
    public long getHighWindSpeedms() { return highWindSpeed; }
    public long getLowWindSpeedkmh() { return Math.round(lowWindSpeed*3.6); }
    public long getHighWindSpeedkmh() { return Math.round(highWindSpeed*3.6); }
    public long getLowWindSpeedfts() { return Math.round(lowWindSpeed*3.28); }
    public long getHighWindSpeedfts() { return Math.round(highWindSpeed*3.28); }
    public long getLowWindSpeedmih() { return Math.round(lowWindSpeed*2.237); }
    public long getHighWindSpeedmih() { return Math.round(highWindSpeed*2.237); }

    public String getWeather() {
        return weather;
    }
}
