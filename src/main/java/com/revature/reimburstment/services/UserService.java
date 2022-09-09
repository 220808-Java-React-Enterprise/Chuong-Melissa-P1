package com.revature.reimburstment.services;

import com.revature.reimburstment.daos.RoleDAO;
import com.revature.reimburstment.daos.UserDAO;
import com.revature.reimburstment.dtos.requests.LoginRequest;
import com.revature.reimburstment.dtos.requests.NewUserRequest;
import com.revature.reimburstment.dtos.responses.Principal;
import com.revature.reimburstment.models.User;
import com.revature.reimburstment.models.UserRole;
import com.revature.reimburstment.utils.custom_exceptions.AuthenticationException;
import com.revature.reimburstment.utils.custom_exceptions.InvalidRequestException;
import com.revature.reimburstment.utils.custom_exceptions.InvalidTypeException;
import com.revature.reimburstment.utils.custom_exceptions.ResourceConflictException;

import java.util.List;
import java.util.UUID;

public class UserService {
    private final UserDAO userDAO;

    private final RoleDAO roleDAO;



    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
        this.roleDAO = new RoleDAO();
    }

    public static void validateUsername(String password) {
        if(!password.matches("^[A-Za-z\\d@$!%*?&]{5,30}$"))
            throw new InvalidRequestException("Password must be between 5 and 30 alphanumeric or special characters.");
    }

    // register a new user to the system. validation on username and password
    public User register(NewUserRequest request) {
        User user = null;

        if (isValidUsername(request.getUsername())) {
            if (!isDuplicateUsername(request.getUsername())) {
                if (isValidPassword(request.getPassword1())) {
                    if (isSamePassword(request.getPassword1(), request.getPassword2())) {
                        user = new User();
                        user.setUser_id(UUID.randomUUID().toString());
                        user.setUsername(request.getUsername());
                        user.setEmail(request.getEmail());
                        user.setPassword(request.getPassword1());
                        user.setGiven_name(request.getGiven_name());
                        user.setSurname(request.getSurname());
                        user.setIs_active(request.getIs_active());
                        user.setRole_id(request.getRole_id());
                        userDAO.save(user);
                    }
                }
            }
        }

        return user;
    }

    public List<User> getAllUsers() {
        List<User> usersList = userDAO.getAll();

        return usersList;
    }

    public Principal login(LoginRequest request) {
        User user = userDAO.getUserByUsernameAndPassword(request.getUsername(), request.getPassword());
        if (user == null) throw new AuthenticationException("\nIncorrect username or password :(");
        if (!user.is_active()) throw new AuthenticationException("Account is not active");
        return new Principal(user.getUser_id(), user.getUsername(), user.getRole_id());
    }

    public static void validatePassword(String password) throws InvalidRequestException {
        if(!password.matches("^[A-Za-z\\d@$!%*?&]{5,30}$"))
            throw new InvalidRequestException("Password must be between 5 and 30 alphanumeric or special characters.");
    }

    public User getUserById(String id) {
        return userDAO.getById(id);
    }

    public boolean isValidUsername(String username) {
        if (!username.matches("^(?=[a-zA-Z0-9._]{8,20}$)(?!.*[_.]{2})[^_.].*[^_.]$")) throw new InvalidRequestException("Invalid username! username is 8-20 characters long. no _ or . at the beginning. no __ or _. or ._ or .. inside");
        return true;
    }

    public boolean isValidPassword(String password) {
        if (!password.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")) throw new InvalidRequestException("Invalid password! Minimum eight characters, at least one letter and one number");
        return true;
    }

    public  boolean isDuplicateUsername(String username) {
        if (userDAO.getUsername(username) != null) throw new ResourceConflictException("Sorry, " + username + " already been taken :(");
        return false;
    }

    public boolean isSamePassword(String password, String password2) {
        if (!password.equals(password2)) throw new InvalidRequestException("Password do not match :(");
        return true;
    }

    public void update(User user) {
        userDAO.update(user);
    }

    public void delete(String user_id) {
        userDAO.delete(user_id);
    }
}
