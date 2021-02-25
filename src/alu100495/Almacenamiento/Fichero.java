package alu100495.Almacenamiento;

import java.io.File;
import java.io.IOException;

public class Fichero extends Item {
	
	
	public Fichero(File direccion) throws IOException {
		super(direccion);
		size=attr.size();
	}
	
}
