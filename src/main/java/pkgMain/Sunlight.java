package pkgMain;

public enum Sunlight {

	FULLSUN("Full Sun"), PARTSUN("Part Sun"), SHADE("Shade");

	private String name = null;

	/**
	 * Constructor for Sunlight enum.
	 */
	private Sunlight(String s) {
		name = s;
	}

	/**
	 * Returns the name of Sunlight.
	 * 
	 * @return name name of Sunlight.
	 */
	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return name;
	}

}