package alu100495.Menu;

import javax.swing.JOptionPane;

import alu100495.Almacenamiento.Motor;

public class ActualizarDatos extends Thread {
	private Menu menuActualizar;
	 public ActualizarDatos(Menu menu) {
		menuActualizar=menu;
	}
	
	@Override
	public void run() {
		while(Motor.algunoVivo()) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			menuActualizar.getTablaTipo().updateUI();
			menuActualizar.getArbol().updateUI();
			menuActualizar.getTablaCarpeta().updateUI();
			menuActualizar.getTablaCentral().updateUI();
		}
		JOptionPane.showMessageDialog(menuActualizar.getFrame(),"Se ha cargado correctamente");
	}
}
