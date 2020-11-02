//imports
import java.util.Scanner;
import java.util.ArrayList;
import java.io.*; 

class WrongCheck{

    public static void main(String[]args){
        //scan input
        ArrayList<String> emails = new ArrayList<String>(); 
        System.out.println("enter email");
        Scanner sc = new Scanner(System.in);
        while(sc.hasNextLine()){
            emails.add(sc.nextLine()); 
        }
            
        //check parameters
        emails.forEach((e)->CheckParam(e));  
        
    }
    
    public static void CheckParam(String email){

        //transform email into unified params
        email = email.toLowerCase(); 
        email = email.replace("_at_", "@"); 
        email = email.replace("_dot_", "."); 

        //check for @ symbol 
        if(!email.contains("@")){
            System.out.println(email + " <- Missing @ symbol");
            return; 
        }

        //check for "."
        if(!email.contains(".")){
            System.out.println(email + " <- Missing '.' symbol");
            return; 
        }

	//checks for a valid ip domain  extension
        int i = email.indexOf("@");
        char ipCheck = email.charAt(i+1);  
        if(ipCheck == '['  && email.contains("]")){
           //email is working. 
	       System.out.println(email); 
	    return; 

	}
	
        //checks for a valid web domain extension 
        if(!email.contains("co.nz") && !email.contains("com.au") && !email.contains("co.ca")
           && !email.contains("com") && !email.contains("co.us") && !email.contains("co.uk")){
            System.out.println(email + " <- Invalid extension");
            return; 
        }

        //check for valid mailbox name
        if(email.substring(0,1).contains("@")){
            System.out.println(email + " <- Invalid mailbox name");
            return; 
        }

        //check for valid domain name
        char domCheck = email.charAt(i+1); 
        if(domCheck == '.'){
           //invalid domain  
           System.out.println(email + " <- Invalid domain name"); 
           return;
        }

        //email is fully working 
        System.out.println(email); 
    }
}
