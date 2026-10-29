
package listpropertycontrol;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import jdbcc.DatabaseConnection;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import util.SceneSwitcher;

public class listPropertyC {

    @FXML
    private ImageView Prev1;

    @FXML
    private ImageView prev2;

    @FXML
    private TextField txtAddr;

    @FXML
    private Button doPrev1;

    @FXML
    private Button doPrev2;

    @FXML
    private RadioButton txtAgr;

    @FXML
    private ComboBox<String> txtCApp;

    @FXML
    private RadioButton txtCom;

    @FXML
    private ComboBox<String> txtCombo;

    @FXML
    private RadioButton txtConst;

    @FXML
    private TextField txtArea;

    @FXML
    private TextField txtCity;

    @FXML
    private TextField txtDir;

    @FXML
    private TextField txtFront;

    @FXML
    private TextField txtInfo;

    @FXML
    private TextField txtLeft;

    @FXML
    private TextField txtMob;

    @FXML
    private RadioButton txtPlot;

    @FXML
    private TextField txtPrice;

    @FXML
    private TextField txtRear;

    @FXML
    private RadioButton txtRes;

    @FXML
    private TextField txtRight;

    @FXML
    private TextField txtSize;

    Connection con;

    String picPath1;
    String picPath2;

    List<Integer> propertyIds = new ArrayList<>();

    @FXML
    void doFetch(ActionEvent event) {
        System.out.println("Mobile = " + txtMob.getText());
        try {
            txtCombo.getItems().clear();
            propertyIds.clear();

            PreparedStatement pst = con.prepareStatement(
                    "select rid,constype from properties where mobile=?");

            pst.setString(1, txtMob.getText().trim());

            ResultSet rs = pst.executeQuery();

            txtCombo.setPromptText("Select Property");

            while (rs.next()) {
                System.out.println("RID = " + rs.getInt("rid"));

                propertyIds.add(rs.getInt("rid"));
                txtCombo.getItems().add(rs.getString("constype"));
            }

            System.out.println("Fetched properties = " + txtCombo.getItems());

        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }

    @FXML
    void doCombo(ActionEvent event) {
        try {
            int index = txtCombo.getSelectionModel().getSelectedIndex();

            if (index < 0 || index >= propertyIds.size()) {
                System.out.println("Please select a property");
                return;
            }

            PreparedStatement pst = con.prepareStatement(
                    "select * from properties where rid=?");

            pst.setInt(1, propertyIds.get(index));

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                txtMob.setText(rs.getString("mobile"));
                txtAddr.setText(rs.getString("location"));
                txtArea.setText(rs.getString("area"));
                txtCity.setText(rs.getString("city"));

                txtSize.setText(String.valueOf(rs.getFloat("size")));
                txtFront.setText(String.valueOf(rs.getFloat("front")));
                txtRear.setText(String.valueOf(rs.getFloat("rear")));
                txtLeft.setText(String.valueOf(rs.getFloat("leftv")));
                txtRight.setText(String.valueOf(rs.getFloat("rightv")));

                txtDir.setText(rs.getString("facing"));

                txtPlot.setSelected(false);
                txtAgr.setSelected(false);
                txtRes.setSelected(false);
                txtCom.setSelected(false);
                txtConst.setSelected(false);

                if ("Plot".equals(rs.getString("proptype"))) {
                    txtPlot.setSelected(true);
                } else if ("Agriculture".equals(rs.getString("proptype"))) {
                    txtAgr.setSelected(true);
                }

                if ("Residential".equals(rs.getString("constype"))) {
                    txtRes.setSelected(true);
                } else if ("Commercial".equals(rs.getString("constype"))) {
                    txtCom.setSelected(true);
                } else if ("Under Construction".equals(rs.getString("constype"))) {
                    txtConst.setSelected(true);
                }

                txtCApp.getSelectionModel().select(rs.getString("approvedby"));
                txtPrice.setText(String.valueOf(rs.getInt("price")));
                txtInfo.setText(rs.getString("otherinfo"));

                byte[] pic1 = rs.getBytes("pic1");
                if (pic1 != null && pic1.length > 0) {
                    Prev1.setImage(new Image(new ByteArrayInputStream(pic1)));
                } else {
                    Prev1.setImage(null);
                }

                byte[] pic2 = rs.getBytes("pic2");
                if (pic2 != null && pic2.length > 0) {
                    prev2.setImage(new Image(new ByteArrayInputStream(pic2)));
                } else {
                    prev2.setImage(null);
                }

                picPath1 = null;
                picPath2 = null;

                System.out.println("Property loaded. RID = " + propertyIds.get(index));

            } else {
                System.out.println("No Record Found");
            }

        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }

    @FXML
    void doPrev1(ActionEvent event) {
        try {
            FileChooser chooser = new FileChooser();
            chooser.setTitle("Select property Pic:");
            chooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("All Images", "*.jpg", "*.png"));

            File fileRef = chooser.showOpenDialog(null);

            if (fileRef != null) {
                picPath1 = fileRef.getAbsolutePath();
                Prev1.setImage(new Image(new FileInputStream(fileRef)));
            }

        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }

    @FXML
    void doPrev2(ActionEvent event) {
        try {
            FileChooser chooser = new FileChooser();
            chooser.setTitle("Select Profile Pic:");
            chooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("All Images", "*.jpg", "*.png"));

            File fileRef = chooser.showOpenDialog(null);

            if (fileRef != null) {
                picPath2 = fileRef.getAbsolutePath();
                prev2.setImage(new Image(new FileInputStream(fileRef)));
            }

        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }

    @FXML
    void doList(ActionEvent event) {
        try {
            PreparedStatement pst = con.prepareStatement(
                    "insert into properties(mobile,location,area,city,size,front,rear,leftv,rightv,facing,proptype,constype,approvedby,price,otherinfo,pic1,pic2) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

            pst.setString(1, txtMob.getText());
            pst.setString(2, txtAddr.getText());
            pst.setString(3, txtArea.getText());
            pst.setString(4, txtCity.getText());

            pst.setFloat(5, Float.parseFloat(txtSize.getText()));
            pst.setFloat(6, Float.parseFloat(txtFront.getText()));
            pst.setFloat(7, Float.parseFloat(txtRear.getText()));
            pst.setFloat(8, Float.parseFloat(txtLeft.getText()));
            pst.setFloat(9, Float.parseFloat(txtRight.getText()));

            pst.setString(10, txtDir.getText());

            if (txtPlot.isSelected()) {
                pst.setString(11, "Plot");
            } else if (txtAgr.isSelected()) {
                pst.setString(11, "Agriculture");
            } else {
                pst.setString(11, "");
            }

            if (txtRes.isSelected()) {
                pst.setString(12, "Residential");
            } else if (txtCom.isSelected()) {
                pst.setString(12, "Commercial");
            } else if (txtConst.isSelected()) {
                pst.setString(12, "Under Construction");
            } else {
                pst.setString(12, "");
            }

            pst.setString(13, txtCApp.getSelectionModel().getSelectedItem());
            pst.setInt(14, Integer.parseInt(txtPrice.getText()));
            pst.setString(15, txtInfo.getText());

            File fileImg1 = new File(picPath1);
            FileInputStream stream1 = new FileInputStream(fileImg1);
            pst.setBinaryStream(16, stream1, (int) fileImg1.length());

            File fileImg2 = new File(picPath2);
            FileInputStream stream2 = new FileInputStream(fileImg2);
            pst.setBinaryStream(17, stream2, (int) fileImg2.length());

            pst.executeUpdate();

            System.out.println("Property Listed Successfully");

        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }

    @FXML
    void doRemove(ActionEvent event) {
        try {
            int index = txtCombo.getSelectionModel().getSelectedIndex();

            if (index < 0 || index >= propertyIds.size()) {
                System.out.println("Please select a property to remove");
                return;
            }

            PreparedStatement pst = con.prepareStatement(
                    "delete from properties where rid=?");

            pst.setInt(1, propertyIds.get(index));

            int status = pst.executeUpdate();

            if (status > 0) {
                System.out.println("Property Removed Successfully");
                txtCombo.getItems().remove(index);
                propertyIds.remove(index);
                Prev1.setImage(null);
                prev2.setImage(null);
            } else {
                System.out.println("Record Not Found");
            }

        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }

    @FXML
    void doUpdate(ActionEvent event) {
        try {
            int index = txtCombo.getSelectionModel().getSelectedIndex();

            if (index < 0 || index >= propertyIds.size()) {
                System.out.println("Please select a property before updating");
                return;
            }

            int rid = propertyIds.get(index);

            byte[] oldPic1 = null;
            byte[] oldPic2 = null;

            PreparedStatement oldPst = con.prepareStatement(
                    "select pic1,pic2 from properties where rid=?");

            oldPst.setInt(1, rid);

            ResultSet oldRs = oldPst.executeQuery();

            if (oldRs.next()) {
                oldPic1 = oldRs.getBytes("pic1");
                oldPic2 = oldRs.getBytes("pic2");
            } else {
                System.out.println("Record Not Found");
                return;
            }

            PreparedStatement pst = con.prepareStatement(
                    "update properties set location=?,area=?,city=?,size=?,front=?,rear=?,leftv=?,rightv=?,facing=?,proptype=?,constype=?,approvedby=?,price=?,otherinfo=?,pic1=?,pic2=? where rid=?");

            pst.setString(1, txtAddr.getText());
            pst.setString(2, txtArea.getText());
            pst.setString(3, txtCity.getText());

            pst.setFloat(4, Float.parseFloat(txtSize.getText()));
            pst.setFloat(5, Float.parseFloat(txtFront.getText()));
            pst.setFloat(6, Float.parseFloat(txtRear.getText()));
            pst.setFloat(7, Float.parseFloat(txtLeft.getText()));
            pst.setFloat(8, Float.parseFloat(txtRight.getText()));

            pst.setString(9, txtDir.getText());

            if (txtPlot.isSelected()) {
                pst.setString(10, "Plot");
            } else if (txtAgr.isSelected()) {
                pst.setString(10, "Agriculture");
            } else {
                pst.setString(10, "");
            }

            if (txtRes.isSelected()) {
                pst.setString(11, "Residential");
            } else if (txtCom.isSelected()) {
                pst.setString(11, "Commercial");
            } else if (txtConst.isSelected()) {
                pst.setString(11, "Under Construction");
            } else {
                pst.setString(11, "");
            }

            pst.setString(12, txtCApp.getSelectionModel().getSelectedItem());
            pst.setInt(13, Integer.parseInt(txtPrice.getText()));
            pst.setString(14, txtInfo.getText());

            if (picPath1 != null) {
                File file1 = new File(picPath1);
                FileInputStream stream1 = new FileInputStream(file1);
                pst.setBinaryStream(15, stream1, (int) file1.length());
            } else {
                pst.setBytes(15, oldPic1);
            }

            if (picPath2 != null) {
                File file2 = new File(picPath2);
                FileInputStream stream2 = new FileInputStream(file2);
                pst.setBinaryStream(16, stream2, (int) file2.length());
            } else {
                pst.setBytes(16, oldPic2);
            }

            pst.setInt(17, rid);

            int status = pst.executeUpdate();

            if (status > 0) {
                System.out.println("Property Updated Successfully");
                picPath1 = null;
                picPath2 = null;
            } else {
                System.out.println("Record Not Found");
            }

        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }

    @FXML
    void doBack(ActionEvent event) {
        SceneSwitcher.switchScene(event, "/dashboardv/dashBoardV.fxml");
    }

    @FXML
    void initialize() {
        String approved[] = {
                "Select", "PUDA", "GMADA", "GLADA", "BDA",
                "Municipal Corporation", "Municipal Council",
                "Municipal Committee", "Improvement Trust",
                "Gram Panchayat", "Town & Country Planning",
                "HUDA", "Not Approved", "Other"
        };

        txtCApp.getItems().addAll(approved);
        txtCApp.getSelectionModel().select(0);

        doConnect();
    }

    public void doConnect() {
        con = DatabaseConnection.doConnectToDB();

        if (con == null) {
            System.out.println("Connection Error");
        } else {
            System.out.println("Alll is Welll");
        }
    }
}
