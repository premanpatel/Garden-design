package pkgMain;

import javafx.scene.image.Image;

/**
 * The PlantImage class represents a plant with its image, genus, and species
 * 
 * @author alexsyou
 *
 */
public class PlantImage {
	private Image image;
	private String genus;
	private String species;

	static final int IMG_HEIGHT = 1024;
	static final int IMG_WIDTH = 1024;

	/**
	 * Constructor for a PlantImage
	 * 
	 * @param image   The Image of the plant to be displayed
	 * @param genus   The genus of the plant
	 * @param species The species of the plant
	 */
	public PlantImage(Image image, String genus, String species) {
		this.image = image;
		this.genus = genus;
		this.species = species;
	}

	/**
	 * Returns the Image of the plant
	 * 
	 * @return Image of the plant
	 */
	public Image getImage() {
		return image;
	}

	/**
	 * Sets the Image of the plant
	 * 
	 * @param image Image to set the plant to
	 */
	public void setImage(Image image) {
		this.image = image;
	}

	/**
	 * Returns the genus of the plant
	 * 
	 * @return genus of the plant
	 */
	public String getGenus() {
		return genus;
	}

	/**
	 * Sets the genus of the plant
	 * 
	 * @param genus String to set the plant genus to
	 */
	public void setGenus(String genus) {
		this.genus = genus;
	}

	/**
	 * Returns the species of the plant
	 * 
	 * @return species of the plant
	 */
	public String getSpecies() {
		return species;
	}

	/**
	 * Sets the species of the plant
	 * 
	 * @param species String to set the plant species to
	 */
	public void setSpecies(String species) {
		this.species = species;
	}

	/**
	 * Returns the image height of the plant
	 * 
	 * @return imgHeight of plant
	 */
	public static int getImgheight() {
		return IMG_HEIGHT;
	}

	/**
	 * Returns the image width of the plant
	 * 
	 * @return imgWidth of plant
	 */
	public static int getImgwidth() {
		return IMG_WIDTH;
	}

	/**
	 * Compares this plant to another plant, and checks if they have the same name
	 * 
	 * @param genus   The genus of the other plant
	 * @param species The species of the other plant
	 * 
	 * @return boolean of whether the plants have the same name
	 */
	public boolean equalName(String genus, String species) {
		return this.genus.equals(genus) && this.species.equals(species);
	}
}
