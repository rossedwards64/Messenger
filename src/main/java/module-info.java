module com.rossedwards.nsdassignment {
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;
    requires java.sql;


    opens com.rossedwards.nsdassignment to javafx.fxml;
    exports com.rossedwards.nsdassignment;
}