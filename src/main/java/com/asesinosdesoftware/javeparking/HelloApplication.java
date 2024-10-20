package com.asesinosdesoftware.javeparking;

import com.asesinosdesoftware.javeparking.init.JDBCInitializer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-View.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("JAVEPARKING");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {

        try {
            JDBCInitializer initializer = new JDBCInitializer();
            Connection connection = initializer.getConnection();
            initializer.inicializarTablas(connection);
            connection.close();


        } catch (SQLException e) {
            System.out.println("Ocurrio un error en la conexión de base de datos: " + e);
        } catch (Exception e) {
            System.out.println("Ocurrio un error: " + e);
        }

        launch();
    }
}