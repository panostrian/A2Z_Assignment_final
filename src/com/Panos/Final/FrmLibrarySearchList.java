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

public class FrmLibrarySearchList extends JFrame {
	private static final long serialVersionUID = 1L;

	// serchTitle is package-private static var that
	// gets the text user input of the text field
	// Since it is package-private it is accessible from
	// FrmLibraryView Form, while it is not accessible
	// from outside this package

	static String searchName;
	private JTable table_2;

	public FrmLibrarySearchList() {
		final JPanel contentPane;
		final JTextField userInputLibTitle;

		this.setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/resources/aueb.jpg")));
		this.setTitle("Library Searchbar - Library List - New Library Additions");
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setBounds(100, 100, 800, 530);

		contentPane = new JPanel();
		contentPane.setLayout(null);
		contentPane.setBackground(new Color(224, 255, 255));
		contentPane.setBorder(new LineBorder(new Color(0, 0, 0)));
		setContentPane(contentPane);

		// Label Library Title
		JLabel lblLibraryName_search = new JLabel("Libraries");
		lblLibraryName_search.setForeground(new Color(178, 34, 34));
		lblLibraryName_search.setHorizontalAlignment(SwingConstants.CENTER);
		lblLibraryName_search.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblLibraryName_search.setBounds(168, 20, 118, 45);
		contentPane.add(lblLibraryName_search);

		// TextFiled for LibraryInsert Title
		userInputLibTitle = new JTextField();
		userInputLibTitle.setToolTipText("");
		userInputLibTitle.setFont(new Font("Gill Sans Nova", Font.ITALIC, 17));
		userInputLibTitle.setBounds(25, 75, 430, 29);
		contentPane.add(userInputLibTitle);
		userInputLibTitle.setColumns(10);

//===========================================================================================
		// Button for searching ..
		JButton btnsearch_library = new JButton("Search");
		btnsearch_library.setForeground(new Color(0, 0, 153));
		btnsearch_library.setFont(new Font("Malgun Gothic", Font.BOLD, 17));
		btnsearch_library.setBounds(474, 76, 129, 25);

		// Event handler for Searching ..
		btnsearch_library.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FrmLibrarySearchList.searchName = userInputLibTitle.getText();
				DriverClass.librarysearchlist.setEnabled(false);
				DriverClass.libraryview.setVisible(true);
				refreshData(); // Refresh the table data
			}
		});
		contentPane.add(btnsearch_library);

//===========================================================================================

		// Button for Insert
		JButton btnAddNew_library = new JButton("Add new");
		btnAddNew_library.setForeground(new Color(0, 0, 153));
		btnAddNew_library.setFont(new Font("Malgun Gothic", Font.BOLD, 17));
		btnAddNew_library.setBounds(636, 76, 129, 25);

		// Insert Event Handler
		btnAddNew_library.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DriverClass.librarysearchlist.setEnabled(false);
				DriverClass.libraryinsert.setVisible(true);
				refreshData(); // Refresh the table data
			}
		});
		contentPane.add(btnAddNew_library);
//===========================================================================================
		// The button to close the window
		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DriverClass.mainFrame.setEnabled(true);
				DriverClass.librarysearchlist.setVisible(false);
			}
		});
		btnClose.setForeground(new Color(0, 0, 153));
		btnClose.setFont(new Font("Malgun Gothic", Font.BOLD, 17));
		btnClose.setBounds(670, 448, 95, 25);
		contentPane.add(btnClose);
//===========================================================================================
		// The Table
		table_2 = new JTable();
		table_2.setShowGrid(true);// To show the lines in the table
		table_2.setShowVerticalLines(true);// To show the lines in the table
		table_2.setEnabled(false);// To disable user edit from table directly , making it view-only
		table_2.setCellSelectionEnabled(true);// To allow the user to select data from the table without editing it


		String[] columnNames = { "ID", "Name", "Address", "Phone"};
		DefaultTableModel model = new DefaultTableModel(columnNames, 0);

		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setVerticalAlignment(SwingConstants.CENTER);
		table_2.setDefaultRenderer(Object.class, renderer);

		JScrollPane scrollPane = new JScrollPane(table_2);
		scrollPane.setBounds(25, 138, 740, 300);
		contentPane.add(scrollPane);

		try {

			Connection conn = DBconnector.getConnection();
			String query = "SELECT * FROM LIBRARY";
			PreparedStatement pst = conn.prepareStatement(query);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				Object[] data = new Object[4];
				data[0] = rs.getInt("LIBRARY_ID");
				data[1] = rs.getString("LIBRARY_NAME");
				data[2] = rs.getString("LIBRARY_ADDRESS");
				data[3] = rs.getString("LIBRARY_PHONE");
				model.addRow(data);
			}

			table_2.setModel(model);
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
		DefaultTableModel model = (DefaultTableModel) table_2.getModel();
		model.setRowCount(0); // Clear existing data

		try {
			Connection conn = DBconnector.getConnection();
			String query = "SELECT * FROM LIBRARY";
			PreparedStatement pst = conn.prepareStatement(query);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				Object[] data = new Object[4];
				data[0] = rs.getInt("LIBRARY_ID");
				data[1] = rs.getString("LIBRARY_NAME");
				data[2] = rs.getString("LIBRARY_ADDRESS");
				data[3] = rs.getString("LIBRARY_PHONE");
				model.addRow(data);
			}

			table_2.setModel(model); // Update the table model
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
