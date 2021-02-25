package alu100495.ModelosTabla;

import java.awt.Component;
import java.nio.file.attribute.FileTime;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class FechaCellRender implements TableCellRenderer {

	
	
	public FechaCellRender() {
		
	}
	
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
    	FileTime fecha= (FileTime)value;
    	char[] fechaString= new char[19];
    	for(int i=0;i<19;i++) {
    		if(fecha.toString().toCharArray()[i]!='T') {
    			fechaString[i]=fecha.toString().toCharArray()[i];
    		}
    		else {
    			fechaString[i]=' ';
    		}
    		
    		
    	}
    	
    	
        return new JLabel(String.copyValueOf(fechaString));
    }
}
