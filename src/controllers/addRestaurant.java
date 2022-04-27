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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.Restaurant;
import resources.ReadProperties;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class addRestaurant implements Initializable {
    ReadProperties readP = new ReadProperties();

    @FXML
    private Button btnBack;
    @FXML
    private TextField txtIdRestaurant;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtCity;
    @FXML
    private TextField txtCapacity;
    @FXML
    private TableView<Restaurant> tableRestaurant;
    @FXML
    private TableColumn colId;
    @FXML
    private TableColumn colName;
    @FXML
    private TableColumn colCity;
    @FXML
    private TableColumn colCapacity;

    private ObservableList<Restaurant> restaurantObservableList;

    /**
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        restaurantObservableList = FXCollections.observableArrayList();
        this.colId.setCellValueFactory(new PropertyValueFactory("IdRestaurant"));
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
        cargaRestaurants(restaurantsList);
    }

    private void mongo() {
        GestorPersistenciaMongo gestorPersistenciaMongo = new GestorPersistenciaMongo();
        List<Restaurant> restaurantList = gestorPersistenciaMongo.getAll_Restaurants();
        cargaRestaurants(restaurantList);
    }

    /**
     * @param restList Carga los books en el observable list
     */
    public void cargaRestaurants(List<Restaurant> restList) {
        for (int i = 0; i < restList.size(); i++) {
            this.restaurantObservableList.add(restList.get(i));
        }
        this.tableRestaurant.setItems(restaurantObservableList);
    }

    public void addRestaurant() {
        GestorPersistencia gestorJdbc = new GestorPersistenciaJDBC();
        GestorPersistencia gestorMongo = new GestorPersistenciaMongo();

        int id = Integer.parseInt(this.txtIdRestaurant.getText());
        String name = this.txtName.getText();
        String city = this.txtCity.getText();
        int capacity = Integer.parseInt(this.txtCapacity.getText());

        Restaurant restaurant = new Restaurant(id, name, city, capacity);
        this.restaurantObservableList.add(restaurant);
        this.tableRestaurant.setItems(restaurantObservableList);

        if (home.selected.equals("JDBC")) {
            System.out.println("JDBC");
            gestorJdbc.insert_Restaurant(restaurant);
        }
        if (home.selected.equals("MONGODB")) {
            System.out.println("Mongo");
            gestorMongo.insert_Restaurant(restaurant);
        }
        clear();
    }

    public void clear() {
        txtIdRestaurant.clear();
        txtName.clear();
        txtCity.clear();
        txtCapacity.clear();
    }

    /**
     * Va a la página anterior
     */
    public void goBack(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(readP.getrView()+"home.fxml"));
            Stage stage = (Stage) btnBack.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

