package controllers;

import dao.GestorPersistencia;
import dao.GestorPersistenciaJDBC;
import dao.GestorPersistenciaMongo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.Restaurant;
import resources.ReadProperties;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class home implements Initializable {
    ReadProperties readP = new ReadProperties();

    public static int positionRestaurant;
    public static Restaurant restaurantSelected;

    static String selected = principal.seletedMethod;
    @FXML
    private Button btnAddBook;
    @FXML
    private Button btnAddRestaurant;
    @FXML
    private Button btnConsultBook;
    @FXML
    private Button btnEditRestaurant;
    @FXML
    private TableView<Restaurant> tableRestaurant;
    @FXML
    private TableColumn colName;
    @FXML
    private TableColumn colCity;
    @FXML
    private TableColumn colCapacity;
    @FXML
    private Button btnEditBook;

    private ObservableList<Restaurant> observableRestaurants;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        observableRestaurants = FXCollections.observableArrayList();
        this.colName.setCellValueFactory(new PropertyValueFactory("Name"));
        this.colCity.setCellValueFactory(new PropertyValueFactory("City"));
        this.colCapacity.setCellValueFactory(new PropertyValueFactory("Capacity"));

        if (home.selected.equals("JDBC")) {
            jdbc();
        }
        if (home.selected.equals("MONGODB")) {
            mongo();
        }
    }

    private void jdbc() {
        GestorPersistencia gestorPersistenciaJdbc = new GestorPersistenciaJDBC();
        List<Restaurant> restaurantsList = gestorPersistenciaJdbc.getAll_Restaurants();
        cargaBooks(restaurantsList);
    }

    private void mongo() {
        GestorPersistenciaMongo gestorPersistenciaMongo = new GestorPersistenciaMongo();
        List<Restaurant> listRestaurants = gestorPersistenciaMongo.getAll_Restaurants();
        cargaBooks(listRestaurants);
    }

    @FXML
    public void cargaBooks(List<Restaurant> listRestaurants) {
        for (int i = 0; i < listRestaurants.size(); i++) {
            this.observableRestaurants.add(listRestaurants.get(i));
        }
        this.tableRestaurant.setItems(observableRestaurants);
    }


    public void addRestaurant(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(readP.getrView()+"addRestaurant.fxml"));
            Stage stage = (Stage) btnAddRestaurant.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void consultBooks(ActionEvent actionEvent) {
        selection();
        if (restaurantSelected != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(readP.getrView()+"consultBooks.fxml"));
                Stage stage = (Stage) btnConsultBook.getScene().getWindow();
                Scene scene = new Scene(loader.load());
                stage.setScene(scene);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Tienes que seleccionar un restaurante, para hacer esta opción :)");

            alert.showAndWait();
        }
    }

    public void editRestaurant(ActionEvent actionEvent) {
        selection();
        if (restaurantSelected != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(readP.getrView()+"editRestaurant.fxml"));
                Stage stage = (Stage) btnEditRestaurant.getScene().getWindow();
                Scene scene = new Scene(loader.load());
                stage.setScene(scene);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Tienes que seleccionar un restaurante, para hacer esta opción :) ");

            alert.showAndWait();
        }
    }

    public void selection() {
        List<Restaurant> listRestaurants = new ArrayList<>(observableRestaurants);
        ObservableList selectedItem = tableRestaurant.getSelectionModel().getSelectedItems();
        Restaurant restaurant = (Restaurant) selectedItem.get(selectedItem.size() - 1);
        restaurantSelected = restaurant;
    }

    public void addBook(ActionEvent actionEvent) {
        selection();
        if (restaurantSelected != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(readP.getrView()+"addBook.fxml"));
                Stage stage = (Stage) btnAddBook.getScene().getWindow();
                Scene scene = new Scene(loader.load());
                stage.setScene(scene);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Tienes que seleccionar un restaurante, para hacer esta opción :) ");

            alert.showAndWait();
        }
    }
}
