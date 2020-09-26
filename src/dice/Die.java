package dice;
import java.util.Random;

public class Die {

	private int currentValue;
	private boolean isHeld;
	private Random random = new Random();
	
	public Die() {
		isHeld = false;
		this.RollDie();
	}
	
	public Die(int i) {
		isHeld = false;
		currentValue = i;
	}

	public void RollDie() {
		if(!isHeld) {
			currentValue = random.nextInt(6)+1;
		}
	}

	public void toggleHeld() {
		isHeld = !isHeld;
	}

	public int getCurrentValue() {
		return currentValue;
	}
	
	public void setHeld(boolean isHeld) {
		this.isHeld = isHeld;
	}

	public boolean isHeld() {
		return isHeld;
	}
	
	public void Unhold() {
		isHeld = false;
	}
}
