package com.asesinosdesoftware.javeparking.controller;

import com.asesinosdesoftware.javeparking.entities.Cliente;
import com.asesinosdesoftware.javeparking.repository.ClienteRepository;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.sql.Connection;

public class Registro_View_Controller {
    @FXML
    private ComboBox <String> Iduniversity;
    @FXML
    private TextField IDCedula;
    @FXML
    private TextField IdNombre;
    @FXML
    private TextField IdApellido;

    @FXML
    private void registro() {

        try {

            Cliente C = new Cliente();
            C.setCedula(IDCedula.getText());
            C.setNombre(IdNombre.getText());
            C.setApellido(IdApellido.getText());
            C.setUniversidad(Iduniversity.getValue().charAt(0));

            JDBCController controller = new JDBCController();
            Connection connection = controller.getConnection();
            ClienteRepository repository = new ClienteRepository();

            repository.agregarCliente(connection,C);

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

}


