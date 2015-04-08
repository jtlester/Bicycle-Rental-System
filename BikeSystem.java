// system imports
import javax.swing.UIManager;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;
import java.util.ResourceBundle;
import java.io.FileOutputStream;
import java.io.File;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
import java.awt.Font;

// project imports
import event.Event;
import event.EventLog;
import common.PropertyFile;
import model.*;
import userinterface.*;

public class BikeSystem {
	private Peon peon;

	/** Main frame of the application */
	private MainFrame mainFrame;

	// constructor for this class, the main application object
	//----------------------------------------------------------
	public BikeSystem(String[] args) {
		System.out.println("\nBike Rental System Version 1.01");
		System.out.println("Copyright 2015 Josh Lester, Danielle Drzewiecki, Evan Lewis, Zachary Beardsley, Jeremy LaPlant");

		// figure out the desired look and feel
		String lookNFeel = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";

		// See if you can set the look and feel requested, if not indicate error
		try {
			UIManager.setLookAndFeel(lookNFeel);
		} catch(Exception e) {
			lookNFeel = UIManager.getCrossPlatformLookAndFeelClassName();
			try {
				UIManager.setLookAndFeel(lookNFeel);
			}
			catch(Exception f) {
				System.err.println("Librarian.Librarian - Unable to set look and feel");
				new Event(Event.getLeafLevelClassName(this), "Librarian.<init>", "Unable to set look and feel", Event.ERROR);
				f.printStackTrace();
			}
		}

		// Create the top-level container (main frame) and add contents to it.
		mainFrame = MainFrame.getInstance("Brockport Bike Rental System v1.01");

		// put in icon for window border and toolbar
		Toolkit myToolkit = Toolkit.getDefaultToolkit();

		/*   File iconFile = new File("ATM.jpg");

	    if (iconFile.exists() == true)
	    {
	    	Image myImage = myToolkit.getImage("ATM.jpg"); // TO CHANGE!!
	    	mainFrame.setIconImage(myImage);
	 	}
		 */
		// Finish setting up the frame, and show it.
		mainFrame.addWindowListener(new WindowAdapter() {
			// event handler for window close events
			public void windowClosing(WindowEvent event) {
				System.exit(0);
			}
		});

		try {
			peon = new Peon();
		} catch(Exception ex) {
			System.err.println("ERROR: Could not create peon!");
			new Event(Event.getLeafLevelClassName(this), "Peon.<init>", "Unable to create Peon object", Event.ERROR);
			ex.printStackTrace();
		}

		mainFrame.pack();
		WindowPosition.placeCenter(mainFrame);
		mainFrame.setVisible(true);
	}

	/** The "main" entry point for the application. Carries out actions to
	 * set up the application
	 */
	public static void main(String[] args) {
		try {
			new BikeSystem(args);
		}
		catch(Exception e) {
			new Event("TestAssignment", "TestAssignment.main", "Unhandled Exception: " + e, Event.FATAL);
			e.printStackTrace();
		}
	}

}
