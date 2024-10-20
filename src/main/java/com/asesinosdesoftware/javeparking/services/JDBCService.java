package com.asesinosdesoftware.javeparking.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import com.asesinosdesoftware.javeparking.entities.Administrador;
import com.asesinosdesoftware.javeparking.entities.Cliente;
import com.asesinosdesoftware.javeparking.entities.Empleado;
import com.asesinosdesoftware.javeparking.exceptions.RepositoryException;
import com.asesinosdesoftware.javeparking.repository.AdministradorRepository;
import com.asesinosdesoftware.javeparking.repository.ClienteRepository;
import com.asesinosdesoftware.javeparking.repository.EmpleadoRepository;

/**
 * Esta clase se encarga de realizar las operaciones necesarias para la conexión y la incialización de la base de datos.
 */
public class JDBCService {

    private ResourceBundle reader = null;
    private static final String FILENAME = "dbconfig";
    private AdministradorRepository administradorRepository = new AdministradorRepository();
    private ClienteRepository clienteRepository = new ClienteRepository();
    private EmpleadoRepository empleadoRepository = new EmpleadoRepository();

    /**
     *Método que inicializa la conexión con la base de datos
     * @return El objeto de conexión con la base de datos o nulo en caso de que la conexión falle
     */
    public Connection getConnection() throws SQLException {

        reader = ResourceBundle.getBundle(FILENAME);
        return DriverManager.getConnection(reader.getString("db.url"),reader.getString("db.username"),reader.getString("db.password"));
    }

    /**
     * Método que crea las tablas en la base de datos y las incializa con algunos datos para realizar pruebas
     * @param connection: Objeto tipo connection que representa la conexión con la base de datos
     * @throws SQLException
     */
    public void inicializarTablas(Connection connection) throws SQLException, RepositoryException {

        Statement stmt = connection.createStatement();
        stmt.execute("DROP TABLE IF EXISTS `javeparking`.`administrador`;");
        stmt.execute("DROP TABLE IF EXISTS pagoreserva");
        stmt.execute("DROP TABLE IF EXISTS empleado");
        stmt.execute("DROP TABLE IF EXISTS reserva");
        stmt.execute("DROP TABLE IF EXISTS puesto");
        stmt.execute("DROP TABLE IF EXISTS parqueadero");
        stmt.execute("DROP TABLE IF EXISTS vehiculo");
        stmt.execute("DROP TABLE IF EXISTS suscripcion");
        stmt.execute("DROP TABLE IF EXISTS cliente");


        stmt.execute("CREATE TABLE `javeparking`.`administrador` (\n" +
                "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                "  `cedula` VARCHAR(20) NOT NULL,\n" +
                "  `nombre` VARCHAR(45) NULL,\n" +
                "  `apellido` VARCHAR(45) NULL,\n" +
                "  `hash` VARCHAR(100) NOT NULL,\n" +
                "  PRIMARY KEY (`id`),\n" +
                "  UNIQUE INDEX `cedula_UNIQUE` (`cedula` ASC) VISIBLE)");



        stmt.execute("CREATE TABLE `javeparking`.`cliente` (\n" +
                "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                "  `cedula` VARCHAR(20) NOT NULL,\n" +
                "  `nombre` VARCHAR(45) NULL,\n" +
                "  `apellido` VARCHAR(45) NULL,\n" +
                "  `universidad` VARCHAR(1) NULL,\n" +
                "  `hash` VARCHAR(100) NOT NULL,\n" +
                "  PRIMARY KEY (`id`),\n" +
                "  UNIQUE INDEX `cedula_UNIQUE` (`cedula` ASC) VISIBLE);");



        stmt.execute("CREATE TABLE `javeparking`.`empleado` (\n" +
                "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                "  `cedula` VARCHAR(20) NOT NULL,\n" +
                "  `nombre` VARCHAR(45) NULL,\n" +
                "  `apellido` VARCHAR(45) NULL,\n" +
                "  `hash` VARCHAR(100) NOT NULL,\n" +
                "  PRIMARY KEY (`id`),\n" +
                "  UNIQUE INDEX `cedula_UNIQUE` (`cedula` ASC) VISIBLE);\n");



        stmt.execute("CREATE TABLE `javeparking`.`parqueadero` (\n" +
                "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                "  `TarifaEstandar` FLOAT NOT NULL,\n" +
                "  PRIMARY KEY (`id`));");




        stmt.execute("CREATE TABLE `javeparking`.`puesto` (\n" +
                "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                "  `tamano` VARCHAR(1) NULL,\n" +
                "  `disponibilidad` BIT NULL,\n" +
                "  `parqueaderoID` INT NULL,\n" +
                "  PRIMARY KEY (`id`),\n" +
                "  INDEX `parqueaderoID_idx` (`parqueaderoID` ASC) VISIBLE,\n" +
                "  CONSTRAINT `parqueaderoID`\n" +
                "    FOREIGN KEY (`parqueaderoID`)\n" +
                "    REFERENCES `javeparking`.`parqueadero` (`id`)\n" +
                "    ON DELETE NO ACTION\n" +
                "    ON UPDATE NO ACTION);");



        stmt.execute("CREATE TABLE `javeparking`.`vehiculo` (\n" +
                "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                "  `placa` VARCHAR(10) NULL,\n" +
                "  `tamano` VARCHAR(1) NULL,\n" +
                "  `tipo` VARCHAR(45) NULL,\n" +
                "  `clienteID` INT NULL,\n" +
                "  PRIMARY KEY (`id`),\n" +
                "  UNIQUE INDEX `placa_UNIQUE` (`placa` ASC) VISIBLE,\n" +
                "  INDEX `clienteID_idx` (`clienteID` ASC) VISIBLE,\n" +
                "  CONSTRAINT `clienteID`\n" +
                "    FOREIGN KEY (`clienteID`)\n" +
                "    REFERENCES `javeparking`.`cliente` (`id`)\n" +
                "    ON DELETE NO ACTION\n" +
                "    ON UPDATE NO ACTION);");



        stmt.execute("CREATE TABLE `javeparking`.`reserva` (\n" +
                "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                "  `horaEntrada` DATETIME NOT NULL,\n" +
                "  `horaSalida` DATETIME NULL,\n" +
                "  `vehiculoID` INT NULL,\n" +
                "  `puestoID` INT NULL,\n" +
                "  PRIMARY KEY (`id`),\n" +
                "  INDEX `vehiculoID_idx` (`vehiculoID` ASC) VISIBLE,\n" +
                "  INDEX `puestoId_idx` (`puestoID` ASC) VISIBLE,\n" +
                "  CONSTRAINT `vehiculoID`\n" +
                "    FOREIGN KEY (`vehiculoID`)\n" +
                "    REFERENCES `javeparking`.`vehiculo` (`id`)\n" +
                "    ON DELETE NO ACTION\n" +
                "    ON UPDATE NO ACTION,\n" +
                "  CONSTRAINT `puestoId`\n" +
                "    FOREIGN KEY (`puestoID`)\n" +
                "    REFERENCES `javeparking`.`puesto` (`id`)\n" +
                "    ON DELETE NO ACTION\n" +
                "    ON UPDATE NO ACTION);");

        stmt.execute("CREATE TABLE `javeparking`.`suscripcion` (\n" +
                "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                "  `clienteID` INT NOT NULL,\n" +  // Debe coincidir con el tipo de dato en la tabla cliente
                "  `fecha_inicio` DATE NULL,\n" +
                "  `fecha_fin` DATE NULL,\n" +
                "  `estado` VARCHAR(100) NOT NULL,\n" +
                "  PRIMARY KEY (`id`),\n" +
                "  CONSTRAINT `fk_cliente_suscripcion`\n" +
                "    FOREIGN KEY (`clienteID`)\n" +
                "    REFERENCES `javeparking`.`cliente` (`id`)\n" + // Referencia a la tabla cliente
                "    ON DELETE NO ACTION\n" +
                "    ON UPDATE NO ACTION);");

        stmt.execute("CREATE TABLE `javeparking`.`pagoReserva` (\n" +
                "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                "  `valor` DECIMAL NULL,\n" +
                "  `reservaID` INT NULL,\n" +
                "  `fechaPago` DATETIME NOT NULL,\n" +
                "  `metodoPago` VARCHAR(100) NOT NULL,\n" + // Nueva columna metodoPago
                "  PRIMARY KEY (`id`),\n" +
                "  INDEX `reservaID_idx` (`reservaID` ASC) VISIBLE,\n" +
                "  CONSTRAINT `reservaID`\n" +
                "    FOREIGN KEY (`reservaID`)\n" +
                "    REFERENCES `javeparking`.`reserva` (`id`)\n" +
                "    ON DELETE NO ACTION\n" +
                "    ON UPDATE NO ACTION,\n" +
                "  CONSTRAINT `check_metodoPago`\n" + // Restricción CHECK
                "    CHECK (`metodoPago` IN ('Online', 'Presencial'))\n" +
                ");");

        administradorRepository.agregarAdministrador(connection,new Administrador("10", "Luis", "Ramos", PasswordService.hashPassword("1234")));

        clienteRepository.agregarCliente(connection, new Cliente("30", "Emily", "Ramos" , 'n',PasswordService.hashPassword("1234")));
        clienteRepository.agregarCliente(connection, new Cliente("40", "Tran", "Esposito", 'a', PasswordService.hashPassword("1234")));
        clienteRepository.agregarCliente(connection, new Cliente("50", "Maria", "Menethil", 'e', PasswordService.hashPassword("1234")));

        empleadoRepository.agregarEmpleado(connection, new Empleado("20", "Simba", "Gonzales", PasswordService.hashPassword("1234")));

        stmt.execute("INSERT INTO `javeparking`.`parqueadero` (`id`, `TarifaEstandar`) VALUES ('1', 15.50);\n");

        stmt.execute("INSERT INTO `javeparking`.`puesto` (`tamano`, `disponibilidad`, `parqueaderoID`) VALUES ('g', b'0', b'1');");
        stmt.execute("INSERT INTO `javeparking`.`puesto` (`tamano`, `disponibilidad`, `parqueaderoID`) VALUES ('g', b'0', b'1');");
        stmt.execute("INSERT INTO `javeparking`.`puesto` (`tamano`, `disponibilidad`, `parqueaderoID`) VALUES ('g', b'0', b'1');");
        stmt.execute("INSERT INTO `javeparking`.`puesto` (`tamano`, `disponibilidad`, `parqueaderoID`) VALUES ('m', b'0', b'1');");
        stmt.execute("INSERT INTO `javeparking`.`puesto` (`tamano`, `disponibilidad`, `parqueaderoID`) VALUES ('m', b'0', b'1');");
        stmt.execute("INSERT INTO `javeparking`.`puesto` (`tamano`, `disponibilidad`, `parqueaderoID`) VALUES ('m', b'0', b'1');");
        stmt.execute("INSERT INTO `javeparking`.`puesto` (`tamano`, `disponibilidad`, `parqueaderoID`) VALUES ('p', b'0', b'1');");
        stmt.execute("INSERT INTO `javeparking`.`puesto` (`tamano`, `disponibilidad`, `parqueaderoID`) VALUES ('p', b'0', b'1');");
        stmt.execute("INSERT INTO `javeparking`.`puesto` (`tamano`, `disponibilidad`, `parqueaderoID`) VALUES ('p', b'0', b'1');");



        connection.close();

    }

}
