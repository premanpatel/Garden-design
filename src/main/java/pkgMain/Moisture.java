package pkgMain;

/**
 * The Moisture enum represents different moisture levels: wet, dry, and
 * average.
 * 
 * @author rbila
 *
 */
public enum Moisture {

	WET("Wet"), DRY("Dry"), AVERAGE("Average");

	private String name = null;

	/**
	 * Constructor for the moisture enum.
	 * 
	 * @param s The name of the moisture level.
	 */
	private Moisture(String s) {
		name = s;
	}

	/**
	 * Returns the name of the moisture level.
	 * 
	 * @return The name of the moisture level.
	 */
	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return name;
	}
}