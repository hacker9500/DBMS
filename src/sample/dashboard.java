package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

/**
 * Created by hacker9500 on 28/04/16.
 */
public class dashboard {
    @FXML
    Button signout, place_order, refresh;
    @FXML
    TextField search;
    @FXML
    TableView<Order> orders;

    TableColumn<Order, String> orderId;
    TableColumn<Order, String> stage;

    String username, password;

    ObservableList<Order> data;

    public void initialize(Stage primaryStage, String usr1, String pass){
        this.username = usr1;
        this.password = pass;

        signout.setOnAction(e -> {
            primaryStage.setTitle("Login... !!!");
            primaryStage.setScene(Main.root);
        });

        orderId = new TableColumn<>("Order-ID");
        stage = new TableColumn<>("Stage( rec - prep - del )");
        orderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        stage.setCellValueFactory(new PropertyValueFactory<>("stage"));

        data = FXCollections.observableArrayList();

        orders.getColumns().addAll(orderId, stage);
        orders.setItems(data);

        place_order.setOnAction(e -> {
            FXMLLoader fx1 = new FXMLLoader(getClass().getResource("place_order.fxml"));
            Stage place = new Stage();
            try {
                place.setScene(new Scene(fx1.load()));
                place.setTitle("place new Order !!");
                place.initModality(Modality.APPLICATION_MODAL);
                place.show();
                placeOrder ps = fx1.getController();
                //ps.initialize();
                ObservableList<placeItem> list = ps.data;
                final int[] number = {0};
                ps.place.setOnAction(e1 -> {
                    try {
                        int count = 0;
                        for(placeItem pi: list)
                            if(Integer.valueOf(pi.getQty()) > 0)
                                count++;
                        if(count == 0)
                            return;

                        Class.forName("com.mysql.jdbc.Driver");
                        Connection connect = DriverManager.getConnection(
                                "jdbc:mysql://192.168.2.0:3306/DBMS", "hacker9500", "Heha@123");
                        Statement statement = connect.createStatement();
                        String sql0 = "select * From Orders Order By OrderID DESC ";
                        ResultSet rs = statement.executeQuery(sql0);
                        while(rs.next()){
                            number[0] = rs.getInt(1);
                            //System.out.println(number[0] + 1);
                            break;
                        }
                        String sql1 = "INSERT INTO `Orders` ( `OrderID`, `userID`) " +
                                "VALUES ( "+String.valueOf(number[0] +1)+", '"+username+"' )";

                        statement.execute(sql1);
                        String sql2 = "Insert Into `OrderStage` (`orderId` ,`stage`) Values ("+String.valueOf(number[0]+1)+", 0);";
                        statement.execute(sql2);
                        for(placeItem pi : list) {
                            if(Integer.valueOf(pi.getQty()) <= 0)
                                continue;
                            String sql3 = "INSERT INTO `OrderItems` ( `itemID`, `orderID`, `qty`, `total`) " +
                                    "VALUES ("+pi.getItemId()+ " ,"+ String.valueOf(number[0]+1) +" ,"+pi.getQty()+" , "+String.valueOf(Float.valueOf(pi.getPrice())*Integer.valueOf(pi.getQty()))+" );";
                            statement.execute(sql3);
                            place.close();
                        }
                        rs.close();
                        statement.close();
                        connect.close();
                        this.addData();
                    } catch (SQLException e2) {
                        e2.printStackTrace();
                    } catch (ClassNotFoundException e2) {
                        e2.printStackTrace();
                    }
                });
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        this.addData();
        refresh.setOnAction(e -> this.addData());

        orders.setOnMouseClicked(e -> {
            ObservableList<Order> selected = orders.getSelectionModel().getSelectedItems();
            Stage status = new Stage();
            FXMLLoader fx3 = new FXMLLoader(getClass().getResource("status.fxml"));
            status.setTitle("orderId=> "+ selected.get(0).getOrderId());
            try {
                status.setScene(new Scene(fx3.load()));
                status.initModality(Modality.APPLICATION_MODAL);
                status.show();
                Status st = fx3.getController();
                st.initialize(selected.get(0).getOrderId(), selected.get(0).getStage());
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
            Statement statement2 = connect.createStatement();
            ResultSet result = statement.executeQuery("select * from `Orders` Where userId=\""+this.username+"\" Order By OrderID Desc");
            while(result.next()){
                ResultSet rs = statement2.executeQuery("select * from `OrderStage` WHERE orderId="+String.valueOf(result.getInt(1))+"");
                rs.next();
                data.add(new Order(rs.getInt(1), rs.getInt(2)));
                rs.close();
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
