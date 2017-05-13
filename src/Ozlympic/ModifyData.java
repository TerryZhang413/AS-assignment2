package Ozlympic;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import Data.DataBase;
import Data.TextFile;
import Exception.ReadDataBaseException;

public class ModifyData {
	private ArrayList<Athletes> athletes = new ArrayList<Athletes>();
	private ArrayList<Officials> officials = new ArrayList<Officials>();
	ArrayList<Game> game = new ArrayList<Game>();
	private boolean DBexist;
	private boolean gameRecord;
	private String recordFilePath = "gameResults.txt";
	private String athleteFilePath = "participants.txt";
	private DataBase db;
	private TextFile tf;

	public ModifyData(ArrayList<Game> game, ArrayList<Officials> officials, ArrayList<Athletes> athletes) {
		this.game = game;
		this.officials = officials;
		this.athletes = athletes;
		db = new DataBase();
		tf = new TextFile();
	}

	public void writeResult() {
		try {

		} catch (Exception e) {
		}
	}

	public void loadData() {
		ArrayList<String> participants = new ArrayList<String>();
		ArrayList<String> games = new ArrayList<String>();
		DBexist = db.checkDB();

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
		gameCols.add("Rank");

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
				participants = tf.readText(athleteFilePath);
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
				System.out.println("Can not find participants.txt");
			} catch (Exception e) {
				System.out.println("Cannot create table (Participant)");
			}
		} else {
			identifyType(participants);
		}

		if (games.size() == 0) {
			try {
				games = tf.readText(recordFilePath);
				initGameRecord(games);
				if (DBexist) {
					ArrayList<String> type = new ArrayList<String>();
					type.add("varchar(10)");
					type.add("varchar(10)");
					type.add("varchar(30)");
					type.add("varchar(10)");
					type.add("varchar(10)");
					type.add("varchar(2)");
					db.writeDB("GameRecord", gameCols, games, type);
				}
			} catch (IOException e) {
				// GameRecord.txt doesn't exist, so new one
				newRecordText(recordFilePath);
			} catch (Exception e) {
				System.out.println("Cannot create table (GameRecord)");
			}
		}
	}

	public void addRecord(Game gameInf) {
		addRecordTexT(gameInf);
		if (DBexist)
			addRecordDB(gameInf);
	}

	private void addRecordDB(Game gameInf) {
		ArrayList<String> athletes = gameInf.getAthletes();
		ArrayList<Integer> records = gameInf.getResults();
		ArrayList<Integer> points = gameInf.getPoints();
		ArrayList<String> gameCols = new ArrayList<String>(5);
		ArrayList<String> context = new ArrayList<String>();
		gameCols.add("GameID");
		gameCols.add("OfficialID");
		gameCols.add("Time");
		gameCols.add("AthleteID");
		gameCols.add("Result");
		gameCols.add("Rank");
		try {
			for (int i = 0; i < athletes.size(); i++) {
				context.add(gameInf.getGameID());
				context.add(gameInf.getOfficialID());
				context.add(gameInf.getTime());
				context.add(athletes.get(i));
				context.add(String.valueOf(records.get(i)));
				context.add(String.valueOf(points.get(i)));
			}
			db.writeDB("Participant", gameCols, context);
		} catch (Exception e) {
		}
	}

	private void addRecordTexT(Game gameInf) {
		ArrayList<String> athletes = gameInf.getAthletes();
		ArrayList<Integer> records = gameInf.getResults();
		ArrayList<Integer> points = gameInf.getPoints();
		ArrayList<String> context = new ArrayList<String>();
		String introduction = gameInf.getGameID() + ", ";
		introduction += gameInf.getOfficialID() + ", ";
		introduction += gameInf.getTime() + "\n";
		context.add(introduction);
		for (int i = 0; i < athletes.size(); i++) {
			context.add(athletes.get(i) + ", " + records.get(i) + ", " + points.get(i) + "\n");
		}
		context.add("\n");
		tf.writeText(recordFilePath, context);
	}

	private void newRecordText(String filePath) {
		try {
			FileWriter writer = new FileWriter(filePath);
			writer.close();
			gameRecord = true;
		} catch (IOException e) {
			System.err.println("File cannot be created!");
			gameRecord = false;
		}
	}

	private void initGameRecord(ArrayList<String> games) {
		String gameID, officialID, time, athleteID;
		Game gameTem;
		ArrayList<String> athletes = new ArrayList<String>();
		ArrayList<Integer> results = new ArrayList<Integer>();
		ArrayList<Integer> points = new ArrayList<Integer>();
		try {
			for (int i = 0; i < games.size(); i++) {
				gameID = games.get(i++);
				officialID = games.get(i++);
				time = games.get(i++);
				while (!(games.get(i).equals(""))) {
					athletes.add(games.get(i++));
					results.add(Integer.valueOf(games.get(i++)));
					points.add(Integer.valueOf(games.get(i++)));
					if (i == games.size())
						break;
				}
				gameTem = new Game(gameID, officialID, athletes, results, points);
				game.add(gameTem);
			}
		} catch (Exception e) {
		}

	}

	public void identifyType(ArrayList<String> participants) {
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

	public void loadOfficial(ArrayList<String> newOfficials) {
		try {
			this.officials.add(new Officials(newOfficials.get(0), newOfficials.get(2),
					Integer.valueOf(newOfficials.get(3)), newOfficials.get(4)));

		} catch (Exception e) {
			System.out.println("Loading official data is error!");
		}
	}

	public void loadAthlete(ArrayList<String> newAthletes) {
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
