package controllers;

import dao.GestorPersistencia;
import dao.GestorPersistenciaJDBC;
import dao.GestorPersistenciaMongo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import models.Book;
import resources.ReadProperties;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class consultBook implements Initializable {
    ReadProperties readP= new ReadProperties();
    public static int idBookSelectioned = 0;

    @FXML
    private Button btnGoBack;
    @FXML
    private Button btnEdit;
    @FXML
    private TableView<Book> tableBooks;
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

    /**
     * Este método recibe la id seleccionada para después trabajar en la db
     */
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
        this.tableBooks.setItems(observableBooks);
    }

    public void selection() {
        ObservableList selectedItem = tableBooks.getSelectionModel().getSelectedItems();
        Book book = (Book) selectedItem.get(selectedItem.size() - 1);
        idBookSelectioned = book.getIdBook();
    }

    public void editBook() {
        selection();
        if (idBookSelectioned != 0) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(readP.getrView()+"editBook.fxml"));
                Stage stage = (Stage) btnEdit.getScene().getWindow();
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

    public void deleteRestaurant() {
        GestorPersistencia gestorPersistenciaJdbc = new GestorPersistenciaJDBC();
        GestorPersistencia gestorPersistenciaMongo = new GestorPersistenciaMongo();
        selection();

        if (home.selected.equals("JDBC")) {
            gestorPersistenciaJdbc.deleteOne_Book(idBookSelectioned);

        } else if (home.selected.equals("MONGODB")) {
            gestorPersistenciaMongo.deleteOne_Book(idBookSelectioned);
        }

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Se ha eliminado el restaurante.");
        alert.showAndWait();

        goBack();
    }

    /**
     * Va a la página anterior
     */
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
