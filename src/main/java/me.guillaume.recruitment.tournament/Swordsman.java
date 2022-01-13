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
		if (equipement.equals("armor")){
			setDamageReduce(3);
			setDamagePerHit(getDamagePerHit()-1);
		}

		equipements.add(equipement);
	}
}
