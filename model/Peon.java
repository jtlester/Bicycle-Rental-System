// specify the package
package model;

// system imports
import java.util.*;
import javax.swing.*;

// project imports
import impresario.IModel;
import impresario.ISlideShow;
import impresario.IView;
import impresario.ModelRegistry;
import exception.InvalidPrimaryKeyException;
import exception.PasswordMismatchException;
import event.Event;
import userinterface.*;

public class Peon {
	public JFrame myFrame;
	
	public Peon myModel;
	public MainMenuView mainMenuView;
	public WorkerView workerView;
	public LoginView loginView;
	public UserView userView;
	public BicycleView bicycleView;
	
	public Peon() {
		myFrame = MainFrame.getInstance();
		createAndShowLoginView();
	}

	public void authenticateLogin(Properties props) {
		Login login = new Login(props);
		if(login.authenticationIsSuccessful()) {
			createAndShowMainMenuView();
		}
	}

	public void createNewWorker() {
		createAndShowWorkerView();
	}
	
	public void createNewUser() {
		createAndShowUserView();
	}
	
	public void createNewBicycle() {
		createAndShowBicycleView();
	}
	
	public void processWorkerData(Properties workerProperties) {
		Worker newWorker = new Worker(workerProperties);
		newWorker.update();
		
		workerView.displayMessage("Worker with name: " + workerProperties.getProperty("firstName") + " saved successfully.");
	}
	
	public void processUserData(Properties userProperties) {
		User newUser = new User(userProperties);
		newUser.update();
		
		userView.displayMessage(userProperties.getProperty("firstName") + " saved successfully");
	}
	
	public void processBicycleData(Properties bicycleProperties) {
		Bicycle newBicycle = new Bicycle(bicycleProperties);
		newBicycle.update();
		
		bicycleView.displayMessage("Bicycle with a serial number of " + bicycleProperties.getProperty("serialNumber") + " saved successfully");
	}
	
	public void workerDataDone() {
		createAndShowMainMenuView();
	}
	
	public void userDataDone() {
		createAndShowMainMenuView();
	}
	
	public void bicycleDataDone() {
		createAndShowMainMenuView();
	}
	
	public void exitSystem() {
		System.exit(0);
	}
	
	public void createAndShowLoginView() {
		if(loginView == null) {
			loginView = new LoginView(this);
			myFrame.getContentPane().add(loginView);
			myFrame.pack();
		} else {
			swapToView(loginView);
		}
	}

	public void createAndShowMainMenuView() {
		mainMenuView = new MainMenuView(this);
		myFrame.getContentPane().add(mainMenuView);
		myFrame.pack();
		swapToView(mainMenuView);
	}
	
	public void createAndShowWorkerView() {		
		workerView = new WorkerView(this);
		myFrame.getContentPane().add(workerView); 
		myFrame.pack();
		swapToView(workerView);
	}
	
	public void createAndShowUserView() {	
		userView = new UserView(this);
		myFrame.getContentPane().add(userView);
		myFrame.pack();
		swapToView(userView);
	}
	
	public void createAndShowBicycleView() {
		bicycleView = new BicycleView(this);
		myFrame.getContentPane().add(bicycleView);
		myFrame.pack();
		swapToView(bicycleView);
	}
	
	public void swapToView(JPanel otherView) {
		if (otherView == null) {
			new Event(Event.getLeafLevelClassName(this), "swapToView",
				"Missing view for display ", Event.ERROR);
			return;
		}

		if (otherView instanceof JPanel) {
			swapToPanelView((JPanel)otherView);
		} else {
			new Event(Event.getLeafLevelClassName(this), "swapToView",
				"Non-displayable view object sent ", Event.ERROR);
		}
	}
	
	protected void swapToPanelView(JPanel otherView) {
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
