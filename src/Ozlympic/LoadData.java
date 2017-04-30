package Ozlympic;

import java.io.IOException;
import java.util.ArrayList;

public class LoadData {
    private ArrayList<Athletes> athletes = new ArrayList<Athletes>();
    private ArrayList<Officials> officials = new ArrayList<Officials>();
    ArrayList<Game> game = new ArrayList<Game>();

    public LoadData(ArrayList<Game> game, ArrayList<Officials> officials,
            ArrayList<Athletes> athletes) {
        this.game = game;
        this.officials = officials;
        this.athletes = athletes;

    }

    public void loadData() {
        ReadFile readFile = new ReadFile();
        try {
            loadAthlete(readFile.readText("Data/Athlete List.txt"));
            loadOfficial(readFile.readText("Data/Official List.txt"));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Can not find file!");
        }

    }

    public void loadOfficial(ArrayList<String> newOfficials) {
        try {
            for (int i = 0; i < newOfficials.size(); i = i + 4) {
                this.officials.add(new Officials(newOfficials.get(i),
                        newOfficials.get(i + 1),
                        Integer.valueOf(newOfficials.get(i + 2)),
                        newOfficials.get(i + 3)));
            }
        } catch (Exception e) {
            System.out.println("Loading official data is error!");
        }
    }

    public void loadAthlete(ArrayList<String> newAthletes) {
        try {
            for (int i = 0; i < newAthletes.size(); i = i + 5) {
                this.athletes.add(
                        new Athletes(newAthletes.get(i), newAthletes.get(i + 1),
                                Integer.valueOf(newAthletes.get(i + 2)),
                                newAthletes.get(i + 3),
                                Integer.valueOf(newAthletes.get(i + 4))));
            }
        } catch (Exception e) {
            System.out.println("Loading athlete data is error!");
        }
    }

}
