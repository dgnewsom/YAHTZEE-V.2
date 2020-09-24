package dice;

import javafx.scene.paint.Color;

public enum DieColour {
	
	WHITE("White", Color.WHITE),
	BLACK("Black", Color.BLACK),
	RED("Red", Color.rgb(236,28,36)),
	GREEN("Green", Color.rgb(0,255,0)),
	BLUE("Blue", Color.rgb(63,72,204)),
	ORANGE("Orange", Color.rgb(255,130,44)),
	PINK("Pink", Color.rgb(255,90,143)),
	YELLOW("Yellow", Color.rgb(255,242,0));
	
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
