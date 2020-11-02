//imports 
import java.util.Scanner; 
import java.util.ArrayList; 
import java.io.*; 
import java.util.regex.*;
import java.util.*;

public class PokerHands{
	public static String[] currentHand = new String[5]; // the current hand 
	public static int[] nums = new int[5]; //the hands current numbers by index
	public static String[] suit = new String[5]; // the hands current suits by index
	public static int count; 
	public static String orgHand;

	public static void main(String[]args){
 		ArrayList<String> hands = new ArrayList<>();
 		Scanner sc = new Scanner(System.in); 
 		while(sc.hasNextLine()){
            String s = sc.nextLine(); 
            if(!s.isEmpty()){
                hands.add(s); 
            }
        }

        for(String hand : hands){
        	//update global variables to be empty again and count to be 0 
        	for(int i=0; i<currentHand.length;i++){
        		currentHand[i] = ""; 
        		nums[i] = 0; 
        		suit[i] = ""; 
        	} 
        	count=0; 

        	//check hand for any errors
        	if(checkHand(hand) && noDuplicates()){ 
        		sortHand(); //if there are no errors then sort the hand 

        	}else{
        		System.out.println("Invalid: "+ hand); 
        	}
        }
	}

	/** checks to see if the basic pattern is correct and if the right letters are allowed.   */  
	public static boolean checkHand(String hand){
		orgHand = hand;  //if wrong it will return the hand unchanged for output 
		String regex = "^[ACDHJKQSacdhjkqs0-9]+((\\/[ACDHJKQSacdhjkqs0-9]+)*|(\\-[ACDHJKQSacdhjkqs0-9]+)*|(\\ [ACDHJKQSacdhjkqs0-9]+)*)";
		if(hand.matches(regex)){
			//get the individual cards and check they are correct 
			String[] cards = getCards(hand); 
			for(int i=0;i<cards.length;i++){
				if(!checkCard(cards[i])){
					return false; 
				}
			} 
			return true; 
		}else{
			return false; 
		}
	}

	/* checks each individual card to see whether its valid */ 
	public static boolean checkCard(String card){
		try{
			//arrays of each suit. 
			String[] clubs = {"2C","3C","4C","5C","6C","7C","8C","9C","10C","JC","QC","KC","AC"}; 
			String[] diamonds = {"2D","3D","4D","5D","6D","7D","8D","9D","10D","JD","QD","KD","AD"};
			String[] hearts = {"2H","3H","4H","5H","6H","7H","8H","9H","10H","JH","QH","KH","AH"};
			String[] spades = {"2S","3S","4S","5S","6S","7S","8S","9S","10S","JS","QS","KS","AS"};

			card = card.toUpperCase(); 
			card = numberToSuit(card); //checks to see if the card is a 1,11,12, or 13 

			//check to see if the card is valid 
			for(int i=0;i<clubs.length;i++){
				if(card.equals(clubs[i]) || card.equals(diamonds[i]) 
				   || card.equals(hearts[i]) || card.equals(spades[i])){
					currentHand[count] = card; 
					count++; 
					return true; 
				}
			}

			return false;
		}catch (NullPointerException e) {
            return false; 
        }catch(ArrayIndexOutOfBoundsException ae){
        	return false; 
        }
	}

	/*method to actually sort the hand. Using quicksort */ 
	public static void sortHand(){ 
		try{
			for(int i=0;i<currentHand.length;i++){
				currentHand[i] = suitToNumber(currentHand[i]); // make sure its the number version of the card 
				nums[i] = Integer.parseInt(currentHand[i].substring(0,currentHand[i].length()-1));  
				suit[i] = currentHand[i].substring(currentHand[i].length()-1);

				if(nums[i] ==1){
					nums[i]=14; 
				}
			}
			 
			//sort the cards 
			sort(nums,0,nums.length-1); 

			//check the suits are in order 
			for(int i=1;i<nums.length;i++){
				if(nums[i] == nums[i-1]){
					int s1 = getSuitEquivalent(suit[i-1]);
					int s2 = getSuitEquivalent(suit[i]);  

					if(s1>s2){
						//swap the numers and suits  
				        int temp = nums[i-1]; 
				        nums[i-1] = nums[i]; 
				        nums[i] = temp; 
				        String t = suit[i-1]; 
				        suit[i-1] = suit[i]; 
				        suit[i] = t;
				        i = 0; 
					}
				}
			}
			//update current hand again 
			for(int i=0;i<nums.length;i++){
				if(nums[i]==14){
					nums[i]=1;
				}
				currentHand[i] = nums[i]+suit[i]; 
			}

			//change it to suits for the final answer. 
			for(int i=0;i<currentHand.length;i++){
				currentHand[i] = numberToSuit(currentHand[i]); 
			}
			String output = ""; 
			//print out the final sorted hand 
			for(int i=0;i<currentHand.length;i++){
				output = output + currentHand[i] + " "; 
			}
			output = output.substring(0,output.length()-1);
			System.out.println(output); 
		} catch(StringIndexOutOfBoundsException ie){
			System.out.println("Invalid: "+ orgHand); 
		}
	}

	/* takes a hand of cards and will return an array of each card. */ 
	public static String[] getCards(String hand){
		String orgHand = hand;
		String[] seperators = {"-","/"," "};
		String[] cards = new String[5]; 
		//split the hand 
		for(int i=0;i<seperators.length;i++){
			if(hand.contains(seperators[i])){
				cards = hand.split(seperators[i]); 
			}
		}

		return cards; 
	}

	/*gets a number 1-4 depending on the suit weight */ 
	public static int getSuitEquivalent(String suit){
		if(suit.equals("C")){
			return 1; 
		} else if(suit.equals("D")){
			return 2; 
		} else if(suit.equals("H")){
			return 3; 
		} else{
			return 4; 
		}
	}

	/*checks to make sure there are no duplicates in the hand */ 
	public static boolean noDuplicates(){
		for(int i=0;i<currentHand.length;i++){
			for(int j=i+1;j<currentHand.length;j++){
				if(currentHand[i].equals(currentHand[j])){
					return false; 
				}
			}
		} 
		return true; 
	}

	/** replaces the card format, e.g. 13S -> KS */
	public static String numberToSuit(String card){
		String[] numbers = {"1C","1D","1H","1S","11C","11D","11H","11S"
							,"12C","12D","12H","12S","13C","13D","13H","13S"};
		String[] letters = {"AC","AD","AH","AS","JC","JD","JH","JS"
							,"QC","QD","QH","QS","KC","KD","KH","KS"};
		for(int i=0;i<numbers.length;i++){
			if(card.equals(numbers[i])){
				card = letters[i]; 
				return card; 
			}
		}
		return card; 
	}

	/** replaces the card format, e.g. KS -> 13S */
	public static String suitToNumber(String card){
		String[] numbers = {"1C","1D","1H","1S","11C","11D","11H","11S"
							,"12C","12D","12H","12S","13C","13D","13H","13S"};
		String[] letters = {"AC","AD","AH","AS","JC","JD","JH","JS"
							,"QC","QD","QH","QS","KC","KD","KH","KS"};
		for(int i=0;i<numbers.length;i++){
			if(card.equals(letters[i])){
				card = numbers[i]; 
				return card; 
			}
		}
		return card; 
	}

	/* the main function that implements quicksort*/ 
	public static void sort(int arr[], int low, int high){ 
        if (low < high) 
        { 
            /* pi is partitioning index, arr[pi] is  
              now at right place */
            int pi = partition(arr, low, high); 
  
            // Recursively sort elements before 
            // partition and after partition 
            sort(arr, low, pi-1); 
            sort(arr, pi+1, high); 
        } 
    } 

	/* takes the last element as pivot and places the pivot 
		element in its correct position */ 
	public static int partition(int arr[], int low, int high) 
    { 
        int pivot = arr[high];  
        int i = (low-1); // index of smaller element 
        for (int j=low; j<high; j++) 
        { 
            // If current element is smaller than the pivot 
            if (arr[j] < pivot) 
            { 
                i++; 
  
                // swap arr[i] and arr[j] 
                int temp = arr[i]; 
                arr[i] = arr[j]; 
                arr[j] = temp; 
                String t = suit[i]; 
		        suit[i] = suit[j]; 
		        suit[j] = t;
            } 
        } 
  
        //swap the numers and suits  
        int temp = arr[i+1]; 
        arr[i+1] = arr[high]; 
        arr[high] = temp; 
        String t = suit[i+1]; 
        suit[i+1] = suit[high]; 
        suit[high] = t; 
  
        return i+1; 
    }
}