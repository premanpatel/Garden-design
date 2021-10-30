package pkgMain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class SoilTypeTest {

	@Test
	void test() {
		SoilType s = SoilType.CLAY;
		assertEquals(s.getName(), "Clay");
		s = SoilType.LOAM;
		assertEquals(s.getName(), "Loam");
		s = SoilType.SAND;
		
		assertEquals(s.getName(), "Sand");	
		assertEquals(s.toString(), "Sand");
	}

}
