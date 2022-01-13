package me.guillaume.recruitment.tournament;

import java.util.ArrayList;

public abstract class AbstractPlayer {
	private int hitPoints;
	private int damagePerHit;
	protected final ArrayList<String> equipements = new ArrayList<>();
	private int bucklerBlock = 0;
	private int damageReduce = 0;

	public AbstractPlayer(int hitPoints, int damagePerHit) {
		this.hitPoints = hitPoints;
		this.damagePerHit = damagePerHit;
	}

	public void engage(AbstractPlayer player2) {
		int round = 0;

		this.setDamagePerHit(this.getDamagePerHit() - player2.getDamageReduce());
		player2.setDamagePerHit(player2.getDamagePerHit() - this.getDamageReduce());

		while (this.hitPoints() > 0 && player2.hitPoints() > 0) {
			calculateDamages(this, player2, round);
			round += 1;
		}

		if (this.hitPoints() < 0) {
			this.setHitPoints(0);
		}
		if (player2.hitPoints() < 0) {
			player2.setHitPoints(0);
		}
	}

	private void calculateDamages(AbstractPlayer player1, AbstractPlayer player2, int round) {
		boolean player1AsBuckler = player1.getEquipements().contains("buckler");
		boolean player2AsBuckler = player2.getEquipements().contains("buckler");

		if (player1.getBucklerBlock() == 3 && player2 instanceof Viking) {
			player1.getEquipements().remove("buckler");
		}

		if (player2.getBucklerBlock() == 3 && player1 instanceof Viking) {
			player2.getEquipements().remove("buckler");
		}

		if (player1 instanceof Highlander && round % 3 == 0) {
			player1.setHitPoints(player1.hitPoints() - player2.getDamagePerHit());
		}

		if (player2 instanceof Highlander && round % 3 == 0) {
			player2.setHitPoints(player2.hitPoints() - player1.getDamagePerHit());
		} else {
			if (player1AsBuckler && player2AsBuckler) {
				if (round % 2 == 0) {
					player1.setHitPoints(player1.hitPoints() - player2.getDamagePerHit());
					player2.setHitPoints(player2.hitPoints() - player1.getDamagePerHit());
				} else {
					player1.setBucklerBlock(player1.getBucklerBlock() + 1);
					player2.setBucklerBlock(player2.getBucklerBlock() + 1);
				}
				return;
			}

			if (calculateWithOneBuckler(player1, player2, round, player1AsBuckler)) return;

			if (calculateWithOneBuckler(player2, player1, round, player2AsBuckler)) return;

			player1.setHitPoints(player1.hitPoints() - player2.getDamagePerHit());
			player2.setHitPoints(player2.hitPoints() - player1.getDamagePerHit());
		}

	}

	private boolean calculateWithOneBuckler(AbstractPlayer player1, AbstractPlayer player2, int round, boolean player1AsBuckler) {
		if (player1AsBuckler) {
			if (round % 2 == 0) {
				player1.setHitPoints(player1.hitPoints() - player2.getDamagePerHit());
			} else {
				player1.setBucklerBlock(player1.getBucklerBlock() + 1);
			}

			player2.setHitPoints(player2.hitPoints() - player1.getDamagePerHit());
			return true;
		}
		return false;
	}

	public int hitPoints() {
		return hitPoints;
	}

	public AbstractPlayer equip(String equipement) {
		return this;
	}

	public void setHitPoints(int hitPoints) {
		this.hitPoints = hitPoints;
	}

	public int getDamagePerHit() {
		return damagePerHit;
	}

	public void setDamagePerHit(int damagePerHit) {
		this.damagePerHit = damagePerHit;
	}

	public ArrayList<String> getEquipements() {
		return equipements;
	}

	public int getBucklerBlock() {
		return bucklerBlock;
	}

	public void setBucklerBlock(int bucklerBlock) {
		this.bucklerBlock = bucklerBlock;
	}

	public int getDamageReduce() {
		return damageReduce;
	}

	public void setDamageReduce(int damageReduce) {
		this.damageReduce = damageReduce;
	}
}
