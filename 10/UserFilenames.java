import java.util.*; 
import java.io.*;
import java.util.regex.*; 

/**
 * User Filenames
 * Etude 10
 * Semester 1 2020
 * 
 * Takes a directory as an argument, sorts the files within the directory in sequential order
 * and then reads each file and puts the contents into a plaintext file called "result.txt".
 * 
 * @author: Cedric Stephani
 * @author: Hugo Baird
 */

public class UserFilenames {
  public static ArrayList<File> documents = new ArrayList<>();
  public static List<File> wrongFiles = new ArrayList<>(); 
  public static File[] filesArray;
  public static String[] fileNames;
  public static int[] firstDigits;
  public static int[] secondDigits;
  public static int[] thirdDigits;
  
  public static void main(String[]args) {
    try{
      String directoryPath = args[0];  
      
      File mainDirectory = new File(directoryPath); 
      if(mainDirectory.exists() && mainDirectory.isDirectory()){
        File directories[] = mainDirectory.listFiles(); 
        
        recursiveAdd(directories,0,0); 
      }else{
        System.out.println("Invalid: Could not find the specified directory."); 
      }
      
      List<File> textFiles = new ArrayList<>();
      filesArray = new File[textFiles.size()];
      textFiles = checkFiles();  
      filesArray = textFiles.toArray(filesArray);
      fileNames = new String[textFiles.size()];
      
      int i = 0;
      for(File file: filesArray){
        fileNames[i] = (file.getName());
        i++;
      }
      
      firstDigits = new int[textFiles.size()];
      secondDigits = new int[textFiles.size()];
      thirdDigits = new int[textFiles.size()];
      
      i = 0;
      for(String fileName: fileNames){
        Scanner sc = new Scanner(fileName);
        fileName = fileName.replaceAll("[^0-9]","");
        int firstDigit = Integer.parseInt(fileName.substring(0,2));
        int secondDigit = Integer.parseInt(fileName.substring(2,4));
        int thirdDigit = Integer.parseInt(fileName.substring(4,6));
        firstDigits[i] = (firstDigit);
        secondDigits[i] = (secondDigit);
        thirdDigits[i] = (thirdDigit);
        i++;
      }
      
      insertionSort();
      selectionSort(firstDigits, secondDigits, thirdDigits, filesArray);
      selectionSort(secondDigits, thirdDigits, firstDigits, filesArray);
        
      createOutputFile(filesArray);
    } catch (Exception e) {
      System.out.println("Invalid: Please input a directory name as an argument."); 
    }
  }
  
  public static void insertionSort() {
    for (int i = 1; i < fileNames.length; i++) {
      int currentFirst = firstDigits[i];
      int currentSecond = secondDigits[i];
      int currentThird = thirdDigits[i];
      String currentStr = fileNames[i];
      File currentFile = filesArray[i];
      int j = i - 1;
      while(j >= 0 && currentFirst < firstDigits[j]) {
        firstDigits[j+1] = firstDigits[j];
        secondDigits[j+1] = secondDigits[j];
        thirdDigits[j+1] = thirdDigits[j];
        fileNames[j+1] = fileNames[j];
        filesArray[j+1] = filesArray[j];
        j--;
      }
      firstDigits[j+1] = currentFirst;
      secondDigits[j+1] = currentSecond;
      thirdDigits[j+1] = currentThird;
      fileNames[j+1] = currentStr;
      filesArray[j+1] = currentFile;
    }
  }
  
  public static void selectionSort(int[] numbersBy, int[] numbersTo, int[] numbersOut, File[] files) {
    int start = 0;
    int limit;
    for (int i = 0; i < numbersBy.length-1; i++) {
      if (numbersBy[i] != numbersBy[i+1]) {
        limit = i + 1;
        for (int n = start; n < (limit); n++) {
          int index = n;
          for (int j = n + 1; j < limit; j++){  
            if (numbersTo[j] < numbersTo[index]){  
              index = j; 
            }  
          }  
          int smallerNumberTo = numbersTo[index];   
          int smallerNumberOut = numbersOut[index]; 
          String smallestStr = fileNames[index]; 
          File file = files[index];
          numbersTo[index] = numbersTo[n];  
          numbersOut[index] = numbersOut[n];
          fileNames[index] = fileNames[n];
          files[index] = files[n];
          numbersTo[n] = smallerNumberTo;
          numbersOut[n] = smallerNumberOut;
          fileNames[n] = smallestStr;
          files[n] = file;
        }
        start = limit;
      }
    }
    firstDigits = numbersBy;
    secondDigits = numbersTo;
    thirdDigits = numbersOut;
    filesArray = files;
  }
  
  /* Recursive method to dive into a given directory and its sub directories 
   * and get all the files names included to then add to an arraylist. */ 
  public static void recursiveAdd(File[] directories, int index, int level){
    //terminate condition 
    if(index == directories.length) return; 
    
    if(directories[index].isFile()){
      documents.add(directories[index]);
    }else if(directories[index].isDirectory()){
      recursiveAdd(directories[index].listFiles(),0,level+1); 
    }
    
    recursiveAdd(directories,++index,level); 
  }
  
  /* checks each file to see if it matches what is needed and adds it to a new list */ 
  public static List<File> checkFiles(){
    List<File> textFiles = new ArrayList<>(); 
    for(File document : documents){
      String fileName = document.getName();
      fileName = fileName.replaceAll("[^0-9]","");
      if (fileName.length() == 6){
          textFiles.add(document); 
      }
    }
    return textFiles; 
  }
  
  /*creates the output file */ 
  public static void createOutputFile(File[] filesArray){
    try{
      FileWriter writer = new FileWriter("results.txt"); 
      for(File file : filesArray){
        Scanner readFile = new Scanner(file); 
        while(readFile.hasNextLine()){
          writer.write(readFile.nextLine() + System.lineSeparator()); 
        }
      }
      writer.close();
    }catch(IOException ie){
      System.out.println("IOException");
    }
  }
}