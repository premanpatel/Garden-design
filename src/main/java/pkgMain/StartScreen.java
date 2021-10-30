package pkgMain;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.StackPane;

public class StartScreen extends Screen {

	private static final int LOGO_FIR_HEIGHT = 450;
	private static final int B0_INSET_TOP = 0;
	private static final int B0_INSET_RIGHT = 0;
	private static final int B0_INSET_BOTTOM = 50;
	private static final int B0_INSET_LEFT = 0;

	private static final int B1_INSET_TOP = 50;
	private static final int B1_INSET_RIGHT = 0;
	private static final int B1_INSET_BOTTOM = 0;
	private static final int B1_INSET_LEFT = 0;
	private static final int B2_INSET_TOP = 150;
	private static final int B2_INSET_RIGHT = 0;
	private static final int B2_INSET_BOTTOM = 0;
	private static final int B2_INSET_LEFT = 0;

	/**
	 * Creates the StartScreen.
	 * 
	 * @param canvasW width of the canvas.
	 * @param canvasH height of the canvas.
	 * 
	 * @return scene The scene for the stage.
	 */
	Scene createScreen(double canvasW, double canvasH) {
		root = new StackPane();
		root.setBackground(new Background(new BackgroundImage(new Image("img/BackgroundLandscape.jpg"),
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
				new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, true, true))));
		ImageView logo = new ImageView();
		logo.setImage(new Image("img/NativeGardenLogo.png"));
		logo.setPreserveRatio(true);
		logo.setFitHeight(LOGO_FIR_HEIGHT);
		StackPane.setAlignment(logo, Pos.TOP_CENTER);

		root.getChildren().add(logo);
		createButtons();

		scene = new Scene(root, canvasW, canvasH);
		return scene;
	}

	/**
	 * Creates the buttons for the StartScreen.
	 */
	void createButtons() {
		buttonList = new ArrayList<Button>();
		buttonList.add(new Button("Create a New Garden"));
		buttonList.add(new Button("Load a Previous Garden"));
		buttonList.add(new Button("General Info"));

		Button b0 = buttonList.get(0);
		Button b1 = buttonList.get(1);
		Button b2 = buttonList.get(2);

		StackPane.setMargin(b0, new Insets(B0_INSET_TOP, B0_INSET_RIGHT, B0_INSET_BOTTOM, B0_INSET_LEFT));
		StackPane.setMargin(b1, new Insets(B1_INSET_TOP, B1_INSET_RIGHT, B1_INSET_BOTTOM, B1_INSET_LEFT));
		StackPane.setMargin(b2, new Insets(B2_INSET_TOP, B2_INSET_RIGHT, B2_INSET_BOTTOM, B2_INSET_LEFT));

		root.getChildren().addAll(buttonList);

		b0.setOnAction(event -> view.getController().changeScreen(event, view.getCreateNew()));

		b1.setOnAction(event -> view.getController().changeScreen(event, view.getLoad()));

		b2.setOnAction(event -> view.getController().changeScreen(event, view.getGenInfo()));
	}

	/**
	 * Creates the labels for the StartScreen.
	 */
	void createLabels() {
	}

	/**
	 * Constructor for StartScreen.
	 */
	public StartScreen(View view) {
		super(view);
	}
}
