module com.github.juaquii {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.xml.bind;
    requires org.mariadb.jdbc;

    opens com.github.juaquii to javafx.fxml;
    opens com.github.juaquii.Model.Connection to java.xml.bind;
    exports com.github.juaquii;
    exports com.github.juaquii.View;
    opens com.github.juaquii.View to javafx.fxml;
    exports com.github.juaquii.utils;
    opens com.github.juaquii.utils to javafx.fxml;
}
