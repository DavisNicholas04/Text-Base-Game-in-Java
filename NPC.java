// subclass of Creature that overrides preferences and responses for the creature

class NPC extends Creature{

	public NPC(String NPCName, String description){
		super(NPCName, description);
	}

	@Override final String preferenceSetter(){
		return "dirty";
	}
	@Override final String hatePreferenceSetter(){
		return "clean";
	}
	@Override final String happyResponse() {
		return "smiles at you";
	}
	@Override final String madResponse(){
		return "grumbles at you";
	}
	@Override String creature_type_setter(){
		return "NPC";
	}

}