package common;

import com.mysql.cj.jdbc.MysqlDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;

public class DataProvider {
    private static DataSource dataSource;
    private DataProvider(){}

    public static DataSource getDataSource(){
        if(dataSource == null){
            var ds = new MysqlDataSource();
            ds.setURL("jdbc:mysql://localhost:3307/juegosPc");
            ds.setUser("root");
            ds.setPassword("root");
            try{
               ds.setAllowMultiQueries(true);
            }catch(SQLException e){
                throw new RuntimeException(e);
            }
            dataSource = ds;
            DataInitializer.initializeTables();
        }
        return dataSource;
    }
}
