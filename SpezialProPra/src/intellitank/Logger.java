package intellitank;

import java.awt.Color;
import java.io.IOException;

import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

public class Logger
{
	private JTextPane console;
	
	public boolean debug = false;
	
	public Logger(JTextPane console)
	{
		this.console = console;
		
		console.setEditable(false);
	}
	
	public boolean isDebug()
	{
		return debug;
	}

	public void setDebug(boolean debug)
	{
		this.debug = debug;
	}
	
	public void log(String msg)
	{
		if(debug) System.out.println(msg);
		
		printToConsole("[LOG] " + msg, PrintType.NORMAL);
	}
	
	public void warning(String warning)
	{
		if(debug) System.out.println(warning);
		
		printToConsole("[WARNING] " + warning, PrintType.WARNING);
	}
	
	public void error(String error)
	{
		if(debug) System.err.println(error);
		
		printToConsole("[ERROR] " + error, PrintType.ERROR);
	}
	
	public void throwGeneric(String error)
	{
		error("(type 100) " + error);
	}
	
	public void throwInvalidDataInput(String in)
	{
		error("invalid data input in >" + in + "< " + "(type 101)");
	}
	
	public void throwNumberFormat(NumberFormatException exception)
	{
		error(exception.toString() + "(type 102)");
	}
	
	public void throwIO(IOException exception)
	{
		error(exception.toString() + "(type 103)");
	}
	
	public void throwInvalidGasstationID(int id)
	{
		error("invalid gas station id: " + id + " (type 104)");
	}
	
	private void printToConsole(String msg, PrintType type)
	{
		if(console != null)
		{
			StyleContext context = StyleContext.getDefaultStyleContext();
	        AttributeSet attSet = context.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, type.getColor());
	
	        attSet = context.addAttribute(attSet, StyleConstants.FontFamily, "Lucida Console");
	        attSet = context.addAttribute(attSet, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);
	
	        console.setCaretPosition(console.getDocument().getLength());
	        console.setCharacterAttributes(attSet, false);
	        console.replaceSelection(msg + "\n");
		}
	}
	
	enum PrintType
	{
		NORMAL, WARNING, ERROR;
		
		public Color getColor()
		{
			if(this.equals(WARNING)) return Color.ORANGE;
			else if(this.equals(ERROR)) return Color.RED;
			else return Color.BLACK;
		}
	}
}
