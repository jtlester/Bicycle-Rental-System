package userinterface;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

//==============================================================================
public class BicycleTableModel extends AbstractTableModel implements TableModel {
	private Vector myState;

	public BicycleTableModel(Vector bicycleData) {
		myState = bicycleData;
	}

	public int getColumnCount() {
		return 5;
	}

	public int getRowCount() {
		return myState.size();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		Vector bicycle = (Vector)myState.elementAt(rowIndex);
		return "    " + bicycle.elementAt(columnIndex);
	}

	//--------------------------------------------------------------------------
	public String getColumnName(int columnIndex) {
		if(columnIndex == 0) {
			return "bicycleId";
		} else if(columnIndex == 1) {
			return "author";
		} else if(columnIndex == 2) {
			return "title";
		} else if(columnIndex == 3) {
			return "pubYear";
		} else if(columnIndex == 4) {
			return "Status";
		} else {
			return "other";
		}
	}
}
