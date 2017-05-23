/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.security.SecureRandom;

/**
 *
 * @author iacobs
 */
public class Utility {

    protected static SecureRandom random = new SecureRandom();

    public static String generateToken() {
        long longToken = Math.abs(random.nextLong());
        return Long.toString(longToken, 200);

    }

}

