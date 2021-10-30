package pkgMain;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Path;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Scale;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * The View class shows different screens to the user.
 * 
 * @author rbila
 *
 */
public class View {

	Collection<PlantImage> plantImages;
	Collection<LepImage> lepImages;
	static double CANVAS_WIDTH = 1380;
	static double CANVAS_HEIGHT = 940;
	private final double POP_LABEL_HEIGHT = 50;
	private final double POP_LABEL_WIDTH = 70;
	static final Color BACKGROUND_COLOR = Color.web("#164A41");
	Screen AddPlant;
	Screen CreateNew;
	Screen EditPre;
	Screen LepList;
	Screen Load;
	Screen Main;
	Screen PlantList;
	Screen PlantMore;
	Screen Start;
	Screen Current;
	Screen GenInfo;
	Scene scene;
	Stage stage;

	Controller controller;
	Pane plotPane;

	private boolean shouldDrawPlants;
	DecimalFormat df;

	/**
	 * Constructor for View given stage and controller.
	 */
	public View(Stage theStage, Controller controller) {
		stage = theStage;
		this.controller = controller;
		Group root = new Group();
		theStage.setTitle("Native Garden Designer");
		plantImages = new HashSet<PlantImage>();
		lepImages = new HashSet<LepImage>();
		shouldDrawPlants = false;
		df = new DecimalFormat("#.##");
		createScreen();
		changeScreen(Start);
		CANVAS_WIDTH = stage.getScene().getWidth();
		CANVAS_HEIGHT = stage.getScene().getHeight();
		update();
		theStage.getIcons().add(new Image("icon.png"));
		theStage.show();
	}

	/**
	 * Returns whether or not the plants should be redrawn on the screen.
	 * 
	 * @return true if they should be; false otherwise.
	 */
	public boolean isShouldDrawPlants() {
		return shouldDrawPlants;
	}

	/**
	 * Sets whether of not the plants should be redrawn.
	 * 
	 * @param shouldDrawPlants true if they should be; false otherwise.
	 */
	public void setShouldDrawPlants(boolean shouldDrawPlants) {
		this.shouldDrawPlants = shouldDrawPlants;
	}

	/**
	 * Returns the decimal formatter to use for decimal values.
	 * 
	 * @return The DecimalFormat.
	 */
	public DecimalFormat getDf() {
		return df;
	}

	/**
	 * Sets the decimal formatter to use for decimal values to the given one.
	 * 
	 * @param df The decimal formatter.
	 */
	public void setDf(DecimalFormat df) {
		this.df = df;
	}

	/**
	 * Formats the given number according to the View's decimal format.
	 * 
	 * @param number The number to format.
	 * @return The String of the formatted number.
	 */
	public String format(double number) {
		return df.format(number);
	}

	/**
	 * Returns the amount of land in the Garden formatted as a String.
	 * 
	 * @return The total amount of land in the Garden.
	 */
	public String formatLandTotal() {
		return format(controller.getLandTotal());
	}

	/**
	 * Returns the amount of land used in the Garden formatted as a String.
	 * 
	 * @return The amount of land used in the Garden.
	 */
	public String formatLandUsed() {
		return format(controller.getLandUsed());
	}

	/**
	 * Returns the amount of land left in the Garden formatted as a String.
	 * 
	 * @return The amount of land left in the Garden.
	 */
	public String formatLandLeft() {
		return format(controller.getLandTotal() - controller.getLandUsed());
	}

	/**
	 * Returns the budget of the Garden formatted as a String.
	 * 
	 * @return The budget of the Garden.
	 */
	public String formatBudget() {
		return format(controller.getBudget());
	}

	/**
	 * Returns the money used for the Garden formatted as a String.
	 * 
	 * @return The amount of money used for the Garden.
	 */
	public String formatMoneyUsed() {
		return format(controller.getPlantCost());
	}

	/**
	 * Returns the money left for the Garden formatted as a String.
	 * 
	 * @return The amount of money left for the Garden.
	 */
	public String formatMoneyLeft() {
		return format(controller.getBudget() - controller.getPlantCost());
	}

	/**
	 * Loads plant images of a given plant information.
	 * 
	 * @param genus    genus name of the plant.
	 * @param species  species name of the plant.
	 * @param imageURL URL of the image of the plant.
	 */
	public void loadPlantImage(String genus, String species, String imageURL) {
		if (imageURL.replace("[\\s]", "").equals("")) {
			imageURL = "img/commonMilkweed.png";
		}
		plantImages.add(new PlantImage(new Image(imageURL), genus, species));
	}

	/**
	 * Loads lep images of a given lep information.
	 * 
	 * @param genus    genus name of the lep.
	 * @param species  species name of the lep.
	 * @param imageURL URL of the image of the lep.
	 */
	public void loadLepImage(String genus, String species, String imageURL) {
		if (imageURL.replace("[\\s]", "").equals("")) {
			imageURL = "img/showyemerald.png";
		} else {
			imageURL = imageURL.replace("%20", " ");
//			imageURL = imageURL.replace("http://", "https://");
		}
		System.out.println(imageURL);
		lepImages.add(new LepImage(new Image(imageURL), genus, species));
	}

	/**
	 * Instantiates all the screens in the program.
	 */
	public void createScreen() {
		Start = new StartScreen(this);
		CreateNew = new CreateNewGardenScreen(this);
		EditPre = new EditPreexistingGardenScreen(this);
		Load = new LoadGardenScreen(this);
		PlantMore = new PlantMoreInfoScreen(this);
		PlantList = new PlantListScreen(this);
		Main = new MainScreen(this);
		LepList = new LepListScreen(this);
		AddPlant = new AddPlantListScreen(this);
		GenInfo = new GenInfoScreen(this);
		Current = null;
	}

	/**
	 * Changes the screen to display a different screen.
	 * 
	 * @param screen The screen object that you want to change to.
	 */
	public void changeScreen(Screen screen) {
		boolean max = stage.isMaximized();
		if (Current != null) {
			Screen past = Current;
			Current = screen;
			CANVAS_WIDTH = past.getScene().getWidth();
			CANVAS_HEIGHT = past.getScene().getHeight();
		} else {
			Current = screen;
		}
		var sHeight = stage.getHeight();
		var sWidth = stage.getWidth();
		scene = Current.createScreen(CANVAS_WIDTH, CANVAS_HEIGHT);
		scene.getStylesheets().add("theme.css");
		stage.setScene(scene);
		stage.setWidth(sWidth);
		stage.setHeight(sHeight);
	}

	/**
	 * Sets the Scene.
	 * 
	 * @param scene The scene that is to be set.
	 */
	public void setScene(Scene scene) {
		stage.setScene(scene);
	}

	/**
	 * Returns the width of the canvas.
	 * 
	 * @return canvasWidth The width of the canvas.
	 */
	public static double getCanvaswidth() {
		return CANVAS_WIDTH;
	}

	/**
	 * Returns the height of the canvas.
	 * 
	 * @return canvasHeight The height of the canvas.
	 */
	public static double getCanvasheight() {
		return CANVAS_HEIGHT;
	}

	/**
	 * Returns the PlantImage given its information.
	 * 
	 * @return p The PlantImage of the given information.
	 */
	public PlantImage getPlantImage(String genus, String species) {
		for (PlantImage p : plantImages) {
			if (p.equalName(genus, species)) {
				return p;
			}
		}
		return null;
	}

	/**
	 * Returns the LepImage given its information.
	 * 
	 * @return l The LepImage of the given information.
	 */
	public LepImage getLepImage(String genus, String species) {
		for (LepImage l : lepImages) {
			if (l.equalName(genus, species)) {
				return l;
			}
		}
		return null;
	}

	/**
	 * Draws the plot of the garden that the user has created.
	 */
	public void drawGarden() {
		if (plotPane == null) {
			plotPane = new BorderPane();

			ArrayList<Double> points = controller.getGardenPoints();
			Polygon p = new Polygon();
			p.setMouseTransparent(true);
			p.setFill(Color.DARKKHAKI); // changed bc of background img

			p.setStroke(Color.BLACK);
			p.getPoints().addAll(points);
			((BorderPane) plotPane).setCenter(p);

			shouldDrawPlants = true;

			plotPane.widthProperty().addListener(controller.plotSizeListener);
			plotPane.heightProperty().addListener(controller.plotSizeListener);
		}
	}

	/**
	 * Draws the plants in the Garden in the plot.
	 */
	public void drawPlants() {
		if (shouldDrawPlants && plotPane.getWidth() > 0 && plotPane.getHeight() > 0) {
			Collection<Node> toRemove = new ArrayList<>();

			for (Node n : plotPane.getChildren()) {
				if (n instanceof Circle) {
					toRemove.add(n);
				}
			}
			plotPane.getChildren().removeAll(toRemove);

			double scale = controller.getScale();

			double xOffset = controller.getXOffset();
			double yOffset = controller.getYOffset();

			for (GardenPlant gp : controller.getPlacedPlants()) {
				Plant p = gp.getPlant();
				PlantImage i = getPlantImage(p.getGenus(), p.getSpecies());

				double x = gp.getLocation().getX() + xOffset;
				double y = gp.getLocation().getY() + yOffset;

				Circle c1 = new Circle(x, y, p.getSpread() * scale / 2);
				c1.setFill(new ImagePattern(i.getImage()));

				c1.setOnMousePressed(event1 -> controller.pressed(event1));
				c1.setOnMouseDragged(event2 -> controller.drag(event2));
				c1.setOnMouseReleased(event3 -> controller.released(event3));
				plotPane.getChildren().add(c1);

			}
			shouldDrawPlants = false;

			plotPane.getStyleClass().add("polygon");
		}
	}

	/**
	 * Creates the screen to show to the user.
	 */
	public void update() {
		Current.createScreen((double) CANVAS_WIDTH, (double) CANVAS_HEIGHT);

	}

	/**
	 * Returns the Controller.
	 * 
	 * @return controller The Controller.
	 */
	public Controller getController() {
		return controller;
	}

	/**
	 * Returns the AddPlant screen.
	 * 
	 * @return AddPlant AddPlant screen.
	 */
	public Screen getAddPlant() {
		return AddPlant;
	}

	/**
	 * Sets the value of the property AddPlant.
	 * 
	 * @param addPlant The value for the property AddPlant.
	 */
	public void setAddPlant(Screen addPlant) {
		AddPlant = addPlant;
	}

	/**
	 * Returns the CreateNew screen.
	 * 
	 * @return CreateNew CreateNew screen.
	 */
	public Screen getCreateNew() {
		return CreateNew;
	}

	/**
	 * Sets the value of the property CreateNew.
	 * 
	 * @param createNew The value for the property CreateNew.
	 */
	public void setCreateNew(Screen createNew) {
		CreateNew = createNew;
	}

	/**
	 * Returns the EditPre screen.
	 * 
	 * @return EditPre EditPre screen.
	 */
	public Screen getEditPre() {
		return EditPre;
	}

	/**
	 * Sets the value of the property EditPre.
	 * 
	 * @param editPre The value for the property EditPre.
	 */
	public void setEditPre(Screen editPre) {
		EditPre = editPre;
	}

	/**
	 * Returns the LepList screen.
	 * 
	 * @return LepList LepList screen.
	 */
	public Screen getLepList() {
		return LepList;
	}

	/**
	 * Sets the value of the property lepList.
	 * 
	 * @param lepList The value for the property LepList.
	 */
	public void setLepList(Screen lepList) {
		LepList = lepList;
	}

	/**
	 * Returns the Load screen.
	 * 
	 * @return Load Load screen.
	 */
	public Screen getLoad() {
		return Load;
	}

	/**
	 * Sets the value of the property Load.
	 * 
	 * @param load The value for the property Load.
	 */
	public void setLoad(Screen load) {
		Load = load;
	}

	/**
	 * Returns the Main screen.
	 * 
	 * @return Main Main screen.
	 */
	public Screen getMain() {
		return Main;
	}

	/**
	 * Sets the value of the property Main.
	 * 
	 * @param main The value for the property Main.
	 */
	public void setMain(Screen main) {
		Main = main;
	}

	/**
	 * Returns the PlantList screen.
	 * 
	 * @return PlantList PlantList screen.
	 */
	public Screen getPlantList() {
		return PlantList;
	}

	/**
	 * Sets the value of the property PlantList.
	 * 
	 * @param plantList The value for the property PlantList.
	 */
	public void setPlantList(Screen plantList) {
		PlantList = plantList;
	}

	/**
	 * Returns the PlantMore screen.
	 * 
	 * @return PlantMore PlantMore screen.
	 */
	public Screen getPlantMore() {
		return PlantMore;
	}

	/**
	 * Sets the value of the property PlantMore.
	 * 
	 * @param plantMore The value for the property PlantMore.
	 */
	public void setPlantMore(Screen plantMore) {
		PlantMore = plantMore;
	}

	/**
	 * Returns the Start screen.
	 * 
	 * @return Start Start screen.
	 */
	public Screen getStart() {
		return Start;
	}

	/**
	 * Sets the value of the property GenInfo.
	 * 
	 * @param genInfo The value for the property GenInfo.
	 */
	public void setGenInfo(Screen genInfo) {
		GenInfo = genInfo;
	}

	/**
	 * Returns the GenInfo screen.
	 * 
	 * @return GenInfo GenInfo screen.
	 */
	public Screen getGenInfo() {
		return GenInfo;
	}

	/**
	 * Sets the value of the property Start.
	 * 
	 * @param start The value for the property Start.
	 */
	public void setStart(Screen start) {
		Start = start;
	}

	/**
	 * Returns the Current screen.
	 * 
	 * @return Current Current screen.
	 */
	public Screen getCurrent() {
		return Current;
	}

	/**
	 * Sets the value of the property Current.
	 * 
	 * @param current The value for the property Current.
	 */
	public void setCurrent(Screen current) {
		Current = current;
	}

	/**
	 * Returns the value for the property stage.
	 * 
	 * @return stage The value for the property stage.
	 */
	public Stage getStage() {
		return stage;
	}

	/**
	 * Sets the value of the property stage.
	 * 
	 * @param stage The value for the property stage.
	 */
	public void setStage(Stage stage) {
		this.stage = stage;
	}

	/**
	 * Returns the value for the property plotPane.
	 * 
	 * @return plotPane The value for the property plotPane.
	 */
	public Pane getPlotPane() {
		return plotPane;
	}

	/**
	 * Sets the value of the property plotPane.
	 * 
	 * @param plotPane The value for the property plotPane.
	 */
	public void setPlotPane(Pane plotPane) {
		this.plotPane = plotPane;
	}

	/**
	 * Returns the outline of the given garden scaled to a smaller size.
	 * 
	 * @param g The Garden to scale.
	 * @return A polygon scaled to 25% of the original.
	 */
	Polygon getGardenScaledPolygon(Garden g) {
		Scale scale = new Scale();
		scale.setX(0.25);
		scale.setY(0.25);
		scale.setPivotX(300);
		scale.setPivotY(135);
		ArrayList<Double> points = g.getPoints();
		Polygon p = new Polygon();
		p.setMouseTransparent(true);
		p.setFill(Color.WHITE); // changed bc of background img
		p.setStroke(Color.BLACK);
		p.getPoints().addAll(points);
		p.getTransforms().addAll(scale);
		return p;
	}
}