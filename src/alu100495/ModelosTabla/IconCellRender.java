package alu100495.ModelosTabla;

import java.awt.Component;


import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.plaf.IconUIResource;
import javax.swing.table.TableCellRenderer;

	

public class IconCellRender implements TableCellRenderer {
	JLabel label;
	public IconCellRender() {
		label= new JLabel();
	}
	
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
    	label.setIcon((IconUIResource)value);
    	return label;
    }
    
   
}
