package Ozlympic;

/**
 * @author Yipeng Zhang
 * @content Athlete object storing athletes' personal information
 */

public class Athletes extends Users {
	private int athleteType;
	private int point;

	public Athletes(String userID, String name, int age, String state, int athleteType) {
		super(userID, name, age, state);
		this.setAthleteType(athleteType);
		this.setPoint(0);
	}

	public int getAthleteType() {
		return athleteType;
	}

	public void setAthleteType(int athleteType) {
		this.athleteType = athleteType;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}
}
