package e2_1;

public class RoundResult {
	private HandSign playerHand;
	private HandSign computerHand;
	private Judge result;

	public RoundResult(HandSign playerHand, HandSign computerHand) {
		this.playerHand = playerHand;
		this.computerHand = computerHand;
		this.result = playerHand.judge(computerHand);
	}

	public HandSign getPlayerHand() {
		return this.playerHand;
	}

	public HandSign getComputerHand() {
		return this.computerHand;
	}

	public Judge getResult() {
		return this.result;
	}
}