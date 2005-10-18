package nomad.gui;

import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import nomad.patch.Module;

public class ModuleGUI extends JPanel implements MouseListener, MouseMotionListener {

	private JLabel nameLabel = null;
	private ModuleSectionGUI parent = null;
	private Module module = null;

//  --- drag
	int dragX = 0, dragY = 0;
    int oldModuleDragX = 0;
    int oldModuleDragY = 0;

    public ModuleGUI(Module module, ModuleSectionGUI moduleSectionGUI) {
    	super();
    	
    	addMouseListener(this);
    	addMouseMotionListener(this);
    	
    	parent = moduleSectionGUI;
    	this.module = module;
    	
        setLayout(null);
    	setBorder(BorderFactory.createRaisedBevelBorder());
    	
    	nameLabel = new JLabel("");
        nameLabel.setLocation(3,0);
        nameLabel.setFont(new Font("Dialog", Font.PLAIN, 10));
        add(nameLabel);
    }
    
    public void setNameLabel(String name, int width) {
        nameLabel.setSize(width, 16);
    	nameLabel.setText(name);
    }
    
    public Module getModule() {
    	return module;
    }

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
	    if (!e.isPopupTrigger()) {
	        dragX = e.getX();
	        dragY = e.getY();
	        oldModuleDragX = dragX;
	        oldModuleDragY = dragY;
	        parent.setLayer(this, JLayeredPane.DRAG_LAYER.intValue());
	    }
	}

	public void mouseReleased(MouseEvent e) {
	    if (!e.isPopupTrigger()) { 
	    	parent.setLayer(this, JLayeredPane.DEFAULT_LAYER.intValue());
	        module.setNewPixLocation(getLocation().x, getLocation().y);
			module.getModuleSection().rearangeModules(module);
	    }

//	    if (e.isPopupTrigger()) {
//            menu.show(e.getComponent(), e.getX(), e.getY());
//        }
	}

	public void mouseDragged(MouseEvent e) {
        if (!e.isPopupTrigger()) { // TODO +BUG??? e.getButton() always 0 ???!
            setLocation(getLocation().x + (e.getX() - dragX), getLocation().y + (e.getY() - dragY));
        }
	}

	public void mouseMoved(MouseEvent e) {
	}
}