package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

/**
 * Created by hacker9500 on 28/04/16.
 */
public class Login {
    @FXML
    TextField username;
    @FXML
    PasswordField password;
    @FXML
    public Button signin, signup;

    public void initialize(Stage primarystage){
        System.out.println("Login screen");

        signin.setOnAction(e -> {
            String usr = username.getText();
            String pass = password.getText();
            if(usr.isEmpty() || pass.isEmpty()) {
                username.clear();
                password.clear();
                return;
            }
            try {
                String usr1 = "";
                for(char c: usr.toCharArray())
                    if(c != ' ')
                        usr1 += c;
                String pass1 = "";
                for(char c: pass.toCharArray())
                    if(c != ' ')
                        pass1 += c;
                Class.forName("com.mysql.jdbc.Driver");
                Connection connect=DriverManager.getConnection(
                        "jdbc:mysql://192.168.2.0:3306/DBMS","hacker9500","Heha@123");
                Statement statement = connect.createStatement();
                String sql = "select * from User where login=\""+usr1+"\" AND password=\""+pass1+"\"";
                ResultSet result = statement.executeQuery(sql);
                int count = 0;
                String name = "";
                while(result.next()){
                    name = result.getString(2);
                    count++;
                }
                if(count > 0){
                    System.out.println("ok");
                    if(name == null) name = "";
                    primarystage.setTitle("** DashBoard ** -- "+name);
                    FXMLLoader fx = new FXMLLoader(getClass().getResource("dashboard.fxml"));
                    Scene dash = new Scene(fx.load());
                    primarystage.setScene(dash);
                    dashboard board = fx.getController();
                    board.initialize(primarystage, usr1, pass1);
                }
                else{
                    username.setText("not valid");
                    password.clear();
                }
                result.close();
                statement.close();
                connect.close();
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            } catch (SQLException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        });
    }
}
