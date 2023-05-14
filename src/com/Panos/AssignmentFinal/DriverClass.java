package com.Panos.AssignmentFinal;

import java.awt.EventQueue;

public class DriverClass {
	static MainWindow mainFrame;
    static FrmBookInsert bookinsert;
    static FrmBookView bookview;
    static FrmBookSearchList booksearchlist;
    static FrmLibraryInsert libraryinsert;
    static FrmLibraryView libraryview;
    static FrmLibrarySearchList librarysearchlist;
    static FrmOrder order;
    static FrmStockBookLibraries stockbooklibraries;
    static FrmVersion version;


    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    mainFrame = new MainWindow();
                    mainFrame.setLocationRelativeTo(null);
                    mainFrame.setVisible(true);

                    booksearchlist = new FrmBookSearchList();
                    booksearchlist.setLocationRelativeTo(null);
                    booksearchlist.setVisible(false);
                    
                    bookview = new FrmBookView();
                    bookview.setLocationRelativeTo(null);
                    bookview.setVisible(false);
                    
                    bookinsert = new FrmBookInsert();
                    bookinsert.setLocationRelativeTo(null);
                    bookinsert.setVisible(false);
                    
                    librarysearchlist = new FrmLibrarySearchList();
                    librarysearchlist.setLocationRelativeTo(null);
                    librarysearchlist.setVisible(false);
                    
                    libraryview = new FrmLibraryView();
                    libraryview.setLocationRelativeTo(null);
                    libraryview.setVisible(false);
                    
                    libraryinsert = new FrmLibraryInsert();
                    libraryinsert.setLocationRelativeTo(null);
                    libraryinsert.setVisible(false);
                    
                    stockbooklibraries = new FrmStockBookLibraries();
                    stockbooklibraries.setLocationRelativeTo(null);
                    stockbooklibraries.setVisible(false);
                    
                    order = new FrmOrder();
                    order.setLocationRelativeTo(null);
                    order.setVisible(false);

                    version = new FrmVersion();
                    version.setLocationRelativeTo(null);
                    version.setVisible(false);
                    

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }}
