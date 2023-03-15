module CSProject {
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;
    opens sample.Model;
    opens sample.View;
    opens sample.Control;
}