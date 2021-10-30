package pkgMain;

import java.util.ArrayList;
import java.util.Collection;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * The abstract class Screen defines values that all screens need to have and
 * methods
 * 
 * @author alexsyou
 */
public abstract class Screen {

	ArrayList<Button> buttonList;
	Collection<Label> labelList;
	Collection<Image> imageList;
	View view;
	double canvasWidth;
	double canvasHeight;
	protected ImageView iv1;
	protected Scene scene;
	protected Pane root;

	/**
	 * The createScreen method will create a screen
	 * 
	 * @param canvasW The width of the canvas
	 * @param canvasH The height of the canvas
	 * @return The scene of the screen created
	 */
	abstract Scene createScreen(double canvasW, double canvasH);

	/**
	 * The createButtons method will create buttons on the screen
	 */
	abstract void createButtons();

	/**
	 * The createLabels method will create labels on the screen
	 */
	abstract void createLabels();

	/**
	 * Returns the scene of the screen
	 * 
	 * @return scene of the screen
	 */
	public Scene getScene() {
		return scene;
	}

	/**
	 * The screen constructor sets the view, as well as initializes the height and
	 * width of the screen to the height and width from the view
	 * 
	 * @param view The view
	 */
	public Screen(View view) {
		this.view = view;
		canvasWidth = View.getCanvaswidth();
		canvasHeight = View.getCanvasheight();
	}
}
