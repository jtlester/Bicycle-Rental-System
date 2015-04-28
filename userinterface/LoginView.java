package userinterface;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import model.LocaleConfig;
import model.Peon;
// system imports
// project imports

public class LoginView extends JPanel implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
	private JLabel useridLabel;

	// For showing error message
	private MessageView statusLog;

	public ResourceBundle localizedBundle;
	public Locale currentLocale;
	public String userName;

	public LoginView(Peon p) {
		peon = p;

		currentLocale = LocaleConfig.currentLocale();
		localizedBundle = ResourceBundle.getBundle("BicycleStringsBundle", currentLocale);
		this.setPreferredSize(new Dimension(800, 300));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		// create our GUI components, add them to this panel
		//JPanel titlePanel = new JPanel();
		//titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		add(createGreeting());
		
		//titleLabel = new JLabel(localizedBundle.getString("greetings"));
		//Font myFont = new Font("Arial", Font.BOLD, 24);
		//titleLabel.setFont(myFont);
		//titlePanel.add(titleLabel);
		//add(titlePanel);
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
	
	private JPanel createGreeting()
	{
		JPanel temp = new JPanel();
		temp.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		BufferedImage logo = null;
		BufferedImage reverseLogo = null;
		
		try
		{
			logo = ImageIO.read(new File("images/logo.jpg"));
			JLabel picLabel = new JLabel(new ImageIcon(logo));
			temp.add(picLabel);
		}
		catch(IOException e)
		{
			System.out.println("Cannot load logo");
		}
		
		titleLabel = new JLabel(localizedBundle.getString("greetings"));
		Font myFont = new Font("Arial", Font.BOLD, 24);
		titleLabel.setFont(myFont);
		temp.add(titleLabel);
		
		try
		{
			reverseLogo = ImageIO.read(new File("images/logo_reversed.jpg"));
			JLabel rightLabel = new JLabel(new ImageIcon(reverseLogo));
			temp.add(rightLabel);
		}
		catch(IOException e)
		{
			System.out.println("Cannot load logo");
		}
		
		return temp;
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
		entryFieldPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

		useridLabel = new JLabel("BannerID");
		entryFieldPanel.add(useridLabel);

		bannerID = new JTextField(20);
		bannerID.addActionListener(this);
		entryFieldPanel.add(bannerID);

		dataEntryPanel.add(entryFieldPanel);

		JPanel dataFieldPanel2 = new JPanel();
		dataFieldPanel2.setLayout(new FlowLayout(FlowLayout.CENTER));

		passwordLabel = new JLabel(localizedBundle.getString("password") + ": ");
		dataFieldPanel2.add(passwordLabel);

		password = new JPasswordField(20);
		password.addActionListener(this);
		dataFieldPanel2.add(password);

		dataEntryPanel.add(dataFieldPanel2);

		JPanel dataFieldPanel3 = new JPanel();

		dataFieldPanel3.setLayout(new FlowLayout(FlowLayout.CENTER));
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

		//submitButton.setDefaultCapable(true);
		submitButton = new JButton(localizedBundle.getString("login"));
		//submitButton.setDefaultCapable(true);
		submitButton.addActionListener(this);
		//submitButton.addActionListener(this);
		temp.add(submitButton);
		//myFrame.getRootPane().setDefaultButton( submitButton );
		

		//JRootPane root = submitButton.getRootPane();
		//root.setDefaultButton(submitButton);

		return temp;
	}
	
	public void actionPerformed(ActionEvent evt) {
		clearErrorMessage();

		String bannerIDEntered = bannerID.getText();

		if(evt.getSource() == submitButton) {
			if ((bannerIDEntered == null) || (bannerIDEntered.length() == 0)) {
				//displayErrorMessage("Please enter a Banner ID");
				peon.errorMessagePopup("bannerId");
				
			} else {
				char[] passwordValueEntered = password.getPassword();
				String passwordEntered = new String(passwordValueEntered);
				
				if ((passwordEntered == null) || passwordEntered.length() == 0) {
					//displayErrorMessage("Please enter a Password");
					peon.errorMessagePopup("password");
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
			useridLabel.setText(localizedBundle.getString("bannerID"));
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
		props.setProperty("bannerId", useridString);
		props.setProperty("password", passwordString);

		// clear fields for next time around
		bannerID.setText("");
		password.setText("");
		if (English.isSelected() == true) {
			LocaleConfig.setLocale(new Locale("en", "US"));
		} else {
			LocaleConfig.setLocale(new Locale("fr", "FR"));
		}
		//userName = props.getProperty("bannerId");
		//returnUserName();
		peon.authenticateLogin(props);
	}
	/*public String returnUserName()
	{
		return userName;
	}*/

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
