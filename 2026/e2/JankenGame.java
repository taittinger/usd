package e2_1;

import java.util.Random;
import java.util.Scanner;


public class JankenGame {
	
	public static void main(String[] args) {
		Random random = new Random();
		HandSign[] handsignList = HandSign.values();
		for(int i = 0; i < handsignList.length; i++) {
			System.out.println(handsignList[i] +"：" + i);
		}
		System.out.print("ジャンケンの番号を入力してください：");
		Scanner scanner = new Scanner(System.in);
		HandSign playerHand = handsignList[Integer.parseInt(scanner.next())];
		scanner.close();
		HandSign computerHand = handsignList[random.nextInt(handsignList.length)];
		System.out.println("人間：" + playerHand);
		System.out.println("計算機：" + computerHand);
		Judge judge = playerHand.judge(computerHand);
		if(judge == Judge.DRAW) {
			System.out.println("あいこ");
		} else {
			System.out.println((judge == Judge.WIN ? "人間の": "計算機の") + "勝ち");
		}
	}

}