package plugin.classictheme; 

import org.nomad.plugin.NomadFactory;

/**
 * The classic theme plugin.
 * 
 * @author Christian Schneider
 */
public class NomadPlugin extends org.nomad.plugin.NomadPlugin {

	private final
	String[] author_list = 
		new String[]{"Ian Hoogeboom", "Christian Schneider"};
	
	public String getName() {
		return "Classic Theme";
	}

	public String[] getAuthors() {
		return author_list;
	}

	public String getDescription() {
		return "Theme of the official editor.";
	}

	public int getFactoryType() {
		return org.nomad.plugin.NomadPlugin.NOMAD_FACTORY_TYPE_UI;
	}

	public NomadFactory getFactoryInstance() {
		return new ClassicThemeFactory();
	}

	public boolean supportsCurrentPlatform() {
		// supports any platform
		return true;
	}

}
