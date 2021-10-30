package pkgMain;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

/**
 * The LepListScreen class is a screen that lists the leps in the garden.
 * 
 * @author rbila
 *
 */
public class LepListScreen extends ListScreen {

	private static final int IMAGE_VIEW_FIGH_HEIGHT = 100;

	/**
	 * Creates the scene with the given height and width.
	 * 
	 * @param canvasW The width of the scene.
	 * @param canvasH The height of the scene.
	 * @return The created scene.
	 */
	public Scene createScreen(double canvasW, double canvasH) {
		ArrayList<BorderPane> paneList = new ArrayList<>();
		for (Lep l : view.controller.getUniqueLeps()) {
			LepImage image = view.getLepImage(l.getGenus(), l.getSpecies());
			ImageView iv = new ImageView();
			iv.setImage(image.getImage());
			iv.setPreserveRatio(true);
			iv.setFitHeight(IMAGE_VIEW_FIGH_HEIGHT);
			Label label = new Label(l.getName() + "; " + l.getGenus() + " " + l.getSpecies());
			label.setGraphic(iv);

			BorderPane bp = new BorderPane();
			bp.setLeft(label);

			paneList.add(bp);
		}

		oList = FXCollections.observableArrayList(paneList);

		root = new BorderPane();
		list = new ListView<>(oList);
		list.setPrefHeight(canvasH);
		list.setPrefWidth(canvasW);
		((BorderPane) root).setCenter(list);
		createButtons();
		scene = new Scene(root, canvasW, canvasH);
		return scene;
	}

	/**
	 * Creates the buttons for the scene and adds them to root.
	 */
	public void createButtons() {
		buttonList = new ArrayList<Button>();
		buttonList.add(new Button("Main Screen"));
		var fp = new FlowPane();
		fp.getChildren().addAll(buttonList);
		((BorderPane) root).setTop(fp);
		Button mainScreen = buttonList.get(0);
		mainScreen.setOnAction(event -> view.getController().changeScreen(event, view.getMain()));
	}

	/**
	 * Creates labels for the scene and adds them to root.
	 * <p>
	 * Currently doesn't create any labels.
	 */
	public void createLabels() {

	}

	/**
	 * Creates a new screen with the given view.
	 * 
	 * @param view The main view.
	 */
	public LepListScreen(View view) {
		super(view);
	}
}