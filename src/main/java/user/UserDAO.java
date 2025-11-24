package user;

import common.DAO;

import javax.sql.DataSource;
import java.sql.*;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class UserDAO implements DAO<User> {
    private DataSource dataSource;
    private final Logger log = Logger.getLogger(UserDAO.class.getName());

    private User mapper(ResultSet rs) throws SQLException {
        return new User(
                rs.getInt("id"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getBoolean("is_admin")
        );
    }
    public UserDAO(DataSource dataSource){
        this.dataSource = dataSource;
        log.info("Inicializando UserDAO");
    }

    @Override
    public Optional<User> save(User user) {
        return Optional.empty();
    }

    @Override
    public Optional<User> update(User user) {
        return Optional.empty();
    }

    @Override
    public Optional<User> delete(User user) {
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        return List.of();
    }

    @Override
    public Optional<User> findById(Integer id) {
        return Optional.empty();
    }

    public Optional<User> findByEmailAndPassword(String email, String password) {
        log.info("UserDAO findByEmailAndPassword");

        try(Connection conn = dataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(
                    "SELECT * FROM user WHERE email = ? AND password = ?");
        ){
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            log.info("UserDAO executeQuery("+email+","+password+")");
            if(rs.next()){
                User user = mapper(rs);
                log.info("User found!");
                log.info("User email: " + user.getEmail());
                return Optional.of(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        log.info("User not found!");
        return Optional.empty();
    }
}
