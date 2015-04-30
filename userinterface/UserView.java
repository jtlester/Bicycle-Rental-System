// specify the package
package userinterface;
// system imports
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.DateLabelFormatter;
import model.LocaleConfig;
import model.Peon;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

//Add UserView
public class UserView extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;

	private Peon peon;
	private JTextField firstNameTextField;
	private JTextField lastNameTextField;
	private JTextField bannerTextField;
	private JTextField emailTextField;
	private JTextField phoneTextField;
	private JButton submitButton;
	private JButton backButton;
	private JDatePickerImpl registrationDatePicker;
	public ResourceBundle localizedBundle;
	public MessageView statusLog;



	public UserView(Peon p) {
		peon = p;
		Locale currentLocale = LocaleConfig.currentLocale();
		localizedBundle = ResourceBundle.getBundle("BicycleStringsBundle", currentLocale);

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		JLabel lbl = new JLabel(localizedBundle.getString("addUser"));
		Font myFont = new Font("Helvetica", Font.BOLD, 20);
		lbl.setFont(myFont);
		titlePanel.add(lbl);
		add(titlePanel);
		add(dataEntryPanel());
		add(createDate());
		add(navigationPanel());
		add(createStatusLog("                          "));

	}
	private JPanel createStatusLog(String initialMessage) {
		statusLog = new MessageView(initialMessage);
		return statusLog;
	}

	private JPanel dataEntryPanel() {
		JPanel entryPanel = new JPanel();
		// set the layout for this panel
		entryPanel.setLayout(new GridLayout(7,2,20,20));
		entryPanel.setBorder(BorderFactory.createEmptyBorder(10,15,10,15));

		// data entry fields		
		JLabel bannerLabel = new JLabel(localizedBundle.getString("bannerID") + ": ");
		entryPanel.add(bannerLabel);
		bannerTextField = new JTextField(20);
		bannerTextField.addActionListener(this);
		entryPanel.add(bannerTextField);

		JLabel firstNameLabel = new JLabel(localizedBundle.getString("firstName") + ": ");
		entryPanel.add(firstNameLabel);
		firstNameTextField = new JTextField(20);
		firstNameTextField.addActionListener(this);
		entryPanel.add(firstNameTextField);

		JLabel lastNameLabel = new JLabel(localizedBundle.getString("lastName") + ": ");
		entryPanel.add(lastNameLabel);
		lastNameTextField = new JTextField(20);
		lastNameTextField.addActionListener(this);
		entryPanel.add(lastNameTextField);

		JLabel phoneLabel = new JLabel(localizedBundle.getString("phoneNumber") + ": ");
		entryPanel.add(phoneLabel);
		phoneTextField = new JTextField(20);
		phoneTextField.addActionListener(this);
		entryPanel.add(phoneTextField);

		JLabel emailLabel = new JLabel(localizedBundle.getString("email") +  ": ");
		entryPanel.add(emailLabel);
		emailTextField = new JTextField(20);
		emailTextField.addActionListener(this);
		entryPanel.add(emailTextField);

		return entryPanel;
	}

	// Create the navigation buttons
	//-------------------------------------------------------------
	private JPanel navigationPanel() {
		JPanel navPanel = new JPanel();		// default FlowLayout is fine
		FlowLayout f1 = new FlowLayout(FlowLayout.CENTER);
		f1.setVgap(50);
		f1.setHgap(25);
		navPanel.setLayout(f1);

		backButton = new JButton(localizedBundle.getString("back"));
		backButton.addActionListener(this);
		navPanel.add(backButton);

		submitButton = new JButton(localizedBundle.getString("submit"));
		submitButton.addActionListener(this);
		navPanel.add(submitButton);

		return navPanel;
	}

	//Create Date
	private JPanel createDate() {
		JPanel temp = new JPanel();
		temp.setLayout(new FlowLayout(FlowLayout.CENTER));

		JLabel rentDateLabel = new JLabel("Date: ");
		temp.add(rentDateLabel);

		UtilDateModel model = new UtilDateModel();
		model.setSelected(true);
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		registrationDatePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());		 
		temp.add(registrationDatePicker);

		return temp;
	}

	public void displayMessage(String message) {
		statusLog.displayMessage(message);
	}

	public void displayErrorMessage(String message) {
		statusLog.displayErrorMessage(message);
	}
	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == submitButton) {
			if(bannerTextField.getText().equals("") || !Peon.isNumber(bannerTextField.getText())) {
				JOptionPane.showMessageDialog(this, localizedBundle.getString("errorInvalidBannerID"), "Error", JOptionPane.WARNING_MESSAGE);
			} else if(firstNameTextField.getText().equals("")) {
				JOptionPane.showMessageDialog(this, localizedBundle.getString("errorInvalidFirstName"), "Error", JOptionPane.WARNING_MESSAGE);
			} else if(lastNameTextField.getText().equals("")) {
				JOptionPane.showMessageDialog(this, localizedBundle.getString("errorInvalidLastName"), "Error", JOptionPane.WARNING_MESSAGE);
			} else if(phoneTextField.getText().length() != 11) {
				JOptionPane.showMessageDialog(this, localizedBundle.getString("errorInvalidPhoneNumber"), "Error", JOptionPane.WARNING_MESSAGE);
			} else if(emailTextField.getText().equals("")) {
				JOptionPane.showMessageDialog(this, localizedBundle.getString("errorInvalidEmail"), "Error", JOptionPane.WARNING_MESSAGE);
			}else {
				String day = String.valueOf(registrationDatePicker.getModel().getDay());
				String month = String.valueOf(registrationDatePicker.getModel().getMonth() + 1);
				String year = String.valueOf(registrationDatePicker.getModel().getYear());

				Properties userProperties = new Properties();
				userProperties.setProperty("bannerId",bannerTextField.getText());
				userProperties.setProperty("firstName",firstNameTextField.getText());
				userProperties.setProperty("lastName",lastNameTextField.getText());
				userProperties.setProperty("phoneNumber",phoneTextField.getText());
				userProperties.setProperty("email",emailTextField.getText());
				userProperties.setProperty("dateRegistered", day + "-" + month + "-" + year);
				
				if(peon.processUserData(userProperties)){
					JOptionPane.showMessageDialog(this, localizedBundle.getString("successUser"), "Success", JOptionPane.PLAIN_MESSAGE);
				}
				clearEntries();
			}
		} else if(event.getSource() == backButton) {
			clearEntries();
			peon.userDataDone();
		}
	}

	private void clearEntries() {
		bannerTextField.setText("");
		firstNameTextField.setText("");
		lastNameTextField.setText("");
		phoneTextField.setText("");
		emailTextField.setText("");
	}
}