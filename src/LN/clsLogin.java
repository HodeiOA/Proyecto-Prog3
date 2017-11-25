package LN;

public class clsLogin
{
		 
	    public static boolean authenticate(String username, String password) 
	    {
	        // hardcoded username and password
	        if (username.equals("bob") && password.equals("secret")) 
	        {
	            return true;
	        }
	        return false;
	    }
}
