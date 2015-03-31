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

public class BikeSystem
{
	private Peon myPeon;		// the main behavior for the application

	/** Main frame of the application */
	private MainFrame mainFrame;


	// constructor for this class, the main application object
	//----------------------------------------------------------
	public BikeSystem(String[] args)
	{
		System.out.println("\nBike Rental System Version 1.00");
		//System.out.println("Copyright 2015 Josh Lester, Danielle Drzewiecki, Evan Lewis");

		// figure out the desired look and feel
		String LookNFeel = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";

		// See if you can set the look and feel requested, if not indicate error
		try
		{
			UIManager.setLookAndFeel(LookNFeel);
		}
		catch(Exception e)
		{
			LookNFeel = UIManager.getCrossPlatformLookAndFeelClassName();
			try
			{
				UIManager.setLookAndFeel(LookNFeel);
			}
			catch(Exception f)
			{
				System.err.println("Librarian.Librarian - Unable to set look and feel");
				new Event(Event.getLeafLevelClassName(this), "Librarian.<init>", "Unable to set look and feel", Event.ERROR);
				f.printStackTrace();
			}
		}


        // Create the top-level container (main frame) and add contents to it.
		mainFrame = MainFrame.getInstance("Brockport Bike Rental System v1.0");

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
        mainFrame.addWindowListener(new WindowAdapter()
		{
			// event handler for window close events
            public void windowClosing(WindowEvent event)
			{
				System.exit(0);
        	}
        });

		try
		{
			myPeon = new Peon();
		}
		catch(Exception exc)
		{
			System.err.println("Assignment2Tester.assignment - could not create Librarian!");
			new Event(Event.getLeafLevelClassName(this), "ATM.<init>", "Unable to create Librarian object", Event.ERROR);
			exc.printStackTrace();
		}

  	    mainFrame.pack();

  	    WindowPosition.placeCenter(mainFrame);

        mainFrame.setVisible(true);
    }


	/** The "main" entry point for the application. Carries out actions to
	 * set up the application
	 */
	//----------------------------------------------------------
    public static void main(String[] args)
	{

		// crank up an instance of this object
		try
		{
			new BikeSystem(args);
		}
		catch(Exception e)
		{
			new Event("TestAssignment", "TestAssignment.main", "Unhandled Exception: " + e, Event.FATAL);
			e.printStackTrace();
		}
	}


}
