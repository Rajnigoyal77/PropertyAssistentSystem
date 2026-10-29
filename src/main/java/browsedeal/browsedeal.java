package browsedeal;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import jdbcc.DatabaseConnection;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import util.SceneSwitcher;
import util.SceneSwitcher;
public class browsedeal {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ToggleGroup rad1;

    @FXML
    private DatePicker txtDate;

    @FXML
    private TableView<dealbean> txttable;

    @FXML
    private RadioButton txtrad1;

    @FXML
    private RadioButton txtrad2;

    @FXML
    void doPdf(ActionEvent event)
    {
        try
        {
            FileChooser chooser = new FileChooser();

            chooser.setTitle("Save Deal PDF");
            chooser.setInitialFileName("Deals.pdf");
            chooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));

            File file = chooser.showSaveDialog(null);

            if(file == null)
                return;


            Document document = new Document();

            PdfWriter.getInstance(document, new FileOutputStream(file));

            document.open();


            document.add(new Paragraph("Deal Details"));
            document.add(new Paragraph(" "));


            PdfPTable pdfTable = new PdfPTable(11);


            // Header
            pdfTable.addCell("ID");
            pdfTable.addCell("Seller");
            pdfTable.addCell("Seller Contact");
            pdfTable.addCell("Buyer");
            pdfTable.addCell("Buyer Contact");
            pdfTable.addCell("Final Amount");
            pdfTable.addCell("Down Payment");
            pdfTable.addCell("Deal Date");
            pdfTable.addCell("Commission");
            pdfTable.addCell("Advance");
            pdfTable.addCell("Balance");


            // Data
            for(dealbean obj : txttable.getItems())
            {
                pdfTable.addCell(String.valueOf(obj.getId()));
                pdfTable.addCell(obj.getSeller());
                pdfTable.addCell(obj.getSellercontact());
                pdfTable.addCell(obj.getBuyer());
                pdfTable.addCell(obj.getBuyercontact());
                pdfTable.addCell(String.valueOf(obj.getFinalamt()));
                pdfTable.addCell(String.valueOf(obj.getDownpmt()));
                pdfTable.addCell(String.valueOf(obj.getDtofdeal()));
                pdfTable.addCell(String.valueOf(obj.getTotalcom()));
                pdfTable.addCell(String.valueOf(obj.getAdv()));
                pdfTable.addCell(String.valueOf(obj.getBalance()));
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
    void doExcel(ActionEvent event)
    {
        try
        {
            Workbook workbook = new XSSFWorkbook();

            Sheet sheet = workbook.createSheet("Deals");


            int rownum = 0;


            // Header
            Row header = sheet.createRow(rownum++);

            header.createCell(0).setCellValue("ID");
            header.createCell(1).setCellValue("Seller");
            header.createCell(2).setCellValue("Seller Contact");
            header.createCell(3).setCellValue("Buyer");
            header.createCell(4).setCellValue("Buyer Contact");
            header.createCell(5).setCellValue("Final Amount");
            header.createCell(6).setCellValue("Down Payment");
            header.createCell(7).setCellValue("Deal Date");
            header.createCell(8).setCellValue("Commission");
            header.createCell(9).setCellValue("Advance");
            header.createCell(10).setCellValue("Balance");


            // Data
            for(dealbean obj : txttable.getItems())
            {
                Row row = sheet.createRow(rownum++);


                row.createCell(0).setCellValue(obj.getId());
                row.createCell(1).setCellValue(obj.getSeller());
                row.createCell(2).setCellValue(obj.getSellercontact());
                row.createCell(3).setCellValue(obj.getBuyer());
                row.createCell(4).setCellValue(obj.getBuyercontact());
                row.createCell(5).setCellValue(obj.getFinalamt());
                row.createCell(6).setCellValue(obj.getDownpmt());
                row.createCell(7).setCellValue(String.valueOf(obj.getDtofdeal()));
                row.createCell(8).setCellValue(String.valueOf(obj.getTotalcom()));
                row.createCell(9).setCellValue(obj.getAdv());
                row.createCell(10).setCellValue(obj.getBalance());
            }


            FileChooser chooser = new FileChooser();

            chooser.setTitle("Save Deals as Excel");
            chooser.setInitialFileName("Deals.xlsx");

            chooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"));


            File file = chooser.showSaveDialog(null);


            if(file == null)
                return;


            FileOutputStream fos = new FileOutputStream(file);

            workbook.write(fos);


            fos.close();

            workbook.close();


            System.out.println("EXCEL CREATED");

        }
        catch(Exception exp)
        {
            exp.printStackTrace();
        }
    }

    @FXML
    void doSearch(ActionEvent event)
    {
        txttable.getColumns().clear();


        // Columns
        TableColumn<dealbean,Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<dealbean,String> sellerCol = new TableColumn<>("Seller");
        sellerCol.setCellValueFactory(new PropertyValueFactory<>("seller"));

        TableColumn<dealbean,String> sellerConCol = new TableColumn<>("Seller Contact");
        sellerConCol.setCellValueFactory(new PropertyValueFactory<>("sellercontact"));

        TableColumn<dealbean,String> buyerCol = new TableColumn<>("Buyer");
        buyerCol.setCellValueFactory(new PropertyValueFactory<>("buyer"));

        TableColumn<dealbean,String> buyerConCol = new TableColumn<>("Buyer Contact");
        buyerConCol.setCellValueFactory(new PropertyValueFactory<>("buyercontact"));

        TableColumn<dealbean,Integer> finalAmtCol = new TableColumn<>("Final Amount");
        finalAmtCol.setCellValueFactory(new PropertyValueFactory<>("finalamt"));

        TableColumn<dealbean,Integer> downCol = new TableColumn<>("Down Payment");
        downCol.setCellValueFactory(new PropertyValueFactory<>("downpmt"));

        TableColumn<dealbean,String> dealDateCol = new TableColumn<>("Deal Date");
        dealDateCol.setCellValueFactory(new PropertyValueFactory<>("dtofdeal"));

        TableColumn<dealbean,String> comCol = new TableColumn<>("Commission");
        comCol.setCellValueFactory(new PropertyValueFactory<>("totalcom"));

        TableColumn<dealbean,Integer> advCol = new TableColumn<>("Advance");
        advCol.setCellValueFactory(new PropertyValueFactory<>("adv"));

        TableColumn<dealbean,Integer> balCol = new TableColumn<>("Balance");
        balCol.setCellValueFactory(new PropertyValueFactory<>("balance"));

        txttable.getColumns().addAll(idCol, sellerCol, sellerConCol, buyerCol, buyerConCol, finalAmtCol, downCol, dealDateCol, comCol, advCol, balCol
        );

        ObservableList<dealbean> list = FXCollections.observableArrayList();

        try
        {
            PreparedStatement pst = con.prepareStatement(
                    "SELECT * FROM deals WHERE dtofdeal=? and status=?");

            pst.setDate(1, java.sql.Date.valueOf(txtDate.getValue()));

            if(txtrad1.isSelected())
            {
                pst.setInt(2, 1);   // Pending
            }
            else if(txtrad2.isSelected())
            {
                pst.setInt(2, 2);   // Fulfilled
            }

            ResultSet rs = pst.executeQuery();

            while(rs.next())
            {
                dealbean obj = new dealbean(
                        rs.getInt("id"),
                        rs.getString("seller"),
                        rs.getString("sellercontact"),
                        rs.getString("buyer"),
                        rs.getString("buyercontact"),
                        rs.getInt("finalamt"),
                        rs.getInt("downpmt"),
                        rs.getDate("dtofdeal").toString(),
                        rs.getString("totalcom"),
                        rs.getInt("adv"),
                        rs.getInt("balance")
                );

                list.add(obj);
            }

            txttable.setItems(list);
        }
        catch(Exception exp)
        {
            exp.printStackTrace();
        }
    }













    @FXML

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
   public void doBack(ActionEvent event) {
        SceneSwitcher.switchScene(event, "/dashboardv/dashBoardV.fxml");
    }


    @FXML
    void initialize() {
   doConnect();
        txtrad1.setSelected(true);

    }

}
