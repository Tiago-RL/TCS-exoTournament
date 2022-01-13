package me.guillaume.recruitment.tournament;

import java.util.ArrayList;

public abstract class AbstractPlayer {
	private int hitPoints;
	private final int initialHitPoints;
	private int damagePerHit;
	protected final ArrayList<String> equipements = new ArrayList<>();
	private int bucklerBlock = 0;
	private int damageReduce = 0;
	private String name = "";
	private boolean berserkModOn = false;

	public AbstractPlayer(int hitPoints, int damagePerHit) {
		this.hitPoints = hitPoints;
		this.initialHitPoints = hitPoints;
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

		checkBuckler(player1, player2);

		checkViciousPlayer(player1, player2, round);

		checkBerserkMod(player1, player2);

		if (checkHighlanderMiss(player1, player2, round)) return;

		calculateDamageForTwoPlayers(player1, player2, round, player1AsBuckler, player2AsBuckler);


	}

	private void checkBerserkMod(AbstractPlayer player1, AbstractPlayer player2) {
		if (player1.isBerserkModOn() && player1.getName().equals("Veteran") && (player1.getInitialHitPoints() * 0.3) >= player1.hitPoints()) {
			player1.setDamagePerHit(player1.getDamagePerHit() * 2 + player2.getDamageReduce());
			player1.setBerserkModOn(true);
		}

		if (player2.isBerserkModOn() && player2.getName().equals("Veteran") && (player2.getInitialHitPoints() * 0.3) >= player2.hitPoints()) {
			player2.setDamagePerHit(player2.getDamagePerHit() * 2 + player1.getDamageReduce());
			player2.setBerserkModOn(true);
		}
	}

	private void checkViciousPlayer(AbstractPlayer player1, AbstractPlayer player2, int round) {
		if (player1.getName().equals("Vicious") && round < 3) {
			player2.setHitPoints(player2.hitPoints() - 20);
		}

		if (player2.getName().equals("Vicious") && round < 3) {
			player1.setHitPoints(player1.hitPoints() - 20);
		}
	}

	private boolean checkHighlanderMiss(AbstractPlayer player1, AbstractPlayer player2, int round) {
		if (player1 instanceof Highlander && round % 3 == 0) {
			player1.setHitPoints(player1.hitPoints() - player2.getDamagePerHit());
			return true;
		}

		if (player2 instanceof Highlander && round % 3 == 0) {
			player2.setHitPoints(player2.hitPoints() - player1.getDamagePerHit());
			return true;
		}
		return false;
	}

	private void calculateDamageForTwoPlayers(AbstractPlayer player1, AbstractPlayer player2, int round, boolean player1AsBuckler, boolean player2AsBuckler) {
		if (player1AsBuckler && player2AsBuckler) {
			if (round % 2 == 1) {
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

	private void checkBuckler(AbstractPlayer player1, AbstractPlayer player2) {
		if (player1.getBucklerBlock() == 3 && player2 instanceof Viking) {
			player1.getEquipements().remove("buckler");
		}

		if (player2.getBucklerBlock() == 3 && player1 instanceof Viking) {
			player2.getEquipements().remove("buckler");
		}
	}

	private boolean calculateWithOneBuckler(AbstractPlayer player1, AbstractPlayer player2, int round, boolean player1AsBuckler) {
		if (player1AsBuckler) {
			if (round % 2 == 1) {
				player1.setHitPoints(player1.hitPoints() - player2.getDamagePerHit());
			} else {
				player1.setBucklerBlock(player1.getBucklerBlock() + 1);
			}

			if (player1.hitPoints() > 0) {
				player2.setHitPoints(player2.hitPoints() - player1.getDamagePerHit());
			}
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getInitialHitPoints() {
		return initialHitPoints;
	}

	public boolean isBerserkModOn() {
		return !berserkModOn;
	}

	public void setBerserkModOn(boolean berserkModOn) {
		this.berserkModOn = berserkModOn;
	}
}
