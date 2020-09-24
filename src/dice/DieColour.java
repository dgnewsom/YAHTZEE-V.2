package dice;

import javafx.scene.paint.Color;

public enum DieColour {
	
	BLACK("Black", Color.BLACK),
	BLUE("Blue", Color.rgb(63,72,204)),
	GREEN("Green", Color.rgb(0,255,0)),
	ORANGE("Orange", Color.rgb(255,130,44)),
	PINK("Pink", Color.PINK),
	RED("Red", Color.RED),
	WHITE("White", Color.WHITE),
	YELLOW("Yellow", Color.YELLOW);
	
	private String descriptionName;
	private Color colour;
	
	
	private DieColour(String descriptionName, Color colour) {
		this.descriptionName = descriptionName;
		this.colour = colour;
	}
	
	public String getDescriptionName() {
		return descriptionName;
	}
	
	public Color getColour() {
		return colour;
	}

	@Override
	public String toString() {
		return "";
	}
}
