package pkgMain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MoistureTest {

	@Test
	void test() {
		Moisture m = Moisture.AVERAGE;
		assertEquals(m.getName(), "Average");
		m = Moisture.DRY;
		assertEquals(m.getName(), "Dry");
		m = Moisture.WET;

		assertEquals(m.getName(), "Wet");
		assertEquals(m.toString(), "Wet");
	}

}
