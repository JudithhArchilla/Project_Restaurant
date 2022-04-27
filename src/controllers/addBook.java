package controllers;

import Utils.Comprobaciones;
import dao.GestorPersistencia;
import dao.GestorPersistenciaJDBC;
import dao.GestorPersistenciaMongo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.Book;
import resources.ReadProperties;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class addBook implements Initializable {
   ReadProperties readP = new ReadProperties();

    @FXML
    private TextField txtId;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtDate;
    @FXML
    private TextField txtNroOfDinners;
    @FXML
    private TextField txtTableNro;
    @FXML
    private TextField txtVgMenu;
    @FXML
    private Button btnGoBack;
    @FXML
    private Button btnAddBook;
    @FXML
    private TableView<Book> tableBooks;
    @FXML
    private TableColumn colId;
    @FXML
    private TableColumn colName;
    @FXML
    private TableColumn colDate;
    @FXML
    private TableColumn colNDiners;
    @FXML
    private TableColumn colTableN;
    @FXML
    private TableColumn colVegetarianMenu;

    private ObservableList<Book> observableBooks;
    public int idBookSelectioned = 0;

    /**
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        observableBooks = FXCollections.observableArrayList();
        this.colId.setCellValueFactory(new PropertyValueFactory("idBook"));
        this.colName.setCellValueFactory(new PropertyValueFactory("bookName"));
        this.colDate.setCellValueFactory(new PropertyValueFactory("bookDate"));
        this.colNDiners.setCellValueFactory(new PropertyValueFactory("dinersBook"));
        this.colTableN.setCellValueFactory(new PropertyValueFactory("tableN"));
        this.colVegetarianMenu.setCellValueFactory(new PropertyValueFactory("vegetarianMenu"));

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

        for (int i = 0; i < listBooks.size(); i++) {
            if (idRestaurantSelected == listBooks.get(i).getIdRestaurant()) {
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

        for (int i = 0; i < listBooks.size(); i++) {
            if (idRestaurantSelected == listBooks.get(i).getIdRestaurant()) {
                selectedBooks.add(listBooks.get(i));
            }
        }
        cargaBooks(selectedBooks);
    }

    /**
     * @param listBooks Carga los books en el observable list
     */
    private void cargaBooks(List<Book> listBooks) {
        for (int i = 0; i < listBooks.size(); i++) {
            this.observableBooks.add(listBooks.get(i));
        }
        this.tableBooks.setItems(observableBooks);
    }

    public void addBook() {
        GestorPersistencia gestorJdbc = new GestorPersistenciaJDBC();
        GestorPersistencia gestorMongo = new GestorPersistenciaMongo();
        LocalDate comprobacionDate;
        boolean comprobationVgMenu;
        int idSelectedRestaurant = home.restaurantSelected.getIdRestaurant();
        int idBook = Integer.parseInt(this.txtId.getText());
        String name = this.txtName.getText();
        String Date = this.txtDate.getText();
        int dinnersBook = Integer.parseInt(this.txtNroOfDinners.getText());
        int tableNro = Integer.parseInt(this.txtTableNro.getText());
        String vgMenu = this.txtVgMenu.getText();

        comprobationVgMenu = Comprobaciones.comprobacionVgMenu(vgMenu);
        comprobacionDate = Comprobaciones.comprobacionFecha(Date);

        Book book = new Book(idBook, idSelectedRestaurant, name, comprobacionDate, dinnersBook, tableNro, comprobationVgMenu);

        this.observableBooks.add(book);
        this.tableBooks.setItems(observableBooks);

        if (home.selected.equals("JDBC")) {
            System.out.println("JDBC");
            gestorJdbc.insert_Book(book);
        }
        if (home.selected.equals("MONGODB")) {
            System.out.println("Mongo");
            gestorMongo.insert_Book(book);
        }
        clear();
    }

    public void clear() {
        txtId.clear();
        txtNroOfDinners.clear();
        txtName.clear();
        txtTableNro.clear();
        txtVgMenu.clear();
        txtDate.clear();
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
