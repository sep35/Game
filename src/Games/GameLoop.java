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

public class GameLoop {
	double countframe = 0;
	int score = 0, backtomenu = 0;	
	private Stage home;
	private Scene s;
	private Circle jumpy, sun;
	private Maincharacter player;
	private Enemies foes = new Enemies();
	private Group lasers = new Group(), enemies = new Group(), clouds = new Group();
	private Scene scene;
	private Rectangle gun;
	private Group root;
	private Ellipse cloud, cloud1, cloud2, cloud3, cloud4, cloud5, cloud8, cloud9, cloud10;
	private boolean GameOver = false, GameWon = false;
	private Text scoreofgame = new Text(), t = new Text();
	private double timer = 10;
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
		
		sun = new Circle(0, 0, 200);
		sun.setFill(Color.YELLOW);
		
		gun = new Rectangle(jumpy.getCenterX(), jumpy.getCenterY(), 20, 2);
		gun.setFill(Color.RED);

		Rectangle ground = new Rectangle(0,2*scene.getHeight()/3,scene.getWidth(), scene.getHeight()/3);
		ground.setFill(Color.GREEN);
		
		generateclouds();
		MainMenub = MainMenu;
		
    	root.getChildren().add(ground);
    	root.getChildren().add(sun);
		root.getChildren().add(jumpy);
		root.getChildren().add(clouds);
		root.getChildren().add(gun);
		root.getChildren().add(enemies);
		root.getChildren().add(lasers);
		t = new Text(scene.getWidth()-30, 30, Integer.toString((int) timer));
		root.getChildren().add(t);
		return scene;
	}
	public void generateclouds(){
		cloud  = new Ellipse(50, 50, 60, 40);   cloud.setFill(Color.WHITESMOKE); cloud.setOpacity(.5);
		cloud1 = new Ellipse(570, 200, 60, 40);	cloud1.setFill(Color.WHITESMOKE);cloud1.setOpacity(.5);
		cloud2 = new Ellipse(400, 120, 60, 40 );cloud2.setFill(Color.WHITESMOKE);cloud2.setOpacity(.5);
		cloud3 = new Ellipse(75, 210, 60, 40);  cloud3.setFill(Color.WHITESMOKE);cloud3.setOpacity(.5);
		cloud4 = new Ellipse(300, 250, 60, 40); cloud4.setFill(Color.WHITESMOKE);cloud4.setOpacity(.5);
		cloud5 = new Ellipse(200, 90, 60, 40);  cloud5.setFill(Color.WHITESMOKE);cloud5.setOpacity(.5);
		cloud8 = new Ellipse(500, 280, 60, 40 );cloud8.setFill(Color.WHITESMOKE);cloud8.setOpacity(.5);
		cloud9 = new Ellipse(650, 100, 60, 40); cloud9.setFill(Color.WHITESMOKE);cloud9.setOpacity(.5);
		cloud10 = new Ellipse(740, 260, 60,40); cloud10.setFill(Color.WHITESMOKE);cloud10.setOpacity(.5);
		
		clouds.getChildren().add(cloud);
		clouds.getChildren().add(cloud1);
		clouds.getChildren().add(cloud2);
		clouds.getChildren().add(cloud3);
		clouds.getChildren().add(cloud4);
		clouds.getChildren().add(cloud5);
		clouds.getChildren().add(cloud8);
		clouds.getChildren().add(cloud9);
		clouds.getChildren().add(cloud10);	
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
					if(createstage < 1){
						foes.createnemies(countframe, scene.getHeight(), scene.getWidth(), enemies, true);
						createstage++;
					}
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
		if(player.goright) movebackgroundright();
		if(player.goleft) movebackgroundleft();
		if(player.firegod) player.updatelaser(lasers, scene);
		foes.updatenemies(enemies, false);
	}

	
	public void movebackgroundleft(){
		for(Node n:clouds.getChildren()){
			Ellipse cloud = (Ellipse) n;
			cloud.setCenterX(cloud.getCenterX()+15);
		}
		sun.setCenterX(sun.getCenterX()-5);
	}
	public void movebackgroundright(){
		for(Node n:clouds.getChildren()){
			Ellipse cloud = (Ellipse) n;
			cloud.setCenterX(cloud.getCenterX()-15);
		}
		sun.setCenterX(sun.getCenterX()+5);
	}
	
	public void hitdetection(Group foes, Group lsrs){
		Stack<Node> fotracker = new Stack<Node>();
		Stack<Node> lstracker = new Stack<Node>();
		for(Node foe:foes.getChildren()){
			for(Node lsr:lsrs.getChildren()){
				Rectangle fo = (Rectangle) foe;
				Circle ls = (Circle) lsr;
				if(intersect(fo, ls)){		
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
				System.out.println("U DEAD");
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
