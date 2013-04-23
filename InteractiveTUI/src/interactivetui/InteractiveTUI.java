package interactivetui;

import processing.core.PApplet;
import processing.serial.*;
import controlP5.ControlEvent;
import controlP5.ControlP5;


public class InteractiveTUI extends PApplet {

	/******************
	 * Misc Variables *
	 ******************/
	ComponentList componentList;
	int canvasWidth  = 800;
	int canvasHeight = 400;

	int shift = 100;
	ControlP5 controlP5;

	boolean ready = false;
	/***************************
	 * Communication Variables *
	 ***************************/
	Serial arduino;

	/** */
	private static final long serialVersionUID = 1206534198805285720L;

	//#############################################################################################################################
	// SETUP
	//#############################################################################################################################
	public void setup()	{
		size(canvasWidth, canvasHeight);
		frameRate(30);
		controlP5 = new ControlP5(this);

		// I know that the first port in the serial list is always my Arduino, so I open Serial.list()[0].
		arduino = new Serial(this, Serial.list()[0], 19200); //has to be 19200, because that's also the baud rate of the SM130 RFID Reader module
		arduino.bufferUntil('\n');
		
		componentList = new ComponentList(this);

		// The events from controlP5 are triggered once when created 
		// we thus wait until they are all created before activating 
		// the event listener controlEvent below
		ready = true;
	}



	//#############################################################################################################################
	// DRAW
	//#############################################################################################################################
	public void draw()
	{
		background(112);
		//arduino.bufferUntil('\n'); // don't generate a serialEvent() unless you get a newline character. Rest happens in serialEvent()
		String s;
		if (arduino.available() > 0) {
			println("available");
			s = arduino.readStringUntil('\n');
			println(s);
		}
	}

	
	//#############################################################################################################################
	// SerialEvent
	//#############################################################################################################################
	public void serialEvent (Serial receiving) {  
		println("serialEvent");
		String inString = receiving.readStringUntil('\n');// get the ASCII string:
		if (inString != null) {
			inString = trim(inString);// trim off any whitespace:
			println("SerialEvent happened: "+inString);
			// Go through the list of components
			for (Component component:componentList) {
				// Go through the list of RFIDs for each component
				for (Rfid action:component.actionList) {
					if (action.id.equals(inString)) {
						new ComponentUI(this,controlP5,component,50,50);
						// Trigger the action
						open(action.action);
					}
					println("");
				}
			}
		}
	}

	public void controlEvent(ControlEvent theEvent) {
		println("controlEvent");
		
		if (ready) {
			if(theEvent.getController().getValue()==0) {
				println("isButton " + theEvent.getController().getName() + " " + theEvent.getController().getValueLabel());
				ActionPicker ap = new ActionPicker(this, controlP5, mouseX, mouseY);
			} else {
				if (theEvent.getController().getId() == -1) {
					println(theEvent.controller().name()+" = "+theEvent.value());  
					println("TYPE " + theEvent.controller().getInfo());
					println(theEvent.getName());
					//PApplet.open(theEvent.getName());
				}
			}
		}
		//		if (theEvent.isFrom(cp5.getController("n1"))) {
		//			PApplet.println("this event was triggered by Controller n1");
		//		}

		//String eventSourceName = theEvent.getController().getName();		
	}


	public static void main(String _args[]) {
		PApplet.main(new String[] { interactivetui.InteractiveTUI.class.getName() });
	}
	
		
}
