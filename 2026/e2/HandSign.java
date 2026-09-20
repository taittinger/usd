package e2_1;

public enum HandSign {
	ROCK {
		@Override
		public Judge judge(HandSign other) {
			return (this == other) ? Judge.DRAW: (other == SCISSORS ? Judge.WIN: Judge.LOSE);
		}
	}, 
	SCISSORS {
		@Override
		public Judge judge(HandSign other) {
			return (this == other) ? Judge.DRAW: (other == PAPER ? Judge.WIN: Judge.LOSE);
		}
	}, 
	PAPER {
		@Override
		public Judge judge(HandSign other) {
			return (this == other) ? Judge.DRAW: (other == ROCK ? Judge.WIN: Judge.LOSE);
		}
	};
	
	abstract public Judge judge(HandSign other);
}
