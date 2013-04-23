package interactivetui;

import processing.core.PApplet;
import processing.core.PImage;
import controlP5.ControlP5;

public class ComponentUI {

	int xPos=0;
	int yPos=0;
	Component component;
	PApplet papplet;
	PImage img = null; 
	ControlP5 cp5;

	public ComponentUI(PApplet p, ControlP5 controlP5, Component c) {
		componentUIBuilder(p,controlP5,c,-50,-50);
	}

	public ComponentUI(PApplet p, ControlP5 controlP5, Component c, int x, int y) {
		componentUIBuilder(p,controlP5,c,x,y);
	}

	private void componentUIBuilder(PApplet p, ControlP5 controlP5, Component c, int x, int y) {
		component=c;
		papplet=p;
		cp5 = controlP5;

		// Load and show image
		if (component.imagePath != "") {
			img = papplet.loadImage(component.imagePath);
			xPos = x;
			yPos = y;
			// Show Rfid buttons
			createUIelements();
		}
	}

	public void setPosition(int x, int y) {
		xPos = x;
		yPos = y;
	}

	public int getHeight(){
		if (img != null) {
			return img.height;
		} else {
			return 0;
		}
	}

	public int getWidth(){
		if (img != null) {
			return img.width;
		} else {
			return 0;
		}
	}

	private void createUIelements(){
		int numberOfRfid = component.actionList.size();
		int w = getWidth();
		int distance = (int) w / numberOfRfid;

		// Create image button
		cp5.addButton(component.name)
		.setValue(0)
		.setPosition(xPos, yPos)
		.setSize(getWidth(),getHeight())
		.setImage(img);

		papplet.image(img, xPos, yPos);

		int num=0;
		for (Rfid r:component.actionList) {			  
			if (r.name != "") {
				cp5.addButton(r.name)
				.setValue(0)
				.setPosition(xPos + w , yPos+ distance*num)
				.setSize(100,10)
				.setValueLabel(r.id);
				;
			} else {
				cp5.addButton("To define,"+r.id)
				.setValue(0)
				.setPosition(xPos + w , yPos+ distance*num)
				.setSize(100,10)
				;
			}
			num++;
		}
	}

}
