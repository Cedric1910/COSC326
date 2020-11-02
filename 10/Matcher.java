import java.io.*;
import java.util.regex.*;

public class Matcher{
	public static void main(String[]args){

        String text = ".DS_Store";

        String regex = "[0]+([0-5]+\\-)+[0-2]+[0-9]+\\-+[0-9][0-9]+(.txt)"; 
        boolean b = Pattern.matches(regex,text);

        if(b){
        	System.out.println("match"); 
        }else{
        	System.out.println("invalid"); 
        }
        
	}
}