package Ozlympic;

import java.io.IOException;
import java.util.ArrayList;

import Exception.ReadDataBaseException;
import ReadData.DataBase;
import ReadData.ReadFile;

public class LoadData {
	private ArrayList<Athletes> athletes = new ArrayList<Athletes>();
	private ArrayList<Officials> officials = new ArrayList<Officials>();
	ArrayList<Game> game = new ArrayList<Game>();

	public LoadData(ArrayList<Game> game, ArrayList<Officials> officials, ArrayList<Athletes> athletes) {
		this.game = game;
		this.officials = officials;
		this.athletes = athletes;

	}

	public void loadData() {
		ReadFile readFile = new ReadFile();
		ArrayList<String> participants = new ArrayList<String>();
		DataBase DB = new DataBase();
		ArrayList<String> cols = new ArrayList<String>(5);
		cols.add("ID");
		cols.add("TYPE");
		cols.add("NAME");
		cols.add("AGE");
		cols.add("STATE");

		try {
			participants = DB.readDB("Participant", cols);
		} catch (ReadDataBaseException e) {
		}
		if (participants.size() == 0) {
			try {
				participants = readFile.readText("Data/participants.txt");
				DB.writeDB("Participant", cols, participants);
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Can not find file!");
			}
		}
		identifyType(participants);

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
