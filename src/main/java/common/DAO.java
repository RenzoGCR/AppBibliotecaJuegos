package common;

import java.util.List;
import java.util.Optional;

public interface DAO<T> {
    /* Métodos de escritura */
    Optional<T> save(T t);
    Optional<T> update(T t);
    Optional<T> delete(T t);

    /* Métodos de lectura */
    List<T> findAll();
    Optional<T> findById(Integer id);

    /*
    Historias de usuarios:

    - Quiero renombrar todos los juegos de una determinada plataforma.
            servicio.renameAllGamePlatform(platform)
            -> buscar juegos de una plataforma
            -> iterar y cambiar
    - Quiero reasignar un juego a otro usuario
            servicio.reasignUser(game, user)
            -> buscar juego
            -> buscar usuario
            -> modificar juego
    - Quiero obtener todos los juegos que salieron en un año concreto
            -> dao.findAllByYear()
    - Quiero eliminar los juegos que no tengan usuario asignado
            ->
    - Quiero listar todos los juegos de los usuarios que accedieron en las ultimas 24h a la aplicación
            -> daoUsuario.findAllByLastAccess(24h)
            -> para cada usuario, daoJuego.findAllByUser()

     */
}
