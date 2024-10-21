import com.asesinosdesoftware.javeparking.entities.Sesion;
import com.asesinosdesoftware.javeparking.exceptions.InicioDeSesionException;
import com.asesinosdesoftware.javeparking.exceptions.RepositoryException;
import com.asesinosdesoftware.javeparking.init.JDBCInitializer;
import com.asesinosdesoftware.javeparking.persistencia.DBConnectionManager;
import com.asesinosdesoftware.javeparking.persistencia.IDBConnectionManager;
import com.asesinosdesoftware.javeparking.repository.AdministradorRepository;
import com.asesinosdesoftware.javeparking.repository.ClienteRepository;
import com.asesinosdesoftware.javeparking.repository.EmpleadoRepository;
import com.asesinosdesoftware.javeparking.services.InicioDeSesionService;
import com.asesinosdesoftware.javeparking.exceptions.InicioDeSesionException;
import com.asesinosdesoftware.javeparking.exceptions.RepositoryException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.Connection;
import java.sql.SQLException;

public class InicioDeSesionTest {

    InicioDeSesionService inicioDeSesionService;
    IDBConnectionManager dbConnectionManager = new DBConnectionManager();

    @BeforeEach
    void setUp() throws SQLException, RepositoryException {
        JDBCInitializer jdbcInitializer = new JDBCInitializer(dbConnectionManager,new AdministradorRepository(),new ClienteRepository(), new EmpleadoRepository());
        inicioDeSesionService = new InicioDeSesionService();
        jdbcInitializer.inicializarTablas();
    }

    @Test
    void InicioDeSesionAdmin() throws SQLException, InicioDeSesionException {
        inicioDeSesionService.InicioDeSesion(dbConnectionManager.getConnection(), "10", "1234");
        assertEquals('a', Sesion.getTipo());
    }
}
