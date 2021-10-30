package pkgMain;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;

class GardenTest {

	@Test
	void testGetPlantCost() {
		Garden g = new Garden();
		g.setPlantCost(55.5);
		assertEquals(g.getPlantCost(), 55.5);
	}

	@Test
	void testGetBudegt() {
		Garden g = new Garden(100, 0);
		assertEquals(g.getBudget(), 100);
		g.setBudget(200);
		assertEquals(g.getBudget(), 200);
		g.setBudget(-100);
		assertEquals(g.getBudget(), 200);
	}

	@Test
	void testgetLandUsed() {
		Garden g = new Garden();
		g.setLandUsed(1000);
		assertEquals(g.getLandUsed(), 1000);
	}

	@Test
	void testGetLandTotal() {
		Garden g = new Garden(0, 10000);
		assertEquals(g.getLandTotal(), 10000);
		g.setLandTotal(2000);
		assertEquals(g.getLandTotal(), 2000);
	}

	@Test
	void testGetMoisture() {
		Garden g = new Garden();
		HashSet<Moisture> m = new HashSet<Moisture>();
		m.add(Moisture.DRY);
		g.setMoisture(m);
		assertEquals(g.getMoisture(), m);
		g.addMoisture(Moisture.WET);
		m.add(Moisture.WET);
		assertEquals(g.getMoisture(), m);
		g.removeMoisture(Moisture.DRY);
		m.remove(Moisture.DRY);
		assertEquals(g.getMoisture(), m);
	}

	@Test
	void testGetSunlight() {
		Garden g = new Garden();
		HashSet<Sunlight> s = new HashSet<Sunlight>();
		s.add(Sunlight.PARTSUN);
		g.setSunlight(s);
		assertEquals(g.getSunlight(), s);
		g.addSunlight(Sunlight.FULLSUN);
		s.add(Sunlight.FULLSUN);
		assertEquals(g.getSunlight(), s);
		g.removeSunlight(Sunlight.PARTSUN);
		s.remove(Sunlight.PARTSUN);
		assertEquals(g.getSunlight(), s);
	}

	@Test
	void testGetSoiltype() {
		Garden g = new Garden();
		HashSet<SoilType> s = new HashSet<SoilType>();
		s.add(SoilType.CLAY);
		g.setSoiltype(s);
		assertEquals(g.getSoiltype(), s);
		g.addSoiltype(SoilType.LOAM);
		s.add(SoilType.LOAM);
		assertEquals(g.getSoiltype(), s);
		g.removeSoiltype(SoilType.CLAY);
		s.remove(SoilType.CLAY);
		assertEquals(g.getSoiltype(), s);
	}

	@Test
	void testGetLepCount() {
		Garden g = new Garden();
		g.setLepCount(409);
		assertEquals(g.getLepCount(), 409);
	}

	@Test
	void testGetPlacedPlants() {
		Garden g = new Garden();
		g.setCurrentPlants(null);
		assertEquals(g.getPlacedPlants(), null);

	}

	@Test
	void testGetPlantsToAdd() {
		Garden g = new Garden();
		g.setPlantsToAdd(null);
		assertEquals(g.getPlantsToAdd(), null);
		Plant p = new Plant();
	}

	@Test
	void testGetPlacedLeps() {
		Garden g = new Garden();
		g.setCurrentLeps(null);
		assertEquals(g.getCurrentLeps(), null);
	}

	@Test
	void testGetCompostPlants() {
		Garden g = new Garden();
		g.setCompostPlants(null);
		assertEquals(g.getCompostPlants(), null);
	}

	@Test
	void testGetName() {
		Garden g = new Garden();
		g.setName("Test");
		assertEquals(g.getName(), "Test");
	}

	@Test
	void testGetUniquePlants() {
		Garden g = new Garden();
		ArrayList<Double> points = new ArrayList<Double>();
		points.add(0.0);
		points.add(0.0);
		points.add(10.0);
		points.add(10.0);
		points.add(10.0);
		points.add(0.0);

		g.setPoints(points);
		g.scaleFromFT(10);

		Plant p = new Plant();
		Plant p2 = new Plant();
		g.addPlacedPlant(p, 0, 0);
		g.addPlacedPlant(p2, 0, 0);
		HashSet<Plant> gp = new HashSet<Plant>();
		gp.add(p);
		gp.add(p2);
		GardenPlant gp2 = new GardenPlant(p2, 0, 0);
		HashSet<Plant> up = (HashSet<Plant>) g.getUniquePlants();
		assertEquals(up, gp);

	}

	@Test
	void testAddPlant() {
		Garden g = new Garden();
		Plant p = new Plant();
		Plant p2 = new Plant();
		g.addPlant(p);
		g.addPlant(p2);
		HashSet<Plant> hp = new HashSet();
		hp.add(p);
		hp.add(p2);
		assertEquals(g.getPlantsToAdd(), hp);
	}

	@Test
	void testMovePlant() {
		Garden g = new Garden();
		ArrayList<Double> points = new ArrayList<Double>();
		points.add(0.0);
		points.add(0.0);
		points.add(10.0);
		points.add(10.0);
		points.add(10.0);
		points.add(0.0);

		g.setPoints(points);
		g.scaleFromFT(10);

		Plant p = new Plant();
		g.addPlacedPlant(p, 0, 0);
		Point2D start = new Point2D(0, 0);
		Point2D end = new Point2D(1.0, 1.0);
		Point2D end2 = new Point2D(11.0, 11.0);
		g.movePlant(start, end);
		assertEquals(g.getGardenPlant(end), new GardenPlant(p, 1.0, 1.0));
		g.movePlant(end, end2);
		assertEquals(g.getGardenPlant(end2), new GardenPlant(p, 11.0, 11.0));
		g.movePlant(end2, start);
		assertEquals(g.getGardenPlant(start), new GardenPlant(p, 0.0, 0.0));
	}

	@Test
	void testGetGardenInfo() {
		Garden g = new Garden();
		g.getGardenInfo();
	}

	@Test
	void testInsideGarden() {
		Garden g = new Garden();
		ArrayList<Double> p = new ArrayList<Double>();
		ObservableList<Double> points = FXCollections.observableList(p);
		points.add(0.0);
		points.add(0.0);
		points.add(0.0);
		points.add(100.0);
		points.add(100.0);
		points.add(100.0);
		points.add(100.0);
		points.add(0.0);
		g.setPoints(points);
//		g.addPoint(0,0);
//		g.addPoint(0,100);
//		g.addPoint(100,100);
//		g.addPoint(100,0);

		assertTrue(g.insideGarden(50, 50));
		assertFalse(g.insideGarden(105, 105));
		assertEquals(g.landTotal, 100 * 100);
		Point2D p2 = new Point2D(105, 105);
		assertFalse(g.insideGarden(p2));
		Plant pl = new Plant();
		g.addPlacedPlant(pl, 50, 50);
		Point2D p1 = new Point2D(50, 50);
		Point2D p3 = new Point2D(75, 75);
		assertTrue(g.movePlant(p1, p3));
		p1 = new Point2D(75, 75);
		p3 = new Point2D(105, 105);
		assertFalse(g.movePlant(p1, p3));
		HashSet<Plant> ph = new HashSet<Plant>();
		ph.add(pl);
		g.setPlantsToAdd(ph);
		g.removePlantAdded(pl, 1);
		GardenPlant gp1 = new GardenPlant(pl, 0, 0);
		g.addPlacedPlant(pl, 0, 0);
		g.removePlantPlaced(gp1);
	}

	@Test
	void testSerializable() {
		ArrayList<Double> points = new ArrayList<Double>();
		points.add(0.0);
		points.add(0.0);
		points.add(1.0);
		points.add(1.0);
		points.add(2.0);
		points.add(2.0);
		try {
			Garden g = new Garden();
			g.setBudget(100000);
			g.setPoints(points);
			FileOutputStream fos = new FileOutputStream("gardens/garden.gar");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(g);
			oos.close();
		} catch (Exception e) {
			System.out.println("1" + e);
		}
		try {
			FileInputStream fis = new FileInputStream("gardens/garden.gar");
			ObjectInputStream ois = new ObjectInputStream(fis);

			Garden g = (Garden) ois.readObject();
			ois.close();

			assertEquals(g.getBudget(), 100000);
			assertEquals(g.getPoints(), points);
		} catch (Exception e) {
			System.out.println("2" + e);
		}
	}

	@Test
	void testToString() {
		Garden g = new Garden(100, 500);
		g.setName("Test");
		g.setPlantCost(50);
		g.setLandUsed(250);
		g.setLepCount(500);
		assertEquals(g.toString(),
				"Garden name: Test\nPlaced Plants: []\n Plants to Add: []\n Current Leps: []\n Compost Plants:[]\n Budget: 100.0\n Plants cost: 50.0\n Land Used: 250.0\n Land Total: 500.0\n Lep count: 500");

	}

	@Test
	void testIsOverlap() {
		Garden g = new Garden();

		ArrayList<Double> points = new ArrayList<Double>();
		points.add(0.0);
		points.add(0.0);
		points.add(10.0);
		points.add(10.0);
		points.add(10.0);
		points.add(0.0);

		g.setPoints(points);
		g.scaleFromFT(10);

		Plant test = new Plant();
		test.setSpread(5);

		g.addPlacedPlant(test, 3, 3);
		GardenPlant gp = g.getGardenPlant(3, 3);
		assertEquals(g.isOverlap(test, 2, 1), true);
		assertEquals(g.isOverlap(test, 2, 6), true);
		assertEquals(g.isOverlap(test, 7, 1), true);
		assertEquals(g.isOverlap(gp, 3, 3), false);
		assertEquals(g.isOverlap(test, 15, 15), false);

		g.addPlacedPlant(test, 25, 25);
		assertEquals(g.isOverlap(test, 13.5, 13.5), false);
		assertEquals(g.isOverlap(gp, 25, 22), true);
	}

	@Test
	void testGetGardenPlant() {
		Garden g = new Garden();
		ArrayList<Double> points = new ArrayList<Double>();
		points.add(0.0);
		points.add(0.0);
		points.add(10.0);
		points.add(10.0);
		points.add(10.0);
		points.add(0.0);

		g.setPoints(points);
		g.scaleFromFT(10);

		Plant p = new Plant();
		g.addPlacedPlant(p, 0, 0);
		g.addPlacedPlant(p, 1.0, 0);
		Point2D p2 = new Point2D(0.0, 0.0);
		assertEquals(g.getGardenPlant(p2).getPlant().toString(), p.toString());
		assertEquals(g.getGardenPlant(p2.getX(), p2.getY()).getPlant().toString(), p.toString());
		Point2D p3 = new Point2D(10, 10);
		assertEquals(g.getGardenPlant(p3), null);
		assertEquals(g.getGardenPlant(p3.getX(), p3.getY()), null);

	}

	@Test
	void testGetScale() {
		Garden g = new Garden();

		ArrayList<Double> points = new ArrayList<Double>();
		points.add(0.0);
		points.add(0.0);
		points.add(1.0);
		points.add(1.0);
		points.add(1.0);
		points.add(0.0);

		g.setPoints(points);

		g.scaleFromFT(1);

		assertEquals(g.getScale(), 1.0);

		g.scaleFromFT(2);
		assertEquals(g.getScale(), 0.5);

	}

	@Test
	void testSetScale() {
		Garden g = new Garden();
		g.setScale(9.88);

		assertEquals(g.getScale(), 9.88);
		g.setScale(-1);
		assertEquals(g.getScale(), 9.88);
	}

	@Test
	void testGetScaledLandTotal() {
		Garden g = new Garden();
		ArrayList<Double> points = new ArrayList<Double>();
		points.add(0.0);
		points.add(0.0);
		points.add(1.0);
		points.add(1.0);
		points.add(1.0);
		points.add(0.0);
		g.setPoints(points);
		g.scaleFromFT(2);

		assertEquals(g.getScaledLandTotal(), 2);
	}

	@Test
	void testGetScaledLandUsed() {
		Garden g = new Garden();
		ArrayList<Double> points = new ArrayList<Double>();
		points.add(10.0);
		points.add(0.0);
		points.add(10.0);
		points.add(10.0);
		points.add(0.0);
		points.add(0.0);
		g.setPoints(points);
		Plant p = new Plant();
		p.setSpread(2);
		g.scaleFromFT(2);

		g.addPlacedPlant(p, 0, 0);

		assertEquals(g.getScaledLandUsed(), 3.141592653589793);
	}

	@Test
	void testEquals() {
		Garden g1 = new Garden();
		g1.setBudget(5);
		g1.setName("test1");

		Garden g2 = new Garden();
		g2.setBudget(5);
		g2.setName("test1");

		assertEquals(g1, g2);

		Garden g3 = new Garden();
		g3.setBudget(100);
		g3.setName("test1.2");
		g3.plantCost = -1;

		assertNotEquals(g1, g3);

		Plant p = new Plant();
		assertNotEquals(g1, p);

		Garden g4 = new Garden();

		g4.setCompostPlants(null);
		g4.setCurrentLeps(null);
		g4.setLepCount(-1);
		g4.setLandUsed(-1);
		g4.setLandTotal(-1);
		g4.widthFT = -1;
		g4.scale = -1;
		g4.setPlantsToAdd(null);
		g4.points = null;
		g4.setMoisture(null);
		g4.setSunlight(null);
		g4.setSoiltype(null);
		g4.placedPlants = null;
		g4.uniquePlants = null;
		g4.plantCost = -1;

		g1.points = new ArrayList<Double>();

		assertNotEquals(g1, g4);
		assertNotEquals(g4, g1);
		assertNotEquals(g4, g3);

		assertEquals(g4, g4);
	}

	@Test
	void testHeightWidth() {
		Garden g = new Garden();
		ArrayList<Double> points = new ArrayList<Double>();
		points.add(10.0);
		points.add(0.0);
		points.add(10.0);
		points.add(10.0);
		points.add(0.0);
		points.add(0.0);
		g.setPoints(points);
		assertEquals(10.0, g.getHeight());
		assertEquals(10.0, g.getWidth());

		double[] wPoints = g.getWidthPoints();
		double[] hPoints = g.getHeightPoints();

		assertEquals(0.0, wPoints[0]);
		assertEquals(10.0, wPoints[1]);

		assertEquals(0.0, hPoints[0]);
		assertEquals(10.0, hPoints[1]);

		points = new ArrayList<Double>();
		points.add(2.0);
		points.add(2.0);
		points.add(10.0);
		points.add(10.0);
		points.add(10.0);
		points.add(0.0);
		points.add(5.0);
		points.add(5.0);
		points.add(15.0);
		points.add(15.0);
		points.add(0.0);
		points.add(0.0);

		g.setPoints(points);

		assertEquals(15.0, g.getHeight());
		assertEquals(15.0, g.getWidth());

		wPoints = g.getWidthPoints();
		hPoints = g.getHeightPoints();

		assertEquals(0.0, wPoints[0]);
		assertEquals(15.0, wPoints[1]);

		assertEquals(0.0, hPoints[0]);
		assertEquals(15.0, hPoints[1]);
	}
}
