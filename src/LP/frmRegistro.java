package LP;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import LN.clsGestor;
import javafx.scene.control.PasswordField;

public class frmRegistro extends JDialog implements ActionListener
{
	private JTextField tfUsername;
	private JPasswordField pfPassword;
	private JLabel lbUsername;
	private JLabel lbPassword;
    private JButton btnLogin;
    private JButton btnEntrar;
    private boolean succeeded;
    private JPanel panel;
	private int x;
	private int y;

	/**
	 * Constructor de la ventana
	 * @param frame
	 */
    
	public frmRegistro (JFrame frame)
	{
		super (frame,"Login", true);
//		//grid
//        GridBagConstraints cs = new GridBagConstraints();
//        GridBagLayout gbl = new GridBagLayout();
//        cs.fill = GridBagConstraints.HORIZONTAL;
        
		//tamaño
        this.setSize(500, 300);
        x=frame.getWidth()/3;
        y=frame.getHeight()/3;
        this.setLocation(x, y);
        
		//panel
		panel=new JPanel(); 
		
		//layout
        panel.setLayout(new FlowLayout());
		
		//Botones
		btnLogin= new JButton("Registrar"); 
		btnEntrar = new JButton("Entrar"); 
		
		//jlabels
        lbUsername = new JLabel("Nombre Usuario:");
        lbPassword = new JLabel("Contraseña:");
		
		//jtextfields
		tfUsername = new JTextField ();
		pfPassword = new JPasswordField ();
	
		//añadir a mi panel y a frmPrincipal
		panel.add(btnEntrar);
		panel.add(btnLogin);
		panel.add(tfUsername);
		panel.add(pfPassword);
		panel.add(lbPassword); 
		panel.add(lbUsername);
		
		this.getContentPane().add(panel); //añadir el panel al otro panel de la ventana que la trae predeter.
		
		//color panel
        panel.setBorder((Border) new LineBorder(Color.GRAY));
        
        //add action listeners
        btnLogin.addActionListener(this);
        btnEntrar.addActionListener(this);
    
	}
	
	public void comprobUser ()
	{
		String nick;
		String pass;

		nick = tfUsername.getText();
		pass = String.valueOf(pfPassword.getPassword());		
		
		if (clsGestor.comprobarExistencia(nick, pass)) //true
		 {
			 JOptionPane.showMessageDialog(this,"Holi " + nick + "has iniciado sesión correctamente!!!", "Login", JOptionPane.INFORMATION_MESSAGE);
			 succeeded = true;
			 //llamar a funcion que realice el registro en LN
			 dispose();
		 } 
		
		 else 
		 {
			 JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos", "Login", JOptionPane.ERROR_MESSAGE);
		   // reinicio username y password
		   tfUsername.setText("");
           pfPassword.setText("");
           succeeded = false;
		 }
	}
	
	public void guardarBDUser ()
	{
		String nick;
		String pass;

		nick=tfUsername.getText();
		pass= String.valueOf(pfPassword.getPassword());		
		
		if (nick!=null || pass!= null ) //true
		 {
			 JOptionPane.showMessageDialog(this,"Holi " + nick + "te has registrado correctamente!!!", "Registro", JOptionPane.INFORMATION_MESSAGE);
			 succeeded = true;
			 dispose();
		 } 
		
		 else 
		 {
			 JOptionPane.showMessageDialog(this, "Usuario o contraseña no introducidos", "Registro", JOptionPane.ERROR_MESSAGE);
		   // reinicio username y password
		   tfUsername.setText("");
           pfPassword.setText("");
           succeeded = false;
		 }
	}
	
	public void actionPerformed(ActionEvent arg0)
	{
		String comando = arg0.getActionCommand(); // qué es?¿
		
		switch(comando)
		{
			case ("Entrar") :
				comprobUser();
				break;	
			case ("Registrar") :
				guardarBDUser();
				break;	
		}		
	}

	
}

