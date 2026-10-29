package loginc;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class logincp {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtUser;

    @FXML
    private PasswordField txtfpass;

    @FXML
    void doExit(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void dologin(ActionEvent event) {

        String user = txtUser.getText();
        String pass = txtfpass.getText();

        try {

            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/property",
                    "root",
                    "realjava2002");

            String sql = "select * from login where userid=? and pwd=?";

            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, user);
            pst.setString(2, pass);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Login Successful");
                alert.show();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/dashboardv/dashBoardV.fxml"));
                Parent root = loader.load();

                Scene scene = new Scene(root);

                Stage stage = (Stage) txtUser.getScene().getWindow();
                stage.setScene(scene);
                stage.centerOnScreen();
                stage.show();

            } else {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Invalid User ID or Password");
                alert.show();
            }

            rs.close();
            pst.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void initialize() {

    }
}