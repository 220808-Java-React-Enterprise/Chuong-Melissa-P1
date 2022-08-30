package com.revature.strong.services;

import com.revature.strong.daos.UserDAO;
import com.revature.strong.dtos.request.NewUserRequest;
import com.revature.strong.models.User;
import com.revature.strong.utils.custom_exceptions.InvalidUserException;

import java.util.List;

public class UserService {
    //is the API, UI talks to Services and the services talk to the DAO
    //validate anything pertaining to user
    //replicate of models
    //dependency injection global variable
    private final UserDAO userDAO;

    public UserService(UserDAO userDAO){
        this.userDAO = userDAO;
    }

    public boolean isValidUsername(String username) {
        if (!username.matches("^(?=.{8,20}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9]+(?<![_.])$")) {
            throw new InvalidUserException("\nUsername not Valid.\n*Username can only contain alphanumerics" +
                    "\n*Username must contain a lowercase letter" +
                    "\n*Username must contain a number" +
                    "\n*Username must be 8-20 characters long");
        }
        return true;
    }

    public boolean isValidPassword(String password) {
        if (!password.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")) {
            throw new InvalidUserException("\nPassword not Valid.\n*Password must have a minimum of 8 character" +
                    "\n*Password must have at least one letter" +
                    "\n*Password must have at least one number");

        }
        return true;
    }

    public User register(NewUserRequest requestuser)  {
        User user = null;

        return user;
    }

    public User login(String username, String userpassword){
        User user = userDAO.getUserByUsernameAndPassword(username, userpassword);
        if (user == null) throw new InvalidUserException("\nIncorrect username or password :(");
        return user;
    }

    public boolean isDuplicateUsername(String username) {
        if (userDAO.getUsername(username) != null) throw new InvalidUserException("\nSorry, " +
                username + " already has been taken :(");
        return false;
    }

    public boolean isSamePassword(String userpassword, String password2){
        if (!userpassword.equals(password2)) throw new InvalidUserException("\nPassword do not match :(");
        return true;
    }

    public List<User> getAllUsers(){
        return userDAO.getAll();
    }

    public User getUserByUsername(String username){
        return userDAO.getUserByUsername(username);
    }

}
