package alu100495.ModelosTabla;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;



public class TamanyoCellRender implements TableCellRenderer {

	JLabel label;
	
	public TamanyoCellRender() {
		label= new JLabel();
	}
	
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
    	String nombre="";
    	float aux;
		int contador=0;
		aux=(long)value;
		while(aux>1024.0) {
			contador++;
			aux/= 1024;
			
		}
		aux = Math.round(aux * 100);
		aux = aux/100;
		nombre+=aux;	
		switch(contador) {
			case 0:
				nombre += " bytes";
				break;
			case 1:
				nombre += " Kb";
				break;
			case 2:
				nombre += " Mb";
				break;
			case 3:
				nombre += " Gb";
				break;
			case 4:
				nombre += " Tb";
				break;
		}
		label.setText(nombre);
        return label;
    }
    
   
}
