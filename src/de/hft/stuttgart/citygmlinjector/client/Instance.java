/*
 * CityGML Injector is a Java software for editing a CityGML file. 
 * https://github.com/tomeof/citygml-injector
 * 
 * Copyright (c) 2016, Hochschule fur Technik Stuttgart
 *
 * CityGML Injector is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option)
 * any later version.
 *
 * CityGML Injector is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with CityGML Injector. If not, see <http://www.gnu.org/licenses/>.
 */

package de.hft.stuttgart.citygmlinjector.client;

import java.util.ArrayList;

import de.hft.stuttgart.citygmlinjector.state.Context;
import de.hft.stuttgart.citygmlinjector.state.Operations;
import de.hft.stuttgart.citygmlinjector.validate.Validator;
import de.hft.stuttgart.citygmlinjector.values.Actions;
import de.hft.stuttgart.citygmlinjector.values.Attributes;
import de.hft.stuttgart.citygmlinjector.values.Version;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Instance extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		ListView<String> citygmlList = new ListView<>();
		
		ArrayList<String> actions = new ArrayList<>();
		actions.add(Actions.GENERATE);
		actions.add(Actions.REMOVE);
		ListView<String> actionsList = new ListView<>();
		actionsList.getItems().addAll(actions);

		ArrayList<String> attributes = new ArrayList<>();
		attributes.add(Attributes.GMLID);
		attributes.add(Attributes.XLINK);
		ListView<String> attributeList = new ListView<>();
		attributeList.getItems().addAll(attributes);
		
		TextArea logField = new TextArea();
		
		// layout setup
		GridPane gridpane = new GridPane();
		ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(28);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(28);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(28);
        ColumnConstraints col4 = new ColumnConstraints();
        col4.setPercentWidth(16);
        gridpane.getColumnConstraints().addAll(col1,col2,col3,col4);
        
        RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(70);
        RowConstraints row2 = new RowConstraints();
        row2.setPercentHeight(30);
        gridpane.getRowConstraints().addAll(row1,row2);
        
        // buttons
        Button openButton = new Button("Open...");
        Button applyButton = new Button("Apply");
        Button saveButton = new Button("Save");
        openButton.setMaxWidth(Double.MAX_VALUE);
        applyButton.setMaxWidth(Double.MAX_VALUE);
        saveButton.setMaxWidth(Double.MAX_VALUE);
        
        // vboxes
        VBox vbox1 = new VBox();
        vbox1.getChildren().addAll(new Label("CityGML Elements"), citygmlList);
        VBox.setVgrow(citygmlList, Priority.ALWAYS);

        VBox vbox2 = new VBox();
        vbox2.getChildren().addAll(new Label("Actions"), actionsList);
        VBox.setVgrow(actionsList, Priority.ALWAYS);
        
        VBox vbox3 = new VBox();
        vbox3.getChildren().addAll(new Label("Attributes"), attributeList);
        VBox.setVgrow(attributeList, Priority.ALWAYS);

        VBox vbox4 = new VBox();
        vbox4.setSpacing(2);
        vbox4.setAlignment(Pos.CENTER);
        vbox4.getChildren().addAll(openButton , applyButton, saveButton);
        
        gridpane.add(vbox1, 0, 0);
        gridpane.add(vbox2, 1, 0);
        gridpane.add(vbox3, 2, 0);
        gridpane.add(vbox4, 3, 0);
        gridpane.add(logField, 0, 1, 4, 1);
        gridpane.setHgap(4);
        gridpane.setVgap(4);
		
		// scene setup
		Scene scene = new Scene(gridpane, 700, 500);
		Image icon = new Image("de/hft/stuttgart/citygmlinjector/resources/house_icon.png");
		primaryStage.getIcons().add(icon);
		primaryStage.setTitle("CityGML Injector " + Version.NUMBER);
		primaryStage.setScene(scene);
		primaryStage.show();
		
		// context
		Context context = new Context();
		Validator validator = new Validator();
		context.validator = validator;
		context.gui.stage = primaryStage;		
		context.gui.citygmlList = citygmlList;
		context.gui.actionsList = actionsList;
		context.gui.attributeList = attributeList;
		context.gui.logField = logField;
		context.gui.openButton = openButton;
		context.gui.applyButton = applyButton;
		context.gui.saveButton = saveButton;
		
		@SuppressWarnings("unused")
		Operations operations = new Operations(context);
	}

}
