package server.utility;

import com.google.gson.Gson;
import server.config.Config;

public class Encryption {

    /**
     * Kryptering skal kunne slås til og fra i configfilen
     * <p>
     * Fremgansmåde:
     * <p>
     * Klienten sender et krypteret Json objekt til serveren.
     * Krypteringen hos klienten medfører, at objektet ikke længere er JSON, men blot en ciffertekst.
     * Derfor skal cifferteksten parses til JSON, således at serveren kan modtage det.
     * Det modtagede JSON objekt unparses fra JSON til ciffertekst, således at det kan dekrypteres.
     * Efter objektet er dekrypteret er det igen JSON.
     * Herefter unparses objektet fra JSON igen, således at vi kan bruge objektet i serveren.
     */

    /**
     * Method responsible for encrypting/decrypting a string using our own cipher
     * @param input string that will be encrypted/decrypted
     * @return the string after XOR cypher
     */
    public String encryptDecryptXOR(String input) {

        //check if encryption is true in the Config file
        if (Config.getENCRYPTION()) {

            //Vi vælger selv værdierne til nøglen
            char[] key = {'Y', 'O', 'L', 'O'};

            //En StringBuilder er en klasse, der gør det muligt at ændre en string
            StringBuilder output = new StringBuilder();

            for (int i = 0; i < input.length(); i++) {
                output.append((char) (input.charAt(i) ^ key[i % key.length]));
            }

            return output.toString();

        }else {
            //if encryption is false in the Config file return default value
            return input;
        }

    }

}

