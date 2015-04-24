// specify the package
package userinterface;

// system imports
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Locale;
import java.util.Properties;
import java.util.EventObject;
import java.util.ResourceBundle;

import javax.swing.BoxLayout;
import javax.swing.border.*;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.*;
import javax.swing.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.text.NumberFormat;

// project imports
import impresario.IModel;
import model.*;
import userinterface.LoginView;

public class MainMenuView extends JPanel implements ActionListener {
	private Peon man;
	private JButton insertNewWorkerButton, insertNewUserButton, insertNewBicycleButton, logoutButton, doneButton, rentBikeButton, returnBikeButton, renewBikeButton;
	private JLabel userLabel, workerLabel, bicycleLabel, loggedInUser;

	private MessageView statusLog;

	public ResourceBundle localizedBundle;

	public MainMenuView(Peon p) {
		man = p;

		Locale currentLocale = LocaleConfig.currentLocale();
		localizedBundle = ResourceBundle.getBundle("BicycleStringsBundle", currentLocale);

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		add(createTitle());
		add(createLoggedInUser());
		add(new JSeparator(SwingConstants.HORIZONTAL));
		add(createNavigationButtons());
		
		if(man.obtainAdminLevel().equals("Yes")) {
			add(createAdminAccess());
		}
		
		add(createCancelButtons());

		add(createStatusLog("     "));
	}
	
	private JPanel createLoggedInUser() {
		JPanel temp = new JPanel();
		temp.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		if(man.obtainAdminLevel().equals("Yes")) {
			loggedInUser = new JLabel(localizedBundle.getString("welcomeUser") + " " + man.getUserName() + ". " + localizedBundle.getString("welcomeAdmin"));
		} else {
			loggedInUser = new JLabel(localizedBundle.getString("welcomeUser") + " " + man.getUserName());

		}
		
		temp.add(loggedInUser);
		//temp.add(new JSeparator(SwingConstants.HORIZONTAL));
		return temp;
	}

	private JPanel createTitle() {
		JPanel temp = new JPanel();
		//temp.setLayout(new FlowLayout(FlowLayout.CENTER));

		JLabel label = new JLabel(localizedBundle.getString("greetings"), SwingConstants.CENTER);
		Font myFont = new Font("Arial", Font.BOLD, 24);
		label.setFont(myFont);
		temp.add(label);
		

		return temp;
	}

	private JPanel createNavigationButtons() {
		JPanel mainTemp = new JPanel();
		mainTemp.setLayout(new BoxLayout(mainTemp, BoxLayout.Y_AXIS));
		
		//NORMAL WORKER CREDENTIAL BUTTONS
		//---------------------------------------------------------------------------
		JPanel temp = new JPanel();

		temp.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		temp.setBorder(BorderFactory.createEmptyBorder(10,20,5,20));
	
		rentBikeButton = new JButton(localizedBundle.getString("rentBicycle"));
		rentBikeButton.addActionListener(this);
		returnBikeButton = new JButton(localizedBundle.getString("returnBicycle"));
		returnBikeButton.addActionListener(this);
		temp.add(rentBikeButton);
		temp.add(returnBikeButton);
		mainTemp.add(temp);
		
		JPanel temp2 = new JPanel();
		temp2.setLayout(new FlowLayout(FlowLayout.CENTER));
		temp2.setBorder(BorderFactory.createEmptyBorder(10,20,10,20));
		renewBikeButton = new JButton(localizedBundle.getString("renewBicycle"));
		renewBikeButton.addActionListener(this);
		temp2.add(renewBikeButton);
		mainTemp.add(temp2);

		mainTemp.add(new JSeparator(SwingConstants.HORIZONTAL));
		
		//---------------------------------------------------------------------------
		return mainTemp;
	}
	
	private JPanel createCancelButtons() {
		//EXIT AND LOGOUT BUTTON
		JPanel temp = new JPanel();
		FlowLayout f1 = new FlowLayout(FlowLayout.CENTER);
		f1.setVgap(25);
		f1.setHgap(25);
		temp.setLayout(f1);
		
		doneButton = new JButton(localizedBundle.getString("exit"));
		doneButton.addActionListener(this);
		temp.add(doneButton);
		temp.setAlignmentY(doneButton.CENTER_ALIGNMENT);
		
		logoutButton = new JButton(localizedBundle.getString("logout"));
		logoutButton.addActionListener(this);
		temp.add(logoutButton);
		temp.setAlignmentY(logoutButton.CENTER_ALIGNMENT);
		
		return temp;
	}
	
	private JPanel createAdminAccess() {
		JPanel temp = new JPanel();
		Border one = BorderFactory.createLineBorder(Color.black);
		//Border two = BorderFactory.createTitledBorder(one, "Administrative Access", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION);
		temp.setLayout(new BoxLayout(temp, BoxLayout.Y_AXIS));
		temp.setBorder(BorderFactory.createTitledBorder(one, "Administrative Access", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION));
		
		JPanel tempWorker = new JPanel();
		JLabel newWorkerLabel;
		JPanel tempUser = new JPanel();
		JPanel tempBike = new JPanel();
		
		//WORKER ADMIN BUTTONS
		tempWorker.setLayout(new GridLayout(1,2,10,10));
		tempWorker.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
		
		newWorkerLabel = new JLabel(localizedBundle.getString("worker") + ":");
		tempWorker.add(newWorkerLabel);
		
		insertNewWorkerButton = new JButton(localizedBundle.getString("addWorker"));
		insertNewWorkerButton.addActionListener(this);
		tempWorker.add(insertNewWorkerButton);
		//temp.setAlignmentX(insertNewWorkerButton.CENTER_ALIGNMENT);
		temp.add(tempWorker);
		temp.add(new JSeparator(SwingConstants.HORIZONTAL));
		
		
		//USER ADMIN BUTTONS		
		//--------------------------------------------------------------------------
		tempUser.setLayout(new GridLayout(1,2,10,10));
		tempUser.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
		
		userLabel = new JLabel(localizedBundle.getString("user") + ":");
		tempUser.add(userLabel);
		
		insertNewUserButton = new JButton(localizedBundle.getString("addUser"));
		insertNewUserButton.addActionListener(this);
		tempUser.add(insertNewUserButton);
		temp.add(tempUser);
		temp.add(new JSeparator(SwingConstants.HORIZONTAL));
		
		//ADD BIKE BUTTON AND LABEL
		//-------------------------------------------------------------------------
		tempBike.setLayout(new GridLayout(1,2,10,10));
		tempBike.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
		bicycleLabel = new JLabel(localizedBundle.getString("bicycle") + ":");
		tempBike.add(bicycleLabel);
		
		insertNewBicycleButton = new JButton(localizedBundle.getString("addBicycle"));
		insertNewBicycleButton.addActionListener(this);
		tempBike.add(insertNewBicycleButton);
		temp.add(tempBike);
		
		return temp;
	}

	private JPanel createStatusLog(String initialMessage) {
		statusLog = new MessageView(initialMessage);
		return statusLog;
	}
	
	/*private JPanel createLoggedInWorker(Properties )
	{
		
	}*/

	public void actionPerformed(ActionEvent event) {
		clearErrorMessage();

		if(event.getSource() == insertNewWorkerButton) {
			man.createNewWorker();
		} else if(event.getSource() == insertNewUserButton) {
			man.createNewUser();
		} else if(event.getSource() == insertNewBicycleButton) {
			man.createNewBicycle();
		} else if(event.getSource() == logoutButton) {
			man.createAndShowLoginView();
		} else if(event.getSource() == doneButton) {
			man.exitSystem();
		} else if(event.getSource() == rentBikeButton) {
			man.createRentBicycleView();
		} else if(event.getSource() == returnBikeButton) {
			man.createReturnBicycleView();
		} else if(event.getSource() == renewBikeButton) {
			man.createRenewBicycleView();
		}
	}

	public void displayErrorMessage(String message) {
		statusLog.displayErrorMessage(message);
	}

	public void clearErrorMessage() {
		statusLog.clearErrorMessage();
	}
}
