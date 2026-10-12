package com.example;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class JankenController implements Initializable {
    private static final int MAX_ROUNDS = 3;
    private final Random random = new Random();
    private final List<RoundResult> roundResults = new ArrayList<>();

    @FXML private Button rockButton;
    @FXML private Button scissorsButton;
    @FXML private Button paperButton;
    @FXML private Button resetButton;
    @FXML private Label roundLabel;
    @FXML private Label computerLabel;
    @FXML private Label playerLabel;
    @FXML private Label judgeLabel;
    @FXML private Label scoreLabel;
    @FXML private Label summaryLabel;
    @FXML private TextArea historyArea;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        resetGame();
    }

    @FXML
    public void rockAction(ActionEvent event) {
        playRound(HandSign.ROCK);
    }

    @FXML
    public void scissorsAction(ActionEvent event) {
        playRound(HandSign.SCISSORS);
    }

    @FXML
    public void paperAction(ActionEvent event) {
        playRound(HandSign.PAPER);
    }

    @FXML
    public void resetAction(ActionEvent event) {
        resetGame();
    }

    private void playRound(HandSign playerHand) {
        // イベントが直接呼ばれても、4回目の結果を追加しない。
        if (roundResults.size() >= MAX_ROUNDS) {
            return;
        }

        HandSign[] hands = HandSign.values();
        HandSign computerHand = hands[random.nextInt(hands.length)];
        RoundResult round = new RoundResult(playerHand, computerHand);
        roundResults.add(round);

        playerLabel.setText("人間：" + round.getPlayerHand());
        computerLabel.setText("コンピュータ：" + round.getComputerHand());
        judgeLabel.setText("勝敗：" + round.getResult());
        updateResults();
    }

    private void updateResults() {
        int wins = 0;
        int losses = 0;
        int draws = 0;
        StringBuilder history = new StringBuilder();

        for (int i = 0; i < roundResults.size(); i++) {
            RoundResult round = roundResults.get(i);
            switch (round.getResult()) {
                case WIN: wins++; break;
                case LOSE: losses++; break;
                case DRAW: draws++; break;
            }
            if (i > 0) {
                history.append('\n');
            }
            history.append("第").append(i + 1).append("回：人間 ")
                .append(round.getPlayerHand()).append(" / コンピュータ ")
                .append(round.getComputerHand()).append(" / ")
                .append(round.getResult());
        }

        historyArea.setText(history.toString());
        scoreLabel.setText("成績：" + wins + "勝 " + losses + "敗 " + draws + "分");
        boolean finished = roundResults.size() == MAX_ROUNDS;
        setHandButtonsDisabled(finished);
        resetButton.setDisable(!finished);

        if (finished) {
            roundLabel.setText(MAX_ROUNDS + "回終了");
            String result = wins > losses ? "あなたの勝ち"
                : wins < losses ? "コンピュータの勝ち" : "引き分け";
            summaryLabel.setText("最終結果：" + result);
        } else {
            roundLabel.setText("第" + (roundResults.size() + 1) + "回 / 全" + MAX_ROUNDS + "回");
            summaryLabel.setText("あと" + (MAX_ROUNDS - roundResults.size()) + "回です。");
        }
    }

    private void resetGame() {
        roundResults.clear();
        playerLabel.setText("人間：");
        computerLabel.setText("コンピュータ：");
        judgeLabel.setText("勝敗：");
        updateResults();
        summaryLabel.setText("手を選んでください。あいこも1回に数えます。");
    }

    private void setHandButtonsDisabled(boolean disabled) {
        rockButton.setDisable(disabled);
        scissorsButton.setDisable(disabled);
        paperButton.setDisable(disabled);
    }
}
