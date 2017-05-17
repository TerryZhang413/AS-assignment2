package tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Exception.OutOfGameType;
import Ozlympic.Driver;

public class SelectGameTest {

	Driver driver;

	@Before
	public void setUp() throws Exception {
		driver = new Driver();
	}

	@Test
	public void testSelectGame1() throws OutOfGameType {
		Boolean check = driver.selectGame(1);
		assertEquals(true, check);

	}

	@Test
	public void testSelectGame2() throws OutOfGameType {
		Boolean check = driver.selectGame(2);
		assertEquals(true, check);

	}

	@Test
	public void testSelectGame3() throws OutOfGameType {
		Boolean check = driver.selectGame(3);
		assertEquals(true, check);

	}

	@Test(expected = OutOfGameType.class)
	public void testSelectGame4() throws OutOfGameType {
		Boolean check = driver.selectGame(4);
	}

}
