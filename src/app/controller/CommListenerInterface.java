package app.controller;

import java.io.FileNotFoundException;

/**
 * comm port listener interface
 * @author Krzysiek
 */
public interface CommListenerInterface {
	/**
	 * message received notification
	 * @param data message in array
	 * @param len message length
	 * @throws FileNotFoundException 
	 */
    public void messageReceived(int[] data);
    
    /**
     * port in use error notification
     */
    public void portAlreadyInUse();
    
    /**
     * write to port error notification
     */
    public void writingError();
    
    /**
     * read from port error notification
     */
    public void readingError();
}
