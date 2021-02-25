package alu100495.ModelosTabla;

import java.awt.Component;

import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class ProgressCellRender implements TableCellRenderer {

	JProgressBar bar;
	
	public ProgressCellRender() {
		bar = new JProgressBar();
	}
	
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
    	int progress = 0;
        progress = Math.round((float) value);
        bar.setValue(progress);
        bar.setString(value+"%");
        bar.setStringPainted(true);
        return bar;
    }
}
