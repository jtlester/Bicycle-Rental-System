// specify the package
package userinterface;
// system imports
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;

import model.Worker;
import model.LocaleConfig;
import model.Peon;

public class ModifyWorkerView extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;
	private Peon peon;
	private JTextField bannerIdTextField, firstNameTextField, lastNameTextField, phoneNumberTextField, emailTextField;
	private JComboBox adminLevelComboBox;
	private JPasswordField passwordTextField;
	private JButton findButton, backButton, submitButton;
	private ResourceBundle localizedBundle;
	private Worker worker;
	private String bannerId;
	private Map adminHashMap;

	public ModifyWorkerView(Peon p) {
		peon = p;
		Locale currentLocale = LocaleConfig.currentLocale();
		localizedBundle = ResourceBundle.getBundle("BicycleStringsBundle", currentLocale);
		adminHash();

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));

		JLabel lbl = new JLabel(localizedBundle.getString("modifyWorker"));
		Font myFont = new Font("Helvetica", Font.BOLD, 20);
		lbl.setFont(myFont);
		titlePanel.add(lbl);
		add(titlePanel);
		add(createWorkerSearch());
		add(new JSeparator(SwingConstants.HORIZONTAL));
		add(createEditFields());
		add(createAdminBox());
		add(navigationPanel());
	}

	private JPanel createWorkerSearch() {
		//800# entry numbers
		JPanel workerEntryPanel = new JPanel();
		FlowLayout layoutOne = new FlowLayout(FlowLayout.CENTER);
		workerEntryPanel.setLayout(layoutOne);

		JLabel bannerIdLabel = new JLabel(localizedBundle.getString("bannerID"));
		bannerIdTextField = new JTextField(9);
		bannerIdTextField.addActionListener(this);

		findButton = new JButton(localizedBundle.getString("find"));
		findButton.addActionListener(this);

		workerEntryPanel.add(bannerIdLabel);
		workerEntryPanel.add(bannerIdTextField);
		workerEntryPanel.add(findButton);	
		return workerEntryPanel;
	}

	private JPanel createEditFields() {
		JPanel entryPanel = new JPanel();
		entryPanel.setLayout(new GridLayout(7,2,15,15));
		entryPanel.setBorder(BorderFactory.createEmptyBorder(10,15,10,15));

		//Password Fields
		JLabel passwordLabel = new JLabel(localizedBundle.getString("password") + ": ");
		passwordTextField = new JPasswordField(20);
		passwordTextField.addActionListener(this);
		entryPanel.add(passwordLabel);
		entryPanel.add(passwordTextField);

		//FirstName Field
		JLabel firstNameLabel = new JLabel(localizedBundle.getString("firstName") + ": ");
		firstNameTextField = new JTextField(20);
		firstNameTextField.addActionListener(this);
		entryPanel.add(firstNameLabel);
		entryPanel.add(firstNameTextField);

		//Last Name
		JLabel lastNameLabel = new JLabel(localizedBundle.getString("lastName") + ": ");
		lastNameTextField = new JTextField(20);
		lastNameTextField.addActionListener(this);
		entryPanel.add(lastNameLabel);
		entryPanel.add(lastNameTextField);

		//phone numbers
		JLabel phoneLabel = new JLabel(localizedBundle.getString("phoneNumber") + ": ");
		phoneNumberTextField = new JTextField(20);
		phoneNumberTextField.addActionListener(this);
		entryPanel.add(phoneLabel);
		entryPanel.add(phoneNumberTextField);

		//Email
		JLabel emailLabel = new JLabel(localizedBundle.getString("email") + ": ");
		emailTextField = new JTextField(20);
		emailTextField.addActionListener(this);
		entryPanel.add(emailLabel);
		entryPanel.add(emailTextField);

		return entryPanel;
	}

	private JPanel createAdminBox() {
		JPanel adminPanel = new JPanel();
		adminPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

		JLabel adminLabel = new JLabel(localizedBundle.getString("administrator") + ": ");
		adminLevelComboBox = new JComboBox();
		String [] adminChoices = {localizedBundle.getString("yes"), localizedBundle.getString("no")};
		adminLevelComboBox = new JComboBox(adminChoices);
		adminLevelComboBox.addActionListener(this);
		adminPanel.add(adminLabel);
		adminPanel.add(adminLevelComboBox);

		return adminPanel;
	}

	// Create the navigation buttons
	private JPanel navigationPanel() {
		JPanel navPanel = new JPanel(); // default FlowLayout is fine
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

	private void adminHash() {
		adminHashMap = new HashMap();
		adminHashMap.put("Yes", 0);
		adminHashMap.put("No", 1);
	}

	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == submitButton) {
			Properties newWorkerProperties = new Properties();
			if (passwordTextField.getPassword().equals("")) {
				JOptionPane.showMessageDialog(this, localizedBundle.getString("errorInvalidPassword"), localizedBundle.getString("error"), JOptionPane.WARNING_MESSAGE);
			} else if(firstNameTextField.getText().equals("")) {
				JOptionPane.showMessageDialog(this, localizedBundle.getString("errorInvalidFirstName"), localizedBundle.getString("error"), JOptionPane.WARNING_MESSAGE);
			} else if(lastNameTextField.getText().equals("")) {
				JOptionPane.showMessageDialog(this, localizedBundle.getString("errorInvalidLastName"), localizedBundle.getString("error"), JOptionPane.WARNING_MESSAGE);
			} else if(phoneNumberTextField.getText().length() <= 10) {
				JOptionPane.showMessageDialog(this, localizedBundle.getString("errorInvalidPhoneNumber"), localizedBundle.getString("error"), JOptionPane.WARNING_MESSAGE);
			} else if(emailTextField.getText() == null) {
				JOptionPane.showMessageDialog(this, localizedBundle.getString("errorInvalidEmail"), localizedBundle.getString("error"), JOptionPane.WARNING_MESSAGE);
			} else {
				newWorkerProperties.setProperty("bannerId", bannerId);
				newWorkerProperties.setProperty("password", String.valueOf(passwordTextField.getPassword()));
				newWorkerProperties.setProperty("adminLevel", (String)adminLevelComboBox.getSelectedItem());
				newWorkerProperties.setProperty("firstName", firstNameTextField.getText());
				newWorkerProperties.setProperty("lastName", lastNameTextField.getText());
				newWorkerProperties.setProperty("phoneNumber", phoneNumberTextField.getText());
				newWorkerProperties.setProperty("email", emailTextField.getText());

				if(peon.processUpdateWorkerData(newWorkerProperties)) {
					JOptionPane.showMessageDialog(this, localizedBundle.getString("successUpdate"), localizedBundle.getString("success"), JOptionPane.PLAIN_MESSAGE);
					clearFields();
					peon.createAndShowMainMenuView();
				}
			}
		} else if(event.getSource() == findButton) {
			//User clicked find button
			bannerId = bannerIdTextField.getText();
			Properties workerBanner = new Properties();
			workerBanner.setProperty("bannerId", bannerId);
			worker = new Worker(workerBanner);
			Properties workerProperties = worker.workerInfo(workerBanner);
			if(workerProperties == null) {
				JOptionPane.showMessageDialog(this, localizedBundle.getString("errorWorkerNotFound"), localizedBundle.getString("error"), JOptionPane.WARNING_MESSAGE);
				bannerIdTextField.setText("");
				return;
			}
			passwordTextField.setText(workerProperties.getProperty("password"));
			firstNameTextField.setText(workerProperties.getProperty("firstName"));
			lastNameTextField.setText(workerProperties.getProperty("lastName"));
			phoneNumberTextField.setText(workerProperties.getProperty("phoneNumber"));
			emailTextField.setText(workerProperties.getProperty("email"));
			if(adminHashMap.containsKey(workerProperties.getProperty("adminLevel"))) {
				adminLevelComboBox.setSelectedIndex((Integer) adminHashMap.get(workerProperties.getProperty("adminLevel")));
			}
		} else if(event.getSource() == backButton) {
			peon.createAndShowMainMenuView();
		}
	}

	private void clearFields() {
		bannerIdTextField.setText("");
		passwordTextField.setText("");
		firstNameTextField.setText("");
		lastNameTextField.setText("");
		phoneNumberTextField.setText("");
		emailTextField.setText("");
	}

}