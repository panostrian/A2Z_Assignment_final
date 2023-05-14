package com.Panos.AssignmentFinal;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FrmLibrarySearchList extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// serchEpwnympVar is package-private static var that
    // gets the text user input of the text field
    // Since it is package-private it is accessible from
    // FrmEkpaideytesUpdate Form, while it is not accessible
    // from outside this package
    static String LibraryName;

    public FrmLibrarySearchList() {
        final JPanel contentPane;
        final JTextField userInputEpwnymo;
        final JButton btnSearch;
        final JButton btnClose;

        this.setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/resources/aueb.jpg")));
        this.setTitle("Αναζήτηση Εκπαιδευτών");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setBounds(100, 100, 489, 381);

        contentPane = new JPanel();
        contentPane.setLayout(null);
        contentPane.setBackground(new Color(224, 255, 255));
        contentPane.setBorder(new LineBorder(new Color(0, 0, 0)));
        setContentPane(contentPane);

        // Label Last Name
        JLabel lblLastName = new JLabel("Επώνυμο");
        lblLastName.setForeground(new Color(178, 34, 34));
        lblLastName.setHorizontalAlignment(SwingConstants.CENTER);
        lblLastName.setFont(new Font("Tahoma", Font.PLAIN, 26));
        lblLastName.setBounds(166, 30, 118, 45);
        contentPane.add(lblLastName);

        // TextFiled for inserting Last Name
        userInputEpwnymo = new JTextField();
        userInputEpwnymo.setBounds(125, 77, 200, 22);
        contentPane.add(userInputEpwnymo);
        userInputEpwnymo.setColumns(10);

        // Button for searching ..
        btnSearch = new JButton("Αναζήτηση");
        btnSearch.setForeground(new Color(0, 0, 255));
        btnSearch.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnSearch.setBounds(163, 112, 133, 45);
/*
        // Event handler for Searching ..
        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SearchForm.searchEpwnymoVar = userInputEpwnymo.getText();
                LibraryBookOrders.ekpaidSearchFrame.setEnabled(false);
                LibraryBookOrders.ekpaidUpdateFrame.setVisible(true);
            }
        });
        contentPane.add(btnSearch);
*/
        // Container for Search Components
        JPanel searchPanelContainer = new JPanel();
        searchPanelContainer.setBorder(new LineBorder(new Color(0, 0, 0)));
        searchPanelContainer.setBounds(68, 13, 324, 151);
        contentPane.add(searchPanelContainer);

        // Button for Insert
        JButton btnInsert = new JButton("Εισαγωγή");
        btnInsert.setForeground(Color.BLUE);
        btnInsert.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnInsert.setBounds(166, 200, 133, 45);

        // Insert Event Handler
        btnInsert.addActionListener(new ActionListener() {
            //handler for insert
            public void actionPerformed(ActionEvent e) {
                DriverClass.librarysearchlist.setEnabled(false);
                DriverClass.libraryinsert.setVisible(true);
            }
        });
        contentPane.add(btnInsert);

        // Container for Insert
        JPanel insertPanelContainer = new JPanel();
        insertPanelContainer.setBorder(new LineBorder(new Color(0, 0, 0)));
        insertPanelContainer.setBounds(68, 177, 324, 93);
        contentPane.add(insertPanelContainer);

        // Button for Close
        btnClose = new JButton("Close");
        btnClose.setForeground(new Color(0, 0, 205));
        btnClose.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnClose.setBounds(295, 296, 97, 25);

        // Event handler for close button
        btnClose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DriverClass.mainFrame.setEnabled(true);             // Enables previous form
                DriverClass.librarysearchlist.setVisible(false);    // Non-visible current form
            }
        });
        contentPane.add(btnClose);
    }
}
