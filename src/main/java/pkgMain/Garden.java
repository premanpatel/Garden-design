package pkgMain;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * 
 * 
 */
import java.util.Collection;
import java.util.HashSet;

import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

/**
 * The Garden class represents a garden with different conditions and can have
 * plants placed and moved around in it.
 * 
 * @author rbila
 *
 */
public class Garden implements Serializable {

	HashSet<GardenPlant> placedPlants;
	HashSet<Plant> uniquePlants;
	HashSet<Plant> plantsToAdd;
	HashSet<Lep> currentLeps;

	ArrayList<GardenPlant> compostPlants;
	ArrayList<Double> points;
	int numPoints;

	double rightDiagonal;
	double leftDiagonal;

	double plantCost;
	double budget;

	double landUsed;
	double landTotal;
	double scale; // pixels per ft
	double widthFT;

	HashSet<Moisture> moisture;
	HashSet<Sunlight> sunlight;
	HashSet<SoilType> soiltype;

	String name;

	int lepCount;

	/**
	 * Default constructor that sets budget and land to 0.
	 */
	Garden() {
		this(0, 0);
	}

	/**
	 * Constructor that sets given budget and land total.
	 * 
	 * @param budget    Total budget in $ for the Garden
	 * @param landTotal Total land in ft2 for the Garden
	 */
	Garden(double budget, double landTotal) {
		this.budget = budget;
		this.landTotal = landTotal;

		placedPlants = new HashSet<GardenPlant>();
		uniquePlants = new HashSet<Plant>();
		plantsToAdd = new HashSet<Plant>();
		currentLeps = new HashSet<Lep>();
		compostPlants = new ArrayList<GardenPlant>();
		moisture = new HashSet<>();
		sunlight = new HashSet<>();
		soiltype = new HashSet<>();

		points = null;
		landUsed = 0;
		rightDiagonal = 0;
		leftDiagonal = 0;
	}

	/**
	 * Returns the total cost of all placed plants in the Garden.
	 * 
	 * @return plantCost Total plant cost.
	 */
	public double getPlantCost() {
		return plantCost;
	}

	/**
	 * Returns the total budget for the Garden.
	 * 
	 * @return budget Total budget.
	 */
	public double getBudget() {
		return budget;
	}

	/**
	 * Returns the width of the Garden in pixels.
	 * 
	 * @return The pixel width of the garden.
	 */
	public double getWidth() {
		double[] widthPoints = getWidthPoints();
		return widthPoints[1] - widthPoints[0];
	}

	/**
	 * Returns the height of the Garden in pixels.
	 * 
	 * @return The pixel height of the garden.
	 */
	public double getHeight() {
		double[] heightPoints = getHeightPoints();
		return heightPoints[1] - heightPoints[0];
	}

	/**
	 * Returns the leftmost and rightmost x locations of the Garden in an array.
	 * 
	 * @return An array with {leftmost, rightmost}.
	 */
	public double[] getWidthPoints() {
		double leftMost = -1;
		double rightMost = -1;
		for (int i = 2; i < points.size() - 1; i += 2) {
			if (leftMost == -1 && rightMost == -1) {
				leftMost = Math.min(points.get(0), points.get(i));
				rightMost = Math.max(points.get(0), points.get(i));
			} else {
				if (points.get(i) > rightMost) {
					rightMost = points.get(i);
				}
				if (points.get(i) < leftMost) {
					leftMost = points.get(i);
				}
			}
		}
		return new double[] { leftMost, rightMost };
	}

	/**
	 * Returns the topmost and bottommost y locations of the Garden in an array.
	 * 
	 * @return An array with {topmost, bottommost}.
	 */
	public double[] getHeightPoints() {
		double topMost = -1;
		double bottomMost = -1;
		for (int i = 3; i < points.size(); i += 2) {
			if (topMost == -1 && bottomMost == -1) {
				topMost = Math.min(points.get(1), points.get(i));
				bottomMost = Math.max(points.get(1), points.get(i));
			} else {
				if (points.get(i) > bottomMost) {
					bottomMost = points.get(i);
				}
				if (points.get(i) < topMost) {
					topMost = points.get(i);
				}
			}
		}
		return new double[] { topMost, bottomMost };
	}

	/**
	 * Returns the total land used in the Garden, in pixels.
	 * 
	 * @return landUsed Total land used in pixels.
	 */
	public double getLandUsed() {
		return landUsed;
	}

	/**
	 * Returns the total amount of land in the Garden, in pixels.
	 * 
	 * @return landTotal Total land in pixels.
	 */
	public double getLandTotal() {
		return landTotal;
	}

	/**
	 * Returns the total amount of land used in the garden based off of the scale
	 * value.
	 * 
	 * @return Total land used in ft^2.
	 */
	public double getScaledLandUsed() {
		return landUsed / (scale * scale);
	}

	/**
	 * Returns the total amount of land in the garden based off of the scale value.
	 * 
	 * @return Total land in ft^2.
	 */
	public double getScaledLandTotal() {
		return landTotal / (scale * scale);
	}

	/**
	 * Returns the scale value of the Garden, in pixels/ft.
	 * 
	 * @return The scale value.
	 */
	public double getScale() {
		return scale;
	}

	/**
	 * Sets the scale of the Garden to the given value.
	 * 
	 * @param scale The scale value.
	 */
	public void setScale(double scale) {
		if (scale > 0) {
			this.scale = scale;
		}
	}

	/**
	 * Calculates the scale value based off of the given width of the garden.
	 * 
	 * @param ft How wide the garden is in ft.
	 */
	public void scaleFromFT(double ft) {// width of the garden / ft
		widthFT = ft;
		double leftMost = -1;
		double rightMost = -1;
		for (int i = 2; i < points.size() - 1; i += 2) {
			if (leftMost == -1 && rightMost == -1) {
				leftMost = Math.min(points.get(0), points.get(i));
				rightMost = Math.max(points.get(0), points.get(i));
			} else {
				if (points.get(i) > rightMost) {
					rightMost = points.get(i);
				}
				if (points.get(i) < leftMost) {
					leftMost = points.get(i);
				}
			}
		}

		double totalWidth = rightMost - leftMost;
		this.scale = totalWidth / ft;
	}

	/**
	 * Returns a collection of all moisture types in the Garden.
	 * 
	 * @return moisture Collection of all moisture types.
	 */
	public Collection<Moisture> getMoisture() {
		return moisture;
	}

	/**
	 * Sets the collection of moisture types in the Garden to the given one.
	 * 
	 * @param moisture a HashSet of moisture types.
	 */
	public void setMoisture(HashSet<Moisture> moisture) {
		this.moisture = moisture;
	}

	/**
	 * Adds the given moisture type to Garden.
	 * 
	 * @param m The moisture type to add to the Garden.
	 * @return boolean Whether or not m was added to the collection.
	 */
	public boolean addMoisture(Moisture m) {
		return this.moisture.add(m);
	}

	/**
	 * Removes the given moisture type from Garden.
	 * 
	 * @param m The moisture type to remove from the Garden.
	 * @return boolean Whether or not m was removed from the collection.
	 */
	public boolean removeMoisture(Moisture m) {
		return this.moisture.remove(m);
	}

	/**
	 * Returns a collection of all sunlight types in the Garden.
	 * 
	 * @return sunlight Collection of all sunlight types.
	 */
	public Collection<Sunlight> getSunlight() {
		return sunlight;
	}

	/**
	 * Sets the collection of sunlight types in the Garden to the given one.
	 * 
	 * @param sunlight a HashSet of sunlight types.
	 */
	public void setSunlight(HashSet<Sunlight> sunlight) {
		this.sunlight = sunlight;
	}

	/**
	 * Adds the given sunlight type to Garden.
	 * 
	 * @param s The sunlight type to add to the Garden.
	 * @return boolean Whether or not s was added to the collection.
	 */
	public boolean addSunlight(Sunlight s) {
		return this.sunlight.add(s);
	}

	/**
	 * Removes the given sunlight type from Garden.
	 * 
	 * @param s The sunlight type to remove from the Garden.
	 * @return boolean Whether or not s was removed from the collection.
	 */
	public boolean removeSunlight(Sunlight s) {
		return this.sunlight.remove(s);
	}

	/**
	 * Returns a collection of all soil types in the Garden.
	 * 
	 * @return soil Collection of all soil types.
	 */
	public Collection<SoilType> getSoiltype() {
		return soiltype;
	}

	/**
	 * Sets the collection of soil types in the Garden to the given one.
	 * 
	 * @param soiltype a HashSet of soil types.
	 */
	public void setSoiltype(HashSet<SoilType> soiltype) {
		this.soiltype = soiltype;
	}

	/**
	 * Adds the given soil type to Garden.
	 * 
	 * @param s The soil type to add to the Garden.
	 * @return boolean Whether or not s was added to the collection.
	 */
	public boolean addSoiltype(SoilType s) {
		return this.soiltype.add(s);
	}

	/**
	 * Removes the given soil type from Garden.
	 * 
	 * @param s The soil type to remove from the Garden.
	 * @return boolean Whether or not s was removed from the collection.
	 */
	public boolean removeSoiltype(SoilType s) {
		return this.soiltype.remove(s);
	}

	/**
	 * Sets the given budget as the Garden's budget.
	 * <p>
	 * Doesn't change if newBudget &lt; 0.
	 * 
	 * @param newBudget the value to set budget to.
	 */
	public void setBudget(double newBudget) {
		if (newBudget >= 0) {
			this.budget = newBudget;
		}
	}

	/**
	 * Returns the number of lep species supported by the plants in the Garden.
	 * 
	 * @return lepCount Number of leps in the Garden.
	 */
	public int getLepCount() {
		return lepCount;
	}

	/**
	 * Sets the number of lep species in the Garden to the given value.
	 * 
	 * @param lepCount Number of leps in the Garden.
	 */
	public void setLepCount(int lepCount) {
		this.lepCount = lepCount;
	}

	/**
	 * Returns the collection of plants placed in the Garden.
	 * 
	 * @return placedPlants Plants placed in the Garden.
	 */
	public Collection<GardenPlant> getPlacedPlants() {
		return placedPlants;
	}

	/**
	 * Sets the HashSet of plants placed in the Garden to the given one.
	 * 
	 * @param currentPlants Plants placed in the Garden.
	 */
	public void setCurrentPlants(HashSet<GardenPlant> currentPlants) {
		this.placedPlants = currentPlants;
	}

	/**
	 * Returns the collection of unique plants that are to be added to the Garden.
	 * 
	 * @return plantsToAdd Plants to be added to the Garden.
	 */
	public Collection<Plant> getPlantsToAdd() {
		return plantsToAdd;
	}

	/**
	 * Sets the HashSet of plants that are to be added to the Garden to the given
	 * one.
	 * 
	 * @param plantsToAdd Plants to be added to the Garden.
	 */
	public void setPlantsToAdd(HashSet<Plant> plantsToAdd) {
		this.plantsToAdd = plantsToAdd;
	}

	/**
	 * Returns the collection of unique leps that are supported by the Garden.
	 * 
	 * @return currentLeps Leps currently in the Garden.
	 */
	public Collection<Lep> getCurrentLeps() {
		return currentLeps;
	}

	/**
	 * Sets the HashSet of unique leps that are in the Garden to the given one.
	 * 
	 * @param currentLeps Leps currently in the Garden.
	 */
	public void setCurrentLeps(HashSet<Lep> currentLeps) {
		this.currentLeps = currentLeps;
	}

	/**
	 * Returns the collection of plants that are composted in the Garden.
	 * 
	 * @return compostPlants The composted plants in the Garden.
	 */
	public Collection<GardenPlant> getCompostPlants() {
		return compostPlants;
	}

	/**
	 * Sets the collection of plants that are composted in the Garden to the given
	 * one.
	 * 
	 * @param compostPlants The composted plants in the Garden.
	 */
	public void setCompostPlants(ArrayList<GardenPlant> compostPlants) {
		this.compostPlants = compostPlants;
	}

	/**
	 * Sets the cost of all plants in the Garden to the given value.
	 * 
	 * @param plantCost The cost of all placed plants in the Garden.
	 */
	public void setPlantCost(double plantCost) {
		this.plantCost = plantCost;
	}

	/**
	 * Sets the total land used in the Garden to the given value, in pixels.
	 * 
	 * @param landUsed The land used in the Garden.
	 */
	public void setLandUsed(double landUsed) {
		this.landUsed = landUsed;
	}

	/**
	 * Sets the total land in the Garden to the given value, in pixels.
	 * 
	 * @param landTotal The total land in the Garden.
	 */
	public void setLandTotal(double landTotal) {
		this.landTotal = landTotal;
	}

	/**
	 * Returns the name of the Garden.
	 * 
	 * @return name The name of the Garden.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the Garden to the given one.
	 * 
	 * @param name The name of the Garden.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns a collection of unique plants placed in the Garden.
	 * 
	 * @return uniquePlants The unique plants in the Garden.
	 */
	public Collection<Plant> getUniquePlants() {
		return uniquePlants;
	}

	/**
	 * Updates the collection of unique plants placed in the Garden.
	 */
	public void updateUniquePlants() {
		uniquePlants = new HashSet<>();
		for (GardenPlant gp : placedPlants) {
			uniquePlants.add(gp.getPlant());
		}
		updateLeps();
	}

	/**
	 * Adds the given plant to the collection of plants to place in the Garden.
	 * 
	 * @param p The plant to add.
	 */
	public void addPlant(Plant p) {
		plantsToAdd.add(p);
	}

	/**
	 * Places the given plant at the given coordinates in the Garden.
	 * 
	 * @param p The plant to place in the Garden.
	 * @param x The x location of the plant.
	 * @param y The y location of the plant.
	 */
	public void addPlacedPlant(Plant p, double x, double y) {
		GardenPlant gp = new GardenPlant(p, x, y);
		placedPlants.add(gp);
		updateUniquePlants();
		updateStats(gp, true);
	}

	/**
	 * Attempts to return the plant at the given location.
	 * <p>
	 * If no plant is found, returns null.
	 * 
	 * @param location The x-y location to find a plant at.
	 * @return p The plant placed at the location.
	 */
	public GardenPlant getGardenPlant(Point2D location) {
		return getGardenPlant(location.getX(), location.getY());
	}

	/**
	 * Attempts to return the plant at the given location.
	 * <p>
	 * If no plant is found, returns null.
	 * 
	 * @param x The x location of the plant
	 * @param y The y location of the plant
	 * @return p The plant placed at the location.
	 */
	public GardenPlant getGardenPlant(double x, double y) {
		for (GardenPlant p : placedPlants) {
			if ((Math.abs(p.getX() - x) <= 9) && (Math.abs(p.getY() - y) <= 9)) {
				return p;
			}
		}
		return null;
	}

	/**
	 * Removes the given placed plant from the Garden.
	 * 
	 * @param gp The plant to remove from the Garden.
	 */
	public void removePlantPlaced(GardenPlant gp) {
		placedPlants.remove(gp);

		compostPlants.add(gp);
		updateUniquePlants();
		updateStats(gp, false);
	}

	/**
	 * Removes the given plant the given number of times from the collection of
	 * plants to place in the Garden.
	 * 
	 * @param p        The plant to add to the Garden.
	 * @param quantity The number of plants to add to the Garden.
	 */
	public void removePlantAdded(Plant p, int quantity) {
		GardenPlant gp = new GardenPlant(p, -1, -1);
		for (int i = 0; i < quantity; i++) {
			if (plantsToAdd.contains(p)) {
				plantsToAdd.remove(p);
			}
		}
	}

	/**
	 * Moves the plant from the start location to the end location.
	 * 
	 * @param start Location to move from.
	 * @param end   Location to move to.
	 * @return If the plant was moved inside the Garden or not.
	 */
	public boolean movePlant(Point2D start, Point2D end) { // check for overlap w/ other plants and proper region
		GardenPlant gp = getGardenPlant(start);
		boolean startInside = insideGarden(start);
		boolean endInside = insideGarden(end);
		gp.setLocation(end);
		updateUniquePlants();
		Plant p = gp.getPlant();
		double landMod = Math.PI * Math.pow((p.getSpread() * scale / 2.0), 2);
		if (startInside && !endInside) { // move from inside to outside
			this.landUsed -= landMod;
		}
		if (!startInside && endInside) {
			this.landUsed += landMod;
		}
		return endInside;
	}

	/**
	 * Updates the number of leps and unique leps in the Garden.
	 */
	public void updateLeps() {
		lepCount = 0;
		currentLeps = new HashSet<>();
		for (Plant p : uniquePlants) {
			currentLeps.addAll(p.getSupportedLeps());
			lepCount += p.getLepCount();
		}
	}

	/**
	 * Updates the plant cost and land used in the garden when adding/removing a
	 * plant.
	 * 
	 * @param gp       The plant to add/remove.
	 * @param isAdding True means the plant is being added; false means it is being
	 *                 removed.
	 */
	public void updateStats(GardenPlant gp, boolean isAdding) {

		Plant p = gp.getPlant();

		double landMod = Math.PI * Math.pow((p.getSpread() * scale / 2.0), 2);

		boolean isInsideBounds = insideGarden(gp.getLocation());

		if (isAdding) {
			this.plantCost += p.getCost();

		} else {
			this.plantCost -= p.getCost();

		}

		if (isInsideBounds && isAdding) {
			this.landUsed += landMod;
		} else if (isInsideBounds && !isAdding) {
			this.landUsed -= landMod;
		}
	}

	/**
	 * Returns the list of points that make up the outline of the Garden.
	 * <p>
	 * Points are in the order of x0, y0, x1, y1, ..., xn, yn
	 * 
	 * @return points The points that outline the Garden.
	 */
	public ArrayList<Double> getPoints() {
		return points;
	}

	/**
	 * Sets the Garden points to the given values.
	 * <p>
	 * Additionally updates the area of the Garden based on the new points.
	 * 
	 * @param points The points that outline the Garden.
	 */
	public void setPoints(ObservableList<Double> points) {
		this.points = new ArrayList<Double>();
		this.points.addAll(points);
		updateArea();
		if (widthFT != -1) {
			scaleFromFT(widthFT);
		}
	}

	/**
	 * Sets the Garden points to the given values.
	 * <p>
	 * Additionally updates the area of the Garden based on the new points.
	 * 
	 * @param points The points that outline the Garden.
	 */
	public void setPoints(ArrayList<Double> points) {
		this.points = points;
		updateArea();
		if (widthFT != -1) {
			scaleFromFT(widthFT);
		}
	}

	/**
	 * Checks whether or not the given point is inside the Garden.
	 * 
	 * @param point x-y location
	 * @return Whether or not the point is inside the Garden.
	 */
	public boolean insideGarden(Point2D point) {
		return insideGarden(point.getX(), point.getY());
	}

	/**
	 * Checks whether or not the given point is inside the Garden.
	 * 
	 * @param x x location of the point.
	 * @param y y location of the point.
	 * @return Whether or not the point is inside the Garden.
	 */
	public boolean insideGarden(double x, double y) {
		Polygon shape = new Polygon();
		shape.getPoints().addAll(points);
		return shape.contains(x, y);
	}

	/**
	 * Updates the pixel area of the Garden.
	 */
	public void updateArea() {
		landTotal = shoelaceArea(points);
	}

	/**
	 * Currently not implemented.
	 */
	public static void getGardenInfo() { // read in a file, makes garden from that file

	}

	/**
	 * Returns the string representation of the Garden.
	 * 
	 * @return The string representation of the Garden.
	 */
	public String toString() {

		return "Garden name: " + name + "\nPlaced Plants: " + placedPlants.toString() + "\n Plants to Add: "
				+ plantsToAdd.toString() + "\n Current Leps: " + currentLeps.toString() + "\n Compost Plants:"
				+ compostPlants.toString() + "\n Budget: " + budget + "\n Plants cost: " + plantCost + "\n Land Used: "
				+ landUsed + "\n Land Total: " + landTotal + "\n Lep count: " + lepCount;
	}

	/**
	 * Calculates and returns the area inside the polygon made from the points.
	 * 
	 * @param v List of points that make up the outline of a polygon.
	 * @return The area inside the points.
	 */
	private double shoelaceArea(ArrayList<Double> v) {
		int n = v.size();
		double a = 0.0;
		for (int i = 0; i < n - 2; i += 2) {
			a += v.get(i) * v.get(i + 3) - v.get(i + 1) * v.get(i + 2);
		}
		return Math.abs(a + v.get(n - 2) * v.get(1) - v.get(0) * v.get(n - 1)) / 2.0;
	}

	/**
	 * Checks for overlap between the given plant at the given location and any
	 * other plant in the Garden.
	 * 
	 * @param movingPlant The plant to check for overlap.
	 * @param x           The x location of the plant.
	 * @param y           The y location of the plant.
	 * @return Whether or not the plant's spread overlaps any other plant's spread.
	 */
	boolean isOverlap(GardenPlant movingPlant, double x, double y) {
		for (GardenPlant gp : placedPlants) {
			if (!movingPlant.equals(gp)) {
				double xDiff = x - gp.getX();
				double yDiff = y - gp.getY();

				double centerDistance = Math.sqrt(xDiff * xDiff + yDiff * yDiff);
				double radiusSum = movingPlant.getPlant().getSpread() * scale / 2
						+ gp.getPlant().getSpread() * scale / 2;

				if (centerDistance < radiusSum) {
					return true;
				}
			}

		}
		return false;
	}

	/**
	 * Checks for overlap between the given plant at the given location and any
	 * other plant in the Garden.
	 * 
	 * @param movingPlant The plant to check for overlap.
	 * @param x           The x location of the plant.
	 * @param y           The y location of the plant.
	 * @return Whether or not the plant's spread overlaps any other plant's spread.
	 */
	boolean isOverlap(Plant movingPlant, double x, double y) {
		for (GardenPlant gp : placedPlants) {

			double xDiff = x - gp.getX();
			double yDiff = y - gp.getY();

			double centerDistance = Math.sqrt(xDiff * xDiff + yDiff * yDiff);
			double radiusSum = movingPlant.getSpread() * scale / 2 + gp.getPlant().getSpread() * scale / 2;

			if (centerDistance < radiusSum) {
				return true;
			}

		}
		return false;
	}

	/**
	 * Returns whether or not this Garden is the same as th other Garden.
	 * <p>
	 * only used for tests with assertEquals
	 * </p>
	 * 
	 * @return true if they are the same; false otherwise.
	 */
	public boolean equals(Object other) {
		if (other instanceof Garden) {
			Garden g = (Garden) other;
			boolean equal = true;
			if (placedPlants != null) {
				equal &= placedPlants.equals(g.placedPlants);
			} else {
				equal &= g.placedPlants == null;
			}
			if (uniquePlants != null) {
				equal &= uniquePlants.equals(g.uniquePlants);
			} else {
				equal &= g.uniquePlants == null;
			}
			if (plantsToAdd != null) {
				equal &= plantsToAdd.equals(g.plantsToAdd);
			} else {
				equal &= g.plantsToAdd == null;
			}
			if (currentLeps != null) {
				equal &= currentLeps.equals(g.currentLeps);
			} else {
				equal &= g.currentLeps == null;
			}
			if (compostPlants != null) {
				equal &= compostPlants.equals(g.compostPlants);
			} else {
				equal &= g.compostPlants == null;
			}
			if (points != null) {
				equal &= points.equals(g.points);
			} else {
				equal &= g.points == null;
			}
			if (name != null) {
				equal &= name.equals(g.name);
			} else {
				equal &= g.name == null;
			}

			equal &= plantCost == g.plantCost;
			equal &= budget == g.budget;
			equal &= landUsed == g.landUsed;
			equal &= landTotal == g.landTotal;
			equal &= scale == g.scale;
			equal &= widthFT == g.widthFT;

			if (moisture != null) {
				equal &= moisture.equals(g.moisture);
			} else {
				equal &= g.moisture == null;
			}
			if (sunlight != null) {
				equal &= sunlight.equals(g.sunlight);
			} else {
				equal &= g.sunlight == null;
			}
			if (soiltype != null) {
				equal &= soiltype.equals(g.soiltype);
			} else {
				equal &= g.soiltype == null;
			}

			return equal;
		}
		return false;
	}

}