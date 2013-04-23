package interactivetui;

public class Rfid {
	String action = "";
	String id = "";
	String name = "";

	Rfid (String actionPath, String identifier, String title) {
		action = actionPath;
		id = identifier;
		name = title;
	}

	public String toJson(){
		//{"id":"f128de34", "action":"actions/Volume/0.app", "name":"0%"},
		String jsonString = "{";
		jsonString += "\"id\":\"" + id + "\", ";
		jsonString += "\"action\":\"" + action + "\", ";
		jsonString += "\"name\":\"" + name + "\", ";
		jsonString += "},";
		return jsonString;

	}
}

