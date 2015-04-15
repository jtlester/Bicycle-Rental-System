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

public class MainMenuView extends JPanel implements ActionListener {
	private Peon man;
	private JButton insertNewWorkerButton, insertNewUserButton, insertNewBicycleButton, logoutButton, doneButton;
	private JLabel userLabel, workerLabel, bicycleLabel, loggedInUser;

	private MessageView statusLog;

	public ResourceBundle localizedBundle;

	public MainMenuView(Peon p) {
		man = p;

		Locale currentLocale = LocaleConfig.currentLocale();
		localizedBundle = ResourceBundle.getBundle("BicycleStringsBundle", currentLocale);

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		add(createTitle());
		add(createNavigationButtons());

		add(createStatusLog("     "));
	}

	private JPanel createTitle() {
		JPanel temp = new JPanel();
		temp.setLayout(new FlowLayout(FlowLayout.CENTER));

		JLabel label = new JLabel(localizedBundle.getString("greetings"));
		Font myFont = new Font("Arial", Font.BOLD, 24);
		label.setFont(myFont);
		temp.add(label);

		return temp;
	}

	private JPanel createNavigationButtons() {
		JPanel mainTemp = new JPanel();
		mainTemp.setLayout(new BoxLayout(mainTemp, BoxLayout.Y_AXIS));
		
		JPanel temp = new JPanel();
		JPanel temp2 = new JPanel();
		JPanel temp3 = new JPanel();
		JPanel temp4 = new JPanel();
		
		temp2.setLayout(new GridLayout(1,2,10,10));
		temp2.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
		
		workerLabel = new JLabel(localizedBundle.getString("worker") + ":");
		temp2.add(workerLabel);
		
		insertNewWorkerButton = new JButton(localizedBundle.getString("addWorker"));
		insertNewWorkerButton.addActionListener(this);
		temp2.add(insertNewWorkerButton);
		//temp.setAlignmentX(insertNewWorkerButton.CENTER_ALIGNMENT);
		
		mainTemp.add(temp2);
		
		//--------------------------------------------------------------------------
		temp3.setLayout(new GridLayout(1,2,10,10));
		temp3.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
		
		userLabel = new JLabel(localizedBundle.getString("user") + ":");
		temp3.add(userLabel);
		
		insertNewUserButton = new JButton(localizedBundle.getString("addUser"));
		insertNewUserButton.addActionListener(this);
		temp3.add(insertNewUserButton);
		
		mainTemp.add(temp3);
		
		//-------------------------------------------------------------------------
		temp4.setLayout(new GridLayout(1,2,10,10));
		temp4.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
		bicycleLabel = new JLabel(localizedBundle.getString("bicycle") + ":");
		temp4.add(bicycleLabel);
		
		insertNewBicycleButton = new JButton(localizedBundle.getString("addBicycle"));
		insertNewBicycleButton.addActionListener(this);
		temp4.add(insertNewBicycleButton);
		
		mainTemp.add(temp4);
		
		//--------------------------------------------------------------------------
		//temp.setLayout(new GridLayout(1,2,10,10));
		//temp.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50));
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
		//logoutButton.setHorizontalAlignment(SwingConstants.LEFT);
		
		mainTemp.add(temp);

		return mainTemp;
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

		if(event.getSource() == insertNewWorkerButton)
		{
			man.createNewWorker();
		}
		else if(event.getSource() == insertNewUserButton)
		{
			man.createNewUser();
		}
		else if(event.getSource() == insertNewBicycleButton)
		{
			man.createNewBicycle();
		}
		else if(event.getSource() == logoutButton)
		{
			man.createAndShowLoginView();
		}
		else if(event.getSource() == doneButton)
		{
			man.exitSystem();
		}
	}

	public void displayErrorMessage(String message) {
		statusLog.displayErrorMessage(message);
	}

	public void clearErrorMessage() {
		statusLog.clearErrorMessage();
	}
}
