package pkgMain;

import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ListView;

/**
 * The ListScreen class is a screen that lists the something for the user.
 * 
 * @author rbila
 *
 */
abstract class ListScreen extends Screen {

	protected ListView<?> list;
	protected ObservableList<?> oList;

	/**
	 * Creates a scene with the given height and width.
	 * 
	 * @param canvasW The width of the scene.
	 * @param canvasH The height of the scene.
	 * @return The created scene.
	 */
	abstract Scene createScreen(double canvasW, double canvasH);

	/**
	 * Creates buttons and adds them to the root.
	 */
	abstract void createButtons();

	/**
	 * Creates labels and adds them to the root.
	 */
	abstract void createLabels();

	/**
	 * Creates a new screen with the given view.
	 * 
	 * @param view The main view.
	 */
	public ListScreen(View view) {
		super(view);
	}
}