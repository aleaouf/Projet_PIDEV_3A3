package Controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {

        try {
            StageManager stageManager = StageManager.getInstance();
            stageManager.setPrimaryStage(primaryStage);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherClient.fxml"));
            Parent root = loader.load();
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {


        launch(args);
    }


    }

