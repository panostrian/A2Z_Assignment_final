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

public class FrmBookView extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PreparedStatement pst;
	private ResultSet rs;

	private JTextField textFieldBookID_view;
	private JTextField textFieldTitle_view;
	private JTextField textFieldYear_view;
	private JTextField textFieldEdition_view;
	private JTextField textFieldAuthor_view;

	public FrmBookView() {
		final JPanel contentPane;

		this.setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/resources/aueb.jpg")));

		setBackground(SystemColor.activeCaption);
		setTitle("View selected Book");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 601, 370);

		contentPane = new JPanel();
		contentPane.setBackground(new Color(240, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		// This form is getting its data from BOOKS table in the DB
		// We have to do a Select not just when Window is Opened but also
		// when the window is Activated (refresh - window opened included)
		// after a delete or update action
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				try {
					String querry = "SELECT BOOK_ID, BOOK_TITLE, BOOK_YEAR, BOOK_EDITION, BOOK_AUTHOR FROM BOOK WHERE BOOK_TITLE LIKE ?";

					Connection conn = DBconnector.getConnection();
					pst = conn.prepareStatement(querry, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
					pst.setString(1, FrmBookSearchList.searchTitle + '%');
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

		JLabel lblBookID_view = new JLabel("ID");
		lblBookID_view.setHorizontalAlignment(SwingConstants.RIGHT);
		lblBookID_view.setForeground(new Color(153, 0, 0));
		lblBookID_view.setFont(new Font("Malgun Gothic", Font.BOLD, 16));
		lblBookID_view.setBounds(47, 37, 60, 25);
		contentPane.add(lblBookID_view);

		JLabel lblTitle_view = new JLabel("Title");
		lblTitle_view.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTitle_view.setForeground(new Color(153, 0, 0));
		lblTitle_view.setFont(new Font("Malgun Gothic", Font.BOLD, 16));
		lblTitle_view.setBounds(50, 77, 60, 25);
		contentPane.add(lblTitle_view);

		JLabel lblYear_view = new JLabel("Year");
		lblYear_view.setHorizontalAlignment(SwingConstants.RIGHT);
		lblYear_view.setForeground(new Color(153, 0, 0));
		lblYear_view.setFont(new Font("Malgun Gothic", Font.BOLD, 16));
		lblYear_view.setBounds(50, 117, 60, 22);
		contentPane.add(lblYear_view);

		JLabel lblEdition_view = new JLabel("Edition");
		lblEdition_view.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEdition_view.setForeground(new Color(153, 0, 0));
		lblEdition_view.setFont(new Font("Malgun Gothic", Font.BOLD, 16));
		lblEdition_view.setBounds(50, 157, 60, 25);
		contentPane.add(lblEdition_view);

		JLabel lblAuthor_view = new JLabel("Author(s)");
		lblAuthor_view.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAuthor_view.setForeground(new Color(153, 0, 0));
		lblAuthor_view.setFont(new Font("Malgun Gothic", Font.BOLD, 16));
		lblAuthor_view.setBounds(30, 197, 80, 25);
		contentPane.add(lblAuthor_view);

		textFieldBookID_view = new JTextField();
		textFieldBookID_view.setEditable(false);
		textFieldBookID_view.setFont(new Font("Malgun Gothic", Font.PLAIN, 16));
		textFieldBookID_view.setColumns(12);
		textFieldBookID_view.setBounds(120, 37, 95, 25);
		contentPane.add(textFieldBookID_view);

		textFieldTitle_view = new JTextField();
		textFieldTitle_view.setColumns(10);
		textFieldTitle_view.setBounds(119, 80, 430, 25);
		contentPane.add(textFieldTitle_view);

		textFieldYear_view = new JTextField();
		textFieldYear_view.setColumns(10);
		textFieldYear_view.setBounds(119, 120, 120, 25);
		contentPane.add(textFieldYear_view);

		textFieldEdition_view = new JTextField();
		textFieldEdition_view.setColumns(10);
		textFieldEdition_view.setBounds(120, 160, 120, 25);
		contentPane.add(textFieldEdition_view);

		textFieldAuthor_view = new JTextField();
		textFieldAuthor_view.setColumns(10);
		textFieldAuthor_view.setBounds(120, 200, 429, 25);
		contentPane.add(textFieldAuthor_view);

//===========================================================================================
		// Close Button
		JButton btn_Close_view = new JButton("Close");
		btn_Close_view.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DriverClass.booksearchlist.setEnabled(true);
				DriverClass.bookview.setVisible(false);
				DriverClass.booksearchlist.refreshData(); // Refresh the table data
			}
		});
		btn_Close_view.setForeground(new Color(0, 0, 153));
		btn_Close_view.setFont(new Font("Malgun Gothic", Font.BOLD, 17));
		btn_Close_view.setBounds(454, 284, 95, 25);
		contentPane.add(btn_Close_view);

//===========================================================================================
		// Event Handler for Update Button
		JButton btn_update = new JButton("Update");
		btn_update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query = "UPDATE BOOK set BOOK_TITLE = ?, BOOK_YEAR = ?, BOOK_EDITION = ? , BOOK_AUTHOR = ?  where BOOK_ID = ?";

					Connection conn = DBconnector.getConnection();
					PreparedStatement preparedStmt = conn.prepareStatement(query);

					preparedStmt.setString(1, textFieldTitle_view.getText());
					preparedStmt.setInt(2, Integer.parseInt(textFieldYear_view.getText()));
					preparedStmt.setInt(3, Integer.parseInt(textFieldEdition_view.getText()));
					preparedStmt.setString(4, textFieldAuthor_view.getText());
					preparedStmt.setInt(5, Integer.parseInt(textFieldBookID_view.getText()));

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
		btn_update.setBounds(290, 284, 95, 25);
		contentPane.add(btn_update);

//===========================================================================================
		// Event Handler for Delete
		JButton btn_delete = new JButton("Delete");
		btn_delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query = "DELETE from BOOK where BOOK_ID = ?";
					Connection conn = DBconnector.getConnection();
					PreparedStatement preparedStmt = conn.prepareStatement(query);
					preparedStmt.setInt(1, Integer.parseInt(textFieldBookID_view.getText()));

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
		btn_delete.setBounds(186, 284, 95, 25);
		contentPane.add(btn_delete);

//===========================================================================================
		// Event Handler for clicking First Record Icon
		JButton btnFirst = new JButton("");
		btnFirst.setIcon(new ImageIcon(FrmBookView.class.getResource("/resources/FirstRecord.png")));
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
		btnPrev.setIcon(new ImageIcon(FrmBookView.class.getResource("/resources/PreviousRecord.png")));
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
		btnNext.setIcon(new ImageIcon(FrmBookView.class.getResource("/resources/NextRecord.png")));
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (rs.next()) {
						updateFieldsFromResultSet();

					} else {
						rs.last();
						updateFieldsFromResultSet();
					}
				} catch (SQLException e4) {
					e4.printStackTrace();
				}
			}
		});
		btnNext.setBounds(288, 240, 45, 25);
		contentPane.add(btnNext);

		// Event Handler for clicking Last Record Icon
		JButton btnLast = new JButton("");
		btnLast.setIcon(new ImageIcon(FrmBookView.class.getResource("/resources/LastRecord.png")));
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

		JPanel panel3 = new JPanel();
		panel3.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel3.setBounds(10, 10, 564, 308);
		contentPane.add(panel3);

	}

	// Update the fields based on the current record in the result set
	private void updateFieldsFromResultSet() throws SQLException {
		textFieldBookID_view.setText(Integer.toString(rs.getInt("BOOK_ID")));
		textFieldTitle_view.setText(rs.getString("BOOK_TITLE"));
		textFieldYear_view.setText(Integer.toString(rs.getInt("BOOK_YEAR")));
		textFieldEdition_view.setText(Integer.toString(rs.getInt("BOOK_EDITION")));
		textFieldAuthor_view.setText(rs.getString("BOOK_AUTHOR"));
	}

}
