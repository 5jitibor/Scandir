package alu100495.Almacenamiento;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;

import javax.swing.Icon;
import javax.swing.filechooser.FileSystemView;
import javax.swing.plaf.IconUIResource;


public class Item {
	protected Icon icono;
	protected BasicFileAttributes attr;
	protected File direccion;
	protected Long size;
	
	public Item(File direccion) throws IOException {
		this.direccion=direccion;
		Path file = Paths.get(direccion.getAbsolutePath());
		attr = Files.readAttributes(file, BasicFileAttributes.class);
		FileSystemView view=FileSystemView.getFileSystemView();   
		try {
			 icono= new IconUIResource(view.getSystemIcon(direccion));
		} catch (IllegalArgumentException e) {
			icono= new IconUIResource(view.getSystemIcon(new File(".classpath")));
		}
       
	}
	
	public BasicFileAttributes getAttr() {
		
			return attr;
	}

	public Icon getIcono() {
		synchronized (icono) {
			return icono;
		}
			
		
		
	}

	public void setIcono(Icon icono) {
		synchronized (this.icono) {
			this.icono = icono;
		}
		
	}

	public void setAttr(BasicFileAttributes attr) {
			this.attr = attr;
		
	}

	public File getDireccion() {
		return direccion;
	}

	public void setDireccion(File direccion) {
		this.direccion = direccion;
	}

	public long getSize() {
			return size;
		
		
	}

	public void setSize(long size) {
			this.size = size;
	}
	
	public void sumarSize(long size) {
		synchronized (this.size) {
			this.size += size;
		}
	}
	
	public String getTamanyoString() {
		String frase="";
		float aux;
		int contador=0;
		aux=(float)size;
		while(aux>1024.0) {
			contador++;
			aux/= 1024;
			
		}
		aux = Math.round(aux * 100);
		aux = aux/100;
		frase+=aux;
		switch(contador) {
			case 0:
				frase += " bytes";
				break;
			case 1:
				frase += " Kb";
				break;
			case 2:
				frase += " Mb";
				break;
			case 3:
				frase += " Gb";
				break;
			case 4:
				frase += " Tb";
				break;
		}
		return frase;
	}
	
	public float getPerTamnyo(long size) {
		
		float aux=(float)this.size/(float)size;
		
		aux = Math.round(aux * 10000);
		aux = aux/100;
		
		return aux;
	}

	public String toString(int tabulaciones) {
		String frase ="";
		float aux;
		int contador=0;
		for(int i=0;i<tabulaciones;i++) {
			frase += "\t";
		}
		frase +=direccion.getName()+"\n";
		for(int i=0;i<=tabulaciones;i++) {
			frase += "\t";
		}
		frase +="Espacio: ";
		aux=(float)size;
		while(aux>1024.0) {
			contador++;
			aux/= 1024;
			
		}
		frase+=aux;
		switch(contador) {
			case 0:
				frase += "bytes";
				break;
			case 1:
				frase += "Kb";
				break;
			case 2:
				frase += "Mb";
				break;
			case 3:
				frase += "Gb";
				break;
			case 4:
				frase += "Tb";
				break;
		}
		frase+="\n";
		
		for(int i=0;i<=tabulaciones;i++) {
			frase += "\t";
			
		}
		frase +="Fecha de ultima modificacion:"+attr.lastModifiedTime()+"\n";
		
		for(int i=0;i<=tabulaciones;i++) {
			frase += "\t";
			
		}
		
		frase +="Permisos:\n";
		for(int i=0;i<=tabulaciones+1;i++) {
			frase += "\t";
			
		}
		frase += "Ejecutar: ";
		if(direccion.canExecute()) frase+=" si";	
		else 	frase+=" no";
		frase +="\n";
		
		for(int i=0;i<=tabulaciones+1;i++) {
			frase += "\t";
			
		}
		frase += "Leer: ";
		if(direccion.canRead()) frase+=" si";	
		else 	frase+=" no";
		frase +="\n";
		
		for(int i=0;i<=tabulaciones+1;i++) {
			frase += "\t";
			
		}
		frase += "Escribir: ";
		if(direccion.canWrite()) frase+=" si";	
		else 	frase+=" no";
		frase +="\n";
		
		for(int i=0;i<=tabulaciones+1;i++) {
			frase += "\t";
			
		}
		frase += "Oculto: ";
		if(direccion.isHidden()) frase+=" si";	
		else 	frase+=" no";
		frase +="\n";
		return frase;
	}
	
	public String getPerTamnyoString(long size) {
		
		float aux= getPerTamnyo(size);
		
		
		return aux+"%";
	}


	
}
