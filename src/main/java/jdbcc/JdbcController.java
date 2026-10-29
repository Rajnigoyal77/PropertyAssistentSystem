package jdbcc;

import javafx.fxml.FXML;

import java.io.File;
import java.io.FileInputStream;
import javafx.scene.control.Label;

import java.io.InputStream;
import java.sql.Connection;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;

public class JdbcController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> ComRoll;

    @FXML
    private Button btnClear;
    @FXML
    private Button btnShowAll;


    @FXML
    private Button btnDlt;

    @FXML
    private Button btnUpd;

    @FXML
    private ImageView myImage;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnSearch;

    @FXML
    private Label ImgPath;




    @FXML
    private DatePicker txtDate;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPer;

    @FXML
    void doClear(ActionEvent event) {

    }

    @FXML
    void doShowImg(ActionEvent event)
    {
        try {
            FileChooser chooser = new FileChooser();

            chooser.setTitle("Select Profile Pic:");

            chooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("All Images", "*.jpg", "*.png"));
            File fileRef = chooser.showOpenDialog(null);
            ImgPath.setText(fileRef.getAbsolutePath());
            myImage.setImage(new Image(new FileInputStream(fileRef)));



        }
        catch (Exception exp)
        {
            exp.printStackTrace();
        }
    }



    @FXML
    void doDelete(ActionEvent event) {

        try {

            PreparedStatement pst = con.prepareStatement(
                    "delete from students where rollno=?");

            pst.setInt(1, Integer.parseInt(ComRoll.getValue()));
            pst.executeUpdate();

            System.out.println("Record Delete successfully");

        } catch (Exception exp) {
            exp.printStackTrace();
        }

    }


    @FXML
    void doShowALL(ActionEvent event) {
        try {

            PreparedStatement pst = con.prepareStatement(
                    "select *from students ");


            ResultSet result = pst.executeQuery();
            while (result.next())
            {
                int Rollno=result.getInt("rollno");
                String Name=result.getString("name");
                int Per=result.getInt("per");
                Date dob=result.getDate("dob");
                System.out.println(Rollno+" "+Name+" "+Per+" "+dob.toString());

            }
        }
        catch (Exception exp) {
            exp.printStackTrace();
        }
    }

    @FXML
    void doSave(ActionEvent event) {
        try {

            PreparedStatement pst = con.prepareStatement(
                    "insert into students values(?,?,?,?");

            pst.setInt(1, Integer.parseInt(ComRoll.getValue()));
            pst.setString(2, txtName.getText());
            pst.setFloat(3, Float.parseFloat(txtPer.getText()));

            LocalDate local = txtDate.getValue();
            java.sql.Date date = java.sql.Date.valueOf(local);
            pst.setDate(4, date);


            File fileImg = new File(ImgPath.getText());
            FileInputStream stream = new FileInputStream(fileImg);

            pst.setBinaryStream(4, stream, (int) fileImg.length());

            pst.executeUpdate();

            System.out.println("Record saved successfully");

        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }

    @FXML
    void doSearch(ActionEvent event) {
        try {

            PreparedStatement pst = con.prepareStatement
                    (
                    "select *from students where rollno=? ");
            pst.setInt(1, Integer.parseInt(ComRoll.getValue()));

            ResultSet result = pst.executeQuery();
            if (result.next())
            {
                int Roll=result.getInt("rollno");
                String Name=result.getString("name");
                int Per=result.getInt("per");
               java.sql. Date dob1=result.getDate("dob");
//                System.out.println(Rollno+" "+Name+" "+Per+" "+dob.toString());

                txtName.setText(Name);
                txtPer.setText(String.valueOf(Per));
                LocalDate ldt=dob1.toLocalDate();
                txtDate.setValue(ldt);


                InputStream strm= result.getBinaryStream("pic");
                myImage.setImage(new Image(strm));

            }
            else
                System.out.println("Invalid record");
        }
        catch (Exception exp) {
            exp.printStackTrace();
        }
    }

    @FXML
    void doUpadte(ActionEvent event)
    {

        try {

            PreparedStatement pst = con.prepareStatement(
                    "update students set name=?, per=?, Dob=? where rollno=?");

            pst.setString(1, txtName.getText());
            pst.setFloat(2, Float.parseFloat(txtPer.getText()));

            LocalDate local = txtDate.getValue();
            java.sql.Date date = java.sql.Date.valueOf(local);
            pst.setDate(3, date);

            pst.setInt(4, Integer.parseInt(ComRoll.getValue()));

            int status = pst.executeUpdate();

            if (status > 0)
                System.out.println("Record Updated Successfully");
            else
                System.out.println("Record Not Found");

        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }

    @FXML
    void initialize() {
        doConnect();
    }

    ////////////////// SQL Connection //////////////////

    Connection con;

    public void doConnect() {
        con = DatabaseConnection.doConnectToDB();

        if (con == null)
            System.out.println("Connection Error");
        else
            System.out.println("All is Well");
    }
}