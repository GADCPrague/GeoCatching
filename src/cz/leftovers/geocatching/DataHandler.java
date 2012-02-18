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
		public ArrayList<String> locations = new ArrayList<String>();
		public ArrayList<Integer> capacity = new ArrayList<Integer>();
		public ArrayList<Integer> connected = new ArrayList<Integer>();
		public ArrayList<String> starts = new ArrayList<String>();
		public ArrayList<String> ends = new ArrayList<String>();
	}
	private class ParsedMyGamesSet {
		public ArrayList<String> names = new ArrayList<String>();
		public ArrayList<String> locations = new ArrayList<String>();
		public ArrayList<Integer> limit = new ArrayList<Integer>();
		public ArrayList<Integer> connected = new ArrayList<Integer>();
		public ArrayList<String> starts = new ArrayList<String>();
		public ArrayList<String> ends = new ArrayList<String>();
	}
	
	private class ParsedOneGameSet {
		private int activeUsersCount;
		private int allUsersCount;
		private String preyName;
		private int preyAge;
		private String interests;
	}
}
