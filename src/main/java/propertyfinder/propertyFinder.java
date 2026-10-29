package propertyfinder;
import util.SceneSwitcher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

import javafx.scene.control.cell.PropertyValueFactory;
import jdbcc.DatabaseConnection;

import java.io.File;
import java.io.FileOutputStream;
import javafx.stage.FileChooser;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javafx.scene.control.TableView;

public class propertyFinder {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;




    @FXML
    private ComboBox<String> txtConst;

    @FXML
    private TextField txtMax;

    @FXML
    private TextField txtMin;


    @FXML
    private ComboBox<String> txtPropt;

   @FXML
   private TableView<propertybean> table;

    @FXML
    private ComboBox<String> txtarea;

    @FXML
    private ComboBox<String> txtcity;





    Connection con;




    @FXML
    void doShow(ActionEvent event)
    {
        //System.out.println(("KKKK"));
        table.getColumns().clear();

        TableColumn<propertybean, Integer> ridCol = new TableColumn<>("RID");
        ridCol.setCellValueFactory(new PropertyValueFactory<>("rid"));

        TableColumn<propertybean, String> mobCol = new TableColumn<>("Mobile");
        mobCol.setCellValueFactory(new PropertyValueFactory<>("mobile"));

        TableColumn<propertybean, String> locationCol = new TableColumn<>("Location");
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));

        TableColumn<propertybean, Float> sizeCol = new TableColumn<>("Size");
        sizeCol.setCellValueFactory(new PropertyValueFactory<>("size"));

        TableColumn<propertybean, Float> frontCol = new TableColumn<>("Front");
        frontCol.setCellValueFactory(new PropertyValueFactory<>("front"));

        TableColumn<propertybean, Float> rearCol = new TableColumn<>("Rear");
        rearCol.setCellValueFactory(new PropertyValueFactory<>("rear"));

        TableColumn<propertybean, Float> leftCol = new TableColumn<>("Left");
        leftCol.setCellValueFactory(new PropertyValueFactory<>("leftv"));

        TableColumn<propertybean, Float> rightCol = new TableColumn<>("Right");
        rightCol.setCellValueFactory(new PropertyValueFactory<>("rightv"));

        TableColumn<propertybean, String> facingCol = new TableColumn<>("Facing");
        facingCol.setCellValueFactory(new PropertyValueFactory<>("facing"));

        TableColumn<propertybean, String> approvedCol = new TableColumn<>("Approved By");
        approvedCol.setCellValueFactory(new PropertyValueFactory<>("approvedby"));

        TableColumn<propertybean, Integer> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        table.getColumns().addAll(
                ridCol,
                mobCol,
                locationCol,
                sizeCol,
                frontCol,
                rearCol,
                leftCol,
                rightCol,
                facingCol,
                approvedCol,
                priceCol
        );

        ObservableList<propertybean> list = FXCollections.observableArrayList();

        try
        {
            PreparedStatement pst = con.prepareStatement(
                    "SELECT rid,mobile,location,size,front,rear,leftv,rightv,facing,approvedby,price " +
                            "FROM properties " +
                            "WHERE city=? AND area=? AND proptype=? AND constype=? " +
                            "AND price BETWEEN ? AND ?");

            pst.setString(1, txtcity.getValue());
            pst.setString(2, txtarea.getValue());
            pst.setString(3, txtPropt.getValue());   // Plot / Construction
            pst.setString(4, txtConst.getValue());
//            pst.setInt(5, Integer.parseInt(txtMin.getText()));
//            pst.setInt(6, Integer.parseInt(txtMax.getText()));

            int minPrice, maxPrice;

            try {
                minPrice = Integer.parseInt(txtMin.getText().trim());
                maxPrice = Integer.parseInt(txtMax.getText().trim());
            } catch (NumberFormatException exp) {
                System.out.println("Please enter valid minimum and maximum prices.");
                return;
            }

            if (minPrice > maxPrice) {
                System.out.println("Minimum price cannot be greater than maximum price.");
                return;
            }

            pst.setInt(5, minPrice);
            pst.setInt(6, maxPrice);


            ResultSet rs = pst.executeQuery();

            while(rs.next())
            {
                System.out.println("Record Found");

                propertybean obj = new propertybean(
                        rs.getInt("rid"),
                        rs.getString("mobile"),
                        rs.getString("location"),
                        rs.getFloat("size"),
                        rs.getFloat("front"),
                        rs.getFloat("rear"),
                        rs.getFloat("leftv"),
                        rs.getFloat("rightv"),
                        rs.getString("facing"),
                        rs.getString("approvedby"),
                        rs.getInt("price")
                );

                list.add(obj);
            }

            table.setItems(list);
        }
        catch(Exception exp)
        {
            exp.printStackTrace();
        }
    }












    @FXML
    void doPdf(ActionEvent event)
    {
        try
        {
            // Save Dialog
            FileChooser chooser = new FileChooser();
            chooser.setTitle("Save Property PDF");
            chooser.setInitialFileName("Properties.pdf");
            chooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));

            File file = chooser.showSaveDialog(null);

            if(file == null)
                return;

            // Create PDF
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(file));

            document.open();

            document.add(new Paragraph("Property Details"));
            document.add(new Paragraph(" "));

            PdfPTable pdfTable = new PdfPTable(11);

            // Header
            pdfTable.addCell("RID");
            pdfTable.addCell("Mobile");
            pdfTable.addCell("Location");
            pdfTable.addCell("Size");
            pdfTable.addCell("Front");
            pdfTable.addCell("Rear");
            pdfTable.addCell("Left");
            pdfTable.addCell("Right");
            pdfTable.addCell("Facing");
            pdfTable.addCell("Approved By");
            pdfTable.addCell("Price");

            // Data
            for(propertybean obj : table.getItems())
            {
                pdfTable.addCell(String.valueOf(obj.getRid()));
                pdfTable.addCell(obj.getMobile());
                pdfTable.addCell(obj.getLocation());
                pdfTable.addCell(String.valueOf(obj.getSize()));
                pdfTable.addCell(String.valueOf(obj.getFront()));
                pdfTable.addCell(String.valueOf(obj.getRear()));
                pdfTable.addCell(String.valueOf(obj.getLeftv()));
                pdfTable.addCell(String.valueOf(obj.getRightv()));
                pdfTable.addCell(obj.getFacing());
                pdfTable.addCell(obj.getApprovedby());
                pdfTable.addCell(String.valueOf(obj.getPrice()));
            }

            document.add(pdfTable);

            document.close();

            System.out.println("PDF Generated Successfully");
        }
        catch(Exception exp)
        {
            exp.printStackTrace();
        }
    }


    @FXML
    void doCity(ActionEvent event)
    {
        try
        {
            txtarea.getItems().clear();

            PreparedStatement pst = con.prepareStatement(
                    "SELECT DISTINCT area FROM properties WHERE city=?");

            pst.setString(1, txtcity.getSelectionModel().getSelectedItem());

            ResultSet rs = pst.executeQuery();

            while(rs.next())
            {

                txtarea.getItems().add(rs.getString("area"));

            }
            txtarea.getSelectionModel().selectFirst();

        }
        catch(Exception exp)
        {
            exp.printStackTrace();
        }
    }




    @FXML
  public   void doBack(ActionEvent event) {
        SceneSwitcher.switchScene(event, "/dashboardv/dashBoardV.fxml");
    }

    @FXML
    void initialize()
    {
        doConnect();

        try
        {
            PreparedStatement pst = con.prepareStatement(
                    "SELECT DISTINCT city FROM properties");

            ResultSet rs = pst.executeQuery();

            while(rs.next())
            {
                txtcity.getItems().add(rs.getString("city"));
            }
          String consti[]={"Select","Residential","Commercial","Agriculture"};
            txtConst.getItems().addAll(consti);
            txtConst.getSelectionModel().select(1);

            String propri[] = {"Select","Plot","Construction"};
            txtPropt.getItems().addAll(propri);
            txtPropt.getSelectionModel().select(1);
        }
        catch(Exception exp)
        {
            exp.printStackTrace();
        }
    }


    public void doConnect()
    {
        con = DatabaseConnection.doConnectToDB();

        if(con==null)
            System.out.println("Connection Error");
        else
            System.out.println("Alll is Welll");
    }

}