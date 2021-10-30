package pkgMain;

import java.util.ArrayList;
import java.util.HashSet;

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
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.util.Callback;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

/**
 * The screen that is responsible for displaying all the plants in a tableview.
 * 
 * @author JD
 *
 */
public class PlantListScreen extends ListScreen {

	private final int listTranslateHeight = 30;
	private final int budgetLabelY = 276;
	private final int areaLabelY = 552;
	private final int plantLabelY = 828;
	private final int lepLabelY = 1104;
	private final int labelY = 10;
	private static final double IMAGE_SIZE = 100.0;
	private static final double ANCHOR_PANE_OFFSET = 0.0;
	private static final double FLOW_PANE_GAP = 10.0;

	/**
	 * Creates the PlantListScreen.
	 * 
	 * @param canvasW width of the canvas.
	 * @param canvasH height of the canvas.
	 * 
	 * @return scene The scene for the stage.
	 */
	public Scene createScreen(double canvasW, double canvasH) {
		ObservableList<Plant> oList = FXCollections.observableArrayList(view.getController().getUniquePlants());
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
			return new SimpleObjectProperty<String>(p.getValue().getSoilType().toString());

		});
		sunCol.setCellValueFactory(p -> {
			return new SimpleObjectProperty<String>(p.getValue().getSunlight().toString());
		});
		moistureCol.setCellValueFactory(new Callback<CellDataFeatures<Plant, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<Plant, String> p) {
				return new SimpleObjectProperty<String>(p.getValue().getMoisture().toString());
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

		Callback<TableColumn<Plant, Void>, TableCell<Plant, Void>> cellFactory = new Callback<TableColumn<Plant, Void>, TableCell<Plant, Void>>() {
			@Override
			public TableCell<Plant, Void> call(final TableColumn<Plant, Void> param) {
				return new TableCell<Plant, Void>() {

					private final Button btn = new Button("More Info");

					{
						btn.setOnAction((ActionEvent event) -> {
							Plant p = getTableView().getItems().get(getIndex());
							getTableView().refresh();
							view.getController().changeScreen(event, view.getPlantMore(), p.getGenus(), p.getSpecies());
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
				spreadCol, costCol, colBtn);
		table.getStyleClass().add("table");

		Button mainScreen = new Button("Main Screen");

		var fp = new FlowPane(mainScreen, new Label("Search: "), filterInput);
		fp.setHgap(FLOW_PANE_GAP);
		((BorderPane) root).setTop(fp);
		mainScreen.setOnAction(event -> view.getController().changeScreen(event, view.getMain()));

		((BorderPane) root).setCenter(table);
		createButtons();
		scene = new Scene(root, canvasW, canvasH);
		return scene;
	}

	/**
	 * Creates buttons for the screen.
	 */
	public void createButtons() {

		var mainScreen = new Button("Main Screen");
		var addPlants = new Button("Add Plants");
		Region region1 = new Region();
		HBox.setHgrow(region1, Priority.ALWAYS);
		Region region2 = new Region();
		HBox.setHgrow(region2, Priority.ALWAYS);
		HBox hbox = new HBox(mainScreen, region1, new Label("List of Plants in Garden"), region2, addPlants);
		((BorderPane) root).setTop(hbox);
		mainScreen.setOnAction(event -> view.getController().changeScreen(event, view.getMain()));
		addPlants.setOnAction(event -> view.getController().changeScreen(event, view.getAddPlant()));

	}

	/**
	 * Creates labels for the screen.
	 */
	public void createLabels() {
		String landTotal = view.formatLandTotal();
		String landUsed = view.formatLandUsed();
		
		String budgetTotal = view.formatBudget();
		String moneyUsed = view.formatMoneyUsed();
		
		Label budgetLabel = new Label("Budget: $" + moneyUsed + " of $"+budgetTotal+" used");
		Label areaLabel = new Label("Area: " + landUsed + "ft2 of "+landTotal+"ft2 used");

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
	 * Constructor for PlantListScreen.
	 */
	public PlantListScreen(View view) {
		super(view);
	}
}