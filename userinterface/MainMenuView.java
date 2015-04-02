// specify the package
package userinterface;

// system imports
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Locale;
import java.util.Properties;
import java.util.EventObject;
import java.util.ResourceBundle;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.text.NumberFormat;



// project imports
import impresario.IModel;
import model.*;

public class MainMenuView extends JPanel implements ActionListener
{
	private Peon man;
	private JButton insertNewWorkerButton, doneButton;

	private MessageView statusLog;

	public ResourceBundle localizedBundle;
	
	//----------------------------------------------------------------
	public MainMenuView(Peon guy)
	{
		man = guy;
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		add(createTitle());
		add(createNavigationButtons());
		
		add(createStatusLog("     "));

        Locale currentLocale = LocaleConfig.currentLocale();
		localizedBundle = ResourceBundle.getBundle("BicycleStringsBundle", currentLocale);
	}
	//----------------------------------------------------------------
	private JPanel createTitle()
	{
		JPanel temp = new JPanel();
		temp.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		JLabel label = new JLabel(localizedBundle.getString("greetings"));
		Font myFont = new Font("Arial", Font.BOLD, 24);
		label.setFont(myFont);
		temp.add(label);
		
		return temp;
	}
	//----------------------------------------------------------------
	private JPanel createNavigationButtons()
	{
		JPanel temp = new JPanel();
		
		temp.setLayout(new BoxLayout(temp, BoxLayout.Y_AXIS));
		
		insertNewWorkerButton = new JButton(localizedBundle.getString("newWorker");
		insertNewWorkerButton.addActionListener(this);
		temp.add(insertNewWorkerButton);
		temp.setAlignmentX(insertNewWorkerButton.CENTER_ALIGNMENT);
		
		doneButton = new JButton(localizedBundle.getString("cancel");
		doneButton.addActionListener(this);
		temp.add(doneButton);
		temp.setAlignmentX(doneButton.CENTER_ALIGNMENT);
		
		
		return temp;
	}
	//--------------------------------------------------------------
	private JPanel createStatusLog(String initialMessage)
	{
		statusLog = new MessageView(initialMessage);
		
		return statusLog;
	}
	//--------------------------------------------------------------
	public void actionPerformed(ActionEvent event)
	{
		clearErrorMessage();
		
		if(event.getSource() == insertNewWorkerButton)
		{
			man.createNewWorker();
		}
		else if(event.getSource() == doneButton)
		{
			man.exitSystem();
		}
	}
	//------------------------------------------------------------
	public void displayErrorMessage(String message)
	{
		statusLog.displayErrorMessage(message);
	}
	//------------------------------------------------------------
	public void clearErrorMessage()
	{
		statusLog.clearErrorMessage();
	}
}
