package interactivetui;

import java.util.ArrayList;
import processing.core.*;
import org.json.*;

public class Component {
	String name;
	String imagePath;
	
	PImage image;
	ArrayList<Rfid> actionList = new ArrayList<Rfid>();  // Create an empty ArrayList

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

	public Rfid getRfid(String id) {
		for (Rfid rfid:actionList) {
			if (rfid.id == id) {
				return rfid;
			}
		}
		return null;
	}
	
	public String toJsonString() {
		String jsonString = "";
		jsonString += "\"name\":\"" + this.name + "\",\n";
		jsonString += "\"image\":\"" + this.image + "\",\n";
		jsonString += "\"actions\": [";
		for (Rfid rfid:actionList) {
			jsonString += rfid.toJson() + "\n";
		}
		jsonString += "]},\n";
		
		return jsonString;
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

