package models;

import java.time.LocalDate;
import java.util.Date;

public class Book {
    private int idBook;
    private int idRestaurant;
    private String bookName;
    private LocalDate bookDate;
    private int dinersBook;
    private int tableN;
    private boolean vegetarianMenu;

    public Book(int idBook, int idRestaurant, String bookName, LocalDate bookDate, int dinersBook, int tableN, boolean vegetarianMenu) {
        this.idBook = idBook;
        this.idRestaurant = idRestaurant;
        this.bookName = bookName;
        this.bookDate = bookDate;
        this.dinersBook = dinersBook;
        this.tableN = tableN;
        this.vegetarianMenu = vegetarianMenu;
    }

    public int getIdBook() {
        return idBook;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public LocalDate getBookDate() {
        return bookDate;
    }

    public void setBookDate(LocalDate bookDate) {
        this.bookDate = bookDate;
    }

    public int getTableN() {
        return tableN;
    }

    public void setTableN(int tableN) {
        this.tableN = tableN;
    }

    public boolean isVegetarianMenu() {
        return vegetarianMenu;
    }

    public void setVegetarianMenu(boolean vegetarianMenu) {
        this.vegetarianMenu = vegetarianMenu;
    }

    public int getDinersBook() {
        return dinersBook;
    }

    public void setDinersBook(int dinersBook) {
        this.dinersBook = dinersBook;
    }

    public int getIdRestaurant() {
        return idRestaurant;
    }

    @Override
    public String toString() {
        return "Book { " +
                "idBook = " + idBook +
                ", idRestaurant = " + idRestaurant +
                ", bookName = '" + bookName + '\'' +
                ", bookDate = " + bookDate +
                ", dinersBook = " + dinersBook +
                ", tableN = " + tableN +
                ", vegetarianMenu = " + vegetarianMenu +
                '}';
    }
}
