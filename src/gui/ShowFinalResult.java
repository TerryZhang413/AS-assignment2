package gui;

import java.util.ArrayList;

import Exception.NullResultException;
import Ozlympic.Driver;
import Ozlympic.Game;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ShowFinalResult implements EventHandler<ActionEvent> {

	
	Driver driver;

	ShowFinalResult(Driver driver) {
		this.driver = driver;
	}

	@Override
	public void handle(ActionEvent event) {
		try {
			ArrayList<Game> games;
			games=driver.getGame(true);
			int countGame = games.size();
	
			Stage showFinalResult = new Stage();
			showFinalResult.setTitle("Result");
	
			GridPane border = new GridPane();
			ScrollPane scrollPane = new ScrollPane();
			scrollPane.setContent(border);
	
			border.setPadding(new Insets(5));
			border.setHgap(20);
			border.setVgap(5);
	
			Scene scene=new Scene(scrollPane,450, 400);         
			int lastLineNum=0;
			for(int i=0;i<countGame;i++)
			{
		        Text GameNumber=new Text( "GameNumber: "+games.get(i).getGameID());
		        GameNumber.setFill(Color.RED);
		        Text OfficalName=new Text( "OfficalName: "+driver.getOffName(games.get(i).getOfficialID()));
		        final Text Name=new Text( "Name");
		        final Text Age=new Text("Age");
		        final Text State=new Text("State");
		        final Text AthleteType=new Text("Athlete Type");
		        final Text Time=new Text("Time");
		        final Text Point=new Text("Point");
		        border.add(GameNumber, 0, lastLineNum+0);
		        border.add(OfficalName, 0, lastLineNum+1);
		        border.add(Name, 0, lastLineNum+2);
		        border.add(Age, 1, lastLineNum+2);
		        border.add(State, 2, lastLineNum+2);
		        border.add(AthleteType, 3, lastLineNum+2);
		        border.add(Time, 4, lastLineNum+2);
		        border.add(Point, 5, lastLineNum+2);
		        
		         int countAthlete=games.get(i).getAthletes().size();
		        for(int j=0;j<countAthlete;j++)
		        {
		    		String[] athleteinf = driver.getAthleteInf(games.get(i).getAthletes().get(j));
		        	int time = games.get(i).getResults().get(j);
					int point = games.get(i).getPoints().get(j);
					border.add(new Text(athleteinf[0]), 0,lastLineNum+j+3);
					border.add(new Text(athleteinf[1]), 1,lastLineNum+j+3);
					border.add(new Text(athleteinf[2]), 2,lastLineNum+j+3);
					border.add(new Text(athleteinf[3]), 3,lastLineNum+j+3);
					border.add(new Text(time+""), 4,lastLineNum+j+3);
					border.add(new Text(point+""), 5,lastLineNum+j+3);
		        }
		        lastLineNum=lastLineNum+countAthlete+4;
			}
				showFinalResult.setScene(scene);
				showFinalResult.show(); // Display the stage
		} catch (NullResultException e) {
			Stage errorWarning = new Stage();
	    	errorWarning.setTitle("Warning");
	    	Text warningText=new Text(e.getMessage());
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
		}
	}
	
}