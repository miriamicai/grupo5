import javax.swing.JFrame;

public class Login {
     public static void main(String[] a){
        //Creating object of LoginFrame class and setting some of its properties
        
        LoginFrame frame=new LoginFrame();
        frame.setTitle("Login Form");
        frame.setVisible(true);
        frame.setBounds(10,10,500,650);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
 
    }
}
