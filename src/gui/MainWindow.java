package gui;

import algo.DijkstraAlgorithm;
import algo.networkflow;
import models.Graph;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import gui.MainWindow;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JPanel {

    private Graph graph;
    private GraphPanel graphPanel;
    private final Action action = new SwingAction();
    private JTextField textField;
    private String Output="";
    private JFrame j = new JFrame("My 2nd Window!");
    private JTextArea txtrOutput;
   
    
    public MainWindow(){
        setLayout(new BorderLayout());
        setGraphPanel();
    }

    private void setGraphPanel(){
        graph = new Graph();
        graphPanel = new GraphPanel(graph);
        graphPanel.setBackground(Color.LIGHT_GRAY);
        graphPanel.setPreferredSize(new Dimension(9000, 4096));

        JScrollPane scroll = new JScrollPane();
        scroll.setViewportView(graphPanel);
        scroll.setPreferredSize(new Dimension(750, 500));
        scroll.getViewport().setViewPosition(new Point(4100, 0));
        add(scroll, BorderLayout.CENTER);
        
        JPanel panel = new JPanel();
        scroll.setRowHeaderView(panel);
        panel.setLayout(new BorderLayout(0, 0));
        
        JScrollPane scrollPane = new JScrollPane();
        panel.add(scrollPane);
        
        txtrOutput = new JTextArea();
        txtrOutput.setEditable(false);
        scrollPane.setViewportView(txtrOutput);
        txtrOutput.setRows(20);
        txtrOutput.setColumns(30);
        
        JLabel lblNewLabel_1 = new JLabel("Output");
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_1.setForeground(new Color(204, 0, 0));
        lblNewLabel_1.setFont(new Font("Arial", Font.BOLD, 16));
        scrollPane.setColumnHeaderView(lblNewLabel_1);
        setTopPanel();
        setButtons();
    }

    private void setTopPanel() {
        JLabel info = new JLabel("Maximum Flow and Dijkstra Algorithms.");
        info.setFont(new Font("Arial", Font.BOLD, 18));
        info.setForeground(new Color(204, 0, 0));
        JPanel panel = new JPanel();
        panel.setBackground(Color.DARK_GRAY);
        panel.add(info);
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        add(panel, BorderLayout.NORTH);
        
        JButton btnNewButton = new JButton("Back");
        btnNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		txtrOutput.setText("");
        		textField.setText("");
        		Output="";
        		graphPanel.DelPath();
        	}
        });
        btnNewButton.setForeground(new Color(204, 0, 0));
        btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 13));
        panel.add(btnNewButton);
    }

    private void setButtons(){
        final JButton info = new JButton();
        info.setForeground(new Color(204, 0, 0));
        info.setFont(new Font("Tahoma", Font.BOLD, 13));
        info.setText("Help");
        JPanel buttonPanel = new JPanel();
        JPanel buttonPanel2 = new JPanel();
        FlowLayout flowLayout_1 = (FlowLayout) buttonPanel.getLayout();
        buttonPanel.setBackground(Color.DARK_GRAY);
        
        JLabel lblNewLabel = new JLabel("Total Flow");
        lblNewLabel.setFont(new Font("Arial", Font.BOLD, 17));
        lblNewLabel.setForeground(new Color(204, 0, 0));
        buttonPanel.add(lblNewLabel);
        
        JButton Dijkstra = new JButton("Run Dijkstra");
        Dijkstra.setForeground(new Color(204, 0, 0));
        Dijkstra.setFont(new Font("Tahoma", Font.BOLD, 13));
        Dijkstra.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    DijkstraAlgorithm dijkstraAlgorithm = new DijkstraAlgorithm(graph);
            		try{
                        dijkstraAlgorithm.run();
                        String Intro="From: "+ graph.getSource().getId() +" To: "+graph.getDestination().getId() +"\n";
                        graphPanel.setPath(dijkstraAlgorithm.getDestinationPath());
                        textField.setText(String.valueOf(dijkstraAlgorithm.getDestinationDistance()));
                        Output+=Intro+dijkstraAlgorithm.getOutput();
                        txtrOutput.setText(Output);

                    } catch (IllegalStateException ise){
                        JOptionPane.showMessageDialog(null, ise.getMessage());
                    }
                    
                   
                    
                }
            });
        
        textField = new JTextField();
        textField.setEditable(false);
        buttonPanel.add(textField);
        textField.setColumns(10);
        buttonPanel.add(Dijkstra);
        
        JButton btnRunMaxflow = new JButton("Run MaxFlow");
        btnRunMaxflow.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		DijkstraAlgorithm dijkstraAlgorithm = new DijkstraAlgorithm(graph);
        		try{
                    dijkstraAlgorithm.run();
                    graphPanel.DelPath();
                    networkflow mine = new networkflow(networkflow.main(graph), graph.getSource().getId()-1, graph.getDestination().getId()-1);
            		int answer = mine.getMaxFlow();
            		String Maxflow="The Maximum flow = "+answer;
            		String Intro="From: "+ graph.getSource().getId() +" To: "+graph.getDestination().getId() +"\n";
            		textField.setText(String.valueOf(answer));
                    Output+=Intro+mine.getOutput()+Maxflow;
                    txtrOutput.setText(Output);
        		}
        		catch (IllegalStateException ise){
                    JOptionPane.showMessageDialog(null, ise.getMessage());
                }
        	}
        });
        btnRunMaxflow.setForeground(new Color(204, 0, 0));
        btnRunMaxflow.setFont(new Font("Tahoma", Font.BOLD, 13));
        buttonPanel.add(btnRunMaxflow);
        JButton reset = new JButton();
        reset.setForeground(new Color(204, 0, 0));
        reset.setFont(new Font("Tahoma", Font.BOLD, 13));
        reset.setText("Reset");
        buttonPanel.add(reset);
        
                reset.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        graphPanel.reset();
                    }
                });
        buttonPanel.add(info);

        info.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,
                        "Click on empty space to create new node\n" +
                        "Drag from node to node to create an edge\n" +
                        "Click on edges to set the weight\n\n" +
                        "Combinations:\n" +
                        "Shift + Left Click       :    Set node as source\n" +
                        "Shift + Right Click     :    Set node as destination\n" +
                        "Ctrl  + Drag               :    Reposition Node\n" +
                        "Ctrl  + Click                :    Get Path of Node\n" +
                        "Ctrl  + Shift + Click   :    Delete Node/Edge\n");
            }
        });

        add(buttonPanel, BorderLayout.SOUTH);
    }


	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
}
