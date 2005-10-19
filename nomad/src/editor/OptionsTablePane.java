package editor;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

import nomad.model.descriptive.DModule;
import nomad.model.descriptive.DParameter;

public class OptionsTablePane extends JPanel {

	private JTable table = null;
	private JScrollPane scrollpane = null;
	private ModulePane modulePane = null;
	private OptionsTableModel dataModel = null;
	
	public OptionsTablePane() {
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(200, 300));
		dataModel = new OptionsTableModel();
		table = new JTable(dataModel);
	    table.getColumnModel().getColumn(0).setHeaderValue("Property");
	    table.getColumnModel().getColumn(1).setHeaderValue("Value");
	    
	    table.setEditingColumn(1);
		/*
		model.addColumn("Col1");
	    model.addColumn("Col2");
	    */
	    // Change name of first visible column
	    
		
	    scrollpane = new JScrollPane(table);
	    scrollpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	    scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

	    this.add(scrollpane, BorderLayout.CENTER);
	}

	public void setModulePane(ModulePane modulePane) {
		this.modulePane=modulePane;
		dataModel.setModule(modulePane);
		updateEditor();
	}
	
	private void updateEditor() {
		//
	}
	
	class OptionsTableModel extends AbstractTableModel {
		
		private DModule module=null;
		private ModulePane mpane = null;
		
		public void setModule(ModulePane modulePane) {
			this.module= null;
			this.mpane = modulePane;
			
			if (modulePane!=null) {
				this.module=modulePane.getModule();
				for (int i=0;i<module.getParameterCount();i++) {
					DParameter param = module.getParameter(i);
					JSlider slider = mpane.getParamControl(param);
					slider.addChangeListener(new ParamChangeListener(i));
				}
			}
			this.fireTableDataChanged();
		}
		
		class ParamChangeListener implements ChangeListener {
			private int index=0;
			public ParamChangeListener(int index) {
				this.index=index;
			}
			public void stateChanged(ChangeEvent arg0) {
				OptionsTableModel.this.fireTableCellUpdated(index,1);
			}
		}

		public int getRowCount() {
			if (module!=null) {
				return module.getParameterCount();
			}
			return 0;
		}

		public Object getValueAt(int row, int column) {
			if (module!=null) {
				if (column==0) {
					if (row<module.getParameterCount()) {
						return module.getParameter(row).getName();
					}
				} else if (column==1) {
					if (row<module.getParameterCount()) {
						DParameter param = module.getParameter(row);
						JSlider slider = mpane.getParamControl(param);
						return param.getFormattedValue(slider.getValue());
					}
				} else
					return "0";
			}
			
			return "null";
		}

		public int getColumnCount() {
			return 2;
		}	
	}
	
}
