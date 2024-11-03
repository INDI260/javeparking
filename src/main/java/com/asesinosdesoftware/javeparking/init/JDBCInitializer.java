package com.asesinosdesoftware.javeparking.init;

import java.sql.SQLException;
import java.sql.Statement;

import com.asesinosdesoftware.javeparking.entities.Administrador;
import com.asesinosdesoftware.javeparking.entities.Cliente;
import com.asesinosdesoftware.javeparking.entities.Empleado;
import com.asesinosdesoftware.javeparking.exceptions.RepositoryException;
import com.asesinosdesoftware.javeparking.persistencia.DBConnectionManager;
import com.asesinosdesoftware.javeparking.persistencia.IDBConnectionManager;
import com.asesinosdesoftware.javeparking.repository.AdministradorRepository;
import com.asesinosdesoftware.javeparking.repository.ClienteRepository;
import com.asesinosdesoftware.javeparking.repository.EmpleadoRepository;
import com.asesinosdesoftware.javeparking.services.PasswordService;

/**
 * Esta clase se encarga de realizar las operaciones necesarias para la conexión y la incialización de la base de datos.
 */
public class JDBCInitializer {

    private IDBConnectionManager dbConnectionManager;
    private AdministradorRepository administradorRepository;
    private ClienteRepository clienteRepository;
    private EmpleadoRepository empleadoRepository;

    /**
     * Método constructor por parametros de JDBCInitializer
     * @param dbConnectionManager
     * @param administradorRepository
     * @param clienteRepository
     * @param empleadoRepositoy
     */
    public JDBCInitializer(IDBConnectionManager dbConnectionManager, AdministradorRepository administradorRepository, ClienteRepository clienteRepository, EmpleadoRepository empleadoRepositoy) {
        this.dbConnectionManager = dbConnectionManager;
        this.administradorRepository = administradorRepository;
        this.clienteRepository = clienteRepository;
        this.empleadoRepository = empleadoRepositoy;
    }

    /**
     * Método que crea las tablas en la base de datos y las incializa con algunos datos para realizar pruebas
     * @throws SQLException
     */
    public void inicializarTablas() throws SQLException, RepositoryException {

        Statement stmt = dbConnectionManager.getConnection().createStatement();
        stmt.execute("DROP TABLE IF EXISTS `javeparking`.`administrador`;");
        stmt.execute("DROP TABLE IF EXISTS pagoReserva");
        stmt.execute("DROP TABLE IF EXISTS pagoSuscripcion");
        stmt.execute("DROP TABLE IF EXISTS empleado");
        stmt.execute("DROP TABLE IF EXISTS pago");
        stmt.execute("DROP TABLE IF EXISTS reserva");
        stmt.execute("DROP TABLE IF EXISTS puesto");
        stmt.execute("DROP TABLE IF EXISTS parqueadero");
        stmt.execute("DROP TABLE IF EXISTS suscripcion");
        stmt.execute("DROP TABLE IF EXISTS vehiculo");
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
                "  `TarifaPequeno` DECIMAL NOT NULL,\n" +
                "  `TarifaMediano` DECIMAL NOT NULL,\n" +
                "  `TarifaGrande` DECIMAL NOT NULL,\n" +
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
                "  `clienteID` INT NOT NULL,        -- Relacionada con el cliente\n" +
                "  `vehiculoID` INT NOT NULL,       -- Relacionada con el vehículo\n" +
                "  `fecha_inicio` DATETIME NULL,\n" +
                "  `fecha_fin` DATETIME NULL,\n" +
                "  `estado` VARCHAR(100) NOT NULL,\n" +
                "  PRIMARY KEY (`id`),\n" +
                "  CONSTRAINT `fk_cliente_suscripcion`\n" +
                "    FOREIGN KEY (`clienteID`)\n" +
                "    REFERENCES `javeparking`.`cliente` (`id`)\n" +
                "    ON DELETE NO ACTION\n" +
                "    ON UPDATE NO ACTION,\n" +
                "  CONSTRAINT `fk_vehiculo_suscripcion`\n" +
                "    FOREIGN KEY (`vehiculoID`)\n" +
                "    REFERENCES `javeparking`.`vehiculo` (`id`)   -- Referencia a la tabla vehículo\n" +
                "    ON DELETE NO ACTION\n" +
                "    ON UPDATE NO ACTION\n" +
                ");");

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

        stmt.execute("CREATE TABLE `javeparking`.`pagoSuscripcion` (\n" +
                "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                "  `suscripcionID` INT NOT NULL,\n" +
                "  `valor` DECIMAL(10, 2) NOT NULL,\n" +
                "  `fechaPago` DATETIME NOT NULL,\n" +
                "  `metodoPago` VARCHAR(50) NULL,\n" +
                "  PRIMARY KEY (`id`),\n" +
                "  INDEX `suscripcionID_idx` (`suscripcionID` ASC) VISIBLE,\n" +
                "  CONSTRAINT `suscripcionID`\n" +
                "    FOREIGN KEY (`suscripcionID`)\n" +
                "    REFERENCES `javeparking`.`suscripcion` (`id`)\n" +
                "    ON DELETE CASCADE\n" +
                "    ON UPDATE NO ACTION\n" +
                ");");

        administradorRepository.agregarAdministrador(new Administrador("10", "Luis", "Ramos", PasswordService.hashPassword("1234")));

        clienteRepository.agregarCliente(new Cliente("30", "Emily", "Ramos" , 'n',PasswordService.hashPassword("1234")));
        clienteRepository.agregarCliente(new Cliente("40", "Tran", "Esposito", 'a', PasswordService.hashPassword("1234")));
        clienteRepository.agregarCliente(new Cliente("50", "Maria", "Menethil", 'e', PasswordService.hashPassword("1234")));

        empleadoRepository.agregarEmpleado(new Empleado("20", "Simba", "Gonzales", PasswordService.hashPassword("1234")));

        stmt.execute("INSERT INTO `javeparking`.`parqueadero` (`TarifaPequeno`, `TarifaMediano`, `TarifaGrande`) VALUES (15.50, 18.7, 20.8 );\n");

        stmt.execute("INSERT INTO `javeparking`.`puesto` (`tamano`, `disponibilidad`, `parqueaderoID`) VALUES ('g', b'0', b'1');");
        stmt.execute("INSERT INTO `javeparking`.`puesto` (`tamano`, `disponibilidad`, `parqueaderoID`) VALUES ('g', b'0', b'1');");
        stmt.execute("INSERT INTO `javeparking`.`puesto` (`tamano`, `disponibilidad`, `parqueaderoID`) VALUES ('g', b'0', b'1');");
        stmt.execute("INSERT INTO `javeparking`.`puesto` (`tamano`, `disponibilidad`, `parqueaderoID`) VALUES ('m', b'0', b'1');");
        stmt.execute("INSERT INTO `javeparking`.`puesto` (`tamano`, `disponibilidad`, `parqueaderoID`) VALUES ('m', b'0', b'1');");
        stmt.execute("INSERT INTO `javeparking`.`puesto` (`tamano`, `disponibilidad`, `parqueaderoID`) VALUES ('m', b'0', b'1');");
        stmt.execute("INSERT INTO `javeparking`.`puesto` (`tamano`, `disponibilidad`, `parqueaderoID`) VALUES ('p', b'0', b'1');");
        stmt.execute("INSERT INTO `javeparking`.`puesto` (`tamano`, `disponibilidad`, `parqueaderoID`) VALUES ('p', b'0', b'1');");
        stmt.execute("INSERT INTO `javeparking`.`puesto` (`tamano`, `disponibilidad`, `parqueaderoID`) VALUES ('p', b'0', b'1');");



        dbConnectionManager.getConnection().close();

    }

    public static void main(String[] args) throws SQLException, RepositoryException {
        new JDBCInitializer(new DBConnectionManager(), new AdministradorRepository(), new ClienteRepository(), new EmpleadoRepository()).inicializarTablas();

    }

}
