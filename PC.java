//The following class allows the user to interact with the programs methods in Creatures.

import java.util.Scanner;

class PC extends Creature{

	static int respect;
	static int youLose;
	static int youWin;
	boolean continueSettings = false;
	final static private int LIMIT = 1;
	private static int x = 0;
	static boolean calledFromPC = false;
	private static final String seperater = "\n-------------------------------------------------";
	private final String helpMessage = "Commands can only contain one or two words. Two words"
		+ " are split by \":\" (without the quotes). White spaces of any kind are forbidden.\n"
		+ "One-word commands apply to the PC, two-word commands apply to other creatures\n"
		+ "To move the PC, type (without the quotes) \"[direction]\" where [direction] is N, n, E, e, S, s,"
		+ " W, or w. If the PC cannot move into the selected direction (e.g. because there is no room in"
		+ " this direction or because the room there is full), a corresponding error message will be "
		+ "given and the PC will not move.\n"
		+ "To move a creature, type (without the quotes) \"[Creature name]:[direction]\". If the creature "
		+ "cannot go there (for the same reasons as the PC), a corresponding message will be given, and "
		+ "the creature will react correspondingly (e.g., and NPC will grumble if it is asked to move to"
		+ "a full room, etc. See project description.)\n"
		+ "To make the PC clean or dirty the current room, type \"clean\" or \"dirty\".\n"
		+ "To make the PC look around the current room, type \"look\".\n"
		+ "To make a creature clean or dirty the current room, type \"[Creature name]:clean\" or \""
		+ "[Creature name]:dirty\".\n"
		+ "To make a creature that is in the current room look around the room, type \"[Creature name]:"
		+" look\". Error, if creature is not in the room.\n"
		+ "To see this menu, type \"Help\" (not case-sensitive)\n"
		+ "To exit the game, type \"Exit\" (not case-sensitive)";

	public PC(String PCName, String description){
		super(PCName, description);
		x++;
		onlyOneInstance();
	}


	//Ensures the User can only make one instance of this class
	private void onlyOneInstance() {
		if (x > LIMIT){
			System.out.print("Im sorry, the program has quit because you tried to make more than one PC");
			System.exit(-1);
		}
	}
	public void changeRoomCommand(Creature name, String direction) {
		if (name.currentRoom == PC.this.currentRoom) {
			calledFromPC = true;
			name.causeRoomChange(direction);
		}
		else {
			System.out.println("eesh, sorry you cannot do that");
		}

		System.out.println("After completing that command the PCs current respect is " + respect + seperater);
	}

	public void changeRoomCommand(String direction) {
			calledFromPC = true;
			PC.this.causeRoomChange(direction);
		System.out.println("After completing that command the PCs current respect is " + respect + seperater);
	}

	public void changeStateCommand(Creature name, String newState) {
		if (name.currentRoom == PC.this.currentRoom) {
			calledFromPC = true;
			name.causeStateChange(newState);
		}
		else {
			System.out.println("eesh, sorry you cannot do that");
		}
		System.out.println("After completing that command the PCs current rsspect is " + respect + seperater);
	}

	public void changeStateCommand(String newState) {
		calledFromPC = true;
		PC.this.causeStateChange(newState);
		System.out.println("After completing that command the PCs current respect is " + respect + seperater);
	}

	public void lookCommand(Creature name) {
		if (name.currentRoom == PC.this.currentRoom) {
			calledFromPC = true;
			name.causeLook(currentRoom);
		}
		else {
			System.out.println("eesh, sorry you cannot do that");}
		System.out.println("After completing that command the PCs current respect is " + respect + seperater);
	}

	public void lookCommand() {
		calledFromPC = true;
		PC.this.causeLook(currentRoom);
		System.out.println("After completing that command the PCs current respect is " + respect + seperater);
	}
	@Override String creature_type_setter(){
		return "PC";
	}

	public void help() {
		System.out.println(helpMessage);
	}

//	public Creature getCreature(String CreatureName){
//		int low = -1;
//		int high = MyHandler.getPC().currentRoom.roomMap.size() + 1;
//		int mid;
//		LinkedList roomList = MyHandler.getPC().currentRoom.roomList;
//		while (low + 1 != high){
//			mid = (low + high)/2;
//			int comparer = ((Creature) roomList.get(mid)).CREATURE_NAME.toLowerCase().compareTo(CreatureName.toLowerCase());
//			if (comparer == 0) {
//				return ((Creature)roomList.get(mid));
//			} else if (comparer > 0){
//				high = mid;
//			} else {
//				low = mid;
//			}
//		}
//		return null;
//	}

	private void callCommand(int commandAndNameLength, String input, String creatureName, Creature creature){
		boolean commandArgument = false;

		if (commandAndNameLength == 1){
			commandCheck(input, creature, commandArgument);
		} else {
			commandArgument = true;
			creature = currentRoom.roomMap.get(creatureName.toLowerCase());
			if (creature != null){
				commandCheck(input, creature, commandArgument);
			} else {System.out.println("I\'m sorry but " + creatureName + " is not in this room");}
		}		

	}

	private void commandCheck(String input, Creature creature, boolean commandArgument){
		switch (input.toLowerCase()) {
			case "look":
				if (!commandArgument){
					lookCommand();
				} else {
					lookCommand(creature);
				}
				break;

			case "n":
				if (!commandArgument){
					changeRoomCommand("north");
				} else {
					changeRoomCommand(creature, "north");
				}
				break;	

			case "e":
				if (!commandArgument){
					changeRoomCommand("east");
				} else {
					changeRoomCommand(creature, "east");
				}
				break;	

			case "s":
				if (!commandArgument){
					changeRoomCommand("south");
				} else {
					changeRoomCommand(creature, "south");
				}
				break;	

			case "w":
				if (!commandArgument){
					changeRoomCommand("west");
				} else {
					changeRoomCommand(creature, "west");
				}
				break;

			case "dirty": 
				if (!commandArgument){
					changeStateCommand("dirty");
				} else {
					changeStateCommand(creature, "dirty");
				}
				break;	

			case "clean": 
				if (!commandArgument){
					changeStateCommand("clean");
				} else {
					changeStateCommand(creature, "clean");
				}
				break;	
		}
	}

	private void setValues(int value, String respectString, Scanner scanner){
		this.continueSettings = false;
		String inWhichValue = "";

		setValuesLoop: while (true) {
			switch (value){
				case 40:
					System.out.println("\u001B[36m" + "set your starting respect value(must be greater than 0):" + "\u001B[0m");
					inWhichValue = "respect";
					break;

				case 80:
					System.out.println("\u001B[36m" + "set your winning respect value(must be larger than starting value):" + "\u001B[0m");
					inWhichValue = "win";
					break;

				case 0:
					System.out.println("\u001B[36m" + "set your losing respect value(must be 0 or greater but less than starting value):" + "\u001B[0m");
					inWhichValue = "lose";
					break;
				default: 
					System.out.println("\u001B[91m" + "ERROR IN SETTING THE VALUES OF CUSTOM GAME" + "\u001B[0m");
					System.exit(-1);
			}

			System.out.print("\u001B[33m");
			respectString = scanner.nextLine();
			System.out.print("\u001B[0m");

			System.out.println();
			if ("back".equalsIgnoreCase(respectString)){
				this.continueSettings = true;
				break;
			}
			else if ("exit".equalsIgnoreCase(respectString)){
				System.exit(0);
			}
			else if (!"exit".equalsIgnoreCase(respectString) || !"back".equalsIgnoreCase(respectString)){
				try{
					Integer i = Integer.valueOf(respectString);
					value = i;
				}
				catch (NumberFormatException e){
					System.out.println("that is not an option.");
					continue;
				}
			}

			switch (inWhichValue){
				case "respect":
					if (value < 1){
						System.out.println("Please choose a value that is larger than 0." + "\u001B[0m");
						value = 40;
						continue setValuesLoop;
					}
					this.respect = value;
					break;
				case "win":
					if (respect >= value){
						System.out.println("Please choose a value that is larger than your start value.");
						value = 80;
						continue setValuesLoop;
					}
					this.youWin = value;
					break;
				case "lose":
					if (respect <= value){
						System.out.println("Please choose a value that is smaller than your start value.");
						value = 0;
						continue setValuesLoop;
					}
					else if (value < 0){
						System.out.println("Please choose a value that 0 or greater.");
						value = 0;
						continue setValuesLoop;
					}
					this.youLose = value;
			}
			
			break;
		}
	}

	public void play(Scanner scanner){

		System.out.println(helpMessage);
		String input;
		String[] commandAndName;
		String creatureName = "";
		int autoQuit = 0;
		boolean normalPlay = true;
		Creature creature = null;


		settings: while(true){
			respect = 40;
			youLose = 0;
			youWin = 80;
			System.out.println("\nAre you playing a normal game or a custom game?\n" 
				+ "\u001B[36m" + "Click" + "\u001B[96m" +  " ENTER " + "\u001B[36m"
				 + "to start a normal game. Type" + "\u001B[96m" +  " custom " + "\u001B[36m" + "to start a custom game. Type" + "\u001B[96m" + " exit " + "\u001B[36m" + "to exit.");

			System.out.print("\u001B[33m");
			input = scanner.nextLine();
			System.out.print("\u001B[0m");

			if ("custom".equalsIgnoreCase(input)) {
				String respectString = "";
				System.out.println("\nYou have entered custom settings mode, type" + "\u001B[96m" + " Exit" + "\u001B[0m" + " to exit at any time or type " + "\u001B[96m" + "back" + "\u001B[0m" + " to go back and re-select your game mode at any time.");

				setValues(respect, respectString, scanner);
				if (continueSettings) {
					continue settings;
				}
				setValues(youWin, respectString, scanner);
				if (continueSettings) {
					continue settings;
				}
				setValues(youLose, respectString, scanner);
				if (continueSettings) {
					continue settings;
				}
			}
			else if ("exit".equalsIgnoreCase(input)){
				System.out.println("Goodbye.");
				System.exit(0);
			}
			else if (!("".equalsIgnoreCase(input))){
				System.out.println("That is not an option, try again.");
				continue;
			}

			break;
		}

		while (true) {
			//checks if player won
			if (this.respect >= youWin && normalPlay){
				System.out.println("Wow you won! I had a lot of fun, let\'s play again sometime. \n\n"
					+ "By the way, if you want to continue in free play mode and see how many points you can get let me know."
					+ "\u001B[36m" + "\nContinue? (yes/no)");
				while (true){
					System.out.print("\u001B[33m");
					input = scanner.nextLine();	
					System.out.print("\u001B[36m");
					if ("no".equalsIgnoreCase(input)){
						System.out.print("\u001B[0m" + "Goodbye.");
						System.exit(0);
					} else if ("yes".equalsIgnoreCase(input)){
						System.out.println("\u001B[0m" + "Continuing in free play mode.");
						normalPlay = false;
						break;
					} else {
						System.out.println("Command not accepted, Try again\nContinue? (yes/no)");
					}
					autoQuit++;
					if (autoQuit == 3){
						System.out.println("\u001B[0m" + "you have entered the wrong command 3 times, autoQuit will now take place."
							+ "\n Goodbye.");
						System.exit(0);
					}
				}
			} else if (respect <= youLose){
				System.out.println("YOU LOSE...horribly. But I did have fun, so let\'s play again sometime.\nGoodbye.");
				System.exit(0);
			}

			System.out.println("\u001B[36m" + "Type new command:");
			System.out.print("\u001B[33m");
			input = scanner.nextLine();
			System.out.print("\u001B[0m");

			System.out.println("   |\n   V");
			commandAndName = input.split(":");

			if (commandAndName.length == 1){
				input = commandAndName[0];
			} else if (commandAndName.length == 2){
				input = commandAndName[1];
				creatureName = commandAndName[0];
			} else if (commandAndName.length > 1) {
				System.out.println("I'm sorry but you entered two colens and this program does not accept mutliple commands. Try again.");
				continue;
			}

			switch (input.toLowerCase()) {
				case "look":
					callCommand(commandAndName.length, input, creatureName, creature);
					break;

				case "n":
					callCommand(commandAndName.length, input, creatureName, creature);
					break;	

				case "e":
					callCommand(commandAndName.length, input, creatureName, creature);
					break;	

				case "s":
					callCommand(commandAndName.length, input, creatureName, creature);
					break;	

				case "w":
					callCommand(commandAndName.length, input, creatureName, creature);
					break;

				case "dirty": 
					callCommand(commandAndName.length, input, creatureName, creature);
					break;	

				case "clean": 
					callCommand(commandAndName.length, input, creatureName, creature);
					break;	

				case "help":
					help();
					break;

				case "exit":
					System.out.println("\u001B[91m" + "You entered exit, are you sure you want to leave? Your progress will not be saved. (Yes/No)" + "\u001B[0m");
					System.out.print("\u001B[33m");
					input = scanner.nextLine();
					System.out.print("\u001B[0m");

					if ("yes".equalsIgnoreCase(input)){
						System.out.println("Ok, bye.");
						System.exit(0);
					}	
					else if ("no".equalsIgnoreCase(input)){
						System.out.println("\u001B[36m" + "Then lets keep having fun. Type \"help\" if you need to"
							+ " be reminded of the commands." + "\u001B[0m");
						break;
					}
					else {
						System.out.println("\u001B[36m" + "I\'ll take that as a no. Type \"help\" if you need to"
							+ " be reminded of the commands." + "\u001B[0m");
						break;
					}				

				default: System.out.println(input + " is not a command");
			}
		}
	}
}