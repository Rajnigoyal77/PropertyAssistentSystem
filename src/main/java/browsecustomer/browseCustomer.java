package browsecustomer;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import util.SceneSwitcher;
import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import jdbcc.DatabaseConnection;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.itextpdf.text.Document;

import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;






;

public class browseCustomer {


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> doCombo;

    @FXML
    private TableView<CustomerBean> table;

    @FXML
    void doExcel(ActionEvent event) {
        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Customers");

            int rownum = 0;

            // Header Row
            Row header = sheet.createRow(rownum++);
            header.createCell(0).setCellValue("Mobile");
            header.createCell(1).setCellValue("Name");
            header.createCell(2).setCellValue("City");
            header.createCell(3).setCellValue("Address");
            header.createCell(4).setCellValue("Email");
            header.createCell(5).setCellValue("Date");

            // Data Rows
            for (CustomerBean obj : table.getItems()) {
                Row row = sheet.createRow(rownum++);

                row.createCell(0).setCellValue(obj.getMobile());
                row.createCell(1).setCellValue(obj.getName());
                row.createCell(2).setCellValue(obj.getCity());
                row.createCell(3).setCellValue(obj.getAdresss());
                row.createCell(4).setCellValue(obj.getEmail());
                row.createCell(5).setCellValue(obj.getDae().toString());
            }

            // Save Dialog
            FileChooser chooser = new FileChooser();
            chooser.setTitle("Save Customers as Excel");
            chooser.setInitialFileName("Customers.xlsx");
            chooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"));

            File file = chooser.showSaveDialog(null);

            if (file == null)
                return;

            FileOutputStream fos = new FileOutputStream(file);
            workbook.write(fos);

            fos.close();
            workbook.close();

            System.out.println("EXCEL CREATED");

        } catch (Exception exp) {
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
            chooser.setTitle("Save Customers PDF");
            chooser.setInitialFileName("Customers.pdf");
            chooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));

            File file = chooser.showSaveDialog(null);

            if (file == null)
                return;

            // Create PDF
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(file));

            document.open();

            document.add(new Paragraph("Customer Details"));
            document.add(new Paragraph(" "));   // Blank Line

            PdfPTable pdfTable = new PdfPTable(6);

            // Header
            pdfTable.addCell("Mobile");
            pdfTable.addCell("Name");
            pdfTable.addCell("City");
            pdfTable.addCell("Address");
            pdfTable.addCell("Email");
            pdfTable.addCell("Date");

            // Data
            for (CustomerBean obj : table.getItems())
            {
                pdfTable.addCell(obj.getMobile());
                pdfTable.addCell(obj.getName());
                pdfTable.addCell(obj.getCity());
                pdfTable.addCell(obj.getAdresss());
                pdfTable.addCell(obj.getEmail());
                pdfTable.addCell(obj.getDae().toString());
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
    void doShow(ActionEvent event)
    {
        table.getColumns().clear();

        TableColumn<CustomerBean, String> mobCol =
                new TableColumn<>("Mobile");
        mobCol.setCellValueFactory(new PropertyValueFactory<>("mobile"));

        TableColumn<CustomerBean, String> nameCol =
                new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<CustomerBean, String> cityCol =
                new TableColumn<>("City");
        cityCol.setCellValueFactory(new PropertyValueFactory<>("city"));

        TableColumn<CustomerBean, String> addCol =
                new TableColumn<>("Address");
        addCol.setCellValueFactory(new PropertyValueFactory<>("adresss"));

        TableColumn<CustomerBean, String> emailCol =
                new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<CustomerBean, String> doeCol =
                new TableColumn<>("Date");
        doeCol.setCellValueFactory(new PropertyValueFactory<>("dae"));

        table.getColumns().addAll(mobCol, nameCol, cityCol, addCol, emailCol, doeCol);

        ObservableList<CustomerBean> list = FXCollections.observableArrayList();

        try
        {
            PreparedStatement pst;

            if(doCombo.getSelectionModel().getSelectedItem().equals("All"))
            {
                pst = con.prepareStatement("select * from Customers");
            }
            else
            {
                pst = con.prepareStatement(
                        "select * from Customers where ctype=?");

                pst.setString(1, doCombo.getSelectionModel().getSelectedItem());
            }

            ResultSet rs = pst.executeQuery();

            while(rs.next())
            {
                CustomerBean obj = new CustomerBean(
                        rs.getString("mobile"),
                        rs.getString("name"),
                        rs.getString("city"),
                        rs.getString("adresss"),
                        rs.getString("email"),
                        rs.getDate("dae").toString()
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

    Connection con;

    public void doConnect()
    {
        con = DatabaseConnection.doConnectToDB();

        if(con==null)
            System.out.println("Connection Error");
        else
            System.out.println("All Is Well");
    }




    @FXML
  public   void doBack(ActionEvent event) {
        SceneSwitcher.switchScene(event, "/dashboardv/dashBoardV.fxml");
    }
    @FXML
    void initialize() {
        String type[]={"Select","All","Buyer","Seller","Both"};
        doCombo.getItems().addAll(type);

        doCombo.getSelectionModel().select(0);
        doConnect();


    }

}