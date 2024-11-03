--CREATE DATABASE  IF NOT EXISTS javeparking /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
--USE javeparking;
-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: localhost    Database: javeparking
-- ------------------------------------------------------
-- Server version	8.0.39

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table administrador
--

DROP TABLE IF EXISTS administrador;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE administrador (
  id int NOT NULL AUTO_INCREMENT,
  cedula varchar(20) NOT NULL,
  nombre varchar(45) DEFAULT NULL,
  apellido varchar(45) DEFAULT NULL,
  hash varchar(100) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY cedula_admin_UNIQUE (cedula)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table administrador
--

--LOCK TABLES administrador WRITE;
/*!40000 ALTER TABLE administrador DISABLE KEYS */;
INSERT INTO administrador VALUES (1,'10','Luis','Ramos','$2a$10$Pqa7UsfTJ6OymjVKnocANO.3JYG/n2t1FEaVtMY.dTsVAZGGHTrY.');
/*!40000 ALTER TABLE administrador ENABLE KEYS */;
--UNLOCK TABLES;

--
-- Table structure for table cliente
--

DROP TABLE IF EXISTS cliente;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE cliente (
  id int NOT NULL AUTO_INCREMENT,
  cedula varchar(20) NOT NULL,
  nombre varchar(45) DEFAULT NULL,
  apellido varchar(45) DEFAULT NULL,
  universidad varchar(1) DEFAULT NULL,
  hash varchar(100) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY cedula_cliente_UNIQUE (cedula)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table cliente
--

--LOCK TABLES cliente WRITE;
/*!40000 ALTER TABLE cliente DISABLE KEYS */;
INSERT INTO cliente VALUES (1,'30','Emily','Ramos','n','$2a$10$TLbzW5kwgc354wlTo6XWAuLknBx4EqMAOzKzzAU1HUtg7BiEBbjJS'),(2,'40','Tran','Esposito','a','$2a$10$/AwbCPDoJ5aTjW87X7XwQuyRlxY09fPgPjxKDZeNCCbxpzgDXD/96'),(3,'50','Maria','Menethil','e','$2a$10$fPRqtz8UTVuiVDkgOIdVzOYIkrsn3tBNSvvaVB7bjchuHRRdd8H.m');
/*!40000 ALTER TABLE cliente ENABLE KEYS */;
--UNLOCK TABLES;

--
-- Table structure for table empleado
--

DROP TABLE IF EXISTS empleado;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE empleado (
  id int NOT NULL AUTO_INCREMENT,
  cedula varchar(20) NOT NULL,
  nombre varchar(45) DEFAULT NULL,
  apellido varchar(45) DEFAULT NULL,
  hash varchar(100) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY cedula_empleado_UNIQUE (cedula)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table empleado
--

--LOCK TABLES empleado WRITE;
/*!40000 ALTER TABLE empleado DISABLE KEYS */;
INSERT INTO empleado VALUES (1,'20','Simba','Gonzales','$2a$10$AnfaTGHCE4RSXkUYd7z69e09kd6ghGtOu0FKouU7Cq/dadoSI3Pxq');
/*!40000 ALTER TABLE empleado ENABLE KEYS */;
--UNLOCK TABLES;

--
-- Table structure for table pagoreserva
--

--
-- Table structure for table parqueadero
--

DROP TABLE IF EXISTS parqueadero;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE parqueadero (
  id int NOT NULL AUTO_INCREMENT,
  TarifaPequeno decimal(10,0) NOT NULL,
  TarifaMediano decimal(10,0) NOT NULL,
  TarifaGrande decimal(10,0) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table parqueadero
--

--LOCK TABLES parqueadero WRITE;
/*!40000 ALTER TABLE parqueadero DISABLE KEYS */;
INSERT INTO parqueadero VALUES (1,16,19,21);
/*!40000 ALTER TABLE parqueadero ENABLE KEYS */;
--UNLOCK TABLES;

--
-- Table structure for table puesto
--

DROP TABLE IF EXISTS puesto;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE puesto (
  id int NOT NULL AUTO_INCREMENT,
  tamano varchar(1) DEFAULT NULL,
  disponibilidad bit(1) DEFAULT NULL,
  parqueaderoID int DEFAULT NULL,
  PRIMARY KEY (id),
  KEY parqueaderoID_idx (parqueaderoID),
  CONSTRAINT parqueaderoID FOREIGN KEY (parqueaderoID) REFERENCES parqueadero (id)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table puesto
--

--LOCK TABLES puesto WRITE;
/*!40000 ALTER TABLE puesto DISABLE KEYS */;
INSERT INTO puesto VALUES (1,'g','0',1),(2,'g','0',1),(3,'g','0',1),(4,'m','0',1),(5,'m','0',1),(6,'m','0',1),(7,'p','0',1),(8,'p','0',1),(9,'p','0',1);
/*!40000 ALTER TABLE puesto ENABLE KEYS */;
--UNLOCK TABLES;

--
-- Table structure for table reserva
--

DROP TABLE IF EXISTS vehiculo;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE vehiculo (
                          id int NOT NULL AUTO_INCREMENT,
                          placa varchar(10) DEFAULT NULL,
                          tamano varchar(1) DEFAULT NULL,
                          tipo varchar(45) DEFAULT NULL,
                          clienteID int DEFAULT NULL,
                          PRIMARY KEY (id),
                          UNIQUE KEY placa_UNIQUE (placa),
                          KEY clienteID_idx (clienteID),
                          CONSTRAINT clienteID FOREIGN KEY (clienteID) REFERENCES cliente (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table vehiculo
--

--LOCK TABLES vehiculo WRITE;
/*!40000 ALTER TABLE vehiculo DISABLE KEYS */;
/*!40000 ALTER TABLE vehiculo ENABLE KEYS */;
--UNLOCK TABLES;


DROP TABLE IF EXISTS reserva;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE reserva (
  id int NOT NULL AUTO_INCREMENT,
  horaEntrada datetime NOT NULL,
  horaSalida datetime DEFAULT NULL,
  vehiculoID int DEFAULT NULL,
  puestoID int DEFAULT NULL,
  PRIMARY KEY (id),
  KEY vehiculoID_idx (vehiculoID),
  KEY puestoId_idx (puestoID),
  CONSTRAINT puestoId FOREIGN KEY (puestoID) REFERENCES puesto (id),
  CONSTRAINT vehiculoID FOREIGN KEY (vehiculoID) REFERENCES vehiculo (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table reserva
--

--LOCK TABLES reserva WRITE;
/*!40000 ALTER TABLE reserva DISABLE KEYS */;
/*!40000 ALTER TABLE reserva ENABLE KEYS */;
--UNLOCK TABLES;

--
-- Table structure for table suscripcion
--

DROP TABLE IF EXISTS suscripcion;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE suscripcion (
  id int NOT NULL AUTO_INCREMENT,
  clienteID int NOT NULL,
  vehiculoID int NOT NULL,
  fecha_inicio datetime DEFAULT NULL,
  fecha_fin datetime DEFAULT NULL,
  estado varchar(100) NOT NULL,
  PRIMARY KEY (id),
  KEY fk_cliente_suscripcion (clienteID),
  KEY fk_vehiculo_suscripcion (vehiculoID),
  CONSTRAINT fk_cliente_suscripcion FOREIGN KEY (clienteID) REFERENCES cliente (id),
  CONSTRAINT fk_vehiculo_suscripcion FOREIGN KEY (vehiculoID) REFERENCES vehiculo (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table suscripcion
--

--LOCK TABLES suscripcion WRITE;
/*!40000 ALTER TABLE suscripcion DISABLE KEYS */;
/*!40000 ALTER TABLE suscripcion ENABLE KEYS */;
--UNLOCK TABLES;

--
-- Table structure for table vehiculo
--


DROP TABLE IF EXISTS pagoreserva;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE pagoreserva (
                             id int NOT NULL AUTO_INCREMENT,
                             valor decimal(10,0) DEFAULT NULL,
                             reservaID int DEFAULT NULL,
                             fechaPago TIMESTAMP NOT NULL,
                             metodoPago varchar(100) NOT NULL,
                             PRIMARY KEY (id),
                             KEY reservaID_idx (reservaID),
                             CONSTRAINT reservaID FOREIGN KEY (reservaID) REFERENCES reserva (id),
                             CONSTRAINT check_metodoPago CHECK ((metodoPago in ('Online','Presencial')))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table pagoreserva
--

--LOCK TABLES pagoreserva WRITE;
/*!40000 ALTER TABLE pagoreserva DISABLE KEYS */;
/*!40000 ALTER TABLE pagoreserva ENABLE KEYS */;
--UNLOCK TABLES;

--
-- Table structure for table pagosuscripcion
--

DROP TABLE IF EXISTS pagosuscripcion;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE pagosuscripcion (
                                 id int NOT NULL AUTO_INCREMENT,
                                 suscripcionID int NOT NULL,
                                 valor decimal(10,2) NOT NULL,
                                 fechaPago TIMESTAMP NOT NULL,
                                 metodoPago varchar(50) DEFAULT NULL,
                                 PRIMARY KEY (id),
                                 KEY suscripcionID_idx (suscripcionID),
                                 CONSTRAINT suscripcionID FOREIGN KEY (suscripcionID) REFERENCES suscripcion (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table pagosuscripcion
--

--LOCK TABLES pagosuscripcion WRITE;
/*!40000 ALTER TABLE pagosuscripcion DISABLE KEYS */;
/*!40000 ALTER TABLE pagosuscripcion ENABLE KEYS */;
--UNLOCK TABLES;

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-11-02 14:25:25
