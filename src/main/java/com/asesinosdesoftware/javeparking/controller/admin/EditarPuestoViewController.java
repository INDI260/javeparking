package com.asesinosdesoftware.javeparking.controller.admin;

import com.asesinosdesoftware.javeparking.entities.Puesto;
import com.asesinosdesoftware.javeparking.exceptions.ServiceException;
import com.asesinosdesoftware.javeparking.repository.PuestoRepository;
import com.asesinosdesoftware.javeparking.services.PuestosService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;

public class EditarPuestoViewController {


    @FXML
    private TableView<Puesto> tablaPuestos;
    @FXML
    private TableColumn<Puesto, Integer> columnaIDPuesto;
    @FXML
    private TableColumn<Puesto, String> columnaTamano;
    @FXML
    private TableColumn<Puesto, Integer> columnaIDParqueadero;
    @FXML
    private TableColumn<Puesto, Boolean> columnaDisponibilidad;

    @FXML
    private ComboBox<String> IdTamano;
    @FXML
    private ComboBox<Boolean> comboDisponibilidad;

    @FXML
    private TextField IDparqueadero;

    // Lista observable para la tabla
    private ObservableList<Puesto> puestosObservableList = FXCollections.observableArrayList();

    PuestosService puestosService = new PuestosService();

    /**
     * Inicialización del controlador.
     * Este método se ejecuta al cargar la vista FXML.
     */
    @FXML
    public void initialize() {
        // Configuración de las columnas de la tabla
        columnaIDPuesto.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnaTamano.setCellValueFactory(new PropertyValueFactory<>("tamano"));
        columnaIDParqueadero.setCellValueFactory(new PropertyValueFactory<>("parqueaderoID"));
        columnaDisponibilidad.setCellValueFactory(new PropertyValueFactory<>("disponibilidad"));

        // Opciones para el tamaño del puesto
        IdTamano.getItems().addAll("p", "m", "g");

        // Opciones para la disponibilidad
        comboDisponibilidad.getItems().addAll(true, false);

        // Cargar los puestos en la tabla
        cargarPuestos();
    }

    /**
     * Método para cargar todos los puestos y mostrarlos en la tabla.
     */
    private void cargarPuestos() {
        try {
            PuestoRepository puestoRepository = new PuestoRepository();
            puestosObservableList.setAll(puestoRepository.obtenerTodosPuestos());
            tablaPuestos.setItems(puestosObservableList);

        } catch (Exception e) {
            showError("Error al cargar los puestos.");
            e.printStackTrace();
        }
    }

    /**
     * metodo que permite al administrador editar los datos de los puestos
     * registrados en cada parqueadero
     * la logica del metodo esta en puestosService
     */

    @FXML
    public void editarPuesto() {
    Puesto puestoseleccionado = tablaPuestos.getSelectionModel().getSelectedItem();


    if (puestoseleccionado != null) {
        int parq = Integer.parseInt(IDparqueadero.getText());
        try {

            puestosService.editarpuestos(IdTamano.getValue(),parq,comboDisponibilidad.getValue(),puestoseleccionado);
            showSuccess("Puesto editado con exito");
            cargarPuestos();

        }catch (ServiceException | SQLException e) {
            showError(e.toString());
        }
    } else showError("Seleccione un puesto para editar");

    }

    /**
     * metodo que permite al administrador eliminar un puesto de cualquier parqueader
     * la logica del metodo se encuentra en puestosService
     */

    @FXML
    public void eliminarPuesto() {
        Puesto puestoseleccionado = tablaPuestos.getSelectionModel().getSelectedItem();
        if (puestoseleccionado != null) {
            try {
                puestosService.eliminarPuestos(puestoseleccionado);
                showSuccess("Puesto eliminado con exito");
                cargarPuestos();
            }catch (Exception e) {
                showError("Error al eliminar el puesto");
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

    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Éxito");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}