package gui;

import java.util.ArrayList;

import Exception.NoGameCreated;
import Exception.NoRefereeException;
import Exception.NullResultException;
import Exception.TooFewAthleteException;
import Ozlympic.Driver;
import Ozlympic.Game;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class RunningGame implements EventHandler<ActionEvent> {

	Driver driver;
	Stage reopenStage;

	RunningGame(Driver driver, Stage reopenStage) {
		this.driver = driver;
		this.reopenStage = reopenStage;
	}

	public void handle(ActionEvent e) {


    	
    try{
    	double coefficient=1;
    	Stage runningGame = new Stage();
        BorderPane border=new BorderPane();  
        Scene scene=new Scene(border, 500, 500);  
          
        driver.starGame();
        
        Line endLine  = new Line(450, 53,   450,   450);
        endLine.setStroke(Color.RED);
        Line startLine  = new Line(5, 53,   5,   450);
    	//RunningGame rg=new RunningGame(driver,games, reopenStage);
    	//String gameTypeName=rg.getGameType();
    	
    	Game gameInfo = driver.getGame(false).get(0);
    	
    	
    	final Text official=new Text(100,20,"Offical: "+driver.getOffName(gameInfo.getOfficialID()));
    //	final Text gameName=new Text(250, 20, "Game Type: "+ gameTypeName);
        final Text gameID=new Text(0, 20, "Game ID: "+gameInfo.getGameID());
        
        ArrayList <Text> athleteList=new ArrayList <Text>();
        int x=140; //set the x for first athlete.
        for(int i=0;i<gameInfo.getAthletes().size();i++)
        {
        	athleteList.add(new Text(10,x,driver.getAthleteInf(gameInfo.getAthletes().get(i))[0]));
        	x+=40;
        }
        
        final Button showResult=new Button("Show Result");
        border.setBottom(showResult);
        ShowResult ShowResult=new ShowResult(gameInfo,driver,runningGame);
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
        border.getChildren().addAll(official,gameID,endLine,startLine);
        for(int j=0;j<athleteList.size();j++)
        {
        	border.getChildren().add(athleteList.get(j));
        }
        border.getChildren().add(new Text(425, 50, "End point"));
        border.getChildren().add(new Text(0, 50, "Start point"));

         
        //创建时间轴  
        final Timeline timeline=new Timeline();          

        ArrayList<KeyValue> keyValue=new  ArrayList<KeyValue>();
        for(int j=0;j<athleteList.size();j++)
        {
        	keyValue.add(new KeyValue(athleteList.get(j).xProperty(), 450));
        }
        
        final KeyValue kv11=new KeyValue(runner.xProperty(),450);
        switch(driver.getGameType())
        {
        	case 1 :coefficient=0.02;break;
        	case 2 :coefficient=0.1;break;
        	case 3 :coefficient=1;break;
        }
        ArrayList<KeyFrame> keyFrame=new  ArrayList<KeyFrame>();
        for(int j=0;j<athleteList.size();j++)
        {
        	keyFrame.add(new KeyFrame(Duration.millis(coefficient*1000*gameInfo.getResults().get(j)), keyValue.get(j)));
        }

        final KeyFrame kf11=new KeyFrame(Duration.millis(5000), kv11);
        //将关键帧加到时间轴中  
        for(int j=0;j<athleteList.size();j++)
        {
        	timeline.getKeyFrames().add(keyFrame.get(j));
        }    
        timeline.getKeyFrames().add(kf11);
        timeline.play();//运行  

        runningGame.setScene(scene);
    	runningGame.show(); // Display the stage   	
    }
    catch(NoGameCreated e1)
    {
    	Stage errorWarning = new Stage();
    	errorWarning.setTitle("Warning");
    	Text warningText=new Text(e1.getMessage());
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
    } catch (TooFewAthleteException e1) {
    	Stage errorWarning = new Stage();
    	errorWarning.setTitle("Warning");
    	Text warningText=new Text(e1.getMessage());
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
	} catch (NoRefereeException e1) {
		Stage errorWarning = new Stage();
    	errorWarning.setTitle("Warning");
    	Text warningText=new Text(e1.getMessage());
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
	} catch (NullResultException e1) {
		// TODO 自动生成的 catch 块
		e1.printStackTrace();
	} 
    
    
	}  
	

	public String getGameType() throws Exception {
		String gameType = "Unknown type";
		if (Ozlympic.gameTypeName.equals("Unknown type")) {
			throw new NoGameCreated();
		} else
			gameType = Ozlympic.gameTypeName;
		return gameType;
	}
}