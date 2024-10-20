import com.asesinosdesoftware.javeparking.exceptions.RepositoryException;
import com.asesinosdesoftware.javeparking.init.JDBCInitializer;
import com.asesinosdesoftware.javeparking.persistencia.DBConnectionManager;
import com.asesinosdesoftware.javeparking.persistencia.IDBConnectionManager;
import com.asesinosdesoftware.javeparking.repository.AdministradorRepository;
import com.asesinosdesoftware.javeparking.repository.ClienteRepository;
import com.asesinosdesoftware.javeparking.repository.EmpleadoRepository;
import com.asesinosdesoftware.javeparking.services.InicioDeSesionService;
import org.junit.jupiter.api.BeforeEach;

import java.sql.Connection;
import java.sql.SQLException;

public class InicioDeSesionTest {

    InicioDeSesionService inicioDeSesionService;

    @BeforeEach
    void setUp() throws SQLException, RepositoryException {
        IDBConnectionManager dbConnectionManager = new DBConnectionManager();
        JDBCInitializer jdbcInitializer = new JDBCInitializer(dbConnectionManager,new AdministradorRepository(),new ClienteRepository(), new EmpleadoRepository());
        inicioDeSesionService = new InicioDeSesionService();
        Connection con = dbConnectionManager.getConnection();
        jdbcInitializer.inicializarTablas();
    }
}
