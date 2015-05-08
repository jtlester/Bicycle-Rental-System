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
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import model.DateLabelFormatter;
import model.LocaleConfig;
import model.Peon;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

public class WorkerView extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private Peon peon;
	private JTextField firstNameTextField;
	private JTextField lastNameTextField;
	private JTextField bannerTextField;
	private JTextField emailTextField;
	private JTextField phoneTextField;
	private JPasswordField passwordTextField;
	private JButton submitButton;
	private JButton backButton;
	private JComboBox adminComboBox;

	private JDatePickerImpl registrationDatePicker;

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
		add(dateCalendar());
		add(choiceBox());
		add(navigationPanel());
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
		passwordTextField = new JPasswordField(20);
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

	//Create Date
	private JPanel dateCalendar() {
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

	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == submitButton) {
			if(bannerTextField.getText().equals("") || !Peon.isNumber(bannerTextField.getText())) {
				JOptionPane.showMessageDialog(this, localizedBundle.getString("errorInvalidBannerID"), "Error", JOptionPane.WARNING_MESSAGE);
			} else if (passwordTextField.getPassword().equals("")) {
				JOptionPane.showMessageDialog(this, localizedBundle.getString("errorInvalidPassword"), "Error", JOptionPane.WARNING_MESSAGE);
			} else if(firstNameTextField.getText().equals("")) {
				JOptionPane.showMessageDialog(this, localizedBundle.getString("errorInvalidFirstName"), "Error", JOptionPane.WARNING_MESSAGE);
			} else if(lastNameTextField.getText().equals("")) {
				JOptionPane.showMessageDialog(this, localizedBundle.getString("errorInvalidLastName"), "Error", JOptionPane.WARNING_MESSAGE);
			} else if(phoneTextField.getText().length() != 11) {
				JOptionPane.showMessageDialog(this, localizedBundle.getString("errorInvalidPhoneNumber"), "Error", JOptionPane.WARNING_MESSAGE);
			} else if(emailTextField.getText() == null) {
				JOptionPane.showMessageDialog(this, localizedBundle.getString("errorInvalidEmail"), "Error", JOptionPane.WARNING_MESSAGE);
			} else {

				String day = String.valueOf(registrationDatePicker.getModel().getDay());
				String month = String.valueOf(registrationDatePicker.getModel().getMonth() + 1);
				String year = String.valueOf(registrationDatePicker.getModel().getYear());

				Properties workerProperties = new Properties();
				workerProperties.setProperty("bannerId",bannerTextField.getText());
				workerProperties.setProperty("password",String.valueOf(passwordTextField.getPassword()));
				workerProperties.setProperty("adminLevel",(String)adminComboBox.getSelectedItem());
				workerProperties.setProperty("firstName",firstNameTextField.getText());
				workerProperties.setProperty("lastName",lastNameTextField.getText());
				workerProperties.setProperty("phoneNumber",phoneTextField.getText());
				workerProperties.setProperty("email",emailTextField.getText());
				workerProperties.setProperty("registrationDate", day + "-" + month + "-" + year);

				if(peon.processWorkerData(workerProperties)) {
					JOptionPane.showMessageDialog(this, localizedBundle.getString("successWorker"), "Success", JOptionPane.PLAIN_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(this, localizedBundle.getString("errorWorker"), "Error", JOptionPane.PLAIN_MESSAGE);
				}
				clearEntries();
			}
		} else if (event.getSource() == backButton) {
			clearEntries();
			peon.createAndShowMainMenuView();
		}
	}

	private void clearEntries() {
		bannerTextField.setText("");
		passwordTextField.setText("");
		firstNameTextField.setText("");
		lastNameTextField.setText("");
		phoneTextField.setText("");
		emailTextField.setText("");
	}
}