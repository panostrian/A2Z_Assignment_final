package com.Panos.Final;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JSeparator;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.*;

import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

public class FrmLibraryView extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PreparedStatement pst;
	private ResultSet rs;

	private JTextField textFieldLibraryID_view;
	private JTextField textFieldLibraryName_view;
	private JTextField textFieldAddress_view;
	private JTextField textFieldPhone_view;

	public FrmLibraryView() {
		final JPanel contentPane;

		this.setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/resources/aueb.jpg")));

		setBackground(SystemColor.activeCaption);
		setTitle("View selected Library");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 601, 370);

		contentPane = new JPanel();
		contentPane.setBackground(new Color(240, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		// This form is getting its data from LIBRARY table in the DB
		// We have to do a Select not just when Window is Opened but also
		// when the window is Activated (refresh - window opened included)
		// after a delete or update action
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				try {
					String querry = "SELECT LIBRARY_ID, LIBRARY_NAME, LIBRARY_ADDRESS, LIBRARY_PHONE FROM LIBRARY WHERE LIBRARY_ID LIKE ?";

					Connection conn = DBconnector.getConnection();
					pst = conn.prepareStatement(querry, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
					pst.setString(1, FrmLibrarySearchList.searchName + '%');
					rs = pst.executeQuery();
					if (rs.next()) {
						updateFieldsFromResultSet();
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});

//===========================================================================================

		// Separator Line
		JSeparator separator3 = new JSeparator();
		separator3.setBounds(76, 272, 429, 2);
		contentPane.add(separator3);

		JLabel lblLibraryID_view = new JLabel("ID");
		lblLibraryID_view.setHorizontalAlignment(SwingConstants.RIGHT);
		lblLibraryID_view.setForeground(new Color(153, 0, 0));
		lblLibraryID_view.setFont(new Font("Malgun Gothic", Font.BOLD, 16));
		lblLibraryID_view.setBounds(68, 37, 60, 25);
		contentPane.add(lblLibraryID_view);

		JLabel lblLibraryName_view = new JLabel("LibraryName");
		lblLibraryName_view.setBounds(17, 79, 111, 25);
		contentPane.add(lblLibraryName_view);
		lblLibraryName_view.setHorizontalAlignment(SwingConstants.RIGHT);
		lblLibraryName_view.setForeground(new Color(153, 0, 0));
		lblLibraryName_view.setFont(new Font("Malgun Gothic", Font.BOLD, 16));

		JLabel lblAddress_view = new JLabel("Address");
		lblAddress_view.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAddress_view.setForeground(new Color(153, 0, 0));
		lblAddress_view.setFont(new Font("Malgun Gothic", Font.BOLD, 16));
		lblAddress_view.setBounds(48, 119, 80, 22);
		contentPane.add(lblAddress_view);

		JLabel lblPhone_view = new JLabel("Phone");
		lblPhone_view.setHorizontalAlignment(SwingConstants.CENTER);
		lblPhone_view.setForeground(new Color(153, 0, 0));
		lblPhone_view.setFont(new Font("Malgun Gothic", Font.BOLD, 16));
		lblPhone_view.setBounds(75, 160, 63, 25);
		contentPane.add(lblPhone_view);

		textFieldLibraryID_view = new JTextField();
		textFieldLibraryID_view.setEditable(false);
		textFieldLibraryID_view.setFont(new Font("Malgun Gothic", Font.PLAIN, 16));
		textFieldLibraryID_view.setColumns(12);
		textFieldLibraryID_view.setBounds(148, 36, 95, 25);
		contentPane.add(textFieldLibraryID_view);

		textFieldLibraryName_view = new JTextField();
		textFieldLibraryName_view.setColumns(10);
		textFieldLibraryName_view.setBounds(148, 80, 376, 25);
		contentPane.add(textFieldLibraryName_view);

		textFieldAddress_view = new JTextField();
		textFieldAddress_view.setColumns(10);
		textFieldAddress_view.setBounds(148, 119, 376, 25);
		contentPane.add(textFieldAddress_view);

		textFieldPhone_view = new JTextField();
		textFieldPhone_view.setColumns(10);
		textFieldPhone_view.setBounds(149, 160, 375, 25);
		contentPane.add(textFieldPhone_view);
//===========================================================================================

		// Close Button
		JButton btn_Close_view = new JButton("Close");
		btn_Close_view.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DriverClass.librarysearchlist.setEnabled(true);
				DriverClass.libraryview.setVisible(false);
				DriverClass.librarysearchlist.refreshData(); // Refresh the table data
			}
		});
		btn_Close_view.setForeground(new Color(0, 0, 153));
		btn_Close_view.setFont(new Font("Malgun Gothic", Font.BOLD, 17));
		btn_Close_view.setBounds(335, 284, 95, 25);
		contentPane.add(btn_Close_view);

//===========================================================================================

		// Event Handler for Update Button
		JButton btn_update = new JButton("Update");
		btn_update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query = "UPDATE LIBRARY set LIBRARY_NAME = ?, LIBRARY_ADDRESS = ?, LIBRARY_PHONE = ? where LIBRARY_ID = ?";

					Connection conn = DBconnector.getConnection();
					PreparedStatement preparedStmt = conn.prepareStatement(query);

					preparedStmt.setString(1, textFieldLibraryName_view.getText());
					preparedStmt.setString(2, textFieldAddress_view.getText());
					preparedStmt.setString(3, textFieldPhone_view.getText());
					preparedStmt.setInt(4, Integer.parseInt(textFieldLibraryID_view.getText()));


					int numberOfRowsAffected = preparedStmt.executeUpdate();

					JOptionPane.showMessageDialog(null, numberOfRowsAffected + " rows affected", "UPDATE",
							JOptionPane.PLAIN_MESSAGE);
					preparedStmt.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btn_update.setForeground(new Color(0, 0, 153));
		btn_update.setFont(new Font("Malgun Gothic", Font.BOLD, 17));
		btn_update.setBounds(238, 284, 95, 25);
		contentPane.add(btn_update);

//===========================================================================================
		// Event Handler for Delete
		JButton btn_delete = new JButton("Delete");
		btn_delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query = "DELETE from LIBRARY where LIBRARY_ID = ?";
					Connection conn = DBconnector.getConnection();
					PreparedStatement preparedStmt = conn.prepareStatement(query);
					preparedStmt.setInt(1, Integer.parseInt(textFieldLibraryID_view.getText()));

//===========================================================================================
					// Execute the prepared statement after confirmation
					int dialogButton;
					dialogButton = JOptionPane.showConfirmDialog(null, "Are you sure you wish to delete this itemn ?",
							"Warning", JOptionPane.YES_NO_OPTION);

					if (dialogButton == JOptionPane.YES_OPTION) {
						int numberOfRowsAffected = preparedStmt.executeUpdate();
						JOptionPane.showMessageDialog(null, numberOfRowsAffected + " rows deleted successfully",
								"DELETE", JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (SQLException e6) {
					e6.printStackTrace();
				}
			}
		});
		btn_delete.setForeground(new Color(0, 0, 153));
		btn_delete.setFont(new Font("Malgun Gothic", Font.BOLD, 17));
		btn_delete.setBounds(141, 284, 95, 25);
		contentPane.add(btn_delete);

//===========================================================================================

		// Event Handler for clicking First Record Icon
		JButton btnFirst = new JButton("");
		btnFirst.setIcon(new ImageIcon(FrmLibraryView.class.getResource("/resources/FirstRecord.png")));
		btnFirst.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (rs.first()) {
						updateFieldsFromResultSet();

					} else {
						JOptionPane.showMessageDialog(null, "No further records found", "Empty result",
								JOptionPane.WARNING_MESSAGE);
					}
				} catch (SQLException e2) {
					e2.printStackTrace();
				}
			}
		});
		btnFirst.setBounds(186, 240, 50, 25);
		contentPane.add(btnFirst);

		// Event Handler for clicking Previous Record Icon
		JButton btnPrev = new JButton("");
		btnPrev.setIcon(new ImageIcon(FrmLibraryView.class.getResource("/resources/PreviousRecord.png")));
		btnPrev.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (rs.previous()) {
						updateFieldsFromResultSet();
					} else {
						rs.first();
						updateFieldsFromResultSet();
					}
				} catch (SQLException e3) {
					e3.printStackTrace();
				}
			}
		});
		btnPrev.setBounds(238, 240, 45, 25);
		contentPane.add(btnPrev);

		// Event Handler for clicking Next Record Icon
		JButton btnNext = new JButton("");
		btnNext.setIcon(new ImageIcon(FrmLibraryView.class.getResource("/resources/NextRecord.png")));
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (rs.next()) {
						updateFieldsFromResultSet();
					} else
						rs.last();
					updateFieldsFromResultSet();

				} catch (SQLException e4) {
					e4.printStackTrace();
				}
			}
		});
		btnNext.setBounds(288, 240, 45, 25);
		contentPane.add(btnNext);

		// Event Handler for clicking Last Record Icon
		JButton btnLast = new JButton("");
		btnLast.setIcon(new ImageIcon(FrmLibraryView.class.getResource("/resources/LastRecord.png")));
		btnLast.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (rs.last()) {
						updateFieldsFromResultSet();
					} else {
						JOptionPane.showMessageDialog(null, "No further records found", "Empty result",
								JOptionPane.WARNING_MESSAGE);
					}
				} catch (SQLException e5) {
					e5.printStackTrace();
				}
			}
		});
		btnLast.setBounds(335, 240, 50, 25);
		contentPane.add(btnLast);
//===========================================================================================     
		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setBounds(10, 10, 564, 308);
		contentPane.add(panel);

	}

	// Update the fields based on the current record in the result set
	private void updateFieldsFromResultSet() throws SQLException {
		textFieldLibraryID_view.setText(Integer.toString(rs.getInt("LIBRARY_ID")));
		textFieldLibraryName_view.setText(rs.getString("LIBRARY_NAME"));
		textFieldAddress_view.setText(rs.getString("LIBRARY_ADDRESS"));
		textFieldPhone_view.setText(rs.getString("LIBRARY_PHONE"));
	}
}
