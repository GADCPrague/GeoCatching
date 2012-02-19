package cz.leftovers.geocatching;

import java.util.ArrayList;

import cz.leftovers.geocatching.GameListActivity.LineOfGame;

public class DataHandler {

	public ParsedActiveGamesSet pags;
	public ParsedMyGamesSet pmgs;
	public ParsedOneGameSet pogs;
	
	public DataHandler(){
		pags = new ParsedActiveGamesSet();
		pmgs = new ParsedMyGamesSet();
		pogs = new ParsedOneGameSet();
		
		testingAttentionPlease();
	}
	
	public void testingAttentionPlease() {
		pags.names.add("first game");
		pags.names.add("second game");
		pags.names.add("third game");
		pags.names.add("fourth game");
		
		pags.locations.add("Prague");
		pags.locations.add("Paris");
		pags.locations.add("NYC");
		pags.locations.add("London");
		
		pags.capacity.add(300);
		pags.capacity.add(600);
		pags.capacity.add(900);
		pags.capacity.add(700);
		
		pags.connected.add(42);
		pags.connected.add(89);
		pags.connected.add(323);
		pags.connected.add(4);
		
		pags.starts.add("01/03/2012");
		pags.starts.add("10/04/2012");
		pags.starts.add("29/02/2012");
		pags.starts.add("30/06/2012");
		
		pags.ends.add("01/05/2012");
		pags.ends.add("10/06/2012");
		pags.ends.add("29/05/2012");
		pags.ends.add("31/12/2012");
		
		/* ==================================== */
		
		pmgs.names.add("second game");
		pmgs.names.add("fourth game");
		
		pmgs.locations.add("Paris");
		pmgs.locations.add("London");
		
		pmgs.capacity.add(600);
		pmgs.capacity.add(700);
		
		pmgs.connected.add(89);
		pmgs.connected.add(4);
		
		pmgs.starts.add("10/04/2012");
		pmgs.starts.add("30/06/2012");
		
		pmgs.ends.add("10/06/2012");
		pmgs.ends.add("31/12/2012");
		
		/* ==================================== */
		
		pogs.activeUsersCount = 70;
		pogs.allUsersCount = 89;
		pogs.preyName = "Collin Jones";
		pogs.preyAge = 22;
		pogs.interests = "trolling";
	}
	
	class ParsedActiveGamesSet {
		public ArrayList<String> names = new ArrayList<String>();
		public ArrayList<String> locations = new ArrayList<String>();
		public ArrayList<Integer> capacity = new ArrayList<Integer>();
		public ArrayList<Integer> connected = new ArrayList<Integer>();
		public ArrayList<String> starts = new ArrayList<String>();
		public ArrayList<String> ends = new ArrayList<String>();
		
		public ArrayList<LineOfGame> lg;
	}
	class ParsedMyGamesSet {
		public ArrayList<String> names = new ArrayList<String>();
		public ArrayList<String> locations = new ArrayList<String>();
		public ArrayList<Integer> capacity = new ArrayList<Integer>();
		public ArrayList<Integer> connected = new ArrayList<Integer>();
		public ArrayList<String> starts = new ArrayList<String>();
		public ArrayList<String> ends = new ArrayList<String>();
	}
	
	class ParsedOneGameSet {
		public int activeUsersCount;
		public int allUsersCount;
		public String preyName;
		public int preyAge;
		public String interests;
	}
}
