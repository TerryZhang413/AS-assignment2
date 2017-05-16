package gui;

import Ozlympic.Driver;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

public class GameType implements EventHandler<ActionEvent> {

	String gameTypeName;
	Stage closeStage;
	Stage reopenStage;
	int gameType;
	Driver driver;

	GameType(Stage closeStage, Stage reopenStage, String gameTypeName, int gameType, Driver driver) {
		this.closeStage = closeStage;
		this.gameTypeName = gameTypeName;
		this.reopenStage = reopenStage;
		this.gameType = gameType;
		this.driver=driver;
	}

	public void handle(ActionEvent e) {
		MultiWindow.gameTypeName = gameTypeName;
		driver.selectGame(gameType);
		closeStage.close();
		reopenStage.show();
	}
}