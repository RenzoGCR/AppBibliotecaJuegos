package org.example.gestorjuegosfx.utils;

import org.example.gestorjuegosfx.user.User;
import org.example.gestorjuegosfx.user.UserDAO;

import java.util.Optional;
import java.util.logging.Logger;

public class AuthService{

    private static UserDAO userDAO;
    private Logger logger =Logger.getLogger(AuthService.class.getName());
    private static User currentUser;

    public AuthService(UserDAO userDAO){
        this.userDAO=userDAO;
    }

    public Optional<User> login(String email, String password){
        logger.info("Iniciando login");
        var user =  userDAO.findByEmailAndPassword(email,password);
        if(user.isPresent()){
            logger.info("Usuario encontrado");
            currentUser=user.get();
        }else{
            logger.info("Usuario no encontrado");
        }
        return Optional.ofNullable(currentUser);
    }

    public void logout(){
        logger.info("Realizando logout");
        currentUser=null;
    }

    public Optional<User> getCurrentUser(){
        logger.info("Current user: "+currentUser.toString());
        return Optional.ofNullable(currentUser);
    }

}
