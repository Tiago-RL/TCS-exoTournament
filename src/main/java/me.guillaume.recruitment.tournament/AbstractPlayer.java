package me.guillaume.recruitment.tournament;

public abstract class AbstractPlayer {
	private int hitPoints;
	private int damagePerHit;

	public AbstractPlayer(int hitPoints, int damagePerHit) {
		this.hitPoints = hitPoints;
		this.damagePerHit = damagePerHit;
	}

	public void engage(AbstractPlayer abstractPlayer) {
		int lifePlayer1 = this.hitPoints;
		int lifePlayer2 = abstractPlayer.hitPoints();

		while (lifePlayer1 > 0 && lifePlayer2 > 0) {
			lifePlayer1 -= abstractPlayer.getDamagePerHit();
			lifePlayer2 -= this.damagePerHit;
		}

		if (lifePlayer1 > 0){
			this.hitPoints = lifePlayer1;
			abstractPlayer.setHitPoints(0);
		} else {
			this.hitPoints = 0;
			abstractPlayer.setHitPoints(lifePlayer2);
		}
	}

	public int hitPoints() {
		return hitPoints;
	}

	public AbstractPlayer equip(String equipement) {

		return null;
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
}
