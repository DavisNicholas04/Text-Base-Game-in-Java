//The following class creates a creature and stores its name, description
//additionally it allows creatures to interact with their room and adjacent rooms based on their preferences via PC class

import java.util.HashMap;
import java.util.Random;

abstract class Creature {

		final String CREATURE_NAME;
		final String DESCRIPTION;
		Room currentRoom;
		int creatureIndexValue; //makes removing a creature from a room easy based on its location in the array
		final String COMPROMISE_PREFERENCE = "half-dirty";
		final String PREFERENCE = preferenceSetter();
		final String HATE_PREFERENCE = hatePreferenceSetter();
		final String HAPPY_RESPONSE = happyResponse();
		final String MAD_RESPONSE = madResponse();
		int lastStraw = 0;
		int neswIndexer = 3;
		boolean pcInRoom = false;
		boolean changeSuccessful = false;
		final String CREATURE_TYPE = creature_type_setter();
		Room[] nesw = new Room[4];

	public Creature(String CREATURE_NAME, String DESCRIPTION){
		this.CREATURE_NAME = CREATURE_NAME;
		this.DESCRIPTION = DESCRIPTION;
	}

	public String toString(){
		if (PC.calledFromPC) {
			return "Name: " + CREATURE_NAME + " || Creature type: " + CREATURE_TYPE + " || description: " + DESCRIPTION;
		}
		//RECENT CHANGE
		else {return CREATURE_NAME;}
	}

	private void changeRoom(Room newRoom){

		if (newRoom != null && !newRoom.capacityReached) {

			Room roomTemp = Creature.this.currentRoom;
			int indexTemp = Creature.this.creatureIndexValue;
			newRoom.addCreature(Creature.this);
			changeSuccessful = true;
			neswIndexer = 3;
			roomTemp.roomMap.remove(Creature.this.CREATURE_NAME.toLowerCase());
			//shifts the array to close the gap left by the creature that has changed rooms and changes the index value
			//of each creature in the room array to match the new real value of their index (a-respectively)



			//roomTemp.roomArray[roomTemp.lastUsedIndex] = null;
			//roomTemp.lastUsedIndex--;
			roomTemp.capacityReached = false;

			System.out.println(Creature.this.CREATURE_NAME + " has left " + roomTemp.ROOM_NAME + " and entered " + Creature.this.currentRoom.ROOM_NAME);

			if (newRoom.state.equalsIgnoreCase(Creature.this.HATE_PREFERENCE)) {
				Creature.this.changeState(PREFERENCE);
			}

			this.lastStraw = 0;
		}

		else {
			System.out.println(newRoom.ROOM_NAME + " is currently full, and " + Creature.this.CREATURE_NAME + " was not able to enter.\n" + Creature.this.CREATURE_NAME + " can try another room.");
			if (!(Creature.this instanceof PC)) {
				this.lastStraw++;
			}
		}
	}

	public void causeRoomChange(String direction) {
		String wall = "yeah that\'s a wall buddy";

		if (PC.calledFromPC = true) {
			switch (direction.toLowerCase()) {
				case "north":
					if (currentRoom.north == null) { System.out.println(wall); break; }
					changeRoom(currentRoom.north);
					break;
				case "east":
					if (currentRoom.east == null) { System.out.println(wall); break; }
					changeRoom(currentRoom.east);
					break;
				case "south":
					if (currentRoom.south == null) { System.out.println(wall); break; }
					changeRoom(currentRoom.south);
					break;
				case "west":
					if (currentRoom.west == null) { System.out.println(wall); break; }
					changeRoom(currentRoom.west);
					break;
				default:
					System.out.println("Cannot perform this action: type HELP for commands(Still needs to be implemented)");
			}
			PC.calledFromPC = false;
		}
	}

	private void changeState(String newState) {

		if (Creature.this.currentRoom.stateIndex < 2 && "clean".equalsIgnoreCase(newState) && !"clean".equalsIgnoreCase(Creature.this.currentRoom.state)) {
			Creature.this.currentRoom.stateIndex++;
			Creature.this.currentRoom.state = Creature.this.currentRoom.STATE_ARRAY[Creature.this.currentRoom.stateIndex];
			System.out.println(Creature.this.CREATURE_NAME + " has cleaned the " + Creature.this.currentRoom.ROOM_NAME + " room changing its state to " + Creature.this.currentRoom.state);
			if (pcInRoom) {
				pcInRoom = false;				
				response();
			}
		}
		else if (Creature.this.currentRoom.stateIndex > 0 && "dirty".equalsIgnoreCase(newState) && !"dirty".equalsIgnoreCase(Creature.this.currentRoom.state)) {
			Creature.this.currentRoom.stateIndex--;
			Creature.this.currentRoom.state = Creature.this.currentRoom.STATE_ARRAY[Creature.this.currentRoom.stateIndex];
			System.out.println(Creature.this.CREATURE_NAME + " has dirtied the " + Creature.this.currentRoom.ROOM_NAME + " room changing its state to " + Creature.this.currentRoom.state);
			if (pcInRoom) {
				pcInRoom = false;
				response();
			}
		}
	}
	public void causeStateChange(String newState){
		if (PC.calledFromPC = true) {
			pcInRoom = true;
			changeState(newState);
			PC.calledFromPC = false;
		}
		else {System.out.println("Cannot perform this action: type HELP for commands(Still needs to be implemented)");}
	}
	@SuppressWarnings("unchecked cast")
	private void response() {

		StringBuilder recordResponse = new StringBuilder();

		HashMap<String, Creature> roomMapCopy = (HashMap<String, Creature>) currentRoom.roomMap.clone();
		for (Creature c: currentRoom.roomMap.values()) {
			//if the creature is made to clean/dirty the room according to their preference
			if (c.PREFERENCE.equalsIgnoreCase(currentRoom.state) && c == Creature.this) {
				System.out.println(Creature.this.CREATURE_NAME + " is so happy you made them " + Creature.this.PREFERENCE + " the room, they gave you 3x the respect from them.");
				for (int i = 0; i < 3; i++) {
					PC.respect++;
					recordResponse.append(c.CREATURE_NAME).append(" ").append(c.HAPPY_RESPONSE).append("\n");
				}
			} else if ((!(c instanceof PC) && (c.HATE_PREFERENCE.equalsIgnoreCase(currentRoom.state) || c.COMPROMISE_PREFERENCE.equalsIgnoreCase(currentRoom.state))) && c == Creature.this) {
				System.out.println(Creature.this.CREATURE_NAME + " is so mad you made them " + Creature.this.HATE_PREFERENCE + " the room, you lost 3x the respect from them.");
				for (int j = 0; j < 3; j++) {
					PC.respect--;
					recordResponse.append(c.CREATURE_NAME).append(" ").append(c.MAD_RESPONSE).append("\n");
				}
			} else if (c.PREFERENCE.equalsIgnoreCase(currentRoom.state)) {
				PC.respect++;
				recordResponse.append(c.CREATURE_NAME).append(" ").append(c.HAPPY_RESPONSE).append("\n");

			} else if (!(c instanceof PC) && (c.HATE_PREFERENCE.equalsIgnoreCase(currentRoom.state) || c.COMPROMISE_PREFERENCE.equalsIgnoreCase(currentRoom.state))) {
				PC.respect--;
				recordResponse.append(c.CREATURE_NAME).append(" ").append(c.MAD_RESPONSE).append("\n");
			}
		}
		System.out.println(recordResponse);

		Room tempRoom = currentRoom;
		//for (int i = 0; i <= tempRoom.roomList.length(); i++) {
		for (Creature c: roomMapCopy.values()){

			if (c.HATE_PREFERENCE.equalsIgnoreCase(tempRoom.state)) {
				nesw[0] = currentRoom.north;
				nesw[1] = currentRoom.east;
				nesw[2] = currentRoom.south;
				nesw[3] = currentRoom.west;

				for (int j = 0; j < 4; j++) {
					c.changeRoom(c.randomRoomChange());
					if (changeSuccessful = true){
						nesw[0] = currentRoom.north;
						nesw[1] = currentRoom.east;
						nesw[2] = currentRoom.south;
						nesw[3] = currentRoom.west;
						changeSuccessful = false;
						break;
					}
					if (lastStraw == 4) {
						recordResponse.append(c.CREATURE_NAME).append(" drilled a hole in the ceiling and left the house");
						tempRoom.roomMap.remove(c.CREATURE_NAME.toLowerCase());
					}
				}
				//i--;
			}
		}
	}

	/**Overridden in classes animal, NPC, and PC
	 * (would it have been better to use an interface?)*/
	String preferenceSetter(){
		return "THIS SHOULD NEVER DISPLAY";
	}
	String hatePreferenceSetter(){
		return "THIS SHOULD NEVER DISPLAY";
	}
	String happyResponse() {
		return "THIS SHOULD NEVER DISPLAY";
	}
	String madResponse(){
		return "THIS SHOULD NEVER DISPLAY";
	}
	String creature_type_setter(){
		return "THIS SHOULD NEVER DISPLAY";
	}

	//allows the animal to choose a random room to go to each time they are not satisfied with the newly changed state via PC
	private Room randomRoomChange(){
		//		Random x = new Random();
		//		int i = x.nextInt(3);
		while (true) {


			Random random = new Random();

			int randomNumber = random.nextInt(this.neswIndexer + 1);

			Room selectedRoom = nesw[randomNumber];

			for (int i = randomNumber; i < this.neswIndexer; i++) {
				nesw[i] = nesw[i + 1];
			}
			nesw[this.neswIndexer] = null;
			this.neswIndexer--;
			if (selectedRoom != null) {
				return selectedRoom;
			}
			lastStraw++;
		}
	}

	private void look(Room currentRoom) {
		String roomList = "Doors to:\n\n";
		String[] directionName = {"North: ", "East: ", "South: ", "West: "};
		for (int x = 0; x < 4; x++) {
			if (currentRoom.ROOM_DIRECTION_ARRAY[x] != null) {
				roomList += directionName[x] + currentRoom.ROOM_DIRECTION_ARRAY[x] + "\n";
			}
		}


		System.out.println("room: " + currentRoom.ROOM_NAME + ", " + currentRoom.DESCRIPTION + ", " + currentRoom.state + "\n"+ roomList + "\nCreatures:");


		String creatureList = "";
		for (Creature c: currentRoom.roomMap.values()) {
			if (c.equals(Creature.this)){
				continue;
			}
			else {
				creatureList += c + "\n";
			}
		}

		System.out.println(creatureList);
	}

	public void causeLook(Room currentRoom) {
		if (PC.calledFromPC = true) {
			look(currentRoom);
			PC.calledFromPC = false;
		}
		else {System.out.println("Cannot perform this action: type HELP for commands(Still needs to be implemented)");}
	}
}