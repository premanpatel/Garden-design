package pkgMain;

import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

/**
 * The screen that is responsible for displaying the informations for a plant.
 * 
 * @author JD
 *
 */
public class PlantMoreInfoScreen extends MoreInfoScreen {

	private static final int SPLIT_PANE_DIVIDER_INDEX = 0;
	private static final double SPLIT_PANE_DIVIDER_POSITION = 0.8;

	/**
	 * Creates the PlantMoreInfoScreen.
	 * 
	 * @param canvasW width of the canvas.
	 * @param canvasH height of the canvas.
	 * 
	 * @return scene The scene for the stage.
	 */
	Scene createScreen(double canvasW, double canvasH) {
		root = new BorderPane();
		root.getStyleClass().add("background");
		var sp = new SplitPane();
		Plant p = view.getController().getPlant(genus, species);
		Label l = new Label("Scientific Name: "+p.getGenus() + " "+p.getSpecies()+", also known as "+p.getName()+":\n\n"+p.getDescription());
//		l.setGraphic(new ImageView(view.getPlantImage(genus, species).getImage()));
		l.setWrapText(true);
		var imageView = new ImageView(view.getPlantImage(genus, species).getImage());
		imageView.setPreserveRatio(true);
		imageView.setFitWidth(root.getWidth());
		var img = new Pane();
		img.setBackground(new Background(new BackgroundImage(imageView.getImage(), BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, new BackgroundPosition(Side.LEFT, 0.5, true, Side.TOP, 0.5, true),
				new BackgroundSize(0.5, 0.5, true, true, true, false))));
		sp.getItems().addAll(img, l);
		((BorderPane) root).setCenter(sp);

		createButtons();
		sp.setDividerPosition(SPLIT_PANE_DIVIDER_INDEX, SPLIT_PANE_DIVIDER_POSITION);

		scene = new Scene(root, canvasW, canvasH);
		return scene;
	}

	/**
	 * Creates buttons for screen.
	 */
	void createButtons() {
		Button backbtn = new Button("Back");
		backbtn.setOnAction(event -> view.getController().changeScreen(event, view.getPlantList()));
		((BorderPane) root).setTop(backbtn);
	}

	/**
	 * Creates labels for screen.
	 */
	void createLabels() {
	}

	/**
	 * Constructor for PlantMoreInfoScreen.
	 */
	public PlantMoreInfoScreen(View view) {
		super(view);
	}
}