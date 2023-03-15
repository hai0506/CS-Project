package sample.Control;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import sample.Model.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

public class SampleController {
    @FXML
    private AnchorPane mainPane;
    @FXML
    private GridPane locationOptionPane;
    @FXML
    private Button savedBtn;
    @FXML
    private Button nearBtn;
    @FXML
    private Button weatherBtn;
    @FXML
    private Button PSIBtn;
    @FXML
    private Button UVBtn;
    @FXML
    private Button forecastBtn;
    @FXML
    private Label PSIAdviceLbl;
    @FXML
    private Button addStationBtn;
    @FXML
    private Button removeStationBtn;
    @FXML
    private ScrollPane weatherPane;
    @FXML
    private Label updateTimeLbl;
    @FXML
    private GridPane PSIPane;
    @FXML
    private Line line;
    @FXML
    private Line line2;
    @FXML
    private ComboBox regionBox;
    @FXML
    private Label PM25Lbl;
    @FXML
    private Label COLbl;
    @FXML
    private Label NO2Lbl;
    @FXML
    private Label PM10Lbl;
    @FXML
    private Label SO2Lbl;
    @FXML
    private Label O3Lbl;
    @FXML
    private Label PSILbl;
    @FXML
    private Pane PSIImage;
    @FXML
    private GridPane UVPane;
    @FXML
    private Label UVIndexLbl;
    @FXML
    private Label UVAdviceLbl;
    @FXML
    private Hyperlink UVHyperlink;
    @FXML
    private GridPane forecastPane;
    @FXML
    private Label humid1;
    @FXML
    private Label wind1;
    @FXML
    private Label temp1;
    @FXML
    private Label w1;
    @FXML
    private Label day1Lbl;
    @FXML
    private Label humid2;
    @FXML
    private Label wind2;
    @FXML
    private Label temp2;
    @FXML
    private Label w2;
    @FXML
    private Label day2Lbl;
    @FXML
    private Label humid3;
    @FXML
    private Label wind3;
    @FXML
    private Label temp3;
    @FXML
    private Label w3;
    @FXML
    private Label day3Lbl;
    @FXML
    private Label humid4;
    @FXML
    private Label wind4;
    @FXML
    private Label temp4;
    @FXML
    private Label w4;
    @FXML
    private GridPane distPane;
    @FXML
    private TextField maxDistTF;
    @FXML
    private ScrollPane nearLocationPane;
    @FXML
    private GridPane settingsPane;
    @FXML
    private ToggleButton celsiusBtn;
    @FXML
    private ToggleButton farenheitBtn;
    @FXML
    private ComboBox languageBox;
    @FXML
    private ComboBox windUnitBox;
    @FXML
    private AnchorPane managePane;
    @FXML
    private ListView listView;
    @FXML
    private Button backBtn;
    @FXML
    private AnchorPane infoPane;
    @FXML
    private Label pollutantNameLbl;
    @FXML
    private Button backBtn2;
    @FXML
    private Label generalInfoLbl;
    @FXML
    private Label shorttermLbl;
    @FXML
    private Label longtermLbl;
    @FXML
    private AnchorPane aboutPane;
    @FXML
    private Button backBtn3;
    @FXML
    private AnchorPane UVInfoPane;
    @FXML
    private Button backBtn21;
    @FXML
    private ImageView settingsIcon;
    @FXML
    private ComboBox distUnitBox;
    @FXML
    private BarChart UVChart;
    private ArrayList<Station> stations = new ArrayList<>();
    private ArrayList<Station> savedStations = new ArrayList<>();
    private ArrayList<Region> regions = new ArrayList<>();
    private ArrayList<Forecast> forecasts = new ArrayList<>();
    private Settings settings;
    private ResourceBundle rb;
    private Coordinate myCoordinate;
    public void retrieveWeather() {
        try {
            Date d = new Date();
            String date = new SimpleDateFormat("yyyy-MM-dd'T'HH'%3A'mm'%3A00'").format(d);
            HttpURLConnection temperature_API = (HttpURLConnection) new URL("https://api.data.gov.sg/v1/environment/air-temperature?date_time=" + date).openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(temperature_API.getInputStream()));
            String temp = "";
            String current;
            while ((current = in.readLine()) != null) {
                temp += current;
            }
            PrintWriter writer = new PrintWriter(new FileOutputStream("src/sample/Model/JSON/temperature.json"));
            writer.println(temp);
            writer.close();


            HttpURLConnection wind_API = (HttpURLConnection) new URL("https://api.data.gov.sg/v1/environment/wind-speed?date_time=" + date).openConnection();
            in = new BufferedReader(new InputStreamReader(wind_API.getInputStream()));
            temp = "";
            while ((current = in.readLine()) != null) {
                temp += current;
            }
            PrintWriter writer4 = new PrintWriter(new FileOutputStream("src/sample/Model/JSON/wind.json"));
            writer4.println(temp);
            writer4.close();

            HttpURLConnection weather_API = (HttpURLConnection) new URL("https://api.data.gov.sg/v1/environment/24-hour-weather-forecast?date_time=" + date).openConnection();
            in = new BufferedReader(new InputStreamReader(weather_API.getInputStream()));
            temp = "";
            while ((current = in.readLine()) != null) {
                temp += current;
            }
            PrintWriter writer6 = new PrintWriter(new FileOutputStream("src/sample/Model/JSON/weather.json"));
            writer6.println(temp);
            writer6.close();

            HttpURLConnection humidity_API = (HttpURLConnection) new URL("https://api.data.gov.sg/v1/environment/relative-humidity?date_time=" + date).openConnection();
            in = new BufferedReader(new InputStreamReader(humidity_API.getInputStream()));
            temp = "";
            while ((current = in.readLine()) != null) {
                temp += current;
            }
            PrintWriter writer7 = new PrintWriter(new FileOutputStream("src/sample/Model/JSON/humidity.json"));
            writer7.println(temp);
            writer7.close();

            HttpURLConnection location_API = (HttpURLConnection) new URL("https://freegeoip.app/json/").openConnection();
            in = new BufferedReader(new InputStreamReader(location_API.getInputStream()));
            temp = "";
            while ((current = in.readLine()) != null) {
                temp += current;
            }
            PrintWriter writer9 = new PrintWriter(new FileOutputStream("src/sample/Model/JSON/location.json"));
            writer9.println(temp);
            writer9.close();

            if(settings.getLanguage().equals("English")) updateTimeLbl.setText("Last Updated: " + new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(d));
            else if(settings.getLanguage().equals("Español")) updateTimeLbl.setText("Última actualización: " + new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(d));
            else updateTimeLbl.setText("Cập nhật lúc: "+ new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(d));
            PrintWriter writer5 = new PrintWriter(new FileOutputStream("src/sample/Model/lastUpdatedTime.txt"));
            writer5.println(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(d));
            writer5.close();
        } catch (IOException e) {
            try {
                Scanner sc = new Scanner(new FileInputStream("src/sample/Model/lastUpdatedTime.txt"));
                if(settings.getLanguage().equals("English")) updateTimeLbl.setText("Last Updated: " + sc.nextLine() + " (No Internet Connection)");
                else if(settings.getLanguage().equals("Español")) updateTimeLbl.setText("Última actualización: " + sc.nextLine() + " (Sin conexión a Internet)");
                else updateTimeLbl.setText("Cập nhật lúc: " + sc.nextLine() + " (Không có kết nối)");
                sc.close();
            } catch (IOException e2) {
                System.out.println(e.getMessage());
            }
        }
    }
    public void retrieveOthers() {
        try {
            Date d = new Date();
            String date = new SimpleDateFormat("yyyy-MM-dd'T'HH'%3A'mm'%3A00'").format(d);
            HttpURLConnection PSI_API = (HttpURLConnection) new URL("https://api.data.gov.sg/v1/environment/psi?date_time=" + date).openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(PSI_API.getInputStream()));
            String temp = "";
            String current;
            while ((current = in.readLine()) != null) {
                temp += current;
            }
            PrintWriter writer2 = new PrintWriter(new FileOutputStream("src/sample/Model/JSON/PSI.json"));
            writer2.println(temp);
            writer2.close();

            HttpURLConnection UV_API = (HttpURLConnection) new URL("https://api.data.gov.sg/v1/environment/uv-index?date_time=" + date).openConnection();
            in = new BufferedReader(new InputStreamReader(UV_API.getInputStream()));
            temp = "";
            while ((current = in.readLine()) != null) {
                temp += current;
            }
            PrintWriter writer3 = new PrintWriter(new FileOutputStream("src/sample/Model/JSON/UV.json"));
            writer3.println(temp);
            writer3.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    public void retrieveForecast() {
        try {
            Date d = new Date();
            String date = new SimpleDateFormat("yyyy-MM-dd'T'HH'%3A'mm'%3A00'").format(d);
            HttpURLConnection forecast_API = (HttpURLConnection) new URL("https://api.data.gov.sg/v1/environment/4-day-weather-forecast?date_time=" + date).openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(forecast_API.getInputStream()));
            String temp = "";
            String current;
            while ((current = in.readLine()) != null) {
                temp += current;
            }
            PrintWriter writer8 = new PrintWriter(new FileOutputStream("src/sample/Model/JSON/forecast.json"));
            writer8.println(temp);
            writer8.close();
        } catch (IOException e) {System.out.println(e.getMessage());}
    }
    public void getLocation() {
        try {
            JSONParser jsonParser = new JSONParser();
            JSONObject obj = (JSONObject) jsonParser.parse(new FileReader("src/sample/Model/JSON/location.json"));
            double lat = (Double) obj.get("latitude");
            double lon = (Double) obj.get("longitude");
            myCoordinate = new Coordinate(lat,lon);
        }catch (IOException e) {System.out.println(e.getMessage());}
        catch (ParseException e) {System.out.println(e.getMessage());}

    }

    public void loadStations() {
        stations.clear();
        savedStations.clear();
        try {
            String name;
            String id;
            double latitude, longitude;
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader("src/sample/Model/JSON/temperature.json"));
            JSONObject stationData = (JSONObject) jsonObject.get("metadata");
            JSONArray s = (JSONArray) stationData.get("stations");
            for (int i = 0; i < s.size(); i++) {
                JSONObject obj = (JSONObject) s.get(i);
                id = (String) obj.get("device_id");
                name = (String) obj.get("name");
                latitude = (Double) ((JSONObject) obj.get("location")).get("latitude");
                longitude = (Double) ((JSONObject) obj.get("location")).get("longitude");
                JSONObject items = (JSONObject) ((JSONArray) jsonObject.get("items")).get(0);
                JSONArray readings = (JSONArray) items.get("readings");
                for (int j = 0; j < readings.size(); j++) {
                    JSONObject reading = (JSONObject) readings.get(j);
                    if (reading.get("station_id").equals(id)) {
                        if (reading.get("value") instanceof Double)
                            stations.add(new Station(name, id, (Double) reading.get("value"), longitude, latitude));
                        else if (reading.get("value") instanceof Long)
                            stations.add(new Station(name, id, ((Long) reading.get("value")).doubleValue(), longitude, latitude));
                    }
                }
            }
            JSONObject jsonObject2 = (JSONObject) jsonParser.parse(new FileReader("src/sample/Model/JSON/wind.json"));
            JSONObject items = (JSONObject) ((JSONArray) jsonObject2.get("items")).get(0);
            JSONArray readings = (JSONArray) (items.get("readings"));
            for (int i = 0; i < readings.size(); i++) {
                JSONObject obj = (JSONObject) readings.get(i);
                for (Station station : stations) {
                    if (station.getId().equals(obj.get("station_id"))) {
                        if (obj.get("value") instanceof Double)
                            station.setWindSpeed((Double) obj.get("value"));
                        else if (obj.get("value") instanceof Long)
                            station.setWindSpeed(((Long) obj.get("value")).doubleValue());
                    }
                }
            }

            JSONObject jsonObject3 = (JSONObject) jsonParser.parse(new FileReader("src/sample/Model/JSON/weather.json"));
            JSONObject weatherItems = (JSONObject) ((JSONArray) jsonObject3.get("items")).get(0);
            JSONObject weather = (JSONObject) ((JSONArray) weatherItems.get("periods")).get(0);
            JSONObject data = (JSONObject) weather.get("regions");
            for (Station station : stations) {
                station.setWeather((String) data.get(station.getRegion()));
            }

            JSONObject jsonObject4 = (JSONObject) jsonParser.parse(new FileReader("src/sample/Model/JSON/humidity.json"));
            JSONObject items4 = (JSONObject) ((JSONArray) jsonObject4.get("items")).get(0);
            JSONArray readings4 = (JSONArray) (items4.get("readings"));
            for (int i = 0; i < readings4.size(); i++) {
                JSONObject obj = (JSONObject) readings4.get(i);
                for (Station station : stations) {
                    if (station.getId().equals(obj.get("station_id"))) {
                        if (obj.get("value") instanceof Double)
                            station.setHumidity((Double) obj.get("value"));
                        else if (obj.get("value") instanceof Long)
                            station.setHumidity(((Long) obj.get("value")).doubleValue());
                    }
                }
            }

            Scanner sc = new Scanner(new FileInputStream("src/sample/Model/saved.txt"));
            while (sc.hasNextLine()) {
                String savedId = sc.nextLine();
                for (Station st : stations) {
                    if (st.getId().equals(savedId)) {
                        savedStations.add(st);
                    }
                }
            }
            sc.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
    }

    public void loadRegions() {
        regions.clear();
        try {
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader("src/sample/Model/JSON/PSI.json"));
            JSONObject items = (JSONObject) ((JSONArray) jsonObject.get("items")).get(0);
            JSONObject readings = (JSONObject) items.get("readings");
            JSONObject o3 = (JSONObject) readings.get("o3_sub_index");
            JSONObject pm10 = (JSONObject) readings.get("pm10_twenty_four_hourly");
            JSONObject co = (JSONObject) readings.get("co_sub_index");
            JSONObject pm25 = (JSONObject) readings.get("pm25_twenty_four_hourly");
            JSONObject no2 = (JSONObject) readings.get("no2_one_hour_max");
            JSONObject so2 = (JSONObject) readings.get("so2_twenty_four_hourly");
            JSONObject psi = (JSONObject) readings.get("psi_twenty_four_hourly");
            JSONObject[] pollutants = {pm10, co, pm25, so2, no2, psi, o3};
            String[] names = {"north", "south", "east", "west", "central"};
            for (int i = 0; i < names.length; i++) {
                double[] values = new double[7];
                for (int j = 0; j < pollutants.length; j++) {
                    if (pollutants[j].get(names[i]) instanceof Double)
                        values[j] = (Double) pollutants[j].get(names[i]);
                    else if (pollutants[j].get(names[i]) instanceof Long)
                        values[j] = ((Long) pollutants[j].get(names[i])).doubleValue();
                }
                regions.add(new Region(names[i], values[0], values[1], values[2], values[3], values[4], values[5], values[6]));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
    }

    public void loadUV() {
        try {
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader("src/sample/Model/JSON/UV.json"));
            JSONObject items = (JSONObject) ((JSONArray) jsonObject.get("items")).get(0);
            JSONArray index = (JSONArray) items.get("index");
            JSONObject value = (JSONObject) index.get(0);
            long UVIndex = (Long) value.get("value");
            UVIndexLbl.setText(UVIndex + "");
            if (UVIndex <= 2) {
                UVIndexLbl.setTextFill(Color.web("#00FF00"));
                UVAdviceLbl.setText(rb.getString("uv1"));
            } else if (UVIndex <= 5) {
                UVIndexLbl.setTextFill(Color.web("yellow"));
                UVAdviceLbl.setText(rb.getString("uv2"));
            } else if (UVIndex <= 7) {
                UVIndexLbl.setTextFill(Color.web("orange"));
                UVAdviceLbl.setText(rb.getString("uv3"));

            } else if (UVIndex <= 10) {
                UVIndexLbl.setTextFill(Color.web("red"));
                UVAdviceLbl.setText(rb.getString("uv4"));
            } else {
                UVIndexLbl.setTextFill(Color.web("purple"));
                UVAdviceLbl.setText(rb.getString("uv5"));
            }
            UVChart.getData().clear();
            XYChart.Series<String,Long> series = new XYChart.Series<>();
            for(int i = index.size()-1; i >= 0; i--) {
                JSONObject obj = (JSONObject)index.get(i);
                String date = ((String)obj.get("timestamp")).substring(((String) obj.get("timestamp")).length()-14,((String) obj.get("timestamp")).length()-9);
                series.getData().add(new XYChart.Data(date,obj.get("value")));
            }
            UVChart.getData().add(series);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (ParseException e) {
            System.out.println(e.getMessage());

        }
    }

    public void initialize() {
        languageBox.getItems().addAll("English", "Español", "Tiếng Việt");
        windUnitBox.getItems().addAll("m/s", "km/h", "ft/s", "mi/h");
        distUnitBox.getItems().addAll("km","mile");
        distUnitBox.getSelectionModel().selectFirst();
        ToggleGroup g = new ToggleGroup();
        celsiusBtn.setToggleGroup(g);
        farenheitBtn.setToggleGroup(g);
        try {
            Scanner sc = new Scanner(new FileInputStream("src/sample/Model/default.txt"));
            String degree = sc.nextLine();
            String windUnit = sc.nextLine();
            String language = sc.nextLine();
            settings = new Settings(degree,windUnit,language);
            sc.close();
            if(degree.equals("Celsius")) celsiusBtn.setSelected(true);
            else farenheitBtn.setSelected(true);
            windUnitBox.getSelectionModel().select(windUnit);
            languageBox.getSelectionModel().select(language);
            if(language.equals("Español")) rb = ResourceBundle.getBundle("sample.Model.MessagesBundle", new Locale("es","ES"));
            else if (language.equals("Tiếng Việt")) rb = ResourceBundle.getBundle("sample.Model.MessagesBundle", new Locale("vi","VN"));
            else rb = ResourceBundle.getBundle("sample.Model.MessagesBundle", new Locale("en","US"));

        } catch (IOException e) {System.out.println(e.getMessage());}
        listView.setStyle("-fx-font-size: 1.5em;");
        mainPane.setDisable(false);
        mainPane.setVisible(true);
        managePane.setDisable(true);
        managePane.setVisible(false);
        weatherPane.fitToWidthProperty().set(true);
        regionBox.getItems().setAll("Central", "North", "South", "West", "East");
        regionBox.getSelectionModel().selectFirst();
        Runnable task1 = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                retrieveWeather();
                                getLocation();
                                loadStations();
                                loadRegions();
                                loadWeather();
                                loadNear();
                            }
                        });
                        Thread.sleep(60000);
                    } catch (InterruptedException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        };
        Thread backgroundThread = new Thread(task1);
        backgroundThread.setDaemon(true);
        backgroundThread.start();
        Runnable task2 = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                retrieveOthers();
                                loadPSI();
                                loadUV();
                            }
                        });
                        Thread.sleep(60000 * 3600);
                    } catch (InterruptedException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        };
        Thread backgroundThread2 = new Thread(task2);
        backgroundThread2.setDaemon(true);
        backgroundThread2.start();
        Runnable task3 = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                retrieveForecast();
                                loadForecast();
                            }
                        });
                        Thread.sleep(60000 * 1800);
                    } catch (InterruptedException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        };
        Thread backgroundThread3 = new Thread(task3);
        backgroundThread3.setDaemon(true);
        backgroundThread3.start();
    }

    public void showWeather() {
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(1), line);
        translateTransition.setFromX(450);
        translateTransition.setToX(0);
        translateTransition.setCycleCount(1);
        translateTransition.play();
        line.setVisible(true);
        line2.setVisible(true);
        weatherPane.setVisible(true);
        weatherPane.setDisable(false);
        nearLocationPane.setVisible(false);
        nearLocationPane.setDisable(true);
        PSIPane.setVisible(false);
        PSIPane.setDisable(true);
        addStationBtn.setVisible(true);
        addStationBtn.setDisable(false);
        removeStationBtn.setVisible(true);
        removeStationBtn.setDisable(false);
        locationOptionPane.setVisible(true);
        locationOptionPane.setDisable(false);
        UVPane.setVisible(false);
        UVPane.setDisable(true);
        forecastPane.setVisible(false);
        forecastPane.setDisable(true);
        PSIAdviceLbl.setVisible(false);
        weatherBtn.setStyle("-fx-background-color: #D8D8D8; -fx-text-fill: #086A87;");
        PSIBtn.setStyle("-fx-background-color: white; -fx-text-fill: #086A87;");
        UVBtn.setStyle("-fx-background-color: white; -fx-text-fill: #086A87;");
        forecastBtn.setStyle("-fx-background-color: white; -fx-text-fill: #086A87;");
    }

    public void loadWeather() {
        try {
            TilePane panes = new TilePane(10, 10);
            panes.setPrefHeight(280);
            panes.setPrefWidth(870);
            panes.setPadding(new Insets(10, 0, 10, 10));
            panes.setStyle("-fx-background-color: #E6E6E6");
            for (Station s : savedStations) {
                Pane newLoadedPane = FXMLLoader.load(getClass().getResource("/sample/View/weatherpane.fxml"));
                newLoadedPane.setStyle("-fx-border-radius: 10 10 10 10; -fx-background-radius: 10 10 10 10; -fx-background-color: white;");
                List<Node> nodes = newLoadedPane.getChildren();
                GridPane gp = (GridPane) nodes.get(0);
                Label name = (Label) gp.getChildren().get(4);
                Label temp = (Label) gp.getChildren().get(1);
                Label wind = (Label) gp.getChildren().get(3);
                Label weather = (Label) gp.getChildren().get(5);
                Label humidity = (Label) gp.getChildren().get(7);
                name.setText(s.getName());
                if(celsiusBtn.isSelected()) temp.setText(String.format(new Locale("en","US"),"%.1f",s.getTemperature()) + " °C");
                else if(farenheitBtn.isSelected()) temp.setText(String.format(new Locale("en","US"),"%.1f",s.getFahrenheit()) + " °F");
                temp.setTooltip(new Tooltip(rb.getString("tempTt")));
                if (s.getWindSpeed() == 999.0) wind.setText("No data");
                else {
                    if(windUnitBox.getValue().equals("m/s")) wind.setText(String.format(new Locale("en","US"),"%.1f", s.getWindSpeed()) + " m/s");
                    else if(windUnitBox.getValue().equals("km/h")) wind.setText(String.format(new Locale("en","US"),"%.1f", s.getWindSpeedkmh()) + " km/h");
                    else if(windUnitBox.getValue().equals("ft/s")) wind.setText(String.format(new Locale("en","US"),"%.1f", s.getWindSpeedfts()) + " ft/s");
                    else wind.setText(String.format(new Locale("en","US"),"%.1f", s.getWindSpeedmih()) + " mi/h");
                }
                wind.setTooltip(new Tooltip(rb.getString("windTt")));
                weather.setText(s.getWeather());
                humidity.setText(s.getHumidity() + "%");
                humidity.setTooltip(new Tooltip(rb.getString("humidTt")));
                panes.getChildren().add(newLoadedPane);
            }
            weatherPane.setContent(panes);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    public void loadNear() {
        double maxDist=0;
        if(maxDistTF.getText().isEmpty()) maxDist = 999999999;
        else if(maxDistTF.getText().matches("\\d+")) {
            if (distUnitBox.getValue().equals("km")) {
                maxDist = Double.parseDouble(maxDistTF.getText());
            }
            if (distUnitBox.getValue().equals("mile")) {
                maxDist = Double.parseDouble(maxDistTF.getText()) * 1.609344;
            }
        }
        try {
            TilePane panes = new TilePane(10, 10);
            panes.setPrefHeight(282);
            panes.setPrefWidth(880);
            panes.setPadding(new Insets(10, 0, 10, 10));
            panes.setStyle("-fx-background-color: #E6E6E6");
            for (Station s : stations) {
                if (s.getCoordinate().calDist(myCoordinate) <= maxDist) {
                    Pane newLoadedPane = FXMLLoader.load(getClass().getResource("/sample/View/weatherpane.fxml"));
                    newLoadedPane.setStyle("-fx-border-radius: 10 10 10 10; -fx-background-radius: 10 10 10 10; -fx-background-color: white;");
                    List<Node> nodes = newLoadedPane.getChildren();
                    GridPane gp = (GridPane) nodes.get(0);
                    Label name = (Label) gp.getChildren().get(4);
                    Label temp = (Label) gp.getChildren().get(1);
                    Label wind = (Label) gp.getChildren().get(3);
                    Label weather = (Label) gp.getChildren().get(5);
                    Label humidity = (Label) gp.getChildren().get(7);
                    Label dist = (Label) gp.getChildren().get(8);
                    if(distUnitBox.getValue().equals("km")) dist.setText(rb.getString("dist")+ String.format(new Locale("en","US"),"%.1f",s.getCoordinate().calDist(myCoordinate)) + distUnitBox.getValue());
                    else dist.setText(rb.getString("dist") + String.format(new Locale("en","US"),"%.1f",s.getCoordinate().calDist(myCoordinate)/1.609344) +" "+ distUnitBox.getValue());
                    name.setText(s.getName());
                    if (celsiusBtn.isSelected())
                        temp.setText(String.format(new Locale("en", "US"), "%.1f", s.getTemperature()) + " °C");
                    else if (farenheitBtn.isSelected())
                        temp.setText(String.format(new Locale("en", "US"), "%.1f", s.getFahrenheit()) + " °F");
                    temp.setTooltip(new Tooltip(rb.getString("tempTt")));
                    if (s.getWindSpeed() == 999.0) wind.setText("No data");
                    else {
                        if (windUnitBox.getValue().equals("m/s"))
                            wind.setText(String.format(new Locale("en", "US"), "%.1f", s.getWindSpeed()) + " m/s");
                        else if (windUnitBox.getValue().equals("km/h"))
                            wind.setText(String.format(new Locale("en", "US"), "%.1f", s.getWindSpeedkmh()) + " km/h");
                        else if (windUnitBox.getValue().equals("ft/s"))
                            wind.setText(String.format(new Locale("en", "US"), "%.1f", s.getWindSpeedfts()) + " ft/s");
                        else wind.setText(String.format(new Locale("en", "US"), "%.1f", s.getWindSpeedmih()) + " mi/h");
                    }
                    wind.setTooltip(new Tooltip(rb.getString("windTt")));
                    weather.setText(s.getWeather());
                    humidity.setText(s.getHumidity() + "%");
                    humidity.setTooltip(new Tooltip(rb.getString("humidTt")));
                    panes.getChildren().add(newLoadedPane);
                }
            }
            nearLocationPane.setContent(panes);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    public void showSaved(ActionEvent event) {
        weatherPane.setVisible(true);
        addStationBtn.setDisable(false);
        addStationBtn.setVisible(true);
        removeStationBtn.setDisable(false);
        removeStationBtn.setVisible(true);
        distPane.setVisible(false);
        distPane.setDisable(true);
        if(nearLocationPane.isVisible()) {
            TranslateTransition translateTransition = new TranslateTransition(Duration.millis(200), line);
            translateTransition.setFromX(450);
            translateTransition.setToX(0);
            translateTransition.setCycleCount(1);
            translateTransition.play();
            nearLocationPane.setVisible(false);
        }
    }
    public void showNear(ActionEvent event) {
        nearLocationPane.setVisible(true);
        nearLocationPane.setDisable(false);
        addStationBtn.setDisable(true);
        addStationBtn.setVisible(false);
        removeStationBtn.setDisable(true);
        removeStationBtn.setVisible(false);
        distPane.setVisible(true);
        distPane.setDisable(false);
        if(weatherPane.isVisible()) {
            TranslateTransition translateTransition = new TranslateTransition(Duration.millis(200), line);
            translateTransition.setFromX(0);
            translateTransition.setToX(450);
            translateTransition.setCycleCount(1);
            translateTransition.play();
            weatherPane.setVisible(false);
        }
    }
    public void addStation(ActionEvent event) {
        listView.getItems().clear();
        mainPane.setDisable(true);
        mainPane.setVisible(false);
        settingsIcon.setDisable(true);
        settingsIcon.setVisible(false);
        managePane.setDisable(false);
        managePane.setVisible(true);
        for (Station s : stations) {
            if (!savedStations.contains(s)) listView.getItems().add(s.getName());
        }
        listView.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                String station = (String) listView.getSelectionModel().getSelectedItem();
                for (Station s : stations) {
                    if (s.getName().equals(station)) {
                        Alert a = new Alert(Alert.AlertType.CONFIRMATION, rb.getString("addConfirm"), ButtonType.YES, ButtonType.NO);
                        a.setHeaderText(rb.getString("add"));
                        a.showAndWait();
                        if (a.getResult().equals(ButtonType.YES)) {
                            savedStations.add(s);
                            listView.getItems().remove(s.getName());
                            try {
                                PrintWriter w = new PrintWriter(new FileOutputStream("src/sample/Model/saved.txt"));
                                for (Station s2 : savedStations) {
                                    w.println(s2.getId());
                                }
                                w.close();
                            } catch (IOException ex) {
                                System.out.println(ex);
                            }
                        } else a.close();
                    }
                }
            }
        });
    }

    public void removeStation(ActionEvent event) {
        listView.getItems().clear();
        mainPane.setDisable(true);
        mainPane.setVisible(false);
        settingsIcon.setDisable(true);
        settingsIcon.setVisible(false);
        managePane.setDisable(false);
        managePane.setVisible(true);
        for (Station s : savedStations) {
            listView.getItems().add(s.getName());
        }
        listView.setOnMouseClicked(e -> {
            Station removing = null;
            if (e.getClickCount() == 2) {
                String station = (String) listView.getSelectionModel().getSelectedItem();
                for (Station s : savedStations) {
                    if (s.getName().equals(station)) {
                        removing = s;
                    }
                }
                Alert a = new Alert(Alert.AlertType.CONFIRMATION, rb.getString("removeConfirm"), ButtonType.YES, ButtonType.NO);
                a.setHeaderText(rb.getString("remove"));
                a.showAndWait();
                if (a.getResult().equals(ButtonType.YES)) {
                    savedStations.remove(removing);
                    listView.getItems().remove(removing.getName());
                    try {
                        PrintWriter w = new PrintWriter(new FileOutputStream("src/sample/Model/saved.txt"));
                        for (Station s2 : savedStations) {
                            w.println(s2.getId());
                        }
                        w.close();
                    } catch (IOException ex) {
                        System.out.println(ex);
                    }
                } else a.close();
            }
        });
    }

    public void back(ActionEvent e) {
        loadWeather();
        mainPane.setDisable(false);
        mainPane.setVisible(true);
        managePane.setDisable(true);
        managePane.setVisible(false);
        settingsIcon.setDisable(false);
        settingsIcon.setVisible(true);
    }

    public void showPSI() {
        line.setVisible(false);
        line2.setVisible(false);
        PSIPane.setVisible(true);
        PSIPane.setDisable(false);
        weatherPane.setVisible(false);
        weatherPane.setDisable(true);
        nearLocationPane.setVisible(false);
        nearLocationPane.setDisable(true);
        locationOptionPane.setVisible(false);
        locationOptionPane.setDisable(true);
        distPane.setVisible(false);
        distPane.setDisable(true);
        UVPane.setVisible(false);
        UVPane.setDisable(true);
        addStationBtn.setVisible(false);
        addStationBtn.setDisable(true);
        removeStationBtn.setVisible(false);
        removeStationBtn.setDisable(true);
        forecastPane.setVisible(false);
        forecastPane.setDisable(true);
        PSIAdviceLbl.setVisible(true);
        weatherBtn.setStyle("-fx-background-color: white; -fx-text-fill: #086A87;");
        PSIBtn.setStyle("-fx-background-color: #D8D8D8; -fx-text-fill: #086A87;");
        UVBtn.setStyle("-fx-background-color: white; -fx-text-fill: #086A87;");
        forecastBtn.setStyle("-fx-background-color: white; -fx-text-fill: #086A87;");
    }

    public void showInfo(MouseEvent event) {
        infoPane.setDisable(false);
        settingsIcon.setDisable(true);
        FadeTransition f1 = new FadeTransition(Duration.seconds(0.5), mainPane);
        FadeTransition f2 = new FadeTransition(Duration.seconds(0.5), settingsIcon);
        FadeTransition f3 = new FadeTransition(Duration.seconds(0.5), infoPane);
        f1.setFromValue(1);
        f1.setToValue(0.5);
        f2.setFromValue(1);
        f2.setToValue(0.5);
        f3.setFromValue(0);
        f3.setToValue(1);
        f1.play();
        f2.play();
        f3.play();
        if (event.getSource().equals(PM25Lbl)) {
            pollutantNameLbl.setText("PM2.5");
            generalInfoLbl.setText(rb.getString("pm25General"));
            shorttermLbl.setText(rb.getString("pm25Short"));
            longtermLbl.setText(rb.getString("pm25Long"));
        } else if (event.getSource().equals(PM10Lbl)) {
            pollutantNameLbl.setText("PM10");
            generalInfoLbl.setText(rb.getString("pm10General"));
            shorttermLbl.setText(rb.getString("pm10Short"));
            longtermLbl.setText(rb.getString("pm10Long"));
        } else if (event.getSource().equals(SO2Lbl)) {
            pollutantNameLbl.setText("SO2");
            generalInfoLbl.setText(rb.getString("SO2General"));
            shorttermLbl.setText(rb.getString("SO2Short"));
            longtermLbl.setText(rb.getString("SO2Long"));
        } else if (event.getSource().equals(COLbl)) {
            pollutantNameLbl.setText("CO");
            generalInfoLbl.setText(rb.getString("COGeneral"));
            shorttermLbl.setText(rb.getString("COShort"));
            longtermLbl.setText(rb.getString("COLong"));
        } else if (event.getSource().equals(NO2Lbl)) {
            pollutantNameLbl.setText("NO2");
            generalInfoLbl.setText(rb.getString("NO2General"));
            shorttermLbl.setText(rb.getString("NO2Short"));
            longtermLbl.setText(rb.getString("NO2Long"));
        } else {
            pollutantNameLbl.setText("O3");
            generalInfoLbl.setText(rb.getString("O3General"));
            shorttermLbl.setText(rb.getString("O3Short"));
            longtermLbl.setText(rb.getString("O3Long"));
        }
    }

    public void back2(ActionEvent e) {
        infoPane.setDisable(true);
        settingsIcon.setDisable(false);
        FadeTransition f1 = new FadeTransition(Duration.seconds(0.5), mainPane);
        FadeTransition f2 = new FadeTransition(Duration.seconds(0.5), settingsIcon);
        FadeTransition f3 = new FadeTransition(Duration.seconds(0.5), infoPane);
        f1.setFromValue(0.5);
        f1.setToValue(1);
        f2.setFromValue(0.5);
        f2.setToValue(1);
        f3.setFromValue(1);
        f3.setToValue(0);
        f1.play();
        f2.play();
        f3.play();
    }

    public void loadPSI() {
        String regStr = ((String) regionBox.getValue()).toLowerCase();
        for (Region r : regions) {
            if (r.getName().equals(regStr)) {
                PSILbl.setText(r.getPsi() + "");
                COLbl.setText("CO: " + r.getCo());
                NO2Lbl.setText("NO2: " + r.getNo2());
                O3Lbl.setText("O3: " + r.getO3());
                SO2Lbl.setText("SO2: " + r.getSo2());
                PM10Lbl.setText("PM10: " + r.getPm10());
                PM25Lbl.setText("PM2.5: " + r.getPm25());
                if (r.getPsi() <= 50) {
                    PSIImage.setStyle("-fx-background-color: #00FF00;");
                    PSIAdviceLbl.setText(rb.getString("psi1"));
                } else if (r.getPsi() <= 100) {
                    PSIImage.setStyle("-fx-background-color: yellow;");
                    PSIAdviceLbl.setText(rb.getString("psi2"));
                } else if (r.getPsi() <= 150) {
                    PSIImage.setStyle("-fx-background-color: orange;");
                    PSIAdviceLbl.setText(rb.getString("psi3"));
                } else if (r.getPsi() <= 200) {
                    PSIImage.setStyle("-fx-background-color: red;");
                    PSIAdviceLbl.setText(rb.getString("psi4"));
                } else if (r.getPsi() <= 300) {
                    PSIImage.setStyle("-fx-background-color: purple;");
                    PSIAdviceLbl.setText(rb.getString("psi5"));
                } else {
                    PSIImage.setStyle("-fx-background-color: #800000;");
                    PSIAdviceLbl.setText(rb.getString("psi6"));
                }
            }
        }
    }

    public void showUV() {
        line.setVisible(false);
        line2.setVisible(false);
        PSIPane.setVisible(false);
        PSIPane.setDisable(true);
        weatherPane.setVisible(false);
        weatherPane.setDisable(true);
        UVPane.setVisible(true);
        UVPane.setDisable(false);
        addStationBtn.setVisible(false);
        addStationBtn.setDisable(true);
        removeStationBtn.setVisible(false);
        removeStationBtn.setDisable(true);
        forecastPane.setVisible(false);
        forecastPane.setDisable(true);
        PSIAdviceLbl.setVisible(false);
        nearLocationPane.setVisible(false);
        nearLocationPane.setDisable(true);
        locationOptionPane.setVisible(false);
        locationOptionPane.setDisable(true);
        distPane.setVisible(false);
        distPane.setDisable(true);
        weatherBtn.setStyle("-fx-background-color: white; -fx-text-fill: #086A87;");
        PSIBtn.setStyle("-fx-background-color: white; -fx-text-fill: #086A87;");
        UVBtn.setStyle("-fx-background-color: #D8D8D8; -fx-text-fill: #086A87;");
        forecastBtn.setStyle("-fx-background-color: white; -fx-text-fill: #086A87;");
    }

    public void exitSettings(MouseEvent event) {
        if (settingsPane.getOpacity()==1) {
            settingsPane.setDisable(true);
            FadeTransition f1 = new FadeTransition(Duration.seconds(0.3), settingsPane);
            f1.setFromValue(1);
            f1.setToValue(0);
            f1.play();
        }
    }

    public void showSettings(MouseEvent event) {
        if (settingsPane.getOpacity()==1) {
            settingsPane.setDisable(true);
            FadeTransition f1 = new FadeTransition(Duration.seconds(0.3), settingsPane);
            f1.setFromValue(1);
            f1.setToValue(0);
            f1.play();
        } else {
            settingsPane.setDisable(false);
            FadeTransition f1 = new FadeTransition(Duration.seconds(0.3), settingsPane);
            f1.setFromValue(0);
            f1.setToValue(1);
            f1.play();
        }
    }

    public void showAbout(MouseEvent event) {
        aboutPane.setDisable(false);
        settingsIcon.setDisable(true);
        FadeTransition f1 = new FadeTransition(Duration.seconds(0.5), mainPane);
        FadeTransition f2 = new FadeTransition(Duration.seconds(0.5), settingsIcon);
        FadeTransition f3 = new FadeTransition(Duration.seconds(0.5), aboutPane);
        f1.setFromValue(1);
        f1.setToValue(0);
        f2.setFromValue(1);
        f2.setToValue(0);
        f3.setFromValue(0);
        f3.setToValue(1);
        f1.play();
        f2.play();
        f3.play();
        f3.setOnFinished(e-> {
            mainPane.setDisable(true);
        });
    }

    public void back3(ActionEvent event) {
        aboutPane.setDisable(true);
        settingsIcon.setDisable(false);
        FadeTransition f1 = new FadeTransition(Duration.seconds(0.5), mainPane);
        FadeTransition f2 = new FadeTransition(Duration.seconds(0.5), settingsIcon);
        FadeTransition f3 = new FadeTransition(Duration.seconds(0.5), aboutPane);
        f1.setFromValue(0.5);
        f1.setToValue(1.0);
        f2.setFromValue(0.5);
        f2.setToValue(1.0);
        f3.setFromValue(1.0);
        f3.setToValue(0.0);
        f1.play();
        f2.play();
        f3.play();
        f3.setOnFinished(e-> {
            mainPane.setDisable(false);
        });
    }

    public void UVInfo(ActionEvent event) {
        UVInfoPane.setDisable(false);
        settingsIcon.setDisable(true);
        FadeTransition f1 = new FadeTransition(Duration.seconds(0.5), mainPane);
        FadeTransition f2 = new FadeTransition(Duration.seconds(0.5), settingsIcon);
        FadeTransition f3 = new FadeTransition(Duration.seconds(0.5), UVInfoPane);
        f1.setFromValue(1.0);
        f1.setToValue(0.5);
        f2.setFromValue(1.0);
        f2.setToValue(0.5);
        f3.setFromValue(0.0);
        f3.setToValue(1.0);
        f1.play();
        f2.play();
        f3.play();
    }

    public void back4(ActionEvent event) {
        UVInfoPane.setDisable(true);
        settingsIcon.setDisable(false);
        FadeTransition f1 = new FadeTransition(Duration.seconds(0.5), mainPane);
        FadeTransition f2 = new FadeTransition(Duration.seconds(0.5), settingsIcon);
        FadeTransition f3 = new FadeTransition(Duration.seconds(0.5), UVInfoPane);
        f1.setFromValue(0.5);
        f1.setToValue(1.0);
        f2.setFromValue(0.5);
        f2.setToValue(1.0);
        f3.setFromValue(1.0);
        f3.setToValue(0.0);
        f1.play();
        f2.play();
        f3.play();
    }

    public void toggle(ActionEvent event) {
        if (event.getSource().equals(celsiusBtn)) {
            if (!celsiusBtn.isSelected() && !farenheitBtn.isSelected()) {
                farenheitBtn.setSelected(true);
            }
        }
        if (event.getSource().equals(farenheitBtn)) {
            if (!farenheitBtn.isSelected() && !celsiusBtn.isSelected()) {
                celsiusBtn.setSelected(true);
            }
        }
        loadWeather();
        loadNear();
        loadForecast();
        try {
            PrintWriter w = new PrintWriter(new FileOutputStream("src/sample/Model/default.txt"));
            if(celsiusBtn.isSelected()) settings.setTempUnit("Celsius");
            else settings.setTempUnit("Fahrenheit");
            w.println(settings);
            w.close();
        } catch (IOException e) {System.out.println(e.getMessage());}
    }
    public void changeWindUnit(ActionEvent event) {
        loadWeather();
        loadNear();
        loadForecast();
        try {
            PrintWriter w = new PrintWriter(new FileOutputStream("src/sample/Model/default.txt"));
            settings.setWindUnit((String) windUnitBox.getValue());
            w.println(settings);
            w.close();
        } catch (IOException e) {System.out.println(e.getMessage());}
    }
    public void changeLanguage(ActionEvent event) {
        try {
            PrintWriter w = new PrintWriter(new FileOutputStream("src/sample/Model/default.txt"));
            settings.setLanguage((String) languageBox.getValue());
            w.println(settings);
            w.close();
            Stage stage = (Stage) mainPane.getScene().getWindow();
            stage.close();
            if(settings.getLanguage().equals("Español")) rb = ResourceBundle.getBundle("sample.Model.MessagesBundle", new Locale("es","ES"));
            else if (settings.getLanguage().equals("Tiếng Việt")) rb = ResourceBundle.getBundle("sample.Model.MessagesBundle", new Locale("vi","VN"));
            else rb = ResourceBundle.getBundle("sample.Model.MessagesBundle", new Locale("en","US"));
            Parent root = FXMLLoader.load(getClass().getResource("/sample/View/sample.fxml"),rb);
            Stage s = new Stage();
            s.setTitle("Singapore's Atmospheric Data");
            s.setScene(new Scene(root));
            s.getIcons().add(new Image("/sample/View/app-icon.jpg"));
            s.show();
        } catch (IOException e) {System.out.println(e.getMessage());}
    }
    public void showForecast(ActionEvent event) {
        line.setVisible(false);
        line2.setVisible(false);
        PSIPane.setVisible(false);
        PSIPane.setDisable(true);
        weatherPane.setVisible(false);
        weatherPane.setDisable(true);
        nearLocationPane.setVisible(false);
        nearLocationPane.setDisable(true);
        locationOptionPane.setVisible(false);
        locationOptionPane.setDisable(true);
        distPane.setVisible(false);
        distPane.setDisable(true);
        UVPane.setVisible(false);
        UVPane.setDisable(true);
        addStationBtn.setVisible(false);
        addStationBtn.setDisable(true);
        removeStationBtn.setVisible(false);
        removeStationBtn.setDisable(true);
        PSIAdviceLbl.setVisible(false);
        forecastPane.setVisible(true);
        forecastPane.setDisable(false);
        weatherBtn.setStyle("-fx-background-color: white; -fx-text-fill: #086A87;");
        PSIBtn.setStyle("-fx-background-color: white; -fx-text-fill: #086A87;");
        UVBtn.setStyle("-fx-background-color: white; -fx-text-fill: #086A87;");
        forecastBtn.setStyle("-fx-background-color: #D8D8D8; -fx-text-fill: #086A87;");
    }
    public void loadForecast() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader("src/sample/Model/JSON/forecast.json"));
            JSONObject items = (JSONObject) ((JSONArray) jsonObject.get("items")).get(0);
            JSONArray forecastsItems = (JSONArray) items.get("forecasts");
            for(int i = 0; i < 4; i++) {
                JSONObject obj = (JSONObject)forecastsItems.get(i);
                Date d = sdf.parse((String)obj.get("date"));
                JSONObject temp = (JSONObject)obj.get("temperature");
                Long lowT = (Long)temp.get("low");
                Long highT = (Long)temp.get("high");
                JSONObject humid = (JSONObject)obj.get("relative_humidity");
                Long lowH = (Long)humid.get("low");
                Long highH = (Long)humid.get("high");
                JSONObject wind = (JSONObject)((JSONObject)obj.get("wind")).get("speed");
                Long lowW = (Long)wind.get("low");
                Long highW = (Long)wind.get("high");
                String weather = (String)(obj.get("forecast"));
                Forecast f = new Forecast(d,lowT,highT,lowH,highH,lowW,highW,weather);
                forecasts.add(f);
            }
            day1Lbl.setText(rb.getString(new SimpleDateFormat("EE").format(forecasts.get(1).getDate())));
            day2Lbl.setText(rb.getString(new SimpleDateFormat("EE").format(forecasts.get(2).getDate())));
            day3Lbl.setText(rb.getString(new SimpleDateFormat("EE").format(forecasts.get(3).getDate())));
            w1.setText(forecasts.get(0).getWeather());
            w2.setText(forecasts.get(1).getWeather());
            w3.setText(forecasts.get(2).getWeather());
            w4.setText(forecasts.get(3).getWeather());
            if(celsiusBtn.isSelected()) {
                temp1.setText(forecasts.get(0).getLowTemp() + " - " + forecasts.get(0).getHighTemp() + " °C");
                temp2.setText(forecasts.get(1).getLowTemp() + " - " + forecasts.get(1).getHighTemp() + " °C");
                temp3.setText(forecasts.get(2).getLowTemp() + " - " + forecasts.get(2).getHighTemp() + " °C");
                temp4.setText(forecasts.get(3).getLowTemp() + " - " + forecasts.get(3).getHighTemp() + " °C");
            } else {
                temp1.setText(forecasts.get(0).getLowTempF() + " - " + forecasts.get(0).getHighTempF() + " °F");
                temp2.setText(forecasts.get(1).getLowTempF() + " - " + forecasts.get(1).getHighTempF() + " °F");
                temp3.setText(forecasts.get(2).getLowTempF() + " - " + forecasts.get(2).getHighTempF() + " °F");
                temp4.setText(forecasts.get(3).getLowTempF() + " - " + forecasts.get(3).getHighTempF() + " °F");
            }
            humid1.setText(forecasts.get(0).getLowHumid() + " - " + forecasts.get(0).getHighHumid() + " %");
            humid2.setText(forecasts.get(1).getLowHumid() + " - " + forecasts.get(1).getHighHumid() + " %");
            humid3.setText(forecasts.get(2).getLowHumid() + " - " + forecasts.get(2).getHighHumid() + " %");
            humid4.setText(forecasts.get(3).getLowHumid() + " - " + forecasts.get(3).getHighHumid() + " %");
            if(windUnitBox.getValue().equals("m/s")){
                wind1.setText(forecasts.get(0).getLowWindSpeedms() + " - " + forecasts.get(0).getHighWindSpeedms() + " m/s");
                wind2.setText(forecasts.get(1).getLowWindSpeedms() + " - " + forecasts.get(1).getHighWindSpeedms() + " m/s");
                wind3.setText(forecasts.get(2).getLowWindSpeedms() + " - " + forecasts.get(2).getHighWindSpeedms() + " m/s");
                wind4.setText(forecasts.get(3).getLowWindSpeedms() + " - " + forecasts.get(3).getHighWindSpeedms() + " m/s");
            }
            else if(windUnitBox.getValue().equals("km/h")){
                wind1.setText(forecasts.get(0).getLowWindSpeedkmh() + " - " + forecasts.get(0).getHighWindSpeedkmh() + " km/h");
                wind2.setText(forecasts.get(1).getLowWindSpeedkmh() + " - " + forecasts.get(1).getHighWindSpeedkmh() + " km/h");
                wind3.setText(forecasts.get(2).getLowWindSpeedkmh() + " - " + forecasts.get(2).getHighWindSpeedkmh() + " km/h");
                wind4.setText(forecasts.get(3).getLowWindSpeedkmh() + " - " + forecasts.get(3).getHighWindSpeedkmh() + " km/h");
            }
            else if(windUnitBox.getValue().equals("ft/s")){
                wind1.setText(forecasts.get(0).getLowWindSpeedfts() + " - " + forecasts.get(0).getHighWindSpeedfts() + " ft/s");
                wind2.setText(forecasts.get(1).getLowWindSpeedfts() + " - " + forecasts.get(1).getHighWindSpeedfts() + " ft/s");
                wind3.setText(forecasts.get(2).getLowWindSpeedfts() + " - " + forecasts.get(2).getHighWindSpeedfts() + " ft/s");
                wind4.setText(forecasts.get(3).getLowWindSpeedfts() + " - " + forecasts.get(3).getHighWindSpeedfts() + " ft/s");
            }
            else{
                wind1.setText(forecasts.get(0).getLowWindSpeedmih() + " - " + forecasts.get(0).getHighWindSpeedmih() + " mi/h");
                wind2.setText(forecasts.get(1).getLowWindSpeedmih() + " - " + forecasts.get(1).getHighWindSpeedmih() + " mi/h");
                wind3.setText(forecasts.get(2).getLowWindSpeedmih() + " - " + forecasts.get(2).getHighWindSpeedmih() + " mi/h");
                wind4.setText(forecasts.get(3).getLowWindSpeedmih() + " - " + forecasts.get(3).getHighWindSpeedmih() + " mi/h");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        } catch (java.text.ParseException e) {
            System.out.println(e.getMessage());
        }
    }
}





