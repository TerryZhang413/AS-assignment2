package Ozlympic;

import java.util.ArrayList;

import Exception.NoGameCreated;
import Exception.NoRefereeException;
import Exception.NullResultException;
import Exception.TooFewAthleteException;

public interface SportGame {
	public boolean selectGame(int gameType);

	public ArrayList<String> showGameList();

	public boolean starGame() throws NoGameCreated, TooFewAthleteException, NoRefereeException;

	public ArrayList<Game> getGame(boolean getAll) throws NullResultException;

	public ArrayList<Athletes> getAthelte();

	public void exit();
}
