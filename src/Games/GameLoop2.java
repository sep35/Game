package Games;




import java.util.Random;
import java.util.Stack;

import javafx.animation.KeyFrame;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameLoop2 {
	double countframe = 0;
	int score = 0, backtomenu =0;	
	private Circle jumpy;
	private Maincharacter player;
	private Enemies foes = new Enemies();
	private Group lasers = new Group(), enemies = new Group();
	private Scene scene;
	private Rectangle gun;
	private Group root;
	private boolean GameOver = false;
	private boolean GameWon  = false;
	private Text scoreofgame = new Text(), t= new Text(); double timer = 20;
	int dx = 10, jumpcount=0, createstage =0;
	private Stage sglobal = new Stage();
	private Button MainMenub = new Button();
	
	/* This method will help create the scene that I want to create
	 * I call on it to set the scene that is being rendered
	 */
	public Scene initialize(Stage s, int width, int height, Button MainMenu){
		root = new Group();
		// creating the place to be able to see everything
		scene = new Scene(root, width, height, Color.SKYBLUE);
		// Adding my scenes to my stage
		sglobal = s;
		// Set character starting point
		player = new Maincharacter();
		jumpy = player.character((double)30, scene.getHeight()-21, (double) 20);
		jumpy.setFill(Color.BLUE);
		
		gun = new Rectangle(jumpy.getCenterX(), jumpy.getCenterY(), 20, 2);
		gun.setFill(Color.RED);
		
		MainMenub = MainMenu;
		
		root.getChildren().add(jumpy);
		root.getChildren().add(gun);
		root.getChildren().add(enemies);
		root.getChildren().add(lasers);
		t = new Text(scene.getWidth()-30, 30, Integer.toString((int) timer));
		root.getChildren().add(t);
		return scene;
	}

	private EventHandler<ActionEvent> oneFrame = new EventHandler<ActionEvent>() {
		public void handle(ActionEvent evt) {
			countframe++;
			timer = timer-(1.0/60);
			if(timer >0){
			root.getChildren().remove(t);
			t = new Text(scene.getWidth()-30, 30, Integer.toString((int) timer));
			t.setFont(new Font(40));
			root.getChildren().add(t);
			}
			if (player.GameWon && backtomenu<1){
				root.getChildren().add(MainMenub);
				backtomenu++;
				GameOver = false;
			}else if(!GameOver && timer >= 0 && !player.GameWon){
				updateSprites();
				foes.createnemies(countframe, scene.getHeight(), scene.getWidth(), enemies, false);
				hitdetection(enemies, lasers);
				if(!player.immortality)	playerhitdet(jumpy, enemies);
				score++;									
			} else {
				if(backtomenu < 1){
					root.getChildren().removeAll();
					root.getChildren().clear();
					root.getChildren().add(MainMenub);
					backtomenu++;						
					GameOver= false;
				}
			}
	};
		};
		
	public KeyFrame start () {
		return new KeyFrame(Duration.millis(1000/60), oneFrame);
	}				

	private void updateSprites(){
		player.updatemovement(scene);
		player.regressmovement(scene);
		player.updatelocation(countframe, scene, jumpy, gun, lasers);
		player.updatelaser(lasers, scene);
		foes.updatenemies(enemies, true);
	}
	
	public void hitdetection(Group foes, Group lsrs){
		Stack<Node> fotracker = new Stack<Node>();
		Stack<Node> lstracker = new Stack<Node>();
		for(Node foe:foes.getChildren()){
			for(Node lsr:lsrs.getChildren()){
				Rectangle fo = (Rectangle) foe;
				Circle ls = (Circle) lsr;
				if(intersect(fo, ls)){
					System.out.println("Hit");			
					fotracker.add(foe);
					lstracker.add(lsr);
				}
			}	
		}
		for(Node n:lstracker){
		lasers.getChildren().remove(n);
		}
		for(Node n:fotracker){
		enemies.getChildren().remove(n);
		}
	}
	public void playerhitdet(Circle player, Group foes){
		for(Node n:foes.getChildren()){
			Rectangle foe = (Rectangle) n;
			if(intersect(foe, player)){
				GameOver = true;
			}
		}
	}
	
	public boolean intersect(Shape foe, Shape lsr){
		Path b =(Path) Shape.intersect(foe, lsr);
		if(!b.getElements().isEmpty()){
			return true;
		}		
		return false;
	}
}
