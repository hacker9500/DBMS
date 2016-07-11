package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

/**
 * Created by hacker9500 on 28/04/16.
 */
public class Signup {
    @FXML
    TextField name, username, contact, locc;
    @FXML
    PasswordField password, confirmPassword;
    @FXML
    Button done, back_to_login;
    @FXML
    Label err;

    Stage primaryStage;

    public void initialize(Stage primaryStage){
        this.primaryStage = primaryStage;
        done.setOnAction(e -> this.signUp());
        err.setText("SignUp...");
    }

    public void signUp() {
        String nm = name.getText();
        String usr = username.getText();
        String pass = password.getText();
        String mob = contact.getText();
        String confirm = confirmPassword.getText();
        String loc = locc.getText();

        if (nm.isEmpty() || usr.isEmpty() || pass.isEmpty() || mob.isEmpty() || confirm.isEmpty() || loc.isEmpty()) {
            err.setText("All fields are mandatory");
        } else if (!pass.equals(confirm)) {
            err.setText("password and confirm password do not match");
        } else {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection connect = DriverManager.getConnection(
                        "jdbc:mysql://192.168.2.0:3306/DBMS", "hacker9500", "Heha@123");
                Statement statement = connect.createStatement();
                String sql = "select * from User where login=\"" + usr + "\"";
                ResultSet result = statement.executeQuery(sql);
                int count = 0;
                String name = "";
                while (result.next()) {
                    name = result.getString(2);
                    count++;
                }
                if (count > 0) {
                    err.setText("Login id in use please select a different login-ID(username)");
                    username.clear();
                } else {
                    String sql1 = "INSERT INTO `User` ( `contact`, `location`, `login`, `name`, `password`) " +
                            "VALUES ( '"+mob+"', '"+loc+"', '"+usr+"', '"+nm+"', '"+pass+"' );";
                    statement.execute(sql1);
                    FXMLLoader fx = new FXMLLoader(getClass().getResource("dashboard.fxml"));
                    primaryStage.setScene(new Scene(fx.load()));
                    dashboard board = fx.getController();
                    board.initialize(primaryStage, usr, pass);
                }
                result.close();
                statement.close();
                connect.close();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
