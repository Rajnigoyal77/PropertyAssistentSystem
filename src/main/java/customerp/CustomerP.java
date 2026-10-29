package customerp;
import util.SceneSwitcher;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import jdbcc.DatabaseConnection;


public class CustomerP
    {

        @FXML
        private ComboBox<String> ComboType;

        @FXML
        private ResourceBundle resources;

        @FXML
        private URL location;

        @FXML
        private ImageView Prev1;






        @FXML
        private Button Prev1B;

        @FXML
        private ImageView Prev2;

        @FXML
        private Button Prev2B;

        @FXML
        private Button btnSearch;

        @FXML
        private ImageView imgLogo;

        @FXML
        private TextField txtAddr;

        @FXML
        private TextField txtCity;

        @FXML
        private TextField txtMail;

        @FXML
        private TextField txtMob;

        @FXML
        private TextField txtName;

        @FXML
        void doClear(ActionEvent event)
        {
            txtMob.clear();
            txtName.clear();
            txtAddr.clear();
            txtCity.clear();
            txtMail.clear();

            ComboType.getSelectionModel().select(0); // Select

            Prev1.setImage(null);
            Prev2.setImage(null);

            picPath = null;
            aadharPath = null;
        }

        @FXML
        void doDelete(ActionEvent event)
        {
            try {

                PreparedStatement pst = con.prepareStatement(
                        "delete from Customers where mobile=?");

                pst.setString(1, (txtMob.getText()));
                pst.executeUpdate();

                System.out.println("Record Delete successfully");

            } catch (Exception exp) {
                exp.printStackTrace();
            }

        }


        String picPath;
        @FXML
        void doShowImg1(ActionEvent event) {

            try {
                FileChooser chooser = new FileChooser();

                chooser.setTitle("Select Profile Pic:");

                chooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("All Images", "*.jpg", "*.png"));
                File fileRef = chooser.showOpenDialog(null);

                if (fileRef != null)
                {
                    picPath = fileRef.getAbsolutePath();   // ✅ Path save karo
                    Prev1.setImage(new Image(new FileInputStream(fileRef)));
                }



            }
            catch (Exception exp)
            {
                exp.printStackTrace();
            }

        }
        String aadharPath;
        @FXML
        void doShowImg2(ActionEvent event) {
            try {
                FileChooser chooser = new FileChooser();

                chooser.setTitle("Select Profile Pic:");

                chooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("All Images", "*.jpg", "*.png"));
                File fileRef = chooser.showOpenDialog(null);

                if (fileRef != null)
                {
                    aadharPath = fileRef.getAbsolutePath();   // ✅ Path save karo
                    Prev2.setImage(new Image(new FileInputStream(fileRef)));
                }



            }
            catch (Exception exp)
            {
                exp.printStackTrace();
            }
        }

        @FXML
        void doSave(ActionEvent event)

        {
            try
            {
                PreparedStatement pst= con.prepareStatement("insert into Customers values(?,?,?,?,?,?,?,?,current_date())");
                pst.setString(1, txtMob.getText());
                pst.setString(2, txtName.getText());
                pst.setString(3,txtAddr.getText());
                pst.setString(4,txtCity.getText());
                pst.setString(5,txtMail.getText());
                pst.setString(6,ComboType.getSelectionModel().getSelectedItem());

                File fileImg = new File(picPath);
                FileInputStream stream = new FileInputStream(fileImg);
                pst.setBinaryStream(7, stream, (int) fileImg.length());

                File fileImg2 = new File(aadharPath);
                FileInputStream stream2 = new FileInputStream(fileImg2);
                pst.setBinaryStream(8, stream2, (int) fileImg2.length());



//                System.out.println("Pic Path = " + picPath);
//                System.out.println("Aadhar Path = " + aadharPath);
//                System.out.println(fileImg.exists());
//                System.out.println(fileImg.length());

                pst.executeUpdate();

                System.out.println("Customers record saved successfully");

            }


            catch(Exception exp)
            {
                exp.printStackTrace();

        }








        }


        @FXML
        void doSearch(ActionEvent event) {
            try {

                PreparedStatement pst = con.prepareStatement(
                        "SELECT * FROM Customers WHERE mobile=?");

                pst.setString(1, txtMob.getText());

                ResultSet rs = pst.executeQuery();

                if (rs.next()) {

                    txtName.setText(rs.getString("name"));
                    txtAddr.setText(rs.getString("adresss"));
                    txtCity.setText(rs.getString("city"));
                    txtMail.setText(rs.getString("email"));
                    ComboType.setValue(rs.getString("ctype"));

                    // Profile Image
                    InputStream stream1 = rs.getBinaryStream("pic");

                        Prev1.setImage(new Image(stream1));




                    // Aadhaar Image
                    InputStream strm2 = rs.getBinaryStream("acard");

                        Prev2.setImage(new Image(strm2));

                } else {
                    System.out.println("Record Not Found");
                }

            } catch (Exception exp) {
                exp.printStackTrace();
            }
        }


        @FXML
        void doUpdate(ActionEvent event)
        {

            try {
                PreparedStatement pst = con.prepareStatement("update Customers set mobile=?, name=?, adresss=? ,city=?,email=?,ctype=?,pic=?,acard=?,dae=CURRENT_DATE() where mobile=?");



                pst.setString(1, txtMob.getText());
                pst.setString(2, txtName.getText());
                pst.setString(3,txtAddr.getText());
                pst.setString(4,txtCity.getText());
                pst.setString(5,txtMail.getText());
                pst.setString(6,ComboType.getSelectionModel().getSelectedItem());

                if (picPath == null || picPath.isEmpty()) {
                    System.out.println("Please select a picture first");
                    return;
                }

                File fileImg = new File(picPath);
                FileInputStream stream = new FileInputStream(fileImg);
                pst.setBinaryStream(7, stream, (int) fileImg.length());

                File fileImg2 = new File(aadharPath);
                FileInputStream stream2 = new FileInputStream(fileImg2);
                pst.setBinaryStream(8, stream2, (int) fileImg2.length());


                pst.setString(9, txtMob.getText());

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
       public void doBack(ActionEvent event) {
            SceneSwitcher.switchScene(event, "/dashboardv/dashBoardV.fxml");
        }

        @FXML
        void initialize()
        {
       String type[]={"Select","Buyer","Seller","Both"};
           ComboType.getItems().addAll(type);

           ComboType.getSelectionModel().select(0);


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

























