package org.nomad.port;

/**
 * Error handler for the HeartBeatTask class. 
 * 
 * @author Christian Schneider
 * @see org.nomad.port.HeartbeatTask
 * @hidden
 */
public interface HeartbeatErrorHandler {

	/**
	 * Invoked if an exception occured inside the thread that calls
	 * the heartbeat() message of the used ComPort instance
	 * @param message the message
	 * @see ComPort
	 * @see HeartbeatTask
	 */
	void exceptionOccured(HeartbeatTaskExceptionMessage message);

}