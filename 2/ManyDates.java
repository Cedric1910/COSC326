
import java.util.Scanner;
import java.util.ArrayList; 

import java.util.*;
import java.text.*;

public class ManyDates{
  
  public static void main(String[]args){
    ArrayList<String> dates = new ArrayList<>(); 
    
    //scan in the dates
    Scanner scan = new Scanner(System.in); 
    while(scan.hasNextLine()){
      dates.add(scan.nextLine()); 
    } 
    //find the most common date format 
    String[] formats = {"dd/MM/yy","MM/dd/yy", "dd/yy/MM","MM/yy/dd","yy/dd/MM","yy/MM/dd"};
    int[] counts = new int[] {0, 0, 0, 0, 0, 0};
    int i = 0;
    for(String date : dates){
      for(String format : formats){
        
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        dateFormat.setLenient(false);
        boolean dateChecked = dateCheck(date, dateFormat);
        if(dateChecked == true){
          counts[i] += 1;
        }
        i = i + 1;
        if(i == 6){
          i = 0;
        }
      }
    }
    int f = 0; 
    int max = 0; 
    for(i =0;i<counts.length;i++){
      if(counts[i]>max){
        max = counts[i]; 
        f = i; 
      }
    }
    
    //run through all dates again with the most probable format
    for(String date : dates){
      SimpleDateFormat dateFormat = new SimpleDateFormat(formats[f]);
      dateFormat.setLenient(false);
      boolean dateChecked = dateCheck(date, dateFormat);
      if(dateChecked){
        String newDate = checkYear(date, formats[f]); 
        if(errorCheck(date, newDate, formats[f])){
          try{
            Date output = new SimpleDateFormat(formats[f]).parse(date);  
            outputDate(output); 
          }catch(ParseException e){
            System.out.println("parse exception caught"); 
          }
        }
      }else{
        errorCheck(date, date, formats[f]);
      }
    }    
  }
  
  /**checks if year is a 2 digit number*/ 
  public static String checkYear(String date, String format){
    String[] dateSplit = date.split("/"); 
    String[] formatSplit = format.split("/"); 
    int i; 
    for(i=0;i<formatSplit.length;i++){
      if(formatSplit[i].contains("yy")){
        break; 
      }
    }
    
    if(Integer.parseInt(dateSplit[i]) <50){
      dateSplit[i] = "20"+dateSplit[i]; 
    }else if(Integer.parseInt(dateSplit[i]) >=50 && Integer.parseInt(dateSplit[i]) <100){
      dateSplit[i] = "19"+dateSplit[i]; 
    }
    
    //put date back together
    String returnDate =""; 
    for(i=0;i<dateSplit.length;i++){
      returnDate = returnDate+dateSplit[i]; 
      if(i<2){
        returnDate = returnDate + "/"; 
      }
    }
    return returnDate; 
  }
  
  public static boolean errorCheck(String date, String newDate, String format){
    try{
      SimpleDateFormat dateFormat = new SimpleDateFormat(format);
      String[] dateSplit = newDate.split("/");
      String[] formatSplit = format.split("/");
      String day = "";
      String month = "";
      String year = "";
      for(int i = 0; i < 3; i++){
        if((formatSplit[i]).equals("yy")){
          year = dateSplit[i];
        }
        if((formatSplit[i]).equals("dd")){
          day = dateSplit[i];
        }
        if((formatSplit[i]).equals("MM")){
          month = dateSplit[i];
        }
      }
      
      if(Integer.parseInt(day) <= 31){
        if(Integer.parseInt(month) <= 12){
          if(Integer.parseInt(year) >= 1753 & Integer.parseInt(year) <= 3000){
            if(dateCheck(newDate, dateFormat)){
              return true;
            }else{
              System.out.println(date + "- INVALID: Day out of range.");
            }
          }else{
            System.out.println(date + " - INVALID: Year out of range.");
          }
        }else{
          System.out.println(date + " - INVALID: Month out of range.");
        }
      }else{
        System.out.println(date + " - INVALID: Day out of range.");
      }
    }catch(ArrayIndexOutOfBoundsException e){
      System.out.println(date + " - INVALID: Incorrect input");
    }
    return false;
  }
  
  public static boolean dateCheck(String date, SimpleDateFormat dateFormat){
    try{
      dateFormat.setLenient(false);
      dateFormat.parse(date);
      return true;
    }catch(ParseException e){
      return false;
    }
  }
  
  
  public static void outputDate(Date date){
    SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy");
    String output = df.format(date);
    System.out.println(output); 
  } 
}