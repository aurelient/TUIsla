package interactivetui;

import java.util.ArrayList;
import processing.core.*;
import org.json.*;

public class Component {
	String name;
	String imagePath;
	
	PImage image;
	int x, y; // Position
	ArrayList<Rfid> actionList = new ArrayList<Rfid>();  // Create an empty ArrayList

	Component(String name, int xpos, int ypos) {
	}

	Component() {
	}
	
	Component(JSONObject json) { 
		name = json.getString("name");
		imagePath = json.getString("image");
		JSONArray actions = json.getJSONArray("actions");
		for (int i = 0; i < actions.length(); i++) {
			JSONObject childJSONObject = actions.getJSONObject(i);
			String rfidName = childJSONObject.getString("name");
			String rfidId = childJSONObject.getString("id");
			String rfidAction = childJSONObject.getString("action");
			addRFID(new Rfid(rfidAction, rfidId, rfidName));
		}
	}


	void addRFID(Rfid r) {
		actionList.add(r);
	}

	void backup() {
		// TODO:
		// Load config file, 
		// Look for name in file
		// Remove old actions
		// Add new actions
		// Save file
		// TODO later: handle position
	}
}

