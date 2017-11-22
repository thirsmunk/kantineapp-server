package server.endpoints;

import com.google.gson.Gson;
import server.authentication.Authentication;
import server.authentication.Secured;
import server.models.User;
import server.utility.Encryption;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/start")
public class RootEndpoint {
    private Authentication auth = new Authentication();
    private Encryption encryption = new Encryption();

    /**
     * @param encryptedJSON
     * @return Response with entity as a userAsJson that includes a token to be used for authorization
     * Gives user access to endpoint methods through assigning them a token.
     */
    @POST
    @Path("/login")
    public Response login(String encryptedJSON) {
        User user;
        User loginUser;
        String decryptedJSON;

        //if encryption is true in config file
        //decrypt userAsJson from a Json object containing a encrypted Json object to contain a decrypted Json object
        decryptedJSON = encryption.encryptDecryptXOR(encryptedJSON);

        // parse json object
        user = new Gson().fromJson(decryptedJSON, User.class);

        //Logikken der tjekker, hvorvidt en bruger findes eller ej

        loginUser = auth.getMcontroller().authorizeUser(user);

        if (loginUser == null) {

            return Response
                    .status(401)
                    .type("plain/text")
                    .entity(encryption.encryptDecryptXOR("\"User not authorized\""))
                    .build();

        } else {

            loginUser.setToken(auth.AuthUser(loginUser));

            String jsonUser = new Gson().toJson(loginUser, User.class);

            //return encrypted object in json format
            return Response
                    .status(200)
                    .type("plain/text")
                    .entity(encryption.encryptDecryptXOR(jsonUser))
                    .build();
        }
    }


    /**
     * @param userAsJson
     * @return Plain text based on whether or not logout was successful.
     * Logout for users, deletes token in database (as well as all previous ones if they forgot to logout in an earlier visit).
     */
    @Secured
    @POST
    @Path("/logout")
    public Response logout(String userAsJson) {
        String decryptedJSON;
        boolean deleted;
        User userFromJson;

        decryptedJSON = encryption.encryptDecryptXOR(userAsJson);

        userFromJson = new Gson().fromJson(decryptedJSON, User.class);

        deleted = auth.getMcontroller().deleteToken(userFromJson.getUserId());

        if (deleted) {
            return Response
                    .status(200)
                    .type("plain/text")
                    .entity(encryption.encryptDecryptXOR("{\"Response:\":\"Logged out\"}"))
                    .build();
        } else {
            return Response
                    .status(500)
                    .type("plain/text")
                    .entity(encryption.encryptDecryptXOR("{\"Response:\":\"Server error, token might not exist.\"}"))
                    .build();
        }
    }
}
