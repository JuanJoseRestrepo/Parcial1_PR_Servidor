package view;

import control.GameController;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ChadWindow extends Stage{
	
	private Scene scene;

	private TextArea messageArea;
	
	private GameController control;
	
	public ChadWindow() {
		

		messageArea = new TextArea();
		
		VBox vbox = new VBox();
		vbox.getChildren().add(messageArea);

		scene = new Scene(vbox,400,400);
		this.setScene(scene);
		
		control = new GameController(this);
	}


	public TextArea getMessageArea() {
		return messageArea;
	}

	public void setMessageArea(TextArea messageArea) {
		this.messageArea = messageArea;
	}
	
	

	
	

}
