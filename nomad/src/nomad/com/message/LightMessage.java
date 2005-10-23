package nomad.com.message;

import nomad.com.ComPortListener;

/**
 * @author Christian Schneider
 * @hidden
 */
public abstract class LightMessage extends MidiMessage {
	public abstract int getStartIndex();
	public abstract int getLightStatus(int lightNo); 
	public abstract int getPid();

	public void notifyListener(ComPortListener listener) {
		listener.messageReceived(this);
	}
}
