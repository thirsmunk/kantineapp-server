package server.controllers;

import server.database.DBConnection;
import server.endpoints.RootEndpoint;
import server.endpoints.StaffEndpoint;
import server.endpoints.UserEndpoint;
import server.models.User;
import server.utility.Digester;

/**
 * Class responsible for all logic related to general operations
 * @author Group YOLO
 */

public class MainController {
    private Digester dig;
    private DBConnection db;

    /**
     * Constructor for main controller
     */
    public MainController() {
        dig = new Digester();
        db = new DBConnection();
    }

    /**
     * Method for authorizing users when logging in
     * @param user
     * @return userCheck
     */
    public User authorizeUser(User user) {
        String hashedPassword = dig.hashWithSalt(user.getPassword());
        user.setPassword(hashedPassword);
        User userCheck = db.authorizeUser(user);
        return userCheck;
    }

    /**
     * Method for deleting existing tokens when logging out
     * @param id
     * @return boolean
     */
    public boolean deleteToken(int id) {
       int result = db.deleteToken(id);

       if(result>0){
           return true;
       }
       return false;
    }

    /**
     * Method to check if a token exists in the database
     * @param token
     * @return boolean
     */
    public boolean checkTokenOwner(String token) {
        String serverToken = db.tokenExists(token);
        if(serverToken.equals(token)){
            return true;
        }
        return false;
    }

    /**
     * Method to create a new token
     * @param tokenUser
     * @param token
     */
    public void createToken(User tokenUser, String token){
        db.createToken(tokenUser, token);
    }

}