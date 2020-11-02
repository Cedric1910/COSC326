import java.util.Scanner;
import java.util.ArrayList;
import java.io.*; 
import java.util.regex.*;

class Problem { 
	public static void main(String[]args){
		String text = "+50";
        Pattern pattern = Pattern.compile("^[0-9]*");
   		Matcher matcher = pattern.matcher(text);
		if(matcher.matches()){
			System.out.println("match: "+text); 
		} else{
			System.out.println("incorrect: "+text);
		}
	}
}