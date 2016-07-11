package sample;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainAdmin extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        final FXMLLoader[] fx = {new FXMLLoader(getClass().getResource("admin_pending.fxml"))};
        primaryStage.setTitle("Admin DashBoard");
        primaryStage.setScene(new Scene(fx[0].load()));
        primaryStage.show();
        Admin_Pending  pending = fx[0].getController();
        pending.initialize(primaryStage);

    }
    public static void main(String[] args) {
        launch(args);
    }
}
