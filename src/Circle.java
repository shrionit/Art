import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Circle{
    private double x;
    private double y;
    private double r = 0;
    private Color sC = Color.WHITE;
    private Color fC = Color.TRANSPARENT;
    public static GraphicsContext gc;
    public boolean space = true;

    public Circle(double x, double y){
        this.x = x;
        this.y = y;
    }

    public Circle(double x, double y, Color c){
        this.x = x;
        this.y = y;
        sC = c;
        fC = c;
    }

    public Circle(double x, double y, Color sC, Color fC){
        this.x = x;
        this.y = y;
        this.sC = sC;
        this.fC = fC;
    }

    public void setSpace(boolean s){
        space = s;
    }
    
    public void show(String mode){

        if(mode.equals("fill")){
            gc.setFill(fC);
            gc.fillOval(x-r/2, y-r/2, r, r);
        }else if(mode.equals("stroke")){
            gc.setStroke(sC);
            gc.strokeOval(x-r/2, y-r/2, r, r);
        }else{
            gc.setFill(fC);
            gc.fillOval(x-r/2, y-r/2, r, r);
            gc.setStroke(sC);
            gc.strokeOval(x-r/2, y-r/2, r, r);
        }
        
        if(space){
            r++;
        }
        //System.out.println("r = "+r);
    }

    public boolean edges(double W, double H){
        return (x+r/2 > W || x-r/2 < 0 || y+r/2 > H || y-r/2 < 0)?true:false;
    }

    public double  getRadius(){
        return r/2;
    }

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }
}