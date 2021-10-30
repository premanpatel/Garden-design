package pkgMain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * The Lep class represents a lep, lepidoptera, that can be supported by a
 * plant.
 * 
 * @author rbila
 *
 */
public class Lep implements Serializable {

	private String name;
	private String genus;
	private String description;
	private String species;
	private HashSet<Plant> supportingPlants;

	/**
	 * The default constructor for a lep that sets no values.
	 */
	Lep() {
		this.supportingPlants = new HashSet<>();
	}

	/**
	 * Creates a lep with with given values.
	 * 
	 * @param name        The common name of the lep.
	 * @param description The description of the lep.
	 * @param genus       The genus of the lep.
	 * @param species     The species of the lep.
	 */
	Lep(String name, String description, String genus, String species) {
		this.name = name;
		this.description = description;
		this.species = species;
		this.genus = genus;
		this.supportingPlants = new HashSet<>();
	}

	/**
	 * Returns the common name of the lep.
	 * 
	 * @return The common name of the lep.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the common name of the lep to the given value.
	 * 
	 * @param name The common name of the lep.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the genus of the lep.
	 * 
	 * @return The genus of the lep.
	 */
	public String getGenus() {
		return genus;
	}

	/**
	 * Sets the genus of the lep to the given value.
	 * 
	 * @param genus The genus of the lep.
	 */
	public void setGenus(String genus) {
		this.genus = genus;
	}

	/**
	 * Returns the description of the lep.
	 * 
	 * @return The description of the lep.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description of the lep to the given value.
	 * 
	 * @param description The description of the lep.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Returns the species of the lep.
	 * 
	 * @return The species of the lep.
	 */
	public String getSpecies() {
		return species;
	}

	/**
	 * Sets the species of the lep to the given value.
	 * 
	 * @param species The species of the lep.
	 */
	public void setSpecies(String species) {
		this.species = species;
	}

	/**
	 * Returns the collection of plants that support the lep.
	 * 
	 * @return The plants that support the lep.
	 */
	public HashSet<Plant> getSupportingPlants() {
		return supportingPlants;
	}

	/**
	 * Sets the collection of plants that support the lep to the given one.
	 * 
	 * @param supportingPlants The plants that support the lep.
	 */
	public void setSupportingPlants(HashSet<Plant> supportingPlants) {
		this.supportingPlants = supportingPlants;
	}

	/**
	 * Adds the given plant to the collection of plants that support the lep.
	 * 
	 * @param p The plant that supports the lep.
	 */
	public void addSupportingPlant(Plant p) {
		supportingPlants.add(p);
	}

	/**
	 * Returns a string representation of the lep.
	 */
	@Override
	public String toString() {
		return "Lep [name=" + name + ", genus=" + genus + ", description=" + description + "]";
	}

}