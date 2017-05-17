package gui;

/**
 * @author Yanjie Zhang
 * @content show the primary stage for the basic menu
 * 
 */

import java.util.ArrayList;

import Exception.NoParticipantDataException;
import Exception.NoneDBConnectionException;
import Ozlympic.Athletes;
import Ozlympic.Driver;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Ozlympic extends Application {

	// private ArrayList<Game> games;
	private ArrayList<Athletes> athletes;

	static int gameType = -1;
	static String gameTypeName = "Unknown type";
	static String gameId = null;

	@Override // Override the start method in the Application class
	public void start(Stage primaryStage) {

		Driver driver = null; //create a new object 
		try {
			driver = new Driver();
			athletes = driver.getAthelte(); //get the athlete list
		} catch (NoParticipantDataException e) {
			//show the error stage
			Stage errorWarning = new Stage();  
			errorWarning.setTitle("Warning");
			Text warningText = new Text(e.getMessage());  //show error message on screen
			BorderPane pane = new BorderPane();
			pane.setPadding(new Insets(10, 20, 10, 20));

			final ImageView warning = new ImageView(new Image("image/warning.png"));
			Button closeWindow = new Button("Ok");
			closeWindow.setOnAction((ActionEvent t) -> {
				errorWarning.close();
			});

			Scene sceneWarning = new Scene(pane, 500, 100);
			pane.setCenter(warningText);
			pane.setLeft(warning);
			pane.setBottom(closeWindow);

			errorWarning.setScene(sceneWarning);
			errorWarning.show();
		}

		primaryStage.setTitle("Ozlympic Game"); // Set the stage title
		VBox pane = new VBox();
		pane.setPadding(new Insets(50, 100, 50, 100));
		pane.setSpacing(10);//set the spacing

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
		// scene.getStylesheets().add(MultiWindow.class.getResource("login.css").toExternalForm());
		primaryStage.show(); // Display the stage
		//event to choose game type
		GameTypeMenu gtm = new GameTypeMenu(primaryStage, driver, athletes);
		button1.setOnAction(gtm);
		//event to run a new game
		RunningGame runGame = new RunningGame(driver, primaryStage);
		button2.setOnAction(runGame);
		//event to show result of all games
		ShowFinalResult sfr = new ShowFinalResult(driver);
		button3.setOnAction(sfr);
		//event to show all athlete's points
		ShowFinalPoint showFinalPoint = new ShowFinalPoint(driver, athletes);
		button4.setOnAction(showFinalPoint);
		//event to end the program
		button5.setOnAction((ActionEvent t) -> {
			primaryStage.close();
			System.exit(0);
		});
		
		try {
			driver.checkDB();  //check if there is a db and catch the exception
		} catch (NoneDBConnectionException e) {
			Stage errorWarning = new Stage();
			errorWarning.setTitle("Warning");
			Text warningText = new Text(e.getMessage());
			BorderPane border = new BorderPane();
			border.setPadding(new Insets(10, 20, 10, 20));

			final ImageView warning = new ImageView(new Image("image/warning.png"));
			Button closeWindow = new Button("Ok");
			closeWindow.setOnAction((ActionEvent t) -> {
				errorWarning.close();
			});

			Scene sceneWarning = new Scene(border, 550, 100);
			border.setCenter(warningText);
			border.setLeft(warning);
			border.setBottom(closeWindow);

			errorWarning.setScene(sceneWarning);
			errorWarning.show();
		}
	}

	public static void main(String[] args) {
		Application.setUserAgentStylesheet(STYLESHEET_CASPIAN);// set the style
		Application.launch(args); 							   //run the program
	}

}