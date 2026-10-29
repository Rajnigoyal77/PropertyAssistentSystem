package com.example.javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
       // FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
         //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/jsgjectv/JsgView.fxml"));
     //  FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/customerv/CustometV.fxml"));
    //  FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/listpropertyv/listPropertyView.fxml"));


//        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/browsecustomerv/browseCustomerV.fxml"));
      //  FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/dealsview/DealsView.fxml"));
      // FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/propertyfinder/propertyFinderV.fxml"));
    //    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/browsedealv/

//        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/chartsV/chartsV.fxml"));
   //     FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/barchartv/barChartV.fxml"));
//    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/dashboardv/dashBoardV.fxml"));
       FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/loginv/LoginView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }
}
