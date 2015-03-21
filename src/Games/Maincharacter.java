package Games;

import java.util.Stack;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class Maincharacter extends Circle {
	private double preTime;
	protected boolean shoot, goup, godown, goleft, goright, gospace, immortality=false, GameWon = false, firegod; 

	public Circle character(double xloc, double yloc, double radius){
		  Circle Character = new Circle(xloc, yloc, radius);
		  return Character;
	}
	
	protected void updatelocation(double countframe, Scene scene, Circle jumpy, Rectangle gun, Group lasers){
		if(goup){
			if(jumpy.getCenterY() < 20){
				jumpy.setCenterY(jumpy.getCenterY()-0*(preTime)+ (9.8)*(.5)*Math.pow(preTime, 2));
				preTime = preTime + 2.0/60.0;				
			} else if(jumpy.getCenterY() >= scene.getHeight()-19) {	
				goup = false;
				preTime = 0;
				jumpy.setCenterY(scene.getHeight()-20);
			} else {
				jumpy.setCenterY(jumpy.getCenterY()-10.0*(preTime)+ (9.8)*(.5)*Math.pow(preTime, 2));
			    preTime = preTime + 5.0/60.0;	
			}
			gun.setY(jumpy.getCenterY());
		}
		if(goleft){
			if(jumpy.getCenterX() <= 20){
				goleft = false;
				
			}else{
				jumpy.setCenterX(jumpy.getCenterX() -10); 
				gun.setTranslateX(jumpy.getCenterX()-30); 
			}
		}
		if(goright){
			if(jumpy.getCenterX() >= scene.getWidth()-20){
				goright= false;
				GameWon = true;
			} else {
				jumpy.setCenterX(jumpy.getCenterX() +10);
				gun.setTranslateX(jumpy.getCenterX()-30);
			}	
		}
		if(gospace){
			if((countframe % 5)== 0){
				Circle laser = new Circle(jumpy.getCenterX()+10, scene.getHeight()-20, 5);
				laser.setFill(Color.PURPLE);
				lasers.getChildren().add(laser);
				laser.setCenterX(jumpy.getCenterX()); 
				laser.setCenterY(jumpy.getCenterY());
			}
		}
		if(godown){
			if(jumpy.getCenterY() >= scene.getHeight()-30){
				godown = false;
			}else{
				jumpy.setCenterY(jumpy.getCenterY() +10); 
				gun.setY(jumpy.getCenterY()); 
			}
		}
	
	}
	
	protected void updatemovement(Scene scene){
		scene.setOnKeyPressed(new EventHandler<KeyEvent>(){
			public void handle(KeyEvent event){
				switch (event.getCode()) {
				case UP: goup=true; preTime = 0; break;
				case RIGHT:	goright = true; break;
				case DOWN: godown = true; break;		
				case LEFT: goleft = true; break;						
				case SPACE: shoot = true; gospace = true; break;
				case SHIFT: immortality = true; break;
				case F: firegod = true; break;
				default: break;
				}
			}
		});
	}
	
	protected void regressmovement(Scene scene){
		scene.setOnKeyReleased(new EventHandler<KeyEvent>(){
			public void handle(KeyEvent event){
				switch (event.getCode()) {
				case RIGHT: goright = false; break;
				case DOWN:  godown = false; break;
				case LEFT:  goleft = false; break;
				case SPACE: gospace = false; break;
				default: break;
				}
			}
		});
	}

	protected void updatelaser(Group laser, Scene scene){	
		Stack<Node>	lstracker = new Stack<Node>();
		if(shoot){
			for(Node lsr:laser.getChildren()){
				Circle ls = (Circle) lsr;
				ls.setCenterX(ls.getCenterX()+5);
				if(ls.getCenterX() == scene.getWidth()-5 ){
					lstracker.add(lsr);
				}			
			}
			for(Node n:lstracker){
				laser.getChildren().remove(n);
			}
		}
	}
}
