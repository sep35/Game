package Games;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class JumpyJacky extends Application {
	public int height = 800, width = 600;
	public GameLoop level1;
	public GameLoop2 level2;
	public Scene beginning;
	private Stage s = new Stage();
	private Button btn = new Button();
	private Button level = new Button();
	private Button cheats = new Button();

	public void start (Stage s){
		s.setTitle("JumpyJacky");
		level1 = new GameLoop();
		level2 = new GameLoop2();
		
		cheats.setText("Here are some Cheats to survive by");
		cheats.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				showCheats();
			}
		});

		btn.setText("Play Level 1");
		btn.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {				
				showLvlOneScreen();
			}
		}); 
		
		level.setText("Play level 2");
		level.setOnMouseClicked(new EventHandler<MouseEvent>(){
			public void handle(MouseEvent event){
				showLvlTwoScreen();
			}
		});
		
		level.setLayoutX(width/2);
		level.setLayoutY(height/2 - 100);
		showBeginScreen();    	
	}

	void showBeginScreen() {
		Group root = new Group();
		btn.setLayoutX(width/2);
		btn.setLayoutY(height/2);
		Text win1 = new Text(width/4, 50, "you can win level one by making it to the other end of the map in time");
		Text win2 = new Text(width/4, 70, "you win level two by surviving the clock ");
		root.getChildren().addAll(btn, cheats, level, win1, win2); 
		beginning = new Scene(root, 800, 600, Color.AQUAMARINE);
		s.setScene(beginning);
		s.show();	
	}

	private void showLvlOneScreen() {
		Group root = new Group();
		System.out.println("We got through here");
		Button lvl1 = new Button();
		lvl1.setText("back to main menu");
		root.getChildren().add(lvl1);
		Scene scene = level1.initialize(s, height, width, lvl1);
		s.setScene(scene);
		s.show();
		
		
		lvl1.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				showBeginScreen();				
			}
		});
		
		KeyFrame frame = level1.start();
		Timeline animation = new Timeline();
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
		animation.play();
	}
	
	private void showLvlTwoScreen() {
		Group root = new Group();
		Button lvl2 = new Button();
		lvl2.setText("back to main menu");
		root.getChildren().add(lvl2);
		Scene scene = level2.initialize(s, height, width, lvl2);
		s.setScene(scene);
		s.show();
		
		lvl2.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				showBeginScreen();				
			}
		});
		
		KeyFrame frame = level2.start();
		Timeline animation = new Timeline();
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
		animation.play();
	}
	private void showCheats(){
		Group root = new Group();
		
		Text t = new Text(width/2-200, 50, "This is how you can Win but you are strongly advised to not cheat");
		t.setFont(new Font(20));
		
		Text cheat1 = new Text(width/2-30, 150, "Press shift for immortality");
		cheat1.setFont(new Font(20));
		
		Text cheat2 = new Text( width/2-30, 250, "f for fire mode in level 1");
		cheat2.setFont(new Font(20));
		
		Text cheat3 = new Text( width/2-100, 350,"S to stop enemies from moving in level 2");
		cheat3.setFont(new Font(20));
		Button cheater = new Button();
				
		cheater.setText("go back to main menu buddy");
		root.getChildren().addAll(t, cheat1, cheat2, cheat3, cheater); 
		cheater.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				root.getChildren().remove(cheater);
				showBeginScreen();
			}
		});
	
		Scene cheats = new Scene(root, 800, 600, Color.AQUAMARINE);
		s.setScene(cheats);
		s.show();	
	}
	public static void main(String[] args){
		launch(args);
	}

}
