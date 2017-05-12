package Ozlympic;

import java.util.ArrayList;

public class Game {
	private String gameID;
	private int type;
	private String officialID;
	private String time;
	private ArrayList<String> athletes = new ArrayList<String>();
	private ArrayList<Integer> results = new ArrayList<Integer>();
	private ArrayList<Integer> ranks = new ArrayList<Integer>();

	public Game(String gameID, int type, String officialID, ArrayList<String> athletes) {
		this.gameID = gameID;
		this.type = type;
		this.officialID = officialID;
		this.athletes = athletes;
	}

	public String getGameID() {
		return gameID;
	}

	public void setGameID(String gameID) {
		this.gameID = gameID;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getOfficialID() {
		return officialID;
	}

	public void setOfficialID(String officialID) {
		this.officialID = officialID;
	}

	public ArrayList<String> getAthletes() {
		return athletes;
	}

	public void setAthletes(ArrayList<String> athletes) {
		this.athletes = athletes;
	}

	public ArrayList<Integer> getResults() {
		return results;
	}

	public void setResults(ArrayList<Integer> results) {
		this.results = results;
	}

	public ArrayList<Integer> getRanks() {
		return ranks;
	}

	public void setRanks(ArrayList<Integer> ranks) {
		this.ranks = ranks;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
}
