//Author Name: Che Zavala
//Date: 11/14/21
//Program Name: Zavala_JavaDoc
//Purpose: Add JavaDoc to Word Occurrence app 

package application;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Pair;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.ListIterator;
import java.util.Scanner;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.layout.HBox;

public class Main extends Application implements EventHandler<ActionEvent>{
	Button button;
	TextField textField = new TextField();
	TableView<Pair<String, Integer>> tableView = new TableView<Pair<String, Integer>>();
	
	@Override
	public void start(Stage primaryStage) {
		
		primaryStage.setTitle("Word Occurence");
		button = new Button();
		button.setText("Submit");
		button.setOnAction(this);
		
		
		
		Label label1 = new Label("Enter .txt file location:");
		
		TableColumn<Pair<String, Integer>, String> column1 = new TableColumn<>("Word");
	    column1.setCellValueFactory(new PropertyValueFactory<>("key"));


	    TableColumn<Pair<String, Integer>, Integer> column2 = new TableColumn<>("Occurences");
	    column2.setCellValueFactory(new PropertyValueFactory<>("value"));
		
	    tableView.getColumns().add(column1);
	    tableView.getColumns().add(column2);

	    
		
		HBox hBox = new HBox();
		hBox.getChildren().addAll(label1, textField, button);
		hBox.setSpacing(10);
		
		VBox vBox = new VBox(8); 
	    vBox.getChildren().addAll(hBox, tableView);
		
		StackPane layout = new StackPane();
		layout.getChildren().add(vBox);
		
		Label label = new Label("Word Occurences in text:");
		Font labelFont = Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 12);
		label.setFont(labelFont);
		
		
		//***DEFAULT CODE***
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(layout,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
		// ^^^DEFAULT CODE^^^
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		launch(args);	
	}
	
	
	//Button handling
	@Override
	public void handle (ActionEvent event) {
		if(event.getSource() == button) {
	
			ArrayList <Pair<String, Integer>> wordPairArrayList = new ArrayList <Pair<String, Integer>> ();
			
			//read file
			//String fileName = "src/Macbeth.txt";
			String fileName = textField.getText();
			File file = new File(fileName);
			Scanner input = null;
			try {
				input = new Scanner(file);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//read file word by word
			while (input.hasNext()) {
			      String inputWord = input.next();
			      boolean inList = false;
			      //check list for duplicate words
			      
			      //Iterator to avoid "checkForComodification" errors
			      ListIterator<Pair<String, Integer>> iterator = wordPairArrayList.listIterator();
			      while (iterator.hasNext()) {
			    	  Pair<String, Integer> pair = iterator.next();
			    	  String pairWord = (String) pair.getKey();
			    	  //increase value of pair for word repeats
			    	  if (pairWord.equals(inputWord)) {
			    		  int tempValue = (int) pair.getValue();
			    		  tempValue++;
			    		  //Remove old pair, add updated pair with new value
			    		  iterator.remove();
			    		  iterator.add(new Pair<String, Integer>(inputWord, tempValue));
			    		  inList = true;  
			    	  }
			      }
			      //create new pair for new words
			      if (!inList) { 
			    	  wordPairArrayList.add(new Pair<String, Integer>(inputWord, 1));
			      }
			}
			
			//Sort pair list by value. 
			wordPairArrayList.sort(Comparator.<Pair<String, Integer>, Integer>comparing(Pair::getValue));
			Collections.reverse(wordPairArrayList);
			
			//Put pairs into output string list
			for (Pair<String, Integer> pair:wordPairArrayList) {
				String k = pair.getKey();
				int v = pair.getValue();
				
				tableView.getItems().add(new Pair<String, Integer>(k, v));
			}
		}
	}
}
