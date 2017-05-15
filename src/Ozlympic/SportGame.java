package Ozlympic;

import java.util.ArrayList;

import Exception.NullResultException;

public interface SportGame {
	public boolean selectGame(int gameType);

	public ArrayList<String> showGameList();

	public void setPredict(int newIndex);

	public boolean starGame();

	public ArrayList<Game> getGame(boolean getAll) throws NullResultException;

	public void exit();
}
