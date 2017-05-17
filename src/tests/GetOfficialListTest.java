package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Ozlympic.Driver;
import Ozlympic.Officials;

public class GetOfficialListTest {
	Driver driver;

	@Before
	public void setUp() throws Exception {
		driver = new Driver();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetOfficialList() {
		ArrayList<Officials> offial = driver.getOfficialList();
		assertNotNull(offial);
	}

}
