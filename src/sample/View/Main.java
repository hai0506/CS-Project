package sample.View;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.*;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Main extends Application {
    private AnchorPane splashLayout;
    private ImageView image;
    private ProgressBar bar;
    private Label l;
    private Stage mainStage = new Stage();



    public void init() {
        try {
            splashLayout = FXMLLoader.load(getClass().getResource(("splash-screen.fxml")));
            List<Node> nodes = splashLayout.getChildren();
            image = (ImageView) nodes.get(0);
            bar = (ProgressBar) nodes.get(1);
            l = (Label)((HBox) nodes.get(5)).getChildren().get(0);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
        @Override
        public void start(final Stage initStage) throws Exception {
        final Task<ObservableList<String>> friendTask = new Task<ObservableList<String>>() {
            @Override
            protected ObservableList<String> call() throws InterruptedException {
                ObservableList<String> messages =
                        FXCollections.observableArrayList("Loading data from API","Making the UI","Displaying random messages","Procrastinating","Programming an app that is doomed to fail and not function properly and will result in thousands of bugs that I cannot fix and I will end up doubting myself and have an existential crisis wondering why I am doing this with my life and what decisions have led to this very experience that I am currently suffering from","Animating things");
                for (int i = 0; i < 6; i++) {
                    Thread.sleep(1000);
                    if(i==4) l.setFont(new Font(10));
                    if(i==5) l.setFont(new Font(14));
                    updateProgress(i + 1, messages.size());
                    String msg = messages.get(i);
                    updateMessage(msg);
                }
                Thread.sleep(1000);
                return messages;
            }
        };

        showSplash(
                initStage,
                friendTask,
                () -> showMainStage()
        );
        new Thread(friendTask).start();
    }

    private void showMainStage() {
        try {
            Scanner sc = new Scanner(new FileInputStream("src/sample/Model/default.txt"));
            sc.nextLine();
            sc.nextLine();
            String language = sc.nextLine();
            sc.close();
            ResourceBundle rb;
            if(language.equals("Español")) rb = ResourceBundle.getBundle("sample.Model.MessagesBundle", new Locale("es","ES"));
            else if (language.equals("Tiếng Việt")) rb = ResourceBundle.getBundle("sample.Model.MessagesBundle", new Locale("vi","VN"));
            else rb = ResourceBundle.getBundle("sample.Model.MessagesBundle", new Locale("en","US"));
            Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"),rb);
            mainStage.setTitle("Hai's Atmospheric Indicator");
            mainStage.setScene(new Scene(root));
            mainStage.getIcons().add(new Image("/sample/View/app-icon.jpg"));
            mainStage.setResizable(false);
            mainStage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void showSplash(
            final Stage initStage,
            Task<?> task,
            InitCompletionHandler initCompletionHandler
    ) {
        l.textProperty().bind(task.messageProperty());
        bar.progressProperty().bind(task.progressProperty());
        task.stateProperty().addListener((observableValue, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                bar.progressProperty().unbind();
                bar.setProgress(1);
                initStage.close();
                initCompletionHandler.complete();
            }
        });
        Scene splashScene = new Scene(splashLayout, Color.TRANSPARENT);
        initStage.setScene(splashScene);
        initStage.initStyle(StageStyle.TRANSPARENT);
        initStage.setAlwaysOnTop(true);
        initStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
public interface InitCompletionHandler {
    void complete();
}

}