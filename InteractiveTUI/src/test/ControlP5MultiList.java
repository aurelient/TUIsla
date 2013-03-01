package test;
/**
 * ControlP5 MultiList
 * by andreas schlegel, 2009
 */
import processing.core.PApplet;
import controlP5.ControlEvent;
import controlP5.ControlP5;
import controlP5.MultiList;
import controlP5.MultiListButton;

public class ControlP5MultiList extends PApplet {
	ControlP5 controlP5;
	MultiList l;

	public void setup() {
		size(400, 400);
		frameRate(30);
		this.controlP5 = new ControlP5(this);
		// add a multiList to controlP5.
		// elements of the list have default dimensions
		// here, a width of 100 and a height of 12
		this.l = this.controlP5.addMultiList("myList", 0, 10, 100, 12);
		// create a multiListButton which we will use to
		// add new buttons to the multilist
		MultiListButton b;
		b = this.l.add("level1", 1);
		// add items to a sublist of button "level1"
		b.add("level11", 11).setLabel("level1 item1");
		b.add("level12", 12).setLabel("level1 item2");
		b = this.l.add("level2", 2);
		int cnt = 100;
		// add some more sublists.
		for (int i = 0; i < 10; i++) {
			final MultiListButton c = b.add("level2" + (i + 1), 20 + i + 1);
			c.setLabel("level2 item" + (i + 1));
			c.setColorBackground(color(64 + (18 * i), 0, 0));
			if (i == 4) {
				// changing the width and the height of a button
				// will be inherited by its sublists.
				c.setWidth(100);
				c.setHeight(20);
			}
			cnt++;
			if (i == 4) {
				for (int j = 0; j < 10; j++) {
					cnt++;
					MultiListButton d;
					d = c.add("level2" + i + "" + j, 250 + j + 1);
					d.setLabel("level2 item" + (i + 1) + " " + "item" + (j + 1));
					d.setColorBackground(color(64 + (18 * j),
							(64 + (18 * j)) / 2, 0));
					d.setId(cnt);
					d.setWidth(200);
				}
			}
		}
		final MultiListButton cc = (MultiListButton) this.controlP5
				.controller("level21");
		cc.setHeight(40);
	}

	public void controlEvent(final ControlEvent theEvent) {
		println(theEvent.controller().name() + " = " + theEvent.value());
		// uncomment the line below to remove a multilist item when clicked.
		// theEvent.controller().remove();
	}

	public void draw() {
		background(0);
	}

	public void keyPressed() {
		if (this.controlP5.controller("level23") != null) {
			println("removing multilist button level23.");
			this.controlP5.controller("level23").remove();
		}
	}

	static public void main(String args[]) {
		PApplet.main(new String[] { "--bgcolor=#F0F0F0", "ControlxP5MultiList" });
	}
}