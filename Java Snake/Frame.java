import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.*;

public class  Frame extends JFrame {
    Frame(){
        //Create a Panel
        this.add(new Panel()); 
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);

    }
    
}