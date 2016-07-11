package sample;

import com.sun.deploy.panel.TextFieldProperty;
import javafx.beans.Observable;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

/**
 * Created by hacker9500 on 29/04/16.
 */
public class placeOrder {
    @FXML
    TableView<placeItem> menu;

    TableColumn<placeItem, String> itemName;
    TableColumn<placeItem, String> itemId;
    TableColumn<placeItem, String> itemPrice;
    TableColumn<placeItem, String> size;
    TableColumn<placeItem, String> qt;

    @FXML
    Button place;

    ObservableList<placeItem> data;

    public void initialize(){
        itemName = new TableColumn<>("Item Name");
        itemId = new TableColumn<>("ItemId");
        itemPrice = new TableColumn<>("@price");
        size = new TableColumn<>("s/m/l - size");
        qt = new TableColumn<>("quantity");

        data = FXCollections.observableArrayList();

        itemName.setCellValueFactory(new PropertyValueFactory<>("name"));
        itemId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        itemPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        size.setCellValueFactory(new PropertyValueFactory<>("size"));
        qt.setCellValueFactory(new PropertyValueFactory<>("qty"));
        //qt.setEditable(true);


        menu.getColumns().addAll(itemId,itemName,itemPrice,size,qt);

        try {
            this.addData();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        menu.setItems(data);

        menu.setOnMouseClicked(e -> {
            //System.out.println("menu clicked");
            ObservableList<placeItem> items = menu.getSelectionModel().getSelectedItems();
            Stage dia = new Stage();
            FXMLLoader fx2 = new FXMLLoader(getClass().getResource("qty.fxml"));
            dia.setTitle("enter Quantity");
            try {
                dia.setScene(new Scene(fx2.load()));
                qty init = fx2.getController();
                init.initialize(items.get(0).getName(), items.get(0).getPrice(), items.get(0).getSize());
                dia.initModality(Modality.APPLICATION_MODAL);
                dia.show();
                init.place.setOnAction(e1 -> {
                    items.get(0).setQty(init.qnty.getText());
                    if(init.qnty.getText().isEmpty())
                        init.qnty.setText("0");
                    dia.close();
                });

            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });


    }

    public void addData() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection connect = DriverManager.getConnection(
                "jdbc:mysql://192.168.2.0:3306/DBMS", "hacker9500", "Heha@123");
        Statement statement = connect.createStatement();
        String sql = "select * from Menu Order By itemId";
        ResultSet result =  statement.executeQuery(sql);
        while(result.next()){
            //System.out.println(result.getString(1));
            data.add(new placeItem(result.getString(1), result.getInt(4), result.getFloat(2), result.getString(3)));
        }
    }


}
