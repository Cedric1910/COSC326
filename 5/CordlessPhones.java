import java.util.*;
import java.lang.Math;
import java.util.Arrays;

/**
 * Cordless Phones
 * Etude 5
 * Semester 1 2020
 *
 * Given a set of positions of telephones, it work out the maximum range that
 * guarantees that not more than eleven of the telephones are within range.
 *
 * Uses SmallestEnclosingCircle.java library
 *
 * @author: Hugo Baird
 * @author: Leon Hoogenraad
 * @author: Cedric Stephani
 * @author: Darcy Knox
 */

public class CordlessPhones{
  //For reading in input
  public static Scanner sc = new Scanner(System.in);
  //Array of telephone sites
  public static ArrayList<Site> sites = new ArrayList<>();
  //The value of pi hopefully - pretty sure this is redundant.
  public static double pi = Math.PI;

  public static void main(String[] args){
    if(sc.hasNextLine()){
      //Get rid of the first line input because pretty sure it's useless
      sc.nextLine();
    }
    ArrayList<Double> eastPoints = new ArrayList <>();
    ArrayList<Double> northPoints = new ArrayList <>();
    try{
      while(sc.hasNextLine()){
        //double east = sc.nextLong();
        //double north = sc.nextLong();
        /*
         Here we're getting input by reading in a line of text as a string, splitting it by a
         whitespace, then converting the 2 first splits into doubles named east and north, then
         creating a new Site object out of those two doubles.
         TBH we could add getNorth() and getEast() methods to Site.java which might make the code look
         cleaner but who can be assed really.
         */
        // String input = sc.nextLine();
        // String inSplit[] = input.split(" ");

        // double east = Double.parseDouble(inSplit[0]);
        // double north = Double.parseDouble(inSplit[1]);
        // eastPoints.add(east);
        // northPoints.add(north);
        // sites.add(new Site(east, north));

        /* Changed the input method to a scanner instead of Double.parseDouble as
         * he said input can be split by more than one whitespace */ 
        String input = sc.nextLine(); 
        Scanner inputScan = new Scanner(input); 
        double east = sc.nextDouble(); 
        double north = sc.nextDouble(); 
        eastPoints.add(east);
        northPoints.add(north);
        sites.add(new Site(east, north));

      }
    }catch(NumberFormatException ne){
      System.out.println("Wrong input format found \nFormat: 'double' 'double'");
    }catch(Exception e){
       e.printStackTrace();
    }

    // Checks that there are equal amount of east and north points for each site.
    if(!(eastPoints.size()==northPoints.size())){
      System.out.println("Number of east points and north points aren't equal");
    }
    sc.close();
    if(sites.size() < 12){
      System.out.println(Double.POSITIVE_INFINITY);
    }
    else{
      double smallestDouble = calculatePoints();
      System.out.println(smallestDouble);
    }
  }


  /**
   * Generate points and add them to an array to create smallest enclosing circles of 11 points
   */
  public static double calculatePoints(){
    double[] eleventh_lens = new double[sites.size()];

    // List of circles from SmallestEnclosingCircle class
    ArrayList<Circle> circles = new ArrayList<>();

    /*
     For every site in sites array, find the 11th furthest away site
     */
    for(int i = 0; i < sites.size(); i++){
      // List of closest points to the current site
      List<Point> points = new ArrayList<>();
      List<Point> closestPoints = new ArrayList<>();
      // Our current site on our journey through this for loop
      Site currSite = sites.get(i);
      // This array will hold the distances from each site to currSite
      double[] lengths = new double[sites.size()];
      for(int x = 0; x < sites.size(); x++){
        Site xSite = sites.get(x);
        lengths[x] = distance(currSite.east, currSite.north, xSite.east, xSite.north);
        points.add(new Point(xSite.east, xSite.north));
      }

      /* insertion sort to get the closest sites in order based on the distance to them */
      for (int k = 1; k < sites.size(); k++)
      {
        double key = lengths[k];
        Point p = points.get(k);
        int j = k - 1;
        while (j >= 0 && lengths[j] > key)
        {
          lengths[j + 1] = lengths[j];
          points.set(j + 1, points.get(j));
          j = j - 1;
        }
        lengths[j + 1] = key;
        points.set(j + 1, p);
      }

      /* Here we set eleventh_lens[i] to the 12th element in lengths. The first
         length is 0.0 because it's the current point. */
      eleventh_lens[i] = lengths[11];
      //Testing
      //System.out.println("11th length: " + Double.toString(lengths[11]));
      //System.out.println("lengths array:");
      //System.out.println(Arrays.toString(lengths));

      /* Get the 11 closest points to the current site */
      for (int z = 0; z < 12; z++) {
        closestPoints.add(points.get(z));
      }

      /* Creates smallest enclosing circle using the closest points to the curent point */
      Circle circle = SmallestEnclosingCircle.makeCircle(closestPoints);
      circles.add(circle);
    }
<<<<<<< HEAD
    Circle smallest = getSmallestCircle(circles);
    Double smallestDouble = Math.floor(smallest.r * 100.0) / 100.0;
    
    return smallestDouble;
=======
    Arrays.sort(eleventh_lens);
    //Testing
    //System.out.println("eleventh_lens array:");
    //System.out.println(Arrays.toString(eleventh_lens));

    //Return the smallest elventh_len
    return eleventh_lens[0];
>>>>>>> b1645d9ac7fd92c8df8604fbf42890e70e966802
  }


  // Returns the straight line distance between two points.
  // Takes two East, North -> (x,y) co-ordinates as arguments.
  private static double distance(double x1, double y1, double x2, double y2) {
    return Math.hypot(Math.abs(x1 - x2), Math.abs(y1 - y2));
  }

  // Returns the smallest circle by radius
  private static Circle getSmallestCircle(ArrayList<Circle> circles) {
    int minRadiusIndex = 0;
    double minRadius = circles.get(0).r;
    for (int z = 1; z < circles.size(); z++) {
      if (circles.get(z).r < minRadius) {
        minRadius = circles.get(z).r;
        minRadiusIndex = z;
      }
    }
    //System.out.println("Min radius: " + Double.toString(minRadius));
    //System.out.println("Smallest circle: " + circles.get(minRadiusIndex));
    //System.out.println("-----------------------------------");
    return circles.get(minRadiusIndex);
  }
}