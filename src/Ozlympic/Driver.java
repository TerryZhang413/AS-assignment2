package Ozlympic;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

import Exception.GameFullException;
import Exception.NoGameCreated;
import Exception.NoRefereeException;
import Exception.NoThisAthleteException;
import Exception.NoThisOfficialException;
import Exception.NullResultException;
import Exception.OutOfGameType;
import Exception.RepeatAthleteJoinException;
import Exception.TooFewAthleteException;
import Exception.WrongTypeException;

public class Driver implements SportGame {
	private Scanner keyBoard = new Scanner(System.in);
	private ModifyData modifyData;
	private ArrayList<Athletes> athletes = new ArrayList<Athletes>();
	private ArrayList<Officials> officials = new ArrayList<Officials>();
	private ArrayList<String> presentAthlete = new ArrayList<String>();
	private String presentOffical = new String();
	private ArrayList<Game> games = new ArrayList<Game>();
	private final int MAX_ATHLETE = 8;// maximum athlete in a game
	private final int MIN_ATHLETE = 4;// minimum athlete in a game
	private int gameIDIndex = -1;// the present game index
	private int gameType = -1;

	public Driver() {
		// initialize data from file
		modifyData = new ModifyData(games, officials, athletes);
		modifyData.loadData();
		gameIDIndex = games.size();
	}

	public void option() {
		int optionNumber = -1;
		int a = 1;
		Menus menus = new Menus();
		do {
			menus.mainMenus();
			try {
				optionNumber = keyBoard.nextInt();
				switch (optionNumber) {
				case 1:
					// select a game type
					selectGame(a);
					a++;
					if (a == 4)
						a = 1;
					break;
				case 2:
					// select a prediction
					// setPredict(1);
					break;
				case 3:
					// star a game
					starGame();
					break;
				case 4:
					// display final results
					// showFinalResult();
					break;
				case 5:
					// display points of all athletes
					// showFinalPoint();
					break;
				case 6:
					exit();
					break;
				default:
				}
			} catch (Exception e) {
			}
		} while (optionNumber != 6);

	}

	public void exit() {
		System.exit(0);
	}

	public ArrayList<Athletes> getAthelte() {
		return athletes;
	}

	public ArrayList<String> showGameList() {
		ArrayList<String> gameList = new ArrayList<String>();
		gameList.add("Swimming");
		gameList.add("Cycling");
		gameList.add("Running");
		return gameList;
	}

	public boolean starGame() throws NoGameCreated, TooFewAthleteException, NoRefereeException {
		int maxTime = 0, miniTime = 0;
		int resultCount;
		ArrayList<Integer> results = new ArrayList<Integer>();
		ArrayList<Integer> ranks = new ArrayList<Integer>();
		ArrayList<Integer> points = new ArrayList<Integer>();
		if (gameType == -1) {
			throw new NoGameCreated();
		}

		newGame(gameType);
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
		Game gameInfo = games.get(gameIDIndex);
		resultCount = gameInfo.getAthletes().size();
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
		presentAthlete = new ArrayList<String>();// clear athlete list
		return true;
	}

	public Boolean showPredict(ArrayList<Integer> ranks, int predictIndex) {
		if (predictIndex != -1) {
			if (ranks.get(predictIndex - 1) == 1)
				return true;
		}
		return false;
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

	public boolean selectGame(int newGameType) {
		// select a game type from 1 to 3
		try {
			if ((newGameType < 1) || (newGameType > 3)) {
				throw new OutOfGameType();
			}
			if (newGameType != gameType)// Type doesn't change
			{
				gameType = newGameType;
				presentAthlete = new ArrayList<String>();
				presentOffical = new String();
			}
			return true;
		} catch (Exception e) {
			return false;
		}
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
		String maxGameID;
		String officialID;
		int gameIDIndex = games.size();
		if (games.size() > 0) {
			maxGameID = games.get(gameIDIndex - 1).getGameID();
		} else {
			maxGameID = "X00";
		}
		if (presentAthlete == null || presentAthlete.size() < 4) {
			throw new TooFewAthleteException();
		}
		if (presentOffical == null) {
			throw new NoRefereeException();
		}
		try {
			maxGameID = getMaxGameID(maxGameID, gameType);
			presentAthlete = getAthlete(gameType);
			officialID = getOfficial();
			games.add(new Game(maxGameID, gameType, officialID, presentAthlete));
			gameIDIndex = games.size() - 1;

		} catch (Exception e) {
		}
	}

	public boolean addOfficial(String officialID) throws NoThisOfficialException {
		boolean find = false;
		for (Officials official : officials) {
			if (official.getUserID().equals(officialID)) {
				presentOffical = officialID;
			}
		}
		if (find)
			return true;
		else
			throw new NoThisOfficialException(officialID);
	}

	public ArrayList<String> getPresentAthlete() {
		return presentAthlete;
	}

	public boolean addAthlete(String athleteID)
			throws WrongTypeException, NoThisAthleteException, GameFullException, RepeatAthleteJoinException {
		boolean find = false;
		if (presentAthlete.size() == 8)
			throw new GameFullException();
		for (String checkID : presentAthlete) {
			if (checkID.equals(athleteID))
				throw new RepeatAthleteJoinException(athleteID);
		}
		for (Athletes athlete : athletes) {
			if (athlete.getUserID().equals(athleteID)) {
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
		if (gameIDIndex == -1)
			throw new NullResultException();
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

	private String getOfficial() {
		Random ranIndex = new Random();
		int sizeList;
		String officialID = new String();
		sizeList = officials.size();
		if (sizeList > 0) {
			sizeList--;
			officialID = officials.get(ranIndex.nextInt(sizeList)).getUserID();
			getClass();
			return officialID;
		} else {
			return null;
		}
	}

	private ArrayList<String> getAthlete(int gameType) {
		try {
			ArrayList<String> temporaryList = new ArrayList<String>();
			// Finding athletes who are satisfied type of sport.
			for (Athletes athlete : athletes) {
				if ((athlete.getAthleteType() == gameType) || (athlete.getAthleteType() == 4)) {
					temporaryList.add(athlete.getUserID());
				}
			}
			if (temporaryList.size() <= MIN_ATHLETE) {
				// no enough athletes
				return null;
			} else {
				Random ranIndex = new Random();
				int sizeList;
				while (temporaryList.size() > MAX_ATHLETE) {
					sizeList = temporaryList.size() - 1;
					temporaryList.remove(ranIndex.nextInt(sizeList));
				}
				return temporaryList;
			}
		} catch (Exception e) {
			return null;
		}
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
