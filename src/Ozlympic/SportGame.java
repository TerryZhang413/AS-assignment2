package Ozlympic;

import java.util.ArrayList;

public interface SportGame {
	public boolean selectGame(int gameType);

	public ArrayList<String> showGameList();

	public void predict(int newIndex);

	public boolean starGame();

	public void showFinalResult();

	public ArrayList<Integer> getResult();

	public ArrayList<Integer> getPoint();

	public void exit();
}
