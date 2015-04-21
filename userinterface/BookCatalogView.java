// specify the package
package userinterface;

// system imports
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Properties;
import java.util.EventObject;
import java.util.Date;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.text.NumberFormat;
import java.util.Vector;
import java.util.Enumeration;
import java.util.EventObject;


// project imports
import impresario.IModel;
import model.*;

public class BookCatalogView extends JPanel implements ActionListener
{
	private Librarian myLibrarian;
	private BookCatalog myBooks;
	
	protected Vector bookVector;
	
	private JButton doneButton;
	protected JTable tableOfBooks;
	
	private MessageView statusLog;
	
	//-----------------------------------------------------------------
	public BookCatalogView(Librarian librarian, BookCatalog coll)
	{
		myLibrarian = librarian;
		myBooks = coll;
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		bookVector = new Vector();
		
		add(createDataEntryFields());
	
		
		add(createStatusLog("         "));
		
		populateFields();
		add(createNavigationButtons());
	}
	
	//--------------------------------------------------------------------------
	protected void populateFields()
	{
		getEntryTableModelValues();
	}
	
	//--------------------------------------------------------------------------
	protected void getEntryTableModelValues()
	{
		bookVector.removeAllElements();
		System.out.println("in entry table model values");		
		try
		{
			System.out.println("in try loop");
			Vector<Book> entryList = myBooks.getBooks();
			Enumeration entries = entryList.elements();
			
			while (entries.hasMoreElements() == true)
			{	
				System.out.println("in while loop");
				Book nextBook = (Book)entries.nextElement();
				Vector view = nextBook.getEntryListView();

				// add this list entry to the list
				bookVector.add(view);
			}
		}
		catch (Exception e) 
		{
			System.out.println("\nError: Unable to enter data in TableModelValues()\n");
		}
	}
	
	// Create the main data entry fields
	//-------------------------------------------------------------
	private JPanel createDataEntryFields()
	{
		JPanel entries = new JPanel();
		entries.setLayout(new BoxLayout(entries, BoxLayout.Y_AXIS));

		JPanel tablePan = new JPanel();
		tablePan.setLayout(new FlowLayout(FlowLayout.CENTER));

		TableModel myData = new BookTableModel(bookVector);

		tableOfBooks = new JTable(myData);

		tableOfBooks.setPreferredScrollableViewportSize(new Dimension(500,150));

		TableColumn column;
		for(int i = 0; i < myData.getColumnCount(); i++)
		{
			column = tableOfBooks.getColumnModel().getColumn(i);
			column.setPreferredWidth(150);
		}
		// Renter First Name

		tablePan.add(tableOfBooks);

		JScrollPane scrollPane = new JScrollPane(tableOfBooks);
		tablePan.add(scrollPane);

		entries.add(tablePan);
		return entries;
	}

	// Create the navigation buttons
	//-------------------------------------------------------------
	private JPanel createNavigationButtons()
	{
		JPanel temp = new JPanel();		// default FlowLayout is fine
		FlowLayout f1 = new FlowLayout(FlowLayout.CENTER);
		
		f1.setVgap(0);
		f1.setHgap(0);
		temp.setLayout(f1);

		// create the buttons, listen for events, add them to the panel
		doneButton = new JButton("Done");
		doneButton.addActionListener(this);
		temp.add(doneButton);

		return temp;
	}
	
	// Create the status log field
	//-------------------------------------------------------------
	private JPanel createStatusLog(String initialMessage)
	{
		statusLog = new MessageView(initialMessage);

		return statusLog;
	}
	
	//----------------------------------------------------------
	public void displayErrorMessage(String message)
	{
		statusLog.displayErrorMessage(message);
	}

	//----------------------------------------------------------
	public void clearErrorMessage()
	{
		statusLog.clearErrorMessage();
	}
	
	//-----------------------------------------------------------------
	public void displayMessage(String message)
	{
		statusLog.displayMessage(message);
	}
	
	//------------------------------------------------------------------
	public void actionPerformed(ActionEvent event)
	{
		clearErrorMessage();
		if(event.getSource() == doneButton)
		{
			myLibrarian.bookDataDone();
		}
	}
}