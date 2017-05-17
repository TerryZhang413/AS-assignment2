package gui;

/**
 * @author Yanjie Zhang
 * @content after the real-time simulation,show the result of this game.
 * 
 */
import Ozlympic.Driver;
import Ozlympic.Game;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

class ShowResult implements EventHandler<ActionEvent> {

	Game gameInfo;
	Driver driver;
	Stage closeStage;

	//constructor to get data from last stage
	ShowResult(Game gameInfo, Driver driver, Stage closeStage) {
		this.gameInfo = gameInfo;
		this.driver = driver;
		this.closeStage = closeStage;
	}

	@Override
	public void handle(ActionEvent event) {
		//close last stage
		closeStage.close();
		Stage showResult = new Stage();
		showResult.setTitle("Final Point");
		GridPane border = new GridPane();
		border.setPadding(new Insets(5));
		border.setHgap(20);
		border.setVgap(5);
		//show the result of this game
        Scene scene=new Scene(border, 500, 400); 
        final Text GameNumber=new Text( "GameNumber: "+gameInfo.getGameID());
        final Text OfficalName=new Text( "OfficalName: "+driver.getOffName(gameInfo.getOfficialID()));
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
		if (gameInfo.getResults().size() == 0)
			return;// game maybe haven't run yet
		countAthlete = gameInfo.getAthletes().size();

		for (int i = 0; i < countAthlete; i++) {
			athleteinf = driver.getAthleteInf(gameInfo.getAthletes().get(i));
			//show every athlete's information
			time = gameInfo.getResults().get(i);
			point = gameInfo.getPoints().get(i);
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