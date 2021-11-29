module com.rossedwards.nsdassignment {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.rossedwards.nsdassignment to javafx.fxml;
    exports com.rossedwards.nsdassignment;
}