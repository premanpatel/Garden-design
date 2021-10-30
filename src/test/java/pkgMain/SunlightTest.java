package pkgMain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class SunlightTest {

	@Test
	void test() {
		Sunlight s = Sunlight.SHADE;
		assertEquals(s.getName(), "Shade");
		s = Sunlight.FULLSUN;
		assertEquals(s.getName(), "Full Sun");
		s = Sunlight.PARTSUN;

		assertEquals(s.getName(), "Part Sun");
		assertEquals(s.toString(), "Part Sun");
	}

}
