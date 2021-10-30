package pkgMain;

import javafx.beans.property.DoubleProperty;
import javafx.scene.Cursor;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;

/**
 * 
 * From https://gist.github.com/jewelsea/5375786
 * 
 * @author jewelsea
 *
 *         This is a helper Class for the Anchor points in the garden ploygon
 */
class Anchor extends Circle {

	/**
	 * DoubleProperty x and y for binding to the actual coordinate of the point in
	 * polygon
	 */
	private final DoubleProperty x, y;
	private static final int ANCHOR_HUE_SHIFT = 1;
	private static final int ANCHOR_SATURATION_FACTOR = 1;
	private static final int ANCHOR_BRIGHTNESS_FACTOR = 1;
	private static final double ANCHOR_OPACITY_FACTOR = 0.5;
	private static final int ANCHOR_RADIUS = 10;
	private static final int STROKE_WIDTH = 2;

	/**
	 * Create a new anchor with the given color, and x, y coordinate.
	 * 
	 * @param color
	 * @param x     coordinate
	 * @param y     coordinate
	 */
	Anchor(Color color, DoubleProperty x, DoubleProperty y) {
		super(x.get(), y.get(), ANCHOR_RADIUS);
		setFill(color.deriveColor(ANCHOR_HUE_SHIFT, ANCHOR_SATURATION_FACTOR, ANCHOR_BRIGHTNESS_FACTOR,
				ANCHOR_OPACITY_FACTOR));
		setStroke(color);
		setStrokeWidth(STROKE_WIDTH);
		setStrokeType(StrokeType.OUTSIDE);

		this.x = x;
		this.y = y;

		x.bind(centerXProperty());
		y.bind(centerYProperty());
		enableDrag();
	}

	/**
	 * Helper method for setting the event handler for the anchor point. allow the
	 * movement of anchor on drag.
	 */
	private void enableDrag() {
		final Delta dragDelta = new Delta();
		setOnMousePressed(mouseEvent -> {
			// record a delta distance for the drag and drop operation.
			dragDelta.x = getCenterX() - mouseEvent.getX();
			dragDelta.y = getCenterY() - mouseEvent.getY();
			getScene().setCursor(Cursor.MOVE);
		});
		setOnMouseReleased(e -> {
			getScene().setCursor(Cursor.HAND);
		});
		setOnMouseDragged(mouseEvent -> {
			double newX = mouseEvent.getX() + dragDelta.x;
			if (newX > 0 && newX < getScene().getWidth()) {
				setCenterX(newX);
			}
			double newY = mouseEvent.getY() + dragDelta.y;
			if (newY > 0 && newY < getScene().getHeight()) {
				setCenterY(newY);
			}
		});
		setOnMouseEntered(mouseEvent -> {
			if (!mouseEvent.isPrimaryButtonDown()) {
				getScene().setCursor(Cursor.HAND);
			}
		});
		setOnMouseExited(mouseEvent -> {
			if (!mouseEvent.isPrimaryButtonDown()) {
				getScene().setCursor(Cursor.DEFAULT);
			}
		});
	}

	/**
	 * This keeps track of the relative x and relative y value of the anchor.
	 * 
	 * @author JD
	 *
	 */
	private class Delta {
		double x, y;
	}
}
