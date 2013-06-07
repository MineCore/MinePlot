package net.minecore.mineplot.plot;

public class InvalidPlotException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5622535462428760741L;
	
	public InvalidPlotException(String s){
		super(s);
	}
	
	public InvalidPlotException(){
		super("Plot invalid for unknown reasons");
	}

}
