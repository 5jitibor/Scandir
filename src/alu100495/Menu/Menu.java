package alu100495.Menu;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

import alu100495.ModelosTabla.*;
import alu100495.Almacenamiento.*;
import alu100495.ModeloTree.IconTreeCellRender;

public class Menu implements ActionListener{
	private JFrame frame;
	private JTable tablaTipo;
	private JTable tablaCentral;
	private JTable tablaCarpeta;
	private JTree arbol;
	private DefaultMutableTreeNode raizNodo;
	private Motor busqueda;
	private Filtro sistemaFiltro;
	private TipoArchivo tipoArchivoActual;
	private ActualizarDatos actualizadorUI;
	
	public Menu() throws Exception {
		raizNodo=new DefaultMutableTreeNode();
		sistemaFiltro= new Filtro(this);
		if(cargarDatos()) {
			crearFrame();
			crearListeners();
			inicializarDatos();	
		}
	}
	
	
	
	public JTable getTablaCentral() {
		return tablaCentral;
	}



	public void setTablaCentral(JTable tablaCentral) {
		this.tablaCentral = tablaCentral;
	}



	public JFrame getFrame() {
		return frame;
	}



	public void setFrame(JFrame frame) {
		this.frame = frame;
	}



	public JTable getTablaCarpeta() {
		return tablaCarpeta;
	}



	public void setTablaCarpeta(JTable tablaCarpeta) {
		this.tablaCarpeta = tablaCarpeta;
	}



	public JTable getTablaTipo() {
		return tablaTipo;
	}



	public void setTablaTipo(JTable tablaTipo) {
		this.tablaTipo = tablaTipo;
	}



	public JTree getArbol() {
		return arbol;
	}


	public void setArbol(JTree arbol) {
		this.arbol = arbol;
	}


	public Motor getBusqueda() {
		return busqueda;
	}

	public void setBusqueda(Motor busqueda) {
		this.busqueda = busqueda;
	}
	
	public TipoArchivo getTipoArchivoActual() {
		return tipoArchivoActual;
	}

	public void setTipoArchivoActual(TipoArchivo tipoArchivoActual) {
		this.tipoArchivoActual = tipoArchivoActual;
	}
	
	public void inicializarDatos() {
		renderizarTablaTipo();
		renderizarTablaCentral(((Carpeta)raizNodo.getUserObject()).getListaArchivo(),((Carpeta)raizNodo.getUserObject()),null);
		renderizarTablaCarpeta((Carpeta)raizNodo.getUserObject());
		tipoArchivoActual=null;
		arbol.setSelectionRow(0);
		arbol.updateUI();
		actualizadorUI.start();
		frame.setVisible(true);
	}

	public boolean cargarDatos() throws Exception {
		
		JFileChooser fc= new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		int returnVal = fc.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) { 	
            raizNodo.removeAllChildren();
            raizNodo.setUserObject(new Carpeta(fc.getSelectedFile().getAbsoluteFile()));
            sistemaFiltro.resetear();
            busqueda= new Motor(sistemaFiltro.getListaTipoArchivo(),raizNodo);
            actualizadorUI= new ActualizarDatos(this);
            busqueda.start();
    		
    		return true;
        }
        return false;
        
	}
	
	public void actualizarTablasFiltro() {
		if(tipoArchivoActual==null) {
			ModeloTablaCarpeta modeloCarpeta = (ModeloTablaCarpeta) tablaCarpeta.getModel();
			renderizarTablaCentral(filtrarLista(modeloCarpeta.getCarpeta().getListaArchivo()),modeloCarpeta.getCarpeta(),null);
		}
		else {
			renderizarTablaCentral(filtrarLista(tipoArchivoActual.getListaFichero()),null,tipoArchivoActual);
			
		}
		tablaTipo.updateUI();
	}
	
	public ArrayList<Item> filtrarLista(ArrayList<Item> listaCompleta) {
		synchronized (listaCompleta) {
			ArrayList<Item> listaAuxiliar= new ArrayList<Item>();
			for(Item a: listaCompleta) {
				if(sistemaFiltro.filtrarDato(a)) {
					listaAuxiliar.add(a);
				}
			}
			return listaAuxiliar;
		}
		
		
	}
	
	public void renderizarTablaTipo() {
		 tablaTipo.setModel(new ModeloTablaTipo(sistemaFiltro.getListaTipoArchivo(),(Carpeta)raizNodo.getUserObject()));
		 renderizarTablaBase(tablaTipo);
	     tablaTipo.getColumn(tablaTipo.getColumnName(4)).setCellRenderer(new ProgressCellRender());
	     tablaTipo.getColumn(tablaTipo.getColumnName(3)).setMaxWidth(55);
	     tablaTipo.getColumn(tablaTipo.getColumnName(5)).setMaxWidth(50);
	     tablaTipo.updateUI();
	}
	
	public void renderizarTablaCentral(ArrayList<Item> listaItems, Carpeta carpeta, TipoArchivo tipo) {
		tablaCentral.setModel(new ModeloTablaCentral(listaItems,carpeta,tipo));
		renderizarTablaBase(tablaCentral);
		tablaCentral.getColumn(tablaCentral.getColumnName(3)).setCellRenderer(new ProgressCellRender());
		tablaCentral.getColumn(tablaCentral.getColumnName(4)).setCellRenderer(new FechaCellRender());
		tablaCentral.getColumn(tablaCentral.getColumnName(5)).setMaxWidth(50);
		tablaCentral.getColumn(tablaCentral.getColumnName(6)).setMaxWidth(50);
		tablaCentral.getColumn(tablaCentral.getColumnName(7)).setMaxWidth(50);
		tablaCentral.getColumn(tablaCentral.getColumnName(8)).setMaxWidth(50);
		tablaCentral.updateUI();

	}
	
	public void renderizarTablaCarpeta(Carpeta c) {
		tablaCarpeta.setModel(new ModeloTablaCarpeta(c));
		renderizarTablaBase(tablaCarpeta);
		tablaCarpeta.updateUI();
	}
	
	public void renderizarTablaBase(JTable tabla) {
		tabla.getColumn(tabla.getColumnName(0)).setCellRenderer(new IconCellRender());
		tabla.getColumn(tabla.getColumnName(0)).setMaxWidth(20);
		tabla.getColumn(tabla.getColumnName(2)).setCellRenderer(new TamanyoCellRender());
		tabla.getColumn(tabla.getColumnName(2)).setMaxWidth(60);
	}
	
	
	
	public void crearListeners() {
		tablaTipo.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseEntered(MouseEvent arg0) {
				if(tablaTipo.getSelectedColumn()==5) {
	        		actualizarTablasFiltro();
	        	}
			}
			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
	   			if(tablaTipo.getSelectedColumn()!=5) {
	   				synchronized (sistemaFiltro.getListaTipoArchivo()) {
						
					}
	   				for(TipoArchivo a: sistemaFiltro.getListaTipoArchivo()) {
	   					if(a.getTipo().equals(tablaTipo.getValueAt(tablaTipo.getSelectedRow(), 1)) && !a.getTipo().contentEquals("Carpeta")){
	   						tipoArchivoActual=a;
	   						actualizarTablasFiltro();
	   						renderizarTablaCarpeta(null);
	   					}
	   				}	
	   			}
			}
		});
		tablaCarpeta.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				ModeloTablaCarpeta modeloCarpeta = (ModeloTablaCarpeta) tablaCarpeta.getModel();
				try {
					Desktop.getDesktop().open(modeloCarpeta.getCarpeta().getDireccion());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		tablaCentral.addMouseListener(new MouseAdapter() {
			
			public void mouseClicked(MouseEvent arg0) {
			  if ( SwingUtilities.isRightMouseButton(arg0)) {
                  Point p = arg0.getPoint();
                  int rowNumber = tablaCentral.rowAtPoint( p );
                  ListSelectionModel modelo = tablaCentral.getSelectionModel();
                  modelo.setSelectionInterval( rowNumber, rowNumber );
              	}
				ModeloTablaCentral modeloCentral= (ModeloTablaCentral) tablaCentral.getModel();
				if(tipoArchivoActual==null) {
					abrirCarpetaArchivo(modeloCentral.getUplas(),arg0);
				}
				else {
					for(TipoArchivo tipo: sistemaFiltro.getListaTipoArchivo()) {
						if(tipo.getTipo().equals(tablaTipo.getValueAt(tablaTipo.getSelectedRow(), 1)) )
							abrirCarpetaArchivo(tipo.getListaFichero(),arg0);
					}
				}
			}
		});
		
		
		 arbol.addMouseListener(new MouseAdapter() {
	    		@Override
				public void mouseReleased(MouseEvent arg0) {
					// TODO Auto-generated method stub
					setTipoArchivoActual(null);
	        		DefaultMutableTreeNode n=(DefaultMutableTreeNode) arbol.getLastSelectedPathComponent();
	        		Carpeta c= (Carpeta) n.getUserObject();
	        		renderizarTablaCarpeta(c);
	        		actualizarTablasFiltro();	
				}
	     	});	 
	}
	
	void abrirCarpetaArchivo(ArrayList<Item> lista,MouseEvent evento) {
		for(Item a: lista) {
			if(a.getDireccion().getName().equals(tablaCentral.getValueAt(tablaCentral.getSelectedRow(), 1))) {
				if(evento.getButton()== MouseEvent.BUTTON1) {
					try {
						Desktop.getDesktop().open(a.getDireccion().getParentFile());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else if(evento.getButton()== MouseEvent.BUTTON3) {
					try {
						Desktop.getDesktop().open(a.getDireccion());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		switch (e.getActionCommand()) {
		case "0":
				frame.setVisible(false);
			try {
				if(cargarDatos()) {
					inicializarDatos();
				}
				else {
					frame.dispose();
				}
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			break;
			
		case "1":
			frame.dispose();
			break;
		case "2":
			try {
				Carpeta raiz= (Carpeta) raizNodo.getUserObject();
				Ficheros.crearFichero(raiz,frame);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			break;	
		case "3":
			try {
				ModeloTablaCarpeta modeloCarpeta = (ModeloTablaCarpeta) tablaCarpeta.getModel();
				Ficheros.crearFichero(modeloCarpeta.getCarpeta(),frame);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			break;		
		}
		
	}
	
	public void crearFrame(){
		 frame = new JFrame("ScanDir");
		 ImageIcon icono = new ImageIcon("icono.png");
		 frame.setIconImage(icono.getImage());
	     frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	     frame.setJMenuBar(barraTareas());
	     frame.setLayout(new GridBagLayout());
	     GridBagConstraints contenidoCaracteristicas= new GridBagConstraints();
	     contenidoCaracteristicas.gridx	=0;
	     contenidoCaracteristicas.gridy=0;
	     contenidoCaracteristicas.gridheight=4;
	     contenidoCaracteristicas.weightx=0.5;
	     contenidoCaracteristicas.weighty=0.5;
	     contenidoCaracteristicas.fill=GridBagConstraints.BOTH;
	     frame.getContentPane().add(crearTree(),contenidoCaracteristicas);
	     contenidoCaracteristicas.fill=GridBagConstraints.HORIZONTAL;
	     contenidoCaracteristicas.weightx=5;
	     contenidoCaracteristicas.weighty=0;
	     contenidoCaracteristicas.gridx=1;
	     contenidoCaracteristicas.gridheight=1;
	     frame.getContentPane().add(crearTablaCarpeta(),contenidoCaracteristicas);
	     contenidoCaracteristicas.fill=GridBagConstraints.BOTH;
	     contenidoCaracteristicas.weighty=0.5;
	     contenidoCaracteristicas.gridy=1;
	     contenidoCaracteristicas.gridheight=3;
	     frame.getContentPane().add(crearTablaCentral(),contenidoCaracteristicas);
	     contenidoCaracteristicas.weightx=1;
	     contenidoCaracteristicas.gridheight=2;
	     contenidoCaracteristicas.gridx=2;
	     contenidoCaracteristicas.gridy=0;
	     frame.getContentPane().add(crearTablaTipo(),contenidoCaracteristicas);
	     contenidoCaracteristicas.fill=GridBagConstraints.HORIZONTAL;
	     contenidoCaracteristicas.weighty=0;
	     contenidoCaracteristicas.gridheight=1;
	     contenidoCaracteristicas.gridy=3;
	     frame.getContentPane().add(crearPanelFiltro(),contenidoCaracteristicas);
         frame.pack();
	}
		
	public JMenuBar barraTareas() {
		JMenuBar barra=new JMenuBar();
		JMenu file=new JMenu("File");
		JMenu export= new JMenu("Exportar");
		
		JMenuItem eleccion=new JMenuItem("Nueva Carpeta");
		eleccion.setActionCommand("0");
		eleccion.addActionListener(this);
		file.add(eleccion);
		file.addSeparator();
		
		eleccion=new JMenuItem("Salir");
		eleccion.setActionCommand("1");
		eleccion.addActionListener(this);
		file.add(eleccion);
		
		eleccion=new JMenuItem("Exportar raiz en txt");
		eleccion.setActionCommand("2");
		eleccion.addActionListener(this);
		export.add(eleccion);
		
		eleccion=new JMenuItem("Exportar carpeta seleccionada en txt");
		eleccion.setActionCommand("3");
		eleccion.addActionListener(this);
		export.add(eleccion);
		
		barra.add(file);
		barra.add(export);
		return barra;
	}
	

	
	public JScrollPane crearTablaTipo() {
		 tablaTipo=new JTable();
	     tablaTipo.setAutoCreateRowSorter(true);
	     tablaTipo.setRowHeight(25);
	     tablaTipo.setShowHorizontalLines(false);
	     tablaTipo.setRowSelectionAllowed(false); 	 
	     tablaTipo.setShowVerticalLines(false);
	     
	     return crearScrollPane("Tabla de tipos de archivo", tablaTipo); 
	}
	
	public JScrollPane crearTablaCentral() {
		 tablaCentral=new JTable(); 
		 tablaCentral.setAutoCreateRowSorter(true);
		 tablaCentral.setShowHorizontalLines(false);
		 tablaCentral.setShowVerticalLines(false);
		 tablaCentral.setRowSelectionAllowed(false);
		 tablaCentral.setRowHeight(25);
	     return crearScrollPane("", tablaCentral); 
	}
	

	
	public JScrollPane crearTree() {
		
		arbol= new JTree(raizNodo);
		arbol.setCellRenderer(new IconTreeCellRender());
		return crearScrollPane("arbol", arbol);
     

	}
	
	public JPanel crearTablaCarpeta() {
		 tablaCarpeta=new JTable();
		 tablaCarpeta.setShowHorizontalLines(false);
		 tablaCarpeta.setShowVerticalLines(false);
		 tablaCarpeta.setRowSelectionAllowed(false);
		 tablaCarpeta.setRowHeight(25);
		 JPanel panelCarpeta= new JPanel();
		 panelCarpeta.setLayout(new BorderLayout());
		 panelCarpeta.add(tablaCarpeta.getTableHeader(),BorderLayout.PAGE_START);
		 panelCarpeta.add(tablaCarpeta,BorderLayout.CENTER);
		 panelCarpeta.setBorder( BorderFactory.createCompoundBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Carpeta Seleccionada"),BorderFactory.createEmptyBorder(0,0,0,0)),panelCarpeta.getBorder()));

		 return panelCarpeta;
	}
	
	public JPanel crearPanelFiltro() {
		JPanel principal= crearPanel("Filtro", 2, 0);
		
		JPanel tipo= crearPanel("Esconder", 0, 2);
		tipo.add(crearBotonEsconder("Esconder todo", true));
		tipo.add(crearBotonEsconder("Mostrar todo", false));
		
		JPanel tamanyo= crearPanel("Tamaño", 2, 0);
		tamanyo.add(crearlineaTamanyo("Maximo",sistemaFiltro.getCheckMaximo(),sistemaFiltro.getOpcionesMaximo(),sistemaFiltro.getDatoNumericoMaximo()));
		tamanyo.add(crearlineaTamanyo("Minimo",sistemaFiltro.getCheckMinimo(),sistemaFiltro.getOpcionesMinimo(),sistemaFiltro.getDatoNumericoMinimo()));
		principal.add(tamanyo);
		principal.add(tipo);
		
		return principal;
		
	}
	
	public JButton crearBotonEsconder(String nombre,boolean esconder) {
		JButton botonMostrar = new JButton(nombre);
		botonMostrar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				for(TipoArchivo a: sistemaFiltro.getListaTipoArchivo()) {
					a.setEscondido(esconder);
				}
				actualizarTablasFiltro();
			}
		});
		return botonMostrar;
	}
	
	
	public JPanel crearlineaTamanyo(String nombre, JCheckBox check, JComboBox<String> combo, JTextField texto) {
		JPanel linea= new JPanel();
		linea.setLayout(new GridLayout(0,4));
		linea.add(texto);
		linea.add(combo);
		linea.add(check);
		linea.add(new JLabel(nombre));
		return linea;
	}
	
	public JScrollPane crearScrollPane(String texto, JComponent componente) {
		JScrollPane scroll= new JScrollPane(componente);
	     scroll.setBorder( BorderFactory.createCompoundBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(texto),BorderFactory.createEmptyBorder(0,0,0,0)),scroll.getBorder()));
		return scroll;
	}
	
	public JPanel crearPanel(String texto, int x, int y) {
		JPanel auxiliarPanel= new JPanel() ;
		auxiliarPanel.setLayout(new GridLayout(x,y));
		auxiliarPanel.setBorder( BorderFactory.createCompoundBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(texto),BorderFactory.createEmptyBorder(0,0,0,0)),auxiliarPanel.getBorder()));
		return auxiliarPanel;
		
	}
}
