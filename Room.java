//The following class creates a room and stores its name, state, description and adjacent rooms.
//additionally this class stores details about the animals in the room


import java.util.Arrays;
import java.util.HashMap;

class Room {

	final String ROOM_NAME;
	final String[] STATE_ARRAY = {"dirty","half-dirty","clean"};
	int stateIndex; // makes indexing through the stateArray possible by holding a rooms current StateArray index value
	String state;
	final String DESCRIPTION;
	HashMap<String, Creature> roomMap = new HashMap<>();
	boolean capacityReached = false;
	Room north = null;
	Room east = null;
	Room south = null;
	Room west = null;
	String[] ROOM_DIRECTION_ARRAY = new String[4]; // used for the -Rooms "toString"- and the -Creatures "look method"-


	public Room(String state, String ROOM_NAME, String DESCRIPTION) {
		this.ROOM_NAME = ROOM_NAME;
		this.DESCRIPTION = DESCRIPTION;
		setStateIndex(state);
		this.state = STATE_ARRAY[stateIndex];
	}
	
	//adds a given animal to the next available index in the roomArray
	//if the room is at capacity, it stores the remaining animals in the Over Capacity List as a string(aka overCapList)
	public void addCreature(Creature creatureName){

		//allows creatures to be added until Capacity is reached
		if (roomMap.size() < 10) {
			roomMap.put(creatureName.CREATURE_NAME.toLowerCase(), creatureName);
			creatureName.currentRoom = Room.this;
		}

		if (roomMap.size() == 10) {
			capacityReached = true;
		}
	}			
	
	//the toString returns all of the information collected about the given room including
	//information about the animals who were supposed to be added but took the room over capacity.
	public String toString(){
		return ROOM_NAME;

	}

//declare an existing room as adjacent in the given direction.
	public void assignNorthRoom(Room north) {
		this.north = north;
		this.ROOM_DIRECTION_ARRAY[0] = this.north.ROOM_NAME;
		north.south = Room.this;
		north.ROOM_DIRECTION_ARRAY[2] = north.south.ROOM_NAME;
	}
	public void assignEastRoom(Room east) {
		this.east = east;
		this.ROOM_DIRECTION_ARRAY[1] = this.east.ROOM_NAME;
		east.west = Room.this;
		east.ROOM_DIRECTION_ARRAY[3] = east.west.ROOM_NAME;
	}
	public void assignSouthRoom(Room south) {
		this.south = south;
		this.ROOM_DIRECTION_ARRAY[2] = this.south.ROOM_NAME;
		south.north = Room.this;
		south.ROOM_DIRECTION_ARRAY[0] = south.north.ROOM_NAME;
	}
	public void assignWestRoom(Room west) {
		this.west = west;
		this.ROOM_DIRECTION_ARRAY[3] = this.west.ROOM_NAME;
		west.east = Room.this;
		west.ROOM_DIRECTION_ARRAY[1] = west.east.ROOM_NAME;
	}

//this is meant to give the state Index its initial value
	private void setStateIndex(String state){

		switch (state.toLowerCase()){
			case "dirty":
				this.stateIndex = 0;
				break;

			case "half-dirty":
				this.stateIndex = 1;
				break;

			case "clean":
				this.stateIndex = 2;
				break;

			default:
				System.out.println("You entered in a state that does not exist for the " + Room.this.ROOM_NAME + " room.");
				System.exit(-1);
		}
	}
}
