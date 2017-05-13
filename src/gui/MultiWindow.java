package gui;


import Exception.NoGameCreated;
import javafx.animation.KeyFrame;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
public class MultiWindow extends Application {
	  static String gameType="Unknown type"; 
	  static String gameId=null;
   @Override // Override the start method in the Application class
   public void start(Stage primaryStage) {
	  
	  primaryStage.setTitle("Ozlympic Game"); // Set the stage title
	  VBox pane = new VBox();
	  pane.setPadding(new Insets(50, 100, 50, 100));
	  pane.setSpacing(10);

	   // Create a scene and place a button in the scene
	  pane.getChildren().add(new Text("Ozlympic Game"));
	  pane.getChildren().add(new Text("==================================="));
	  Button button1 = new Button("1. Select a game to run");
	  Button button2 = new Button("2. Start the game");
	  Button button3 = new Button("3. Display the final results of all games");
	  Button button4 = new Button("4. Display the points of all athletes");
	    
	  pane.getChildren().add(button1);
	  pane.getChildren().add(button2);
	  pane.getChildren().add(button3);
	  pane.getChildren().add(button4);
	    
	  Scene scene = new Scene(pane);
	  primaryStage.setScene(scene); // Place the scene in the stage
	//  scene.getStylesheets().add(MultiWindow.class.getResource("login.css").toExternalForm());
	  primaryStage.show(); // Display the stage
      
	  GameTypeMenu gtm = new GameTypeMenu(primaryStage);
	  button1.setOnAction(gtm);
	  RunningGame runGame=new RunningGame();
	  button2.setOnAction(runGame);
	  

   }
   
   public static void main(String[] args) {
	   Application.setUserAgentStylesheet(STYLESHEET_CASPIAN);
	   
       Application.launch(args);
   }
   
}

class GameTypeMenu implements EventHandler<ActionEvent> {


		Stage closeStage;
	
		GameTypeMenu(Stage closeStage)
		{
		this.closeStage=closeStage;
		}
	
	public void handle(ActionEvent e) {
		  closeStage.close();
	      Stage secondMenu = new Stage(); // Create a new stage
	      secondMenu.setTitle("secondMenu"); // Set the stage title
	      // Set a scene with a button in the stage
	      VBox pane = new VBox();
		  pane.setPadding(new Insets(50, 100, 50, 100));
		  pane.setSpacing(10);
	      pane.getChildren().add(new Text("Ozlympic Game"));
		  pane.getChildren().add(new Text("==================================="));
		  pane.getChildren().add(new Text("Select a sport from 1 to 3"));
		  Button button1 = new Button("1. Swimming");
		  Button button2 = new Button("2. Cycling");
		  Button button3 = new Button("3. Running");

		  pane.getChildren().add(button1);
		  pane.getChildren().add(button2);
		  pane.getChildren().add(button3);
		  
		  GameType sGame = new GameType(secondMenu,closeStage,"Swimming");
		  GameType cGame = new GameType(secondMenu,closeStage,"Cycling");
		  GameType rGame = new GameType(secondMenu,closeStage,"Running");
		  button1.setOnAction(sGame);
		  button2.setOnAction(cGame);
		  button3.setOnAction(rGame);
		  
		  Scene scene = new Scene(pane);
	      secondMenu.setScene(scene);
	      secondMenu.show(); // Display the stage
	}
}


class GameType implements EventHandler<ActionEvent> {
	
	String gameType;
	Stage closeStage;
	Stage reopenStage;
	
	GameType(Stage closeStage,Stage reopenStage,String gameType)
	{
		this.closeStage=closeStage;
		this.gameType=gameType;
		this.reopenStage=reopenStage;
	}
	public void handle(ActionEvent e) {
		MultiWindow.gameType=gameType;
		closeStage.close();
		reopenStage.show();
	}
}



class RunningGame implements EventHandler<ActionEvent> {
	String gameType;
	public void handle(ActionEvent e) {

    	
    try{
    	Stage runningGame = new Stage();
        BorderPane border=new BorderPane();  
        Scene scene=new Scene(border, 500, 500);  
          
        Line endLine  = new Line(450, 53,   450,   500);
        endLine.setStroke(Color.RED);
        Line startLine  = new Line(5, 53,   5,   500);
    	RunningGame rg=new RunningGame();
    	gameType=rg.getGameType();
    	final Text gameName=new Text(150, 20, "Game Type: "+ gameType);
        final Text gameID=new Text(0, 20, "Game ID: "+MultiWindow.gameId);
        final Text text1=new Text(10, 140, "Terry");
        final Text text2=new Text(10, 180, "John");
        final Text text3=new Text(10, 220, "Alex");
        final Text text4=new Text(10, 260, "Peter");
        final Text text5=new Text(10, 300, "Amy");
        final Text text6=new Text(10, 340, "Frank");
        final Text text7=new Text(10, 380, "Levis");
        final Text text8=new Text(10, 420, "Jack");
        final Text Champion=new Text(200, 250, "Champion is Terry");
        Champion.visibleProperty();
        Champion.setOpacity(0);

		final ImageView runner = new ImageView(
       	      new Image("image/runner.png")
       	    );
	//	final ImageView imageView2 = new ImageView(
    //  	      new Image("image/grown.png")
    //  	    );
		runner.setLayoutX(10);
		runner.setLayoutY(100);

		border.getChildren().add(runner);
       // border.getChildren().add(new ImageView(image));
        border.getChildren().add(gameName);
        border.getChildren().add(gameID);
        border.getChildren().add(text1);
        border.getChildren().add(text2);
        border.getChildren().add(text3);
        border.getChildren().add(text4);
        border.getChildren().add(text5);
        border.getChildren().add(text6);
        border.getChildren().add(text7);
        border.getChildren().add(text8);
        border.getChildren().add(endLine);
        border.getChildren().add(startLine);
        border.getChildren().add(Champion);
        border.getChildren().add(new Text(425, 50, "End point"));
        border.getChildren().add(new Text(0, 50, "Start point"));

         
        //创建时间轴  
        final Timeline timeline=new Timeline();  
//        timeline.setCycleCount(Timeline.INDEFINITE);//设置周期运行循环往复  
     //   timeline.setAutoReverse(true);//设置动画的自动往返效果  
          
        //将x的位置在500ms内移动到300处  
        final KeyValue kv1=new KeyValue(text1.xProperty(), 450);
        final KeyValue kv2=new KeyValue(text2.xProperty(), 450);
        final KeyValue kv3=new KeyValue(text3.xProperty(), 450);
        final KeyValue kv4=new KeyValue(text4.xProperty(), 450);
        final KeyValue kv5=new KeyValue(text5.xProperty(), 450);
        final KeyValue kv6=new KeyValue(text6.xProperty(), 450);
        final KeyValue kv7=new KeyValue(text7.xProperty(), 450);
        final KeyValue kv8=new KeyValue(text8.xProperty(), 450);     
        final KeyValue kv9=new KeyValue(Champion.opacityProperty(),1);
        final KeyValue kv10=new KeyValue(Champion.opacityProperty(),0);
        final KeyValue kv11=new KeyValue(runner.xProperty(),450);
        final KeyFrame kf1=new KeyFrame(Duration.millis(5000), kv1);  
        final KeyFrame kf2=new KeyFrame(Duration.millis(8000), kv2); 
        final KeyFrame kf3=new KeyFrame(Duration.millis(14000), kv3);  
        final KeyFrame kf4=new KeyFrame(Duration.millis(8000), kv4);
        final KeyFrame kf5=new KeyFrame(Duration.millis(7000), kv5);  
        final KeyFrame kf6=new KeyFrame(Duration.millis(8700), kv6); 
        final KeyFrame kf7=new KeyFrame(Duration.millis(9000), kv7);  
        final KeyFrame kf8=new KeyFrame(Duration.millis(9000), kv8);
        final KeyFrame kf11=new KeyFrame(Duration.millis(5000), kv11);
        //将关键帧加到时间轴中  
        timeline.getKeyFrames().addAll(kf1,kf2,kf3,kf4,kf5,kf6,kf7,kf8,kf11);
        timeline.getKeyFrames().addAll(
                new KeyFrame(new Duration(14000) // set start position at 0
                		,kv10
                		),
                new KeyFrame(new Duration(15000), // set end position at 40s
                		kv9));
    
    
        timeline.play();//运行  

        runningGame.setScene(scene);
    	runningGame.show(); // Display the stage
    	
    }
    catch(NoGameCreated e1)
    {
    	Stage errorWarning = new Stage();
    	errorWarning.setTitle("Warning");
    	Text warningText=new Text("You need to Select a game firstly!!!");
    	BorderPane pane=new BorderPane();
    	pane.setPadding(new Insets(10,20, 10, 20));
    	
		final ImageView warning = new ImageView(
      	      new Image("image/warning.png")
      	    );
		Button bt1=new Button("Ok");
		
    	
    	Scene sceneWarning = new Scene(pane,350,100);
    	pane.setCenter(warningText);
    	pane.setLeft(warning);
    	pane.setBottom(bt1);
    	bt1.setOnAction((ActionEvent t)->{errorWarning.close();});
    	errorWarning.setScene(sceneWarning); 
    	errorWarning.show();
    } catch (Exception e1) {
		e1.printStackTrace();
	} 
    
    
	}  
	
	public String getGameType() throws Exception 
	{
		String gameType="Unknown type";
		if(MultiWindow.gameType.equals("Unknown type"))
		{   
			throw new NoGameCreated();
		}
		else 
		gameType=MultiWindow.gameType;
		return gameType;
	}
}


