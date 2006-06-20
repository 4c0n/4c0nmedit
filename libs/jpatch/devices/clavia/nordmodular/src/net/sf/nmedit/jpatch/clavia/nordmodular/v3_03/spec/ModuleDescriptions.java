package net.sf.nmedit.jpatch.clavia.nordmodular.v3_03.spec;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

import org.xmlpull.v1.XmlPullParserException;

import net.sf.nmedit.jpatch.clavia.nordmodular.v3_03.spec.substitution.Substitutions;
import net.sf.nmedit.jpatch.clavia.nordmodular.v3_03.xml.ModuleDescriptionParser;

/**
 * A container for all module relevant information.
 * 
 * @author Christian Schneider
 * @composed 1 - n nomad.model.descriptive.DGroup
 */
public class ModuleDescriptions {

	/** Hashmap containing the pairs (DModule.getKey(), DModule) */
	
	private DModule[] modules = new DModule[DModule.APPROXIMATE_MODULE_COUNT];
	
	/** The groups */
	private ArrayList<DGroup> dgroups = new ArrayList<DGroup>();
	/** the static data */
	private static ModuleDescriptions model = null;
	
	public static ModuleDescriptions sharedInstance() {
		return model;
	}
	
	public ModuleDescriptions() {
		Arrays.fill(modules, null);
	}
	
	/**
	 * Initializes the static model by loading all data from the xml file 
	 * @param xmlfile the xml file
	 * @param subs the substitution xml file reader
	 */
	public static void init(String xmlfile, Substitutions subs) {
		model = new ModuleDescriptions(xmlfile, subs);
	}

	/**
	 * Returns a module by it's id
	 * @param id the id
	 * @return the module
	 */
	public DModule getModuleById(int id) {
		return modules[id];
	}
	
	/**
	 * Returns a toolbar group at given index
	 * @param index the index
	 * @return the group
	 */
	public DGroup getGroup(int index) {
		return dgroups.get(index);
	}
	
	/**
	 * Returns the number of groups
	 * @return the number of groups
	 */
	public int getGroupCount() {
		return dgroups.size();
	}
	
	/**
	 * Returns a collection containing all DModule objects
	 * @return a collection containing all DModule objects
	 */
	/*
	public Collection<DModule> getModules() {
		
	}*/
	
	private Substitutions substitutions = null;
	
	public ModuleDescriptions(String xmlfile, Substitutions subs) {
		this.substitutions = subs;

		ModuleDescriptionParser parser = new ModuleDescriptionParser(this);
		try {
			parser.parse(xmlfile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}

	}
	
	public Substitutions getSubstitutions() {
		return substitutions;
	}
	
	public void add(DModule module) {
		modules[module.getModuleID()] = module;
	}

	public void addToolbarGroup(DGroup group) {
		dgroups.add(group);
	}

	public DModule[] getModuleList() {
		int count = 0;
		for (int i=modules.length-1;i>=0;i--)
			if (modules[i]!=null)
				count++;
		
		DModule[] list = new DModule[count];
		
		int dst=0;
		for (int src=0;src<modules.length-1;src++) 
			if (modules[src]!=null)
				list[dst++]=modules[src];
		return list;
		
	}

    public DGroup[] getGroups()
    {
        return dgroups.toArray(new DGroup[getGroupCount()]);
    }
	
}