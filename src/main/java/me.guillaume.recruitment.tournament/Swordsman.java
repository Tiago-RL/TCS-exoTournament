package me.guillaume.recruitment.tournament;

public class Swordsman extends AbstractPlayer {

	public Swordsman() {
		super(100, 5);
	}

	public Swordsman(String name) {
		super(100, 5);
	}

	@Override
	public Swordsman equip(String equipement) {
		addEquipement(equipement);
		return this;
	}

	public void addEquipement(String equipement) {
		equipements.add(equipement);
	}
}
