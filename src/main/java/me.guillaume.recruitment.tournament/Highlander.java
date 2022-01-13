package me.guillaume.recruitment.tournament;

public class Highlander extends AbstractPlayer {

	public Highlander() {
		super(150, 12);
	}

	public Highlander(String name) {
		super(150, 12);
		setName(name);
	}

	@Override
	public Highlander equip(String equipement) {
		addEquipement(equipement);
		return this;
	}

	public void addEquipement(String equipement) {
		equipements.add(equipement);
	}
}
