package pkgMain;

import java.io.Serializable;

import javafx.geometry.Point2D;

/**
 * The GardenPlant class represents a plant placed in the garden, having a
 * location and associated plant.
 * 
 * @author rbila
 *
 */
public class GardenPlant implements Serializable {
	Plant plant;
	double x, y;

	/**
	 * Creates a garden plant of the given plant at the specified point.
	 * 
	 * @param plant The plant type that the garden plant is.
	 * @param x     The x location of the plant.
	 * @param y     The y location of the plant.
	 */
	public GardenPlant(Plant plant, double x, double y) {
		this.plant = plant;
		this.x = x;
		this.y = y;
	}

	/**
	 * Returns the plant type that the garden plant is.
	 * 
	 * @return The plant type.
	 */
	public Plant getPlant() {
		return plant;
	}

	/**
	 * Returns the x-y location of the garden plant.
	 * 
	 * @return The x-y location.
	 */
	public Point2D getLocation() {
		return new Point2D(x, y);
	}

	/**
	 * Sets the x-y location of the garden plant.
	 * 
	 * @param location The x-y location.
	 */
	public void setLocation(Point2D location) {
		this.x = location.getX();
		this.y = location.getY();
	}

	/**
	 * Sets the plant type that the garden plant is to the given one.
	 * 
	 * @param plant The plant type.
	 */
	public void setPlant(Plant plant) {
		this.plant = plant;
	}

	/**
	 * Returns the x location of the garden plant.
	 * 
	 * @return The x location.
	 */
	public double getX() {
		return x;
	}

	/**
	 * Sets the x location of the garden plant to the given value.
	 * 
	 * @param x The x location.
	 */
	public void setX(double x) {
		this.x = x;
	}

	/**
	 * Returns the y location of the garden plant.
	 * 
	 * @return The y location.
	 */
	public double getY() {
		return y;
	}

	/**
	 * Sets the y location of the garden plant to the given value.
	 * 
	 * @param y The y location.
	 */
	public void setY(double y) {
		this.y = y;
	}

	/**
	 * Checks if the garden plant is equal to the provided other.
	 * 
	 * @return true if equal; false otherwise.
	 */
	@Override
	public boolean equals(Object o) {
		if (o instanceof GardenPlant) {
			GardenPlant gp = (GardenPlant) o;
			return this.plant.equals(gp.plant) && this.x == gp.x && this.y == gp.y;
		}

		return false;
	}

}
