package userinterface;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.util.ResourceBundle;

// system imports
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Properties;
import java.util.EventObject;
import java.util.Locale;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.text.NumberFormat;

// project imports
import impresario.IModel;
import model.*;

public class LoginView extends JPanel implements ActionListener
{
	// GUI stuff
	private Peon peon;
	private JTextField bannerID;
	private JPasswordField password;
	private JButton submitButton;
	private JButton cancelButton;
	private JRadioButton English;
	private JRadioButton French;
	private JLabel titleLabel;
	private JLabel passwordLabel;
	private JLabel selectLanguageLabel;

	// For showing error message
	private MessageView statusLog;

	public ResourceBundle localizedBundle;
	public Locale currentLocale;

	public LoginView(Peon p) {
		peon = p;

		currentLocale = LocaleConfig.currentLocale();
		localizedBundle = ResourceBundle.getBundle("BicycleStringsBundle", currentLocale);

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		// create our GUI components, add them to this panel
		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		titleLabel = new JLabel(localizedBundle.getString("greetings"));
		Font myFont = new Font("Arial", Font.BOLD, 24);
		titleLabel.setFont(myFont);
		titlePanel.add(titleLabel);
		add(titlePanel);
		add(dataEntryFields());
		add(navigationButtons());

		// Error message area
		add(createStatusLog("                          "));

		populateFields();
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		bannerID.requestFocus();
	}
	
	private JPanel createStatusLog(String initialMessage) {
		statusLog = new MessageView(initialMessage);
		return statusLog;
	}
	
	public void populateFields() {
		bannerID.setText("");
		password.setText("");
		English.setSelected(true);
		French.setSelected(false);
	}
	
	private JPanel dataEntryFields() {
		JPanel dataEntryPanel = new JPanel();
		// set the layout for this panel
		dataEntryPanel.setLayout(new BoxLayout(dataEntryPanel, BoxLayout.Y_AXIS));

		// data entry fields
		JPanel entryFieldPanel = new JPanel();
		entryFieldPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

		JLabel useridLabel = new JLabel("BannerID: ");
		entryFieldPanel.add(useridLabel);

		bannerID = new JTextField(20);
		bannerID.addActionListener(this);
		entryFieldPanel.add(bannerID);

		dataEntryPanel.add(entryFieldPanel);

		JPanel dataFieldPanel2 = new JPanel();
		dataFieldPanel2.setLayout(new FlowLayout(FlowLayout.LEFT));

		passwordLabel = new JLabel(localizedBundle.getString("password") + ": ");
		dataFieldPanel2.add(passwordLabel);

		password = new JPasswordField(20);
		password.addActionListener(this);
		dataFieldPanel2.add(password);

		dataEntryPanel.add(dataFieldPanel2);

		JPanel dataFieldPanel3 = new JPanel();

		dataFieldPanel3.setLayout(new FlowLayout(FlowLayout.LEFT));
		ButtonGroup LanguageButtons = new ButtonGroup();

		selectLanguageLabel = new JLabel(localizedBundle.getString("chooseLanguage"));
		dataFieldPanel3.add(selectLanguageLabel);
		English = new JRadioButton("English");
		English.addActionListener(this);
		LanguageButtons.add(English);
		dataFieldPanel3.add(English);

		French = new JRadioButton("Français");
		French.addActionListener(this);
		LanguageButtons.add(French);
		dataFieldPanel3.add(French);
		dataEntryPanel.add(dataFieldPanel3);
		
		return dataEntryPanel;
	}
	
	private JPanel navigationButtons() {
		JPanel temp = new JPanel();		// default FlowLayout is fine
		FlowLayout f1 = new FlowLayout(FlowLayout.CENTER);
		f1.setVgap(1);
		f1.setHgap(25);
		temp.setLayout(f1);

		// create the buttons, listen for events, add them to the panel
		cancelButton = new JButton(localizedBundle.getString("exit"));
		cancelButton.addActionListener(this);
		temp.add(cancelButton);

		submitButton = new JButton(localizedBundle.getString("login"));
		submitButton.addActionListener(this);
		temp.add(submitButton);

		return temp;
	}
	
	public void actionPerformed(ActionEvent evt) {
		clearErrorMessage();

		String bannerIDEntered = bannerID.getText();

		if(evt.getSource() == submitButton) {
			if ((bannerIDEntered == null) || (bannerIDEntered.length() == 0)) {
				displayErrorMessage("Please enter a Banner ID");
			} else {
				char[] passwordValueEntered = password.getPassword();
				String passwordEntered = new String(passwordValueEntered);
				
				if ((passwordEntered == null) || passwordEntered.length() == 0) {
					displayErrorMessage("Please enter a Password");
				} else {
					for (int cnt = 0; cnt < passwordValueEntered.length; cnt++) {
						passwordValueEntered[cnt] = 0;
					}
					processUserIDAndPassword(bannerIDEntered, passwordEntered);
				}
			}
		} 

		if(evt.getSource() == cancelButton) {
			peon.exitSystem();
		}

		if(evt.getSource() == French || evt.getSource() == English) {
			if(evt.getSource() == French) {
				LocaleConfig.setLocale(new Locale("fr", "FR"));
				currentLocale = new Locale("fr", "FR");
			} else {
				LocaleConfig.setLocale(new Locale("en", "US"));
				currentLocale = new Locale("en", "US");
			}
			localizedBundle = ResourceBundle.getBundle("BicycleStringsBundle", currentLocale);
			titleLabel.setText(localizedBundle.getString("greetings"));
			passwordLabel.setText(localizedBundle.getString("password"));
			cancelButton.setText(localizedBundle.getString("exit"));
			submitButton.setText(localizedBundle.getString("login"));
			selectLanguageLabel.setText(localizedBundle.getString("chooseLanguage"));
			selectLanguageLabel.revalidate();
			selectLanguageLabel.repaint();
			cancelButton.revalidate();
			cancelButton.repaint();
			submitButton.revalidate();
			submitButton.repaint();
			passwordLabel.revalidate();
			passwordLabel.repaint();
			titleLabel.revalidate();
			titleLabel.repaint();
		}

		return;
	}

	/**
	 * Process userid and pwd supplied when Submit button is hit.
	 * Action is to pass this info on to the teller object
	 */
	//----------------------------------------------------------
	private void processUserIDAndPassword(String useridString,
			String passwordString) {
		Properties props = new Properties();
		props.setProperty("ID", useridString);
		props.setProperty("Password", passwordString);

		// clear fields for next time around
		bannerID.setText("");
		password.setText("");
		String Languege = new String();
		if (English.isSelected() == true) {
			LocaleConfig.setLocale(new Locale("en", "US"));
		} else {
			LocaleConfig.setLocale(new Locale("fr", "FR"));
		}
		peon.authenticateLogin(props);
	}

	public void updateState(String key, Object value) {
		// STEP 6: Be sure to finish the end of the 'perturbation'
		// by indicating how the view state gets updated.
		if (key.equals("LoginError") == true) {
			// display the passed text
			displayErrorMessage((String)value);
		}
	}

	public void displayErrorMessage(String message) {
		statusLog.displayErrorMessage(message);
	}

	public void clearErrorMessage() {
		statusLog.clearErrorMessage();
	}
}
