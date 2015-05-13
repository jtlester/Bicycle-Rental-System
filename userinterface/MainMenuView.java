// specify the package
package userinterface;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import model.LocaleConfig;
import model.Peon;

public class MainMenuView extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private Peon peon;
	private JButton insertNewWorkerButton, insertNewUserButton, insertNewBicycleButton, logoutButton, doneButton, rentBikeButton, returnBikeButton, modifyBikeButton, modifyWorkerButton, modifyUserButton;
	private JLabel userLabel, bicycleLabel, loggedInUserLabel;

	public ResourceBundle localizedBundle;

	public MainMenuView(Peon peon) {
		this.peon = peon;
		Locale currentLocale = LocaleConfig.currentLocale();
		localizedBundle = ResourceBundle.getBundle("BicycleStringsBundle", currentLocale);

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(title());
		add(loggedInUserLabel());
		add(new JSeparator(SwingConstants.HORIZONTAL));
		add(navigationButtons());
		if(peon.obtainAdminLevel().equals("Yes")) {
			add(adminAccessPanel());
		}
		add(cancelButtons());
	}

	private JPanel loggedInUserLabel() {
		JPanel userPanel = new JPanel();
		userPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

		if(peon.obtainAdminLevel().equals("Yes")) {
			loggedInUserLabel = new JLabel(localizedBundle.getString("welcomeUser") + " " + peon.userName() + ". " + localizedBundle.getString("welcomeAdmin"));
		} else {
			loggedInUserLabel = new JLabel(localizedBundle.getString("welcomeUser") + " " + peon.userName());
		}
		userPanel.add(loggedInUserLabel);
		return userPanel;
	}

	private JPanel title() {
		JPanel titlePanel = new JPanel();
		JLabel label = new JLabel(localizedBundle.getString("greetings"), SwingConstants.CENTER);
		Font myFont = new Font("Arial", Font.BOLD, 24);
		label.setFont(myFont);
		titlePanel.add(label);
		return titlePanel;
	}

	private JPanel navigationButtons() {
		JPanel navPanel = new JPanel();
		navPanel.setLayout(new BoxLayout(navPanel, BoxLayout.Y_AXIS));

		//NORMAL WORKER CREDENTIAL BUTTONS
		JPanel workerPanel = new JPanel();

		workerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		workerPanel.setBorder(BorderFactory.createEmptyBorder(10,20,5,20));

		rentBikeButton = new JButton(localizedBundle.getString("rentBicycle"));
		rentBikeButton.addActionListener(this);
		returnBikeButton = new JButton(localizedBundle.getString("returnBicycle"));
		returnBikeButton.addActionListener(this);
		workerPanel.add(rentBikeButton);
		workerPanel.add(returnBikeButton);
		navPanel.add(workerPanel);

		JPanel bikePanel = new JPanel();
		bikePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		bikePanel.setBorder(BorderFactory.createEmptyBorder(10,20,10,20));
		modifyBikeButton = new JButton(localizedBundle.getString("modifyBicycle"));
		modifyBikeButton.addActionListener(this);
		bikePanel.add(modifyBikeButton);
		navPanel.add(bikePanel);

		navPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
		return navPanel;
	}

	private JPanel cancelButtons() {
		//EXIT AND LOGOUT BUTTON
		JPanel cancelPanel = new JPanel();
		FlowLayout f1 = new FlowLayout(FlowLayout.CENTER);
		f1.setVgap(25);
		f1.setHgap(25);
		cancelPanel.setLayout(f1);

		doneButton = new JButton(localizedBundle.getString("exit"));
		doneButton.addActionListener(this);
		cancelPanel.add(doneButton);
		cancelPanel.setAlignmentY(Component.CENTER_ALIGNMENT);

		logoutButton = new JButton(localizedBundle.getString("logout"));
		logoutButton.addActionListener(this);
		cancelPanel.add(logoutButton);
		cancelPanel.setAlignmentY(Component.CENTER_ALIGNMENT);

		return cancelPanel;
	}

	private JPanel adminAccessPanel() {
		JPanel accessPanel = new JPanel();
		Border one = BorderFactory.createLineBorder(Color.black);
		accessPanel.setLayout(new BoxLayout(accessPanel, BoxLayout.Y_AXIS));
		accessPanel.setBorder(BorderFactory.createTitledBorder(one, localizedBundle.getString("administrativePanel"), TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION));

		JPanel workerPanel = new JPanel();
		JLabel newWorkerLabel;
		JPanel userPanel = new JPanel();
		JPanel bikePanel = new JPanel();

		//WORKER ADMIN BUTTONS
		workerPanel.setLayout(new GridLayout(1,3,10,10));
		workerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

		newWorkerLabel = new JLabel(localizedBundle.getString("worker") + ":");
		workerPanel.add(newWorkerLabel);

		insertNewWorkerButton = new JButton(localizedBundle.getString("newWorker"));
		insertNewWorkerButton.addActionListener(this);
		modifyWorkerButton = new JButton(localizedBundle.getString("modifyWorker"));
		modifyWorkerButton.addActionListener(this);
		workerPanel.add(insertNewWorkerButton);
		workerPanel.add(modifyWorkerButton);
		//temp.setAlignmentX(insertNewWorkerButton.CENTER_ALIGNMENT);
		accessPanel.add(workerPanel);
		accessPanel.add(new JSeparator(SwingConstants.HORIZONTAL));

		//USER ADMIN BUTTONS		
		userPanel.setLayout(new GridLayout(1,3,10,10));
		userPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

		userLabel = new JLabel(localizedBundle.getString("user") + ":");
		userPanel.add(userLabel);

		insertNewUserButton = new JButton(localizedBundle.getString("addUser"));
		insertNewUserButton.addActionListener(this);
		userPanel.add(insertNewUserButton);

		modifyUserButton = new JButton(localizedBundle.getString("modifyUser"));
		modifyUserButton.addActionListener(this);
		userPanel.add(modifyUserButton);

		accessPanel.add(userPanel);
		accessPanel.add(new JSeparator(SwingConstants.HORIZONTAL));

		//ADD BIKE BUTTON AND LABEL
		bikePanel.setLayout(new GridLayout(1,2,10,10));
		bikePanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
		bicycleLabel = new JLabel(localizedBundle.getString("bicycle") + ":");
		bikePanel.add(bicycleLabel);

		insertNewBicycleButton = new JButton(localizedBundle.getString("addBicycle"));
		insertNewBicycleButton.addActionListener(this);
		bikePanel.add(insertNewBicycleButton);
		accessPanel.add(bikePanel);
		return accessPanel;
	}

	public void actionPerformed(ActionEvent event) {

		if(event.getSource() == insertNewWorkerButton) {
			peon.createAndShowWorkerView();
		} else if(event.getSource() == insertNewUserButton) {
			peon.createAndShowUserView();
		} else if(event.getSource() == insertNewBicycleButton) {
			peon.createAndShowBicycleView();
		} else if(event.getSource() == logoutButton) {
			peon.createAndShowLoginView();
		} else if(event.getSource() == doneButton) {
			peon.exitSystem();
		} else if(event.getSource() == rentBikeButton) {
			peon.createAndShowRentBicycleView();
		} else if(event.getSource() == returnBikeButton) {
			peon.createAndShowReturnBicycleView();
		} else if(event.getSource() == modifyBikeButton) {
			peon.createAndShowModifyBicycleView();
		} else if(event.getSource() == modifyWorkerButton) {
			peon.createAndShowModifyWorkerView();
		} else if(event.getSource() == modifyUserButton) {
			peon.createAndShowModifyUserView();
		}
	}
}
