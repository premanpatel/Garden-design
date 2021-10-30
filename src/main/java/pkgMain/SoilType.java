package pkgMain;

public enum SoilType {

	CLAY("Clay"), SAND("Sand"), LOAM("Loam");

	private String name = null;

	/**
	 * Constructor for Soiltype enum.
	 */
	private SoilType(String s) {
		name = s;
	}

	/**
	 * Returns the name of SoilType.
	 * 
	 * @return name name of SoilType.
	 */
	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return name;
	}

}