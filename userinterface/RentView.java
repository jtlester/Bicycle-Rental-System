// specify the package
package userinterface;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
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

import model.Bicycle;
import model.DateLabelFormatter;
import model.LocaleConfig;
import model.Peon;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

public class RentView extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;

	private Peon peon;
	private MessageView statusLog;
	private JTextField bannerTextField, statusTextField, bikeTextField, makeTextField, modelTextField, colorTextField, serialNumberTextField, locationOnCampusTextField, descriptionTextField;
	private JButton backButton, submitButton, findButton;
	private JDatePickerImpl rentDatePicker;
	private JDatePickerImpl dueDatePicker;
	private ResourceBundle localizedBundle;
	private String bikeId;

	public RentView(Peon peon)	{
		this.peon = peon;
		Locale currentLocale = LocaleConfig.currentLocale();
		localizedBundle = ResourceBundle.getBundle("BicycleStringsBundle", currentLocale);

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));

		JLabel mainLabel = new JLabel(localizedBundle.getString("rentBicycle"));
		Font lblFont = new Font("Helvetica", Font.BOLD, 20);
		mainLabel.setFont(lblFont);
		titlePanel.add(mainLabel);
		add(titlePanel);
		add(dataEntryPanel());
		add(bikeSearchFunction());
		add(new JSeparator(SwingConstants.HORIZONTAL));
		add(bicycleField());
		add(new JSeparator(SwingConstants.HORIZONTAL));
		add(rentDatePanel());
		add(dueDatePanel());
		add(navigationPanel());		
	}

	private JPanel dataEntryPanel() {
		JPanel entryPanel = new JPanel();

		//SET LAYOUT
		entryPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		entryPanel.setBorder(BorderFactory.createEmptyBorder(10,5,10,25));

		//ENTRY FIELDS 
		//BANNER ID
		JLabel bannerLabel = new JLabel(localizedBundle.getString("renterBannerId") + ": ");
		bannerTextField = new JTextField(20);
		bannerTextField.addActionListener(this);
		entryPanel.add(bannerLabel);
		entryPanel.add(bannerTextField);
		return entryPanel;
	}

	private JPanel bikeSearchFunction() {
		//BIKE ID ENTRY FELDS
		JPanel bikeSearchPanel = new JPanel();
		FlowLayout layoutOne = new FlowLayout(FlowLayout.CENTER);
		bikeSearchPanel.setLayout(layoutOne);

		JLabel bikeLabel = new JLabel(localizedBundle.getString("bicycleId") + ": ");
		bikeTextField = new JTextField(20);
		bikeTextField.addActionListener(this);
		findButton = new JButton(localizedBundle.getString("find"));
		findButton.addActionListener(this);
		bikeSearchPanel.add(bikeLabel);
		bikeSearchPanel.add(bikeTextField);
		bikeSearchPanel.add(findButton);
		return bikeSearchPanel;
	}

	private JPanel bicycleField() {
		JPanel entryPanel = new JPanel();
		// set the layout for this panel
		entryPanel.setLayout(new GridLayout(7,2,20,20));
		entryPanel.setBorder(BorderFactory.createEmptyBorder(10,15,10,15));

		//Make
		JLabel makeLabel = new JLabel(localizedBundle.getString("make") + ": ");
		makeTextField = new JTextField(20);
		makeTextField.addActionListener(this);
		makeTextField.setEditable(false);
		entryPanel.add(makeLabel);
		entryPanel.add(makeTextField);

		//Model
		JLabel modelLabel = new JLabel(localizedBundle.getString("model") + ": ");
		modelTextField = new JTextField(20);
		modelTextField.addActionListener(this);
		modelTextField.setEditable(false);
		entryPanel.add(modelLabel);
		entryPanel.add(modelTextField);

		//Color
		JLabel colorLabel = new JLabel(localizedBundle.getString("color") + ": ");
		colorTextField = new JTextField(20);
		colorTextField.addActionListener(this);
		colorTextField.setEditable(false);
		entryPanel.add(colorLabel);
		entryPanel.add(colorTextField);

		//Serial Number
		JLabel serialNumberLabel = new JLabel(localizedBundle.getString("serialNumber") + ": ");
		entryPanel.add(serialNumberLabel);
		serialNumberTextField = new JTextField(20);
		serialNumberTextField.addActionListener(this);
		serialNumberTextField.setEditable(false);
		entryPanel.add(serialNumberTextField);

		//Location
		JLabel locationOnCampusLabel = new JLabel(localizedBundle.getString("campusLocation") + ": ");
		entryPanel.add(locationOnCampusLabel);
		locationOnCampusTextField = new JTextField(20);
		locationOnCampusTextField.addActionListener(this);
		locationOnCampusTextField.setEditable(false);
		entryPanel.add(locationOnCampusTextField);

		//Description
		JLabel descriptionLabel = new JLabel(localizedBundle.getString("description") + ": ");
		entryPanel.add(descriptionLabel);
		descriptionTextField = new JTextField(20);
		descriptionTextField.addActionListener(this);
		descriptionTextField.setEditable(false);
		entryPanel.add(descriptionTextField); 

		//Status
		JLabel statusLabel = new JLabel(localizedBundle.getString("status"));
		entryPanel.add(statusLabel);
		statusTextField = new JTextField(20);
		statusTextField.addActionListener(this);
		statusTextField.setEditable(false);
		entryPanel.add(statusTextField);

		return entryPanel;

	}

	//Create Date
	private JPanel rentDatePanel() {
		JPanel rentDatePanel = new JPanel();
		rentDatePanel.setLayout(new FlowLayout(FlowLayout.CENTER));

		JLabel rentDateLabel = new JLabel(localizedBundle.getString("rentDate") + ": ");
		rentDatePanel.add(rentDateLabel);

		UtilDateModel model = new UtilDateModel();
		model.setSelected(true);
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		rentDatePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());		 
		rentDatePanel.add(rentDatePicker);
		return rentDatePanel;
	}

	//Create Date
	private JPanel dueDatePanel() {
		JPanel dueDatePanel = new JPanel();
		dueDatePanel.setLayout(new FlowLayout(FlowLayout.CENTER));

		JLabel rentDateLabel = new JLabel(localizedBundle.getString("dueDate") + ": ");
		dueDatePanel.add(rentDateLabel);

		UtilDateModel model = new UtilDateModel();
		model.setSelected(true);
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		dueDatePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());		 
		dueDatePanel.add(dueDatePicker);
		return dueDatePanel;
	}

	// Create the navigation buttons
	private JPanel navigationPanel() {
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
			if(bannerTextField.getText().equals("") || !Peon.isNumber(bannerTextField.getText()) || bannerTextField.getText().length() != 9) {
				JOptionPane.showMessageDialog(this, localizedBundle.getString("errorInvalidBannerID"), localizedBundle.getString("error"), JOptionPane.ERROR_MESSAGE);
			} else if(bikeTextField.getText().isEmpty() || bikeId == null) {
				JOptionPane.showMessageDialog(this, localizedBundle.getString("errorBicycleNotFound"), localizedBundle.getString("error"), JOptionPane.ERROR_MESSAGE);
			} else if(!Peon.isValidDate((Date)rentDatePicker.getModel().getValue(), (Date)dueDatePicker.getModel().getValue())) {
				JOptionPane.showMessageDialog(this, localizedBundle.getString("errorInvalidDate"), localizedBundle.getString("error"), JOptionPane.ERROR_MESSAGE);
			} else {
				String day = String.valueOf(rentDatePicker.getModel().getDay());
				String month = String.valueOf(rentDatePicker.getModel().getMonth() + 1);
				String year = String.valueOf(rentDatePicker.getModel().getYear());

				String dayDue = String.valueOf(dueDatePicker.getModel().getDay());
				String monthDue = String.valueOf(dueDatePicker.getModel().getMonth() + 1);
				String yearDue = String.valueOf(dueDatePicker.getModel().getYear());

				Properties rentBikeProperties = new Properties();
				rentBikeProperties.setProperty("bikeId", bikeTextField.getText());
				rentBikeProperties.setProperty("bannerId", bannerTextField.getText());
				rentBikeProperties.setProperty("rentalDate", day + "-" + month + "-" + year);
				rentBikeProperties.setProperty("dueDate", dayDue + "-" + monthDue + "-" + yearDue);	
				rentBikeProperties.setProperty("status", "Active");

				Properties statusChange = new Properties();
				statusChange.setProperty("bikeId", bikeTextField.getText());
				statusChange.setProperty("status", "Unavailable");
				if(peon.processRentData(rentBikeProperties) && peon.changeStatus(statusChange)) {
					JOptionPane.showMessageDialog(this, localizedBundle.getString("successRent") + "\n" + localizedBundle.getString("bicycle") + ": " + makeTextField.getText() + " " + modelTextField.getText() + "\n" + localizedBundle.getString("dueDate") + ": " + dayDue + "-" + monthDue + "-" + yearDue, "Success", JOptionPane.PLAIN_MESSAGE);
					clearTextFields();
					peon.createAndShowMainMenuView();
				}
			}
		} else if(event.getSource() == findButton) {
			//User clicked find button
			Properties bicycleProps = new Properties();
			bikeId = bikeTextField.getText();
			bicycleProps.setProperty("bikeId", bikeId);
			Bicycle bicycle = new Bicycle(bicycleProps);
			Properties bicycleProperties = bicycle.getBikeInfo(bikeTextField.getText());
			if(bicycleProperties == null) {
				JOptionPane.showMessageDialog(this, localizedBundle.getString("errorBicycleNotFound"), localizedBundle.getString("error"), JOptionPane.ERROR_MESSAGE);
				bikeTextField.setText("");
				return;
			}
			makeTextField.setText(bicycleProperties.getProperty("make"));
			modelTextField.setText(bicycleProperties.getProperty("model"));
			colorTextField.setText(bicycleProperties.getProperty("color"));
			serialNumberTextField.setText(bicycleProperties.getProperty("make"));
			locationOnCampusTextField.setText(bicycleProperties.getProperty("locationOnCampus"));
			descriptionTextField.setText(bicycleProperties.getProperty("description"));
			if(!bicycleProperties.getProperty("status").equals("Available")) {
				statusTextField.setText(localizedBundle.getString("unavailable"));
				submitButton.setEnabled(false);
				statusTextField.setForeground(Color.red);
			} else {
				statusTextField.setText(localizedBundle.getString("available"));
				submitButton.setEnabled(true);
				statusTextField.setForeground(Color.black);
			}
		} else if(event.getSource() == backButton) {
			clearTextFields();
			peon.createAndShowMainMenuView();
		}
	}

	private void clearTextFields() {
		bannerTextField.setText("");
		bikeTextField.setText("");
		makeTextField.setText("");
		modelTextField.setText("");
		colorTextField.setText("");
		serialNumberTextField.setText("");
		locationOnCampusTextField.setText("");
		descriptionTextField.setText("");
		submitButton.setEnabled(true);
		statusTextField.setForeground(Color.black);
	}
}