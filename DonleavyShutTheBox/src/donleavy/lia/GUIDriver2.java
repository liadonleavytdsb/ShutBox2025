package donleavy.lia;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/***
 * GUIDriver2 is the class that runs the visual version of the program.
 * 
 * The shut the box game is a game for 2 players, where dice/die is rolled and the goal is to knock down every tile.
 * Scores are calculated by adding the tiles that remain open at the end of a turn.
 * Lowest score over three rounds wins the game!!
 */
public class GUIDriver2 extends Application {

	Die d1 = new Die(6);
	Die d2 = new Die(6);
	int lastRoll = 0;
	int p1Score = 0;
	int p2Score = 0;
	int currScore = 0;
	int currRound = 1;
	int currPlayer = 1;
	boolean twoDie = true;
	Button btnRoll = new Button("Roll 2 Die");
	Button btnLockIn = new Button ("Lock In Tiles");
	Button btnNext = new Button("Next Player");
	Button btnQuit = new Button("Quit");
	
	/***
	 * Sets up the JavaFx stage-- like buttons, labels, event handlers, etc.
	 *
	 *@param stage - The game visuals for this program
	 */
	@Override
	public void start(Stage stage) throws Exception {
		VBox root = new VBox(10);
		root.setAlignment(Pos.CENTER);
		
		// Create the buttons for the tiles
		Button[] tileBtns = new Button[9];
		for (int i = 0; i < tileBtns.length; i++) {
			 Button button = new Button (String.valueOf(i + 1));
			 button.setStyle("-fx-background-color: #FFFFFF;");
			 tileBtns[i] = button;
		}
		
		// Labels
		Label title = new Label("Shut The Box Game");
		Font bigFont = Font.font("Times New Roman", FontWeight.EXTRA_BOLD, 30);
		Font TNR = new Font("Times New Roman", 15);
		Font TNRbig = new Font("Times New Roman", 22);
		title.setFont(bigFont);
		Label dieResult = new Label("");
		dieResult.setFont(TNR);
		Label message = new Label("");
		message.setFont(TNR);
		Label round = new Label("Round #" + currRound + " - Player " + currPlayer + "'s Turn");
		Label p1S = new Label("Player 1 Score: " + p1Score);
		Label p2S = new Label("Player 2 Score: " + p2Score);
		round.setFont(TNR);
		p1S.setFont(TNR);
		p2S.setFont(TNR);
		Label s = new Label(" || ");
		
		// Create the tiles
		Tile[] tiles = new Tile[9];
		for (int i = 0; i < tiles.length; i++) {
			tiles[i] = new Tile(i + 1);
		}
		
		// Tile Buttons Box
		HBox tileBox = new HBox(10);
		for (Button b : tileBtns) {
			tileBox.getChildren().add(b);
			
			// Add event handler for tile buttons
			b.setOnAction(e -> {
				int number = Integer.valueOf(b.getText());
				Tile selectedTile = tiles[number - 1];
				if (!selectedTile.isDown()) {
					if (!selectedTile.isSelected()) {
						// Sets the color for selected Tiles
						b.setStyle("-fx-background-color: #DAF7A6;");
						selectedTile.select();
					}
					else {
						b.setStyle("-fx-background-color: #FFFFFF;");
						selectedTile.deselect();
					}
				}
			});
		} 
		
		// Roll 2 Dice Button
		btnRoll.setOnAction(e-> {
			if (twoDie) {
				lastRoll = d1.roll() + d2.roll();
			}
			else {
				lastRoll = d1.roll();
			}
			dieResult.setText("Die Roll Result: " + lastRoll);
			btnRoll.setDisable(true);
			message.setText("");
		});
		
		// Lock-In Button
		btnLockIn.setOnAction(e-> {
			// Determine the sum of the selected tiles.
			int sum = 0;
			for (Tile t:tiles) {
				if (t.isSelected()) {
					sum += t.getValue();
				}
			}
			
			// If the sum of the selected tile value(s) equals the last die roll, lock-in the tiles.
			if (sum == lastRoll) {
				// Disable the buttons that are selected and change color.
				for (int i=0; i<tiles.length; i++) {
					if (tiles[i].isSelected()) {
						tiles[i].deselect();
						tiles[i].shut();
						tileBtns[i].setStyle("-fx-background-color: darkgrey");
						tileBtns[i].setDisable(true);
					}
				}
				// Re-enable the roll dice button.
				dieResult.setText("");
				message.setText("");
				btnRoll.setDisable(false);
			}
			else {
				// Deselect all tiles if the move is invalid.
				for (int i=0; i<tiles.length; i++) {
					if (tiles[i].isSelected()) {
						tiles[i].deselect();
						tileBtns[i].setStyle("-fx-background-color: #FFFFFF;");
					}
				}
				message.setText("Invalid Move");
			}
			
			// If tiles 7, 8, and 9 are down, then switch to rolling one die only.
			if (tiles[6].isDown() && tiles[7].isDown() && tiles[8].isDown()) {
				twoDie = false;
				btnRoll.setText("Roll Die");
			}
			
		});
		
		// Next Player Button
			btnNext.setVisible(false);
			btnNext.setOnAction(e-> {
				// Reset the board for next player's turn.
				btnRoll.setDisable(false);
				btnLockIn.setDisable(false);
				btnQuit.setDisable(false);
				
				for (int i=0; i < tiles.length; i++) {
					tiles[i].deselect();
					tiles[i].open();
					tileBtns[i].setDisable(false);
					tileBtns[i].setStyle("-fx-background-color: #FFFFFF");
				}
				lastRoll = 0;
				currScore = 0;
				dieResult.setText("");
				message.setText("");
				btnRoll.setDisable(false);
				btnNext.setVisible(false);
				round.setText("Round #" + currRound + " - Player " + currPlayer + "'s Turn");
				
				// If the game has ended, calculate total scores and display end screen.
				if (currRound > 3) {
					String result = "";
					if (p1Score < p2Score) {
						result = "Player 1 Wins!!";
					}
					else if (p1Score > p2Score) {
						result = "Player 2 Wins!!";
					}
					else {
						result = "It's A Tie!!";
					}
					
					// Game Over Screen Code
					VBox endScreen = new VBox(20);
					endScreen.setAlignment(Pos.CENTER);
					
					Label end1 = new Label("🎉 Game Over 🎉");
					end1.setFont(bigFont);
					Label end2 = new Label(result);
					end2.setFont(TNR);
					Label end3 = new Label("\nFinal Scores:");
					end3.setFont(TNRbig);
					Label end4 = new Label("Player 1 Score: " + p1Score + "\nPlayer 2 Score: " + p2Score);
					end4.setFont(TNR);
					
					endScreen.getChildren().addAll(end1, end2, end3, end4);
					Scene gameOver = new Scene(endScreen, 500, 500);
					stage.setScene(gameOver);
					stage.show();
				}
				twoDie = true;
				btnRoll.setText("Roll 2 Die");
			});
		
		// Quit Button
		btnQuit.setOnAction(e-> {
			// Calculates the current player's score based on the total of the unshut tiles.
			for (Tile t:tiles) {
				if (!t.isDown()) {
					currScore += t.getValue();
				}
			}
			
			// Assigns score to player
			if (currPlayer == 1) {
				p1Score += currScore;
				currPlayer = 2;
				message.setText("Player 1 Turn Ends - Score: " + p1Score + ".");
				p1S.setText("Player 1 Score: " + p1Score);
			}
			else {
				p2Score += currScore;
				currPlayer = 1;
				message.setText("End of Player 2's Turn - Score: " + p2Score + ".");
				p2S.setText("Player 2 Score: " + p2Score);
				currRound++;
			}
			btnRoll.setDisable(true);
			btnLockIn.setDisable(true);
			btnQuit.setDisable(true);
			btnNext.setVisible(true);
		});
		
		
		HBox btnBox = new HBox(20);
		HBox playerBox = new HBox(5);
		playerBox.getChildren().addAll(p1S, s, p2S);
		playerBox.setAlignment(Pos.TOP_CENTER);
		btnBox.setAlignment(Pos.CENTER);
		btnBox.getChildren().addAll(btnRoll, btnLockIn, btnQuit);
		tileBox.setAlignment(Pos.CENTER);
		root.getChildren().addAll(round, playerBox, title, tileBox);
		root.getChildren().addAll(btnBox, dieResult, message, btnNext);
		
		Scene scene = new Scene(root, 500, 500);
		stage.setScene(scene);	
		stage.show();
	}

	public static void main(String[] args) {
		launch();
	}
}
