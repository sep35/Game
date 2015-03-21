package Games;

import java.util.Random;
import java.util.Stack;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class Enemies {
	public void createnemies(double countframe, double sceneH, double sceneW, Group enemies, boolean steady){
		Rectangle block = new Rectangle();
		if(((countframe % 5) == 0) && !steady){
			Random building = new Random();
			block = new Rectangle(sceneW-30 , building.nextInt((int) sceneH), 20, 40);
			block.setFill(Color.BROWN);
			enemies.getChildren().add(block);
		} else if (steady){
			int typeofenemie = 1;
			int sceneWi= 100;
			while(sceneWi < sceneW ){
				if((typeofenemie % 2)!=0){
					block = new Rectangle(sceneWi, 600-30, 20, 40);
				} else {
					block = new Rectangle(sceneWi, 450, 40, 20);					
				}
				typeofenemie++;
				sceneWi+=80;
				block.setFill(Color.BROWN);
				enemies.getChildren().add(block);	
			}
		}
	}


	public void updatenemies(Group foes, boolean moving){
		if(moving){
			Stack<Node> fotracker = new Stack<Node>();
			for(Node foe: foes.getChildren()){
				Rectangle fo = (Rectangle)	foe;
				fo.setX(fo.getX()-180.0/60);
				if(fo.getX() == 0){
					fotracker.add(foe);
				}
			}
			for(Node fo:fotracker){
				foes.getChildren().remove(fo);
			}
		}
	}

	public void createBoss(double countframe, double sceneH, double sceneW, Group enemies){
		Rectangle block = new Rectangle();
		block = new Rectangle(sceneW-100, sceneH-100, 100, 100);

		enemies.getChildren().add(block);
	}
	
	public void updateBoss(Rectangle Boss, Circle player){
		if(player.getCenterX() < Boss.getX()){
			Boss.setX(Boss.getX()-1);
		} else {
			Boss.setX(Boss.getX()+1);
		}
		if(player.getCenterY() < Boss.getY()){
			Boss.setY(Boss.getY()-1);
		} else {
			Boss.setX(Boss.getX()+1);
		}		
	}
}