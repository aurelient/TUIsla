package interactivetui;
import processing.core.PApplet;
import controlP5.*;

public class DoAction implements CallbackListener{

	@Override
	public void controlEvent(CallbackEvent theEvent) {
		// TODO Auto-generated method stub
		PApplet.println(theEvent.getAction());
	}

}
