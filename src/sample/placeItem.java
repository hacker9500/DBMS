package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TextField;

/**
 * Created by hacker9500 on 29/04/16.
 */
public class placeItem{
    public String getItemId() {
        return itemId.get();
    }


    public void setItemId(String itemId) {
        this.itemId.set(itemId);
    }

    public String getName() {
        return name.get();
    }


    public void setName(String name) {
        this.name.set(name);
    }

    public String getPrice() {
        return price.get();
    }

    public void setPrice(String price) {
        this.price.set(price);
    }

    public String getSize() {
        return size.get();
    }

    public void setSize(String size) {
        this.size.set(size);
    }

    public SimpleStringProperty itemId;
    public SimpleStringProperty name;
    public SimpleStringProperty price;
    public SimpleStringProperty size;
    public SimpleStringProperty qty;

    public String getQty() {
        return qty.get();
    }

    public SimpleStringProperty qtyProperty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty.set(qty);
    }

    placeItem(String name, int id, float price, String size){

        this.name = new SimpleStringProperty();
        this.itemId = new SimpleStringProperty();
        this.price = new SimpleStringProperty();
        this.size = new SimpleStringProperty();
        this.qty = new SimpleStringProperty();

        this.itemId.setValue(String.valueOf(id));
        this.name.set(name);
        this.price.set(String.valueOf(price));
        this.size.set(size);
        this.setQty("0");
    }
}
