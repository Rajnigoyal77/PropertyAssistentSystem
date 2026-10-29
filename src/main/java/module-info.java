module com.example.javafx {
    requires javafx.controls;
    requires javafx.fxml;

    requires java.sql;


    opens com.example.javafx to javafx.fxml;
    exports com.example.javafx;





    opens jdbcc to javafx.fxml;
    exports jdbcc;

    opens customerp to javafx.fxml;
    exports customerp;


    opens listpropertycontrol to javafx.fxml;
    exports listpropertycontrol;


    opens dealsviewcont to javafx.fxml;
    exports dealsviewcont;

    opens browsecustomer to javafx.fxml;
    exports browsecustomer;

    opens propertyfinder to javafx.fxml;
    exports propertyfinder;

    opens browsedeal to javafx.fxml;
    exports browsedeal;

    opens charts to javafx.fxml;
    exports charts;

    opens barchart to javafx.fxml;
    exports barchart;



    opens dashboard to javafx.fxml;
    exports dashboard;

    opens loginc to javafx.fxml;
    exports loginc;

    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;
    requires itextpdf;




}