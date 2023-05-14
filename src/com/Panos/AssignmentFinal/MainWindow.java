package com.Panos.AssignmentFinal;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.Box;

public class MainWindow extends JFrame {
    private static final long serialVersionUID = 1L;
    
    JPanel contentPane;

    public MainWindow() {
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/resources/aueb.jpg")));
        this.setResizable(false);
        this.setTitle("AUEB Java A2Z 2023");
        this.setBounds(100, 100, 460, 360);

        // Αντικατέστησα το κώδικα της MainWindow που έκανε τη σύνδεση με τον σερβερ, γιατί δημιουργούσε πρόβλημα όταν έκλεινα την Main Window για να ανοίξω εάν
        // άλλο παράθυρο. Θα μπορούσα να τα κρατάω όλα ανοιχτά για να το αποφύγω, αλλά μου φάνηκε λάθος πρακτική. Θεώρησα ότι λιγότερα ανοιχτά παράθυρα =
        // πιο βολικό για τον χρήστη. Γι’ αυτό έκανα το DBconnector, στο οποίο μετέφερα τον κώδικα της σύνδεσης, έτσι ώστε να είναι μονίμως "ενεργό" και προσβασιμο
        // κατα τη διαρκια χρησης.
        
		/*
		 * this.addWindowListener(new WindowAdapter() {
		 * 
		 * @Override public void windowOpened(WindowEvent e) {
		 * 
		 * String url = "jdbc:mysql://localhost:3306/java_assignment_db"; String
		 * username = "PanosTr"; // Insert your username String password = "123456"; //
		 * Insert your password
		 * 
		 * try { conn = DriverManager.getConnection(url, username, password); } catch
		 * (SQLException ex) { throw new
		 * IllegalStateException("Cannot connect to database!", ex); } } });
		 */
        
 
        

        // Content Pane
        contentPane = new JPanel();
        contentPane.setBackground(new Color(240, 248, 255));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        // Main title Green & Red for shadow
        JLabel lbl_maintitle = new JLabel("Book/Library Orders");
        lbl_maintitle.setBounds(55, 38, 322, 47);
        lbl_maintitle.setHorizontalAlignment(SwingConstants.RIGHT);
        lbl_maintitle.setForeground(new Color(0, 100, 0));
        lbl_maintitle.setFont(new Font("Gill Sans MT", Font.BOLD, 31));
        contentPane.add(lbl_maintitle);

        // Separator Line
        JSeparator separator = new JSeparator();
        separator.setBounds(10, 84, 426, 1);
        contentPane.add(separator);

        // Label Libraries
        JLabel lbl_libraries = new JLabel("Libraries");
        lbl_libraries.setBounds(53, 234, 95, 27);
        lbl_libraries.setHorizontalAlignment(SwingConstants.RIGHT);
        lbl_libraries.setForeground(new Color(153, 0, 0));
        lbl_libraries.setFont(new Font("Gill Sans MT", Font.BOLD, 20));
        contentPane.add(lbl_libraries);

        //Button for moving to the FrmLibrarySearchList
        JButton btnLibraries = new JButton("");
        btnLibraries.setBounds(158, 234, 33, 27);

        btnLibraries.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DriverClass.librarysearchlist.setVisible(true);
                DriverClass.mainFrame.setEnabled(false);
            }
        });
        contentPane.add(btnLibraries);


        // Label Version
        JLabel lbl_version = new JLabel("Version");
        lbl_version.setHorizontalAlignment(SwingConstants.LEFT);
        lbl_version.setBounds(301, 234, 82, 27);
        lbl_version.setForeground(new Color(153, 0, 0));
        lbl_version.setFont(new Font("Gill Sans MT", Font.BOLD, 20));
        contentPane.add(lbl_version);

        // Button Version
        JButton btnVersion = new JButton("");
        btnVersion.setBounds(258, 234, 33, 27);

        btnVersion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DriverClass.mainFrame.setEnabled(false);
                DriverClass.version.setVisible(true);

            }
        });
        contentPane.add(btnVersion);
        
        //	Button for moving to the FrmBookSearchList
        JButton btnBooks = new JButton("");
        btnBooks.setBounds(158, 125, 33, 27);
        btnBooks.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		DriverClass.booksearchlist.setVisible(true);
                DriverClass.mainFrame.setEnabled(false);
        	}
        });
        contentPane.add(btnBooks);
        
        //	Label Books
        JLabel lbl_books = new JLabel("Books");
        lbl_books.setHorizontalAlignment(SwingConstants.RIGHT);
        lbl_books.setForeground(new Color(153, 0, 0));
        lbl_books.setFont(new Font("Gill Sans MT", Font.BOLD, 20));
        lbl_books.setBounds(53, 125, 95, 27);
        contentPane.add(lbl_books);
        
        //Button for moving to the FrmStockBookLibraries
        JButton btnStock = new JButton("");
        btnStock.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
                DriverClass.mainFrame.setEnabled(false);
                DriverClass.stockbooklibraries.setVisible(true);
        	}
        });
        btnStock.setBounds(258, 125, 33, 27);
        contentPane.add(btnStock);
        
        JLabel lbl_stock = new JLabel("Stock");
        lbl_stock.setHorizontalAlignment(SwingConstants.LEFT);
        lbl_stock.setForeground(new Color(153, 0, 0));
        lbl_stock.setFont(new Font("Gill Sans MT", Font.BOLD, 20));
        lbl_stock.setBounds(301, 125, 82, 27);
        contentPane.add(lbl_stock);
        
        JLabel lbl_maintitle_1 = new JLabel("Book/Library Orders");
        lbl_maintitle_1.setHorizontalAlignment(SwingConstants.RIGHT);
        lbl_maintitle_1.setForeground(Color.GRAY);
        lbl_maintitle_1.setFont(new Font("Gill Sans MT", Font.BOLD, 31));
        lbl_maintitle_1.setBounds(56, 40, 322, 47);
        contentPane.add(lbl_maintitle_1);
        
        JSeparator separator_2 = new JSeparator();
        separator_2.setBounds(213, 95, 1, 196);
        contentPane.add(separator_2);
        
        JSeparator separator_3 = new JSeparator();
        separator_3.setBounds(213, 84, 1, 207);
        contentPane.add(separator_3);
        
        JLabel lblNewLabel = new JLabel("(Books in each library)");
        lblNewLabel.setForeground(new Color(153, 0, 0));
        lblNewLabel.setFont(new Font("Gill Sans MT", Font.PLAIN, 14));
        lblNewLabel.setBounds(268, 162, 134, 13);
        contentPane.add(lblNewLabel);
        
        Component rigidArea = Box.createRigidArea(new Dimension(15, 15));
        rigidArea.setFont(null);
        rigidArea.setBackground(new Color(0, 0, 0));
        rigidArea.setForeground(new Color(0, 0, 0));
        rigidArea.setBounds(0, 84, 446, 212);
        contentPane.add(rigidArea);
    }
    

}

