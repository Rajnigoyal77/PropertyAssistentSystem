package jsgject;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class JsgController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnhello;

    @FXML
    void Hello(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert btnhello != null : "fx:id=\"btnhello\" was not injected: check your FXML file 'JsgView.fxml'.";

    }

}
