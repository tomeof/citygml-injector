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

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import de.hft.stuttgart.citygmlinjector.state.Context;
import de.hft.stuttgart.citygmlinjector.state.Operations;
import de.hft.stuttgart.citygmlinjector.validate.Validator;
import de.hft.stuttgart.citygmlinjector.values.Actions;
import de.hft.stuttgart.citygmlinjector.values.Attributes;
import de.hft.stuttgart.citygmlinjector.values.Version;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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
		gridpane.setPadding(new Insets(4, 4, 4, 4));
		ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(33);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(34);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(33);
        gridpane.getColumnConstraints().addAll(col1,col2,col3);
        
        // buttons
        Button openButton = new Button("Open...");
        Button applyButton = new Button("Apply");
        Button saveButton = new Button("Save");
        
        // progressbar
        ProgressBar progressBar = new ProgressBar(0.0);
        
        // vboxes
        VBox vbox1 = new VBox();
        vbox1.getChildren().addAll(new Label("CityGML Elements"), citygmlList);
        VBox.setVgrow(citygmlList, Priority.ALWAYS);

        VBox vbox2 = new VBox();
        vbox2.getChildren().addAll(new Label("Actions"), actionsList);
        VBox.setVgrow(actionsList, Priority.ALWAYS);
        vbox2.setPadding(new Insets(0, 4, 0, 4));
        
        VBox vbox3 = new VBox();
        vbox3.getChildren().addAll(new Label("Attributes"), attributeList);
        VBox.setVgrow(attributeList, Priority.ALWAYS);
        
        // hboxes
        HBox hbox1 = new HBox();
        hbox1.getChildren().addAll(openButton , applyButton, saveButton);
        hbox1.setSpacing(4);
        HBox hbox2 = new HBox();
        hbox2.setAlignment(Pos.CENTER_RIGHT);
        hbox2.getChildren().add(progressBar);
        progressBar.prefHeightProperty().bind(hbox2.heightProperty());

        // rows
        RowConstraints row1 = new RowConstraints();
        row1.prefHeightProperty().bind(gridpane.heightProperty().multiply(0.7).subtract(hbox1.getPrefHeight()));
        RowConstraints row2 = new RowConstraints();
        row2.prefHeightProperty().bind(gridpane.heightProperty().multiply(0.3).subtract(hbox1.getPrefHeight()));
        RowConstraints row3 = new RowConstraints();
        
        gridpane.getRowConstraints().addAll(row1,row2,row3);
        gridpane.add(vbox1, 0, 0);
        gridpane.add(vbox2, 1, 0);
        gridpane.add(vbox3, 2, 0);
        gridpane.add(logField, 0, 1, 3, 1);
        gridpane.add(hbox1, 0, 2, 2, 1);
        gridpane.add(hbox2, 2, 2, 1, 1);
        gridpane.setVgap(4);
		
		// scene setup
		Scene scene = new Scene(gridpane, 700, 500);
		
		Path currentRelativePath = Paths.get("");
		File file = new File(currentRelativePath.toAbsolutePath().toString() + "/resources/house_icon.png");
		
		if (!file.exists()) {
			file = new File(currentRelativePath.toAbsolutePath().toString() + "/../resources/house_icon.png");
		}

		String fileUrl = file.toURI().toURL().toString();
		Image icon = new Image(fileUrl);
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
		context.gui.progressBar = progressBar;
		
		@SuppressWarnings("unused")
		Operations operations = new Operations(context);
	}

}
