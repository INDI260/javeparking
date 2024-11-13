package com.asesinosdesoftware.javeparking.services;
import org.springframework.security.crypto.bcrypt.BCrypt;

public class PasswordService {

    /**
     * Este método recibe un string para encriptarlo usando el algoritmo BCrypt
     * @param password: Contraseña a encriptar.
     * @return La cadena de caracteres correspondiente a la contraseña ingresada, para almacenar en la base de datos.
     */
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    /**
     * Este método determina si una contraseña ingresada corresponde con la almacenada en la base de datos
     * @param password: La contraseña ingresada por el usuario
     * @param hashedPassword: El hash de la contraseña almacenada en la base de datos.
     * @return: True si la contraseña coincide, false si no coincide.
     */
    public static boolean checkPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
}
