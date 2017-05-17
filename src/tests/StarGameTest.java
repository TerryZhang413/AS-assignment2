package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Exception.GameFullException;
import Exception.NoGameCreated;
import Exception.NoRefereeException;
import Exception.NoThisAthleteException;
import Exception.NoThisOfficialException;
import Exception.NullResultException;
import Exception.RepeatAthleteJoinException;
import Exception.TooFewAthleteException;
import Exception.WrongTypeException;
import Ozlympic.Athletes;
import Ozlympic.Driver;

public class StarGameTest {

	Driver driver;

	@Before
	public void setUp() throws Exception {
		driver = new Driver();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testStarGame1()
			throws WrongTypeException, NoThisAthleteException, GameFullException, RepeatAthleteJoinException,
			NoGameCreated, TooFewAthleteException, NoRefereeException, NullResultException, NoThisOfficialException {
		int count1 = driver.getGame(true).size();
		driver.setGameType(1);
		ArrayList<Athletes> athletes = driver.getAthelte();
		int i = 0;
		for (Athletes athlete : athletes) {
			if (i == 8)
				break;
			if (athlete.getAthleteType() == 1 || athlete.getAthleteType() == 4)
				driver.addAthlete(athlete.getUserID());
			i++;
		}
		driver.addOfficial("Oz025");
		driver.starGame();
		int count2 = driver.getGame(true).size();
		assertEquals(count1 + 1, count2);
	}

	@Test
	public void testStarGame2()
			throws WrongTypeException, NoThisAthleteException, GameFullException, RepeatAthleteJoinException,
			NoGameCreated, TooFewAthleteException, NoRefereeException, NullResultException, NoThisOfficialException {
		int count1 = driver.getGame(true).size();
		driver.setGameType(2);
		ArrayList<Athletes> athletes = driver.getAthelte();
		int i = 0;
		for (Athletes athlete : athletes) {
			if (i == 8)
				break;
			if (athlete.getAthleteType() == 2 || athlete.getAthleteType() == 4)
				driver.addAthlete(athlete.getUserID());
			i++;
		}
		driver.addOfficial("Oz025");
		driver.starGame();
		int count2 = driver.getGame(true).size();
		assertEquals(count1 + 1, count2);
	}

	@Test
	public void testStarGame3()
			throws WrongTypeException, NoThisAthleteException, GameFullException, RepeatAthleteJoinException,
			NoGameCreated, TooFewAthleteException, NoRefereeException, NullResultException, NoThisOfficialException {
		int count1 = driver.getGame(true).size();
		driver.setGameType(3);
		ArrayList<Athletes> athletes = driver.getAthelte();
		int i = 0;
		for (Athletes athlete : athletes) {
			if (i == 8)
				break;
			if (athlete.getAthleteType() == 3 || athlete.getAthleteType() == 4)
				driver.addAthlete(athlete.getUserID());
			i++;
		}
		driver.addOfficial("Oz025");
		driver.starGame();
		int count2 = driver.getGame(true).size();
		assertEquals(count1 + 1, count2);
	}

	@Test(expected = NoGameCreated.class)
	public void testStarGame4() throws NoGameCreated, TooFewAthleteException, NoRefereeException {
		driver.starGame();
	}

	@Test(expected = TooFewAthleteException.class)
	public void testStarGame5()
			throws WrongTypeException, NoThisAthleteException, GameFullException, RepeatAthleteJoinException,
			NoGameCreated, TooFewAthleteException, NoRefereeException, NoThisOfficialException {
		driver.setGameType(1);
		ArrayList<Athletes> athletes = driver.getAthelte();
		int i = 0;
		for (Athletes athlete : athletes) {
			if (i == 3)
				break;
			if (athlete.getAthleteType() == 1 || athlete.getAthleteType() == 4)
				driver.addAthlete(athlete.getUserID());
			i++;
		}
		driver.addOfficial("Oz025");
		driver.starGame();
	}

	@Test(expected = NoRefereeException.class)
	public void testStarGame6() throws WrongTypeException, NoThisAthleteException, GameFullException,
			RepeatAthleteJoinException, NoGameCreated, TooFewAthleteException, NoRefereeException {
		driver.setGameType(1);
		ArrayList<Athletes> athletes = driver.getAthelte();
		int i = 0;
		for (Athletes athlete : athletes) {
			if (i == 8)
				break;
			if (athlete.getAthleteType() == 1 || athlete.getAthleteType() == 4)
				driver.addAthlete(athlete.getUserID());
			i++;
		}
		driver.starGame();
	}

}
