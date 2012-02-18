package cz.leftovers.geocatching;

import java.util.ArrayList;

public class DataHandler {

	public ParsedActiveGamesSet pags;
	public ParsedMyGamesSet pmgs;
	public ParsedOneGameSet pogs;
	
	public DataHandler(){
		pags = new ParsedActiveGamesSet();
		pmgs = new ParsedMyGamesSet();
		pogs = new ParsedOneGameSet();
	}
	
	private class ParsedActiveGamesSet {
		public ArrayList<String> names = new ArrayList<String>();
	}
	private class ParsedMyGamesSet {
		public ArrayList<String> posInNames = new ArrayList<String>();
	}
	
	private class ParsedOneGameSet {
		
	}
}
