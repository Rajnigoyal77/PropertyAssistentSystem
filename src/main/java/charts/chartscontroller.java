package charts;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import jdbcc.DatabaseConnection;
import util.SceneSwitcher;

public class chartscontroller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private PieChart piechart;

    Connection con;

    public void doConnect()
    {
        con = DatabaseConnection.doConnectToDB();

        if(con==null)
            System.out.println("Connection Error");
        else
            System.out.println("Database Connected");
    }




    @FXML
   public void doBack(ActionEvent event) {
        SceneSwitcher.switchScene(event, "/dashboardv/dashBoardV.fxml");
    }
    @FXML
    void initialize()
    {
        doConnect();

        ObservableList<PieChart.Data> list =
                FXCollections.observableArrayList();

        try
        {
            PreparedStatement pst = con.prepareStatement(
                    "select status,count(*) total from deals group by status");

            ResultSet rs = pst.executeQuery();

            while(rs.next())
            {
                String statusName="";

                if(rs.getInt("status")==1)
                    statusName="Pending";
                else if(rs.getInt("status")==2)
                    statusName="Fulfilled";
                else if(rs.getInt("status")==3)
                    statusName="Cancelled";

                list.add(new PieChart.Data(statusName,
                        rs.getInt("total")));
            }

            piechart.setData(list);
            piechart.setTitle("Deal Status Report");

        }
        catch(Exception exp)
        {
            exp.printStackTrace();
        }
    }
}