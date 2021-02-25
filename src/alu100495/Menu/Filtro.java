package alu100495.Menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import alu100495.Almacenamiento.Item;
import alu100495.Almacenamiento.TipoArchivo;

public class Filtro {
	
	private JCheckBox checkMinimo;
	private JComboBox<String> opcionesMinimo;
	private JTextField datoNumericoMinimo;
	private long tamanyoMinimo;
	private JCheckBox checkMaximo;
	private JComboBox<String> opcionesMaximo;
	private JTextField datoNumericoMaximo;
	private long tamanyoMaximo;
	private ArrayList<TipoArchivo> listaTipoArchivo;
	private String tiposTamanyos[]= {"bytes","Kb","Mb","Gb"};
	private Menu principal;
	
	
	
	public JCheckBox getCheckMinimo() {
		return checkMinimo;
	}

	public void setCheckMinimo(JCheckBox checkMinimo) {
		this.checkMinimo = checkMinimo;
	}

	public JComboBox<String> getOpcionesMinimo() {	
		return opcionesMinimo;
	}

	public void setOpcionesMinimo(JComboBox<String> opcionesMinimo) {
		this.opcionesMinimo = opcionesMinimo;
	}

	public JTextField getDatoNumericoMinimo() {
		return datoNumericoMinimo;
	}

	public void setDatoNumericoMinimo(JTextField datoNumericoMinimo) {
		this.datoNumericoMinimo = datoNumericoMinimo;
	}

	public JCheckBox getCheckMaximo() {
		return checkMaximo;
	}

	public void setCheckMaximo(JCheckBox checkMaximo) {
		this.checkMaximo = checkMaximo;
	}

	public JComboBox<String> getOpcionesMaximo() {
		return opcionesMaximo;
	}

	public void setOpcionesMaximo(JComboBox<String> opcionesMaximo) {
		this.opcionesMaximo = opcionesMaximo;
	}

	public JTextField getDatoNumericoMaximo() {
		return datoNumericoMaximo;
	}

	public void setDatoNumericoMaximo(JTextField datoNumericoMaximo) {
		this.datoNumericoMaximo = datoNumericoMaximo;
	}

	public ArrayList<TipoArchivo> getListaTipoArchivo() {
		return listaTipoArchivo;
	}

	public void setListaTipoArchivo(ArrayList<TipoArchivo> listaTipoArchivo) {
		this.listaTipoArchivo = listaTipoArchivo;
	}

	public Filtro(Menu principal) {
		this.principal=principal;
		checkMinimo= new JCheckBox();
		datoNumericoMinimo= new JTextField(7);
		opcionesMinimo= new JComboBox<String>(tiposTamanyos);
		checkMaximo= new JCheckBox();
		datoNumericoMaximo= new JTextField(7);
		listaTipoArchivo= new ArrayList<TipoArchivo>();
		opcionesMaximo= new JComboBox<String>(tiposTamanyos);
		resetear();
		crearTextFieldListener(datoNumericoMinimo,opcionesMinimo,true);
		crearComboListener(opcionesMinimo,datoNumericoMinimo,true);
		crearCheckListener(checkMinimo,opcionesMinimo,datoNumericoMinimo);
		crearTextFieldListener(datoNumericoMaximo,opcionesMaximo,false);
		crearComboListener(opcionesMaximo,datoNumericoMaximo,false);
		crearCheckListener(checkMaximo,opcionesMaximo,datoNumericoMaximo);
		
        
	} 
	
	public void resetear() {
		datoNumericoMinimo.setText("0");
		datoNumericoMaximo.setText("0");
		checkMinimo.setSelected(false);
		opcionesMinimo.setEnabled(false);
		datoNumericoMinimo.setEditable(false);
		checkMinimo.setSelected(false);
		opcionesMaximo.setEnabled(false);
		datoNumericoMaximo.setEditable(false);
		listaTipoArchivo.clear();
        listaTipoArchivo.add(new TipoArchivo("Carpeta"));
        tamanyoMinimo=0;
        tamanyoMaximo=0;
	}
		
	public boolean filtrarDato(Item auxiliarItem) {
		
		if(estaEscondido(auxiliarItem) || (checkMinimo.isSelected() && auxiliarItem.getSize()<tamanyoMinimo) || (checkMaximo.isSelected() && auxiliarItem.getSize()>tamanyoMaximo)) {
			return false;
		}
			return true;
		
	}
	
	
	public void crearTextFieldListener(JTextField dato,JComboBox<String> combo,boolean esMinimo) {
		dato.addKeyListener(new KeyListener() {	
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
				if((e.getKeyChar()<'0' || e.getKeyChar()>'9') && (e.getKeyChar()!='.' || dato.getText().contains("."))) {
					e.consume();
				}
				else {
					if(dato.getText().contentEquals("0") && e.getKeyChar()>='0' && e.getKeyChar()<='9') {
							dato.setText("");
						
					}
				}
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyChar() !=KeyEvent.VK_BACK_SPACE && Float.parseFloat(dato.getText())>1024) {

					dato.setText("1024");
				}
				if(dato.getText().contentEquals("")) {
					
					dato.setText("0");
				}

				if(esMinimo) {
					tamanyoMinimo=actualizarValores(dato,combo);
				}
				else {
					tamanyoMaximo=actualizarValores(dato,combo);
				}
				intervalosCorrectos();
				principal.actualizarTablasFiltro();
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
			}
		});
	}
	
	public void crearCheckListener(JCheckBox check, JComboBox<String> combo,JTextField texto) {
		check.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				combo.setEnabled(check.isSelected());
				texto.setEditable(check.isSelected());
				intervalosCorrectos();
				principal.actualizarTablasFiltro();
			}
			
			
		});			
	}
	
	public void crearComboListener(JComboBox<String> combo,JTextField texto,boolean esMinimo) {
		combo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(esMinimo) {
					tamanyoMinimo=actualizarValores(texto,combo);
				}
				else {
					tamanyoMaximo=actualizarValores(texto,combo);
				}
				
				intervalosCorrectos();
				principal.actualizarTablasFiltro();
			}
			
			
		});			
	}
	
	public void intervalosCorrectos() {
		if(tamanyoMinimo>tamanyoMaximo && checkMaximo.isSelected() && checkMinimo.isSelected()) {
			opcionesMaximo.setSelectedIndex(opcionesMinimo.getSelectedIndex());
			datoNumericoMaximo.setText(datoNumericoMinimo.getText());
			tamanyoMaximo=actualizarValores(datoNumericoMaximo, opcionesMaximo);
		}
	}
	
	public long actualizarValores(JTextField dato,JComboBox<String> combo) {
		return (long) (Float.parseFloat(dato.getText())*Math.pow(2,(combo.getSelectedIndex()*10)));
	}
	
	
	public boolean estaEscondido(Item auxiliarItem) {
		synchronized (listaTipoArchivo) {
			for(TipoArchivo auxTipo: listaTipoArchivo) {
				if(auxTipo.getListaFichero().contains(auxiliarItem)) {
					return auxTipo.isEscondido();
				}
			}
		}
		
		return false;
	}
	
}
