package com.asesinosdesoftware.javeparking;

import com.asesinosdesoftware.javeparking.entities.Reserva;
import com.asesinosdesoftware.javeparking.init.JDBCInitializer;
import com.asesinosdesoftware.javeparking.persistencia.H2DBConnectionManager;
import com.asesinosdesoftware.javeparking.persistencia.IDBConnectionManager;
import com.asesinosdesoftware.javeparking.repository.*;
import com.asesinosdesoftware.javeparking.services.PagoService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    /**
     * metodo que carga la pantalla de inicio del programa
     * @param stage the primary stage for this application, onto which
     * the application scene can be set.
     * Applications may create other stages, if needed, but they will not be
     * primary stages.
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-View.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("JAVEPARKING");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * metodo main del programa
     * @param args
     */
    public static void main(String[] args) {

        try {
            IDBConnectionManager dbConnectionManager = new H2DBConnectionManager();
            AdministradorRepository administradorRepository = new AdministradorRepository();
            ClienteRepository clienteRepository = new ClienteRepository();
            EmpleadoRepository empleadoRepository = new EmpleadoRepository();
            VehiculoRepository vehiculoRepository = new VehiculoRepository();
            PuestoRepository puestoRepository = new PuestoRepository();
            ParqueaderoRepository parqueaderoRepository = new ParqueaderoRepository();
            PagoRepository pagoRepository = new PagoRepository();
            ReservaRepository reservaRepository = new ReservaRepository();

            JDBCInitializer initializer = new JDBCInitializer(dbConnectionManager,administradorRepository,clienteRepository,empleadoRepository);
            initializer.inicializarTablas();

        } catch (Exception e) {
            System.out.println("Ocurrio un error: " + e);
        }

        launch();
    }
}