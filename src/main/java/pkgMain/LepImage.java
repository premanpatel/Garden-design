package pkgMain;

import javafx.scene.image.Image;

/**
 * The LepImage class represents a lep with its image, genus, and species
 * 
 * @author alexsyou
 */
public class LepImage {
	private Image image;
	private String genus;
	private String species;

	static final int IMG_HEIGHT = 1024;
	static final int IMG_WIDTH = 1024;

	/**
	 * The constructor creates a LepImage with given params
	 * 
	 * @param image   The Image that shows the lep
	 * @param genus   The genus of the lep
	 * @param species The species of the lep
	 */
	public LepImage(Image image, String genus, String species) {
		this.image = image;
		this.genus = genus;
		this.species = species;
	}

	/**
	 * Returns the Image of the lep
	 * 
	 * @return Image displaying the lep
	 */
	public Image getImage() {
		return image;
	}

	/**
	 * Sets the lep to a specific image
	 * 
	 * @param image Image to set the lep to
	 */
	public void setImage(Image image) {
		this.image = image;
	}

	/**
	 * Returns the genus of the lep
	 * 
	 * @return String genus of the lep
	 */
	public String getGenus() {
		return genus;
	}

	/**
	 * Sets the lep's genus
	 * 
	 * @param genus String genus to set the lep to
	 */
	public void setGenus(String genus) {
		this.genus = genus;
	}

	/**
	 * Returns the species of the lep
	 * 
	 * @return String species of the lep
	 */
	public String getSpecies() {
		return species;
	}

	/**
	 * Sets the lep's species
	 * 
	 * @param species String species to set the lep to
	 */
	public void setSpecies(String species) {
		this.species = species;
	}

	/**
	 * Returns the height of the lep's image
	 * 
	 * @return int imgHeight
	 */
	public static int getImgheight() {
		return IMG_HEIGHT;
	}

	/**
	 * Returns the width of the lep's image
	 * 
	 * @return int imgWidth
	 */
	public static int getImgwidth() {
		return IMG_WIDTH;
	}

	/**
	 * Compares this LepImage to a genus and species to see if this has the same
	 * name as another lep
	 * 
	 * @param genus   The genus of the lep to compare to
	 * @param species The species of the lep to compare to
	 * @return boolean representing if the two leps have the same name
	 */
	public boolean equalName(String genus, String species) {
		return this.genus.equals(genus) && this.species.equals(species);
	}
}
