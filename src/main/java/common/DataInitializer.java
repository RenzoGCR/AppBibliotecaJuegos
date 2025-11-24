package common;

import javax.sql.DataSource;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DataInitializer {

    public static void initializeTables(){
        createUserTable(DataProvider.getDataSource());
    }

    private static void createUserTable(DataSource ds)  {

        try (Connection conn = ds.getConnection();
             Statement stmt = conn.createStatement() ) {
            var sqlFile = DataInitializer.class.getResource("/appbibliotecajuegos/data.sql").toURI();
            String sql = new String( Files.readAllBytes( Path.of(sqlFile) ) );
            stmt.execute(sql);
        } catch (IOException | URISyntaxException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
