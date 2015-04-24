// specify the package
package model;

// system imports
import java.util.*;
import javax.swing.*;

// project imports
import event.Event;
import userinterface.*;

public class Peon {
	public JFrame myFrame;
	
	public Peon myModel;
	public MainMenuView mainMenuView;
	public WorkerView workerView;
	public LoginView loginView;
	public UserView userView;
	public ReturnView returnView;
	public RentView rentView;
	public RenewView renewView;
	public BicycleView bicycleView;
	public Login login;
	public String userName;
	public String adminLevel;
	//public JLabel loggedInUser;
        
        public ResourceBundle localizedBundle;
	public Locale currentLocale;
	
	public Peon() {
                currentLocale = LocaleConfig.currentLocale();
		localizedBundle = ResourceBundle.getBundle("BicycleStringsBundle", currentLocale);
                
		myFrame = MainFrame.getInstance();
		createAndShowLoginView();
	}

	public void authenticateLogin(Properties props) {
		//You can delete this for the FINAL VERSION if I forget
	/*	if(props.getProperty("bannerId").equals("800548775")) {
			adminLevel = "Yes";
			createAndShowMainMenuView();
			return;
		}*/
		login = new Login(props);
		if(login.authentication(props) == true) {
			userName = props.getProperty("bannerId");
			adminLevel = login.getAdminLevel();
			createAndShowMainMenuView();
			
		} else {
			JOptionPane.showMessageDialog(myFrame, "Invalid Username or Password", "Login Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public String getUserName() {
		return userName;
	}
	
	public String obtainAdminLevel() {
		return adminLevel;
	}

	public void errorMessagePopup(String error) {
            
                currentLocale = LocaleConfig.currentLocale();
		localizedBundle = ResourceBundle.getBundle("BicycleStringsBundle", currentLocale);
        
		if(error.equals("bannerId")) {
			JOptionPane.showMessageDialog(myFrame, localizedBundle.getString("errorInvalidBannerID"), "Error", JOptionPane.WARNING_MESSAGE);
		} else if(error.equals("password")) {
			JOptionPane.showMessageDialog(myFrame, localizedBundle.getString("errorInvalidPassword"), "Error", JOptionPane.WARNING_MESSAGE);
		}
		//Bike Error Checking
		else if(error.equals("make")) {
			JOptionPane.showMessageDialog(myFrame, localizedBundle.getString("errorInvalidMake"), "Error", JOptionPane.WARNING_MESSAGE);
		} else if(error.equals("model")) {
			JOptionPane.showMessageDialog(myFrame, localizedBundle.getString("errorInvalidModel"), "Error", JOptionPane.WARNING_MESSAGE);
		} else if(error.equals("serialNumber")) {
			JOptionPane.showMessageDialog(myFrame, localizedBundle.getString("errorInvalidSerial"), "Error", JOptionPane.WARNING_MESSAGE);
		} else if(error.equals("location")) {
			JOptionPane.showMessageDialog(myFrame, localizedBundle.getString("errorInvalidLocation"), "Error", JOptionPane.WARNING_MESSAGE);
		}
		
		//User error checking
		else if(error.equals("firstName")) {
			JOptionPane.showMessageDialog(myFrame, localizedBundle.getString("errorInvalidFirstName"), "Error", JOptionPane.WARNING_MESSAGE);
		} else if(error.equals("lastName")) {
			JOptionPane.showMessageDialog(myFrame, localizedBundle.getString("errorInvalidLastName"), "Error", JOptionPane.WARNING_MESSAGE);
		} else if(error.equals("phoneNumber")) {
			JOptionPane.showMessageDialog(myFrame, localizedBundle.getString("errorInvalidPhoneNumber"), "Error", JOptionPane.WARNING_MESSAGE);
		} else if(error.equals("email")) {
			JOptionPane.showMessageDialog(myFrame, localizedBundle.getString("errorInvalidEmail"), "Error", JOptionPane.WARNING_MESSAGE);
		}
		
		//Calendar error checking
		else if(error.equals("noDate")) {
			JOptionPane.showMessageDialog(myFrame, localizedBundle.getString("errorInvalidDate"), "Error", JOptionPane.WARNING_MESSAGE);
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

	public void createRentBicycleView() {
		createAndShowRentBicycleView();
	}

	public void createReturnBicycleView() {
		createAndShowReturnBicycleView();
	}

	public void createRenewBicycleView() {
		createAndShowRenewBicycleView();
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

	public void processRentData(Properties bicycleProperties) {
		Bicycle newBicycle = new Bicycle(bicycleProperties);
		newBicycle.update();
		
		bicycleView.displayMessage("Bicycle with a serial number of " + bicycleProperties.getProperty("serialNumber") + " saved successfully");
	}

	public void processReturnData(Properties returnProperties) {
		ReturnBike newReturnBike = new ReturnBike(returnProperties);
	    newReturnBike.update();
		
		returnView.displayMessage("Bike number " + returnProperties.getProperty("bikeId") + " has been updated successfully");
	}

	public void processRenewData(Properties bicycleProperties) {
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
	
	public void returnDataDone(){
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

	public void createAndShowRentBicycleView() {
		rentView = new RentView(this);
		myFrame.getContentPane().add(rentView);
		myFrame.pack();
		swapToView(rentView);
	}

	public void createAndShowReturnBicycleView() {
		returnView = new ReturnView(this);
		myFrame.getContentPane().add(returnView);
		myFrame.pack();
		swapToView(returnView);
	}

	public void createAndShowRenewBicycleView() {
		renewView = new RenewView(this);
		myFrame.getContentPane().add(renewView);
		myFrame.pack();
		swapToView(renewView);
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
