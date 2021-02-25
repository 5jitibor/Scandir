package alu100495.Menu;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import alu100495.Almacenamiento.Carpeta;

public class Ficheros {
	public static void crearFichero(Carpeta carpetaAGuardar,JFrame frame) throws IOException {
		if(carpetaAGuardar!=null){
			String nombre=JOptionPane.showInputDialog(frame,"Dame el nombre del txt:");
			if(nombre !=null) {
				if(nombre.isEmpty()) {
					nombre="Generico";
				}
				BufferedWriter out = new BufferedWriter(new FileWriter(nombre+".txt"));
				out.write(carpetaAGuardar.toString(0));
				out.close();
				JOptionPane.showMessageDialog(frame,"Se ha guardado correctamente");
			}
		}
		else {
			JOptionPane.showMessageDialog(frame,"Error:No hay una carpeta seleccionada","Error",JOptionPane.ERROR_MESSAGE);
		}
		
		
	}
}
