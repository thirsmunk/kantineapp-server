
package server.authentication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.google.gson.Gson;
import server.controllers.MainController;
import server.database.DBConnection;
import server.models.User;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * Created by Tobias on 17-10-2017.
 */

/**
 * This class is the endpoint for authenticating the user
 */
public class Authentication {
    private MainController mcontroller = new MainController();

    /**
     * Authenticates the user and returns a token if user exists
     *
     * @param user user object authorized by communication with the database
     * @return
     */
    public String AuthUser(User user) {
        String token = "";
        Date expDate;

            try {
                Algorithm algorithm = Algorithm.HMAC256("secret");
                long timevalue;
                timevalue = (System.currentTimeMillis() * 1000) + 20000205238L;
                expDate = new Date(timevalue);

                token = JWT.create()
                        .withClaim("username", user.getUsername())
                        .withKeyId(String.valueOf(user.getUserId()))
                        .withExpiresAt(expDate)
                        .withIssuer("YOLO")
                        .sign(algorithm);

                mcontroller.createToken(user, token);

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (JWTCreationException e) {
                e.printStackTrace();
            }

            return token;


    }

    public MainController getMcontroller() {
        return mcontroller;
    }
}
