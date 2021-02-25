package alu100495.DialogTXT;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import alu100495.Almacenamiento.Carpeta;

public class DialogTexto extends JDialog implements ActionListener,PropertyChangeListener {
	private Carpeta raiz;
	private String texto;
	private JTextField textField;
	
	private JOptionPane optionPane;
	private String btnString1 = "Enter";
    private String btnString2 = "Cancel";
    
	public DialogTexto(JFrame frame,Carpeta carpetaGuardar) {
		super(frame, true);
		raiz=carpetaGuardar;
		texto="Dame el nombre del txt:";
		textField= new JTextField();
		Object[] array = {texto,textField};
		
		 
	    //Create an array specifying the number of dialog buttons
	    //and their text.
	    Object[] options = {btnString1, btnString2};

	    //Create the JOptionPane.
	    optionPane = new JOptionPane(array, JOptionPane.QUESTION_MESSAGE,JOptionPane.YES_NO_OPTION,null,options,options[0]);
	    setContentPane(optionPane);
	    //setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent we) {
                	optionPane.setValue(new Integer(JOptionPane.CLOSED_OPTION));
            }
        });
        
        this.pack();
        
        addComponentListener(new ComponentAdapter() {
            public void componentShown(ComponentEvent ce) {
                textField.requestFocusInWindow();
            }
        });
 
        //Register an event handler that puts the text into the option pane.
        textField.addActionListener(this);
 
        //Register an event handler that reacts to option pane state changes.
        optionPane.addPropertyChangeListener(this);
    }
 
    /** This method handles events for the text field. */
    public void actionPerformed(ActionEvent e) {
        optionPane.setValue(btnString1);
    }
 
    /** This method reacts to state changes in the option pane. */
    public void propertyChange(PropertyChangeEvent e) {
        String prop = e.getPropertyName();
 
        if (isVisible() && (e.getSource() == optionPane) && (JOptionPane.VALUE_PROPERTY.equals(prop) || JOptionPane.INPUT_VALUE_PROPERTY.equals(prop))) { 
        	Object value = optionPane.getValue();
 
            if (value == JOptionPane.UNINITIALIZED_VALUE) {
                //ignore reset
                return;
            }
 
            optionPane.setValue(JOptionPane.UNINITIALIZED_VALUE);
 
            if (btnString1.equals(value)) {
            		BufferedWriter out = null;
					try {
						out = new BufferedWriter(new FileWriter(textField.getText()+".txt"));
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
        			try {
						out.write(raiz.toString(0));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
        			try {
						out.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                }
            } 
        
              clearAndHide();
            
        }
    
 
    /** This method clears the dialog and hides it. */
    public void clearAndHide() {
        textField.setText(null);
        setVisible(false);
    }


}