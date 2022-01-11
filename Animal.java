// subclass of Creature that overrides preferences and responses for the creature

class Animal extends Creature {

	public Animal(String animalName, String description){
		super(animalName, description);
	}
	
	@Override final String preferenceSetter(){
		return "clean";
	}
	@Override final String hatePreferenceSetter(){
		return "dirty";
	}
	@Override final String happyResponse() {
		return "Licks your face";
	}
	@Override final String madResponse(){
		return "growls at you";
	}
	@Override String creature_type_setter(){
		return "Animal";
	}
}
