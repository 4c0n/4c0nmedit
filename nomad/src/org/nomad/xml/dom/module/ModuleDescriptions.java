package org.nomad.xml.dom.module;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;


import org.nomad.util.misc.ImageTracker;
import org.nomad.xml.XMLAttributeReader;
import org.nomad.xml.XMLAttributeValidationException;
import org.nomad.xml.XMLReader;
import org.nomad.xml.dom.substitution.Substitution;
import org.nomad.xml.dom.substitution.XMLSubstitutionReader;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * A container for all module relevant information.
 * 
 * @author Christian Schneider
 * @composed 1 - n nomad.model.descriptive.DGroup
 */
public class ModuleDescriptions {

	/** Hashmap containing the pairs (DModule.getKey(), DModule) */
	private HashMap dmodules = new HashMap();
	/** The groups */
	private ArrayList dgroups = new ArrayList();
	/** the static data */
	public static ModuleDescriptions model = null;
	
	/**
	 * Initializes the static model by loading all data from the xml file 
	 * @param xmlfile the xml file
	 * @param subs the substitution xml file reader
	 */
	public static void init(String xmlfile, XMLSubstitutionReader subs) {
		model = new ModuleDescriptions(xmlfile, subs);
	}

	/**
	 * Loads the module icons
	 * @param imageTracker the image source
	 */
	public void loadImages(ImageTracker imageTracker) {
		DConnector.setImageTracker(imageTracker);
		//DConnector.loadImages(imageTracker);

		Iterator iter = dmodules.values().iterator();
		while (iter.hasNext()) {
			DModule module = (DModule) iter.next();
			module.setIcon(imageTracker.getImage(Integer.toString(module.getModuleID())));
		}
	}

	/**
	 * Retuns a module by it's key
	 * @param key the key
	 * @return the module
	 */
	public DModule getModuleByKey(String key) {
		return (DModule) dmodules.get(key);
	}

	/**
	 * Returns a module by it's id
	 * @param id the id
	 * @return the module
	 */
	public DModule getModuleById(int id) {
		return getModuleByKey(DModule.getKeyFromId(id));
	}
	
	/**
	 * Returns a toolbar group at given index
	 * @param index the index
	 * @return the group
	 */
	public DToolbarGroup getGroup(int index) {
		return (DToolbarGroup) dgroups.get(index);
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
	public Collection getModules() {
		return dmodules.values();
	}
	
	public ModuleDescriptions(String xmlfile, XMLSubstitutionReader subs) {
		loadFromXML(xmlfile, subs);
	}

	private void loadFromXML(String file, XMLSubstitutionReader subs) {
		loadFromXML(new File(file), subs);
	}
	
	private void loadFromXML(File file, XMLSubstitutionReader subs) {
		Document document = XMLReader.readDocument(file, true);
		if (document!=null)
			loadFromXML(file.getParent()+File.separatorChar, document, subs);
	}
	
	private void loadFromXML(String path, Document doc, XMLSubstitutionReader subs) {
		// load modules
		NodeList modules = doc.getElementsByTagName("module");

		for (int i=0;i<modules.getLength();i++)
			loadModule(modules.item(i), subs);
		modules=null;
		
		// load structure
		NodeList groups = doc.getElementsByTagName("group");
		for (int i=0;i<groups.getLength();i++)
			loadGroup(groups.item(i));
	}
	
	private void loadGroup(Node groupNode) {
		XMLAttributeReader att = new XMLAttributeReader(groupNode);

		String groupName=null;
		try {
			groupName = att.getAttribute("name");
		} catch (XMLAttributeValidationException e) {
			e.printStackTrace();
		}
		
		DToolbarGroup group = new DToolbarGroup(groupName);
		dgroups.add(group);
		
		NodeList sections = groupNode.getChildNodes();
		
		for (int i=0;i<sections.getLength();i++) {
			Node n = sections.item(i);
			if (n.getNodeName().equals("section")) {
				DToolbarSection section = new DToolbarSection(group);
				group.addSection(section);
				
				NodeList modules = sections.item(i).getChildNodes();
				for (int j=0;j<modules.getLength();j++) {
					n = modules.item(j);
					if (n.getNodeName().equals("insert")) {
						att = new XMLAttributeReader(n);
						try {
							String key = att.getAttribute("module-id");
							if (!dmodules.containsKey(key)) {
								System.err.println("** Warning, referenced module (key='"+key+"') does not exist.");
							} else {
								DModule module = (DModule)dmodules.get(key);
								module.setParent(section);
								section.addModule(module);
							}
						} catch (XMLAttributeValidationException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
	
	private void loadModule(Node moduleNode, XMLSubstitutionReader subs) {
		XMLAttributeReader att = new XMLAttributeReader(moduleNode);

		int moduleId;
		String moduleName;
		String shortname;
		int height=0;

		try {
			moduleId = att.getIntegerAttribute("id");
			shortname = att.getAttribute("short-name");
			height = att.getIntegerAttribute("height");
		} catch (XMLAttributeValidationException e) {
			e.printStackTrace();
			return;
		}
		moduleName=att.getAttribute("name", shortname);

		DModule module = new DModule(null, moduleName);
		module.setModuleID(moduleId);
		module.setHeight(height);

		// load not required attributes
		module.setShortName(shortname);
		module.setCycles(att.getDoubleAttribute("cycles",0));
		module.setXmem(att.getDoubleAttribute("xmem",0));
		module.setYmem(att.getDoubleAttribute("ymem",0));
		module.setProgMem(att.getDoubleAttribute("progMem",0));
		module.setDynMem(att.getDoubleAttribute("dynmem",0));
		module.setZeroPage(att.getDoubleAttribute("zeropage",0));

		// load contents: input, output, parameter nodes
		NodeList nlist = moduleNode.getChildNodes();
		for (int i=0;i<nlist.getLength();i++) {
			Node element = nlist.item(i);
			if (element.getNodeName().equals("input"))
				loadConnectionElement(module, element, DConnector.CONNECTOR_TYPE_INPUT);
			if (element.getNodeName().equals("output"))
				loadConnectionElement(module, element, DConnector.CONNECTOR_TYPE_OUTPUT);
			if (element.getNodeName().equals("parameter"))
				loadParameterElement(module, subs, element, false);
			if (element.getNodeName().equals("custom"))
				loadParameterElement(module, subs, element, true);
		}
		
		// finally store module
		dmodules.put(module.getKey(), module);
	}

	private void loadConnectionElement(DModule module, Node element, int connection_type) {
		XMLAttributeReader att = new XMLAttributeReader(element);

		int connectionId;
		String connectionType;
		String connectionName;
		try {
			connectionId = att.getIntegerAttribute("id");
			connectionType = att.getNamedAttribute("type", new String[]{"audio","control","logic","slave"});
			connectionName = att.getAttribute("name");
		} catch (XMLAttributeValidationException e) {
			e.printStackTrace();
			return ;
		}

		int signal_type=DConnector.SIGNAL_AUDIO;
		if (connectionType.equals("control"))
			signal_type = DConnector.SIGNAL_CONTROL;
		else if (connectionType.equals("logic"))
			signal_type = DConnector.SIGNAL_LOGIC;
		else if (connectionType.equals("slave"))
			signal_type = DConnector.SIGNAL_SLAVE;

		module.addConnector(new DConnector(module, connectionId, connection_type, signal_type, connectionName));
	}

	private void loadParameterElement(DModule module, XMLSubstitutionReader subs, Node elem,
			boolean isCustomParam) {
		XMLAttributeReader att = new XMLAttributeReader(elem);

		// required
		int pmId;
		String pmName;
		int pmMin;
		int pmMax;

		try {
			pmId = att.getIntegerAttribute("id");
			pmName = att.getAttribute("name");
			pmMin = att.getIntegerAttribute("min");
			pmMax = att.getIntegerAttribute("max");
		} catch (XMLAttributeValidationException e) {
			e.printStackTrace();
			return;
		}
			
		// implied
		int pmBitC = att.getIntegerAttribute("bit-count", -1);
		int pmDefault = att.getIntegerAttribute("default", 0);
		String subsName = att.getAttribute("use-substitution", null);
		Substitution s = subsName!=null?subs.getSubstitution(subsName):null;
		if (subsName!=null&&s==null) {
			System.err.println("** Warning, unit '"+subsName+"' not found.");
			System.err.println("   "+att.locateElement("use-format"));
		}
		
		if (!isCustomParam) {
			DParameter pinfo = new DParameter(module, s,
					pmMin, pmMax, pmBitC, pmId, pmName);
			pinfo.setDefaultValue(pmDefault);
			module.addParameter(pinfo);
		} else {
			DCustom pinfo = new DCustom(module, s,
					pmMin, pmMax, pmBitC, pmId, pmName);
			pinfo.setDefaultValue(pmDefault);
			module.addCustomParam(pinfo);
		}
	}
		
}