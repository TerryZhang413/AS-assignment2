package gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Warning extends Application  {

	String content;


	Warning(String content) {
		this.content=content;
	}

	@Override
	public void start(Stage errorWarning) throws Exception {
		//Stage errorWarning = new Stage();
    	errorWarning.setTitle("Warning");
    	Text warningText=new Text(content);
    	BorderPane pane=new BorderPane();
    	pane.setPadding(new Insets(10,20, 10, 20));
    	
		final ImageView warning = new ImageView(
      	      new Image("image/warning.png")
      	    );
		Button closeWindow=new Button("Ok");
		closeWindow.setOnAction((ActionEvent t)->{errorWarning.close();});
    	
    	Scene sceneWarning = new Scene(pane,350,100);
    	pane.setCenter(warningText);
    	pane.setLeft(warning);
    	pane.setBottom(closeWindow);
    	
    	errorWarning.setScene(sceneWarning); 
    	errorWarning.show();	
		
	}

}
