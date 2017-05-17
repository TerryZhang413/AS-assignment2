package Ozlympic;

/**
 * @author Yipeng Zhang
 * @content Game object storing Game information
 */
import java.util.ArrayList;

public class Game {
	private String gameID;
	private int type;
	private String officialID;
	private String time;
	private ArrayList<String> athletes = new ArrayList<String>();
	private ArrayList<Integer> results = new ArrayList<Integer>();
	private ArrayList<Integer> points = new ArrayList<Integer>();

	public Game(String gameID, int type, String officialID, ArrayList<String> athletes) {
		this.gameID = gameID;
		this.type = type;
		this.officialID = officialID;
		this.athletes = athletes;
	}

	public Game(String gameID, String officialID, ArrayList<String> athletes, ArrayList<Integer> results,
			ArrayList<Integer> points) {
		this.gameID = gameID;
		this.type = 0;
		this.officialID = officialID;
		this.athletes = athletes;
		this.results = results;
		this.points = points;
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

	public ArrayList<Integer> getPoints() {
		return points;
	}

	public void setPoints(ArrayList<Integer> points) {
		this.points = points;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
}
