package pkgMain;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.HashSet;

/**
 * The model class is the model of the program, allowing creation and
 * interaction with a garden.
 * 
 * @author rbila
 *
 */
public class Model {

	Collection<Plant> allPlants;
	Collection<Lep> allLeps;

	Garden garden;

	private static final int TREE_AND_SHRUB_COST = 20;
	private static final int OTHER_PLANT_COST = 6;

	/**
	 * Creates a model with empty collections.
	 */
	public Model() {
		garden = new Garden();
		createLeps();
		createPlants();
	}

	/**
	 * Returns a collection of all plants.
	 * 
	 * @return all plants.
	 */
	public Collection<Plant> getAllPlants() {
		return allPlants;
	}

	/**
	 * Returns a collection of all leps.
	 * 
	 * @return all leps.
	 */
	public Collection<Lep> getAllLeps() {
		return allLeps;
	}

	/**
	 * Returns the model's garden.
	 * 
	 * @return The garden.
	 */
	public Garden getGarden() {
		return garden;
	}

	/**
	 * Sets the collection of all plants to an empty one.
	 */
	void createPlants() { // Read in from files
		allPlants = new HashSet<>();
	}

	/**
	 * Creates a new plant with the given values and adds it to the collection of
	 * all plants.
	 * 
	 * @param genus       The genus of the plant.
	 * @param species     The species of the plant.
	 * @param commonName  The common name of the plant.
	 * @param plantType   The type of plant that it is.
	 * @param URL         The link to the Mt. Cuba website.
	 * @param spread      The spread of the plant.
	 * @param soilType    The soil types that the plant can grow in.
	 * @param sunlight    The sunlight levels that the plant can grow in.
	 * @param moisture    The moisture levels that the plant can grow in.
	 * @param description The description of the plant.
	 * @param lepCount    The number of leps supported by the plant.
	 * @return The plant that was created.
	 */
	Plant createPlant(String genus, String species, String commonName, String plantType, String URL, double spread,
			String soilType, String sunlight, String moisture, String description, int lepCount) {

		Plant p = new Plant();
		p.setGenus(genus);
		p.setSpecies(species);
		p.setName(commonName);
		p.setLepCount(lepCount);
		p.setURL(URL);
		p.setSpread(spread);

		if (soilType.contains("Clay")) {
			p.addSoilType(SoilType.CLAY);
		}
		if (soilType.contains("Sand")) {
			p.addSoilType(SoilType.SAND);
		}
		if (soilType.contains("Loam")) {
			p.addSoilType(SoilType.LOAM);
		}

		if (plantType.contains("tree") || plantType.contains("shrub")) {
			p.setCost(TREE_AND_SHRUB_COST);
		} else {
			p.setCost(OTHER_PLANT_COST);
		}

		if (sunlight.contains("filtered-shade") || sunlight.contains("partial-shade")
				|| sunlight.contains("part-sun")) {
			p.addSunlight(Sunlight.PARTSUN);
		}
		if (sunlight.contains("full-sun")) {
			p.addSunlight(Sunlight.FULLSUN);
		}
		if (sunlight.startsWith("shade") || sunlight.contains(" shade")) {
			p.addSunlight(Sunlight.SHADE);
		}

		if (moisture.contains("average") || moisture.contains("medium")) {
			p.addMoisture(Moisture.AVERAGE);
		}
		if (moisture.contains("moist") || moisture.contains("wet")) {
			p.addMoisture(Moisture.WET);
		}
		if (moisture.contains("dry")) {
			p.addMoisture(Moisture.DRY);
		}

		p.setDescription(description);

		allPlants.add(p);
		return p;
	}

	/**
	 * Sets the collection of all leps to an empty one.
	 */
	void createLeps() { // Read in from files
		allLeps = new HashSet<>();
	}

	/**
	 * Creates a lep with the given values.
	 * 
	 * @param genus      The genus of the lep.
	 * @param species    The species of the lep.
	 * @param commonName The common name of the lep.
	 * @return The created lep.
	 */
	Lep createLep(String genus, String species, String commonName) {
		Lep l = new Lep();
		l.setGenus(genus);
		l.setSpecies(species);
		l.setName(commonName);
		allLeps.add(l);
		return l;
	}

	/**
	 * Creates a new empty garden.
	 */
	void createGarden() {
		this.garden = new Garden();

	}

	/**
	 * Loads and returns a garden in from a file.
	 * <p>
	 * If successful, sets the current garden to the one that was loaded in.
	 * 
	 * @param gardenName The file of the garden to load.
	 */
	void loadGarden(File gardenName) { // Read in from files
		try {
			System.out.println("loading");
			FileInputStream fis = new FileInputStream(gardenName);

			ObjectInputStream ois = new ObjectInputStream(fis);

			this.garden = (Garden) ois.readObject();
			ois.close();

		} catch (Exception e) {
			System.out.println("failed to load file");
			e.printStackTrace();
		}
	}

	/**
	 * Deletes the garden from files.
	 * 
	 * @param gardenFile The file for the garden that should be deleted.
	 * @return True if the file was successfully deleted, false otherwise.
	 */
	boolean deleteGarden(File gardenFile) {
		if (gardenFile.delete()) {
			System.out.println("Deleted the file: " + gardenFile.getName());
			return true;
		} else {
			System.out.println("Failed to delete the file.");
			return false;
		}
	}

	/**
	 * Loads and returns a garden from a file.
	 * 
	 * @param gardenName The file of the garden to load.
	 * @return g The garden that is loaded from a file.
	 */
	Garden loadGardenFromFile(File gardenName) {

		Garden g = null;

		try {
			System.out.println("loading");
			FileInputStream fis = new FileInputStream(gardenName);

			ObjectInputStream ois = new ObjectInputStream(fis);

			g = (Garden) ois.readObject();
			ois.close();
			return g;

		} catch (Exception e) {
			System.out.println("failed to load file this file");
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Returns the plant with the given scientific name.
	 * <p>
	 * Returns null if the plant is not found.
	 * 
	 * @param genus   The genus of the plant.
	 * @param species The species of the plant.
	 * @return The found plant, or null.
	 */
	Plant getPlant(String genus, String species) {
		for (Plant p : allPlants) {
			if (p.getGenus().equals(genus) && p.getSpecies().equals(species)) {
				return p;
			}
		}
		return null;
	}

	/**
	 * Returns the plant with the given scientific name separated by ' - '.
	 * 
	 * @param genus_species The combined scientific name.
	 * @return The found plant, or null.
	 */
	Plant getPlant(String genus_species) {
		String[] s = genus_species.split(" - ");

		return getPlant(s[0], s[1]);
	}

	/**
	 * Finds and returns the lep with the given scientific name.
	 * 
	 * @param genus   The genus of the lep.
	 * @param species The species of the lep.
	 * @return The found lep, or null.
	 */
	Lep getLep(String genus, String species) {
		for (Lep l : allLeps) {
			if (l.getGenus().equals(genus) && l.getSpecies().equals(species)) {
				return l;
			}
		}
		return null;
	}

	/**
	 * Adds the given plant to the garden.
	 * 
	 * @param p The plant to add to the garden.
	 */
	void addPlant(Plant p) {
		garden.addPlant(p);
	}

	/**
	 * Saves the current garden to a file.
	 * <p>
	 * saves to gardens/[garden name].gar
	 */
	public void saveGarden() {

		try {
			System.out.println("saving");
			FileOutputStream fos = new FileOutputStream("gardens/" + garden.getName() + ".gar");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(garden);
			oos.close();
			garden = null;
		} catch (Exception e) {
			System.out.println("failed to save");
			e.printStackTrace();
		}

	}

	/**
	 * Checks which plants match the moisture, soil, and sunlight conditions of the
	 * garden.
	 * 
	 * @return true if the conditions all match; false otherwise
	 */
	public Collection<Plant> getPlantsMatchingConditions() {
		Collection<Plant> matchingPlants = new HashSet<>();
		HashSet<Moisture> moisture = (HashSet<Moisture>) garden.getMoisture();
		HashSet<Sunlight> sunlight = (HashSet<Sunlight>) garden.getSunlight();
		HashSet<SoilType> soil = (HashSet<SoilType>) garden.getSoiltype();

		for (Plant p : allPlants) {
			Collection<Moisture> overlapM = new HashSet<Moisture>(moisture);
			overlapM.retainAll(p.getMoisture());
			if (overlapM.size() > 0) {
				Collection<Sunlight> overlapSun = new HashSet<Sunlight>(sunlight);
				overlapSun.retainAll(p.getSunlight());
				if (overlapSun.size() > 0) {
					Collection<SoilType> overlapSoil = new HashSet<SoilType>(soil);
					overlapSoil.retainAll(p.getSoilType());
					if (overlapSoil.size() > 0) {
						matchingPlants.add(p);
					}
				}
			}
		}

		return matchingPlants;
	}
}