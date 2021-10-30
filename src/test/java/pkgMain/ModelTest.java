package pkgMain;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

import javafx.geometry.Point2D;

class ModelTest {

	@Test
	void testGetAllPlants() {
		Model m = new Model();
		assertEquals(m.getAllPlants(), m.allPlants);
	}

	@Test
	void testGetAllLeps() {
		Model m = new Model();
		assertEquals(m.getAllLeps(), m.allLeps);
	}

	@Test
	void testGetGarden() {
		Model m = new Model();
		assertEquals(m.getGarden(), m.garden);
	}

	@Test
	void testCreate() {
		Model m = new Model();

		m.createGarden();
		m.createLeps();
		m.createPlants();
		HashSet<Moisture> hm = new HashSet();
		hm.add(Moisture.DRY);
		m.getGarden().setMoisture(hm);
		HashSet<Sunlight> hl = new HashSet();
		hl.add(Sunlight.SHADE);
		m.getGarden().setSunlight(hl);
		HashSet<SoilType> ht = new HashSet();
		ht.add(SoilType.CLAY);
		m.getGarden().setSoiltype(ht);
		Lep l = m.createLep("monarch", "species", "butterfly");// String genus, String species, String commonName,
																// String plantType, String URL, double spread, String
																// soilType, String sunlight, String moisture, String
																// description, int lepCount
		Plant p = m.createPlant("Croton", "alabamensis", "bush", "shrub",
				"https://mtcubacenter.org/wp-content/uploads/2016/06/Acer_pensylvancum_2-960x683.jpg", 10.0,
				"Clay,Sand,Loam", "filtered-shade,full-sun, shade", "moist,dry,average", "its a plant", 100);
		Plant p2 = m.createPlant("Croton", "alabamensis", "bush", "flower",
				"https://mtcubacenter.org/wp-content/uploads/2016/06/Acer_pensylvancum_2-960x683.jpg", 10.0, "dirt",
				"sun", "water", "its a plant", 100);
		assertEquals(m.getAllPlants(), m.allPlants);

		assertEquals(m.getAllLeps(), m.allLeps);

		assertEquals(m.getGarden(), m.garden);
		HashSet<Plant> hp = new HashSet<Plant>();
		hp.add(p);
		assertEquals(m.getLep("monarch", "species"), l);
		assertEquals(m.getPlant("Croton", "alabamensis").toString(), p.toString());
		assertEquals(m.getPlant("Croton - alabamensis").toString(), p.toString());
		assertEquals(m.getPlantsMatchingConditions(), hp);
		assertEquals(m.getLep("monarc", "species"), null);
		assertEquals(m.getPlant("Croton", "alabamensi"), null);
	}

	@Test
	void testLoad() {
		Model m = new Model();
		m.createGarden();
		Garden g = m.getGarden();
		g.setName("garden");
		m.saveGarden();
		File fis = new File("garden");
		m.loadGarden(fis);
		Garden h = m.getGarden();

		// Not the right file path, so it should not load
		assertNotEquals(g, h);

		m.createGarden();
		g = m.getGarden();
		g.setName("garden");
		g.setBudget(100);
		m.saveGarden();

		File fi2 = new File("gardens/garden.gar");
		m.loadGarden(fi2);

		Garden i = m.getGarden();
		assertEquals(g, i);

		ArrayList<Double> points = new ArrayList<Double>();
		points.add(0.0);
		points.add(0.0);
		points.add(10.0);
		points.add(10.0);
		points.add(10.0);
		points.add(0.0);
		g.setPoints(points);
		assertNotEquals(g, i);
	}

	@Test
	void testAddPlant() {
		Model m = new Model();
		Plant p = new Plant("plant", "dis", "gen", "species");
		m.addPlant(p);
		assertTrue(m.getGarden().getPlantsToAdd().contains(p));
	}

	@Test
	void testSaveGarden() {
		Model m = new Model();
		m.createGarden();
		m.getGarden().setName("garden");
		Garden g = m.getGarden();
		File fis = new File("gardens/garden.gar");
		m.saveGarden();
		m.loadGarden(fis);
		Garden h = m.getGarden();
		assertEquals(h, g);

		m.createGarden();
		m.getGarden().name = null;
		m.saveGarden();

		m.garden = null;
		m.saveGarden();
	}

	@Test
	void testGetPlantsMatchingConditions() {
		Model m = new Model();

		Plant p1 = m.createPlant("g1", "s1", "n1", "h", "url", 0, "Loam", "full-sun", "medium", "des", 0);
		Plant p2 = m.createPlant("g2", "s2", "n2", "h", "url", 0, "Clay", "full-sun", "medium", "des", 0);
		m.getGarden().addMoisture(Moisture.AVERAGE);
		m.getGarden().addSunlight(Sunlight.FULLSUN);
		m.getGarden().addSoiltype(SoilType.LOAM);

		for (Plant p : m.getPlantsMatchingConditions()) {
			assertEquals(p, p1);
		}
	}

	@Test
	void testDeleteGarden() {

		Model m = new Model();
		m.createGarden();
		Garden g = m.getGarden();

		g.setName("garden");

		m.saveGarden();

		assertTrue(m.deleteGarden(new File("gardens/garden.gar")));
		assertFalse(m.deleteGarden(new File("gardens/garden.gar")));
	}

	@Test
	void testLoadGardenFromFile() {
		Model m = new Model();
		m.createGarden();
		Garden g = m.getGarden();
		g.setName("garden");
		m.saveGarden();

		Garden h = m.loadGardenFromFile(new File("gardens/garden.gar"));
		System.out.println(g);
		System.out.println(h);

		assertEquals(g, h);

		m.createGarden();
		Garden g2 = m.getGarden();
		g2.setName("garden2");
		m.saveGarden();

		Garden h2 = m.loadGardenFromFile(new File("gardens/garden2.gar"));

		assertEquals(g2, h2);
		assertNotEquals(g, h2);

		Garden h3 = m.loadGardenFromFile(new File("gardens/garden3.gar"));
		assertNull(h3);
	}

}
