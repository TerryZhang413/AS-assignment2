package tests;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import Ozlympic.Driver;

public class GetAthleteInfTest {

	Driver driver;
	
	@Before
	public void setUp() throws Exception {
		driver = new Driver();
	}

	@Test
	public void testGetAthleteInfo1()  {
		String[] athlete= driver.getAthleteInf("Oz001");
		assertEquals("JOHN",athlete[0]);
	}
	@Test
	public void testGetAthleteInfo2()  {
		String[] athlete= driver.getAthleteInf("Oz016");
		assertEquals("29",athlete[1]);
	}
	@Test
	public void testGetAthleteInfo3()  {
		String[] athlete= driver.getAthleteInf("Oz020");
		assertEquals("VIC",athlete[2]);
	}
	@Test
	public void testGetAthleteInfo4()  {
		String[] athlete= driver.getAthleteInf("Oz024");
		assertEquals("Super Athlete",athlete[3]);
	}
}
