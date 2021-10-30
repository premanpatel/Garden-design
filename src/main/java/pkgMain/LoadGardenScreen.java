package pkgMain;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.transform.Scale;

/**
 * The LoadGardenScreen allows the user to load a previously saved garden.
 * 
 * @author rbila
 *
 */
public class LoadGardenScreen extends Screen {

	Circle circle = new Circle(100);
	FlowPane flowPane;
	HashMap<File, VBox> vBoxHashMap;
	private final static int FLOW_PANE_HGAP = 100;
	private final static int FLOW_PANE_VGAP = 100;
	private final static int FLOW_PANE_PADDING_TOP = 25;
	private final static int FLOW_PANE_PADDING_RIGHT = 0;
	private final static int FLOW_PANE_PADDING_BOTTOM = 0;
	private final static int FLOW_PANE_PADDING_LEFT = 0;

	private final static int FLOW_PANE_MARGIN_TOP = 0;
	private final static int FLOW_PANE_MARGIN_RIGHT = 35;
	private final static int FLOW_PANE_MARGIN_BOTTOM = 35;
	private final static int FLOW_PANE_MARGIN_LEFT = 35;

	private final static int GARDEN_NAME_LABEL_HEIGHT = 50;
	private final static int GARDEN_NAME_LABEL_WIDTH = 50;
	private final static int GARDEN_NAME_LABEL_FONT_SIZE = 24;

	private final static int LOAD_GARDEN_LABEL_MIN_HEIGHT = 50;
	private final static int LOAD_GARDEN_LABEL_MIN_WIDTH = 50;
	private final static int LOAD_GARDEN_LABEL_MIN_FONT_SIZE = 50;

	/**
	 * Creates a scene with the given height and width.
	 * 
	 * @param canvasW The width of the scene.
	 * @param canvasH The height of the scene.
	 * @return The created scene.
	 */
	Scene createScreen(double canvasW, double canvasH) {

		vBoxHashMap = new HashMap<File, VBox>();

		ArrayList<File> gardenFiles = new ArrayList<>();
		gardenFiles = (ArrayList<File>) view.getController().getListOfGardens();

		flowPane = new FlowPane();
		flowPane.setHgap(FLOW_PANE_HGAP);
		flowPane.setVgap(FLOW_PANE_VGAP);
		flowPane.setPadding(new Insets(FLOW_PANE_PADDING_TOP, FLOW_PANE_PADDING_RIGHT, FLOW_PANE_PADDING_BOTTOM,
				FLOW_PANE_PADDING_LEFT));
		flowPane.setBackground(
				new Background(new BackgroundFill(Color.web("#9DC88D"), CornerRadii.EMPTY, Insets.EMPTY)));

		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setFitToHeight(true);
		scrollPane.setFitToWidth(true);
		scrollPane.setContent(flowPane);

		addGardensToFlowPane(gardenFiles);

		root = new BorderPane();
		((BorderPane) root).setCenter(scrollPane);
		((BorderPane) root).setBackground(
				new Background(new BackgroundFill(Color.web("#9DC88D"), CornerRadii.EMPTY, Insets.EMPTY)));

		createButtons();
		createLabels();
		scene = new Scene(root, canvasW, canvasH);
		return scene;
	}

	/**
	 * Adds the saved gardens to the FlowPane so that the user can load them.
	 * 
	 * @param gardenFiles List of all garden serialzable files to add to FlowPane
	 */
	void addGardensToFlowPane(Collection<File> gardenFiles) {

		Garden g = new Garden();
		Label gardenName;
		Button load;
		Button delete;
		Polygon gardenShape;

		for (File f : gardenFiles) {
			try {
				g = view.getController().loadGardenFromFile(f);

				VBox vbox = new VBox(10);
				vbox.setAlignment(Pos.CENTER);

				gardenName = new Label(g.getName());
				gardenName.setMinHeight(GARDEN_NAME_LABEL_HEIGHT);
				gardenName.setMinWidth(GARDEN_NAME_LABEL_WIDTH);
				gardenName.setFont(new Font("Arial", GARDEN_NAME_LABEL_FONT_SIZE));
				gardenName.setStyle("-fx-font-weight: bold");

				load = new Button("Load Garden");
				load.setOnAction(event -> view.getController().loadGarden(f));
				gardenShape = view.getGardenScaledPolygon(g);

				delete = new Button("Delete");
				delete.setOnAction(event -> deleteGarden(f));
				Group gr = new Group(gardenShape);

				vbox.getChildren().addAll(gr, gardenName, load, delete);
				vBoxHashMap.put(f, vbox);

				flowPane.getChildren().add(vbox);
				FlowPane.setMargin(vbox, new Insets(FLOW_PANE_MARGIN_TOP, FLOW_PANE_MARGIN_RIGHT,
						FLOW_PANE_MARGIN_BOTTOM, FLOW_PANE_MARGIN_LEFT));
			} catch (Exception e) {
				System.out.println("failed to load file");
				e.printStackTrace();
			}
		}
	}

	/**
	 * Creates the buttons and places them on the screen.
	 */
	void createButtons() {

		Button loadGarden = new Button("Open Garden From Files");
		loadGarden.setOnAction(view.getController().loadGardenHandler);

		buttonList = new ArrayList<Button>();
		buttonList.add(new Button("Home Screen"));
		buttonList.add(new Button("Main Screen"));

		Button startScreen = buttonList.get(0);

		startScreen.setOnAction(event -> view.getController().changeScreen(event, view.getStart()));
		Button mainScreen = buttonList.get(1);
		mainScreen.setOnAction(event -> view.getController().changeScreen(event, view.getMain()));

		HBox hbox = new HBox();

		hbox.getChildren().addAll(startScreen, loadGarden);

		((BorderPane) root).setBottom(hbox);
		hbox.setAlignment(Pos.CENTER);
		BorderPane.setAlignment(loadGarden, Pos.BOTTOM_CENTER);

	}

	/**
	 * Creates the labels and places them on the screen.
	 * <p>
	 * Doesn't have any labels to show.
	 */
	void createLabels() {
		Label loadGardenLabel = new Label("Load Your Garden");
		loadGardenLabel.setMinHeight(LOAD_GARDEN_LABEL_MIN_HEIGHT);
		loadGardenLabel.setMinWidth(LOAD_GARDEN_LABEL_MIN_WIDTH);
		loadGardenLabel.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, LOAD_GARDEN_LABEL_MIN_FONT_SIZE));
		loadGardenLabel.setTextFill(Color.WHITE);

		HBox hbox = new HBox();
		hbox.getChildren().add(loadGardenLabel);
		hbox.setBackground(new Background(new BackgroundFill(Color.web("#4D774E"), CornerRadii.EMPTY, Insets.EMPTY)));
		hbox.setAlignment(Pos.CENTER);
		hbox.setPadding(new Insets(50, 0, 50, 0));

		BorderPane.setAlignment(hbox, Pos.TOP_CENTER);
		((BorderPane) root).setTop(hbox);
	}

	/**
	 * Deletes the garden and removes the gardens VBox from the FlowPane.
	 */
	void deleteGarden(File gardenFile) {
		VBox deleteVBox = vBoxHashMap.get(gardenFile);
		vBoxHashMap.remove(gardenFile);
		flowPane.getChildren().remove(deleteVBox);
		view.getController().deleteGarden(gardenFile);
	}

	/**
	 * Creates a new screen with the given view.
	 * 
	 * @param view The main view.
	 */
	public LoadGardenScreen(View view) {
		super(view);
	}
}
