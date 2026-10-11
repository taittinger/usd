//package e4_1;
package com.example;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class JankenController implements Initializable{
	private Random random = new Random();
	@FXML
	private Button rockButton;
	@FXML
	private Button scissorsButton;
	@FXML
	private Button paperButton;
	
	@FXML 
	private Label computerLabel;
    @FXML 
    private Label playerLabel;
    @FXML 
    private Label judgeLabel;
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
    
	@FXML
	public void rockAction(ActionEvent event) {
		this.judge (HandSign.ROCK);
	}
	@FXML
	public void scissorsAction(ActionEvent event) {
		this.judge (HandSign.SCISSORS);
	}
	@FXML
	public void paperAction(ActionEvent event) {
		this.judge (HandSign.PAPER);
	}
	
	private void judge (HandSign playerHand) {
		int computer = this.random.nextInt(3);
		HandSign computerHand = HandSign.values()[computer];
		this.playerLabel.setText("人間：" + playerHand);
		this.computerLabel.setText("コンピュータ：" + computerHand);
		this.judgeLabel.setText("勝敗：" + playerHand.judge(computerHand));
	}

}