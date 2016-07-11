package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;

/**
 * Created by hacker9500 on 29/04/16.
 */
public class OrderA {
    public SimpleStringProperty orderId;
    public SimpleStringProperty stage;

    public String getLocation() {
        return location.get();
    }

    public SimpleStringProperty locationProperty() {
        return location;
    }

    public void setLocation(String location) {
        this.location.set(location);
    }

    public SimpleStringProperty location;

    public String getUserId() {
        return userId.get();
    }

    public SimpleStringProperty userIdProperty() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId.set(userId);
    }

    public SimpleStringProperty userId;

    OrderA(int id, int state, String user, String location){
        orderId = new SimpleStringProperty();
        stage = new SimpleStringProperty();
        userId = new SimpleStringProperty();
        this.location = new SimpleStringProperty();
        setLocation(location);
        setUserId(user);
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
