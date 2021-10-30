package pkgMain;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import javafx.geometry.Point2D;

class GardenPlantTest {

	@Test
	void testSetPlant() {
		Plant p1 = new Plant();
		GardenPlant gp = new GardenPlant(p1, 0, 0);
		Plant p2 = new Plant();
		gp.setPlant(p2);
		assertEquals(gp.getPlant(), p2);
		assertEquals(gp.getX(), 0);
		assertEquals(gp.getY(), 0);
		gp.setX(10);
		gp.setY(10);
		assertEquals(gp.getX(), 10);
		assertEquals(gp.getY(), 10);
		Point2D p2d = new Point2D(10, 10);
		assertEquals(gp.getLocation(), p2d);
		Point2D p2d1 = new Point2D(100, 100);
		gp.setLocation(p2d1);
		assertEquals(gp.getLocation(), p2d1);
		GardenPlant gp1 = new GardenPlant(p1, 100, 100);
		assertFalse(gp.equals(p1));
		assertFalse(gp.equals(gp1));
		gp1.setPlant(p2);
		assertTrue(gp.equals(gp1));
		int val = 100 * 10 + 100 * 1000;
//		assertEquals(gp.hashCode(),p2.hashCode() + val);
	}

}
