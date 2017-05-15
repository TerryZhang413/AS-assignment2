package Ozlympic;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

import Exception.NoGameCreated;
import Exception.NoRefereeException;
import Exception.OutOfGameType;
import Exception.TooFewAthleteException;

public class Driver implements SportGame {
	private Scanner keyBoard = new Scanner(System.in);
	private ModifyData modifyData;
	private final int MAX_ATHLETE = 8;// maximum athlete in a game
	private final int MIN_ATHLETE = 4;// minimum athlete in a game
	public int gameIDIndex = -1;// the present game index
	private int predictIndex = -1;
	private int gameType = -1;



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
					predict(1);
					break;
				case 3:
					// star a game
					starGame(gameType);
					predictIndex = -1;
					break;
				case 4:
					// display final results
					showFinalResult();
					break;
				case 5:
					// display points of all athletes
					showFinalPoint();
					break;
				case 6:
					exit();
					break;
				default:
					println("The number must be in 1 to 6!");
					print("Enter an option:");
				}
			} catch (Exception e) {
				println("Input must be a number!");
				print("Enter an option:");
				keyBoard.next();
			}
		} while (optionNumber != 6);

	}

	public void exit() {
		System.exit(0);
	}

	public ArrayList<String> showGameList() {
		ArrayList<String> gameList = new ArrayList<String>();
		gameList.add("Swimming");
		gameList.add("Cycling");
		gameList.add("Running");
		return gameList;
	}

	public boolean starGame(int gameType) {
		int maxTime = 0, miniTime = 0;
		int resultCount;
		ArrayList<Integer> results = new ArrayList<Integer>();
		ArrayList<Integer> ranks = new ArrayList<Integer>();
		ArrayList<Integer> points = new ArrayList<Integer>();

		try {
			if (gameType == -1) {
				throw new NoGameCreated();
			}
			newGame(gameType);
			switch (gameType) {
			case 1:
				miniTime = 10;
				maxTime = 20;
				break;
			case 2:
				miniTime = 100;
				maxTime = 200;
				break;
			case 3:
				miniTime = 500;
				maxTime = 800;
				break;
			}
			Game gameInfo = MultiWindow.games.get(gameIDIndex);
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
			showResult(gameIDIndex);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public ArrayList<Integer> getResult() {
		Game gameInfo = MultiWindow.games.get(gameIDIndex);
		ArrayList<Integer> results = gameInfo.getResults();
		return results;
	}

	public ArrayList<Integer> getPoint() {
		Game gameInfo = MultiWindow.games.get(gameIDIndex);
		ArrayList<Integer> points = gameInfo.getPoints();
		return points;
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

	private void showResult(int index) {
		String official;
		String[] athleteinf = new String[5];
		int time = 0;
		int point = 0;
		int countAthlete = 0;
		if (MultiWindow.games.get(index).getResults().size() == 0)
			return;// game maybe haven't run yet
		official = getOffName(MultiWindow.games.get(index).getOfficialID());
		countAthlete = MultiWindow.games.get(index).getAthletes().size();
		println("==============================================");
		println("Game Number: " + MultiWindow.games.get(index).getGameID());
		println("Official: " + official);
		println("Game: " + MultiWindow.games.get(index).getGameID());  
		print("Name", 15);
		print("Age", 5);
		print("State", 7);
		print("Athlete Type", 15);
		print("Time", 5);
		println("Rank", 5);
		for (int i = 0; i < countAthlete; i++) {
			athleteinf = getAthleteInf(MultiWindow.games.get(index).getAthletes().get(i));
			time = MultiWindow.games.get(index).getResults().get(i);
			point = MultiWindow.games.get(index).getPoints().get(i);

			print(athleteinf[0], 15);
			print(athleteinf[1], 5);
			print(athleteinf[2], 7);
			print(athleteinf[3], 15);
			print(time, 5);
			println(point, 5);
		}
	}

	String getOffName(String userID) {
		// get officer's id based on userID
		for (Officials official : MultiWindow.officials) {
			if (official.getUserID().equals(userID))
				return official.getName();
		}
		return null;
	}

	String[] getAthleteInf(String userID) {
		// Get athlete's information based on userID
		String[ ] athleteinf = new String[5];
		for (Athletes athlete : MultiWindow.athletes) {
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

	public void showFinalResult() {
		// display all of results respectively
		int countGame = MultiWindow.games.size();
		if (countGame == 0) {
			println("No game record!");
			return;
		}
		for (int i = 0; i < countGame; i++) {
			showResult(i);
		}
	}

	protected void showFinalPoint() {
		// display all of information about athlete
		String[] athleteInf = new String[5];
		int countAthlete = 0;
		print("Name", 15);
		print("Age", 5);
		print("State", 7);
		print("Athlete Type", 15);
		println("Point", 5);
		countAthlete = MultiWindow.athletes.size();
		if (countAthlete == 0) {
			println("Thers isn't any athlete's information;");
		} else {
			for (int i = 0; i < countAthlete; i++) {
				athleteInf = getAthleteInf(MultiWindow.athletes.get(i).getUserID());
				print(athleteInf[0], 15);
				print(athleteInf[1], 5);
				print(athleteInf[2], 7);
				print(athleteInf[3], 15);
				println(athleteInf[4], 5);
			}
		}
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
				predictIndex = -1;
				gameType = newGameType;
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
			println("Cannn't get maxGameID!");
			return null;
		}

	}

	private void newGame(int gameType) {
		String maxGameID = "null";
		String officialID;
		ArrayList<String> presentAthlete = new ArrayList<String>();
		gameIDIndex = MultiWindow.games.size();
		if (MultiWindow.games.size() > 0) {
			maxGameID = MultiWindow.games.get(gameIDIndex - 1).getGameID();
		}
		if (maxGameID.equals("null")) {
			maxGameID = "X00";
			gameIDIndex = 0;
		}
		try {
			maxGameID = getMaxGameID(maxGameID, gameType);
			presentAthlete = getAthlete(gameType);
			officialID = getOfficial();
			if (presentAthlete == null) {
				gameIDIndex = -1;
				throw new TooFewAthleteException();
			}
			if (officialID == null) {
				gameIDIndex = -1;
				throw new NoRefereeException();
			}
			if (gameIDIndex != -1) {
				MultiWindow.games.add(new Game(maxGameID, gameType, officialID, presentAthlete));
				gameIDIndex = MultiWindow.games.size() - 1;
			}
		} catch (Exception e) {
			println("Cann't create a new game!");
		}

	}

	private String getOfficial() {
		Random ranIndex = new Random();
		int sizeList;
		String officialID = new String();
		sizeList = MultiWindow.officials.size();
		if (sizeList > 0) {
			sizeList--;
			officialID = MultiWindow.officials.get(ranIndex.nextInt(sizeList)).getUserID();
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
			for (Athletes athlete : MultiWindow.athletes) {
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

	public void predict(int newIndex) {
		// set prediction and check if it's legal
		int athleteCount;
		if (gameIDIndex == -1) {
			println("Have to select a game first!");
			predictIndex = -1;
			return;
		} else {
			showGameInf(gameIDIndex);
			athleteCount = MultiWindow.games.get(gameIDIndex).getAthletes().size();
		}
		print("Choose an athlete:");
		do {
			try {
				newIndex = keyBoard.nextInt();
				if ((newIndex < 1) || (newIndex > athleteCount)) {
					println("The number must be in 1 to " + athleteCount + "!");
					print("Enter an option:");
					continue;
				}
				predictIndex = newIndex - 1;
			} catch (Exception e) {
				println("Input must be an integer number!");
				print("Enter an option:");
				keyBoard.next();
			}
		} while (predictIndex == -1);// -1 means haven't a prediction
		predictIndex = newIndex;
	}

	private void showGameInf(int index) {
		// show game information in prediction menus
		String athleteName = null;
		String state = null;
		String age = null;
		String athleteType = null;
		String point = null;
		print("Name", 15);
		print("Age", 5);
		print("State", 7);
		print("Athlete Type", 15);
		println("Point", 10);
		for (String userID : MultiWindow.games.get(index).getAthletes()) {
			for (Athletes athlete : MultiWindow.athletes) {
				if (athlete.getUserID().equals(userID)) {
					athleteName = athlete.getName();
					state = athlete.getState();
					age = String.valueOf(athlete.getAge());
					switch (athlete.getAthleteType()) {
					case 1:
						athleteType = "Swimmer";
						break;
					case 2:
						athleteType = "Cyclist";
						break;
					case 3:
						athleteType = "Sprinter";
						break;
					case 4:
						athleteType = "Super Athlete";
						break;
					}
					point = String.valueOf(athlete.getPoint());
					break;
				}
			}
			print(athleteName, 15);
			print(age, 5);
			print(state, 7);
			print(athleteType, 15);
			println(point, 10);
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
		athleteCount = MultiWindow.athletes.size();
		for (int i = 0; i < athleteCount; i++) {
			if (athleteID == MultiWindow.athletes.get(i).getUserID()) {
				point = MultiWindow.athletes.get(i).getPoint() + addPoint;
				MultiWindow.athletes.get(i).setPoint(point);
				return;
			}
		}

	}

	private void print(String message) {
		System.out.print(message);
	}

	private void print(String message, int length) {
		message = String.format("%1$-" + length + "s", message);
		System.out.print(message);
	}

	private void print(int messageInt, int length) {
		String message = String.format("%1$-" + length + "s", messageInt);
		System.out.print(message);
	}

	private void println(String message) {
		System.out.println(message);
	}

	private void println(String message, int length) {
		message = String.format("%1$-" + length + "s", message);
		System.out.println(message);
	}

	private void println(int messageInt, int length) {
		String message = String.format("%1$-" + length + "s", messageInt);
		System.out.println(message);
	}

}
