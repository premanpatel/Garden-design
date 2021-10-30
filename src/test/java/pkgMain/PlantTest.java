package pkgMain;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

class PlantTest {
	@Test
	void testGettersSetters() {
		Plant p = new Plant();
		p.setCost(10);
		p.setDescription("des");
		p.setGenus("genus");
		p.setLepCount(50);
		HashSet<Moisture> hm = new HashSet<Moisture>();
		hm.add(Moisture.DRY);
		p.setMoisture(hm);
		HashSet<Sunlight> hsu = new HashSet<Sunlight>();
		hsu.add(Sunlight.SHADE);
		p.setSunlight(hsu);
		HashSet<SoilType> hso = new HashSet<SoilType>();
		hso.add(SoilType.CLAY);
		p.setSoilType(hso);
		p.setName("name");
		p.setSpecies("species");
		p.setSpread(5);
		Lep l = new Lep();
		p.addSupportedLep(l);
		HashSet<Lep> hl = new HashSet<Lep>();
		hl.add(l);
		p.setSupportedLeps(hl);
		assertEquals(p.getCost(), 10);
		assertEquals(p.getDescription(), "des");
		assertEquals(p.getLepCount(), 50);
		assertEquals(p.getGenus(), "genus");
		assertEquals(p.getMoisture(), hm);
		assertEquals(p.getSunlight(), hsu);
		assertEquals(p.getSoilType(), hso);
		assertEquals(p.getName(), "name");
		assertEquals(p.getSpecies(), "species");
		assertEquals(p.getSpread(), 5);
		assertEquals(p.getSupportedLeps(), hl);
		Lep l2 = new Lep();
		p.addSupportedLep(l2);
		hl.add(l2);
		assertEquals(p.getSupportedLeps(), hl);
		p.addMoisture(Moisture.WET);
		hm.add(Moisture.WET);
		p.addSunlight(Sunlight.FULLSUN);
		hsu.add(Sunlight.FULLSUN);
		p.addSoilType(SoilType.SAND);
		hso.add(SoilType.SAND);
		assertEquals(p.getMoisture(), hm);
		assertEquals(p.getSunlight(), hsu);
		assertEquals(p.getSoilType(), hso);
		assertEquals(p.toString(), "Plant [name=name, genus=genus, description=des]");
	}

	@Test
	void testURL() {
		Plant p = new Plant();
		p.setURL("url");

		assertEquals("url", p.getURL());
	}

}
