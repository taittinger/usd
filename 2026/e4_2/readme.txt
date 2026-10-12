3回じゃんけんゲーム（e4_2）
==========================

2026/e4_1 を参考に、RoundResult を使って3回分の結果を記録する
独立した JavaFX プロジェクトを作成しました。e4_1 は変更していません。

1. ゲームのルール
-----------------
・ROCK（グー）、SCISSORS（チョキ）、PAPER（パー）を押して手を選びます。
・ボタンを1回押すと、コンピュータがランダムに手を選び、1回分を記録します。
・あいこ（DRAW）も1回として数え、必ず3回で終了します。
・画面には現在の手と勝敗、全ラウンドの履歴、勝ち・負け・引き分けの
  回数が表示されます。WIN / LOSE は人間から見た勝敗です。
・3回終了後は手のボタンを無効にします。
・人間の勝ち数が負け数より多ければ「あなたの勝ち」、少なければ
  「コンピュータの勝ち」、同数なら「引き分け」を表示します。
・終了後に「もう一度」を押すと、履歴・成績・表示を初期化し、
  新しい3回戦を始められます。

2. JankenController.java の変更
-------------------------------
・MAX_ROUNDS = 3 でゲームの回数を定義しました。
・List<RoundResult> roundResults に各回の結果を保存します。
・playRound() で、選んだ手とコンピュータの手を渡して
      RoundResult round = new RoundResult(playerHand, computerHand);
  を作成し、リストに追加します。
・手と勝敗の表示には、RoundResult の getPlayerHand()、
  getComputerHand()、getResult() を使用します。
・updateResults() で保存済みの結果を読み、履歴と成績を更新します。
  3回目には最終結果を表示し、手のボタンを無効にします。
・3回終了後にイベントが直接呼ばれても、4回目は追加しないように
  playRound() の先頭で回数を確認します。
・resetAction() と resetGame() を追加し、「もう一度」で初期化します。

3. Janken.fxml の変更
--------------------
・元の3つの手のボタンと、人間・コンピュータ・勝敗のラベルを残しました。
・roundLabel（何回目か）、historyArea（各回の履歴）、
  scoreLabel（通算成績）、summaryLabel（最終結果）、
  resetButton（もう一度）を追加しました。
・historyArea は編集不可の TextArea です。
・画面を600×500に広げ、余白と行間を設定しました。
・FXML と CSS は Maven が標準でコピーする src/main/resources に配置しました。
  GuiJankenGame の getResource("Janken.fxml") で読み込めます。

4. 再利用したクラスとプロジェクト設定
------------------------------------
・RoundResult.java は e4_1 の実装をそのまま使用しています。
  コンストラクタが HandSign.judge() で勝敗を計算します。
・HandSign.java、Judge.java、GuiJankenGame.java、module-info.java も再利用しました。
  一部の古いコメントを除き、処理は変更していません。
・ひな形の App / PrimaryController / SecondaryController は必要ないため含めていません。
・pom.xml の artifactId を e4_2、起動クラスを com.example.GuiJankenGame にしました。
・Java 17 向けのコンパイル設定と JavaFX 17 を維持しています。
・JavaFX の空のマーカー JAR と実体の JAR が重複して選ばれる問題を避けるため、
  base / graphics / controls / fxml の4モジュールについて、
  OSに対応する実体の JAR を明示して指定しています。
  Linux、Windows、macOS および Linux/macOS の aarch64 用プロファイルがあります。
・target/ は .gitignore に登録し、ビルド生成物をソースと分けました。

5. フォルダー構成
-----------------
e4_2/
  pom.xml
  readme.txt
  .gitignore
  src/main/java/module-info.java
  src/main/java/com/example/
    GuiJankenGame.java
    JankenController.java
    HandSign.java
    Judge.java
    RoundResult.java
  src/main/resources/com/example/
    Janken.fxml
    Janken.css
  tests/
    ThreeRoundSmoke.java
    run-smoke.sh

6. ビルドと起動方法
------------------
JDK 17以上と Maven を用意し、2026/e4_2 に移動して実行します。

  mvn clean verify
  mvn javafx:run

通常の起動には、画面を表示できるデスクトップ環境が必要です。
このクラウド環境では、既に準備したツールとプロキシ設定を使えます。

  cd /workspace/usd/2026/e4_2
  source /workspace/.cloud-onboarding/usd/env.sh
  xvfb-run -a mvn -s /workspace/.cloud-onboarding/usd/maven-settings.xml \
    -Dmaven.repo.local=/workspace/.cloud-onboarding/usd/m2 javafx:run

上のコマンドは仮想ディスプレイでの起動検証用です。

7. 動作確認
-----------
Maven のビルド成功だけでなく、JavaFX の画面とコントローラを読み込んで
ボタンを操作する tests/ThreeRoundSmoke.java で検証しています。
この検証は既定の mvn verify とは別に実行します。

JDK と Maven を使用できる Bash 環境で、e4_2 から実行してください。
画面のない Linux では Xvfb が必要です。

  bash tests/run-smoke.sh

このクラウド環境での実行例：

  source /workspace/.cloud-onboarding/usd/env.sh
  bash tests/run-smoke.sh \
    -s /workspace/.cloud-onboarding/usd/maven-settings.xml \
    -Dmaven.repo.local=/workspace/.cloud-onboarding/usd/m2

確認対象は、アプリの起動、全勝、全敗、全回あいこ、勝ち・負け・あいこの
混在した引き分け、各回の RoundResult と履歴、成績、3回後の終了、
4回目の入力防止、「もう一度」による初期化です。
乱数のシードを固定することで、勝ち・負け・引き分けを確実に検証します。

このクラウド環境（Linux、JDK 21、Java 17向けコンパイル）で、
clean verify、javafx:run による画面起動、4ゲーム・計12回の
操作検証（219項目）がすべて成功しました。
