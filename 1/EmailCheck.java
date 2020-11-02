//imports
import java.util.Scanner;
import java.util.ArrayList;
import java.io.*; 
import java.util.regex.*;
import java.util.*;

class EmailCheck{
    /** main method **/ 
    public static void main(String[]args){
        //scan input
        ArrayList<String> emails = new ArrayList<String>(); 
        Scanner sc = new Scanner(System.in);
        while(sc.hasNextLine()){
            String s = sc.nextLine(); 
            if(!s.isEmpty()){
                emails.add(s.trim()); 
            }
        }       
        //check parameters
        emails.forEach((email)->checkParams(email));
    }

    /** Overhead method to check for individual rules **/ 
    public static void checkParams(String email){
        String orgEmail = email; 
        //standerdise the email to lowercase 
        email = email.toLowerCase(); 
        //checks to replace _at_ if there is no @ symbol 
        if(email.contains("_at_") && !email.contains("@")){
            email = check_at_(email); 
        }
        //replace the _dot_
        email = email.replaceAll("_dot_","."); 
        //checks for IPv4 type email 

        //check for invalid @ 
        if(!email.contains("@")){
            System.out.println(orgEmail + " <- Missing @ symbol");
            return;
        }else if(email.charAt(0)== '@'){
            System.out.println(orgEmail +" <- Invalid mailbox name"); 
            return; 
        }

        if(!consecutives(email)){
                    System.out.println(orgEmail + " <- Invalid '.-_' characters");
                    return; 
        } else if(email.contains("@") && email.charAt(email.indexOf("@")+1) == '['){
            //checks to see if its a valid IP. 
            System.out.println(validateIP(email)); 

        } else{
            //check for a normal valid email. 
            String regex = "^[a-z0-9+_.-]+(@|_at_)+[a-z0-9]+(.|_dot_)+(com|co.nz|com.au|co.ca|co.us|co.uk)"; 
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(email);
            if(matcher.matches()){    
                //check for invalid characters in domain
                if(!checkDomain(email)){
                    System.out.println(orgEmail + " <- Invalid domain name"); 
                    return; 
                }
                //check for consecutive characters 
                if(!consecutives(email)){
                    System.out.println(orgEmail + " <- Invalid '.-_' characters");
                    return; 
                }else if(!validExtensions(email)){
                    System.out.println(orgEmail + " <-Invalid extension"); 
                    return; 
                }
                //email is valid 
                System.out.println(email);  
            }else{
                System.out.println(orgEmail + checkWrong(email)); 
            }
        }
    }

    public static String validateIP(String email){
        String orgEmail = email;  
        try{ 
            int start = email.indexOf('['); 
            int end = email.indexOf(']'); 
            email = email.substring(start+1,end);  
            String[] numbers = email.split("\\."); 

            //check to see that email doesnt contain anything after the ']'
            if(orgEmail.charAt(orgEmail.length()-1) != ']'){
                return orgEmail + " <- Invalid IP";
            } 
        
            //check to see if IP is more than 4 parts. 
            if(numbers.length!=4){
                return orgEmail + " <- Invalid IP"; 
            } 
            
            Pattern pattern = Pattern.compile("^[0-9]*"); 
            for(int i=0;i<numbers.length;i++){
                try{
                    Matcher matcher = pattern.matcher(numbers[i]);
                    if(!matcher.matches()){
                        return orgEmail + " <- Invalid IP"; 
                    }

                    int n = Integer.parseInt(numbers[i]);
                    if(n<0 || n>255){
                        return orgEmail + " <- Invalid IP";
                    }
                }catch(NumberFormatException e){
                    return orgEmail + " <- Invalid IP";
                }
            }
            return orgEmail;
        } catch(ArrayIndexOutOfBoundsException ex){
            return orgEmail + " <- Invalid IP";
        }catch(StringIndexOutOfBoundsException se){
            return orgEmail + " <- Invalid IP";
        }
    }

    /** checks and replaces the _at_ **/ 
    public static String check_at_(String email){ 
        int i; 
        ArrayList<Integer> at = new ArrayList<Integer>();
        for(i=0;i<email.length()-4;i++){
            if(email.substring(i,i+4).equals("_at_")){
                at.add(i); 
            }
        }
        int last = at.get(at.size()-1); 
        String before = email.substring(0,last); 
        String after = email.substring(last+4);
        email = before+"@"+after;

        return email;
    }


    /** checks for consecutive ".-_" characters in the email.*/
    public static Boolean consecutives(String email){
        char[] consec = {'.','-','_'};
        int a = email.indexOf("@"); 
        email = email.substring(0,a); 
        int c = 0;   

        //check that the domain doesn't have a special char at the front or back 
        for(int i=0; i<consec.length;i++){
            if(email.charAt(0) == consec[i] || email.charAt(email.length()-1) == consec[i]){
                return false; 
            }
        }

       //check for consecutives 
        for(int i=0;i<consec.length;i++){
            for(int j=1;j<email.length()-1;j++){
                if(email.charAt(j)== consec[i]){
                    for(int p=0;p<consec.length;p++){
                        if(email.charAt(j-1)==consec[p]){
                            return false; 
                        }
                    }
                }
            }
        }
        return true; 
    }

    public static boolean checkDomain(String email){
        int a = email.indexOf("@"); 
        email = email.substring(a+1); 
        //check matcher
        String regex = "^[A-Za-z0-9\\+]+(\\.[A-Za-z0-9]+)*"; 
        if(email.matches(regex)){ 
            return true; 
        }else{
            return false;
        }
    }

    public static boolean validExtensions(String email){
        String[] ex1 = {".co.nz",".com.au",".co.ca",".com",".co.us",".co.uk"};
        int i; 
        for(i=0;i<ex1.length;i++){
            int len = ex1[i].length(); 
            if(email.substring(email.length()-len).equals(ex1[i])){
                return true; 
            }
        }
        return false; 
    }

    public static String checkWrong(String email){ 
        String regex; 
        //check invalid mailbox 
        regex = "^[a-z0-9]+[a-z0-9+_.-]+(@|_at_)+(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if(!matcher.matches()){
            return " <- Invalid mailbox name"; 
        }
        //check for valid extension 
        if(!validExtensions(email)){
            return " <- Invalid extension";
        }
        return " <- Invalid domain name"; 
    }
}