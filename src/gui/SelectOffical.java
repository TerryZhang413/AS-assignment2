package gui;
/**
 * @author Yanjie Zhang
 * @content after selection athlete, user select offical in this menu
 * 
 */
import java.util.ArrayList;

import Exception.NoThisOfficialException;
import Ozlympic.Driver;
import Ozlympic.Officials;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SelectOffical implements EventHandler<ActionEvent>{
	
	private static String officialID;
	Stage closeStage;
	Driver driver;
	Stage reopenMenu;
	
	//constructor to get data from last stage
	public SelectOffical(Driver driver, Stage closeStage, Stage reopenMenu) {
		this.closeStage=closeStage;
		this.driver=driver;
		this.reopenMenu=reopenMenu;
	}

	@Override
	public void handle(ActionEvent arg0) {
		closeStage.close();
		Stage selectOffical=new Stage();
		ArrayList<Officials> officals=new ArrayList<Officials>();
		officals=driver.getOfficialList();
		GridPane border = new GridPane();
		border.setPadding(new Insets(5));
		border.setHgap(5);
		border.setVgap(0);

		final Text Name = new Text("Name");
		final Text Age = new Text("Age");
		final Text State = new Text("State");
		final Text ID = new Text("ID");
		border.add(Name, 2, 0);
		border.add(Age, 3, 0);
		border.add(State, 4, 0);
		border.add(ID, 1, 0);
		
		ArrayList<RadioButton> rb = new ArrayList<RadioButton>();
		final ToggleGroup group = new ToggleGroup();

		int listSize=officals.size();
		for(int i=0;i<listSize;i++)
		{
			border.add(new Text(officals.get(i).getUserID()), 1, i+1);
			border.add(new Text(officals.get(i).getName()), 2, i+1);
			border.add(new Text(String.valueOf(officals.get(i).getAge())), 3, i+1);
			border.add(new Text(officals.get(i).getState()), 4, i+1);
			rb.add(new RadioButton());
			border.add(rb.get(i), 0, i+1);
			rb.get(i).setToggleGroup(group);
			String dd=officals.get(i).getUserID();
			//System.out.println(dd);
			rb.get(i).setUserData(dd);
		}
		
		
		group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
		      public void changed(ObservableValue<? extends Toggle> ov,
		          Toggle old_toggle, Toggle new_toggle) {
		        if (group.getSelectedToggle() != null) {  	
		        	setOfficialID(group.getSelectedToggle().getUserData().toString());
		        	//System.out.println(getOfficialID());
		        }
		      }
		    });
		
		
		Button okay=new Button("okay");
		border.add(okay, 2, 5);
		DetermineOffical deterOff=new DetermineOffical(driver,selectOffical,reopenMenu);
		okay.setOnAction(deterOff);
		
		Scene scene = new Scene(border, 400, 350);
		selectOffical.setScene(scene);
		selectOffical.show(); // Display the stage
	}

	public static String getOfficialID() {
		return officialID;
	}

	public static void setOfficialID(String ID) {
		officialID = ID;
	}

}

class DetermineOffical implements EventHandler<ActionEvent>{

	Driver driver;
	Stage closeStage;
	String officialID=null;
	Stage reopenMenu;
	
	DetermineOffical(Driver driver,Stage closeStage, Stage reopenMenu)
	{
		this.driver=driver;
		this.closeStage=closeStage;
		this.reopenMenu=reopenMenu;
	}
	
	@Override
	public void handle(ActionEvent event) {
		
		try {
			officialID=SelectOffical.getOfficialID();
			if(officialID!=null)
			{	
				driver.addOfficial(officialID);
				SelectOffical.setOfficialID(null);
				closeStage.close();	
				reopenMenu.show();
			}
			else
			{
				Stage errorWarning = new Stage();
		    	errorWarning.setTitle("Warning");
		    	Text warningText=new Text("You must firstly select an offical!!");
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
			
		} catch (NoThisOfficialException e) {
			Stage errorWarning = new Stage();
	    	errorWarning.setTitle("Warning");
	    	Text warningText=new Text(e.getMessage());
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
	
}

