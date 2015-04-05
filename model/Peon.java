// specify the package
package model;

// system imports
import java.util.Hashtable;
import java.util.Properties;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;

// project imports
import impresario.IModel;
import impresario.ISlideShow;
import impresario.IView;
import impresario.ModelRegistry;

import exception.InvalidPrimaryKeyException;
import exception.PasswordMismatchException;
import event.Event;
import userinterface.*;


public class Peon
{
	public JFrame myFrame;
	
	public Peon myModel;
	public MainMenuView mainMenuView;
	public WorkerView workerView;
	public LoginView loginView;
	
	
	//-----------------------------------------------------------------
	public Peon()
	{
		myFrame = MainFrame.getInstance();
		createAndShowLoginView();
	}

	public void authenticateLogin(Properties props) {
		//TODO actually authenticate info
		createAndShowMainMenuView();
	}

	//-----------------------------------------------------------------
	public void createNewWorker()
	{
		createAndShowWorkerView();
	}
	
	//------------------------------------------------------------------
	public void processWorkerData(Properties workerProperties)
	{
		Worker newWorker = new Worker(workerProperties);
		newWorker.update();
		
		workerView.displayMessage(
									"Worker with name: " + workerProperties.getProperty("firstName") +
									" saved successfully."
								);
	}
	
	//------------------------------------------------------------------
	public void workerDataDone()
	{
		// Don't know what this does
		createAndShowMainMenuView();
	}
	
	//------------------------------------------------------------------
	public void exitSystem()
	{
		System.exit(0);
	}
	
	//------------------------------------------------------------------
	public void createAndShowLoginView()
	{
		if(loginView == null)
		{
			loginView = new LoginView(this);
			myFrame.getContentPane().add(loginView);
			myFrame.pack();
		}
		else
		{
			swapToView(loginView);
		}
	}

	//------------------------------------------------------------------
	public void createAndShowMainMenuView()
	{
		if(mainMenuView == null)
		{
			mainMenuView = new MainMenuView(this);
			myFrame.getContentPane().add(mainMenuView);
			myFrame.pack();
		}
		else
		{
			swapToView(mainMenuView);
		}
	}
	
	//------------------------------------------------------------------
	public void createAndShowWorkerView()
	{		
		workerView = new WorkerView(this);
		myFrame.getContentPane().add(workerView); 
		myFrame.pack();

		swapToView(workerView);	
	}
	
	//------------------------------------------------------------------
	public void swapToView(JPanel otherView)
	{
		if (otherView == null)
		{
			new Event(Event.getLeafLevelClassName(this), "swapToView",
				"Missing view for display ", Event.ERROR);
			return;
		}

		if (otherView instanceof JPanel)
		{
			swapToPanelView((JPanel)otherView);
		}//end of SwapToView
		else
		{
			new Event(Event.getLeafLevelClassName(this), "swapToView",
				"Non-displayable view object sent ", Event.ERROR);
		}
	}
	
	
	//----------------------------------------------------------------------------
	protected void swapToPanelView(JPanel otherView)
	{
		JPanel currentView = (JPanel)myFrame.getContentPane().getComponent(0);
		// and remove it
		myFrame.getContentPane().remove(currentView);
		// add our view
		myFrame.getContentPane().add(otherView);
		//pack the frame and show it
		myFrame.pack();
		//Place in center
		WindowPosition.placeCenter(myFrame);
	}
}
