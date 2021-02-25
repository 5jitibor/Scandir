package alu100495.ModeloTree;

import java.awt.Component;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import alu100495.Almacenamiento.Carpeta;


public class IconTreeCellRender extends DefaultTreeCellRenderer {
	private static final long serialVersionUID = 1L;


    public IconTreeCellRender() {
 
    }

    public Component getTreeCellRendererComponent( JTree tree,Object value, boolean sel,boolean expanded,boolean leaf,int row,boolean hasFocus) {
    	
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
        DefaultMutableTreeNode aux =(DefaultMutableTreeNode)value;
        Carpeta c=(Carpeta) aux.getUserObject();
        setIcon(c.getIcono());
        return this;
    }

    
    
}