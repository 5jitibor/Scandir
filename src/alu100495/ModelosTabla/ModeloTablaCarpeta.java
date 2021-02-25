package alu100495.ModelosTabla;


import javax.swing.event.TableModelListener;
import javax.swing.plaf.IconUIResource;
import javax.swing.table.TableModel;

import alu100495.Almacenamiento.Carpeta;


public class ModeloTablaCarpeta implements TableModel {

	Carpeta upla;
	int size;

	//añad una columna extra
	String[] columnNames = new String[] { "","Nombre","Tamaño", "Items", "Archivos","Subdirectorios",};//,"Permisos","Items","Archivos","SubDirectorios"

	public ModeloTablaCarpeta(Carpeta upla) {

		this.upla=upla;
		if(this.upla==null) {
			size=0;
		}
		else {
			size=1;
		}
		
	}

	@Override
	public void addTableModelListener(TableModelListener l) {
	}
	
	@Override
	public void removeTableModelListener(TableModelListener l) {

	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {	
		if(columnIndex == 0)
			return IconUIResource.class;
		if(columnIndex == 2)
			return Long.class;
		return String.class;
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public String getColumnName(int columnIndex) {
		return columnNames[columnIndex];
	}

	@Override
	public int getRowCount() {
		return size;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {

		
		switch (columnIndex) {
			case 0:
				return upla.getIcono();
			case 1:
				return upla.getDireccion().getName();
			case 2:
				return upla.getSize();
			case 3:
				return upla.getCantidad();
			case 4:
				return upla.getFicheros();
			case 5:
				return upla.getCantidad()-upla.getFicheros();	
		}
		return null;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		
	}
	
	public Carpeta getCarpeta() {
		
		return upla;
		
	}
}
