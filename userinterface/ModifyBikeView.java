package userinterface;
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
import javax.swing.SwingConstants;

import model.Bicycle;
import model.LocaleConfig;
import model.Peon;

public class ModifyBikeView extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private Peon peon;
	private JTextField bikeTextField;
	private JButton findButton;
	private JTextField makeTextField;
	private JTextField modelTextField;
	private JTextField serialNumberTextField;
	private JTextField locationOnCampusTextField;
	private JTextField descriptionTextField;
	private JButton submitButton;
	private JButton backButton;
	private JComboBox colorComboBox;
	private JComboBox bikeConditionComboBox;
	private JComboBox statusComboBox;
	private Map<String, Integer> colorMap;
	private Map<String, Integer> conditionMap;
	private Map<String, Integer> statusMap;
	private String bikeId;
	private ResourceBundle localizedBundle;

	public ModifyBikeView(Peon peon) {
		this.peon = peon;
		Locale currentLocale = LocaleConfig.currentLocale();
		localizedBundle = ResourceBundle.getBundle("BicycleStringsBundle", currentLocale);
		
		setupColorHash();
		setupConditionHash();
		statusHash();
		bikeId = new String();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		JLabel lbl = new JLabel(localizedBundle.getString("modifyBicycle"));
		Font myFont = new Font("Helvetica", Font.BOLD, 20);
		lbl.setFont(myFont);
		titlePanel.add(lbl);
		add(titlePanel);
		add(bikeSearchFunction());
		add(new JSeparator(SwingConstants.HORIZONTAL));
		add(dataEntryPanel());
		add(choiceBox());
		add(navigationPanel());
	}

	//Used to get value for ComboBoxes so French and English will be set to correct values
	private void setupColorHash() {
		colorMap = new HashMap<String, Integer>();
		colorMap.put("Red", 0);
		colorMap.put("Orange", 1);
		colorMap.put("Yellow", 2);
		colorMap.put("Green", 3);
		colorMap.put("Blue", 4);
		colorMap.put("Purple", 5);
		colorMap.put("Brown", 6);
		colorMap.put("Black", 7);
		colorMap.put("White", 8); 
	}

	private void setupConditionHash() {
		conditionMap = new HashMap<String, Integer>();
		conditionMap.put("New", 0);
		conditionMap.put("Good", 1);
		conditionMap.put("Fair", 2);
		conditionMap.put("Poor", 3);
	}

	private void statusHash() {
		statusMap = new HashMap<String, Integer>();
		statusMap.put("Available", 0);
		statusMap.put("Unavailable", 1);
	}

	// Create the main data entry fields
	private JPanel dataEntryPanel() {
		JPanel entryPanel = new JPanel();
		// set the layout for this panel
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
		//bikeConditionTextField = new JTextField(20);
		//bikeConditionTextField.addActionListener(this);
		bikeConditionComboBox = new JComboBox();
		String [] conditionPossibilities = {localizedBundle.getString("new"), localizedBundle.getString("good"),
				localizedBundle.getString("fair"), localizedBundle.getString("poor")};
		bikeConditionComboBox = new JComboBox(conditionPossibilities);
		bikeConditionComboBox.addActionListener(this);
		entryPanel.add(bikeConditionLabel);
		entryPanel.add(bikeConditionComboBox);
		//entryPanel.add(bikeConditionTextField);

		//Color
		JLabel colorLabel = new JLabel(localizedBundle.getString("color") + ": ");
		//colorTextField = new JTextField(20);
		//colorTextField.addActionListener(this);
		colorComboBox = new JComboBox();
		String [] colorPossibilities = {localizedBundle.getString("red"), localizedBundle.getString("orange"), localizedBundle.getString("yellow"),
				localizedBundle.getString("green"), localizedBundle.getString("blue"), localizedBundle.getString("purple"), localizedBundle.getString("brown"),
				localizedBundle.getString("black"), localizedBundle.getString("white")};
		colorComboBox = new JComboBox(colorPossibilities);
		colorComboBox.addActionListener(this);
		entryPanel.add(colorLabel);
		entryPanel.add(colorComboBox);
		//entryPanel.add(colorTextField);

		//Serial Number
		JLabel serialNumberLabel = new JLabel(localizedBundle.getString("serialNumber") + ": ");
		entryPanel.add(serialNumberLabel);
		serialNumberTextField = new JTextField(20);
		serialNumberTextField.addActionListener(this);
		entryPanel.add(serialNumberTextField);

		//Location
		JLabel locationOnCampusLabel = new JLabel(localizedBundle.getString("campusLocation") + ": ");
		entryPanel.add(locationOnCampusLabel);
		locationOnCampusTextField = new JTextField(20);
		locationOnCampusTextField.addActionListener(this);
		entryPanel.add(locationOnCampusTextField);

		//Description
		JLabel descriptionLabel = new JLabel(localizedBundle.getString("description") + ": ");
		entryPanel.add(descriptionLabel);
		descriptionTextField = new JTextField(20);
		descriptionTextField.addActionListener(this);
		entryPanel.add(descriptionTextField);
		return entryPanel;
	}


	private JPanel bikeSearchFunction() {
		//BikeId entry fields
		JPanel searchPanel = new JPanel();
		FlowLayout layoutOne = new FlowLayout(FlowLayout.CENTER);
		searchPanel.setLayout(layoutOne);
		JLabel bikeLabel = new JLabel(localizedBundle.getString("bicycleId") + ": ");
		bikeTextField = new JTextField(20);
		bikeTextField.addActionListener(this);
		findButton = new JButton(localizedBundle.getString("find"));
		findButton.addActionListener(this);
		searchPanel.add(bikeLabel);
		searchPanel.add(bikeTextField);
		searchPanel.add(findButton);
		return searchPanel;
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

	// Create the combo box
	private JPanel choiceBox() {
		JPanel cBox = new JPanel(); // default FlowLayout is fine
		FlowLayout f1 = new FlowLayout(FlowLayout.CENTER);
		f1.setVgap(8);
		f1.setHgap(25);
		cBox.setLayout(f1);
		JLabel rentalLabel = new JLabel(localizedBundle.getString("status"));
		cBox.add(rentalLabel);
		String [] rentalPossibilites = { localizedBundle.getString("available"), localizedBundle.getString("unavailable")};
		statusComboBox = new JComboBox(rentalPossibilites);
		statusComboBox.addActionListener(this);
		cBox.add(statusComboBox);
		return cBox;
	}

	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == submitButton) {
			//User clicked submit
			if(makeTextField.getText().equals("")) {
				JOptionPane.showMessageDialog(this, localizedBundle.getString("errorInvalidMake"), localizedBundle.getString("error"), JOptionPane.WARNING_MESSAGE);
			} else if(modelTextField.getText().equals("")) {
				JOptionPane.showMessageDialog(this, localizedBundle.getString("errorInvalidModel"), localizedBundle.getString("error"), JOptionPane.WARNING_MESSAGE);
			} else if(serialNumberTextField.getText().length() != 10 || !Peon.isNumber(serialNumberTextField.getText())) {
				JOptionPane.showMessageDialog(this, localizedBundle.getString("errorInvalidSerial"), localizedBundle.getString("error"), JOptionPane.WARNING_MESSAGE);
			} else if(locationOnCampusTextField.getText().equals("")) {
				JOptionPane.showMessageDialog(this, localizedBundle.getString("errorInvalidLocation"), localizedBundle.getString("error"), JOptionPane.WARNING_MESSAGE);
			} else {
				Properties bicycleProperties = new Properties();
				bicycleProperties.setProperty("bikeId",bikeId);
				bicycleProperties.setProperty("make",makeTextField.getText());
				bicycleProperties.setProperty("model",modelTextField.getText());
				bicycleProperties.setProperty("bikeCondition", (String)bikeConditionComboBox.getSelectedItem());
				bicycleProperties.setProperty("color",(String)colorComboBox.getSelectedItem());
				bicycleProperties.setProperty("serialNumber",serialNumberTextField.getText());
				bicycleProperties.setProperty("locationOnCampus",locationOnCampusTextField.getText());
				bicycleProperties.setProperty("description",descriptionTextField.getText());
				bicycleProperties.setProperty("status",(String)statusComboBox.getSelectedItem());
				if(peon.processUpdateBicycleData(bicycleProperties)) {
					JOptionPane.showMessageDialog(this, localizedBundle.getString("successUpdateBicycle"), localizedBundle.getString("success"), JOptionPane.PLAIN_MESSAGE);
					clearEntries();
					peon.createAndShowMainMenuView();
				}
			}
		} else if(event.getSource() == findButton) {
			//User clicked find button
			bikeId = bikeTextField.getText();
			Properties bicycleProps = new Properties();
			bicycleProps.setProperty("bikeId", bikeId);
			Bicycle bicycle = new Bicycle(bicycleProps);
			Properties bicycleProperties = bicycle.getBikeInfo(bikeTextField.getText());
			if(bicycleProperties == null) {
				JOptionPane.showMessageDialog(this, localizedBundle.getString("errorBicycleNotFound"), localizedBundle.getString("error"), JOptionPane.WARNING_MESSAGE);
				bikeTextField.setText("");
				return;
			}
			makeTextField.setText(bicycleProperties.getProperty("make"));
			modelTextField.setText(bicycleProperties.getProperty("model"));
			if(colorMap.containsKey(bicycleProperties.getProperty("color"))) {
				colorComboBox.setSelectedIndex((Integer) colorMap.get(bicycleProperties.getProperty("color")));
			}
			
			if(conditionMap.containsKey(bicycleProperties.getProperty("bikeCondition"))) {
				bikeConditionComboBox.setSelectedIndex((Integer) conditionMap.get(bicycleProperties.getProperty("bikeCondition")));
			}
			
			if(statusMap.containsKey(bicycleProperties.getProperty("status"))) {
				statusComboBox.setSelectedIndex((Integer) statusMap.get(bicycleProperties.getProperty("status")));
			}

			serialNumberTextField.setText(bicycleProperties.getProperty("serialNumber"));
			locationOnCampusTextField.setText(bicycleProperties.getProperty("locationOnCampus"));
			descriptionTextField.setText(bicycleProperties.getProperty("description"));
		} else if(event.getSource() == backButton) {
			clearEntries();
			peon.createAndShowMainMenuView();
		}
	}

	private void clearEntries() {
		makeTextField.setText("");
		modelTextField.setText("");
		serialNumberTextField.setText("");
		locationOnCampusTextField.setText("");
		descriptionTextField.setText("");
	}
}
