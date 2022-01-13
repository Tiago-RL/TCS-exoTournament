package me.guillaume.recruitment.tournament;

public class Viking extends AbstractPlayer {

	public Viking() {
		super(120, 6);
	}

	@Override
	public Viking equip(String equipement) {
		addEquipement(equipement);
		return this;
	}

	public void addEquipement(String equipement) {
		equipements.add(equipement);
	}
}
