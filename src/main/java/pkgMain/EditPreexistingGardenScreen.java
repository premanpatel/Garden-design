package pkgMain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;

/**
 * The EditPreexisitingGardenScreen displays a screen on which plants can be
 * dragged onto the garden
 * 
 * @author alexsyou
 */
public class EditPreexistingGardenScreen extends EditorScreen {

	protected ObservableList<Label> oList;
	protected ListView<Label> list;
	protected Pane plot;
	private static final int IMAGE_VIEW_FIT_HEIGHT = 100;
	private final double LIST_VIEW_PREF_HEIGHT = canvasHeight - 100;
	private final double LIST_VIEW_PREF_WIDTH = 250;
	private static final int FLOW_PANE_HGAP = 50;

	/**
	 * The createScreen method creates the scene
	 * 
	 * @param canvasW The width of the canvas
	 * @param canvasH The height of the canvas
	 * 
	 * @return The scene displaying the editable garden
	 */
	Scene createScreen(double canvasW, double canvasH) {

		root = new BorderPane();
		// link to image: http://clipart-library.com/clipart/kcMKrBBXi.htm
		root.setBackground(new Background(new BackgroundFill(View.BACKGROUND_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));

		HashSet<Plant> plants = (HashSet<Plant>) view.controller.getPlantsToAdd();
		ArrayList<Label> labelList = new ArrayList<>();
		for (Plant p : plants) {
			PlantImage image = view.getPlantImage(p.getGenus(), p.getSpecies());
			ImageView iv = new ImageView();
			iv.setImage(image.getImage());
			iv.setPreserveRatio(true);
			iv.setFitHeight(IMAGE_VIEW_FIT_HEIGHT);
			Label label = new Label(p.getGenus() + " - " + p.getSpecies());
			label.setWrapText(true);
			label.setMaxWidth(LIST_VIEW_PREF_WIDTH - iv.getFitWidth());
			label.setGraphic(iv);
			labelList.add(label);
			var tooltip = new Tooltip(p.getGenus() + "-" + p.getSpecies() + "\n" + p.getName() + "\nCost: "
					+ p.getCost() + "\nSpread: " + p.getSpread() + "\nNumber of leps supported: " + p.getLepCount());
			tooltip.setShowDelay(Duration.seconds(0));
			Tooltip.install(label, tooltip);
		}
		oList = FXCollections.observableArrayList(labelList);

		plot = view.getPlotPane();

		list = new ListView<>(oList);
		list.setPrefHeight(LIST_VIEW_PREF_HEIGHT);
		list.setPrefWidth(LIST_VIEW_PREF_WIDTH);
		plot.setOnMouseDragReleased((event) -> view.getController().handleListMouseDragReleased(event));
		list.setOnDragDetected((event) -> view.getController().handleListDragDetected(event));

		VBox vSun = new VBox();
		Collection<Sunlight> suns = view.getController().getSunlight();
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
			sunCheck.setSelected(suns.contains(s));
		}

		VBox vSoil = new VBox();
		Collection<SoilType> soils = view.getController().getSoiltype();
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
			soilCheck.setSelected(soils.contains(s));
		}

		VBox vMoisture = new VBox();
		Collection<Moisture> moistures = view.getController().getMoisture();
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
			moistureCheck.setSelected(moistures.contains(m));
		}

		TextField textField_budget = new TextField("" + view.getController().getBudget());
		textField_budget.setTextFormatter(new TextFormatter<>(view.getController().filterInt));
		textField_budget.textProperty().addListener((observable, oldValue, newValue) -> view.getController()
				.textFieldSetBudget(observable, oldValue, newValue));

		TextField textField_name = new TextField(view.getController().getGardenName());
		textField_name.textProperty().addListener((observable, oldValue, newValue) -> view.getController()
				.textFieldSetName(observable, oldValue, newValue));

		var lbl_budget = new Label("Budget: ");
		var lbl_soil = new Label("Soil Type: ");
		var lbl_sunshade = new Label("Sun/Shade: ");
		var lbl_moisture = new Label("Moisture: ");
		Label lbl_name = new Label("Garden Name:");
		lbl_budget.getStyleClass().add("label1");
		lbl_soil.getStyleClass().add("label1");
		lbl_sunshade.getStyleClass().add("label1");
		lbl_moisture.getStyleClass().add("label1");
		lbl_name.getStyleClass().add("label1");

		var fp = new FlowPane();
		fp.setAlignment(Pos.CENTER);
		fp.setHgap(LABEL_WIDTH);
		var fp2 = new FlowPane(Orientation.VERTICAL, 0, CHECKBOX_HEIGHT);
		fp2.setAlignment(Pos.CENTER);

		fp.getChildren().addAll(new HBox(lbl_name, textField_name), new HBox(lbl_budget, textField_budget));
		fp2.getChildren().addAll(new HBox(lbl_sunshade, vSun), new HBox(lbl_soil, vSoil),
				new HBox(lbl_moisture, vMoisture));

		((BorderPane) root).setLeft(list);
		((BorderPane) root).setCenter(plot);
		((BorderPane) root).setRight(fp2);

		createButtons();
		createLabels();

		BorderPane topBar = (BorderPane) (((BorderPane) root).getTop());
		topBar.setRight(fp);

		scene = new Scene(root, canvasW, canvasH);
		scene.widthProperty().addListener(view.getController().listenerX);
		scene.heightProperty().addListener(view.getController().listenerY);
		return scene;
	}

	/**
	 * The createButtons method creates the buttons to be displayed
	 */
	void createButtons() {
		buttonList = new ArrayList<Button>();
		buttonList.add(new Button("Main Screen"));

		Button mainScreen = buttonList.get(0);
		mainScreen.setOnAction(event -> view.getController().changeScreen(event, view.getMain()));

		BorderPane topBar = new BorderPane();
		topBar.setLeft(mainScreen);
		((BorderPane) root).setTop(topBar);
	}

	/**
	 * The createLabels method creates the labels to be displayed
	 */
	void createLabels() {
		Text landTotal = new Text("Total Land (ft^2): "+view.formatLandTotal()+"\n");
		Text landUsed = new Text("Used: "+view.formatLandUsed()+"/");
		Text landLeft = new Text(""+view.formatLandLeft()+" Remaining");

		landTotal.setFill(Color.WHITE);
		landUsed.setFill(Color.ORANGE);
		landLeft.setFill(Color.LIGHTGREEN);
		TextFlow landFlow = new TextFlow();
		landFlow.getChildren().addAll(landTotal, landUsed, landLeft);

		
		Text budgetTotal = new Text("Total budget: $"+view.formatBudget()+"\n");
		Text budgetUsed = new Text("Used: $"+view.formatMoneyUsed()+"/");
		Text budgetLeft = new Text("$"+view.formatMoneyLeft()+" Remaining");

		budgetTotal.setFill(Color.WHITE);
		budgetUsed.setFill(Color.ORANGE);
		budgetLeft.setFill(Color.LIGHTGREEN);
		TextFlow budgetFlow = new TextFlow(budgetTotal, budgetUsed, budgetLeft);
		Text lepsText = new Text("Leps Supported: " + view.getController().getLepCount());
		lepsText.setFill(Color.WHITE);
		FlowPane fp = new FlowPane();
		fp.setAlignment(Pos.CENTER);
		fp.setHgap(FLOW_PANE_HGAP);
		fp.getChildren().addAll(landFlow, budgetFlow, lepsText);

		BorderPane topBar = (BorderPane) (((BorderPane) root).getTop());
		topBar.setCenter(fp);

		Label dragLabel = new Label("Drag a plant into the garden!");
		Label deleteLabel = new Label("You can remove a plant by right-clicking.");
		dragLabel.getStyleClass().add("label1");
		deleteLabel.getStyleClass().add("label1");

		FlowPane bottomPane = new FlowPane();
		bottomPane.setAlignment(Pos.CENTER);
		bottomPane.setHgap(FLOW_PANE_HGAP);
		bottomPane.getChildren().addAll(dragLabel, deleteLabel);

		((BorderPane) root).setBottom(bottomPane);
	}

	/**
	 * The constructor construct the class
	 * 
	 * @param view The view
	 */
	public EditPreexistingGardenScreen(View view) {
		super(view);
	}

}
