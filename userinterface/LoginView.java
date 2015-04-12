package userinterface;

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

	// For showing error message
	private MessageView statusLog;

	public LoginView(Peon p) {
		peon = p;

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		// create our GUI components, add them to this panel
		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		JLabel label = new JLabel("Login to Brockport's Bicycle Rental System");
		Font myFont = new Font("Arial", Font.BOLD, 24);
		label.setFont(myFont);
		titlePanel.add(label);
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

		JLabel passwordLabel = new JLabel("Password: ");
		dataFieldPanel2.add(passwordLabel);

		password = new JPasswordField(20);
		password.addActionListener(this);
		dataFieldPanel2.add(password);

		dataEntryPanel.add(dataFieldPanel2);

		JPanel dataFieldPanel3 = new JPanel();

		dataFieldPanel3.setLayout(new FlowLayout(FlowLayout.LEFT));
		ButtonGroup LanguegeButtons = new ButtonGroup();

		JLabel LanguegeSelect=new JLabel("Select a language: ");
		dataFieldPanel3.add(LanguegeSelect);
		English = new JRadioButton("English");
		English.addActionListener(this);
		LanguegeButtons.add(English);
		dataFieldPanel3.add(English);

		French = new JRadioButton("Français");
		French.addActionListener(this);
		LanguegeButtons.add(French);
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
		cancelButton = new JButton("Exit");
		cancelButton.addActionListener(this);
		temp.add(cancelButton);

		submitButton = new JButton("Login");
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
				//bannerIDEntered.requestFocus();
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
		} else if (evt.getSource() == cancelButton) {
			peon.exitSystem();
		}
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
