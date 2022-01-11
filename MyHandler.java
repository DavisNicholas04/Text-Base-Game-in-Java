import org.xml.sax.helpers.*;
import org.xml.sax.Attributes;

import java.util.HashMap;

public class MyHandler extends DefaultHandler {

	private static HashMap<String, Room> rooms = new HashMap<>();
	private static String currentRoom;
	static private PC pcHolder;

	public static PC getPC(){
		return pcHolder;
	}
	static Room getRoom(String a){
		return rooms.get(a);
	}

	private void assignNeighbors(String north, String east, String south, String west){
		if (north != null && getRoom(currentRoom).north == null && rooms.get(north) != null){
			rooms.get(currentRoom).assignNorthRoom(rooms.get(north));
		}
		if (east != null && getRoom(currentRoom).east == null && rooms.get(east) != null){
			rooms.get(currentRoom).assignEastRoom(rooms.get(east));
		}
		if (south != null && getRoom(currentRoom).south == null && rooms.get(south) != null){
			rooms.get(currentRoom).assignSouthRoom(rooms.get(south));
		}
		if (west != null && getRoom(currentRoom).west == null && rooms.get(west) != null){
			rooms.get(currentRoom).assignWestRoom(rooms.get(west));
		}
	}

	@Override
	public void startDocument() {
	}

	@Override
	public void startElement(String uri, String localName, String qname, Attributes attributes) {

		String name = attributes.getValue("name");
		String description = attributes.getValue("description");
		switch(qname){
			case "room":
				String state = attributes.getValue("state");
				
				rooms.put(name ,new Room(state, name, description));
				currentRoom = name;

				String north = attributes.getValue("north");
				String east = attributes.getValue("east");
				String south = attributes.getValue("south");
				String west = attributes.getValue("west");

				assignNeighbors(north, east, south, west);

				break;

			case "animal":
				Animal animal = new Animal(name, description);
				rooms.get(currentRoom).addCreature(animal);
				break;

			case "NPC":
				NPC npc = new NPC(name, description);

				rooms.get(currentRoom).addCreature(npc);
				break;

			case "PC":
				PC pc = new PC(name, description);
				rooms.get(currentRoom).addCreature(pc);
				pcHolder = pc;
		}
	}

	@Override
	public void endElement(String uri, String localName, String qname){
	}

	@Override
	public void endDocument(){
	}
}

