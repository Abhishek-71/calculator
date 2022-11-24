package application;
	
import java.io.File;
import java.io.FileInputStream;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;


public class Main extends Application {
	private TextField  textField = new TextField();
	private boolean start = true;
	
	@Override
	public void start(Stage primaryStage) throws Exception{
		textField.setPrefHeight(50);
		textField.setFont(Font.font(20));
		textField.setEditable(false);
		textField.setAlignment(Pos.CENTER_RIGHT);
		
		StackPane stackPane = new StackPane();
		stackPane.setPadding(new Insets(10));
		stackPane.getChildren().add(textField);
		
		FlowPane flowPane = new FlowPane();
		flowPane.setHgap(10);
		flowPane.setVgap(5);
		flowPane.setAlignment(Pos.TOP_CENTER);
		
		flowPane.getChildren().add(createButtonForClear());
		flowPane.getChildren().add(createButtonForBack());
		
		flowPane.getChildren().add(createButtonForNumber("7"));
		flowPane.getChildren().add(createButtonForNumber("8"));
		flowPane.getChildren().add(createButtonForNumber("9"));
		flowPane.getChildren().add(createButtonForOperator("/"));
		
		flowPane.getChildren().add(createButtonForNumber("4"));
		flowPane.getChildren().add(createButtonForNumber("5"));
		flowPane.getChildren().add(createButtonForNumber("6"));
		flowPane.getChildren().add(createButtonForOperator("*"));
		
		flowPane.getChildren().add(createButtonForNumber("1"));
		flowPane.getChildren().add(createButtonForNumber("2"));
		flowPane.getChildren().add(createButtonForNumber("3"));
		flowPane.getChildren().add(createButtonForOperator("-"));
		
		flowPane.getChildren().add(createButtonForNumber("0"));
		flowPane.getChildren().add(createButtonForNumber("."));
		flowPane.getChildren().add(createButtonForOperator("="));
		flowPane.getChildren().add(createButtonForOperator("+"));
		
		
		Image im = new Image("file:calculator.png");
		BorderPane root = new BorderPane();
		root.setTop(stackPane);
		root.setCenter(flowPane);
		primaryStage.setScene(new Scene(root,250,295));
		primaryStage.setResizable(false);
		primaryStage.getIcons().add(im);
		primaryStage.setTitle("My Calculator.... ");
		primaryStage.show();
		
		
	}
	
	private Button createButtonForNumber(String ch) {
		Button button = new Button(ch);
		button.setPrefSize(50, 40);
		button.setFont(Font.font(18));
		button.setOnAction(this::processNumber);
		return button;
	}
	
	private Button createButtonForOperator(String ch) {
		Button button = new Button(ch);
		button.setPrefSize(50, 40);
		button.setFont(Font.font(18));
		button.setOnAction(this::processOperator);
		return button;
	}
	
	private void processNumber(ActionEvent e) {
		
		Button button =(Button)e.getSource();
		String value=button.getText();
		
		boolean decimalExists =false;
		if(value==".") {
			if(start) {
				textField.setText("");
				start=false;
			}
			//check if point already exist
			String exp=textField.getText();
			for(int i=exp.length()-1;i>=0;i--) {
				char ch=exp.charAt(i);
				if(ch=='.') {
					decimalExists=true;
				}
				else if(Character.isDigit(ch)) {
					continue;
				}
				else {
					break;
				}
			}
		}
		else {
			if(start) {
				textField.setText("");
				start=false;
			}
		}
		
		if(decimalExists)
			return;
		else
			textField.setText(textField.getText()+value);
	}
	
    private void processOperator(ActionEvent e) {
    	if(start) {
			textField.setText("0");
			start=false;
		}
    	Button button =(Button)e.getSource();
		String value=button.getText();
		
		if(!value.equals("=")) {
			String text=textField.getText();
			if(isOperator(text.charAt(text.length()-1))) {
				
				text=text.substring(0,text.length()-1);
				textField.setText(text);
			}
			textField.setText(textField.getText()+value);
		}
		else {
			//calculate the expression
			String exp = textField.getText();
			String result = MathExpressions.evaluate(filterExpression(exp));
			textField.setText(result);
			start=true;
		}
	}
    
    
    private boolean isOperator(char ch) {
    	if(ch=='+' || ch=='-' || ch=='*' || ch=='/')
    		return true;
    	else
    		return false;
    }
	
	private Button createButtonForClear() {
		Button button = new Button("C");
		button.setPrefSize(110, 40);
		button.setFont(Font.font(18));
		button.setOnAction(e->{
			textField.setText("0");
			start=true;
		});
		return button;
	}
	
	private Button createButtonForBack() {
		
//		Image img = new Image("file:arrow2.png",20,20,true,true);
//		ImageView imgView = new ImageView(img);
		
		Button button = new Button("<--");
		button.setPrefSize(110, 40);
		button.setFont(Font.font(18));
		
		button.setOnAction(e->{
			String text = textField.getText();
			if(text.length()>1)
			{
			   text = text.substring(0, text.length()-1);
			   textField.setText(text);
			}
			else {
				textField.setText("0");
				start=true;
			}
		});
		return button;
	}
	
	
	private String filterExpression(String exp) {
		//
		if(isOperator(exp.charAt(exp.length()-1))) {
			exp=exp.substring(0,exp.length()-1);
		}
		
		for(int i=0;i<exp.length();i++) {
			if(exp.charAt(i)=='.' && isOperator(exp.charAt(i-1))) {
				String str1=exp.substring(0,i);
				String str2=exp.substring(i,exp.length());
				exp=str1+"0"+str2;
			}
		}
		
		
		return exp;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
