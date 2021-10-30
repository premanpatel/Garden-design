package pkgMain;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.UnaryOperator;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import javafx.application.Application;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.control.ComboBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Popup;
import javafx.stage.Stage;

/**
 * The controller class for the native garden designer application.
 * 
 * @author JD
 *
 */
public class Controller extends Application {

	private Model model;
	private View view;

	private final boolean LOAD_IMAGES = true;
	private Stage theStage;

	/**
	 * Moves plant to a different location in the garden.
	 */
	public void movePlant() {

	}

	/**
	 * Adds given plant into a collection that contains all the plants.
	 * 
	 * @param p The plant that will be added to the collection.
	 */
	public void addPlant(Plant p) {
		model.addPlant(p);
	}

	/**
	 * Sets the the points for the garden shape.
	 * 
	 * @param points The points of the garden shape.
	 */
	public void setGardenPoints(ObservableList<Double> points) {
		model.getGarden().setPoints(points);
		view.getCurrent().createLabels();
	}

	/**
	 * return the points of the gardens shape.
	 * 
	 * @return gardenPoints Returns the gardenPoints of a garden shape.
	 */
	public ArrayList<Double> getGardenPoints() {
		return model.getGarden().getPoints();
	}

	/**
	 * Change to a different screen.
	 * 
	 * @param event  for the event handler of the button.
	 * @param screen The screen that will be changed to.
	 */
	public void changeScreen(Event event, Screen screen) {

		if (screen instanceof CreateNewGardenScreen) {
			model.createGarden();
			view.setPlotPane(null);
		} else if (screen instanceof StartScreen || screen instanceof LoadGardenScreen) {
			saveGarden();
		} else if (screen instanceof EditPreexistingGardenScreen || screen instanceof MainScreen) {
			view.setShouldDrawPlants(true);
		}
		view.changeScreen(screen);
	}

	/**
	 * Change to a different screen.
	 * 
	 * @param event            for the event handler of the button.
	 * @param screen           The screen that will be changed to.
	 * @param shouldDrawPlants true if the plant images should be redrawn.
	 */
	public void changeScreen(Event event, Screen screen, boolean shouldDrawPlants) {
		view.setShouldDrawPlants(shouldDrawPlants);
		changeScreen(event, screen);
	}

	/**
	 * Change to a different screen if that screen is a moreinfo screen.
	 * 
	 * @param event   for the event handler of the button.
	 * @param screen  The screen that will be changed to.
	 * @param genus   Genus of the plant or lep.
	 * @param species Species of the plant or lep.
	 */
	public void changeScreen(Event event, Screen screen, String genus, String species) {
		if (screen instanceof CreateNewGardenScreen) {
			model.createGarden();
			view.setPlotPane(null);
		}
		if (screen instanceof MoreInfoScreen) {
			((MoreInfoScreen) screen).setGenus(genus);
			((MoreInfoScreen) screen).setSpecies(species);
		}
		view.changeScreen(screen);
	}

	/**
	 * Returns the collection of all the plants.
	 * 
	 * @return AllPlants Collection of all plants.
	 */
	public Collection<Plant> getAllPlants() {
		return model.getAllPlants();
	}

	/**
	 * Returns the collection of all unique plants.
	 * 
	 * @return UniquePlants Collection of all unique plants.
	 */
	public Collection<Plant> getUniquePlants() {
		return model.getGarden().getUniquePlants();
	}

	/**
	 * Returns the collection of all current leps.
	 * 
	 * @return currentLeps Collection of current leps.
	 */
	public Collection<Lep> getUniqueLeps() {
		return model.getGarden().getCurrentLeps();
	}

	/**
	 * Returns the collection of placed plants.
	 * 
	 * @return PlacedPlants Collection of placed plants.
	 */
	public Collection<GardenPlant> getPlacedPlants() {
		return model.getGarden().getPlacedPlants();
	}

	/**
	 * Returns a plant given its information.
	 * 
	 * @param genus   Genus of the plant.
	 * @param species Species of the plant.
	 * @return Plant The plant returned from the given information.
	 */
	public Plant getPlant(String genus, String species) {
		return model.getPlant(genus, species);
	}

	/**
	 * Returns a lep given its information.
	 * 
	 * @param genus   Genus of the lep.
	 * @param species Species of the lep.
	 * @return Lep The lep returned from the given information.
	 */
	public Lep getLep(String genus, String species) {
		return model.getLep(genus, species);
	}

	/**
	 * Returns the budget of the garden.
	 * 
	 * @return budget budget of the garden.
	 */
	public double getBudget() {
		return model.getGarden().getBudget();
	}

	/**
	 * Returns the plantCost.
	 * 
	 * @return plantCost plantCost of the garden.
	 */
	public double getPlantCost() {
		return model.getGarden().getPlantCost();
	}

	/**
	 * Returns the land total area of the garden.
	 * 
	 * @return landTotal the land total area of the garden
	 */
	public double getLandTotal() {
		return model.getGarden().getScaledLandTotal();
	}

	/**
	 * Returns the land used area of the garden.
	 * 
	 * @return landUsed the land used area of the garden
	 */
	public double getLandUsed() {
		return model.getGarden().getScaledLandUsed();
	}

	/**
	 * Returns the number of placed plants in the garden.
	 * 
	 * @return PlacedPlantCount The number of plants in the garden.
	 */
	public int getPlacedPlantCount() {
		return model.getGarden().getPlacedPlants().size();
	}

	/**
	 * Returns the number of leps in the garden.
	 * 
	 * @return lepCount the number of leps in the garden.
	 */
	public int getLepCount() {
		return model.getGarden().getLepCount();
	}

	/**
	 * Returns the name of the current Garden.
	 * 
	 * @return The Garden's name.
	 */
	public String getGardenName() {
		return model.getGarden().getName();
	}

	/**
	 * Saves the garden into a serializable file.
	 */
	public void saveGarden() {
		model.saveGarden();
	}

	/**
	 * loads the garden from a serializable file.
	 */
	public void loadGarden(File gardenName) {
		model.loadGarden(gardenName);
		view.setPlotPane(null);
		changeScreen(null, view.getMain(), true);
	}

	/**
	 * Deletes the garden from files.
	 * 
	 * @param gardenFile a file for deletion
	 */
	void deleteGarden(File gardenFile) {
		model.deleteGarden(gardenFile);
	}

	/**
	 * loads and return the garden from a serializable file.
	 * 
	 * @param gardenName a file for load.
	 * @return the garden loaded from file.
	 */
	public Garden loadGardenFromFile(File gardenName) {
		Garden g = model.loadGardenFromFile(gardenName);
		return g;
	}

	/**
	 * Starts the program.
	 * 
	 * @param theStage ther stage for the program.
	 */
	@Override
	public void start(Stage theStage) {
		this.theStage = theStage;
		theStage.setMaximized(true);
		view = new View(theStage, this);
		model = new Model();
		readLeps();
		readPlants();
		theStage.setMaximized(true);
		view.update();

		theStage.show();

		theStage.setOnCloseRequest(event -> saveGarden());
	}

	/**
	 * reads Plant info from a file.
	 */
	public void readPlants() {
		FileReader fr = null;
		try {

			fr = new FileReader(new File("src/main/resources/plant-full-leps-soil-spread.csv"), StandardCharsets.UTF_8);

		} catch (Exception e) {
			e.printStackTrace();
		}
		Iterable<CSVRecord> records = null;
		try {
			final String[] HEADER = {

					"Scientific Name", "Common Name", "Plant Type", "URL", "Spread", "Soil Type",
					"Sun/Shade Conditions", "Image", "Summary", "Soil Moisture", "Lep Count", "Supported Leps"

			};

			records = CSVFormat.DEFAULT.withHeader(HEADER).withFirstRecordAsHeader().parse(fr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (records != null) {
			final Image placeholder = new Image("img/commonMilkweed.png");
			for (CSVRecord record : records) {

				String[] scientificName = record.get("Scientific Name").split(" ");

				String commonName = record.get("Common Name");
				commonName = commonName.replaceAll("�", "'");
				String description = record.get("Summary");
				description = description.replaceAll("�", "'");

				Plant t = model.createPlant(scientificName[0], scientificName[1], commonName, record.get("Plant Type"),
						record.get("URL"), Double.valueOf(record.get("Spread")), record.get("Soil Type"),
						record.get("Sun/Shade Conditions"), record.get("Soil Moisture"), description,
						Integer.valueOf(record.get("Lep Count")));
				String[] leps = record.get("Supported Leps").split(", ");
				for (Lep lep : model.getAllLeps()) {
					String lepName = lep.getGenus() + " " + lep.getSpecies();
					for (String name : leps) {
						if (lepName.equals(name)) {
							t.addSupportedLep(lep);
							lep.addSupportingPlant(t);
						}
					}
				}

				if (LOAD_IMAGES) {
					view.loadPlantImage(scientificName[0], scientificName[1], record.get("Image"));
				} else {
					view.plantImages.add(new PlantImage(placeholder, scientificName[0], scientificName[1]));
				}

			}
		}
	}

	/**
	 * reads Lep info from a file.
	 */
	public void readLeps() {
		FileReader fr = null;
		try {
			fr = new FileReader(new File("src/main/resources/leps.csv"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		Iterable<CSVRecord> records = null;
		try {
			final String[] HEADER = { "Scientific Name", "Common Name", "Image" };

			records = CSVFormat.DEFAULT.withHeader(HEADER).withFirstRecordAsHeader().parse(fr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (records != null) {
			final Image placeholder = new Image("img/showyemerald.png");
			for (CSVRecord record : records) {

				String[] scientificName = record.get("Scientific Name").split(" ");
				model.createLep(scientificName[0], scientificName[1], record.get("Common Name"));
				if (LOAD_IMAGES) {
					view.loadLepImage(scientificName[0], scientificName[1], record.get("Image"));
				} else {
					view.lepImages.add(new LepImage(placeholder, scientificName[0], scientificName[1]));
				}

			}
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Adds or removes sunlight from the garden.
	 * 
	 * @param s      the sunlight in the garden.
	 * @param adding whether to add or remove.
	 */
	public void checkSetSun(Sunlight s, boolean adding) {
		if (adding) {
			model.getGarden().addSunlight(s);
		} else {
			model.getGarden().removeSunlight(s);
		}
	}

	/**
	 * Adds or removes soil from the garden.
	 * 
	 * @param s      the soil in the garden.
	 * @param adding whether to add or remove.
	 */
	public void checkSetSoil(SoilType s, boolean adding) {
		if (adding) {
			model.getGarden().addSoiltype(s);
		} else {
			model.getGarden().removeSoiltype(s);
		}
	}

	/**
	 * Adds or removes moisture from the garden.
	 * 
	 * @param m      the moisture in the garden.
	 * @param adding whether to add or remove.
	 */
	public void checkSetMoisture(Moisture m, boolean adding) {
		if (adding) {
			model.getGarden().addMoisture(m);
		} else {
			model.getGarden().removeMoisture(m);
		}
	}

	/**
	 * Observable handler for the textfield that sets the budget of the garden.
	 * 
	 * @param observable the Observable needed to create a textfield.
	 * @param oldValue   Old value of the budget.
	 * @param newValue   new value of the budget.
	 */
	public void textFieldSetBudget(Observable observable, String oldValue, String newValue) {
		if (newValue.equals("")) {
			return;
		}
		double d = Double.valueOf(newValue);
		model.getGarden().setBudget(d);
		if (view.getCurrent() instanceof EditPreexistingGardenScreen) {
			view.getCurrent().createLabels();
		}
	}

	/**
	 * Observable handler for the textfield that sets the name of the garden.
	 * 
	 * @param observable the Observable needed to create a textfield.
	 * @param oldValue   Old value of the name.
	 * @param newValue   new value of the name.
	 */
	public void textFieldSetName(Observable observable, String oldValue, String newValue) {
		if (newValue.equals("")) {
			return;
		}
		model.getGarden().setName(newValue);
	}

	/**
	 * Sets the scale for the garden based on the given width.
	 * 
	 * @param ft The overall width of the garden.
	 */
	public void scaleFromFT(double ft) {
		model.getGarden().scaleFromFT(ft);
		view.getCurrent().createLabels();
	}

	/**
	 * Returns the scale of the garden in pixels/ft
	 * 
	 * @return The scale of the garden
	 */
	public double getScale() {
		return model.getGarden().getScale();
	}

	/**
	 * Returns the collection of the plants that are yet to be added to the garden.
	 * 
	 * @return PlantsToAdd Collection of the plants that are yet to be added to the
	 *         garden.
	 */
	public Collection<Plant> getPlantsToAdd() {

		return model.getGarden().getPlantsToAdd();
	}

	/**
	 * Returns the collection of the moisture of the garden.
	 * 
	 * @return moisture Collection of the moisture of the garden.
	 */
	public Collection<Moisture> getMoisture() {
		return model.getGarden().getMoisture();
	}

	/**
	 * Returns the collection of the SoilType of the garden.
	 * 
	 * @return soilType Collection of the SoilType of the garden.
	 */
	public Collection<SoilType> getSoiltype() {
		return model.getGarden().getSoiltype();
	}

	/**
	 * Returns the collection of the Sunlight of the garden.
	 * 
	 * @return sunlight Collection of the Sunlight of the garden.
	 */
	public Collection<Sunlight> getSunlight() {
		return model.getGarden().getSunlight();
	}

	/**
	 * Returns the collection of the plants matching conditions.
	 * 
	 * @return plantsMatchingConditions Collection of the plants matching conditions
	 */
	public Collection<Plant> getPlantsMatchingConditions() {
		return model.getPlantsMatchingConditions();
	}

	/**
	 * Returns the collection of the Gardens that the user has created.
	 * 
	 * @return files Serilizable file for the garden.
	 */
	public Collection<File> getListOfGardens() {

		File gardenFolder = new File("./gardens");
		ArrayList<File> gardenFiles = new ArrayList<File>(Arrays.asList(gardenFolder.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.toLowerCase().endsWith(".gar");
			}
		})));

		return gardenFiles;
	}

	/**
	 * 
	 * | from DnD_ListView.java \ / V
	 */

	/**
	 * Event handler for the mt cuba center link
	 * 
	 * @param event A click
	 */
	public void handleLink1(ActionEvent event) {
		getHostServices().showDocument("https://mtcubacenter.org");
	}

	/**
	 * Event handler for the nwf link
	 * 
	 * @param event A click
	 */
	public void handleLink2(ActionEvent event) {
		getHostServices().showDocument("https://www.nwf.org");
	}

	/**
	 * Event handler for detecting dragging from a listview.
	 * 
	 * @param event Mouse event.
	 */
	public void handleListDragDetected(MouseEvent event) {
		ListView<Label> list = (ListView<Label>) event.getSource();
		list.startFullDrag(); // key to javaFX special drag-n-drop handling
		event.consume();
	}

	// Add mouse handler for target
	/**
	 * Event handler for detecting drag released from a listview and adding the
	 * plant into the garden and saving its location.
	 * 
	 * @param event Mouse drag event.
	 */
	public void handleListMouseDragReleased(MouseDragEvent event) {
		if (event.getTarget() instanceof Circle) {
			event.consume();
			return;
		}

		Pane plot = (Pane) event.getTarget();
		double x_loc = event.getSceneX() - plot.getLayoutX();
		double y_loc = event.getSceneY() - plot.getLayoutY();

		if (view.getCurrent() instanceof EditPreexistingGardenScreen) {
			ListView<Label> list = (ListView<Label>) event.getGestureSource();
			ObservableList<Label> oList = list.getItems();

			int index = list.getSelectionModel().getSelectedIndex();
			Label selected_l = list.getSelectionModel().getSelectedItem();

			// add plant to garden model
			Plant plant = model.getPlant(selected_l.getText());
			double xOffset = getXOffset();
			double yOffset = getYOffset();

			if (!model.getGarden().isOverlap(plant, x_loc - xOffset, y_loc - yOffset)) {
				model.getGarden().addPlacedPlant(plant, x_loc - xOffset, y_loc - yOffset);

				ImageView selected = (ImageView) selected_l.getGraphic();

				Circle c1 = new Circle(x_loc, y_loc, plant.getSpread() * model.getGarden().getScale() / 2);
				c1.setFill(new ImagePattern(selected.getImage()));

				// put dragged Node back into list in same place
				c1.setOnMousePressed(event1 -> pressed(event1));
				c1.setOnMouseDragged(event2 -> drag(event2));
				c1.setOnMouseReleased(event3 -> released(event3));
				plot.getChildren().add(c1);
				c1.toFront();

				view.getCurrent().createLabels();

			}
			oList.set(index, selected_l);
		}

		event.consume();
	}

	/**
	 * Event handler for a press.
	 * 
	 * @param event Mouse event.
	 */
	public void pressed(MouseEvent event) {
		if (event.getButton() == MouseButton.SECONDARY && event.getSource() instanceof Circle
				&& view.getCurrent() instanceof EditPreexistingGardenScreen) {
			Toast.makeText(view.getStage(), "Plant Deleted");
			double xOffset = getXOffset();
			double yOffset = getYOffset();

			Circle c1 = (Circle) event.getSource();
			double x = c1.getCenterX() - getXOffset();
			double y = c1.getCenterY() - getYOffset();

			GardenPlant placed = model.getGarden().getGardenPlant(x, y);
			if (placed != null) {
				model.getGarden().removePlantPlaced(placed);
				view.getPlotPane().getChildren().remove(c1);
			}
			if (view.getCurrent() instanceof EditPreexistingGardenScreen) {
				view.getCurrent().createLabels();
			}

		} else {
			Node n = (Node) event.getSource();
			n.toFront();
		}
	}

	/**
	 * Event handler for dragging.
	 * 
	 * @param event Mouse event.
	 */
	public void drag(MouseEvent event) {

		if (view.getCurrent() instanceof EditPreexistingGardenScreen) {
			Circle c1 = (Circle) event.getSource();

			double xOffset = getXOffset();
			double yOffset = getYOffset();

			double x_before = c1.getCenterX() - xOffset;
			double y_before = c1.getCenterY() - yOffset;

			double x_after = event.getX() - xOffset;
			double y_after = event.getY() - yOffset;

			double circleXAfter = event.getX();
			double circleYAfter = event.getY();

			Point2D before = new Point2D(x_before, y_before);
			Point2D after = new Point2D(x_after, y_after);

			if (!model.getGarden().isOverlap(model.getGarden().getGardenPlant(before), x_after, y_after)
					&& circleXAfter - c1.getRadius() > 0 && circleYAfter - c1.getRadius() > 0) {
				c1.setCenterX(circleXAfter);
				c1.setCenterY(circleYAfter);
				model.getGarden().movePlant(before, after);
			}
			if (view.getCurrent() instanceof EditPreexistingGardenScreen) {
				view.getCurrent().createLabels();
			}
		}

	}

	/**
	 * Event handler for released.
	 * 
	 * @param event Mouse event.
	 */
	public void released(MouseEvent event) {
		System.out.println("released");
	}

	/**
	 * Returns the offset from the visual x position of the garden to the actual x
	 * position.
	 * <p>
	 * gardenX + offset = visualX
	 * 
	 * @return The x offset.
	 */
	public double getXOffset() {
		double plotWidth = view.getPlotPane().getWidth();
		double gardenWidth = model.getGarden().getWidth();

		double[] widthPoints = model.getGarden().getWidthPoints();

		double offset = (plotWidth / 2) - (gardenWidth / 2) - widthPoints[0];

		return offset;
	}

	/**
	 * Returns the offset from the visual y position of the garden to the actual y
	 * position.
	 * <p>
	 * gardenY + offset = visualY
	 * 
	 * @return The y offset.
	 */
	public double getYOffset() {
		double plotHeight = view.getPlotPane().getHeight();
		double gardenHeight = model.getGarden().getHeight();

		double[] heightPoints = model.getGarden().getHeightPoints();

		double offset = (plotHeight / 2) - (gardenHeight / 2) - heightPoints[0];

		return offset;
	}

	/**
	 * Checks the change in height of the screen.
	 */
	ChangeListener<Number> listenerY = new ChangeListener<Number>() {

		@Override
		public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
			if (!(view.getCurrent() instanceof MainScreen)
					&& !(view.getCurrent() instanceof EditPreexistingGardenScreen)) {
				return;
			}
			view.setPlotPane(null);
			view.drawGarden();
			System.out.println("Y: observable: " + observable + ", oldValue: " + oldValue.doubleValue() + ", newValue: "
					+ newValue.doubleValue());

			// similare to listenerX

		}
	};
	/**
	 * Checks the change in width of the screen.
	 */
	ChangeListener<Number> listenerX = new ChangeListener<Number>() {

		@Override
		public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
			if (!(view.getCurrent() instanceof MainScreen)
					&& !(view.getCurrent() instanceof EditPreexistingGardenScreen)) {
				return;
			}
			view.setPlotPane(null);
			view.drawGarden();
			System.out.println("Y: observable: " + observable + ", oldValue: " + oldValue.doubleValue() + ", newValue: "
					+ newValue.doubleValue());
			// similare to listenerX

		}
	};

	/**
	 * Checks for changes in the width of the plot and redraws the plants in the
	 * proper location.
	 */
	ChangeListener<Number> plotSizeListener = new ChangeListener<Number>() {
		@Override
		public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
			if (view.getPlotPane().getWidth() > 0.1 && view.getPlotPane().getHeight() > 0.1) {
				view.drawPlants();
			}
		}
	};

	/**
	 * https://stackoverflow.com/a/36436243 Prevent the user from entering
	 * characters that are not numbers into the budget textfield.
	 */
	UnaryOperator<TextFormatter.Change> filterInt = change -> {
		String text = change.getText();

		if (text.matches("[0-9]*")) {
			return change;
		}

		return null;
	};
	UnaryOperator<TextFormatter.Change> filterDouble = change -> {
		String text = change.getText();

		if (text.matches("[0-9.]*")) {
			return change;
		}

		return null;
	};

	/**
	 * Handler for FileChooser to load a garden
	 */
	EventHandler<ActionEvent> loadGardenHandler = new EventHandler<ActionEvent>() {
		public void handle(ActionEvent event) {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Open Your Garden");
			fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Garden Files", "*.gar"));
			fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
			view.getController().loadGarden(fileChooser.showOpenDialog(view.getStage()));
		}
	};

	/**
	 * helper method for exporting the garden to a plain text file with all the
	 * plants in the garden.
	 */
	public void export_report() {
		FileChooser chooser = new FileChooser();
		chooser.setTitle("Save As");
		chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text doc(*.txt)", "*.txt"));
		File reportFile = null;
		reportFile = chooser.showSaveDialog(null);
		if (reportFile == null) {
			return;
		}

		PrintWriter outFile = null;
		try {
			outFile = new PrintWriter(reportFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		outFile.println("Plants: ");
		for (Plant p : view.getController().getUniquePlants()) {
			outFile.println(p.getName() + "; " + p.getGenus() + " " + p.getSpecies());
		}
		outFile.close();
	}
}
