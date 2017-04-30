package Ozlympic;

import java.util.ArrayList;

public interface SportGame {
	public void selectGame(int gameType);
	public ArrayList<String> showGameList();
	public void predict(int newIndex);
	public void starGame();
	public void showFinalResult();
	public void refreshPoint();
	public void exit();
}
