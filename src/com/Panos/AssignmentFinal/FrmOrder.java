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

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class FrmOrder extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textFieldStock;
	private JComboBox<String> bookDropdown = new JComboBox<>();
	private JComboBox<String> libraryDropdown = new JComboBox<>();

//==========================================================================================
	public FrmOrder() {
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/resources/aueb.jpg")));
		this.setTitle("Add a New Order of books in libraries");
		this.setBackground(SystemColor.activeCaption);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setBounds(100, 100, 600, 371);

		JPanel contentPane = new JPanel();
		contentPane.setBackground(new Color(240, 255, 255));
		contentPane.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		// WindowListener for window activation event
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				// Clear input fields
				textFieldStock.setText("");

				// bookDropdown.setSelectedIndex(0);
				// libraryDropdown.setSelectedIndex(0);
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

//==========================================================================================

		// JComboBox for book dropdown
		JComboBox<String> bookDropdown = new JComboBox<>();
		bookDropdown.setBounds(200, 53, 308, 31);
		contentPane.add(bookDropdown);

		// JComboBox for library dropdown
		JComboBox<String> libraryDropdown = new JComboBox<>();
		libraryDropdown.setBounds(200, 112, 308, 28);
		contentPane.add(libraryDropdown);

		try {
			Connection conn = DBconnector.getConnection();
			PreparedStatement bookStatement = conn.prepareStatement("SELECT BOOK_ID FROM BOOK");
			PreparedStatement libraryStatement = conn.prepareStatement("SELECT LIBRARY_ID FROM LIBRARY");

			// Populate the book dropdown
			ResultSet bookResultSet = bookStatement.executeQuery();
			while (bookResultSet.next()) {
				String bookID = bookResultSet.getString("BOOK_ID");
				bookDropdown.addItem(bookID);
			}

			// Populate the library dropdown
			ResultSet libraryResultSet = libraryStatement.executeQuery();
			while (libraryResultSet.next()) {
				String libraryID = libraryResultSet.getString("LIBRARY_ID");
				libraryDropdown.addItem(libraryID);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

//===========================================================================================
		// JButton for order and add action listener
		JButton btnInsert = new JButton("Order");
		btnInsert.setForeground(new Color(0, 0, 153));
		btnInsert.setFont(new Font("Malgun Gothic", Font.BOLD, 17));

		btnInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String selectedBookID = bookDropdown.getSelectedItem().toString();
					String selectedLibraryID = libraryDropdown.getSelectedItem().toString();
					int stock = Integer.parseInt(textFieldStock.getText());

					Connection conn = DBconnector.getConnection();
					PreparedStatement ps = conn.prepareStatement(
							"INSERT INTO LIBRARYBOOKS (FK_BOOK_ID, FK_LIBRARY_ID, STOCK) VALUES (?, ?, ?)");
					ps.setString(1, selectedBookID);
					ps.setString(2, selectedLibraryID);
					ps.setInt(3, stock);

					JOptionPane.showMessageDialog(contentPane, "Order placed successfully", "Success",JOptionPane.INFORMATION_MESSAGE);

					ps.close();

				} catch (SQLException e3) {
					JOptionPane.showMessageDialog(contentPane, "Error occurred while inserting the order", "Error",
							JOptionPane.ERROR_MESSAGE);
					e3.printStackTrace();

				}
			}
		});
		btnInsert.setBounds(248, 283, 95, 25);
		contentPane.add(btnInsert);
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

// ===========================================================================================

		// JPanel Container
		JPanel panel1 = new JPanel();
		panel1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel1.setBounds(10, 11, 564, 308);
		contentPane.add(panel1);
	}

// ========================================================================================
//                           Helper Methods
// ========================================================================================


}
