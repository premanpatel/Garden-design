package pkgMain;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

// Ref: https://stackoverflow.com/a/38373408

/**
 * The class for toast message (android style popup message).
 * 
 * @author JD
 *
 */
public final class Toast {
	private static final int TOAST_MSG_TIME = 500; // 0.5 seconds
	private static final int TOAST_FADE_IN_TIME = 50; // 0.05 seconds
	private static final int TOAST_FADE_OUT_TIME = 100; // 0.2 seconds

	/**
	 * Show the toastMsg in the ownerStage using default timing.
	 * 
	 * @param ownerStage the stage where the toast message will be attached.
	 * @param toastMsg   the message to show on the stage.
	 */
	public static void makeText(Stage ownerStage, String toastMsg) {
		makeText(ownerStage, toastMsg, TOAST_MSG_TIME, TOAST_FADE_IN_TIME, TOAST_FADE_OUT_TIME);
	}

	/**
	 * Show the toastMsg in the ownerStage using custom timing.
	 * 
	 * @param ownerStage   the stage where the toast message will be attached.
	 * @param toastMsg     the message to show on the stage
	 * @param toastDelay   the amount of time display the message
	 * @param fadeInDelay  the amount of time for the message to fade-in
	 * @param fadeOutDelay the amount of time for the message to fade-out
	 */
	public static void makeText(Stage ownerStage, String toastMsg, int toastDelay, int fadeInDelay, int fadeOutDelay) {
		Stage toastStage = new Stage();
		toastStage.initOwner(ownerStage);
		toastStage.setResizable(false);
		toastStage.initStyle(StageStyle.TRANSPARENT);

		Text text = new Text(toastMsg);
		text.setFont(Font.font("Verdana", 40));
		text.setFill(Color.BLACK);

		StackPane root = new StackPane(text);
		root.setStyle("-fx-background-radius: 20; -fx-background-color: rgba(0, 0, 0, 0.2); -fx-padding: 50px;");
		root.setOpacity(0);

		Scene scene = new Scene(root);
		scene.setFill(Color.TRANSPARENT);
		toastStage.setScene(scene);
		toastStage.show();

		Timeline fadeInTimeline = new Timeline();
		KeyFrame fadeInKey1 = new KeyFrame(Duration.millis(fadeInDelay),
				new KeyValue(toastStage.getScene().getRoot().opacityProperty(), 1));
		fadeInTimeline.getKeyFrames().add(fadeInKey1);
		fadeInTimeline.setOnFinished((ae) -> {
			new Thread(() -> {
				try {
					Thread.sleep(toastDelay);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Timeline fadeOutTimeline = new Timeline();
				KeyFrame fadeOutKey1 = new KeyFrame(Duration.millis(fadeOutDelay),
						new KeyValue(toastStage.getScene().getRoot().opacityProperty(), 0));
				fadeOutTimeline.getKeyFrames().add(fadeOutKey1);
				fadeOutTimeline.setOnFinished((aeb) -> toastStage.close());
				fadeOutTimeline.play();
			}).start();
		});
		fadeInTimeline.play();
	}
}