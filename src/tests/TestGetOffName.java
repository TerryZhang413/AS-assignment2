package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import Ozlympic.Driver;

public class TestGetOffName {
	
	Driver driver;
	@Before
	public void setUp() throws Exception {
		driver = new Driver();
	}

	@Test
	public void testAddoffical1()  {
		String offical=driver.getOffName("Oz025");
		assertEquals("LILY",offical);
	}
	
	@Test
	public void testAddoffical2()  {
		String offical=driver.getOffName("Oz026");
		assertEquals("HEILINE",offical);
	}
	
	@Test
	public void testAddoffical3()  {
		String offical=driver.getOffName("Oz027");
		assertEquals("JACK",offical);
	}
}
