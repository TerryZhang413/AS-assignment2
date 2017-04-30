package Ozlympic;

public class Athlete extends User {
    private int athleteType;
    private int point;

    public Athlete(String userID, String name, int age, String state,
            int athleteType) {
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
