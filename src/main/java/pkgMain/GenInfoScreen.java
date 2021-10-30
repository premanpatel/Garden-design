package pkgMain;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

/**
 * The GenInfoScreen shows some general information about why native plants are important in gardens
 * @author alexsyou
 *
 */
public class GenInfoScreen extends Screen {

	/**
	 * Create a new GenInfoScreen with given view
	 * @param view	The view
	 */
	public GenInfoScreen(View view) {
		super(view);
	}
	

	/**
	 * Creates the scene for the GenInfoScreen
	 * 
	 * @param 	canvasW		The width of the canvas
	 * @param 	canvasH		The height of the canvas
	 * 
	 * @return	scene		The scene for the stage
	 */
	@Override
	Scene createScreen(double canvasW, double canvasH) {
		root = new BorderPane();
		root.setBackground(new Background(new BackgroundImage(new Image("img/BackgroundLandscape.jpg"),
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
				new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, true, true))));
		Label l = new Label("Place Holder Text");
		((BorderPane) root).setCenter(l);

		createButtons();
		createLabels();
		scene = new Scene(root, canvasW, canvasH);
		return scene;
	}

	/**
	 * Creates the buttons needed in the GenInfoScreen
	 */
	@Override
	void createButtons() {
		Button back = new Button("Back");
		back.setOnAction(event -> view.getController().changeScreen(event, view.getStart()));
		((BorderPane) root).setTop(back);
	}

	/**
	 * Creates the labels in the GenInfoScreen that show the information
	 */
	@Override
	void createLabels() {
		TextFlow flow = new TextFlow();
		Text l1 = new Text(
				"What are leps? \nLeps, or lepidoptera, consists of butterflies and moths. \nLeps are important because they help support the native ecosystem. \nBut in order to do so they need native plants to eat. \nWhich is why planting native plants is vital.\nFor more information Visit: \n");
		Hyperlink l2 = new Hyperlink("https://mtcubacenter.org");
		Text l3 = new Text("\nor \n");
		Hyperlink l4 = new Hyperlink("https://www.nwf.org");
		l2.setOnAction((ActionEvent e) -> view.getController().handleLink1(e));
		l4.setOnAction((ActionEvent e) -> view.getController().handleLink2(e));
		flow.getChildren().addAll(l1, l2, l3, l4);
		flow.getStyleClass().add("large-text");
		flow.setTextAlignment(TextAlignment.CENTER);
		((BorderPane) root).setCenter(flow);
	}

}
