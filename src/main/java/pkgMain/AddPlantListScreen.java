package pkgMain;

import java.util.ArrayList;
import java.util.stream.Collectors;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.util.Callback;
import javafx.scene.layout.Region;

/**
 * The PlantListScreen class is a screen that lists the plants in the garden.
 * 
 * @author rbila
 *
 */
public class AddPlantListScreen extends ListScreen {

	private static final double IMAGE_SIZE = 100.0;
	private static final double ANCHOR_PANE_OFFSET = 0.0;
	private static final double FLOW_PANE_GAP = 10.0;

	/**
	 * Creates the scene with the given height and width.
	 * 
	 * @param canvasW The width of the scene.
	 * @param canvasH The height of the scene.
	 * @return The created scene.
	 */
	public Scene createScreen(double canvasW, double canvasH) {
		ObservableList<Plant> oList = FXCollections
				.observableArrayList(view.getController().getPlantsMatchingConditions());

		root = new BorderPane();
		list = new ListView<>(oList);
		list.setPrefHeight(canvasHeight);
		list.setPrefWidth(canvasWidth);
		AnchorPane.setBottomAnchor(list, ANCHOR_PANE_OFFSET);
		AnchorPane.setLeftAnchor(list, ANCHOR_PANE_OFFSET);
		AnchorPane.setTopAnchor(list, ANCHOR_PANE_OFFSET);
		AnchorPane.setRightAnchor(list, ANCHOR_PANE_OFFSET);

		FilteredList<Plant> filteredData = new FilteredList<Plant>(oList, s -> true);

		TextField filterInput = new TextField();
		filterInput.textProperty().addListener(obs -> {
			String filter = filterInput.getText();
			if (filter == null || filter.length() == 0) {
				filteredData.setPredicate(s -> true);
			} else {
				filteredData.setPredicate(s -> {
					return s.getSpecies().toLowerCase().contains(filter.toLowerCase())
							|| s.getGenus().toLowerCase().contains(filter.toLowerCase())
							|| s.getName().toLowerCase().contains(filter.toLowerCase());
				});
			}
		});

		SortedList<Plant> sortedPlant = new SortedList<>(filteredData);
		TableView<Plant> table = new TableView<Plant>(sortedPlant);
		sortedPlant.comparatorProperty().bind(table.comparatorProperty());
		TableColumn<Plant, String> sciNameCol = new TableColumn<>("Scientific Name");
		TableColumn<Plant, String> soilCol = new TableColumn<>("Soil");
		TableColumn<Plant, String> sunCol = new TableColumn<>("Sun");
		TableColumn<Plant, String> commonNameCol = new TableColumn<>("Common Name");
		TableColumn<Plant, String> moistureCol = new TableColumn<>("Moisture");
		TableColumn<Plant, Integer> lepCountCol = new TableColumn<>("Lep Count");
		TableColumn<Plant, Integer> costCol = new TableColumn<>("$ Cost");
		TableColumn<Plant, ImageView> imageCol = new TableColumn<>("Image");
		TableColumn<Plant, Double> spreadCol = new TableColumn<>("Spread(ft.)");
		TableColumn<Plant, Void> colBtn = new TableColumn<>("Button Column");
		TableColumn<Plant, String> addedCol = new TableColumn<>("Added?");

		sciNameCol.setCellValueFactory(p -> {
			return new SimpleObjectProperty<String>(p.getValue().getGenus() + "-" + p.getValue().getSpecies());

		});

		spreadCol.setCellValueFactory(p -> {
			return new SimpleObjectProperty<Double>(p.getValue().getSpread());

		});
		commonNameCol.setCellValueFactory(p -> {
			return new SimpleObjectProperty<String>(p.getValue().getName());

		});
		soilCol.setCellValueFactory(p -> {
			return new SimpleObjectProperty<String>(
					p.getValue().getSoilType().stream().map(e -> e.toString()).collect(Collectors.joining(", ")));

		});
		sunCol.setCellValueFactory(p -> {
			return new SimpleObjectProperty<String>(
					p.getValue().getSunlight().stream().map(e -> e.toString()).collect(Collectors.joining(", ")));
		});
		moistureCol.setCellValueFactory(new Callback<CellDataFeatures<Plant, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<Plant, String> p) {
				return new SimpleObjectProperty<String>(
						p.getValue().getMoisture().stream().map(e -> e.toString()).collect(Collectors.joining(", ")));
			}
		});

		lepCountCol.setCellValueFactory(p -> {
			return new SimpleObjectProperty<Integer>(p.getValue().getLepCount());
		});

		costCol.setCellValueFactory(p -> {
			return new SimpleObjectProperty<Integer>((int) p.getValue().getCost());
		});

		imageCol.setCellValueFactory(p -> {
			var iv = new ImageView(view.getPlantImage(p.getValue().getGenus(), p.getValue().getSpecies()).getImage());
			iv.setPreserveRatio(true);
			iv.setFitHeight(IMAGE_SIZE);
			return new SimpleObjectProperty<ImageView>(iv);
		});

		addedCol.setCellValueFactory(p -> {
			boolean added = false;
			for (Plant plant : view.getController().getPlantsToAdd()) {
				if (p.getValue() == plant) {
					return new SimpleObjectProperty<String>("\u2714");
				}
			}
			return new SimpleObjectProperty<String>("\u2718");
		});

		Callback<TableColumn<Plant, Void>, TableCell<Plant, Void>> cellFactory = new Callback<TableColumn<Plant, Void>, TableCell<Plant, Void>>() {
			@Override
			public TableCell<Plant, Void> call(final TableColumn<Plant, Void> param) {
				return new TableCell<Plant, Void>() {

					private final Button btn = new Button("Add plant");

					{
						btn.setOnAction((ActionEvent event) -> {
							Plant p = getTableView().getItems().get(getIndex());
							view.getController().addPlant(p);
							getTableView().refresh();
							Toast.makeText(view.getStage(), String.format("Added %s %s", p.getGenus(), p.getSpecies()));
						});
					}

					@Override
					public void updateItem(Void item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
						} else {
							setGraphic(btn);
						}
					}
				};
			}
		};

		colBtn.setCellFactory(cellFactory);

		table.getColumns().setAll(imageCol, commonNameCol, sciNameCol, soilCol, sunCol, moistureCol, lepCountCol,
				spreadCol, costCol, colBtn, addedCol);
		table.getStyleClass().add("table");

		Button mainScreen = new Button("Main Screen");
		Button editGarden = new Button("Edit Garden");

		Label title = new Label("Add Plants");
		Label search = new Label("Search: ");

		Region region1 = new Region();
		HBox.setHgrow(region1, Priority.ALWAYS);
		Region region2 = new Region();
		HBox.setHgrow(region2, Priority.ALWAYS);

		title.setAlignment(Pos.CENTER);

		HBox hbox = new HBox(mainScreen, search, filterInput, region1, title, region2, editGarden);

		hbox.setAlignment(Pos.CENTER);

		((BorderPane) root).setTop(hbox);
		mainScreen.setOnAction(event -> view.getController().changeScreen(event, view.getMain()));
		editGarden.setOnAction(event -> view.getController().changeScreen(event, view.getEditPre()));

		((BorderPane) root).setCenter(table);
		createButtons();
		scene = new Scene(root, canvasW, canvasH);
		return scene;
	}

	/**
	 * Creates the buttons for the scene and adds them to root.
	 * <p>
	 * Currently doesn't create any buttons.
	 */
	public void createButtons() {

	}

	/**
	 * Creates labels for the scene and adds them to root.
	 * <p>
	 * Has labels for budget, area, plant count, and lep count.
	 */
	public void createLabels() {
		Label budgetLabel = new Label(
				"Budget: " + (view.getController().getBudget() - view.getController().getPlantCost()));
		Label areaLabel = new Label(
				"Area: " + (view.getController().getLandTotal() - view.getController().getLandUsed()));
		Label plantLabel = new Label("Plants: " + view.getController().getPlacedPlantCount());
		Label lepLabel = new Label("Leps: " + view.getController().getLepCount());

		labelList = new ArrayList<Label>();
		labelList.add(budgetLabel);
		labelList.add(areaLabel);
		labelList.add(plantLabel);
		labelList.add(lepLabel);

		FlowPane fp = new FlowPane();
		fp.getChildren().addAll(labelList);
		fp.setAlignment(Pos.CENTER);
		fp.setHgap(50);
		((BorderPane) root).setBottom(fp);
	}

	/**
	 * Creates a new screen with the given view.
	 * 
	 * @param view The main view.
	 */
	public AddPlantListScreen(View view) {
		super(view);
	}
}
