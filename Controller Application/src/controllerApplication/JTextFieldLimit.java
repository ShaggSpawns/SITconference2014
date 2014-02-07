package controllerApplication;

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

/**
 * Allows a JTextField to have a limit on the amount of characters allowed to be inputed.
 * @author Jackson Wilson
 * @since 2014
 */
public class JTextFieldLimit extends JTextField {
	private static final long serialVersionUID = 1L; // Version identifier
	
	private final int limit; // Initializes the integer "limit"
	
	/**
	 * A JTextField with a specified character limit.
	 * @param limit
	 */
    public JTextFieldLimit(final int limit) {
        super(); // Gets the prerequisites for building a JTextField
        this.limit = limit; // Initializes the limit variable with the limit parameter
    }
    
    /**
     * Create a document model and return a new object "LimitDocument"
     */
    @Override
    protected Document createDefaultModel() { // Creates a document model
        return new LimitDocument(); // Returns a new object "LimitDocument"
    }
    
    /**
     * Create the object "LimitDocument"
     * @author Jackson Wilson 2014
     */
    private class LimitDocument extends PlainDocument {
		private static final long serialVersionUID = 1L; // Version identifier
		
		/**
		 * 
		 */
		@Override
        public void insertString( final int offset, final String  str, final AttributeSet attr ) throws BadLocationException {
            try { // Attempt to insert a string into the JTextField
            	if (str == null) return; // Returns null if str is null
            	
            	if ((getLength() + str.length()) <= limit) { // Checks to see if the next character added doesn't exceed the limit
                    super.insertString(offset, str, attr); // If it doesn't exceed the limit then the character is add to the JTextField
                }
            } catch (BadLocationException e) { // Catches an error while inserting the string into the JTextField
            	e.printStackTrace(); // Prints the error
            }
        }
    }
}