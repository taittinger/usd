import com.example.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Random;

public class ThreeRoundSmoke extends Application {
    private static int checks;
    private static void check(boolean ok, String message) {
        checks++;
        if (!ok) throw new AssertionError(message);
    }
    private static Label label(Scene scene, String id) { return (Label) scene.lookup("#" + id); }
    private static Button button(Scene scene, String id) { return (Button) scene.lookup("#" + id); }
    @Override public void start(Stage stage) {
        try {
            // Exercise the actual application entry point and its resource lookup.
            new GuiJankenGame().start(stage);
            stage.getScene().getRoot().applyCss();
            check(stage.isShowing() && stage.getTitle().equals("Janken"), "Application startup");
            check(label(stage.getScene(), "roundLabel").getText().equals("第1回 / 全3回"), "Application initial screen");

            FXMLLoader loader = new FXMLLoader(GuiJankenGame.class.getResource("Janken.fxml"));
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            scene.getRoot().applyCss();
            JankenController controller = loader.getController();
            Field randomField = JankenController.class.getDeclaredField("random");
            randomField.setAccessible(true);
            Random random = (Random) randomField.get(controller);
            Field resultsField = JankenController.class.getDeclaredField("roundResults");
            resultsField.setAccessible(true);
            @SuppressWarnings("unchecked") List<RoundResult> results = (List<RoundResult>) resultsField.get(controller);
            TextArea history = (TextArea) scene.lookup("#historyArea");
            String[] buttons = {"rockButton", "scissorsButton", "paperButton"};
            HandSign[] hands = HandSign.values();
            // Exercise winning, losing, all-draw and mixed tied games.
            int[][] offsets = {{2,2,2}, {1,1,1}, {0,0,0}, {2,1,0}};
            String[] summaries = {"あなたの勝ち", "コンピュータの勝ち", "引き分け", "引き分け"};
            for (int game = 0; game < offsets.length; game++) {
                check(results.isEmpty() && history.getText().isEmpty(), "Empty initial/restarted history");
                check(label(scene,"scoreLabel").getText().equals("成績：0勝 0敗 0分"), "Initial score");
                check(label(scene,"roundLabel").getText().equals("第1回 / 全3回"), "Initial round");
                check(label(scene,"playerLabel").getText().equals("人間："), "Reset player label");
                check(label(scene,"computerLabel").getText().equals("コンピュータ："), "Reset computer label");
                check(label(scene,"judgeLabel").getText().equals("勝敗："), "Reset result label");
                long seed = 1234 + game;
                random.setSeed(seed);
                Random prediction = new Random(seed);
                int wins=0, losses=0, draws=0;
                for (int round=0; round<3; round++) {
                    for (String id:buttons) check(!button(scene,id).isDisabled(), "Hand enabled before round");
                    check(button(scene,"resetButton").isDisabled(), "Restart disabled until complete");
                    HandSign computer=hands[prediction.nextInt(3)];
                    HandSign player=hands[(computer.ordinal()+offsets[game][round])%3];
                    Judge expected=offsets[game][round]==2?Judge.WIN:offsets[game][round]==1?Judge.LOSE:Judge.DRAW;
                    button(scene,buttons[player.ordinal()]).fire();
                    check(results.size()==round+1, "Exactly one RoundResult per action");
                    RoundResult record=results.get(round);
                    check(record.getPlayerHand()==player && record.getComputerHand()==computer && record.getResult()==expected, "RoundResult contents");
                    check(label(scene,"playerLabel").getText().equals("人間："+player), "Player display");
                    check(label(scene,"computerLabel").getText().equals("コンピュータ："+computer), "Computer display");
                    check(label(scene,"judgeLabel").getText().equals("勝敗："+expected), "Result display");
                    String[] lines=history.getText().split("\n");
                    check(lines.length==round+1, "History row count");
                    for(int i=0;i<=round;i++) {
                        RoundResult previous=results.get(i);
                        check(lines[i].equals("第"+(i+1)+"回：人間 "+previous.getPlayerHand()+" / コンピュータ "+previous.getComputerHand()+" / "+previous.getResult()), "Retained history");
                    }
                    switch(expected) { case WIN:wins++;break; case LOSE:losses++;break; case DRAW:draws++;break; }
                    check(label(scene,"scoreLabel").getText().equals("成績："+wins+"勝 "+losses+"敗 "+draws+"分"), "Score totals");
                    if(round<2) check(label(scene,"roundLabel").getText().equals("第"+(round+2)+"回 / 全3回"), "Next round");
                }
                for(String id:buttons) check(button(scene,id).isDisabled(), "Hand disabled after third round");
                check(!button(scene,"resetButton").isDisabled(), "Restart enabled after third round");
                check(label(scene,"roundLabel").getText().equals("3回終了"), "Game completed");
                check(label(scene,"summaryLabel").getText().equals("最終結果："+summaries[game]), "Final summary");
                String finalHistory=history.getText();
                button(scene,"rockButton").fire();
                controller.rockAction(null); // Also verify the guard against direct extra events.
                check(results.size()==3 && history.getText().equals(finalHistory), "Fourth round rejected");
                button(scene,"resetButton").fire();
            }
            check(results.isEmpty() && history.getText().isEmpty(), "Final restart cleared results");
            System.out.println("PASS: " + checks + " assertions; 4 games / 12 rounds; win, loss, all draws, mixed tie, fourth-round prevention, restart");
            stage.close();
            Platform.exit();
        } catch(Throwable failure) { failure.printStackTrace(); System.exit(1); }
    }
    public static void main(String[] args) { launch(args); }
}
