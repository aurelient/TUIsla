package interactivetui;

import java.io.BufferedReader;
import java.util.ArrayList;

import org.json.*;

import controlP5.ControlEvent;
import controlP5.ControlP5;
import processing.core.PApplet;
import processing.serial.Serial;


public class InteractiveTUI extends PApplet {

	/******************
	 * Misc Variables *
	 ******************/
	BufferedReader configReader;
	String loadedFile;
	JSONArray obj_list;
	ArrayList<Component> componentList = new ArrayList<Component>();  // Create an empty ArrayList

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
		//arduino = new Serial(this, Serial.list()[0], 19200); //has to be 19200, because that's also the baud rate of the SM130 RFID Reader module


		// Load config file
		String[] config = loadStrings("../data/config.json");
		String data = join(config, "");
		JSONObject myJsonObject = new JSONObject(data);
		JSONArray json = myJsonObject.getJSONArray("components");
		println("a "+json.length());
		for (int i = 0; i < json.length(); i++) {  
			JSONObject childJSONObject = json.getJSONObject(i);
			componentList.add(new Component(childJSONObject));
		}
		println(componentList);

		// Setup components
		int componentNumber = 0;
		for (Component component:componentList) {
			ComponentUI cui = new ComponentUI(this,controlP5,component,20 + 200*componentNumber, 20);
			//cui.setPosition(20 + 200*componentNumber, height-cui.getHeight()/2);
			componentNumber++;
		}

		// The events from controlP5 are trigger once when created 
		// we thus wait until they are all created before activating 
		// the event listener controlEvent below
		ready = true;
	}



	//#############################################################################################################################
	// DRAW
	//#############################################################################################################################
	public void draw()
	{
		background(0);
		//arduino.bufferUntil('\n'); // don't generate a serialEvent() unless you get a newline character. Rest happens in serialEvent()

	}

	//#############################################################################################################################
	// SerialEvent
	//#############################################################################################################################
	void serialEvent (Serial receiving) {  
		String inString = receiving.readStringUntil('\n');// get the ASCII string:
		if (inString != null) {
			inString = trim(inString);// trim off any whitespace:
			println("SerialEvent happened: "+inString);
			// Go through the list of components
			for (Component component:componentList) {
				// Go through the list of RFIDs for each component
				for (Rfid action:component.actionList) {
					if (action.id == inString) {
						// Trigger the action
						open(action.action);
					}
				}
			}
		}
	}


	public void controlEvent(ControlEvent theEvent) {
		//		println(theEvent.controller().name()+" = "+theEvent.value());  
		//
		//		println("TYPE " + theEvent.controller().getInfo());

		if (ready) {
			if(theEvent.getController().getValue()==0) {
				println("isButton");
				ActionPicker ap = new ActionPicker(this, controlP5, mouseX, mouseY);
			} else {
				if (theEvent.getController().getId() == -1) {
					PApplet.open(theEvent.getName());
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
