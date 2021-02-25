package alu100495.ModelosTabla;


import java.nio.file.attribute.FileTime;
import java.util.ArrayList;

import javax.swing.event.TableModelListener;
import javax.swing.plaf.IconUIResource;
import javax.swing.table.TableModel;

import alu100495.Almacenamiento.Carpeta;
import alu100495.Almacenamiento.Item;
import alu100495.Almacenamiento.TipoArchivo;



public class ModeloTablaCentral implements TableModel {

	private ArrayList<Item> uplas;
	private Carpeta carpetaViendo;
	private TipoArchivo tipoViendo;
	
	
	

	

	//añad una columna extra
	private String[] columnNames = new String[] {"","Nombre", "Tamaño", "%","Fecha de ultima modificacion","Ejecutar","Lectura","Escribir","Oculto"};//,"Permisos","Items","Archivos","SubDirectorios"

	public ModeloTablaCentral(ArrayList<Item> uplas, Carpeta carpeta, TipoArchivo tipo) {

		this.uplas = uplas;
		carpetaViendo=carpeta;
		tipoViendo=tipo;
	}
	
	public ArrayList<Item> getUplas() {
		return uplas;
	}

	public void setUplas(ArrayList<Item> uplas) {
		this.uplas = uplas;
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
		if(columnIndex == 1)
			return String.class;
		if(columnIndex == 2)
			return Long.class;
		if(columnIndex == 3)
			return Float.class;
		if(columnIndex == 4)
			return FileTime.class;
		
		return Boolean.class;
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
		
		Item p = uplas.get(rowIndex);
		
		switch (columnIndex) {
			case 0:	
				 return p.getIcono();
			case 1:
				return p.getDireccion().getName();
			case 2:
				return p.getSize();
			case 3:	
				if(carpetaViendo==null) {
					return p.getPerTamnyo(tipoViendo.getTamanyo());
				}
				else {
					return p.getPerTamnyo(carpetaViendo.getSize());
				}
			case 4:
				return p.getAttr().lastModifiedTime();
			case 5:
				return p.getDireccion().canExecute();
			case 6:
				return p.getDireccion().canRead();
			case 7:
				return p.getDireccion().canWrite(); 
			case 8:
				return p.getDireccion().isHidden();
				
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
}
