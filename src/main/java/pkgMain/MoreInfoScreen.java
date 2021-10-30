package pkgMain;

import javafx.scene.Scene;

/**
 * The MoreInfoScreen class displays more info about a plant or lep.
 * 
 * @author rbila
 *
 */
abstract class MoreInfoScreen extends Screen {

	String species;
	String genus;

	/**
	 * Creates a new screen with the given view.
	 * 
	 * @param view The main view.
	 */
	public MoreInfoScreen(View view) {
		super(view);
	}

	/**
	 * Creates a scene with the given height and width.
	 * <p>
	 * Only implemented in subclasses.
	 * 
	 * @param canvasW The width of the scene.
	 * @param canvasH The height of the scene.
	 * @return The created scene.
	 */
	abstract Scene createScreen(double canvasW, double canvasH);

	/**
	 * Creates the button to show on the screen.
	 */
	abstract void createButtons();

	/**
	 * Creates the labels to show on the screen.
	 */
	abstract void createLabels();

	/**
	 * Returns the genus of the plant/lep being displayed.
	 * 
	 * @return The genus.
	 */
	public String getGenus() {
		return genus;
	}

	/**
	 * Sets the genus of the plant/lep being displayed.
	 * 
	 * @param genus The genus.
	 */
	public void setGenus(String genus) {
		this.genus = genus;
	}

	/**
	 * Returns the species of the plant/lep being displayed.
	 * 
	 * @return The species.
	 */
	public String getSpecies() {
		return species;
	}

	/**
	 * Sets the species of the plant/lep being displayed.
	 * 
	 * @param species The species.
	 */
	public void setSpecies(String species) {
		this.species = species;
	}

}