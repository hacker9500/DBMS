package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

/**
 * Created by hacker9500 on 29/04/16.
 */
public class Admin_All {
    @FXML
    Button back, refresh;
    @FXML
    TableView<OrderA> all;

    TableColumn<OrderA,String> orderId;
    TableColumn<OrderA,String> userId;
    TableColumn<OrderA,String> stage;

    ObservableList<OrderA> data;

    public void initialize(Stage primaryStage){
        orderId = new TableColumn<>("Order ID");
        userId = new TableColumn<>("User ID");
        stage = new TableColumn<>("Stage");

        orderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        stage.setCellValueFactory(new PropertyValueFactory<>("stage"));
        userId.setCellValueFactory(new PropertyValueFactory<>("userId"));

        data = FXCollections.observableArrayList();

        this.addData();
        refresh.setOnAction(e -> this.addData());

        all.getColumns().addAll(orderId, userId, stage);
        all.setItems(data);
        all.setOnMouseClicked(e -> {
            ObservableList<OrderA> selected = all.getSelectionModel().getSelectedItems();
            Stage status = new Stage();
            FXMLLoader fx3 = new FXMLLoader(getClass().getResource("status_all.fxml"));
            status.setTitle("about all orders");
            try {
                status.setScene(new Scene(fx3.load()));
                Status_all st = fx3.getController();
                status.initModality(Modality.APPLICATION_MODAL);
                st.initialize(selected.get(0).getOrderId(), selected.get(0).getStage(),selected.get(0).getUserId(),selected.get(0).getLocation());
                status.show();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        back.setOnAction(e -> {
            FXMLLoader fx4 = new FXMLLoader(getClass().getResource("admin_pending.fxml"));
            try {
                primaryStage.setScene(new Scene(fx4.load()));
                Admin_Pending pend = fx4.getController();
                pend.initialize(primaryStage);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
    }

    public void addData(){
        data.clear();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connect = DriverManager.getConnection(
                    "jdbc:mysql://192.168.2.0:3306/DBMS", "hacker9500", "Heha@123");
            Statement statement = connect.createStatement();
            ResultSet result = statement.executeQuery("select * from `Orders` Order By OrderID Desc");
            while(result.next()){
                Statement statement2 = connect.createStatement();
                ResultSet rs = statement2.executeQuery("select * from `OrderStage` WHERE orderId="+String.valueOf(result.getInt(1))+"");
                Statement statement3 = connect.createStatement();
                ResultSet rs2 = statement3.executeQuery("select * from `User` Where login=\""+result.getString(2)+"\"");
                rs.next();
                rs2.next();
                data.add(new OrderA(rs.getInt(1), rs.getInt(2), result.getString(2), rs2.getString(4)));
                rs.close();
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
