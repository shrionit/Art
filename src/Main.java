import java.util.ArrayList;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.input.KeyCode;
import javafx.stage.StageStyle;

public class Main extends Application{
    private double W = 1920;
    private double H = 1080;

    private Canvas canvas;
    private GraphicsContext gc;
    private List<Circle> circles = new ArrayList<>();
    private List<Point2D> points = new ArrayList<>();
    private Image img;
    private boolean kro = false;

    private void setup(){
        canvas = new Canvas(W, H);
        gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, W, H);
        Circle.gc = gc;
        img = new Image(getClass().getResource(getParameters().getNamed().get("img")).toExternalForm());
        PixelReader pr = img.getPixelReader();
        for(int i=0;i<img.getWidth();i++){
            for(int j=0;j<img.getHeight();j++){
                if((pr.getColor(i, j)).getBrightness() == 1 ){
                    points.add(new Point2D((double)i, (double)j));
                }
            }
        }
    }

    private void update(){
        gc.setFill(Color.color(0, 0, 0, 1.0));
        gc.fillRect(0, 0, W, H);
        canvas.setOnKeyPressed(k -> {
            if(k.getCode() == KeyCode.P){
                kro = true;
            }
        });
    }

    private void draw(){
        int total = 10;
        int count = 0;
        int attempts = 0;

        while(count < total){
            Circle circle = newCircle();
            if(circle!=null){
                circles.add(circle);
                count++;
            }
            attempts++;
            if(attempts > 1000){
                break;
            }
        }

        for(Circle C : circles){
            if(C.space){
                if(C.edges(W, H)){
                    C.setSpace(false);
                }else{
                    for(Circle other : circles){
                        if(C != other){
                            double d = new Point2D(C.getX(), C.getY()).distance(other.getX(), other.getY());
                            if(d < C.getRadius() + other.getRadius()){
                                C.space = false;
                                break;
                            }
                        }
                    }
                }
            }
        }
        
        circles.forEach(c -> c.show(""));
        
    }

    private Circle newCircle(){
        int r = (int)(Math.random()*points.size());
        Point2D p = points.get(r);
        double x = p.getX();
        double y = p.getY();
        boolean valid = true;

        for(Circle c : circles){
            double d = new Point2D(x, y).distance(c.getX(), c.getY());
            if(d < c.getRadius()){
                valid = false;
                break;
            }
        }

        if(valid){
            return new Circle(x, y, Color.GREY, Color.color(Math.random(), Math.random()*(125/255), Math.random()*(165/255)));
        }else{
            return null;
        }

    }

    private Parent getContent(){
        Pane pane = new Pane();
        setup();
        new AnimationTimer(){
            @Override
            public void handle(long now) {
                update();
                draw();
            }
        }.start();
        pane.getChildren().add(canvas);
        return pane;
    }

    public void start(Stage stage){
        stage = new Stage(StageStyle.UNDECORATED);
        stage.setScene(new Scene(getContent(), W, H));
        stage.show();
    }

    public static void main(String[] args){launch(args);}
}