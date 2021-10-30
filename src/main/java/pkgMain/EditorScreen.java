package pkgMain;

/**
 * The EditorScreen class is a screen where the user can edit parts of the
 * garden.
 * 
 * @author rbila
 *
 */
public abstract class EditorScreen extends Screen {

	protected final double LABEL_WIDTH = 50;
	protected final double CHECKBOX_HEIGHT = 50;

	void editGarden() {
	}

	/**
	 * Creates a new screen with the given view.
	 * 
	 * @param view The main view.
	 */
	public EditorScreen(View view) {
		super(view);
	}
}
