// http://nmedit.sourceforge.net
    
package nomad.application.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.ToolTipManager;

import nomad.application.Run;
import nomad.model.descriptive.ModuleDescriptions;
import nomad.model.descriptive.substitution.XMLSubstitutionReader;
import nomad.patch.Patch;

public class Nomad extends JFrame {
    public static String creatorProgram = "nomad";
    public static String creatorVersion = "v0.1";
    public static String creatorRelease = "";
	
	JFileChooser fileChooser = new JFileChooser("./data/patches/");
	
	JMenuBar menuBar = null;
	JMenu menuFile = null;
	JMenuItem menuExitItem,	menuNewItem, menuOpenItem, menuCloseItem, menuCloseAllItem = null;
	JMenuItem menuSaveItem, menuSaveAsItem, menuSaveAllItem = null;

	JPanel toolPanel = null;
	JTabbedPane tabbedPane = null;

	JButton button = null;

	JPanel panelMain = null;

    class NewListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JPanel tab = new Patch().createPatch("");
            tabbedPane.add("new" + (tabbedPane.getTabCount()+1), tab);
            tabbedPane.setSelectedComponent(tab);
			tabbedPane.getSelectedComponent().setName(tabbedPane.getTitleAt(tabbedPane.getSelectedIndex()));
        }
    }

    class ExitListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

	class FileLoadListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				String name = fileChooser.getSelectedFile().getName();
				name = name.substring(0,name.indexOf(".pch"));
	            JPanel tab = new Patch().createPatch(fileChooser.getSelectedFile().getPath());
				tabbedPane.add(name,tab);
				tabbedPane.setSelectedComponent(tab);
				tabbedPane.getSelectedComponent().setName(tabbedPane.getTitleAt(tabbedPane.getSelectedIndex()));
			}
		}
	}

	class FileSaveAsListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			fileChooser.setSelectedFile(new File(tabbedPane.getSelectedComponent().getName() + "_new.pch"));
			if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
				OutputStream stream;
				try {
					stream = new FileOutputStream(fileChooser.getSelectedFile());
//					stream.write(((Patch)tabbedPane.getSelectedComponent()).savePatch().toString().getBytes());
					stream.flush();
					stream.close();
				}
				catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	class FileCloseListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			tabbedPane.remove(tabbedPane.getSelectedComponent());
		}
	}

	class ExitWindowListener extends WindowAdapter {
		public void windowClosing(WindowEvent e) {
			System.exit(0);
		}
	}
	
	public Nomad() {

		String loadfile = "src/data/xml/substitutions.xml"; 
		Run.statusMessage(loadfile);
		XMLSubstitutionReader subsReader = new XMLSubstitutionReader(loadfile);
		loadfile = "src/data/xml/modules.xml"; 
		Run.statusMessage(loadfile);
		ModuleDescriptions moduleDescriptions = new ModuleDescriptions(loadfile, subsReader);
		loadfile = "slice:toolbar-icons.gif"; 
		Run.statusMessage(loadfile);
		moduleDescriptions.loadIconsFromSlice("src/data/images/toolbar-icons.gif");
		Run.statusMessage("building toolbar");
		ModuleToolbar moduleToolbar = new ModuleToolbar(moduleDescriptions); 
		
        ToolTipManager.sharedInstance().setInitialDelay(0);
        
        this.setTitle(creatorProgram + " " + creatorVersion + " " + creatorRelease);

        this.setJMenuBar(createMenu());

// Main panel
		panelMain = new JPanel();
		panelMain.setLayout(new BorderLayout());

// TabbedPane
		tabbedPane = new JTabbedPane();
		panelMain.add(tabbedPane, BorderLayout.CENTER);

		this.getContentPane().add(panelMain,BorderLayout.CENTER);
		this.getContentPane().add(moduleToolbar, BorderLayout.NORTH);

		this.addWindowListener(new ExitWindowListener());

		this.setSize(1024, 768);
		
        JPanel tab = new Patch().createPatch("src/data/patches/all.pch");
        tabbedPane.add("all.pch", tab);
		tabbedPane.setSelectedComponent(tab);
		tabbedPane.getSelectedComponent().setName(tabbedPane.getTitleAt(tabbedPane.getSelectedIndex()));

//        Patch patch = new Patch();
//        tabbedPane.add("new" + (tabbedPane.getTabCount()+1),patch.createPatch(""));
//        tabbedPane.setSelectedComponent(patch);
//        tabbedPane.getSelectedComponent().setName(tabbedPane.getTitleAt(tabbedPane.getSelectedIndex()));
    }

	public JMenuBar createMenu() {
		menuBar = new JMenuBar();
	
		menuFile = new JMenu("File");
			menuNewItem = menuFile.add("New");
			menuOpenItem = menuFile.add("Open...");
			menuCloseItem = menuFile.add("Close");
//			menuCloseAllItem = menuFile.add("Close All");
//			menuFile.addSeparator();
//			menuSaveItem = menuFile.add("Save");
			menuSaveAsItem = menuFile.add("Save As...");
//			menuSaveAllItem = menuFile.add("Save All");
			menuFile.addSeparator();
			menuExitItem = menuFile.add("Exit");

		menuBar.add(menuFile);

        menuNewItem.addActionListener(new NewListener());
        menuExitItem.addActionListener(new ExitListener());
		menuOpenItem.addActionListener(new FileLoadListener());
		menuSaveAsItem.addActionListener(new FileSaveAsListener());
		menuCloseItem.addActionListener(new FileCloseListener());
		
		return menuBar; 
	}

	public static void main(String[] args) {
		try {
//			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
//			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (Exception e) {
		}

		/* Default theme */
		System.setProperty("swing.metalTheme", "steel");

		new Nomad();
	}
}
