package alu100495.Almacenamiento;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Carpeta extends Item {
	private ArrayList<Item> listaArchivo= new ArrayList<Item>();
	private Integer cantidad;
	private Integer ficheros;
	
	public ArrayList<Item> getListaArchivo() {
		return listaArchivo;
	}


	public void setListaArchivo(ArrayList<Item> listaArchivo) {
		this.listaArchivo = listaArchivo;
	}


	public int getCantidad() {
		return cantidad;
	}


	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	public void sumarUnoCantidad() {
		synchronized (cantidad) {
			this.cantidad++;
		}
		
	}

	public int getFicheros() {
		return ficheros;
	}
	public void sumarUnoFicheros() {
		synchronized (ficheros) {
			this.ficheros++;
		}	
	}

	public void setFicheros(int ficheros) {
		this.ficheros = ficheros;
	}
	
	
	public Carpeta(File direccion) throws IOException {
		super(direccion);
		cantidad=0;
		ficheros=0;
		size=(long) 0;
	}
	
	
	@Override
	public String toString(int tabulaciones) {
		String frase =super.toString(tabulaciones);
		
		for(int i=0;i<=tabulaciones;i++) {
			frase += "\t";
		}
		frase+="Items: "+cantidad+"\n";
		
		for(int i=0;i<=tabulaciones;i++) {
			frase += "\t";
		}
		frase+="Ficheros: "+ficheros+"\n";
		
		for(int i=0;i<=tabulaciones;i++) {
			frase += "\t";
		}
		frase+="Subdirectorios: "+(cantidad-ficheros)+"\n";
		
		for(Item z:listaArchivo) {
			frase += z.toString(tabulaciones+3);
		}
		
		return frase;
	}
	
	
	/*public String toString() {
		
		return toString(0);
	}*/
	
	public String toString() {
		
		return direccion.getName();
	}
}
