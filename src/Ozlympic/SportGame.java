package Ozlympic;

import java.util.ArrayList;

public interface SportGame {
	public void selectGame(int gameType);

	public ArrayList<String> showGameList();

	public void predict(int newIndex);

	public Boolean starGame();

	public void showFinalResult();

	public void refreshPoint();

	public void exit();
}
