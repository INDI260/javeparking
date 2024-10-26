package com.asesinosdesoftware.javeparking;

import com.asesinosdesoftware.javeparking.entities.Sesion;
import com.asesinosdesoftware.javeparking.persistencia.IDBConnectionManager;
import com.asesinosdesoftware.javeparking.services.InicioDeSesionService;
import com.asesinosdesoftware.javeparking.init.JDBCInitializer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.sql.Connection;

public class HelloController {

    IDBConnectionManager dbConnectionManager;
    @FXML
    private BorderPane contenedor;

    @FXML
    public TextField Usuario;
    @FXML
    public TextField Contrasena;
    // Contenedor principal del layout, inyectado desde el archivo FXML

    @FXML
    private void Salir() {
        System.exit(0);
    }

    @FXML
    private void CerrarSesion() {
        InicioDeSesionService Cerrar = new InicioDeSesionService();
        Cerrar.CerrarSesion();
        try {
            // Carga la vista desde el archivo FXML
            BorderPane pane = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
            // Establece la vista cargada en el centro del contenedor principal
            contenedor.setTop(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para cargar y mostrar la vista de registrar persona
    @FXML
    private void registrarCliente() {
        try {
            // Carga la vista desde el archivo FXML
            AnchorPane pane = FXMLLoader.load(getClass().getResource("Registro_View.fxml"));
            // Establece la vista cargada en el centro del contenedor principal
            contenedor.setCenter(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Método para cargar y mostrar la vista de Inicio de sesion
    @FXML
    private void InicioSesion() {
     try{

         InicioDeSesionService U = new InicioDeSesionService();

         U.InicioDeSesion(dbConnectionManager.getConnection(),Usuario.getText(),Contrasena.getText());
         Menu();
     }
     catch (Exception e) {
         e.printStackTrace();
        }

    }
    @FXML
    private void Menu(){
        if(Sesion.getTipo()=='a'){
            try {
                // Carga la vista desde el archivo FXML
                AnchorPane pane = FXMLLoader.load(getClass().getResource("MenuAdminView.fxml"));
                // Establece la vista cargada en el centro del contenedor principal
                contenedor.setCenter(pane);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        if(Sesion.getTipo()=='c'){
            try {
                // Carga la vista desde el archivo FXML
                AnchorPane pane = FXMLLoader.load(getClass().getResource("MenuClienteView.fxml"));
                // Establece la vista cargada en el centro del contenedor principal
                contenedor.setCenter(pane);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        if(Sesion.getTipo()=='e'){
            try {
                // Carga la vista desde el archivo FXML
                AnchorPane pane = FXMLLoader.load(getClass().getResource("MenuOperarioView.fxml"));
                // Establece la vista cargada en el centro del contenedor principal
                contenedor.setCenter(pane);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Método para mostrar un mensaje de éxito
    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Éxito");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}