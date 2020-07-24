package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.sun.glass.events.WindowEvent;

import sun.applet.Main;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;

import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;

public class StartWindow extends JFrame {

	/**
	 * Launch the application.
	 */
	/**
	 * Create the frame.
	 */
	public StartWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 912, 735);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("img/sss.png"));
		lblNewLabel.setBounds(0, 0, 900, 687);
		getContentPane().add(lblNewLabel);
		
		JButton btnNewButton_1 = new JButton("Start");
		btnNewButton_1.addActionListener(new doneActionListener(this) {
		});
		btnNewButton_1.setFont(new Font("Arial", Font.BOLD, 18));
		btnNewButton_1.setBounds(60, 600, 120, 30);
		getContentPane().add(btnNewButton_1);
		
	}
}
class doneActionListener implements ActionListener{
	private JFrame toBeClose;
    public doneActionListener(JFrame toBeClose) {
        this.toBeClose = toBeClose;
    }
    public void actionPerformed(ActionEvent e) {
        JFrame j = new JFrame();
        JPanel panel= new MainWindow();
        j.setTitle("Dijkstra and MaxFlow Algorithms");
        j.add(panel);
        j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        j.setSize(new Dimension(720, 500));
        j.setVisible(true);
    	
    	toBeClose.setVisible(false);
        toBeClose.dispose();
    }
}
