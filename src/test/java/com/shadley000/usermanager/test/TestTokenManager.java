package com.shadley000.usermanager.test;

import com.shadley000.usermanager.tokenmanager.TokenManager;

public class TestTokenManager {

    public static void main(String args[])
    {
        TokenManager tokenManager = TokenManager.getTokenManager();
        {
            Long token = tokenManager.getToken("bogus", "password");

            if(token == null) {
                System.out.println("bogus user test passed");
            }
            else {
                System.out.println("bogus user test failed");
            }
        }


        {
            Long token = tokenManager.getToken("shadley000", "password");
            if(token!=null) {
                System.out.println("shadley000 token ="+token);

                Long userId = tokenManager.getUserId(token);
                if(userId!=null)
                {
                    System.out.println("shadley000 userid "+ userId);
                    System.out.println("           timeleft "+ tokenManager.getTimeLeft(token));
                }else{
                    System.out.println("unable to find user");
                }
            }
            else
            {
                System.out.println("token creation failed");
            }
        }
    }
}
