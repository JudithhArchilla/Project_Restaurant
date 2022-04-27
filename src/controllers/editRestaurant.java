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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.Book;
import models.Restaurant;
import resources.ReadProperties;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class editRestaurant implements Initializable {
    ReadProperties readP = new ReadProperties();

    @FXML
    private Button btnGoBack;
    @FXML
    private TableView<Book> tableBook;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtCity;
    @FXML
    private TextField txtCapacity;
    @FXML
    private TableColumn colId;
    @FXML
    private TableColumn colNB;
    @FXML
    private TableColumn colDate;
    @FXML
    private TableColumn colNDin;
    @FXML
    private TableColumn colTableN;
    @FXML
    private TableColumn colVMenu;

    private ObservableList<Book> observableBooks;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        observableBooks = FXCollections.observableArrayList();
        this.colId.setCellValueFactory(new PropertyValueFactory("idBook"));
        this.colNB.setCellValueFactory(new PropertyValueFactory("bookName"));
        this.colDate.setCellValueFactory(new PropertyValueFactory("bookDate"));
        this.colNDin.setCellValueFactory(new PropertyValueFactory("dinersBook"));
        this.colTableN.setCellValueFactory(new PropertyValueFactory("tableN"));
        this.colVMenu.setCellValueFactory(new PropertyValueFactory("vegetarianMenu"));

        if (home.selected.equals("JDBC")) {
            jdbc();
        }
        if (home.selected.equals("MONGODB")) {
            mongo();
        }
    }

    private void jdbc() {
        int idRestaurantSelected = home.restaurantSelected.getIdRestaurant();
        List<Book> selectedBooks = new ArrayList<>();

        GestorPersistencia gestorPersistenciaJdbc = new GestorPersistenciaJDBC();
        List<Book> listBooks = gestorPersistenciaJdbc.getAll_Bookings();

        for(int i = 0; i < listBooks.size(); i++){
            if(idRestaurantSelected == listBooks.get(i).getIdRestaurant()){
                selectedBooks.add(listBooks.get(i));
            }
        }
        cargaBooks(selectedBooks);
    }

    private void mongo() {
        GestorPersistenciaMongo gestorPersistenciaMongo = new GestorPersistenciaMongo();
        List<Book> listBooks = gestorPersistenciaMongo.getAll_Bookings();

        int idRestaurantSelected = home.restaurantSelected.getIdRestaurant();
        List<Book> selectedBooks = new ArrayList<>();

        for(int i = 0; i < listBooks.size(); i++){
            if(idRestaurantSelected == listBooks.get(i).getIdRestaurant()){
                selectedBooks.add(listBooks.get(i));
            }
        }
        cargaBooks(selectedBooks);
    }

    private void cargaBooks(List<Book> listBooks) {
        for (int i = 0; i < listBooks.size(); i++) {
            this.observableBooks.add(listBooks.get(i));
        }
        this.tableBook.setItems(observableBooks);
    }

    public void editRestaurant(ActionEvent actionEvent) {
        GestorPersistencia gestorPersistenciaJdbc = new GestorPersistenciaJDBC();
        GestorPersistencia gestorPersistenciaMongo = new GestorPersistenciaMongo();

        String name = this.txtName.getText();
        String city = this.txtCity.getText();
        int capacity = Integer.parseInt(this.txtCapacity.getText());

        Restaurant restaurant = new Restaurant(home.restaurantSelected.getIdRestaurant(), name, city, capacity);

        if (home.selected.equals("JDBC")) {
            gestorPersistenciaJdbc.update_Restaurant(restaurant);

        } else if (home.selected.equals("MONGODB")) {
            gestorPersistenciaMongo.update_Restaurant(restaurant);
        }

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText("Se han actualizado los datos del restaurante");
        alert.showAndWait();
        clear();
    }

    public void deleteRestaurant(ActionEvent actionEvent) {
        GestorPersistencia gestorPersistenciaJdbc = new GestorPersistenciaJDBC();
        GestorPersistencia gestorPersistenciaMongo = new GestorPersistenciaMongo();

        if (home.selected.equals("JDBC")) {
            gestorPersistenciaJdbc.deleteOne_Restaurant(home.restaurantSelected.getIdRestaurant());

        } else if (home.selected.equals("MONGODB")) {
            gestorPersistenciaMongo.deleteOne_Restaurant(home.restaurantSelected.getIdRestaurant());
        }

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("S'ha eliminat el restaurant");
        alert.showAndWait();
        goBack();
    }

    public void clear() {
        txtName.clear();
        txtCity.clear();
        txtCapacity.clear();
    }

    public void goBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(readP.getrView()+"home.fxml"));
            Stage stage = (Stage) btnGoBack.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
