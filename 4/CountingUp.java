/**
 * CountingItUp.java
 * Etude 4
 * Semseter 1 2020
 * 
 * Given a value of n and k, the function n!/k!(n-k)! is calculated to find the
 * number of different ways a hand of k cards is drawn from a pack of n cards.
 * 
 * @author: Hugo Baird 
 * @author: Cedric Stephani
 */

import java.util.Scanner;
import java.util.ArrayList;

public class CountingUp{
  private static ArrayList<Long> big;
  private static ArrayList<Long> small;

 public static void main(String[]args){
  

  //scans in to the array 
  Scanner sc = new Scanner(System.in); 
  int i = 1; 
  long n = 0; long k = 0; 
  while(sc.hasNext()){
   if(i==1){
      n = sc.nextLong();  
        if(n < 0){
          System.out.println("N must be positive");
        }
   }else if(i==2){
    k = sc.nextLong();  
        if(k < 0){
          System.out.println("K must be positive");
        }
    i=0; 
   } 
   i++; 
  }  
    makeArray(n,k);
    matchFactors();
    multiplyBigs();
 }
  
  public static void makeArray(long n, long k){
    //create the arrays 
    ArrayList<Long> biggie = new ArrayList<Long>(); 
    ArrayList<Long> smalls = new ArrayList<Long>();
    
    for(int i =0; i < k; i++){
      biggie.add(n - i);  
      smalls.add(k - i); 
    } 
    big = biggie;
    small = smalls;
    return; 
  }
  
  public static void matchFactors(){
    for(int i = 0; i < big.size(); i++){
       for(int n = small.size() - 1; n > -1; n = n-1){
           if (big.get(i) % small.get(n) == 0) {
             big.set(i, big.get(i) / small.get(n));
             small.set(n, 1L); 
           }  
       }    
    }
    for(int i = 0; i < big.size(); i++){
      System.out.println(big.get(i));
      System.out.println(small.get(i));
    }
  
  }
  
  public static void multiplyBigs(){
    Long bigOutput = Long.valueOf(1); 
    Long smallOutput = Long.valueOf(1); 
    for ( Long b : big) {
      bigOutput = bigOutput * b; 
    }

    for(Long s : small){
      if(s > 1){
        smallOutput = smallOutput / s; 
      }
    }

    System.out.println(bigOutput); 
    System.out.println(smallOutput); 


  }
    
}