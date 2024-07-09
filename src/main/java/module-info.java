module com.keymanager {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires MaterialFX;
    requires java.sql;
    requires java.management;
    requires jdk.jfr;
    requires mysql.connector.j;
    requires kernel;
    requires layout;
    requires jdk.compiler;

    opens app to javafx.fxml;
    opens app.controllers.dashboard;
    opens app.controllers.manage_users;
    opens app.controllers.blocks;
    opens app.controllers.settings;
    opens app.controllers.report;
    opens app.controllers.keys;
    opens app.controllers.login;
    opens app.controllers.personnel;
    opens app.entities;
    opens app.controllers to javafx.fxml;
    opens app.models;

    exports app;
    exports app.controllers;
    exports app.controllers.blocks;
    exports app.controllers.report;
    exports app.controllers.settings;
    exports app.controllers.manage_users;
    exports app.controllers.dashboard;
    exports app.controllers.login;
    exports app.controllers.personnel;
    exports app.controllers.keys;


}