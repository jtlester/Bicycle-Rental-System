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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import model.LocaleConfig;
import model.Peon;

public class LoginView extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	// GUI stuff
	private Peon peon;
	private JTextField bannerIdField;
	private JPasswordField passwordField;
	private JButton submitButton;
	private JButton cancelButton;
	private JRadioButton englshButton;
	private JRadioButton frenchButton;
	private JLabel titleLabel;
	private JLabel passwordLabel;
	private JLabel selectLanguageLabel;
	private JLabel useridLabel;

	public ResourceBundle localizedBundle;
	public Locale currentLocale;
	public String userName;

	public LoginView(Peon peon) {
		this.peon = peon;

		currentLocale = LocaleConfig.currentLocale();
		localizedBundle = ResourceBundle.getBundle("BicycleStringsBundle", currentLocale);
		this.setPreferredSize(new Dimension(800, 300));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(greetingPanel());
		add(dataEntryFields());
		add(navigationButtons());
		populateFields();
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		bannerIdField.requestFocus();
	}
	
	private JPanel greetingPanel() {
		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		BufferedImage logo = null;		
		try {
			logo = ImageIO.read(new File("images/logo.jpg"));
			JLabel picLabel = new JLabel(new ImageIcon(logo));
			titlePanel.add(picLabel);
			
		} catch(IOException e) {
			System.out.println("ERROR: Could not load logos");
		}
		
		titleLabel = new JLabel(localizedBundle.getString("greetings"));
		Font myFont = new Font("Arial", Font.BOLD, 24);
		titleLabel.setFont(myFont);
		titlePanel.add(titleLabel);
		return titlePanel;
	}

	public void populateFields() {
		englshButton.setSelected(true);
		frenchButton.setSelected(false);
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

		bannerIdField = new JTextField(20);
		bannerIdField.addActionListener(this);
		entryFieldPanel.add(bannerIdField);

		dataEntryPanel.add(entryFieldPanel);

		JPanel dataFieldPanel2 = new JPanel();
		dataFieldPanel2.setLayout(new FlowLayout(FlowLayout.CENTER));

		passwordLabel = new JLabel(localizedBundle.getString("password") + ": ");
		dataFieldPanel2.add(passwordLabel);

		passwordField = new JPasswordField(20);
		passwordField.addActionListener(this);
		dataFieldPanel2.add(passwordField);

		dataEntryPanel.add(dataFieldPanel2);

		JPanel dataFieldPanel3 = new JPanel();

		dataFieldPanel3.setLayout(new FlowLayout(FlowLayout.CENTER));
		ButtonGroup LanguageButtons = new ButtonGroup();

		selectLanguageLabel = new JLabel(localizedBundle.getString("chooseLanguage"));
		dataFieldPanel3.add(selectLanguageLabel);
		englshButton = new JRadioButton(localizedBundle.getString("english"));
		englshButton.addActionListener(this);
		LanguageButtons.add(englshButton);
		dataFieldPanel3.add(englshButton);

		frenchButton = new JRadioButton(localizedBundle.getString("french"));
		frenchButton.addActionListener(this);
		LanguageButtons.add(frenchButton);
		dataFieldPanel3.add(frenchButton);
		dataEntryPanel.add(dataFieldPanel3);
		
		return dataEntryPanel;
	}
	
	private JPanel navigationButtons() {
		JPanel navPanel = new JPanel();		// default FlowLayout is fine
		FlowLayout f1 = new FlowLayout(FlowLayout.CENTER);
		f1.setVgap(1);
		f1.setHgap(25);
		navPanel.setLayout(f1);

		// create the buttons, listen for events, add them to the panel
		cancelButton = new JButton(localizedBundle.getString("exit"));
		cancelButton.addActionListener(this);
		navPanel.add(cancelButton);

		submitButton = new JButton(localizedBundle.getString("login"));
		submitButton.addActionListener(this);
		navPanel.add(submitButton);

		return navPanel;
	}
	
	public void actionPerformed(ActionEvent evt) {
		String bannerIDEntered = bannerIdField.getText();

		if(evt.getSource() == submitButton) {
			if ((bannerIDEntered == null) || (bannerIDEntered.length() == 0)) {
				JOptionPane.showMessageDialog(this, localizedBundle.getString("errorInvalidBannerID"), localizedBundle.getString("error"), JOptionPane.ERROR_MESSAGE);
			} else {
				String passwordEntered = String.valueOf(passwordField.getPassword());
				if ((passwordEntered == null) || passwordEntered.length() == 0) {
					JOptionPane.showMessageDialog(this, localizedBundle.getString("errorInvalidPassword"), localizedBundle.getString("error"), JOptionPane.ERROR_MESSAGE);
					passwordField.setText("");
				} else {
					processUserIDAndPassword(bannerIDEntered, passwordEntered);
				}
			}
		} 

		if(evt.getSource() == cancelButton) {
			peon.exitSystem();
		}

		if(evt.getSource() == frenchButton || evt.getSource() == englshButton) {
			if(evt.getSource() == frenchButton) {
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
			englshButton.setText(localizedBundle.getString("english"));
			frenchButton.setText(localizedBundle.getString("french"));
			selectLanguageLabel.revalidate();
			selectLanguageLabel.repaint();
			cancelButton.revalidate();
			cancelButton.repaint();
			submitButton.revalidate();
			submitButton.repaint();
			englshButton.revalidate();
			englshButton.repaint();
			frenchButton.revalidate();
			frenchButton.repaint();
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
	private void processUserIDAndPassword(String useridString,
			String passwordString) {
		Properties props = new Properties();
		props.setProperty("bannerId", useridString);
		props.setProperty("password", passwordString);

		bannerIdField.setText("");
		passwordField.setText("");
		if (englshButton.isSelected()) {
			LocaleConfig.setLocale(new Locale("en", "US"));
		} else {
			LocaleConfig.setLocale(new Locale("fr", "FR"));
		}
		peon.authenticateLogin(props);
	}
}
