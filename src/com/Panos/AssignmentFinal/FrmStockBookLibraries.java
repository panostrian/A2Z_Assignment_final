package com.Panos.AssignmentFinal;

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

public class FrmStockBookLibraries extends JFrame {
	private static final long serialVersionUID = 1L;

	// serchTitle is package-private static var that
	// gets the text user input of the text field
	// Since it is package-private it is accessible from
	// FrmBookView Form, while it is not accessible
	// from outside this package

	private JTable table_3;
	private JLabel lblBookLibraries;

	public FrmStockBookLibraries() {
		final JPanel contentPane;

		this.setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/resources/aueb.jpg")));
		this.setTitle("Book in each library and their stock");
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setBounds(100, 100, 807, 531);

		contentPane = new JPanel();
		contentPane.setLayout(null);
		contentPane.setBackground(new Color(224, 255, 255));
		contentPane.setBorder(new LineBorder(new Color(0, 0, 0)));
		setContentPane(contentPane);

		// Label Book Title
		lblBookLibraries = new JLabel("Books in Libraries and Stock");
		lblBookLibraries.setForeground(new Color(178, 34, 34));
		lblBookLibraries.setHorizontalAlignment(SwingConstants.CENTER);
		lblBookLibraries.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblBookLibraries.setBounds(168, 34, 433, 45);
		contentPane.add(lblBookLibraries);

//===========================================================================================

		// Button for making an Order
		JButton btnMakeOrder = new JButton("Make Order");
		btnMakeOrder.setForeground(new Color(0, 0, 153));
		btnMakeOrder.setFont(new Font("Malgun Gothic", Font.BOLD, 17));
		btnMakeOrder.setBounds(318, 448, 129, 25);

		// Order Event Handler
		btnMakeOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DriverClass.stockbooklibraries.setEnabled(false);
				DriverClass.order.setVisible(true);
				refreshData(); // Refresh the table data
			}
		});
		contentPane.add(btnMakeOrder);
//===========================================================================================
		// The button to close the window
		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DriverClass.mainFrame.setEnabled(true);
				DriverClass.stockbooklibraries.setVisible(false);
			}
		});
		btnClose.setForeground(new Color(0, 0, 153));
		btnClose.setFont(new Font("Malgun Gothic", Font.BOLD, 17));
		btnClose.setBounds(670, 448, 95, 25);
		contentPane.add(btnClose);

//===========================================================================================
				// The Table
				table_3 = new JTable();
				table_3.setShowGrid(true);// To show the lines in the table
				table_3.setShowVerticalLines(true);// To show the lines in the table
				table_3.setEnabled(false);// To disable user edit from table directly , making it view-only
				table_3.setCellSelectionEnabled(true);// To allow the user to select data from the table without editing it

				String[] columnNames = { "Library_ID", "Book_ID", "Available Stock"};
				DefaultTableModel model = new DefaultTableModel(columnNames, 0);

				DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
				renderer.setVerticalAlignment(SwingConstants.CENTER);
				table_3.setDefaultRenderer(Object.class, renderer);

				JScrollPane scrollPane = new JScrollPane(table_3);
				scrollPane.setBounds(25, 101, 740, 337);
				contentPane.add(scrollPane);

				try {

					Connection conn = DBconnector.getConnection();
					String query = "SELECT * FROM BOOKLIBRARIES";
					PreparedStatement pst = conn.prepareStatement(query);
					ResultSet rs = pst.executeQuery();

					while (rs.next()) {
						Object[] data = new Object[3];
						data[0] = rs.getInt("BOOK_ID");
						data[1] = rs.getString("LIBRARY_ID");
						data[2] = rs.getInt("STOCK");
						model.addRow(data);
					}

					table_3.setModel(model);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

//===========================================================================================

		// The JPanel
		JPanel panel1 = new JPanel();
		panel1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel1.setBounds(10, 11, 768, 473);
		contentPane.add(panel1);

	}

//===========================================================================================

	// A class to refresh the table data after a change
	void refreshData() {
		DefaultTableModel model = (DefaultTableModel) table_3.getModel();
		model.setRowCount(0); // Clear existing data

		try {
			Connection conn = DBconnector.getConnection();
			String query = "SELECT * FROM BOOKLIBRARIES";
			PreparedStatement pst = conn.prepareStatement(query);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				Object[] data = new Object[3];
				data[0] = rs.getInt("BOOK_ID");
				data[1] = rs.getString("LIBRARY_ID");
				data[2] = rs.getInt("STOCK");
				model.addRow(data);
			}

			table_3.setModel(model); // Update the table model
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
