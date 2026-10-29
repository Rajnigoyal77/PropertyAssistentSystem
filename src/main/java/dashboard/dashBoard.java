package dashboard;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import com.example.javafx.HelloApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;

public class dashBoard {


    public void openWindow(ActionEvent event, String path)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(
                    HelloApplication.class.getResource(path));

            Scene scene = new Scene(loader.load());

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnadd;

    @FXML
    void doAddC(ActionEvent event) {
        openWindow(event,"/customerv/CustometV.fxml");
    }

    @FXML
    void doBCus(ActionEvent event) {
        openWindow(event,"/browsecustomerv/browseCustomerV.fxml");
    }

    @FXML
    void doBDeal(ActionEvent event) {
        openWindow(event,"/browsedealv/browseDealV.fxml");
    }

    @FXML
    void doCity(ActionEvent event) {
        openWindow(event,"/chartsV/chartsV.fxml");
    }

    @FXML
    void doGetD(ActionEvent event) {
        openWindow(event,"/dealsview/DealsView.fxml");
    }

    @FXML
    void doLOg(ActionEvent event) {
        openWindow(event,"/loginv/LoginView.fxml");   // apna login FXML path do
    }

    @FXML
    void doPAnalatics(ActionEvent event) {
        openWindow(event,"/barchartv/barChartV.fxml");
    }

    @FXML
    void doPFind(ActionEvent event) {
        openWindow(event,"/propertyfinder/propertyFinderV.fxml");

    }

    @FXML
    void doaddP(ActionEvent event) {
        openWindow(event,"/listpropertyv/listPropertyView.fxml");
    }

    @FXML
    void initialize() {
        assert btnadd != null : "fx:id=\"btnadd\" was not injected: check your FXML file 'dashBoardV.fxml'.";

    }

}
