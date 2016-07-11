package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;

/**
 * Created by hacker9500 on 29/04/16.
 */
public class Order {
    public SimpleStringProperty orderId;
    public SimpleStringProperty stage;

    Order(int id, int state){
        orderId = new SimpleStringProperty();
        stage = new SimpleStringProperty();

        setOrderId(String.valueOf(id));
        if(state == 0)
            setStage("received");
        else if(state == 1)
            setStage("preparing");
        else
            setStage("Done");
    }

    public String getOrderId() {
        return orderId.get();
    }

    public SimpleStringProperty orderIdProperty() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId.set(orderId);
    }

    public String getStage() {
        return stage.get();
    }

    public SimpleStringProperty stageProperty() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage.set(stage);
    }
}
