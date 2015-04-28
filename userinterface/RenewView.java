// specify the package
package userinterface;
// system imports
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Locale;
import java.awt.GridLayout;
import java.util.Properties;
import java.util.EventObject;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.text.NumberFormat;
import java.awt.*;
import javax.swing.*;
// project imports
import impresario.IModel;
import model.*;

public class RenewView extends JPanel implements ActionListener {
    private Peon peon;
    private JTextField bikeTextField;
    private JButton findButton;
    private JLabel makeLabel;
    private JTextField makeTextField;
    private JLabel modelLabel;
    private JTextField modelTextField;
    private JLabel bikeConditionLabel;
    private JComboBox bikeConditionComboBox;
    //private JTextField bikeConditionTextField;
    private JLabel colorLabel;
    //private JTextField colorTextField;
    private JComboBox colorComboBox;
    private JLabel serialNumberLabel;
    private JTextField serialNumberTextField;
    private JLabel locationOnCampusLabel;
    private JTextField locationOnCampusTextField;
    private JLabel descriptionLabel;
    private JTextField descriptionTextField;
    private JComboBox rentalComboBox;
    private JButton submitButton;
    private JButton backButton;
    private MessageView statusLog;
    private Map color;
    private Map condition;
    private Map status;
    private String BIKEID;
    public Bicycle Bike;
    public ResourceBundle localizedBundle;
    
    
    public RenewView(Peon p) {
        peon = p;
        Locale currentLocale = LocaleConfig.currentLocale();
        localizedBundle = ResourceBundle.getBundle("BicycleStringsBundle", currentLocale);
        setupColorHash();
        setupConditionHash();
        statusHash();
        BIKEID = new String();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel lbl = new JLabel(localizedBundle.getString("renewBicycle"));
        Font myFont = new Font("Helvetica", Font.BOLD, 20);
        lbl.setFont(myFont);
        titlePanel.add(lbl);
        add(titlePanel);

        add(createBikeSearchFunction());
        add(new JSeparator(SwingConstants.HORIZONTAL));
        add(dataEntryPanel());
        add(choiceBox());
        add(navigationPanel());
        add(createStatusLog("                            "));
    }
    private JPanel createStatusLog(String initialMessage) {
        statusLog = new MessageView(initialMessage);
        return statusLog;
    }
    //USED TO GET VALUE FOR ComboBoxes so French and English will be set to correct values
    private void setupColorHash()
    {
         color = new HashMap();
         color.put("Red", 0);
         color.put("Orange", 1);
         color.put("Yellow", 2);
         color.put("Green", 3);
         color.put("Blue", 4);
         color.put("Purple", 5);
         color.put("Brown", 6);
         color.put("Black", 7);
         color.put("White", 8); 
    }
    private void setupConditionHash()
    {
         condition = new HashMap();
         condition.put("New", 0);
         condition.put("Good", 1);
         condition.put("Fair", 2);
         condition.put("Poor", 3);
    }
    private void statusHash()
    {
        status = new HashMap();
         status.put("In", 0);
         status.put("Out", 1);
        
    }
    // Create the main data entry fields
    //-------------------------------------------------------------
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
    
        
    private JPanel createBikeSearchFunction()
    {
                //BIKE ID ENTRY FELDS
        JPanel temp = new JPanel();
        FlowLayout layoutOne = new FlowLayout(FlowLayout.CENTER);
        //layoutOne.setHgap(2);
        temp.setLayout(layoutOne);
        //temp.setBorder(BorderFactory.createEmptyBorder(10,15,10,15));

        JLabel bikeLabel = new JLabel(localizedBundle.getString("bicycleSerialNumber") + ": ");
        bikeTextField = new JTextField(20);
        bikeTextField.addActionListener(this);
        findButton = new JButton(localizedBundle.getString("find"));
        findButton.addActionListener(this);
        temp.add(bikeLabel);
        temp.add(bikeTextField);
        temp.add(findButton);
        
        return temp;
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
        JLabel rentalLabel = new JLabel(localizedBundle.getString("inOrOut"));
        cBox.add(rentalLabel);
        String [] rentalPossibilites = { localizedBundle.getString("in"), localizedBundle.getString("out")};
        rentalComboBox = new JComboBox(rentalPossibilites);
        rentalComboBox.addActionListener(this);
        cBox.add(rentalComboBox);
        return cBox;
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
            if(makeTextField.getText().equals(""))
            {
                peon.errorMessagePopup("make");
            }
            else if(modelTextField.getText().equals(""))
            {
                peon.errorMessagePopup("model");
            }
            else if(serialNumberTextField.getText().length() != 10)
            {
                peon.errorMessagePopup("serialNumber");
            }
            else if(locationOnCampusTextField.getText().equals(""))
            {
                peon.errorMessagePopup("location");
            }
            else {
                Properties bicycleProperties = new Properties();
                bicycleProperties.setProperty("bikeId",BIKEID);
                bicycleProperties.setProperty("make",makeTextField.getText());
                bicycleProperties.setProperty("model",modelTextField.getText());
                bicycleProperties.setProperty("bikeCondition", (String)bikeConditionComboBox.getSelectedItem());
                bicycleProperties.setProperty("color",(String)colorComboBox.getSelectedItem());
                bicycleProperties.setProperty("serialNumber",serialNumberTextField.getText());
                bicycleProperties.setProperty("locationOnCampus",locationOnCampusTextField.getText());
                bicycleProperties.setProperty("description",descriptionTextField.getText());
                bicycleProperties.setProperty("status",(String)rentalComboBox.getSelectedItem());
                peon.processRenewData(bicycleProperties);
                makeTextField.setText("");
                modelTextField.setText("");
                //bikeConditionTextField.setText("");
                //colorTextField.setText("");
                serialNumberTextField.setText("");
                locationOnCampusTextField.setText("");
                descriptionTextField.setText("");
            }
        }
        else if(event.getSource() == findButton)
        {
            //System.out.println("Clicked find button");
            Properties bicycleId = new Properties();
            bicycleId.setProperty("bikeId", bikeTextField.getText());
            BIKEID = bikeTextField.getText();
            Bike = new Bicycle(bicycleId);
            Bike.getBikeInfo(bicycleId);
            
            makeTextField.setText(Bike.getMake());
            modelTextField.setText(Bike.getModel());
            //colorTextField.setText(Bike.getColor());
            //colorComboBox.setSelected(Bike.getColor());
            if(color.containsKey(Bike.getColor()))
            {
                 colorComboBox.setSelectedIndex((Integer) color.get(Bike.getColor()));
            }
            if(condition.containsKey(Bike.getCondition()))
            {
                 bikeConditionComboBox.setSelectedIndex((Integer) condition.get(Bike.getCondition()));
            }
            if(status.containsKey(Bike.getStatus()))
            {
                 rentalComboBox.setSelectedIndex((Integer) status.get(Bike.getStatus()));
            }

            serialNumberTextField.setText(Bike.getSerial());
            locationOnCampusTextField.setText(Bike.getLocation());
            descriptionTextField.setText(Bike.getDescription());
        }
        else if(event.getSource() == backButton) {
            peon.bicycleDataDone();
        }
    }
}
