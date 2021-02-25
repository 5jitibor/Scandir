package alu100495.Almacenamiento;

import java.io.*;
import java.util.ArrayList;

import javax.swing.tree.DefaultMutableTreeNode;

public class Motor extends Thread{
	private static ArrayList<Motor> listaHilos= new ArrayList<Motor>();
	private ArrayList<TipoArchivo> lista;
	private DefaultMutableTreeNode nodo;
	
	
	public Motor(ArrayList<TipoArchivo> lista,DefaultMutableTreeNode nodo) {
		this.lista=lista;
		this.nodo=nodo;
		listaHilos.add(this);
	}
	
	
	
	public static ArrayList<Motor> getListaHilos() {
		return listaHilos;
	}



	public static void setListaHilos(ArrayList<Motor> listaHilos) {
		Motor.listaHilos = listaHilos;
	}

	public static boolean algunoVivo() {
		synchronized (listaHilos) {
			for(Motor hilo: listaHilos) {
				if(hilo.isAlive()) {
					return true;
				}
			}
			listaHilos.clear();
			return false;
		}
		
	}

	@Override
	public void run() {
		try {
			recorrer(lista, nodo);
			listaHilos.remove(this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			listaHilos.remove(this);
		}
		
	}
	public void recorrer(ArrayList<TipoArchivo> list,DefaultMutableTreeNode padre)  throws IOException  {
		Carpeta p=(Carpeta) padre.getUserObject();
		Carpeta carpetaPadre;
		DefaultMutableTreeNode nodoActual;
		Motor nuevoHilo;
		if(list.get(0).getListaFichero().size()==0) {
			list.get(0).setIcono(p.getIcono());
		}
		
		for(File z:p.getDireccion().listFiles()) {
			if(z.canRead()) {
			if(z.isDirectory() && z.listFiles()!=null) {
				Carpeta pnueva= new Carpeta(z);
				p.getListaArchivo().add(pnueva);
				DefaultMutableTreeNode hijo=new DefaultMutableTreeNode(pnueva);
				padre.add(hijo);
				nuevoHilo= new Motor(list,hijo); 
				nuevoHilo.start();
				nodoActual=padre;
				do {
					carpetaPadre = (Carpeta) nodoActual.getUserObject();
					carpetaPadre.sumarUnoCantidad();
					nodoActual= (DefaultMutableTreeNode) nodoActual.getParent();
				}while(nodoActual!= null);
				synchronized (list) {
					list.get(0).getListaFichero().add(pnueva);
					list.get(0).setNumFicheros(list.get(0).getNumFicheros()+1);
				}
				
			}
			
			else {
				Fichero aux=new Fichero(z);
				nodoActual=padre;
				do {
					carpetaPadre = (Carpeta) nodoActual.getUserObject();
					carpetaPadre.sumarUnoCantidad();
					carpetaPadre.sumarUnoFicheros();
					carpetaPadre.sumarSize(aux.getAttr().size());
					nodoActual= (DefaultMutableTreeNode) nodoActual.getParent();
				}while(nodoActual!= null);
				synchronized (list) {
				p.getListaArchivo().add(aux);
				comprobarTipoArchivo(aux, list);
				}
			}
				
			}
			
		}
	}	
	public void comprobarTipoArchivo(Fichero archivo,ArrayList<TipoArchivo> list ) {
		String tipo=obtenerTipoArchivo(archivo.direccion.getName());
		TipoArchivo tipoExistente = existeTipoArchivo(tipo,list);
		if(tipoExistente==null){
			tipoExistente = new TipoArchivo(tipo,archivo.getIcono());
			list.add(tipoExistente);
		}
		tipoExistente.setTamanyo(tipoExistente.getTamanyo()+archivo.attr.size());
		tipoExistente.setNumFicheros(tipoExistente.getNumFicheros()+1);
		tipoExistente.getListaFichero().add(archivo);
	}
	
	public String obtenerTipoArchivo(String nombre) {
		char nombreCaracter[]=nombre.toCharArray();
		int pos=nombre.length()-1;
		String aux = "";
		while (pos>=0 && nombreCaracter[pos]!='.' ) {
			
			aux=nombreCaracter[pos]+aux;
			pos--;
		}
		if(nombre.equalsIgnoreCase(aux.toString())) {
			return "Otro";
		}
		return aux;
		
	}
	
	public TipoArchivo existeTipoArchivo(String nombre,ArrayList<TipoArchivo> list ) {
		for(TipoArchivo a: list) {
			if(a.getTipo().equalsIgnoreCase(nombre)) {
				return a;
			}
		}
		return null;
	}





	





	

}
