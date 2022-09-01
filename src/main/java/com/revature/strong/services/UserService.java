package com.revature.strong.services;

import com.revature.strong.daos.UserDAO;
import com.revature.strong.dtos.request.NewUserRequest;
import com.revature.strong.models.User;
import com.revature.strong.utils.custom_exceptions.InvalidRequestException;
import com.revature.strong.utils.custom_exceptions.InvalidUserException;
import com.revature.strong.utils.custom_exceptions.ResourceConflictException;

import java.util.List;
import java.util.UUID;

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
            throw new InvalidRequestException("\nUsername not Valid.\n*Username can only contain alphanumerics" +
                    "\n*Username must contain a lowercase letter" +
                    "\n*Username must contain a number" +
                    "\n*Username must be 8-20 characters long");
        }
        return true;
    }

    public boolean isValidPassword(String password) {
        if (!password.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")) {
            throw new InvalidRequestException("\nPassword not Valid.\n*Password must have a minimum of 8 character" +
                    "\n*Password must have at least one letter" +
                    "\n*Password must have at least one number");

        }
        return true;
    }

    public User register(NewUserRequest request)  {
        User user = null;

        if(isValidUsername(request.getUsername())) {
            if (!isDuplicateUsername(request.getUsername())) {
                if (isValidPassword(request.getPassword1())) {
                    if (isSamePassword(request.getPassword1(), request.getPassword2())) {
                        user = new User(request.getUsername(), request.getPassword1(), UUID.randomUUID().toString(), false);
                        userDAO.save(user);
                    }
                }
            }
        }

        return user;
    }

    public User login(String username, String userpassword){
        User user = userDAO.getUserByUsernameAndPassword(username, userpassword);
        if (user == null) throw new InvalidUserException("\nIncorrect username or password :(");
        return user;
    }

    public boolean isDuplicateUsername(String username) {
        if (userDAO.getUsername(username) != null) throw new ResourceConflictException("\nSorry, " +
                username + " already has been taken :(");
        return false;
    }

    public boolean isSamePassword(String userpassword, String password2){
        if (!userpassword.equals(password2)) throw new InvalidRequestException("\nPassword do not match :(");
        return true;
    }

    public List<User> getAllUsers(){
        return userDAO.getAll();
    }

    public User getUserByUsername(String username){
        return userDAO.getUserByUsername(username);
    }

}
