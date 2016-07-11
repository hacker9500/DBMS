package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * Created by hacker9500 on 29/04/16.
 */
public class qty {
    @FXML
    Button place;
    @FXML
    Label name, size,price;
    @FXML
    TextField qnty;

    static String quantity;

    public void initialize(String ...params){
        name.setText(params[0]);
        price.setText(params[1]);
        size.setText(params[2]);
    }
}
