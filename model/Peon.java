// specify the package
package model;

// system imports
import java.util.Date;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import userinterface.BicycleView;
import userinterface.LoginView;
import userinterface.MainFrame;
import userinterface.MainMenuView;
import userinterface.ModifyBikeView;
import userinterface.RentView;
import userinterface.ReturnView;
import userinterface.UserView;
import userinterface.WindowPosition;
import userinterface.WorkerView;
import userinterface.ModifyUserView;
import userinterface.ModifyWorkerView;
// project imports
import event.Event;

public class Peon {
	public JFrame myFrame;

	public Peon myModel;
	public MainMenuView mainMenuView;
	public WorkerView workerView;
	public LoginView loginView;
	public UserView userView;
	public ReturnView returnView;
	public RentView rentView;
	public ModifyBikeView modifyBikeView;
	public ModifyUserView modifyUserView;
	public BicycleView bicycleView;
	public ModifyWorkerView modifyWorkerView;
	public Login login;
	public String firstName;
	public String lastName;
	public String adminLevel;

	public ResourceBundle localizedBundle;
	public Locale currentLocale;

	public Peon() {
		currentLocale = LocaleConfig.currentLocale();
		localizedBundle = ResourceBundle.getBundle("BicycleStringsBundle", currentLocale);
		myFrame = MainFrame.getInstance();
		createAndShowLoginView();
	}

	public void authenticateLogin(Properties props) {
		login = new Login(props);
		if(login.authentication(props) == true) {
			firstName = login.workerFirstName;
			lastName = login.workerLastName;
			adminLevel = login.adminLevel();
			createAndShowMainMenuView();
		} else {
			JOptionPane.showMessageDialog(myFrame, "Invalid Username or Password", "Login Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public boolean processWorkerData(Properties workerProperties) {
		Worker newWorker = new Worker(workerProperties);
		if(newWorker.update()) {
			return true;
		}
		return false;
	}

	public boolean processUserData(Properties userProperties) {
		User newUser = new User(userProperties);
		if(newUser.update()) {
			return true;
		}
		return false;
	}

	public boolean processBicycleData(Properties bicycleProperties) {
		Bicycle newBicycle = new Bicycle(bicycleProperties);
		if(newBicycle.update()) {
			return true;
		}
		return false;
	}

	public boolean processRentData(Properties rentProperties) {
		RentBike newRentBike = new RentBike(rentProperties);
		if(newRentBike.update()) {
			return true;
		}
		return false;
	}

	public boolean changeStatus(Properties p)
	{
		Bicycle bicycle = new Bicycle(p);
		if(bicycle.changeStatus()) {
			return true;
		}
		return false;
	}

	public boolean processReturnData(Properties returnProperties) {
		ReturnBike newReturnBike = new ReturnBike(returnProperties);
		if(newReturnBike.update()) {
			return true;
		}
		return false;
	}

	public boolean processUpdateBicycleData(Properties bicycleProperties) {
		Bicycle newBicycle = new Bicycle(bicycleProperties);
		if(newBicycle.updateBicycleInfo()) {
			return true;
		}
		return false;
	}
	
	public boolean processUpdateUserData(Properties userProperties) {
		User newUser = new User(userProperties);
		if(newUser.updateUserInfo()) {
			return true;
		}
		return false;
	}
	
	public boolean processUpdateWorkerData(Properties workerProperties) {
		Worker updateWorker = new Worker(workerProperties);
		if(updateWorker.updateWorkerInfo()) {
			return true;
		}
		return false;
	}
	
	public String fetchUserName(String bannerId) {
		User newUser = new User(new Properties());
		return newUser.userNameForBannerId(bannerId);
	}
	
	public String fetchBicycleName(String bikeId) {
		Bicycle newBike = new Bicycle(new Properties());
		return newBike.bikeName(bikeId);
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

	public void createAndShowModifyBicycleView() {
		modifyBikeView = new ModifyBikeView(this);
		myFrame.getContentPane().add(modifyBikeView);
		myFrame.pack();
		swapToView(modifyBikeView);
	}

	public void createAndShowModifyWorkerView(){
		modifyWorkerView = new ModifyWorkerView(this);
		myFrame.getContentPane().add(modifyWorkerView);
		myFrame.pack();
		swapToView(modifyWorkerView);
	}

	public void createAndShowModifyUserView() {
		modifyUserView = new ModifyUserView(this);
		myFrame.getContentPane().add(modifyUserView);
		myFrame.pack();
		swapToView(modifyUserView);
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
		myFrame.getContentPane().remove(currentView);
		myFrame.getContentPane().add(otherView);
		myFrame.pack();
		WindowPosition.placeCenter(myFrame);
	}

	//Helper Methods-------------------------------------------
	//Check if string is number
	public static boolean isNumber(String string) {
		try {
			Integer.parseInt(string);
		} catch (NumberFormatException e) {
			System.out.println("Not a number");
			return false;
		}
		return true;
	}

	//Check if date is valid
	public static boolean isValidDate(Date dateToCheck, Date laterDateToCheck) {
		if(laterDateToCheck != null) {
			if(!dateToCheck.before(laterDateToCheck)) {
				return false;
			}
		} else if(dateToCheck.before(new Date())) {
			return false;
		}
		return true;
	}

}
