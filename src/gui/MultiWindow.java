package gui;

import java.util.ArrayList;

import Exception.NoParticipantDataException;
import Exception.NullResultException;
import Ozlympic.Athletes;
import Ozlympic.Driver;
import Ozlympic.Game;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MultiWindow extends Application {

	private ArrayList<Game> games;
	private ArrayList<Athletes> athletes;

	static int gameType = -1;
	static String gameTypeName = "Unknown type";
	static String gameId = null;

	@Override // Override the start method in the Application class
	public void start(Stage primaryStage) {

		Driver driver;
		try {
			driver = new Driver();
			games = driver.getGame(true);
			athletes = driver.getAthelte();

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
			// scene.getStylesheets().add(MultiWindow.class.getResource("login.css").toExternalForm());
			primaryStage.show(); // Display the stage

			GameTypeMenu gtm = new GameTypeMenu(primaryStage, driver, athletes);
			button1.setOnAction(gtm);
			RunningGame runGame = new RunningGame(driver, games, primaryStage);
			button2.setOnAction(runGame);

			ShowFinalResult sfr = new ShowFinalResult(games, driver);
			button3.setOnAction(sfr);

			ShowFinalPoint showFinalPoint = new ShowFinalPoint(driver, athletes);
			button4.setOnAction(showFinalPoint);
			// button4.setOnAction((ActionEvent t)->{driver.showFinalPoint();});
			button5.setOnAction((ActionEvent t) -> {
				primaryStage.close();
				System.exit(0);
			});
		} catch (NullResultException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (NoParticipantDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		Application.setUserAgentStylesheet(STYLESHEET_CASPIAN);
		Application.launch(args);
	}

}