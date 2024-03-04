package com.Panos.Final;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.border.BevelBorder;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableCellRenderer;

public class FrmBookSearchList extends JFrame {
	private static final long serialVersionUID = 1L;

	// serchTitle is package-private static var that
	// gets the text user input of the text field
	// Since it is package-private it is accessible from
	// FrmBookView Form, while it is not accessible
	// from outside this package

	static String searchTitle;
	private JTable table_1;

	public FrmBookSearchList() {
		final JPanel contentPane;
		final JTextField userInputBookTitle;

		this.setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/resources/aueb.jpg")));
		this.setTitle("Book Searchbar - Book List - New Book Additions");
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setBounds(100, 100, 800, 530);

		contentPane = new JPanel();
		contentPane.setLayout(null);
		contentPane.setBackground(new Color(224, 255, 255));
		contentPane.setBorder(new LineBorder(new Color(0, 0, 0)));
		setContentPane(contentPane);

		// Label Book Title
		JLabel lblBookTitle_search = new JLabel("Books");
		lblBookTitle_search.setForeground(new Color(178, 34, 34));
		lblBookTitle_search.setHorizontalAlignment(SwingConstants.CENTER);
		lblBookTitle_search.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblBookTitle_search.setBounds(168, 20, 118, 45);
		contentPane.add(lblBookTitle_search);

		// TextFiled for BookInsert Title
		userInputBookTitle = new JTextField();
		userInputBookTitle.setToolTipText("");
		userInputBookTitle.setFont(new Font("Gill Sans Nova", Font.ITALIC, 17));
		userInputBookTitle.setBounds(25, 75, 430, 29);
		contentPane.add(userInputBookTitle);
		userInputBookTitle.setColumns(10);

//===========================================================================================
		// Button for searching ..
		JButton btnsearch_book = new JButton("Search");
		btnsearch_book.setForeground(new Color(0, 0, 153));
		btnsearch_book.setFont(new Font("Malgun Gothic", Font.BOLD, 17));
		btnsearch_book.setBounds(474, 76, 129, 25);

		// Event handler for Searching ..
		btnsearch_book.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FrmBookSearchList.searchTitle = userInputBookTitle.getText();
				DriverClass.booksearchlist.setEnabled(false);
				DriverClass.bookview.setVisible(true);
				refreshData(); // Refresh the table data
			}
		});
		contentPane.add(btnsearch_book);

//===========================================================================================

		// Button for Insert
		JButton btnAddNew_book = new JButton("Add new");
		btnAddNew_book.setForeground(new Color(0, 0, 153));
		btnAddNew_book.setFont(new Font("Malgun Gothic", Font.BOLD, 17));
		btnAddNew_book.setBounds(636, 76, 129, 25);

		// Insert Event Handler
		btnAddNew_book.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DriverClass.booksearchlist.setEnabled(false);
				DriverClass.bookinsert.setVisible(true);
				refreshData(); // Refresh the table data
			}
		});
		contentPane.add(btnAddNew_book);
//===========================================================================================
		// The button to close the window
		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DriverClass.mainFrame.setEnabled(true);
				DriverClass.booksearchlist.setVisible(false);
			}
		});
		btnClose.setForeground(new Color(0, 0, 153));
		btnClose.setFont(new Font("Malgun Gothic", Font.BOLD, 17));
		btnClose.setBounds(670, 448, 95, 25);
		contentPane.add(btnClose);
//===========================================================================================
		// The Table
		table_1 = new JTable();
		table_1.setShowGrid(true);// To show the lines in the table
		table_1.setShowVerticalLines(true);// To show the lines in the table
		table_1.setEnabled(false);// To disable user edit from table directly , making it view-only
		table_1.setCellSelectionEnabled(true);// To allow the user to select data from the table without editing it

		String[] columnNames = { "ID", "Title", "Year", "Edition", "Author" };
		DefaultTableModel model = new DefaultTableModel(columnNames, 0);

		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setVerticalAlignment(SwingConstants.CENTER);
		table_1.setDefaultRenderer(Object.class, renderer);

		JScrollPane scrollPane = new JScrollPane(table_1);
		scrollPane.setBounds(25, 138, 740, 300);
		contentPane.add(scrollPane);

		try {

			Connection conn = DBconnector.getConnection();
			String query = "SELECT * FROM BOOK";
			PreparedStatement pst = conn.prepareStatement(query);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				Object[] data = new Object[5];
				data[0] = rs.getInt("BOOK_ID");
				data[1] = rs.getString("BOOK_TITLE");
				data[2] = rs.getInt("BOOK_YEAR");
				data[3] = rs.getInt("BOOK_EDITION");
				data[4] = rs.getString("BOOK_AUTHOR");
				model.addRow(data);
			}

			table_1.setModel(model);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

//===========================================================================================

		// The JPanel
		JPanel panel1 = new JPanel();
		panel1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel1.setBounds(10, 10, 768, 473);
		contentPane.add(panel1);

	}

//===========================================================================================

	// A class to refresh the table data after a change
	void refreshData() {
		DefaultTableModel model = (DefaultTableModel) table_1.getModel();
		model.setRowCount(0); // Clear existing data

		try {
			Connection conn = DBconnector.getConnection();
			String query = "SELECT * FROM BOOK";
			PreparedStatement pst = conn.prepareStatement(query);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				Object[] data = new Object[5];
				data[0] = rs.getInt("BOOK_ID");
				data[1] = rs.getString("BOOK_TITLE");
				data[2] = rs.getInt("BOOK_YEAR");
				data[3] = rs.getInt("BOOK_EDITION");
				data[4] = rs.getString("BOOK_AUTHOR");
				model.addRow(data);
			}

			table_1.setModel(model); // Update the table model
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
