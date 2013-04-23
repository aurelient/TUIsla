package interactivetui;
import java.io.File;
import java.util.ArrayList;

import controlP5.*;
import processing.core.*;

public class ActionPicker {
	ControlP5 controlP5;
	MultiList l;
	PApplet parent;
	DoAction doa = new DoAction();

	public ActionPicker(PApplet p, ControlP5 c, int mouseX, int mouseY) {
		parent=p;
		// add a multiList to controlP5.
		// elements of the list have default dimensions
		// here, a width of 100 and a height of 12
		controlP5 = c;
		l = controlP5.addMultiList("myList",mouseX,mouseY,100,12);

		File[] folders = listFiles("actions/");
		//PApplet.println("Folders: " + folders[0] + " " + folders.length);
		populateMenu(folders, l);			
	}



	// This function returns all the files in a directory as an array of Strings  
	public String[] listFileNames(String dir) {
		File file = new File(dir);
		//PApplet.println(file.getAbsoluteFile());
		if (file.isDirectory()) {
			String names[] = file.list();
			return names;
		} else {
			// If it's not a directory
			return null;
		}
	}

	// This function returns all the files in a directory as an array of File objects
	// This is useful if you want more info about the file
	public File[] listFiles(String dir) {
		File file = new File(dir);
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			return files;
		} else {
			// If it's not a directory
			return null;
		}
	}

	// Function to get a list of all files in a directory and all subdirectories
	public ArrayList<File> listFilesRecursive(String dir) {
		ArrayList<File> fileList = new ArrayList<File>(); 
		recurseDir(fileList,dir);
		return fileList;
	}

	// Recursive function to traverse subdirectories
	public void recurseDir(ArrayList<File> a, String dir) {
		File file = new File(dir);
		if (file.isDirectory()) {
			// If you want to include directories in the list
			a.add(file);  
			File[] subfiles = file.listFiles();
			for (int i = 0; i < subfiles.length; i++) {
				// Call this function on all files in this directory
				recurseDir(a,subfiles[i].getAbsolutePath());
			}
		} else {
			// we ignore hidden files
			if (file.getName().charAt(0) != '.') {
				a.add(file);
			}
		}
	}

	// Recursive function to traverse subdirectories
	public void populateMenu(File[] folders, MultiList list){
		for (int i=0; i<folders.length;i++) {
			MultiListButton b;
			File f = folders[i];
			b = l.add(f.getName(),i);
			
			File[] subfiles = f.listFiles();
			for (int j = 0; j < subfiles.length; j++) {
				recursivePopulateMenu(subfiles[j], b, j);
			}
		}


	}

	public void recursivePopulateMenu(File file, MultiListButton menu, int value) {
		if (file.isDirectory()) {
			if (!file.getName().endsWith(".app")) {
				MultiListButton subMenu = menu.add(file.getName(),250);
				File[] subfiles = file.listFiles();
				for (int i = 0; i < subfiles.length; i++) {
					//PApplet.println("rec " + i + " " + subfiles[i].getName());
					// Call this function on all files in this directory
					recursivePopulateMenu(subfiles[i],subMenu,10*value+i);
				} 
			}
			else {
				menu.add(file.getAbsolutePath(), 10+value).setLabel(file.getName());
				//menu.addCallback(doa);
			}
		} else {
			// we ignore hidden files
			//			if (file.getName().charAt(0) != '.') {
			//			}
		}

	}

}
