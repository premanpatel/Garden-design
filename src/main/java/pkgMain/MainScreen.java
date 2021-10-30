package pkgMain;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;

/**
 * MainScreen displays the scene that shows the garden and allows the user to
 * see many aspects of the garden, as well as access many different screens.
 * 
 * @author alexsyou
 *
 */
public class MainScreen extends Screen {

	private static final int FLOW_PANE_HGAP = 50;
	private static final double GRID_WIDTH = 10;

	/**
	 * The createScreen creates the scene that shows the MainScreen
	 * 
	 * @param canvasW Width of the canvas
	 * @param canvasH Height of the canvas
	 * 
	 * @return The scene that displays the MainScreen
	 */
	Scene createScreen(double canvasW, double canvasH) {
		root = new BorderPane();
		// link to image: http://clipart-library.com/clipart/kcMKrBBXi.htm
		root.setBackground(new Background(new BackgroundFill(View.BACKGROUND_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
		view.drawGarden();
		((BorderPane) root).setCenter(view.getPlotPane());
		canvasWidth = canvasW;
		canvasHeight = canvasH;

		createButtons();
		createLabels();
		scene = new Scene(root, canvasW, canvasH);
		return scene;
	}

	/**
	 * The createButtons method adds the buttons necessary for the MainScreen.
	 */
	void createButtons() {
		buttonList = new ArrayList<Button>();
		buttonList.add(new Button("Export"));
		buttonList.add(new Button("Edit Garden"));
		buttonList.add(new Button("Leps: " + view.getController().getLepCount()));

		if (view.getController().getPlacedPlants() != null) {
			buttonList.add(new Button("Plants: " + view.getController().getPlacedPlants().size())); // add plant count
																									// to button txt
		} else {
			buttonList.add(new Button("Plants: 0")); // add plant count to button txt
		}
		buttonList.add(new Button("Add Plants"));
		buttonList.add(new Button("Load Different Garden"));
		root.getChildren().addAll(buttonList);
		Button export = buttonList.get(0);
		Button editGarden = buttonList.get(1);
		Button leps = buttonList.get(2);
		Button plants = buttonList.get(3);
		Button addPlants = buttonList.get(4);
		Button loadDiff = buttonList.get(5);
		BorderPane bp = (BorderPane) root;
		var top = new BorderPane();
		ImageView grid = new ImageView("/img/grid2.png");
		Label gridLabel = new Label(
				String.format("%.2f feet", 1 / (view.getController().getScale() / (GRID_WIDTH * 2))), grid);
		gridLabel.getStyleClass().add("label1");// For some reason, the pixels in the css file are twice the size of the
												// pixels in javafx
		gridLabel.setAlignment(Pos.CENTER_RIGHT);
		var hb = new HBox(FLOW_PANE_HGAP);
		hb.getChildren().addAll(gridLabel, addPlants);
		top.setLeft(loadDiff);
		top.setRight(hb);
		top.setCenter(editGarden);

		var left = new BorderPane();
		left.setCenter(leps);

		var right = new BorderPane();
		right.setCenter(plants);

		var bottom = new BorderPane();

		
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
		FlowPane fp = new FlowPane();
		fp.setAlignment(Pos.CENTER);
		fp.setHgap(FLOW_PANE_HGAP);
		fp.getChildren().addAll(landFlow, export, budgetFlow);
		bottom.setCenter(fp);

		bp.setTop(top);
		bp.setLeft(left);
		bp.setRight(right);
		bp.setBottom(bottom);

		export.setOnAction(event -> view.getController().export_report());
		editGarden.setOnAction(event -> view.getController().changeScreen(event, view.getEditPre()));
		leps.setOnAction(event -> view.getController().changeScreen(event, view.getLepList()));
		plants.setOnAction(event -> view.getController().changeScreen(event, view.getPlantList()));
		addPlants.setOnAction(event -> view.getController().changeScreen(event, view.getAddPlant()));
		loadDiff.setOnAction(event -> view.getController().changeScreen(event, view.getLoad()));
	}

	/**
	 * The createLabels method creates labels for the MainScreen
	 */
	void createLabels() {
	}

	/**
	 * The MainScreen constructor constructs the MainScreen
	 * 
	 * @param view The view
	 */
	public MainScreen(View view) {
		super(view);
	}
}
