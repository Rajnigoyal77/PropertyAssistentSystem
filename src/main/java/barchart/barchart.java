package barchart;


import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import util.SceneSwitcher;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import jdbcc.DatabaseConnection;

public class barchart {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private BarChart<String, Number> batchart;

    Connection con;

    public void doConnect() {
        con = DatabaseConnection.doConnectToDB();

        if (con == null)
            System.out.println("Connection Error");
        else
            System.out.println("Connected Successfully");
    }



    @FXML
    void doBack(ActionEvent event) {
        SceneSwitcher.switchScene(event, "/dashboardv/dashBoardV.fxml");
    }

    @FXML
    void initialize() {

        doConnect();

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Properties");

        try {

            PreparedStatement pst = con.prepareStatement(
                    "SELECT city, COUNT(*) total FROM properties GROUP BY city");

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                series.getData().add(
                        new XYChart.Data<>(
                                rs.getString("city"),
                                rs.getInt("total")
                        )
                );
            }

            batchart.getData().add(series);

        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }
}