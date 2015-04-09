// specify the package
package userinterface;

// system imports
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Locale;
import java.util.ResourceBundle;
import java.awt.GridLayout;
import java.util.Properties;
import java.util.EventObject;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.text.NumberFormat;

// project imports
import impresario.IModel;
import model.*;

public class WorkerView extends JPanel implements ActionListener {
	private Peon peon;
	private JLabel firstNameLabel;
	private JTextField firstNameTextField;
	private JLabel lastNameLabel;
	private JTextField lastNameTextField;
	private JLabel bannerLabel;
	private JTextField bannerTextField;
	private JLabel emailLabel;
	private JTextField emailTextField;
	private JLabel phoneLabel;
	private JTextField phoneTextField;
	private JLabel passwordLabel;
	private JTextField passwordTextField;
	private JButton submitButton;
	private JButton backButton;
	private JComboBox adminComboBox;
	private MessageView statusLog;

    public ResourceBundle localizedBundle;
	
	public WorkerView(Peon p) {
		peon = p;
		Locale currentLocale = LocaleConfig.currentLocale();
		localizedBundle = ResourceBundle.getBundle("BicycleStringsBundle", currentLocale);
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));

		JLabel lbl = new JLabel(localizedBundle.getString("addWorker"));
		Font myFont = new Font("Helvetica", Font.BOLD, 20);
		lbl.setFont(myFont);
		titlePanel.add(lbl);
		add(titlePanel);

		add(dataEntryPanel());
		add(choiceBox());
		add(navigationPanel());

		JPanel statusLog = new MessageView(" ");
		add(statusLog);
	}

	private JPanel dataEntryPanel() {
		JPanel entryPanel = new JPanel();
		// set the layout for this panel
		entryPanel.setLayout(new GridLayout(7,2,20,20));
		entryPanel.setBorder(BorderFactory.createEmptyBorder(10,15,10,15));

		// data entry fields		
		JLabel bannerLabel = new JLabel(localizedBundle.getString("bannerID") + ": ");
		bannerTextField = new JTextField(20);
		bannerTextField.addActionListener(this);
		entryPanel.add(bannerLabel);
		entryPanel.add(bannerTextField);
		
		JLabel passwordLabel = new JLabel(localizedBundle.getString("password") + ": ");
		passwordTextField = new JTextField(20);
		passwordTextField.addActionListener(this);
		entryPanel.add(passwordLabel);
		entryPanel.add(passwordTextField);
		
		JLabel firstNameLabel = new JLabel(localizedBundle.getString("firstName") + ": ");
		firstNameTextField = new JTextField(20);
		firstNameTextField.addActionListener(this);
		entryPanel.add(firstNameLabel);
		entryPanel.add(firstNameTextField);
		
		JLabel lastNameLabel = new JLabel(localizedBundle.getString("lastName") + ": ");
		lastNameTextField = new JTextField(20);
		lastNameTextField.addActionListener(this);
		entryPanel.add(lastNameLabel);
		entryPanel.add(lastNameTextField);
		
		JLabel phoneLabel = new JLabel(localizedBundle.getString("phoneNumber") + ": ");
		phoneTextField = new JTextField(20);
		phoneTextField.addActionListener(this);
		entryPanel.add(phoneLabel);
		entryPanel.add(phoneTextField);
		
		JLabel emailLabel = new JLabel(localizedBundle.getString("email") + ": ");
		emailTextField = new JTextField(20);
		emailTextField.addActionListener(this);
		entryPanel.add(emailLabel);
		entryPanel.add(emailTextField);
		return entryPanel;
	}

	// Create the navigation buttons
	//-------------------------------------------------------------
	private JPanel navigationPanel()
	{
		JPanel navPanel = new JPanel();		// default FlowLayout is fine
		FlowLayout f1 = new FlowLayout(FlowLayout.CENTER);
		f1.setVgap(50);
		f1.setHgap(25);
		navPanel.setLayout(f1);

		// create the buttons, listen for events, add them to the panel
		backButton = new JButton(localizedBundle.getString("back"));
		backButton.addActionListener(this);
		navPanel.add(backButton);

		submitButton = new JButton(localizedBundle.getString("submit"));
		submitButton.addActionListener(this);
		navPanel.add(submitButton);

		return navPanel;
	}
	
	// Create the combo box
	private JPanel choiceBox() {
		JPanel cBox = new JPanel();		// default FlowLayout is fine
		FlowLayout f1 = new FlowLayout(FlowLayout.CENTER);
		f1.setVgap(8);
		f1.setHgap(25);
		cBox.setLayout(f1);
		
		JLabel adminLabel = new JLabel(localizedBundle.getString("administrator") + "?");
		cBox.add(adminLabel);
		
		String [] adminPossibilites = { localizedBundle.getString("yes"), localizedBundle.getString("no") };
		adminComboBox = new JComboBox(adminPossibilites);
		adminComboBox.addActionListener(this);
		cBox.add(adminComboBox);
		
		return cBox;
	}
	
	public void displayErrorMessage(String message) {
		statusLog.displayErrorMessage(message);
	}

	public void clearErrorMessage() {
		statusLog.clearErrorMessage();
	}
	
	public void displayMessage(String message) {
		statusLog.displayMessage(message);
	}
	
	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == submitButton) {
			if((bannerTextField.getText() == null || passwordTextField.getText() == null || firstNameTextField.getText() == null) ||
				(lastNameTextField.getText() == null || phoneTextField.getText().length() < 7 || emailTextField.getText() == null)) {
				displayErrorMessage("Error: Some fields are incorrect");
			} else {
					Properties workerProperties = new Properties();
					workerProperties.setProperty("bannerId",bannerTextField.getText());
					workerProperties.setProperty("password",passwordTextField.getText());
					workerProperties.setProperty("adminLevel",(String)adminComboBox.getSelectedItem());
					workerProperties.setProperty("firstName",firstNameTextField.getText());
					workerProperties.setProperty("lastName",lastNameTextField.getText());
					workerProperties.setProperty("phoneNumber",phoneTextField.getText());
					workerProperties.setProperty("email",emailTextField.getText());

					peon.processWorkerData(workerProperties);
					
					bannerTextField.setText("");
					passwordTextField.setText("");
					firstNameTextField.setText("");
					lastNameTextField.setText("");
					phoneTextField.setText("");
					emailTextField.setText("");
				
			}
			
		} else if(event.getSource() == backButton) {
			peon.workerDataDone();
		}
	}
}
