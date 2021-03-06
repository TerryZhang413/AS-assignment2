package Ozlympic;

/**
 * @author Yipeng Zhang
 * @content Main class. including most of functions
 */

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import Exception.GameFullException;
import Exception.NoGameCreated;
import Exception.NoParticipantDataException;
import Exception.NoRefereeException;
import Exception.NoThisAthleteException;
import Exception.NoThisOfficialException;
import Exception.NoneDBConnectionException;
import Exception.NullResultException;
import Exception.OutOfGameType;
import Exception.RepeatAthleteJoinException;
import Exception.TooFewAthleteException;
import Exception.WrongTypeException;

public class Driver implements SportGame {
	private ModifyData modifyData;
	private ArrayList<Athletes> athletes = new ArrayList<Athletes>();
	private ArrayList<Officials> officials = new ArrayList<Officials>();
	private ArrayList<String> presentAthlete = new ArrayList<String>();
	private String presentOfficial = new String();
	private ArrayList<Game> games = new ArrayList<Game>();
	private final int MAX_ATHLETE = 8;// maximum athlete in a game
	private final int MIN_ATHLETE = 4;// minimum athlete in a game
	private int gameIDIndex = -1;// the present game index
	private int gameType = -1;
	private boolean DBexist;

	public Driver() throws NoParticipantDataException {
		// initialize data from file
		modifyData = new ModifyData(games, officials, athletes);
		modifyData.loadData();
		if (games.size() > 0)
			gameIDIndex = games.size() - 1;
		DBexist = modifyData.getDBexist();
	}

	public boolean checkDB() throws NoneDBConnectionException {
		if (DBexist)
			return DBexist;
		else
			throw new NoneDBConnectionException();

	}

	public ArrayList<Athletes> getAthelte() {
		// get all athlete information
		return athletes;
	}

	public boolean starGame() throws NoGameCreated, TooFewAthleteException, NoRefereeException {
		// star running a new game
		int maxTime = 0, miniTime = 0;
		int resultCount;
		ArrayList<Integer> results = new ArrayList<Integer>();
		ArrayList<Integer> ranks = new ArrayList<Integer>();
		ArrayList<Integer> points = new ArrayList<Integer>();
		if (gameType == -1) {//
			throw new NoGameCreated();
		}
		// generate a new game, add it to game list
		newGame(gameType);
		// generate max&min time based on game type
		switch (gameType) {
		case 1:
			miniTime = 500;
			maxTime = 800;
			break;
		case 2:
			miniTime = 100;
			maxTime = 200;
			break;
		case 3:
			miniTime = 10;
			maxTime = 20;
			break;
		}
		Game gameInfo = games.get(++gameIDIndex);
		resultCount = gameInfo.getAthletes().size();
		// generate results randomly
		for (int i = 0; i < resultCount; i++) {
			results.add(randomTime(miniTime, maxTime));
		}
		gameInfo.setResults(results);
		ranks = rank(gameInfo);
		points = calPoint(ranks);
		gameInfo.setPoints(points);
		refreshPoint(gameInfo);
		String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
		time = time.substring(0, time.length() - 1);
		gameInfo.setTime(time);
		modifyData.addRecord(gameInfo);
		return true;
	}

	private ArrayList<Integer> calPoint(ArrayList<Integer> ranks) {
		ArrayList<Integer> points = new ArrayList<Integer>(ranks.size());
		for (int i = 0; i < ranks.size(); i++) {
			switch (ranks.get(i)) {
			case 1:
				points.add(5);
				break;
			case 2:
				points.add(2);
				break;
			case 3:
				points.add(1);
				break;
			default:
				points.add(0);
			}
		}
		return points;
	}

	private ArrayList<Integer> rank(Game gameInfo) {
		ArrayList<Integer> ranks = new ArrayList<Integer>();
		ArrayList<Integer> results = gameInfo.getResults();
		int sizeResult = results.size();
		boolean existence;// weather this rank is created
		ranks.add(1);// fist one is the least at first
		for (int i = 1; i < sizeResult; i++) {
			existence = false;
			for (int n = 0; n < i; n++) {
				if (results.get(n) > results.get(i)) {
					if (existence) {
						if (ranks.get(i) > ranks.get(n)) {
							// get the smaller one's rank
							ranks.set(i, ranks.get(n));
						}
						// add 1 on smaller one
						ranks.set(n, ranks.get(n) + 1);

					} else {
						// get the smaller one's rank
						ranks.add(ranks.get(n));
						// add 1 on smaller one
						ranks.set(n, ranks.get(n) + 1);
						existence = true;
					}
				} else if (results.get(n) == results.get(i)) {
					if (existence) {
						// get this one's rank
						ranks.set(i, ranks.get(n));
					} else {
						// get this one's rank
						ranks.add(ranks.get(n));
						existence = true;
					}
				}
			}
			if (!existence) {
				// cann't find any one smaller than this one
				ranks.add(i + 1);
			}
		}
		return ranks;
	}

	private int randomTime(int miniTime, int maxTime) {
		// generate a random time from minimum time to maximum time
		Random random = new Random();
		return random.nextInt(maxTime - miniTime + 1) + miniTime;
	}

	public boolean selectGame(int newGameType) throws OutOfGameType {
		// select a game type from 1 to 3
		presentAthlete = new ArrayList<String>();// clear athlete list
		presentOfficial = new String();
		if ((newGameType < 1) || (newGameType > 3)) {
			throw new OutOfGameType();
		}
		if (newGameType != gameType)// Type doesn't change
		{
			gameType = newGameType;
			presentAthlete = new ArrayList<String>();
			presentOfficial = new String();
		}
		return true;
	}

	private String getMaxGameID(String gameID, int gameType) {
		String maxGameID = "null";
		int newGameID = 0;
		int stringLength = 0;
		try {
			stringLength = gameID.length();
			gameID = gameID.substring(1, stringLength);
			newGameID = Integer.valueOf(gameID);
			newGameID++;
			maxGameID = String.valueOf(newGameID);
			for (int i = maxGameID.length(); i < stringLength - 1; i++) {
				maxGameID = "0" + maxGameID;
			}
			switch (gameType) {
			case 1:
				maxGameID = "S" + maxGameID;
				break;
			case 2:
				maxGameID = "C" + maxGameID;
				break;
			case 3:
				maxGameID = "R" + maxGameID;
			}
			return maxGameID;
		} catch (Exception e) {
			return null;
		}
	}

	private void newGame(int gameType) throws TooFewAthleteException, NoRefereeException {
		// create a new Game Class as a new game
		String maxGameID;
		if (gameIDIndex >= 0) {
			maxGameID = games.get(gameIDIndex).getGameID();
		} else {
			maxGameID = "X00";
		}
		if (presentAthlete == null || presentAthlete.size() < MIN_ATHLETE) {
			// not enough athlete
			throw new TooFewAthleteException();
		}
		if (presentOfficial == null || presentOfficial.length() == 0) {
			// no official
			throw new NoRefereeException();
		}
		try {
			maxGameID = getMaxGameID(maxGameID, gameType);
			games.add(new Game(maxGameID, gameType, presentOfficial, presentAthlete));

		} catch (Exception e) {
		}
	}

	public boolean addOfficial(String officialID) throws NoThisOfficialException {
		// choose a official
		boolean find = false;
		for (Officials official : officials) {
			if (official.getUserID().equals(officialID)) {
				presentOfficial = officialID;
				find = true;
			}
		}
		if (find)
			return true;
		else
			throw new NoThisOfficialException(officialID);
	}

	public ArrayList<Officials> getOfficialList() {
		// return all official information
		return officials;
	}

	public ArrayList<String> getPresentAthlete() {
		// return athletes who join this game now
		return presentAthlete;
	}

	public boolean addAthlete(String athleteID)
			throws WrongTypeException, NoThisAthleteException, GameFullException, RepeatAthleteJoinException {
		// add athlete in game
		boolean find = false;
		// check how many athlete already join game
		if (presentAthlete.size() == MAX_ATHLETE)
			throw new GameFullException();
		// check weather this athlete already join this game
		for (String checkID : presentAthlete) {
			if (checkID.equals(athleteID))
				throw new RepeatAthleteJoinException(athleteID);
		}
		// find athlete by user ID
		for (Athletes athlete : athletes) {
			if (athlete.getUserID().equals(athleteID)) {
				// check athlete type
				if ((athlete.getAthleteType() == gameType) || (athlete.getAthleteType() == 4)) {
					presentAthlete.add(athlete.getUserID());
					find = true;
					break;
				} else {
					throw new WrongTypeException(athleteID);
				}
			}
		}
		if (find)
			return true;
		else
			throw new NoThisAthleteException(athleteID);
	}

	public ArrayList<Game> getGame(boolean getAll) throws NullResultException {
		// get game information
		// if (gameIDIndex == -1)
		// throw new NullResultException();
		if (getAll)
			return games;
		else {
			ArrayList<Game> lastGame = new ArrayList<Game>(1);
			lastGame.add(games.get(gameIDIndex));
			return lastGame;
		}
	}

	public String[] getAthleteInf(String userID) {
		// Get athlete's information based on userID
		String[] athleteinf = new String[5];
		for (Athletes athlete : athletes) {
			if (athlete.getUserID().equals(userID)) {
				athleteinf[0] = athlete.getName();
				athleteinf[1] = String.valueOf(athlete.getAge());
				athleteinf[2] = athlete.getState();
				switch (athlete.getAthleteType()) {
				case 1:
					athleteinf[3] = "Swimmer";
					break;
				case 2:
					athleteinf[3] = "Cyclist";
					break;
				case 3:
					athleteinf[3] = "Sprinter";
					break;
				case 4:
					athleteinf[3] = "Super Athlete";
					break;
				}
				athleteinf[4] = String.valueOf(athlete.getPoint());
			}
		}
		return athleteinf;
	}

	public String getOffName(String offID) {
		// get officer's id based on userID
		for (Officials official : officials) {
			if (official.getUserID().equals(offID))
				return official.getName();
		}
		return null;
	}

	private void refreshPoint(Game gameInfo) {
		// find top 3 and add point into their documents
		int resultCount;
		resultCount = gameInfo.getAthletes().size();
		for (int i = 0; i < resultCount; i++) {
			addPoint(gameInfo.getAthletes().get(i), gameInfo.getPoints().get(i));
		}
	}

	private void addPoint(String athleteID, int addPoint) {
		// add point based on users' ID and point
		int athleteCount;
		int point = 0;
		athleteCount = athletes.size();
		for (int i = 0; i < athleteCount; i++) {
			if (athleteID == athletes.get(i).getUserID()) {
				point = athletes.get(i).getPoint() + addPoint;
				athletes.get(i).setPoint(point);
				return;
			}
		}
	}

	public int getGameType() {
		return gameType;
	}

	public void setGameType(int gameType) {
		this.gameType = gameType;
	}
}
