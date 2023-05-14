package com.Panos.AssignmentFinal;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import java.awt.Color;

public class FrmVersion extends JFrame {
    private static final long serialVersionUID = 1L;

    public FrmVersion() {
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/resources/aueb.jpg")));

        this.setTitle("Έκδοση");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(100, 100, 450, 300);

        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout());
        setContentPane(contentPane);

        // Label Version
        JLabel lblVersion = new JLabel("Έκδοση 0.13");
        lblVersion.setForeground(new Color(0, 0, 204));
        lblVersion.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblVersion.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(lblVersion, BorderLayout.CENTER);

        // Button Close Form - set this form to non-visible
        // Make Main Form enabled again
        JButton btnClose = new JButton("Close");
        btnClose.setFont(new Font("Tahoma", Font.PLAIN, 17));

        btnClose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DriverClass.mainFrame.setEnabled(true);
                DriverClass.version.setVisible(false);

            }
        });
        contentPane.add(btnClose, BorderLayout.SOUTH);
    }

}

