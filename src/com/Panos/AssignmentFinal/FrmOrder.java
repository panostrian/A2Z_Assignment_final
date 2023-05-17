package com.Panos.AssignmentFinal;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;

import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;

public class FrmOrder extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//Fields
	private JTextField textFieldStock;
	private JComboBox<String> bookDropdown = new JComboBox<>();
	private JComboBox<String> libraryDropdown = new JComboBox<>();
//==========================================================================================
	//Constructor: FrmOrder()
	public FrmOrder() {
		//Initialize JFrame properties
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/resources/aueb.jpg")));
		this.setTitle("Add a New Order of books in libraries");
		this.setBackground(SystemColor.activeCaption);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setBounds(100, 100, 600, 371);

		//Create JPanel for content pane
		JPanel contentPane = new JPanel();
		contentPane.setBackground(new Color(240, 255, 255));
		contentPane.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		// WindowListener for window activation event
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {

			}
		});

//==========================================================================================
		
		
		JLabel lblBook = new JLabel("Select Book");
		lblBook.setHorizontalAlignment(SwingConstants.RIGHT);
		lblBook.setForeground(new Color(153, 0, 0));
		lblBook.setFont(new Font("Malgun Gothic", Font.BOLD, 16));
		lblBook.setBounds(32, 53, 136, 25);
		contentPane.add(lblBook);

		JLabel lblLibrary = new JLabel("Select Library");
		lblLibrary.setHorizontalAlignment(SwingConstants.RIGHT);
		lblLibrary.setForeground(new Color(153, 0, 0));
		lblLibrary.setFont(new Font("Malgun Gothic", Font.BOLD, 16));
		lblLibrary.setBounds(10, 112, 158, 22);
		contentPane.add(lblLibrary);

		JLabel lblStock = new JLabel("Stock to be added");
		lblStock.setHorizontalAlignment(SwingConstants.RIGHT);
		lblStock.setForeground(new Color(153, 0, 0));
		lblStock.setFont(new Font("Malgun Gothic", Font.BOLD, 16));
		lblStock.setBounds(10, 160, 158, 25);
		contentPane.add(lblStock);

		textFieldStock = new JTextField();
		textFieldStock.setBounds(200, 163, 120, 25);
		contentPane.add(textFieldStock);
		textFieldStock.setColumns(10);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(63, 271, 445, 2);
		contentPane.add(separator_1);

//===========================================================================================

		// JButton for order and add action listener

		JButton btnInsert = new JButton("Order");
		btnInsert.setForeground(new Color(0, 0, 153));
		btnInsert.setFont(new Font("Malgun Gothic", Font.BOLD, 17));
		btnInsert.setBounds(248, 283, 95, 25);
		contentPane.add(btnInsert);

		btnInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedBookID = bookDropdown.getSelectedItem().toString();
				String selectedLibraryID = libraryDropdown.getSelectedItem().toString();
				int stock = Integer.parseInt(textFieldStock.getText());

				// Check if the combination already exists in the BOOKLIBRARIES table
				String query = "SELECT * FROM BOOKLIBRARIES WHERE BOOK_ID = ? AND LIBRARY_ID = ?";
				try (Connection conn = DBconnector.getConnection();
						PreparedStatement ps = conn.prepareStatement(query)) {
					ps.setString(1, selectedBookID);
					ps.setString(2, selectedLibraryID);
					ResultSet resultSet = ps.executeQuery();
					if (resultSet.next()) {
						// Combination already exists, show error message
						JOptionPane.showMessageDialog(contentPane, "Combination already exists", "Error",
								JOptionPane.ERROR_MESSAGE);
					} else {
						// Combination doesn't exist, insert new row into BOOKLIBRARIES table
						String insertQuery = "INSERT INTO BOOKLIBRARIES (BOOK_ID, LIBRARY_ID, STOCK) VALUES (?, ?, ?)";
						try (PreparedStatement insertPs = conn.prepareStatement(insertQuery)) {
							insertPs.setString(1, selectedBookID);
							insertPs.setString(2, selectedLibraryID);
							insertPs.setInt(3, stock);
							insertPs.executeUpdate();

							// Display success message
							JOptionPane.showMessageDialog(contentPane, "Order placed successfully", "Success",
									JOptionPane.INFORMATION_MESSAGE);

							// Clear input fields
							textFieldStock.setText("");
							bookDropdown.setSelectedIndex(0);
							libraryDropdown.setSelectedIndex(0);
						} catch (SQLException ex) {
							ex.printStackTrace();
						}
					}
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		});

//===========================================================================================

		// JButton for close and add action listener
		JButton btnClose = new JButton("Close");
		btnClose.setForeground(new Color(0, 0, 153));
		btnClose.setFont(new Font("Malgun Gothic", Font.BOLD, 17));
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DriverClass.stockbooklibraries.setEnabled(true);
				DriverClass.order.setVisible(false);
				DriverClass.stockbooklibraries.refreshData(); // Refresh the table data
			}
		});
		btnClose.setBounds(463, 283, 95, 25);
		contentPane.add(btnClose);

//==========================================================================================

		// JComboBox for book dropdown
		JComboBox<String> bookDropdown = new JComboBox<>();
		bookDropdown.setBounds(200, 53, 308, 31);
		contentPane.add(bookDropdown);

		//JComboBox for library dropdown
		JComboBox<String> libraryDropdown = new JComboBox<>();
		libraryDropdown.setBounds(200, 112, 308, 28);
		contentPane.add(libraryDropdown);
		
		//===========================================================================================
		
				// JPanel Container
				JPanel panel1 = new JPanel();
				panel1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
				panel1.setBounds(10, 11, 564, 308);
				contentPane.add(panel1);

		// Populate book dropdown with data from the database
		try {
			List<String> bookIDs = getAllBookIDs(); // Retrieve book IDs from the database
			for (String bookID : bookIDs) {
				// Retrieve the book name based on the book ID from the database
				String bookName = getBookNameByID(bookID);
				bookDropdown.addItem(bookName); // Add the book name to the dropdown
			}

			List<String> libraryIDs = getAllLibraryIDs(); // Retrieve library IDs from the database
			for (String libraryID : libraryIDs) {
				// Retrieve the library name based on the library ID from the database
				String libraryName = getLibraryNameByID(libraryID);
				libraryDropdown.addItem(libraryName); // Add the library name to the dropdown
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

// ========================================================================================

	// Private Method: getAllBookIDs()

	private List<String> getAllBookIDs() throws SQLException {
		List<String> bookIDs = new ArrayList<>();

		String query = "SELECT ID FROM BOOK";
		Connection conn = DBconnector.getConnection();
		try (PreparedStatement ps = conn.prepareStatement(query); ResultSet resultSet = ps.executeQuery()) {
			while (resultSet.next()) {
				String bookID = resultSet.getString("BOOK_ID");
				bookIDs.add(bookID);
			}
		}

		return bookIDs;
	}

	// Private Method: getAllLibraryIDs()
	
	private List<String> getAllLibraryIDs() throws SQLException {
		List<String> libraryIDs = new ArrayList<>();

		String query = "SELECT ID FROM LIBRARY";
		Connection conn = DBconnector.getConnection();
		try (PreparedStatement ps = conn.prepareStatement(query); ResultSet resultSet = ps.executeQuery()) {
			while (resultSet.next()) {
				String libraryID = resultSet.getString("LIBRARY_ID");
				libraryIDs.add(libraryID);
			}
		}

		return libraryIDs;
	}

	//	Private Method: getBookNameByID(bookID)
	
	private String getBookNameByID(String bookID) throws SQLException {
		String bookName = null;

		String query = "SELECT Name FROM BOOK WHERE ID = ?";
		Connection conn = DBconnector.getConnection();
		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setString(1, bookID);
			ResultSet resultSet = ps.executeQuery();
			if (resultSet.next()) {
				bookName = resultSet.getString("Name");
			}
		}

		return bookName;
	}

	// Private Method: getLibraryNameByID(libraryID)
	
	private String getLibraryNameByID(String libraryID) throws SQLException {
		String libraryName = null;

		String query = "SELECT Name FROM LIBRARY WHERE ID = ?";
		Connection conn = DBconnector.getConnection();
		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setString(1, libraryID);
			ResultSet resultSet = ps.executeQuery();
			if (resultSet.next()) {
				libraryName = resultSet.getString("Name");
			}
		}

		return libraryName;
	}

}
