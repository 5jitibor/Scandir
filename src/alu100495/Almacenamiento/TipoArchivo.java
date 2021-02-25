package alu100495.Almacenamiento;

import java.util.ArrayList;

import javax.swing.Icon;

public class TipoArchivo {
	private long tamanyo;
	private String tipo;
	private ArrayList<Item> listaFichero;
	private boolean escondido;
	private int numFicheros;
	private Icon icono;
	
	public TipoArchivo(String tipo) {   
        icono=null;
		tamanyo=0;
		this.tipo=tipo;
		listaFichero= new ArrayList<Item>();
		numFicheros=0;
		escondido=false;
		
	}
	
	public ArrayList<Item> getListaFichero() {
		return listaFichero;
	}

	public void setListaFichero(ArrayList<Item> listaFichero) {
		this.listaFichero = listaFichero;
	}
	
	public long getTamanyo() {
		return tamanyo;
	}
	
	public void setTamanyo(long tamanyo) {
		this.tamanyo = tamanyo;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public boolean isEscondido() {
		return escondido;
	}

	public void setEscondido(boolean escondido) {
		this.escondido = escondido;
	}

	public int getNumFicheros() {
		return numFicheros;
	}

	public void setNumFicheros(int numFicheros) {
		this.numFicheros = numFicheros;
	}

	public Icon getIcono() {
		return icono;
	}

	public void setIcono(Icon icono) {
		this.icono = icono;
	}
	
	public TipoArchivo(String tipo,Icon icono) { 
		this(tipo);
        this.setIcono(icono);
	}
	
	public float getPerTamnyo(long size) {
		
		float aux=(float)tamanyo/(float)size;
		
		aux = Math.round(aux * 10000);
		aux = aux/100;
		
		return aux;
	}
}
