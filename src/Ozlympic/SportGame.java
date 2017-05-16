package Ozlympic;

import java.util.ArrayList;

import Exception.GameFullException;
import Exception.NoGameCreated;
import Exception.NoRefereeException;
import Exception.NoThisAthleteException;
import Exception.NoThisOfficialException;
import Exception.NullResultException;
import Exception.RepeatAthleteJoinException;
import Exception.TooFewAthleteException;
import Exception.WrongTypeException;

public interface SportGame {
	public boolean selectGame(int gameType);

	public boolean starGame() throws NoGameCreated, TooFewAthleteException, NoRefereeException;

	public ArrayList<Game> getGame(boolean getAll) throws NullResultException;

	public boolean addOfficial(String officialID) throws NoThisOfficialException;

	public boolean addAthlete(String athleteID)
			throws WrongTypeException, NoThisAthleteException, GameFullException, RepeatAthleteJoinException;

}
