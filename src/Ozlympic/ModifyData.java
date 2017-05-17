package Ozlympic;

/**
 * @author Yipeng Zhang
 * @content this class is used to involve method in database or textfile class
 * or translate data form between database and textfile
 */
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import Data.DataBase;
import Data.TextFile;
import Exception.NoParticipantDataException;
import Exception.NoneDBConnectionException;
import Exception.ReadDataBaseException;

public class ModifyData {
	private ArrayList<Athletes> athletes = new ArrayList<Athletes>();
	private ArrayList<Officials> officials = new ArrayList<Officials>();
	ArrayList<Game> game = new ArrayList<Game>();
	private boolean DBexist;
	private boolean gameRecord;
	private String classFilePath;
	private String recordFilePath = "Data/gameResults.txt";
	private String athleteFilePath = "Data/participants.txt";
	private DataBase db;
	private TextFile tf;

	public ModifyData(ArrayList<Game> game, ArrayList<Officials> officials, ArrayList<Athletes> athletes) {
		classFilePath = this.getClass().getResource("/").getFile();
		this.game = game;
		this.officials = officials;
		this.athletes = athletes;
		tf = new TextFile();
		try {
			db = new DataBase();
			DBexist = true;
		} catch (NoneDBConnectionException e) {
			DBexist = false;
		}
	}

	public boolean getDBexist() {
		return DBexist;
	}

	public void loadData() throws NoParticipantDataException {
		ArrayList<String> participants = new ArrayList<String>();
		ArrayList<String> games = new ArrayList<String>();

		ArrayList<String> athelteCols = new ArrayList<String>(5);
		athelteCols.add("ID");
		athelteCols.add("TYPE");
		athelteCols.add("NAME");
		athelteCols.add("AGE");
		athelteCols.add("STATE");

		ArrayList<String> gameCols = new ArrayList<String>(5);
		gameCols.add("GameID");
		gameCols.add("OfficialID");
		gameCols.add("Time");
		gameCols.add("AthleteID");
		gameCols.add("Result");
		gameCols.add("Point");

		if (DBexist) {
			try {
				participants = db.readDB("Participant", athelteCols);
			} catch (ReadDataBaseException e) {
			}
			try {
				games = db.readDB("GameRecord", gameCols);
			} catch (ReadDataBaseException e) {
			}
		}

		if (participants.size() == 0) {
			try {
				participants = tf.readText(classFilePath + athleteFilePath);
				participants = checkParticipants(participants);
				identifyType(participants);
				if (DBexist) {
					ArrayList<String> type = new ArrayList<String>();
					type.add("varchar(10)");
					type.add("varchar(10)");
					type.add("varchar(20)");
					type.add("varchar(3)");
					type.add("varchar(10)");
					db.writeDB("Participant", athelteCols, participants, type);
				}
			} catch (IOException e) {
			} catch (Exception e) {
			}
		} else {
			identifyType(participants);
		}

		if (participants.size() == 0)
			throw new NoParticipantDataException();

		if (games.size() == 0) {
			try {
				games = tf.readText(classFilePath + recordFilePath);
				initGameRecordText(games);
				if (DBexist) {
					ArrayList<String> type = new ArrayList<String>();
					type.add("varchar(10)");
					type.add("varchar(10)");
					type.add("varchar(30)");
					type.add("varchar(10)");
					type.add("varchar(10)");
					type.add("varchar(2)");
					ArrayList<String> context = transRecordDB(game);
					db.writeDB("GameRecord", gameCols, context, type);
				}
			} catch (IOException e) {
				// GameRecord.txt doesn't exist, so new one
				newRecordText(classFilePath + recordFilePath);
			} catch (Exception e) {
				// System.out.println("Cannot create table (GameRecord)");
			}
		} else {
			initGameRecordDB(games);
		}
	}

	public void addRecord(Game gameInf) {
		addRecordText(gameInf);
		if (DBexist)
			addRecordDB(gameInf);
	}

	private ArrayList<String> transRecordDB(ArrayList<Game> game) {
		// transform game to context which suitable to database
		ArrayList<String> athletes;
		ArrayList<Integer> records;
		ArrayList<Integer> points;
		ArrayList<String> gameCols = new ArrayList<String>(5);
		gameCols.add("GameID");
		gameCols.add("OfficialID");
		gameCols.add("Time");
		gameCols.add("AthleteID");
		gameCols.add("Result");
		gameCols.add("Point");
		ArrayList<String> context = new ArrayList<String>();
		for (Game gameInf : game) {
			athletes = gameInf.getAthletes();
			records = gameInf.getResults();
			points = gameInf.getPoints();
			try {
				for (int i = 0; i < athletes.size(); i++) {
					context.add(gameInf.getGameID());
					context.add(gameInf.getOfficialID());
					context.add(gameInf.getTime());
					context.add(athletes.get(i));
					context.add(String.valueOf(records.get(i)));
					context.add(String.valueOf(points.get(i)));
				}
			} catch (Exception e) {
			}
		}
		return context;
	}

	private void addRecordDB(Game gameInf) {
		// write GameRecord in database
		ArrayList<Game> game = new ArrayList<Game>();
		game.add(gameInf);
		ArrayList<String> context = transRecordDB(game);
		ArrayList<String> gameCols = new ArrayList<String>(5);
		gameCols.add("GameID");
		gameCols.add("OfficialID");
		gameCols.add("Time");
		gameCols.add("AthleteID");
		gameCols.add("Result");
		gameCols.add("Point");
		try {
			db.writeDB("GameRecord", gameCols, context);
			// System.out.println("add record (DB):" + gameInf.getGameID());
		} catch (Exception e) {
		}
	}

	private void addRecordText(Game gameInf) {
		ArrayList<String> athletes = gameInf.getAthletes();
		ArrayList<Integer> records = gameInf.getResults();
		ArrayList<Integer> points = gameInf.getPoints();
		ArrayList<String> context = new ArrayList<String>();
		String introduction = gameInf.getGameID() + ", ";
		introduction += gameInf.getOfficialID() + ", ";
		introduction += gameInf.getTime() + "\r\n";
		context.add(introduction);
		for (int i = 0; i < athletes.size(); i++) {
			context.add(athletes.get(i) + ", " + records.get(i) + ", " + points.get(i) + "\r\n");
		}
		context.add("\r\n");
		tf.writeText(classFilePath + recordFilePath, context);
		// System.out.println("add record (TXT):" + gameInf.getGameID());
	}

	private void newRecordText(String filePath) {
		try {
			FileWriter writer = new FileWriter(filePath);
			writer.close();
			gameRecord = true;
		} catch (IOException e) {
			// System.err.println("File cannot be created!");
			gameRecord = false;
		}
	}

	private void initGameRecordDB(ArrayList<String> games) {
		String gameID = "", officialID = "", time = "";
		Game gameTem;
		ArrayList<String> athletes = new ArrayList<String>();
		ArrayList<Integer> results = new ArrayList<Integer>();
		ArrayList<Integer> points = new ArrayList<Integer>();
		// System.out.println("load record:");
		for (int i = 0; i < games.size();) {
			gameID = games.get(i++);
			officialID = games.get(i++);
			time = games.get(i++);
			athletes.add(games.get(i++));
			results.add(Integer.valueOf(games.get(i++)));
			points.add(Integer.valueOf(games.get(i++)));
			while (gameID.equals(games.get(i))) {
				i = i + 3;
				athletes.add(games.get(i++));
				results.add(Integer.valueOf(games.get(i++)));
				points.add(Integer.valueOf(games.get(i++)));
				if (i == games.size())
					break;
			}
			gameTem = new Game(gameID, officialID, athletes, results, points);
			gameTem.setTime(time);
			athletes = new ArrayList<String>();
			results = new ArrayList<Integer>();
			points = new ArrayList<Integer>();
			game.add(gameTem);
			// System.out.println(gameTem.getGameID());

		}

	}

	private void initGameRecordText(ArrayList<String> games) {
		String gameID, officialID, time;
		Game gameTem;
		ArrayList<String> athletes = new ArrayList<String>();
		ArrayList<Integer> results = new ArrayList<Integer>();
		ArrayList<Integer> points = new ArrayList<Integer>();
		try {
			// System.out.println("load record:");
			for (int i = 0; i < games.size();) {
				gameID = games.get(i++);
				officialID = games.get(i++);
				time = games.get(i++);
				while (!(games.get(i).equals(""))) {
					athletes.add(games.get(i++));
					results.add(Integer.valueOf(games.get(i++)));
					points.add(Integer.valueOf(games.get(i++)));
					if (i == games.size()) {
						break;
					}
				}
				if (i < games.size()) {
					games.remove(i);
				}
				gameTem = new Game(gameID, officialID, athletes, results, points);
				gameTem.setTime(time);
				athletes = new ArrayList<String>();
				results = new ArrayList<Integer>();
				points = new ArrayList<Integer>();
				game.add(gameTem);
				// System.out.println(gameTem.getGameID());
			}
		} catch (Exception e) {
		}
	}

	private ArrayList<String> checkParticipants(ArrayList<String> participants) {
		ArrayList<String> arrayList = new ArrayList<String>();
		for (int i = 0; i < participants.size(); i++) {
			String[] dataArray = participants.get(i).split(",");
			if (dataArray.length != 5)
				continue;
			try {
				if (!(Integer.valueOf(dataArray[3]) instanceof Integer)) {
					continue;
				}
				if (dataArray[0].length() == 0 || dataArray[1].length() == 0 || dataArray[2].length() == 0
						|| dataArray[3].length() == 0 || dataArray[4].length() == 0)
					continue;
			} catch (Exception e) {
				continue;
			}
			for (String element : dataArray) {
				element = element.trim();
				arrayList.add(element);
			}
		}
		return arrayList;
	}

	private void identifyType(ArrayList<String> participants) {
		ArrayList<String> personalInfo;
		for (int i = 0; i < participants.size(); i += 5) {
			personalInfo = new ArrayList<String>();
			for (int j = 0; j < 5; j++) {
				personalInfo.add(participants.get(i + j));
			}
			if (participants.get(i + 1).equals("officer"))
				loadOfficial(personalInfo);
			else
				loadAthlete(personalInfo);
		}
	}

	private void loadOfficial(ArrayList<String> newOfficials) {
		try {
			this.officials.add(new Officials(newOfficials.get(0), newOfficials.get(2),
					Integer.valueOf(newOfficials.get(3)), newOfficials.get(4)));

		} catch (Exception e) {
			System.out.println("Loading official data is error!");
		}
	}

	private void loadAthlete(ArrayList<String> newAthletes) {
		try {
			int athleteType = 0;
			if (newAthletes.get(1).equals("swimmer"))
				athleteType = 1;
			else if (newAthletes.get(1).equals("cyclist"))
				athleteType = 2;
			else if (newAthletes.get(1).equals("sprinter"))
				athleteType = 3;
			else if (newAthletes.get(1).equals("super"))
				athleteType = 4;
			this.athletes.add(new Athletes(newAthletes.get(0), newAthletes.get(2), Integer.valueOf(newAthletes.get(3)),
					newAthletes.get(4), athleteType));
		} catch (Exception e) {
			System.out.println("Loading athlete data is error!");
		}
	}

}
