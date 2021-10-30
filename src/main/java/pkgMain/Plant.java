package pkgMain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

/**
 * The plant class represents a plant that can support different leps and grow
 * in different conditions.
 * 
 * @author rbila
 *
 */
public class Plant implements Serializable {

	private String name;
	private String genus;
	private String species;
	private String description;
	private HashSet<SoilType> soilType;
	private HashSet<Moisture> moisture;
	private HashSet<Sunlight> sunlight;

	private String URL;

	private double cost;
	private double spread;

	private Collection<Lep> supportedLeps;
	private int lepCount;

	/**
	 * The default constructor for a plant that sets no values.
	 */
	Plant() {
		this.soilType = new HashSet<>();
		this.moisture = new HashSet<>();
		this.sunlight = new HashSet<>();
		this.supportedLeps = new HashSet<>();
	}

	/**
	 * Creates a plant with the given values.
	 * 
	 * @param name        The common name of the plant.
	 * @param description The description of the plant.
	 * @param genus       The genus of the plant.
	 * @param species     The species of the plant.
	 */
	Plant(String name, String description, String genus, String species) {
		this.name = name;
		this.description = description;
		this.genus = genus;
		this.species = species;
		this.soilType = new HashSet<>();
		this.moisture = new HashSet<>();
		this.sunlight = new HashSet<>();
		this.supportedLeps = new HashSet<>();
	}

	/**
	 * Returns the common name of the plant.
	 * 
	 * @return name The common name of the plant.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the plant to the given one.
	 * 
	 * @param name The common name of the plant.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the genus of the plant.
	 * 
	 * @return genus The genus of the plant.
	 */
	public String getGenus() {
		return genus;
	}

	/**
	 * Sets the genus of the plant.
	 * 
	 * @param genus The genus of the plant.
	 */
	public void setGenus(String genus) {
		this.genus = genus;
	}

	/**
	 * Returns the description of the plant.
	 * 
	 * @return The description of the plant.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description of the plant to the given one.
	 * 
	 * @param description The description of the plant.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Returns the collection of leps that are supported by the plant.
	 * 
	 * @return supportedLeps The leps supported by the plant.
	 */
	public Collection<Lep> getSupportedLeps() {
		return this.supportedLeps;
	}

	/**
	 * Returns the number of leps supported by the plant.
	 * 
	 * @return The number of leps supported by the plant.
	 */
	public int getLepCount() {
		return this.lepCount;
	}

	/**
	 * Sets the collection of leps supported by the plant to the given one.
	 * 
	 * @param supportedLeps The leps supported by the plant.
	 */
	public void setSupportedLeps(Collection<Lep> supportedLeps) {
		this.supportedLeps = supportedLeps;
	}

	/**
	 * Adds the given plant to the collection of those supported by the plant.
	 * 
	 * @param l The lep to be supported by the plant.
	 * @return Whether or not the lep was successfully added.
	 */
	public boolean addSupportedLep(Lep l) {
		return this.supportedLeps.add(l);
	}

	/**
	 * Sets the number of leps supported by the plant to the given value.
	 * 
	 * @param lepCount The number of leps supported by the plant.
	 */
	public void setLepCount(int lepCount) {
		this.lepCount = lepCount;
	}

	/**
	 * Returns a string representation of the plant.
	 * 
	 * @return The string representation of the plant.
	 */
	@Override
	public String toString() {
		return "Plant [name=" + name + ", genus=" + genus + ", description=" + description + "]";
	}

	/**
	 * Returns the species of the plant.
	 * 
	 * @return species The species of the plant.
	 */
	public String getSpecies() {
		return species;
	}

	/**
	 * Sets the species of the plant to the given value.
	 * 
	 * @param species The species of the plant.
	 */
	public void setSpecies(String species) {
		this.species = species;
	}

	/**
	 * Returns the cost of the plant.
	 * 
	 * @return The cost of the plant.
	 */
	public double getCost() {
		return cost;
	}

	/**
	 * Sets the cost of the plant to the given value.
	 * 
	 * @param cost The cost of the plant.
	 */
	public void setCost(double cost) {
		this.cost = cost;
	}

	/**
	 * Returns the spread of the plant, in ft.
	 * 
	 * @return The spread of the plant.
	 */
	public double getSpread() {
		return spread;
	}

	/**
	 * Returns the link to the Mt Cuba site for the plant.
	 * 
	 * @return The URL.
	 */
	public String getURL() {
		return URL;
	}

	/**
	 * Sets the link to the Mt Cuba site for the plant to the given one.
	 * 
	 * @param uRL The URL.
	 */
	public void setURL(String uRL) {
		URL = uRL;
	}

	/**
	 * Sets the spread of the plant, in ft, to the given value.
	 * 
	 * @param spread The spread of the plant.
	 */
	public void setSpread(double spread) {
		this.spread = spread;
	}

	/**
	 * Returns the collection of soil types that the plant can grow in.
	 * 
	 * @return The soil types that the plant can grow in.
	 */
	public HashSet<SoilType> getSoilType() {
		return soilType;
	}

	/**
	 * Sets the collection of soil types that the plant can grow in to the given
	 * one.
	 * 
	 * @param soilType The soil types that the plant can grow in.
	 */
	public void setSoilType(HashSet<SoilType> soilType) {
		this.soilType = soilType;
	}

	/**
	 * Adds the given soil type to the collection that the plant can grow in.
	 * 
	 * @param s The soil type that the plant can grow in.
	 */
	public void addSoilType(SoilType s) {
		soilType.add(s);
	}

	/**
	 * Returns the collection of moisture levels that the plant can grow in.
	 * 
	 * @return The moisture levels that the plant can grow in.
	 */
	public HashSet<Moisture> getMoisture() {
		return moisture;
	}

	/**
	 * Sets the collection of moisture levels that the plant can grow in to the
	 * given one.
	 * 
	 * @param moisture The moisture levels that the plant can grow in.
	 */
	public void setMoisture(HashSet<Moisture> moisture) {
		this.moisture = moisture;
	}

	/**
	 * Adds the given moisture level to the collection that the plant can grow in.
	 * 
	 * @param m The moisture level that the plant can grow in.
	 */
	public void addMoisture(Moisture m) {
		moisture.add(m);
	}

	/**
	 * Returns the collection of sunlight levels that the plant can grow in.
	 * 
	 * @return The sunlight levels that the plant can grow in.
	 */
	public HashSet<Sunlight> getSunlight() {
		return sunlight;
	}

	/**
	 * Sets the collection of sunlight levels that the plant can grow in to the
	 * given one.
	 * 
	 * @param sunlight The sunlight levels that the plant can grow in.
	 */
	public void setSunlight(HashSet<Sunlight> sunlight) {
		this.sunlight = sunlight;
	}

	/**
	 * Adds the given sunlight level to the collection that the plant can grow in.
	 * 
	 * @param s The sunlight level that the plant can grow in.
	 */
	public void addSunlight(Sunlight s) {
		sunlight.add(s);
	}

}