package com.asesinosdesoftware.javeparking.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

/**
 * Esta clase se encarga de realizar las operaciones necesarias para la conexión y la incialización de la base de datos.
 */
public class JDBCService {

    private ResourceBundle reader = null;
    private static final String FILENAME = "dbconfig";

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
    public void inicializarTablas(Connection connection) throws SQLException {

        Statement stmt = connection.createStatement();
        stmt.execute("DROP TABLE IF EXISTS `javeparking`.`administrador`;");
        stmt.execute("DROP TABLE IF EXISTS pago");
        stmt.execute("DROP TABLE IF EXISTS empleado");
        stmt.execute("DROP TABLE IF EXISTS reserva");
        stmt.execute("DROP TABLE IF EXISTS puesto");
        stmt.execute("DROP TABLE IF EXISTS parqueadero");
        stmt.execute("DROP TABLE IF EXISTS vehiculo");
        stmt.execute("DROP TABLE IF EXISTS cliente");


        stmt.execute("CREATE TABLE `javeparking`.`administrador` (\n" +
                "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                "  `cedula` VARCHAR(20) NULL,\n" +
                "  `nombre` VARCHAR(45) NULL,\n" +
                "  `apellido` VARCHAR(45) NULL,\n" +
                "  PRIMARY KEY (`id`),\n" +
                "  UNIQUE INDEX `cedula_UNIQUE` (`cedula` ASC) VISIBLE)");



        stmt.execute("CREATE TABLE `javeparking`.`cliente` (\n" +
                "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                "  `cedula` VARCHAR(20) NULL,\n" +
                "  `nombre` VARCHAR(45) NULL,\n" +
                "  `apellido` VARCHAR(45) NULL,\n" +
                "  `universidad` VARCHAR(1) NULL,\n" +
                "  PRIMARY KEY (`id`),\n" +
                "  UNIQUE INDEX `cedula_UNIQUE` (`cedula` ASC) VISIBLE);");



        stmt.execute("CREATE TABLE `javeparking`.`empleado` (\n" +
                "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                "  `cedula` VARCHAR(20) NULL,\n" +
                "  `nombre` VARCHAR(45) NULL,\n" +
                "  `apellido` VARCHAR(45) NULL,\n" +
                "  PRIMARY KEY (`id`),\n" +
                "  UNIQUE INDEX `cedula_UNIQUE` (`cedula` ASC) VISIBLE);\n");



        stmt.execute("CREATE TABLE `javeparking`.`parqueadero` (\n" +
                "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
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
                "  `fecha` VARCHAR(45) NULL,\n" +
                "  `horaEntrada` VARCHAR(45) NULL,\n" +
                "  `horaSalida` VARCHAR(45) NULL,\n" +
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



        stmt.execute("CREATE TABLE `javeparking`.`pago` (\n" +
                "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                "  `valor` DECIMAL NULL,\n" +
                "  `reservaID` INT NULL,\n" +
                "  PRIMARY KEY (`id`),\n" +
                "  INDEX `reservaID_idx` (`reservaID` ASC) VISIBLE,\n" +
                "  CONSTRAINT `reservaID`\n" +
                "    FOREIGN KEY (`reservaID`)\n" +
                "    REFERENCES `javeparking`.`reserva` (`id`)\n" +
                "    ON DELETE NO ACTION\n" +
                "    ON UPDATE NO ACTION);");

        stmt.execute("INSERT INTO `javeparking`.`administrador` (`cedula`, `nombre`, `apellido`) VALUES ('10', 'Luis', 'Ramos');\n");

        stmt.execute("INSERT INTO `javeparking`.`cliente` (`cedula`, `nombre`, `apellido`) VALUES ( '30 ', 'Emily', 'Ramos');\n");
        stmt.execute("INSERT INTO `javeparking`.`cliente` (`cedula`, `nombre`, `apellido`, `universidad`) VALUES ( '40', 'Tran', 'Esposito', 'A');\n");
        stmt.execute("INSERT INTO `javeparking`.`cliente` (`cedula`, `nombre`, `apellido`, `universidad`) VALUES ('50', 'Maria', 'Menethil', 'E');\n");

        stmt.execute("INSERT INTO `javeparking`.`empleado` ( `cedula`, `nombre`, `apellido`) VALUES ( '20', 'Simba', 'Gonzales');\n");

        stmt.execute("INSERT INTO `javeparking`.`parqueadero` (`id`) VALUES ('1');\n");

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
