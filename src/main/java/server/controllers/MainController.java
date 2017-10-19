package server.controllers;

import server.database.DBConnection;
import server.endpoints.RootEndpoint;
import server.endpoints.StaffEndpoint;
import server.endpoints.UserEndpoint;
import server.models.User;

public class MainController {

    private User currentUser;
    private DBConnection dbConnection;
    private StaffController staffController;
    private UserController userController;
    private UserEndpoint userEndpoint;
    private StaffEndpoint staffEndpoint;
    private RootEndpoint rootEndpoint;


    public MainController() {
        currentUser = null;
        dbConnection = new DBConnection();
        staffController = new StaffController();
        userController = new UserController();
        userEndpoint = new UserEndpoint();
        staffEndpoint = new StaffEndpoint();
        rootEndpoint = new RootEndpoint();

    }


    public void login(String username, String password) {

        User user = new User();

        user.setUsername(username);
        user.setPassword(password);
        //Logikken der tjekker, hvorvidt en bruger findes eller ej

        try {

         //   currentUser = dbConnection.getUser(username, password);

            if (currentUser == null) {
                //Findes ikke
            } else if (currentUser.isPersonel()) {
           //     staffController.loadUser(currentUser);
            } else {
            //    userController.loadUser(currentUser);
            }


            if (currentUser == null) {
                //Findes ikke
            } else if (!currentUser.isPersonel()) {
                //Log-in as user
                userController.setCurrentUser(user);
            } else {
                //Log-in as staff
            }

        } catch (Exception e) {
            e.printStackTrace();

        }


    }
}

