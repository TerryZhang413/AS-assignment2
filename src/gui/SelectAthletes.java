package gui;

import java.util.ArrayList;

import Exception.GameFullException;
import Exception.NoThisAthleteException;
import Exception.WrongTypeException;
import Ozlympic.Athletes;
import Ozlympic.Driver;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SelectAthletes implements EventHandler<ActionEvent>{

	Stage closeStage;
	String gameTypeName;
	int gameType;
	Driver driver;
	ArrayList<Athletes> athletes;
	
	SelectAthletes(Stage closeStage, String gameTypeName, int gameType, Driver driver,ArrayList<Athletes> athletes)
	{
		this.closeStage=closeStage;
		this.driver=driver;
		this.gameType=gameType;
		this.gameTypeName=gameTypeName;
		this.athletes=athletes;
	}
	@Override
	public void handle(ActionEvent event) {
		MultiWindow.gameTypeName = gameTypeName;
		driver.setGameType(gameType);
		closeStage.close();
		
		Stage selectedAthlete = new Stage();
		selectedAthlete.setTitle("Selected Athlete");
		GridPane pane = new GridPane();
		pane.setPadding(new Insets(5));
		pane.setHgap(20);
		pane.setVgap(5);
		
		Stage selectAthlete = new Stage();
		selectAthlete.setTitle("Select Athlete");
		GridPane border = new GridPane();
		border.setPadding(new Insets(5));
		border.setHgap(20);
		border.setVgap(5);
		Scene scene = new Scene(border, 400, 550);
		final Text Name = new Text("Name");
		final Text Age = new Text("Age");
		final Text State = new Text("State");
		final Text AthleteType = new Text("Athlete Type");
		border.add(Name, 1, 0);
		border.add(Age, 2, 0);
		border.add(State, 3, 0);
		border.add(AthleteType, 4, 0);

		String[] athleteInf = new String[5];
		int countAthlete = 0;
		countAthlete = athletes.size();
		ArrayList<Button> choose=new ArrayList<Button>();
		if (countAthlete == 0) {
			Stage errorWarning = new Stage();
			errorWarning.setTitle("Warning");
			Text warningText = new Text("Thers isn't any athlete's information!");
			BorderPane warningPane = new BorderPane();
			pane.setPadding(new Insets(10, 20, 10, 20));
			String imagePath = this.getClass().getResource("/").getFile();
			final ImageView warning = new ImageView(new Image(imagePath + "image/warning.png"));
			Button bt1 = new Button("Ok");
			Scene sceneWarning = new Scene(pane, 350, 100);
			warningPane.setCenter(warningText);
			warningPane.setLeft(warning);
			warningPane.setBottom(bt1);
			bt1.setOnAction((ActionEvent t) -> {
				errorWarning.close();
			});
			errorWarning.setScene(sceneWarning);
			errorWarning.show();
		} else {
			for (int i = 0; i < countAthlete; i++) {
				athleteInf = driver.getAthleteInf(athletes.get(i).getUserID());
				border.add(new Text(athleteInf[0]), 1, i + 1);
				border.add(new Text(athleteInf[1]), 2, i + 1);
				border.add(new Text(athleteInf[2]), 3, i + 1);
				border.add(new Text(athleteInf[3]), 4, i + 1);
				choose.add(new Button("Choose"));
				border.add(choose.get(i), 0, i+1);		
				AddAthlete addAthlete=new AddAthlete(athletes.get(i).getUserID(),driver,pane,athletes);
				choose.get(i).setOnAction(addAthlete);
				//border.getChildren().removeAll(choose.get(i));				
			}
		}
		border.add(new Button("Ok"),3,countAthlete+1);		
		selectAthlete.setScene(scene);
		selectAthlete.show(); // Display the stage
		
		Scene scene1 = new Scene(pane, 400, 550);
		pane.add(Name, 1, 0);
		pane.add(Age, 2, 0);
		pane.add(State, 3, 0);
		pane.add(AthleteType, 4, 0);	
		selectedAthlete.setScene(scene1);
		selectedAthlete.show(); // Display the stage
		
	}
}

class AddAthlete implements EventHandler<ActionEvent>{

	String athelteID;
	Driver driver;
	GridPane pane;
	ArrayList<Athletes> athletes;
	
	public AddAthlete(String athelteID,Driver driver, GridPane pane, ArrayList<Athletes> athletes) {
		this.athelteID=athelteID;
		this.driver=driver;
		this.pane=pane;
		this.athletes=athletes;
	}

	@Override
	public void handle(ActionEvent arg0) {
		try {
			driver.addAthlete(athelteID);
			String[] athleteInf = new String[5];
			athleteInf = driver.getAthleteInf(athelteID);
			int Ycount=driver.getPresentAthlete().size();
			pane.add(new Text(athleteInf[0]), 1, Ycount + 1);
			pane.add(new Text(athleteInf[1]), 2, Ycount + 1);
			pane.add(new Text(athleteInf[2]), 3, Ycount + 1);
			pane.add(new Text(athleteInf[3]), 4, Ycount + 1);
			
		} catch (WrongTypeException e) {
	    	Stage errorWarning = new Stage();
	    	errorWarning.setTitle("Warning");
	    	Text warningText=new Text("This athlete's type is wrong!!");
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
		} catch (NoThisAthleteException e) {
	    	Stage errorWarning = new Stage();
	    	errorWarning.setTitle("Warning");
	    	Text warningText=new Text("This athlete is not exist");
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
		} catch (GameFullException e) {
	    	Stage errorWarning = new Stage();
	    	errorWarning.setTitle("Warning");
	    	Text warningText=new Text("No more than 8 athletes!");
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