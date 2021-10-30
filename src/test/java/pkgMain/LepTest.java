package pkgMain;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

class LepTest {

	@Test
	void testLep() {
		Lep l = new Lep();
	}

	@Test
	void testGettersSetters() {
		Lep l = new Lep();
		l.setName("name");
		l.setDescription("description");
		l.setGenus("genus");
		l.setSpecies("species");
		assertEquals(l.getName(), "name");
		assertEquals(l.getDescription(), "description");
		assertEquals(l.getGenus(), "genus");
		assertEquals(l.getSpecies(), "species");
		Plant p = new Plant();
		HashSet<Plant> hs = new HashSet<Plant>();
		l.setSupportingPlants(hs);
		assertEquals(l.getSupportingPlants(), hs);
		Plant p2 = new Plant();
		hs.add(p2);
		l.addSupportingPlant(p2);
		assertEquals(l.getSupportingPlants(), hs);
		assertEquals(l.toString(), "Lep [name=name, genus=genus, description=description]");

	}

	@Test
	void testLepConstructor() {
		Lep l = new Lep("name", "description", "genus", "species");

		assertEquals(l.getName(), "name");
		assertEquals(l.getDescription(), "description");
		assertEquals(l.getGenus(), "genus");
		assertEquals(l.getSpecies(), "species");

		assertEquals(l.toString(), "Lep [name=name, genus=genus, description=description]");

	}

}
