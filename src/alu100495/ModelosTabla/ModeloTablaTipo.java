package alu100495.ModelosTabla;


import java.util.ArrayList;
import javax.swing.event.TableModelListener;
import javax.swing.plaf.IconUIResource;
import javax.swing.table.TableModel;

import alu100495.Almacenamiento.Carpeta;
import alu100495.Almacenamiento.TipoArchivo;


public class ModeloTablaTipo implements TableModel  {

	private ArrayList<TipoArchivo> uplas;
	private Carpeta raiz;

	//añad una columna extra
	String[] columnNames = new String[] {"", "Tipo", "Tamaño","Archivos","%","Ocultar"};

	public ModeloTablaTipo(ArrayList<TipoArchivo> uplas, Carpeta raiz) {

		this.uplas = uplas;
		this.raiz=raiz;
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
		if(columnIndex == 3)
			return Integer.class;
		if(columnIndex == 4)
			return Float.class;
		if(columnIndex == 5)
			return Boolean.class;
		
		return String.class;
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public String getColumnName(int columnIndex) {
		//System.out.println("getColumnName "+columnIndex);
		return columnNames[columnIndex];
	}

	@Override
	public int getRowCount() {
		//System.out.println("getRowCount ");
		return uplas.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {

		//System.out.println("getValueAt "+rowIndex+" - "+columnIndex);
		
		TipoArchivo p = uplas.get(rowIndex);
		
		switch (columnIndex) {
			case 0:
				return p.getIcono();
			case 1:
				return p.getTipo();
			case 2:
				return p.getTamanyo();
			case 3:
				return p.getNumFicheros();
			case 4:
				return p.getPerTamnyo(raiz.getSize());
			case 5:
				return p.isEscondido();
			
				
		}
		

		
		

		return null;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if(columnIndex==5) {
			return true;
		}
		return false;
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		uplas.get(rowIndex).setEscondido((boolean)aValue);

		
	}

	
	
}

