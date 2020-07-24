import gui.MainWindow;
import gui.StartWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {

	public static JFrame j;
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {  }

        j = new StartWindow();
        j.setTitle("Dijkstra and MaxFlow Algorithms");
  
        j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        j.setSize(new Dimension(900, 687));
        j.setVisible(true);

    }
    public void Close()
    {
    	j.setVisible(false);
    }
}





/*

JButton btnNewButton_1 = new JButton("Start Program");
btnNewButton_1.addActionListener(new doneActionListener(this) {
});
btnNewButton_1.setFont(new Font("Arial", Font.BOLD, 14));
btnNewButton_1.setBounds(306, 354, 145, 29);
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
*/