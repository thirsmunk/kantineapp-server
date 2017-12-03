package server.controllers;

import server.database.DBConnection;
import server.models.*;
import server.utility.Digester;

import java.util.ArrayList;

/**
 * Class responsible for all logic related to user operations
 * @author Group YOLO
 */
public class UserController {
    private Digester dig;
    private DBConnection db;

    /**
     * Constructor for the user controller
     */
    public UserController() {
        this.dig = new Digester();
        this.db = new DBConnection();
    }

    /**
     * Method to add a user to the database
     * @param user
     * @return boolean
     */
    public boolean addUser(User user) {

        //sets a users password to a hashed with salt password and returns a boolean value if a user has been created

        String hashedPassword = dig.hashWithSalt(user.getPassword());

        user.setPassword(hashedPassword);
        int result = db.addUser(user);

        if(result>0){
            return true;
        }

        return false;
    }

    /**
     * Method to add a new order to the database
     * @param id
     * @param items
     * @return boolean
     */
    public boolean addOrder(int id, ArrayList<Item> items) {
        int result = db.addOrder(id, items);

        if(result == 1){
            return true;
        }
        return false;
    }

    /**
     * Method to find orders specific to a user
     * @param userId
     * @return orders
     */
    public ArrayList<Order> findOrderById(int userId) {
        ArrayList<Order> orders = db.findOrderById(userId);
        return orders;
    }

    /**
     * Method to get all items from the database
     * @return items
     */
    public ArrayList<Item> getItems() {
        ArrayList<Item> items = db.getItems();
        return items;
    }

}
