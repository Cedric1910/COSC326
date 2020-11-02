import java.awt.*;

public class Square {

    double scale;
    Color colour;
    int layer;

    public Square(double scale, Color colour, int layer){
        this.scale = scale;
        this.colour = colour;
        this.layer = layer;
    }

    public double getScale(){
        return scale;
    }

    public int getLayer(){
        return layer;
    }

    public Color getColour(){
        return colour;
    }

}