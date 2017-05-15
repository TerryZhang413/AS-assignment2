package Ozlympic;

import java.util.ArrayList;

import Exception.NullResultException;

public interface SportGame {
	public boolean selectGame(int gameType);

	public ArrayList<String> showGameList();

	public boolean starGame();

	public ArrayList<Game> getGame(boolean getAll) throws NullResultException;

	public ArrayList<Athletes> getAthelte();

	public void exit();
}
