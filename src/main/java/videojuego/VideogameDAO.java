package videojuego;

import common.DAO;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VideogameDAO implements DAO<Videogame> {
    private DataSource dataSource;
    private Videogame mapper(ResultSet rs) throws SQLException {
        Videogame videoGame = new Videogame();
        videoGame.setId(rs.getInt("id"));
        videoGame.setTitle(rs.getString("title"));
        videoGame.setPlatform(rs.getString("platform"));
        videoGame.setYear(rs.getInt("year"));
        videoGame.setDescription(rs.getString("description"));
        videoGame.setUser_id(rs.getInt("user_id"));
        videoGame.setImage_url(rs.getString("image_url"));
        return videoGame;
    }

    public VideogameDAO(DataSource dataProvider) {
        this.dataSource = dataProvider;
    }

    @Override
    public Optional<Videogame> save(Videogame videogame) {
        String sql = "INSERT INTO games VALUES (0,?,?,?,?,?,?)";
        try(Connection conn = dataSource.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
        ){
            pst.setString(1, videogame.getTitle());
            pst.setString(2, videogame.getPlatform());
            pst.setInt(3, videogame.getYear());
            pst.setString(4, videogame.getDescription());
            pst.setInt(5, videogame.getUser_id());
            pst.setString(6, videogame.getImage_url());

            Integer res = pst.executeUpdate();
            if(res>0){
                ResultSet keys = pst.getGeneratedKeys();
                keys.next();
                videogame.setId(keys.getInt(1));
                return Optional.of(videogame);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Videogame> update(Videogame videogame) {
        return Optional.empty();
    }

    @Override
    public Optional<Videogame> delete(Videogame videogame) {
        return Optional.empty();
    }

    @Override
    public List<Videogame> findAll() {
        return List.of();
    }

    @Override
    public Optional<Videogame> findById(Integer id) {
        return Optional.empty();
    }

    public List<Videogame> findAllByUserId(Integer id) {

        ArrayList<Videogame> listado = new ArrayList<>();
        String sql = "SELECT * FROM games WHERE user_id = ?";
        try(Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
        ){
            stmt.setInt(1, id);
            ResultSet resultado = stmt.executeQuery();
            while(resultado.next()){
                listado.add( mapper(resultado) );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return listado;
    }
}
