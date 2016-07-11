package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;

/**
 * Created by hacker9500 on 29/04/16.
 */
public class Status_all {
    String id, state, userid, userlocation;

    ObservableList<placeItem> data;

    @FXML
    Label order_id, status, total, userId, userLocation;
    @FXML
    TableView<placeItem> orders;

    TableColumn<placeItem,String> name;
    TableColumn<placeItem,String> price;
    TableColumn<placeItem,String> size;
    TableColumn<placeItem,String> qty;


    public void initialize(String orderId, String State, String userId, String userLocation){
        this.id = orderId;
        this.userid = userId;
        this.userlocation = userLocation;

        this.userId.setText(userid);
        this.userLocation.setText(userlocation);

        order_id.setText(id);
        this.state = State;
//        if(State.equals("0")){
//            this.state = "received";
//        }
//        else if(State.equals("1")){
//            this.state = "preparing";
//        }
//        else
//            this.state = "Done";

        status.setText(this.state);

        data = FXCollections.observableArrayList();

        name = new TableColumn<>("Item Name");
        price = new TableColumn<>("@price");
        size = new TableColumn<>("size");
        qty = new TableColumn<>("Quantity");

        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        size.setCellValueFactory(new PropertyValueFactory<>("size"));
        qty.setCellValueFactory(new PropertyValueFactory<>("qty"));

        orders.getColumns().addAll(name, price, size, qty);
        orders.setItems(data);


        try {
            int total = 0;
            Class.forName("com.mysql.jdbc.Driver");
            Connection connect = DriverManager.getConnection(
                    "jdbc:mysql://192.168.2.0:3306/DBMS", "hacker9500", "Heha@123");
            Statement statement = connect.createStatement();
            String query = "select * from `OrderItems` where orderID="+this.id;
            ResultSet result = statement.executeQuery(query);
            Statement statement2 = connect.createStatement();
            while(result.next()){
                int itemId = result.getInt(2);
                String query2 = "select * from `Menu` where itemId="+String.valueOf(itemId);
                ResultSet rs = statement2.executeQuery(query2);
                rs.next();
                placeItem pi = new placeItem(rs.getString(1),itemId,rs.getFloat(2), rs.getString(3));
                pi.setQty(String.valueOf(result.getInt(3)));
                data.add(pi);
                total += result.getFloat(4);
                rs.close();
            }
            result.close();
            statement.close();
            statement2.close();
            connect.close();
            this.total.setText(String.valueOf(total)+"/-");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
