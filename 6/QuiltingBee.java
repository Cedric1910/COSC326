/* Etude 6, QuiltingBee 
 * Author: Cedric Stephani
 * takes in multiple lines of input 'scale red green blue'
 * and recursively creates squares with each iteration being on the 
 * previous ones corners. 
 */ 

import java.util.Scanner; 
import java.util.ArrayList; 
import java.util.List;
import javax.swing.*;
import java.awt.*; 

public class QuiltingBee extends JPanel{ 
	public static double combinedScale = 0.0; //gets the total combined scale (dependent on input)
	public static List<Square> squares = new ArrayList<>(); 
	public static int currentLayer = 0;
	public static double squareSize = 300;
	public static int panelSize = 600; 

	public static void main(String[]args){
		int totalLayers = 1; 
		//read in each line from console and then sort them into their relative variables
		Scanner sc = new Scanner(System.in); 
		while(sc.hasNextLine()){
			String[] line = sc.nextLine().split(" "); 
			double scale = Double.parseDouble(line[0]); 
			int red = Integer.parseInt(line[1]);
			int green = Integer.parseInt(line[2]);
			int blue = Integer.parseInt(line[3]); 
			combinedScale = combinedScale + scale; 
			
			//create a new Square object and add it to the list of squares
			squares.add(new Square(scale,red,green,blue,totalLayers));
			totalLayers++; //increment the layer 
		}
		squareSize = squareSize*combinedScale; // make sure square isn't greater than the frame-window 
		squares.add(null);//added to help with the reccursion parameter 

		// set up the frame and JPanel
		JFrame frame = new JFrame("Quilting Bee"); 
		JPanel panel = new JPanel(); 
		panel.setBackground(Color.WHITE);

		frame.setSize(new Dimension(panelSize+20,panelSize+40)); //had to add extra padding around the square
		frame.getContentPane().add(new QuiltingBee()); 
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		frame.setVisible(true);
	}

	public void drawSquare(Graphics g, int index, int x, int y){
		//System.out.println("here"); 
		Square square = squares.get(index); //get the current square 
		double scale = square.getScale(); // get the squares scale 
		double size = scale / combinedScale * panelSize; // sets the square size relevant to the frameSize
		int length = (int)Math.round(size);  //set created size to an integer

		//System.out.println("current layer: "+currentLayer); 
        //System.out.println("square layer is: "+square.getLayer());
        //System.out.println("Layer params: "+index+" "+x+" "+y); 

		//check to see if the square layer is equal to the current layer
		if(square.getLayer() == currentLayer){
			//System.out.println("got into if statement"); 
			g.setColor(square.getColour()); 
			g.fillRect(x-length/2,y-length/2,length,length);
		}
		if(squares.get(index+1)!= null){ // if there is another readable line add squares in the corner 
			drawSquare(g, index+1, x - length/2, y - length/2);
			drawSquare(g, index+1, x + length/2, y - length/2);
            drawSquare(g, index+1, x - length/2, y + length/2);
            drawSquare(g, index+1, x + length/2, y + length/2);
		}

	}

	public void paintComponent(Graphics g){
		super.paintComponent(g); 
		while(currentLayer < squares.size()){
			//System.out.println("loop working"); 
			drawSquare(g,0,310,310); 
			currentLayer++; 
		}
	}
}

class Square{
	public double scale; 
	public int red, green, blue, layer; 

	public Square(double scale,int red, int green, int blue, int layer){
		this.scale = scale; 
		this.red = red; 
		this.green = green; 
		this.blue = blue; 
		this.layer = layer; 
	}

	public double getScale(){
		return scale; 
	}

	public Color getColour(){
		Color c = new Color(red,green,blue); 
		return c; 
	}

	public int getLayer(){
		return layer; 
	}
}