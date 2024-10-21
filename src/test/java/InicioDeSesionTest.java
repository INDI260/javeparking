import com.asesinosdesoftware.javeparking.init.JDBCInitializer;
import com.asesinosdesoftware.javeparking.services.InicioDeSesionService;
import org.junit.jupiter.api.BeforeEach;

import java.sql.Connection;
import java.sql.SQLException;

public class InicioDeSesionTest {

    InicioDeSesionService inicioDeSesionService;

    @BeforeEach
    void setUp() throws SQLException {
        JDBCInitializer jdbcInitializer = new JDBCInitializer();
        inicioDeSesionService = new InicioDeSesionService();
        Connection con = jdbcInitializer.getConnection();
    }
}
