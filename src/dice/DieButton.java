package dice;

import javafx.scene.input.MouseEvent;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class DieButton extends Button {

	private Die die;
	
	public DieButton(Die die) {
		this.die = die;
	}
	
	public void setImage() {
		String imagePathString = "images/dice/";
		imagePathString += "white/";
		if(die.isHeld()) {
			imagePathString += "held/";
		}
		else {
			imagePathString += "unheld/";
		}
		imagePathString += die.getCurrentValue() + ".png";
		
		setGraphic(new ImageView(imagePathString));
		
	}
	
	public void Click(MouseEvent e) {
		die.toggleHeld();
	}

	public Die getDie() {
		return die;
	}
}
