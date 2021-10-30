package pkgMain;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.StrokeLineCap;

/**
 * The CreateNewGardenScreen represent the screen where the user can edit and
 * create a garden. This screen allows the user to the the budget, conditions of
 * the garden, draw the shape of the garden.
 * 
 * @author JD
 *
 */
public class CreateNewGardenScreen extends EditorScreen {

	/**
	 * The garden polygon.
	 */
	Polygon gardenpoly;
	BorderPane bp;
	final static double CLICK_MARGIN = 20;
	private static final double GRID_WIDTH = 20;
	private static final double POLYGON_HUE_SHIFT = 0;
	private static final double POLYFON_SATURATION_FACTOR = 1.2;
	private static final double POLYGON_BRIGHTNESS_FACTOR = 1;
	private static final double POLYGON_OPACITY_FACTOR = 0.6;
	private static final double POLYGON_STROKE_WIDTH = 4;
	private static final Color ANCHOR_COLOR = Color.GOLD;
	private static final ObservableList<Double> ANCHOR_POINTS = FXCollections.observableArrayList(100d, 100d, 800d,
			100d, 800d, 800d, 100d, 800d);

	/**
	 * A ObservableList of Anchors. will contain all the anchors that represent the
	 * points (vertex) in gardenpoly.
	 */
	ObservableList<Anchor> anchors;

	/**
	 * Creates the CreateNewGardenScreen.
	 * 
	 * @param canvasW width of the canvas.
	 * @param canvasH height of the canvas.
	 * 
	 * @return scene The scene for the stage.
	 */
	Scene createScreen(double canvasW, double canvasH) {

		gardenpoly = createStartingPolygon();

		root = new BorderPane();
		root.getChildren().add(gardenpoly);
		canvasWidth = canvasW;
		canvasHeight = canvasH;
		// link to image: http://clipart-library.com/clipart/kcMKrBBXi.htm
		root.getStyleClass().add("polygon2");
		VBox vSun = new VBox();
		for (Sunlight s : Sunlight.values()) {
			CheckBox sunCheck = new CheckBox(s.getName());
			sunCheck.getStyleClass().add("label1");
			sunCheck.selectedProperty().addListener(new ChangeListener<Boolean>() {
				@Override
				public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
					view.getController().checkSetSun(s, newValue);
				}
			});
			vSun.getChildren().add(sunCheck);
		}

		VBox vSoil = new VBox();
		for (SoilType s : SoilType.values()) {
			CheckBox soilCheck = new CheckBox(s.getName());
			soilCheck.getStyleClass().add("label1");
			soilCheck.selectedProperty().addListener(new ChangeListener<Boolean>() {
				@Override
				public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
					view.getController().checkSetSoil(s, newValue);
				}
			});
			vSoil.getChildren().add(soilCheck);
		}

		VBox vMoisture = new VBox();
		for (Moisture m : Moisture.values()) {
			CheckBox moistureCheck = new CheckBox(m.getName());
			moistureCheck.getStyleClass().add("label1");
			moistureCheck.selectedProperty().addListener(new ChangeListener<Boolean>() {
				@Override
				public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
					view.getController().checkSetMoisture(m, newValue);
				}
			});
			vMoisture.getChildren().add(moistureCheck);
		}

		TextField textField_budget = new TextField("0");
		textField_budget.setTextFormatter(new TextFormatter<>(view.getController().filterInt));
		textField_budget.textProperty().addListener((observable, oldValue, newValue) -> view.getController()
				.textFieldSetBudget(observable, oldValue, newValue));

		TextField textField_name = new TextField("garden name");
		textField_name.textProperty().addListener((observable, oldValue, newValue) -> view.getController()
				.textFieldSetName(observable, oldValue, newValue));

		TextField textField_width = new TextField("0");
		textField_width.setTextFormatter(new TextFormatter<>(view.getController().filterDouble));
		textField_width.textProperty().addListener(
				(observable, oldValue, newValue) -> view.getController().scaleFromFT(Double.valueOf(newValue)));

		var lbl_budget = new Label("Budget: ");
		var lbl_soil = new Label("Soil Type: ");
		var lbl_sunshade = new Label("Sun/Shade: ");
		var lbl_moisture = new Label("Moisture: ");
		Label lbl_name = new Label("Garden Name:");
		Label lbl_width = new Label("Total width of garden (ft): ");
		lbl_budget.getStyleClass().add("label1");
		lbl_soil.getStyleClass().add("label1");
		lbl_moisture.getStyleClass().add("label1");
		lbl_sunshade.getStyleClass().add("label1");
		lbl_name.getStyleClass().add("label1");
		lbl_width.getStyleClass().add("label1");

		var fp = new FlowPane();
		fp.setAlignment(Pos.CENTER);
		fp.setHgap(LABEL_WIDTH);
		var fp2 = new FlowPane(Orientation.VERTICAL, 0, CHECKBOX_HEIGHT);
		fp2.setAlignment(Pos.CENTER);
		var btnAddAnchor = new Button("Add Corner");
		var btnDelAnchor = new Button("Delete Corner");

		btnAddAnchor.setOnAction(e -> {
			Anchor tmp;
			var x1 = gardenpoly.getPoints().get(0);
			var y1 = gardenpoly.getPoints().get(1);
			var x2 = gardenpoly.getPoints().get(gardenpoly.getPoints().size() - 2);
			var y2 = gardenpoly.getPoints().get(gardenpoly.getPoints().size() - 1);

			var x = (x1 + x2) / 2.0;
			var y = (y1 + y2) / 2.0;
			this.gardenpoly.getPoints().addAll(x, y);
			DoubleProperty xProperty = new SimpleDoubleProperty(x);
			DoubleProperty yProperty = new SimpleDoubleProperty(y);
			int idx2 = this.gardenpoly.getPoints().size() - 2;

			xProperty.addListener((ov, oldX, x3) -> {
				this.gardenpoly.getPoints().set(idx2, (double) x3);
				view.getController().setGardenPoints(gardenpoly.getPoints());
			});

			yProperty.addListener((ov, oldY, y3) -> {
				this.gardenpoly.getPoints().set(idx2 + 1, (double) y3);
				view.getController().setGardenPoints(gardenpoly.getPoints());
			});
			tmp = new Anchor(ANCHOR_COLOR, xProperty, yProperty);
			anchors.add(tmp);
			root.getChildren().add(tmp);
		});

		btnDelAnchor.setOnAction(e -> {
			var anchor = anchors.get(anchors.size() - 1);
			root.getChildren().remove(anchor);
			ObservableList<Double> points = this.gardenpoly.getPoints();
			for (int idx1 = 0; idx1 < points.size(); idx1 += 2) {
				if (points.get(idx1) < anchor.getCenterX() + CLICK_MARGIN
						&& points.get(idx1) > anchor.getCenterX() - CLICK_MARGIN
						&& points.get(idx1 + 1) < anchor.getCenterY() + CLICK_MARGIN
						&& points.get(idx1 + 1) > anchor.getCenterY() - CLICK_MARGIN) {
					points.remove(idx1);
					points.remove(idx1);
					break;
				}
			}
			anchors.remove(anchor);
		});

		fp.getChildren().addAll(new VBox(btnAddAnchor, btnDelAnchor), new HBox(lbl_name, textField_name),
				new HBox(lbl_budget, textField_budget), new HBox(lbl_width, textField_width));
		fp2.getChildren().addAll(new HBox(lbl_sunshade, vSun), new HBox(lbl_soil, vSoil),
				new HBox(lbl_moisture, vMoisture));
		fp.getStyleClass().add("label2");
		fp2.getStyleClass().add("flowpane");

		((BorderPane) root).setTop(fp);
		((BorderPane) root).setRight(fp2);

		bp = new BorderPane();
		createLabels();
		createButtons();
		root.getChildren().addAll(createControlAnchorsFor(gardenpoly.getPoints()));
		scene = new Scene(root, canvasW, canvasH);

		view.getController().setGardenPoints(gardenpoly.getPoints());
		return scene;
	}

	/**
	 * Creates buttons for this screen.
	 */
	void createButtons() {
		Button back = new Button("Cancel");
		Button mainScreen = new Button("Main Screen");

		bp.setLeft(back);
		bp.setRight(mainScreen);
		back.setOnAction(event -> view.getController().changeScreen(event, view.getStart()));
		mainScreen.setOnAction(event -> view.getController().changeScreen(event, view.getMain()));
		((BorderPane) root).setBottom(bp);
	}

	/**
	 * Creates labels for this screen.
	 */
	void createLabels() {
		Label gridLabel = new Label(String.format("The current width of a grid square is %.2f feet",
				1 / (view.getController().getScale() / (GRID_WIDTH * 2)))); // For some reason, the pixels in the javafx
																			// are half the size of pixels in css
		bp.setCenter(gridLabel);
	}

	/**
	 * Constructor for CreateNewGardenScreen.
	 * 
	 * @param view the view for the program
	 * 
	 */
	public CreateNewGardenScreen(View view) {
		super(view);
	}

	/**
	 * creates the initial polygon
	 * 
	 * @return the garden polygon
	 */
	private Polygon createStartingPolygon() {
		Polygon polygon = new Polygon();

		polygon.getPoints().setAll(ANCHOR_POINTS);

		polygon.setStroke(Color.FORESTGREEN);
		polygon.setStrokeWidth(POLYGON_STROKE_WIDTH);
		polygon.setStrokeLineCap(StrokeLineCap.ROUND);
		polygon.setFill(Color.CORNSILK.deriveColor(POLYGON_HUE_SHIFT, POLYFON_SATURATION_FACTOR,
				POLYGON_BRIGHTNESS_FACTOR, POLYGON_OPACITY_FACTOR));

		return polygon;
	}

	// @return a list of anchors which can be dragged around to modify points in the
	// format [x1, y1, x2, y2...]
	private ObservableList<Anchor> createControlAnchorsFor(final ObservableList<Double> points) {
		anchors = FXCollections.observableArrayList();

		for (int i = 0; i < points.size(); i += 2) {
			final int idx = i;

			DoubleProperty xProperty = new SimpleDoubleProperty(points.get(i));
			DoubleProperty yProperty = new SimpleDoubleProperty(points.get(i + 1));

			xProperty.addListener(new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue<? extends Number> ov, Number oldX, Number x) {
					points.set(idx, (double) x);
					view.getController().setGardenPoints(gardenpoly.getPoints());
				}
			});

			yProperty.addListener(new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue<? extends Number> ov, Number oldY, Number y) {
					points.set(idx + 1, (double) y);
					view.getController().setGardenPoints(gardenpoly.getPoints());
				}
			});

			anchors.add(new Anchor(ANCHOR_COLOR, xProperty, yProperty));
		}

		return anchors;
	}

}
