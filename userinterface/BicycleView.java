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
import javax.swing.JTextField;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import model.DateLabelFormatter;
import model.LocaleConfig;
import model.Peon;

public class BicycleView extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;

	private Peon peon;
	private JTextField makeTextField;
	private JTextField modelTextField;
	private JComboBox bikeConditionComboBox;
	private JComboBox colorComboBox, locationComboBox;
	private JTextField serialNumberTextField;
	private JTextField locationOnCampusTextField;
	private JTextField descriptionTextField;
	private JButton submitButton;
	private JButton backButton;
	private ResourceBundle localizedBundle;
	private JDatePickerImpl rentDatePicker;

	public BicycleView(Peon peon) {
		this.peon = peon;

		Locale currentLocale = LocaleConfig.currentLocale();
		localizedBundle = ResourceBundle.getBundle("BicycleStringsBundle", currentLocale);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		//Title panel
		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		JLabel lbl = new JLabel(localizedBundle.getString("addBicycle"));
		Font myFont = new Font("Helvetica", Font.BOLD, 20);
		lbl.setFont(myFont);
		titlePanel.add(lbl);
		add(titlePanel);
		add(dataEntryPanel());
		add(datePanel());
		add(navigationPanel());
	}

	// Create the main data entry fields
	private JPanel dataEntryPanel() {
		JPanel entryPanel = new JPanel();
		//Set the layout for this panel
		entryPanel.setLayout(new GridLayout(7,2,20,20));
		entryPanel.setBorder(BorderFactory.createEmptyBorder(10,15,10,15));

		//Make
		JLabel makeLabel = new JLabel(localizedBundle.getString("make") + ": ");
		makeTextField = new JTextField(20);
		makeTextField.addActionListener(this);
		entryPanel.add(makeLabel);
		entryPanel.add(makeTextField);

		//Model
		JLabel modelLabel = new JLabel(localizedBundle.getString("model") + ": ");
		modelTextField = new JTextField(20);
		modelTextField.addActionListener(this);
		entryPanel.add(modelLabel);
		entryPanel.add(modelTextField);

		//Condition
		JLabel bikeConditionLabel = new JLabel(localizedBundle.getString("condition") + ": ");
		bikeConditionComboBox = new JComboBox();
		String [] conditionPossibilities = {"---", localizedBundle.getString("new"), localizedBundle.getString("good"),
				localizedBundle.getString("fair"), localizedBundle.getString("poor")};
		bikeConditionComboBox = new JComboBox(conditionPossibilities);
		bikeConditionComboBox.addActionListener(this);
		entryPanel.add(bikeConditionLabel);
		entryPanel.add(bikeConditionComboBox);

		//Color
		JLabel colorLabel = new JLabel(localizedBundle.getString("color") + ": ");
		colorComboBox = new JComboBox();
		String [] colorPossibilities = {"---", localizedBundle.getString("red"), localizedBundle.getString("orange"), localizedBundle.getString("yellow"),
				localizedBundle.getString("green"), localizedBundle.getString("blue"), localizedBundle.getString("purple"), localizedBundle.getString("brown"),
				localizedBundle.getString("black"), localizedBundle.getString("white")};
		colorComboBox = new JComboBox(colorPossibilities);
		colorComboBox.addActionListener(this);
		entryPanel.add(colorLabel);
		entryPanel.add(colorComboBox);

		//Serial Number
		JLabel serialNumberLabel = new JLabel(localizedBundle.getString("serialNumber") + ": ");
		entryPanel.add(serialNumberLabel);
		serialNumberTextField = new JTextField(20);
		serialNumberTextField.addActionListener(this);
		entryPanel.add(serialNumberTextField);

		//Location
		JLabel locationOnCampusLabel = new JLabel(localizedBundle.getString("campusLocation") + ": ");
		entryPanel.add(locationOnCampusLabel);
		String [] locations = {"---", "Seymour College Union", "Welcome Center", "Rakov", "Bramley", "Briggs", "Perry", "Mortimer", "McFarlane", "McClean", "McVicar", "Thompson", "Dobson", "Gordon" };
		locationComboBox = new JComboBox(locations);
		locationComboBox.addActionListener(this);
		entryPanel.add(locationComboBox);

		//Description
		JLabel descriptionLabel = new JLabel(localizedBundle.getString("description") + ": ");
		entryPanel.add(descriptionLabel);
		descriptionTextField = new JTextField(20);
		descriptionTextField.addActionListener(this);
		entryPanel.add(descriptionTextField); 
		return entryPanel;
	}

	private JPanel datePanel() {
		//Create Date Picker
		JPanel date = new JPanel();
		date.setLayout(new FlowLayout(FlowLayout.CENTER));
		JLabel registerDateLabel = new JLabel(localizedBundle.getString("registerDate") + ": ");
		date.add(registerDateLabel);

		UtilDateModel model = new UtilDateModel();
		model.setSelected(true);
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		rentDatePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());		 
		date.add(rentDatePicker);
		return date;
	}

	private JPanel navigationPanel() {
		// Create the navigation buttons
		JPanel navPanel = new JPanel();
		FlowLayout f1 = new FlowLayout(FlowLayout.CENTER);
		f1.setVgap(50);
		f1.setHgap(25);
		navPanel.setLayout(f1);

		// Create the buttons, listen for events, add them to the panel
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
			if(makeTextField.getText().equals("")) {
				JOptionPane.showMessageDialog(this, localizedBundle.getString("errorInvalidMake"), localizedBundle.getString("error"), JOptionPane.ERROR_MESSAGE);
			} else if(modelTextField.getText().equals("")) {
				JOptionPane.showMessageDialog(this, localizedBundle.getString("errorInvalidModel"), localizedBundle.getString("error"), JOptionPane.ERROR_MESSAGE);
			} else if(bikeConditionComboBox.getSelectedIndex() == 0 || bikeConditionComboBox.getSelectedIndex() == -1) {
				JOptionPane.showMessageDialog(this, localizedBundle.getString("errorInvalidCondition"), localizedBundle.getString("error"), JOptionPane.ERROR_MESSAGE);
			} else if(colorComboBox.getSelectedIndex() == 0 || colorComboBox.getSelectedIndex() == -1) {
				JOptionPane.showMessageDialog(this, localizedBundle.getString("errorInvalidColor"), localizedBundle.getString("error"), JOptionPane.ERROR_MESSAGE);
			} else if(serialNumberTextField.getText().length() != 10 || !Peon.isNumber(serialNumberTextField.getText())) {
				JOptionPane.showMessageDialog(this, localizedBundle.getString("errorInvalidSerial"), localizedBundle.getString("error"), JOptionPane.ERROR_MESSAGE);
			} else if(locationComboBox.getSelectedIndex() == 0 || locationComboBox.getSelectedIndex() == -1) {
				JOptionPane.showMessageDialog(this, localizedBundle.getString("errorInvalidLocation"), localizedBundle.getString("error"), JOptionPane.ERROR_MESSAGE);
			} else {
				//The form is valid
				String day = String.valueOf(rentDatePicker.getModel().getDay());
				String month = String.valueOf(rentDatePicker.getModel().getMonth() + 1);
				String year = String.valueOf(rentDatePicker.getModel().getYear());

				Properties bicycleProperties = new Properties();
				bicycleProperties.setProperty("make",makeTextField.getText());
				bicycleProperties.setProperty("model",modelTextField.getText());
				bicycleProperties.setProperty("bikeCondition", (String)bikeConditionComboBox.getSelectedItem());
				bicycleProperties.setProperty("color",(String)colorComboBox.getSelectedItem());
				bicycleProperties.setProperty("serialNumber",serialNumberTextField.getText());
				bicycleProperties.setProperty("locationOnCampus",(String)locationComboBox.getSelectedItem());
				bicycleProperties.setProperty("description",descriptionTextField.getText());
				bicycleProperties.setProperty("status", "Available");
				bicycleProperties.setProperty("dateRegistered", day + "-" + month + "-" + year);

				if(peon.processBicycleData(bicycleProperties)) {
					JOptionPane.showMessageDialog(this, localizedBundle.getString("successBicycle"), localizedBundle.getString("success"), JOptionPane.PLAIN_MESSAGE);
					clearEntries();
					peon.createAndShowMainMenuView();
				} else {
					JOptionPane.showMessageDialog(this, localizedBundle.getString("errorBicycle"), localizedBundle.getString("error"), JOptionPane.ERROR_MESSAGE);
				}
				clearEntries();
			}
		} else if(event.getSource() == backButton) {
			clearEntries();
			peon.createAndShowMainMenuView();
		}
	}

	private void clearEntries() {
		//Clear entry boxes
		makeTextField.setText("");
		modelTextField.setText("");
		serialNumberTextField.setText("");
		locationOnCampusTextField.setText("");
		descriptionTextField.setText("");
	}
}