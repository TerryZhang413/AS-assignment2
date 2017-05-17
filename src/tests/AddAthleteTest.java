package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Exception.GameFullException;
import Exception.NoThisAthleteException;
import Exception.RepeatAthleteJoinException;
import Exception.WrongTypeException;
import Ozlympic.Athletes;
import Ozlympic.Driver;

public class AddAthleteTest {
	Driver driver;

	@Before
	public void setUp() throws Exception {
		driver = new Driver();
		driver.setGameType(1);
	}

	@Test
	public void testAddAthlete1()
			throws WrongTypeException, NoThisAthleteException, GameFullException, RepeatAthleteJoinException {
		driver.addAthlete("Oz001");
		String athleteID = driver.getPresentAthlete().get(0);
		assertEquals("Oz001", athleteID);
	}

	@Test
	public void testAddAthlete2()
			throws WrongTypeException, NoThisAthleteException, GameFullException, RepeatAthleteJoinException {
		driver.addAthlete("Oz002");
		String athleteID = driver.getPresentAthlete().get(0);
		assertEquals("Oz002", athleteID);
	}

	@Test(expected = WrongTypeException.class)
	public void testAddAthlete3()
			throws WrongTypeException, NoThisAthleteException, GameFullException, RepeatAthleteJoinException {
		driver.addAthlete("Oz003");
	}

	@Test(expected = NoThisAthleteException.class)
	public void testAddAthlete4()
			throws WrongTypeException, NoThisAthleteException, GameFullException, RepeatAthleteJoinException {
		driver.addAthlete("Null");
	}

	@Test(expected = RepeatAthleteJoinException.class)
	public void testAddAthlete5()
			throws WrongTypeException, NoThisAthleteException, GameFullException, RepeatAthleteJoinException {
		driver.addAthlete("Oz002");
		driver.addAthlete("Oz002");
	}

	@Test(expected = GameFullException.class)
	public void testAddAthlete6()
			throws WrongTypeException, NoThisAthleteException, GameFullException, RepeatAthleteJoinException {
		ArrayList<Athletes> athletes = driver.getAthelte();
		for (Athletes athlete : athletes) {
			if (athlete.getAthleteType() == 1 || athlete.getAthleteType() == 4)
				driver.addAthlete(athlete.getUserID());
		}
	}

}
