package dealsviewcont;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import jdbcc.DatabaseConnection;
import util.SceneSwitcher;

public class dealsController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button doFetch;

    @FXML
    private Button doSave;

    @FXML
    private TextField txtAdv;

    @FXML
    private TextField txtBal;

    @FXML
    private ToggleGroup rad;



    @FXML
    private TextField txtBuyer;

    @FXML
    private TextField txtCom;

    @FXML
    private TextField txtCont;

    @FXML
    private DatePicker txtDate1;

    @FXML
    private DatePicker txtDate2;

    @FXML
    private TextField txtDown;

    @FXML
    private TextField txtFinalA;

    @FXML
    private TextField txtOwnerC;

    @FXML
    private TextField txtProp;

    @FXML
    private RadioButton txtRad1;

    @FXML
    private RadioButton txtRad2;

    @FXML
    private RadioButton txtRad3;



    @FXML
    private TextField txtSeller;

    @FXML
    void doModify(ActionEvent event)
    {
        try
        {
            int status = 0;

            if(txtRad1.isSelected())
                status = 1;

            else if(txtRad2.isSelected())
                status = 2;

            else if(txtRad3.isSelected())
                status = 3;


            if(status == 3)
            {
                PreparedStatement pst = con.prepareStatement(
                        "delete from deals where id=?");

                pst.setInt(1, Integer.parseInt(txtProp.getText()));

                pst.executeUpdate();

                System.out.println("Deal record removed successfully");
            }


            else if(status == 2)
            {
                PreparedStatement pst = con.prepareStatement(
                        "DELETE FROM properties WHERE mobile=?");

                pst.setString(1, txtCont.getText());

                int rows = pst.executeUpdate();

                if(rows > 0)
                    System.out.println("Property Deleted Successfully");
                else
                    System.out.println("Property Not Found");


            }

            else
            {
                PreparedStatement pst = con.prepareStatement(
                        "update deals set status=? where id=?");


                pst.setInt(1, status);

                pst.setInt(2, Integer.parseInt(txtProp.getText()));


                pst.executeUpdate();


                System.out.println("Status Updated successfully");
            }

        }
        catch(Exception exp)
        {
            exp.printStackTrace();
        }
    }
    @FXML
    void doFetch(ActionEvent event)
    {
        try
        {
            // Step 1 : Search in deals table
            PreparedStatement pst = con.prepareStatement(
                    "SELECT * FROM deals WHERE id=?");

            pst.setInt(1, Integer.parseInt(txtProp.getText()));

            ResultSet rs = pst.executeQuery();

            if(rs.next())
            {
                txtSeller.setText(rs.getString("seller"));
                txtOwnerC.setText(rs.getString("sellercontact"));
                txtBuyer.setText(rs.getString("buyer"));
                txtCont.setText(rs.getString("buyercontact"));
                txtFinalA.setText(String.valueOf(rs.getInt("finalamt")));
                txtDown.setText(String.valueOf(rs.getInt("downpmt")));
                txtDate1.setValue(rs.getDate("dtofdeal").toLocalDate());
                txtDate2.setValue(rs.getDate("dtofreg").toLocalDate());
                txtCom.setText(rs.getString("totalcom"));
                txtAdv.setText(String.valueOf(rs.getInt("adv")));
                txtBal.setText(String.valueOf(rs.getInt("balance")));

                int status = rs.getInt("status");

                if(status==1)
                    txtRad1.setSelected(true);
                else if(status==2)
                    txtRad2.setSelected(true);
                else if(status==3)
                    txtRad3.setSelected(true);
            }
            else
            {
                // Step 2 : If deal not found, search seller from Property + Customer
                PreparedStatement pst2 = con.prepareStatement(
                        "SELECT c.name, c.mobile " +
                                "FROM properties p " +
                                "INNER JOIN customers c ON p.mobile=c.mobile " +
                                "WHERE p.rid=?");

                pst2.setInt(1, Integer.parseInt(txtProp.getText()));

                ResultSet rs2 = pst2.executeQuery();

                if(rs2.next())
                {
                    txtSeller.setText(rs2.getString("name"));
                    txtOwnerC.setText(rs2.getString("mobile"));

                    System.out.println("Seller details loaded.");
                }
                else
                {
                    System.out.println("Property Not Found");
                }
            }
        }
        catch(Exception exp)
        {
            exp.printStackTrace();
        }
    }

    @FXML
    void doSave(ActionEvent event) {

        try {

            PreparedStatement pst = con.prepareStatement(
                    "insert into deals values(?,?,?,?,?,?,?,?,?,?,?,?,?)");

            pst.setInt(1, Integer.parseInt(txtProp.getText()));
            pst.setString(2, txtSeller.getText());
            pst.setString(3, txtOwnerC.getText());
            pst.setString(4, txtBuyer.getText());
            pst.setString(5, txtCont.getText());
            pst.setInt(6, Integer.parseInt(txtFinalA.getText()));
            pst.setInt(7, Integer.parseInt(txtDown.getText()));

            LocalDate dealDate = txtDate1.getValue();
            Date dt1 = Date.valueOf(dealDate);
            pst.setDate(8, dt1);

            LocalDate regDate = txtDate2.getValue();
            Date dt2 = Date.valueOf(regDate);
            pst.setDate(9, dt2);

            pst.setString(10, txtCom.getText());
            pst.setInt(11, Integer.parseInt(txtAdv.getText()));
            pst.setInt(12, Integer.parseInt(txtBal.getText()));

            if (txtRad1.isSelected())
                pst.setInt(13, 1);
            else if (txtRad2.isSelected())
                pst.setInt(13, 2);
            else if (txtRad3.isSelected())
                pst.setInt(13, 3);
            else
                pst.setInt(13, 0);

            pst.executeUpdate();

            System.out.println("Record Saved Successfully");

        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }


    @FXML
    void doClear(ActionEvent event)
    {
        txtProp.clear();
        txtSeller.clear();
        txtOwnerC.clear();
        txtBuyer.clear();
        txtCont.clear();
        txtFinalA.clear();
        txtDown.clear();
        txtCom.clear();
        txtAdv.clear();
        txtBal.clear();

        txtDate1.setValue(null);
        txtDate2.setValue(null);
        txtRad1.setSelected(false);
        txtRad2.setSelected(false);
        txtRad3.setSelected(false);


    }

    @FXML
    public  void doBack(ActionEvent event) {
        SceneSwitcher.switchScene(event, "/dashboardv/dashBoardV.fxml");
    }


    @FXML
    void initialize() {
     doConnect();

    }

    Connection con;
    public void doConnect()
    {
        con= DatabaseConnection.doConnectToDB();
        if(con==null)
            System.out.println("Connection Error");
        else
            System.out.println("Alll is Welll");
    }

}
