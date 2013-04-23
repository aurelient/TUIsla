package interactivetui;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import processing.core.PApplet;

public class ComponentList extends ArrayList<Component> {
	BufferedReader configReader;
	String configFilePath = "../data/config.json";
	JSONObject myJsonObject;
	
	/** */
	private static final long serialVersionUID = 1L;

	public ComponentList(PApplet p) {
		// Load config file
		String[] config = p.loadStrings(configFilePath);
		String data = PApplet.join(config, "");
		myJsonObject = new JSONObject(data);
		
		JSONArray json = myJsonObject.getJSONArray("components");
		for (int i = 0; i < json.length(); i++) {
			JSONObject childJSONObject = json.getJSONObject(i);
			this.add(new Component(childJSONObject));
		}
	}
	
	public Component getComponentById (String id) {
		for (Component component:this) {
			Rfid rfid = component.getRfid(id);
			if (rfid != null) {
				return component;
			}
		}
		return null;
	}
	
	public void setComponentAction(String id, String action) {
		for (Component component:this) {
			Rfid rfid = component.getRfid(id);
			if (rfid != null) {
				rfid.action = action;
			}
		}
	}


	
	public void saveJsonToFile() {
		ObjectOutputStream outputStream = null;
		try{
			outputStream = new ObjectOutputStream(new FileOutputStream(configFilePath));
			outputStream.writeObject(myJsonObject.toString());
			outputStream.flush();
			outputStream.close();
		} catch (Exception e) {
			System.err.println("Error: " + e);
		}
	}
}
