package Ozlympic;


import java.util.ArrayList;
import java.util.Scanner;

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
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;


public class MultiWindow extends Application {
	
	private Scanner keyBoard = new Scanner(System.in);
	private ModifyData modifyData;
	private ArrayList<Athletes> athletes = new ArrayList<Athletes>();
	private ArrayList<Officials> officials = new ArrayList<Officials>();
	static ArrayList<Game> games = new ArrayList<Game>();
	private final int MAX_ATHLETE = 8;// maximum athlete in a game
	private final int MIN_ATHLETE = 4;// minimum athlete in a game
	private int gameIDIndex = -1;// the present game index
	private int predictIndex = -1;


	public MultiWindow() {
		// initialize data from file
		modifyData = new ModifyData(games, officials, athletes);
		modifyData.loadData();
	}
	  static int gameType = -1;
	  static String gameTypeName="Unknown type"; 
	  static String gameId=null;
   @Override // Override the start method in the Application class
   public void start(Stage primaryStage) {
	  
	   Driver driver=new Driver();
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
	  Button button5 = new Button("5. Exit");
	    
	  pane.getChildren().add(button1);
	  pane.getChildren().add(button2);
	  pane.getChildren().add(button3);
	  pane.getChildren().add(button4); 
	  pane.getChildren().add(button5);
	    
	  Scene scene = new Scene(pane);
	  primaryStage.setScene(scene); // Place the scene in the stage
	//  scene.getStylesheets().add(MultiWindow.class.getResource("login.css").toExternalForm());
	  primaryStage.show(); // Display the stage
      
	  GameTypeMenu gtm = new GameTypeMenu(primaryStage);
	  button1.setOnAction(gtm);
	  RunningGame runGame=new RunningGame(driver,games,primaryStage);
	  button2.setOnAction(runGame);
	  
	  ShowFinalResult sfr=new ShowFinalResult(games,driver);
	  button3.setOnAction(sfr);
	  
	  ShowFinalPoint showFinalPoint=new ShowFinalPoint(driver,athletes);
	  button4.setOnAction(showFinalPoint);
	//  button4.setOnAction((ActionEvent t)->{driver.showFinalPoint();});
	  button5.setOnAction((ActionEvent t)->{primaryStage.close(); System.exit(0);});
	  

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
		  
		  GameType sGame = new GameType(secondMenu,closeStage,"Swimming",1);
		  GameType cGame = new GameType(secondMenu,closeStage,"Cycling",3);
		  GameType rGame = new GameType(secondMenu,closeStage,"Running",2);
		  button1.setOnAction(sGame);
		  button2.setOnAction(cGame);
		  button3.setOnAction(rGame);
		  
		  Scene scene = new Scene(pane);
	      secondMenu.setScene(scene);
	      secondMenu.show(); // Display the stage
	}
}


class GameType implements EventHandler<ActionEvent> {
	
	String gameTypeName;
	Stage closeStage;
	Stage reopenStage;
	int gameType;
	
	GameType(Stage closeStage,Stage reopenStage,String gameTypeName,int gameType)
	{
		this.closeStage=closeStage;
		this.gameTypeName=gameTypeName;
		this.reopenStage=reopenStage;
		this.gameType=gameType;
	}
	public void handle(ActionEvent e) {
		MultiWindow.gameTypeName=gameTypeName;
		MultiWindow.gameType=gameType;
		closeStage.close();
		reopenStage.show();
	}
}



class RunningGame implements EventHandler<ActionEvent> {
	String gameType;
	Driver driver;
	ArrayList<Game> games;
	Stage reopenStage;
	RunningGame(Driver driver,ArrayList<Game> games, Stage reopenStage)
	{
		this.driver=driver;
		this.games=games;
		this.reopenStage=reopenStage;
	}
	
	public void handle(ActionEvent e) {

    	
    try{
    	Stage runningGame = new Stage();
        BorderPane border=new BorderPane();  
        Scene scene=new Scene(border, 500, 500);  
          
        driver.starGame(MultiWindow.gameType);
        
        Line endLine  = new Line(450, 53,   450,   450);
        endLine.setStroke(Color.RED);
        Line startLine  = new Line(5, 53,   5,   450);
    	RunningGame rg=new RunningGame(driver,games, reopenStage);
    	gameType=rg.getGameType();
    	
    	final Text official=new Text(100,20,"Offical: "+driver.getOffName(games.get(driver.gameIDIndex).getOfficialID()));
    	final Text gameName=new Text(250, 20, "Game Type: "+ gameType);
        final Text gameID=new Text(0, 20, "Game ID: "+games.get(driver.gameIDIndex).getGameID());
        final Text text1=new Text(10, 140, driver.getAthleteInf(MultiWindow.games.get(driver.gameIDIndex).getAthletes().get(0))[0]);
        final Text text2=new Text(10, 180, driver.getAthleteInf(MultiWindow.games.get(driver.gameIDIndex).getAthletes().get(1))[0]);
        final Text text3=new Text(10, 220, driver.getAthleteInf(MultiWindow.games.get(driver.gameIDIndex).getAthletes().get(2))[0]);
        final Text text4=new Text(10, 260, driver.getAthleteInf(MultiWindow.games.get(driver.gameIDIndex).getAthletes().get(3))[0]);
        final Text text5=new Text(10, 300, driver.getAthleteInf(MultiWindow.games.get(driver.gameIDIndex).getAthletes().get(4))[0]);
        final Text text6=new Text(10, 340, driver.getAthleteInf(MultiWindow.games.get(driver.gameIDIndex).getAthletes().get(5))[0]);
        final Text text7=new Text(10, 380, driver.getAthleteInf(MultiWindow.games.get(driver.gameIDIndex).getAthletes().get(6))[0]);
        final Text text8=new Text(10, 420, driver.getAthleteInf(MultiWindow.games.get(driver.gameIDIndex).getAthletes().get(7))[0]);
        final Button showResult=new Button("Show Result");
        border.setBottom(showResult);
        ShowResult ShowResult=new ShowResult(games,driver,runningGame);
        showResult.setOnAction(ShowResult);
    //    final Text Champion=new Text(200, 250, "Champion is Terry");
    //    Champion.visibleProperty();
    //   Champion.setOpacity(0);

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
        border.getChildren().addAll(official,gameName,gameID,text1,text2,text3,text4,text5,text6,text7,text8,endLine,startLine);
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
  //      final KeyValue kv9=new KeyValue(Champion.opacityProperty(),1);
  //      final KeyValue kv10=new KeyValue(Champion.opacityProperty(),0);
        final KeyValue kv11=new KeyValue(runner.xProperty(),450);
        final KeyFrame kf1=new KeyFrame(Duration.millis(1000*games.get(driver.gameIDIndex).getResults().get(0)), kv1);  
        final KeyFrame kf2=new KeyFrame(Duration.millis(1000*games.get(driver.gameIDIndex).getResults().get(1)), kv2); 
        final KeyFrame kf3=new KeyFrame(Duration.millis(1000*games.get(driver.gameIDIndex).getResults().get(2)), kv3);  
        final KeyFrame kf4=new KeyFrame(Duration.millis(1000*games.get(driver.gameIDIndex).getResults().get(3)), kv4);
        final KeyFrame kf5=new KeyFrame(Duration.millis(1000*games.get(driver.gameIDIndex).getResults().get(4)), kv5);  
        final KeyFrame kf6=new KeyFrame(Duration.millis(1000*games.get(driver.gameIDIndex).getResults().get(5)), kv6); 
        final KeyFrame kf7=new KeyFrame(Duration.millis(1000*games.get(driver.gameIDIndex).getResults().get(6)), kv7);  
        final KeyFrame kf8=new KeyFrame(Duration.millis(1000*games.get(driver.gameIDIndex).getResults().get(7)), kv8);
        final KeyFrame kf11=new KeyFrame(Duration.millis(5000), kv11);
        //将关键帧加到时间轴中  
        timeline.getKeyFrames().addAll(kf1,kf2,kf3,kf4,kf5,kf6,kf7,kf8,kf11);
   /*     timeline.getKeyFrames().addAll(
                new KeyFrame(new Duration(14000) // set start position at 0
                		,kv10
                		),
                new KeyFrame(new Duration(15000), // set end position at 40s
                		kv9)); */
    
    
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
		Button closeWindow=new Button("Ok");
		closeWindow.setOnAction((ActionEvent t)->{errorWarning.close();});
    	
    	Scene sceneWarning = new Scene(pane,350,100);
    	pane.setCenter(warningText);
    	pane.setLeft(warning);
    	pane.setBottom(closeWindow);
    	
    	errorWarning.setScene(sceneWarning); 
    	errorWarning.show();
    } catch (Exception e1) {
		e1.printStackTrace();
	} 
    
    
	}  
	
	public String getGameType() throws Exception 
	{
		String gameType="Unknown type";
		if(MultiWindow.gameTypeName.equals("Unknown type"))
		{   
			throw new NoGameCreated();
		}
		else 
		gameType=MultiWindow.gameTypeName;
		return gameType;
	}
}

class ShowFinalPoint implements EventHandler<ActionEvent> {

	ArrayList<Athletes> athletes;
	Driver driver;
	
	ShowFinalPoint (Driver driver,ArrayList<Athletes> athletes)
	{
		this.athletes=athletes;
		this.driver=driver;
	}
	@Override
	public void handle(ActionEvent event) {
		
		Stage finalPoint = new Stage();
		finalPoint.setTitle("Final Point"); 
		GridPane  border=new GridPane ();  
		border.setPadding(new Insets(5));
		border.setHgap(20);
		border.setVgap(5);
        Scene scene=new Scene(border, 400, 600); 
        final Text Name=new Text( "Name");
        final Text Age=new Text("Age");
        final Text State=new Text("State");
        final Text AthleteType=new Text("Athlete Type");
        final Text Point=new Text("Point");
        border.add(Name, 0, 0);
        border.add(Age, 1, 0);
        border.add(State, 2, 0);
        border.add(AthleteType, 3, 0);
        border.add(Point, 4, 0);
        
        
        String[] athleteInf = new String[5];
		int countAthlete = 0;
		countAthlete = athletes.size();
		if (countAthlete == 0) {
			Stage errorWarning = new Stage();
	    	errorWarning.setTitle("Warning");
	    	Text warningText=new Text("Thers isn't any athlete's information!");
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
		} else {
			for (int i = 0; i < countAthlete; i++) {
				athleteInf = driver.getAthleteInf(athletes.get(i).getUserID());
				border.add(new Text(athleteInf[0]),0,i+1);
				border.add(new Text(athleteInf[1]),1,i+1);
				border.add(new Text(athleteInf[2]),2,i+1);
				border.add(new Text(athleteInf[3]),3,i+1);
				border.add(new Text(athleteInf[4]),4,i+1);
			}
		}        
        finalPoint.setScene(scene);
        finalPoint.show(); // Display the stage
	}	
}


class ShowResult implements EventHandler<ActionEvent> {

	ArrayList<Game> games;
	Driver driver;
	Stage closeStage;
	
	ShowResult (ArrayList<Game> games,Driver driver, Stage closeStage)
	{
		this.games=games;
		this.driver=driver;
		this.closeStage=closeStage;
	}
	@Override
	public void handle(ActionEvent event) {
		
		closeStage.close();
		Stage showResult = new Stage();
		showResult.setTitle("Final Point"); 
		GridPane  border=new GridPane ();  
		border.setPadding(new Insets(5));
		border.setHgap(20);
		border.setVgap(5);
        Scene scene=new Scene(border, 500, 400); 
        final Text GameNumber=new Text( "GameNumber: "+games.get(driver.gameIDIndex).getGameID());
        final Text OfficalName=new Text( "OfficalName: "+driver.getOffName(MultiWindow.games.get(driver.gameIDIndex).getOfficialID()));
        final Text Name=new Text( "Name");
        final Text Age=new Text("Age");
        final Text State=new Text("State");
        final Text AthleteType=new Text("Athlete Type");
        final Text Time=new Text("Time");
        final Text Point=new Text("Point");
        border.add(GameNumber, 0, 0);
        border.add(OfficalName, 0, 1);
        border.add(Name, 0, 2);
        border.add(Age, 1, 2);
        border.add(State, 2, 2);
        border.add(AthleteType, 3, 2);
        border.add(Time, 4, 2);
        border.add(Point, 5, 2);
        
		Button closeWindow=new Button("Ok");
		closeWindow.setOnAction((ActionEvent t)->{showResult.close();});
		 border.add(closeWindow, 2, 0);
        
  
		String[] athleteinf = new String[5];
		int time = 0;
		int point = 0;
		int countAthlete = 0;
		if (games.get(driver.gameIDIndex).getResults().size() == 0)
			return;// game maybe haven't run yet
		countAthlete = MultiWindow.games.get(driver.gameIDIndex).getAthletes().size();

		for (int i = 0; i < countAthlete; i++) {
			athleteinf = driver.getAthleteInf(games.get(driver.gameIDIndex).getAthletes().get(i));
			time = MultiWindow.games.get(driver.gameIDIndex).getResults().get(i);
			point = MultiWindow.games.get(driver.gameIDIndex).getPoints().get(i);
			border.add(new Text(athleteinf[0]), 0,i+3);
			border.add(new Text(athleteinf[1]), 1,i+3);
			border.add(new Text(athleteinf[2]), 2,i+3);
			border.add(new Text(athleteinf[3]), 3,i+3);
			border.add(new Text(time+""), 4,i+3);
			border.add(new Text(point+""), 5,i+3);
			
		}
     
		showResult.setScene(scene);
		showResult.show(); // Display the stage
	}	
}
class ShowFinalResult implements EventHandler<ActionEvent> {

	ArrayList<Game> games;
	Driver driver;
	
	ShowFinalResult(ArrayList<Game> games,Driver driver)
	{
		this.driver=driver;
		this.games=games;
	}
	
	@Override
	public void handle(ActionEvent arg0) {
		int countGame = games.size();
		
		Stage showFinalResult = new Stage();
		showFinalResult.setTitle("Result"); 

		
		GridPane  border=new GridPane (); 
	    ScrollPane scrollPane = new ScrollPane();
	    scrollPane.setContent(border);
	    
		border.setPadding(new Insets(5)); 
		border.setHgap(20);
		border.setVgap(5);
		Scene scene=new Scene(scrollPane,450, 400);         
		
		for(int i=0;i<countGame;i++)
		{
	        Text GameNumber=new Text( "GameNumber: "+games.get(i).getGameID());
	        Text OfficalName=new Text( "OfficalName: "+driver.getOffName(MultiWindow.games.get(i).getOfficialID()));
	        final Text Name=new Text( "Name");
	        final Text Age=new Text("Age");
	        final Text State=new Text("State");
	        final Text AthleteType=new Text("Athlete Type");
	        final Text Time=new Text("Time");
	        final Text Point=new Text("Point");
	        border.add(GameNumber, 0, i*12+0);
	        border.add(OfficalName, 0, i*12+1);
	        border.add(Name, 0, i*12+2);
	        border.add(Age, 1, i*12+2);
	        border.add(State, 2, i*12+2);
	        border.add(AthleteType, 3, i*12+2);
	        border.add(Time, 4, i*12+2);
	        border.add(Point, 5, i*12+2);
	
	        int countAthlete=games.get(i).getAthletes().size();
	        for(int j=0;j<countAthlete;j++)
	        {
	    		String[] athleteinf = driver.getAthleteInf(games.get(i).getAthletes().get(j));
	        	int time = MultiWindow.games.get(i).getResults().get(j);
				int point = MultiWindow.games.get(i).getPoints().get(j);
				border.add(new Text(athleteinf[0]), 0,i*12+j+3);
				border.add(new Text(athleteinf[1]), 1,i*12+j+3);
				border.add(new Text(athleteinf[2]), 2,i*12+j+3);
				border.add(new Text(athleteinf[3]), 3,i*12+j+3);
				border.add(new Text(time+""), 4,i*12+j+3);
				border.add(new Text(point+""), 5,i*12+j+3);
	        }
        
		}
		showFinalResult.setScene(scene);
		showFinalResult.show(); // Display the stage
	}
}