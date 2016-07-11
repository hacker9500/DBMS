package sample;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static Scene root, root1;

    @Override
    public void start(Stage primaryStage) throws Exception{
        // at the start start with the login page


        final FXMLLoader[] fx = {new FXMLLoader(getClass().getResource("login.fxml")), new FXMLLoader(getClass().getResource("signup.fxml"))};
        root = new Scene(fx[0].load());
        root1 = new Scene(fx[1].load());
        primaryStage.setTitle("Login... !!!");
        primaryStage.setScene(root);
        primaryStage.show();

        Login login = fx[0].getController();
        login.initialize(primaryStage);
        login.signup.setOnAction((e) -> {
            primaryStage.setTitle("Signup new Account");
            primaryStage.setScene(root1);
        });

        Signup sign = fx[1].getController();
        sign.initialize(primaryStage);
        sign.back_to_login.setOnAction(e -> {
            primaryStage.setTitle("Login... !!!");
            primaryStage.setScene(root);
        });


//        FXMLLoader fx = new FXMLLoader(getClass().getResource("dashboard.fxml"));
//        primaryStage.setTitle("*** Dashboard *** "+"Shubham Goswami");
//        primaryStage.setScene(new Scene(fx.load()));
//        primaryStage.show();
//        dashboard dash = fx.getController();
//        dash.initialize(primaryStage, "shubham14100", "heha@123");
    }


    public static void main(String[] args) {
        launch(args);
    }
}
