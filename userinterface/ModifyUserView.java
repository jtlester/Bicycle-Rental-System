// specify the package
package userinterface;

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
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import model.LocaleConfig;
import model.Peon;
import model.User;

//ModifyUserView
public class ModifyUserView extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;

	private Peon peon;
	private JTextField firstNameTextField;
	private JTextField lastNameTextField;
	private JTextField bannerIdTextField;
	private JTextField emailTextField;
	private JTextField phoneTextField;
	private JButton submitButton;
	private JButton backButton;
	private JButton findButton;
	private ResourceBundle localizedBundle;
	private String bannerId;

	public ModifyUserView(Peon peon) {
		this.peon = peon;
		Locale currentLocale = LocaleConfig.currentLocale();
		localizedBundle = ResourceBundle.getBundle("BicycleStringsBundle", currentLocale);

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		JLabel lbl = new JLabel(localizedBundle.getString("modifyUser"));
		Font myFont = new Font("Helvetica", Font.BOLD, 20);
		lbl.setFont(myFont);
		titlePanel.add(lbl);
		add(titlePanel);
		add(userSearchFunction());
		add(new JSeparator(SwingConstants.HORIZONTAL));
		add(dataEntryPanel());
		add(navigationPanel());
	}

	private JPanel userSearchFunction() {
		//userId entry fields
		JPanel searchPanel = new JPanel();
		FlowLayout layoutOne = new FlowLayout(FlowLayout.CENTER);
		searchPanel.setLayout(layoutOne);

		JLabel bannerIdLabel = new JLabel(localizedBundle.getString("renterBannerId") + ": ");
		bannerIdTextField = new JTextField(20);
		bannerIdTextField.addActionListener(this);
		findButton = new JButton(localizedBundle.getString("find"));
		findButton.addActionListener(this);
		searchPanel.add(bannerIdLabel);
		searchPanel.add(bannerIdTextField);
		searchPanel.add(findButton);
		return searchPanel;
	}

	private JPanel dataEntryPanel() {
		JPanel entryPanel = new JPanel();
		// set the layout for this panel
		entryPanel.setLayout(new GridLayout(7,2,20,20));
		entryPanel.setBorder(BorderFactory.createEmptyBorder(10,15,10,15));

		// data entry fields        
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
	private JPanel navigationPanel() {
		JPanel navPanel = new JPanel();     // default FlowLayout is fine
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

	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == submitButton) {
			if(bannerIdTextField.getText().equals("") || !Peon.isNumber(bannerId)) {
				JOptionPane.showMessageDialog(this, localizedBundle.getString("errorInvalidBannerID"), "Error", JOptionPane.WARNING_MESSAGE);
			} else if(firstNameTextField.getText().equals("")) {
				JOptionPane.showMessageDialog(this, localizedBundle.getString("errorInvalidFirstName"), "Error", JOptionPane.WARNING_MESSAGE);
			} else if(lastNameTextField.getText().equals("")) {
				JOptionPane.showMessageDialog(this, localizedBundle.getString("errorInvalidLastName"), "Error", JOptionPane.WARNING_MESSAGE);
			} else if(phoneTextField.getText().length() <= 10) {
				JOptionPane.showMessageDialog(this, localizedBundle.getString("errorInvalidPhoneNumber"), "Error", JOptionPane.WARNING_MESSAGE);
			} else if(emailTextField.getText().equals("")) {
				JOptionPane.showMessageDialog(this, localizedBundle.getString("errorInvalidEmail"), "Error", JOptionPane.WARNING_MESSAGE);
			} else {
				Properties  userProperties = new Properties();
				userProperties.setProperty("bannerId", bannerId);
				userProperties.setProperty("firstName",firstNameTextField.getText());
				userProperties.setProperty("lastName",lastNameTextField.getText());
				userProperties.setProperty("phoneNumber",phoneTextField.getText());
				userProperties.setProperty("email",emailTextField.getText());
				if(peon.processUpdateUserData(userProperties)) {
					JOptionPane.showMessageDialog(this, localizedBundle.getString("successUpdateUser"), "Success", JOptionPane.PLAIN_MESSAGE);
					clearEntries();
					peon.createAndShowMainMenuView();
				}
			}
		} else if(event.getSource() == findButton) {
			//User clicked find button
			bannerId = bannerIdTextField.getText();
			Properties userBanner = new Properties();
			userBanner.setProperty("bannerId", bannerIdTextField.getText());
			User user = new User(userBanner);
			Properties userProperties = user.userInfo(userBanner);
			if(userProperties == null) {
				JOptionPane.showMessageDialog(this, localizedBundle.getString("errorUserNotFound"), "Error", JOptionPane.WARNING_MESSAGE);
				bannerIdTextField.setText("");
				return;
			}
			firstNameTextField.setText(userProperties.getProperty("firstName"));
			lastNameTextField.setText(userProperties.getProperty("lastName"));
			phoneTextField.setText(userProperties.getProperty("phoneNumber"));
			emailTextField.setText(userProperties.getProperty("email"));
		} else if(event.getSource() == backButton) {
			clearEntries();
			peon.createAndShowMainMenuView();
		}
	}

	private void clearEntries() {
		bannerIdTextField.setText("");
		firstNameTextField.setText("");
		lastNameTextField.setText("");
		phoneTextField.setText("");
		emailTextField.setText("");
	}
}