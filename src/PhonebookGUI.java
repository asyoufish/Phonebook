import java.io.File;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class PhonebookGUI extends Application {
    // TextFields, Buttons and RadioButtons
    private TextField nameTextField;
    private TextField addressTextField;
    private TextField phoneTextField;

    private Button btnAddEntry;
    private Button btnLoadEntry;
    private Button btnClearEntry;
    private Button btnSaveEntry;
    private Button btnModifyEntry;
    private Button btnSearchEntry;
    private Button btnClearSearch;
    private Button btnDeleteEntry;

    private RadioButton nameSearch;
    private RadioButton addressSearch;
    private RadioButton phoneSearch;

    // uses the Phonebook class
    private static ArrayList<Phonebook> entries = new ArrayList<Phonebook>();

    private static ArrayList<String> entriesStrings = new ArrayList<String>();

    private static ListView<String> entryList;

    // store phonebook file here
    private static String pBookFile = "C:\\temp\\phonebook.txt";

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            // Pane and layout
            GridPane gPane = new GridPane();
            gPane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
            gPane.setHgap(6);
            gPane.setVgap(6);

            // Input labels
            Label nameLabel = new Label("Name: ");
            Label addressLabel = new Label("Address: ");
            Label phoneLabel = new Label("Phone: ");

            // Input textfields
            nameTextField = new TextField();
            addressTextField = new TextField();
            phoneTextField = new TextField();

            gPane.add(nameLabel, 0, 0);
            gPane.add(nameTextField, 1, 0);
            gPane.add(addressLabel, 0, 1);
            gPane.add(addressTextField, 1, 1);
            gPane.add(phoneLabel, 0, 2);
            gPane.add(phoneTextField, 1, 2);

            // Entry Related Buttons
            btnAddEntry = new Button("Add Entry");
            btnClearEntry = new Button("Clear Input");
            btnModifyEntry = new Button("Modify Entry");

            HBox HPane = new HBox();
            HPane.setSpacing(20);
            HPane.getChildren().addAll(btnAddEntry, btnClearEntry, btnModifyEntry);
            HPane.setAlignment(Pos.CENTER);
            GridPane.setColumnSpan(HPane, 2);
            gPane.add(HPane, 0, 3);

            // Add Entry button functionality
            btnAddEntry.setOnAction(e -> {
                try {
                    String name = nameTextField.getText();
                    String address = addressTextField.getText();
                    BigInteger phoneNumber = new BigInteger(phoneTextField.getText()); // attempts to parse int for
                                                                                       // phone number
                    entries.add(new Phonebook(name, address, phoneNumber));
                    entryList.getItems().clear();
                    entriesStrings.clear();
                    for (Phonebook entry : entries)
                        entriesStrings.add(entry.toString());
                    ObservableList<String> items = FXCollections.observableArrayList(entriesStrings);
                    entryList.setItems(items);

                } catch (Exception ex) {
                    Alert alert = new Alert(AlertType.WARNING, "Wrong Input, Please Try Again");
                    alert.setTitle("Error");
                    alert.showAndWait();
                }
            });

            // Clear Input button functionality
            btnClearEntry.setOnAction(e -> {
                nameTextField.setText("");
                addressTextField.setText("");
                phoneTextField.setText("");
            });

            // Modify Entry button functionality
            btnModifyEntry.setOnAction(e -> {
                int index = entryList.getSelectionModel().getSelectedIndex();
                try {
                    String name = nameTextField.getText();
                    String address = addressTextField.getText();
                    BigInteger phoneNumber = new BigInteger(phoneTextField.getText()); // attempts to parse int for
                                                                                       // phone number

                    entries.set(index, new Phonebook(name, address, phoneNumber));
                    entryList.getItems().clear();
                    entriesStrings.clear();
                    for (Phonebook entry : entries)
                        entriesStrings.add(entry.toString());
                    ObservableList<String> items = FXCollections.observableArrayList(entriesStrings);
                    entryList.setItems(items);

                } catch (Exception ex) {
                    Alert alert = new Alert(AlertType.WARNING, "Wrong Input, Please Try Again");
                    alert.showAndWait();
                }
            });

            // Search RadioButtons and Search Button
            nameSearch = new RadioButton("Name");
            addressSearch = new RadioButton("Address");
            phoneSearch = new RadioButton("Phone");
            btnSearchEntry = new Button("Search");
            btnClearSearch = new Button("Clear Search");

            // ToggleGroup for search RadioButton
            final ToggleGroup search = new ToggleGroup();
            nameSearch.setToggleGroup(search);
            addressSearch.setToggleGroup(search);
            phoneSearch.setToggleGroup(search);
            nameSearch.setSelected(true);

            HBox HPane1 = new HBox();
            HPane1.setSpacing(20);
            HPane1.getChildren().addAll(nameSearch, addressSearch, phoneSearch, btnSearchEntry, btnClearSearch);
            HPane1.setAlignment(Pos.CENTER);
            GridPane.setColumnSpan(HPane1, 2);
            gPane.add(HPane1, 0, 4);

            // Search Entry Button functionality
            btnSearchEntry.setOnAction(e -> {
                try {
                    ArrayList<Phonebook> searchList = new ArrayList<Phonebook>();
                    if (nameSearch.isSelected()) {
                        String name = nameTextField.getText().trim();
                        for (int i = 0; i < entries.size(); i++) {
                            if (entries.get(i).getName().equals(name)) {
                                searchList.add(entries.get(i));
                            }
                        }
                    } else if (addressSearch.isSelected()) {
                        String address = addressTextField.getText();
                        for (int i = 0; i < entries.size(); i++) {
                            if (entries.get(i).getAddress().equals(address)) {
                                searchList.add(entries.get(i));
                            }
                        }
                    } else if (phoneSearch.isSelected()) {
                        BigInteger phone = new BigInteger(phoneTextField.getText());
                        for (int i = 0; i < entries.size(); i++) {
                            if (entries.get(i).getPhoneNumber().equals(phone)) {
                                searchList.add(entries.get(i));
                            }
                        }
                    }
                    entriesStrings.clear();
                    for (Phonebook entry : searchList)
                        entriesStrings.add(entry.toString());
                    ObservableList<String> items = FXCollections.observableArrayList(entriesStrings);
                    entryList.setItems(items);
                } catch (Exception ex) {
                    Alert alert = new Alert(AlertType.WARNING, "Entry Not Found");
                    alert.showAndWait();

                }
            });

            // Clear Search Button functionality
            btnClearSearch.setOnAction(e -> {
                entriesStrings.clear();
                nameTextField.clear();
                addressTextField.clear();
                phoneTextField.clear();
                for (Phonebook entry : entries)
                    entriesStrings.add(entry.toString());
                ObservableList<String> items = FXCollections.observableArrayList(entriesStrings);
                entryList.setItems(items);
            });

            // Phone Book List
            entryList = new ListView<String>();
            entryList.setOrientation(Orientation.VERTICAL);

            StackPane entryPane = new StackPane();
            entryPane.getChildren().add(entryList);
            GridPane.setColumnSpan(entryPane, 2);
            gPane.add(entryPane, 0, 9);

            // Phonebook Entries Button
            btnLoadEntry = new Button("Load Phonebook");
            btnSaveEntry = new Button("Save Entry");
            btnDeleteEntry = new Button("Delete Entry");

            HBox HPane2 = new HBox();
            HPane2.setSpacing(20);
            HPane2.getChildren().addAll(btnLoadEntry, btnSaveEntry, btnDeleteEntry);
            HPane2.setAlignment(Pos.CENTER);
            GridPane.setColumnSpan(HPane2, 2);
            gPane.add(HPane2, 0, 10);

            // Load Phonebook button functionality
            btnLoadEntry.setOnAction(e -> {
                entries.clear();
                entriesStrings.clear();
                entryList.getItems().clear();

                loadPhoneBook();

                btnLoadEntry.setDisable(true);
                btnClearEntry.setDisable(false);
                btnAddEntry.setDisable(false);
            });

            // Save button functionality
            btnSaveEntry.setOnAction(e -> {
                File newPBF = new File(pBookFile);

                try {
                    if (!newPBF.exists())
                        newPBF.createNewFile();
                    PrintWriter pw = new PrintWriter(newPBF);
                    Alert alert = new Alert(AlertType.INFORMATION,
                            "Entry is saved in the file " + pBookFile + "\nYou can now safely close the application.");
                    alert.showAndWait();
                    for (Phonebook entry : entries)
                        pw.printf("%s:%s:%d\n", entry.getName(), entry.getAddress(), entry.getPhoneNumber());
                    pw.close();

                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            });

            // Delete Entry button functionality
            btnDeleteEntry.setOnAction(e -> {
                int index = entryList.getSelectionModel().getSelectedIndex();
                try {
                    entries.remove(index);
                    entryList.getItems().clear();
                    entriesStrings.clear();
                    for (Phonebook entry : entries)
                        entriesStrings.add(entry.toString());
                    ObservableList<String> items = FXCollections.observableArrayList(entriesStrings);
                    entryList.setItems(items);
                } catch (Exception ex) {
                    Alert alert = new Alert(AlertType.WARNING, "Please select an entry to delete");
                    alert.setTitle("");
                    alert.showAndWait();
                }
            });

            // Phone Book Selection Listener
            entryList.getSelectionModel().selectedItemProperty()
                    .addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                        int index = entryList.getSelectionModel().getSelectedIndex();
                        nameTextField.setText(entries.get(index).getName());
                        addressTextField.setText(entries.get(index).getAddress());
                        phoneTextField.setText(entries.get(index).getPhoneNumber() + "");
                    });

            // Scene launch
            Scene scene = new Scene(gPane);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Java Project - Phonebook Application");
            primaryStage.setOnShowing(e -> {
                loadPhoneBook();
            });
            primaryStage.show();

            // Save phonebook when close
            primaryStage.setOnCloseRequest(e -> {
                File newPBF = new File(pBookFile);
                try {
                    if (!newPBF.exists())
                        newPBF.createNewFile();
                    PrintWriter pw = new PrintWriter(newPBF);
                    Alert alert = new Alert(AlertType.INFORMATION,
                            "Entry is saved in the file " + pBookFile);
                    alert.setTitle("");
                    alert.showAndWait();
                    for (Phonebook entry : entries)
                        pw.printf("%s:%s:%d\n", entry.getName(), entry.getAddress(), entry.getPhoneNumber());
                    pw.close();

                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            });

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Load Phone Book function
    static void loadPhoneBook() {
        File pBF = new File(pBookFile);
        try {
            if (!pBF.exists())
                pBF.createNewFile();
            Scanner lines = new Scanner(pBF);
            if (!lines.hasNextLine()) {
                Alert alert = new Alert(AlertType.INFORMATION, "File is empty.\nPlease enter your information.");
                alert.showAndWait();
            } else {
                while (lines.hasNextLine()) {
                    Scanner line = new Scanner(lines.nextLine());
                    line.useDelimiter(":\\s*");
                    entries.add(new Phonebook(line.next(), line.next(), line.nextBigInteger()));
                    line.close();
                }
            }
            lines.close();

            for (Phonebook entry : entries)
                entryList.getItems().add(entry.toString());
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}
