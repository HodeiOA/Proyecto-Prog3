package LP;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import COMUN.clsNickRepetido;
import LN.clsGestor;

public class frmRegistro extends JDialog implements ActionListener
{
	private JTextField tfUsername;
	private JPasswordField pfPassword;
	private JLabel lbUsername;
	private JLabel lbPassword;
    private JButton btnLogin;
    private JButton btnEntrar;
    private boolean succeeded;
    private JPanel panelUsu;
    private JPanel panelCont;
    private JPanel panelBot;

	private int x;
	private int y;

	/**
	 * Constructor de la ventana
	 * @param frame es la ventana principal, frmPrincipal
	 */	
	public frmRegistro (JFrame frame)
	{
		//constructor del JDialog
		
		super (frame,"Login", true);
		//Cambiar
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		//tamaño
        this.setSize(400, 150);
        x=frame.getWidth()/3;
        y=frame.getHeight()/3;
        this.setLocation(x, y);
        setResizable(false);
        
        //panelUsu
        panelUsu= new JPanel();
        lbUsername = new JLabel("Nombre Usuario:");
        panelUsu.add(lbUsername);
        
		tfUsername = new JTextField ();
		tfUsername.setSize(20, 10);
        panelUsu.add(tfUsername);
        tfUsername.setColumns(10);
        
        //panel contraseña
        panelCont= new JPanel();
        lbPassword = new JLabel("Contraseña:");
        panelCont.add(lbPassword);
        
		pfPassword = new JPasswordField ();
		pfPassword.setSize(20, 10);
        panelCont.add(pfPassword);
        pfPassword.setColumns(10);
        
        //Botones
        panelBot= new JPanel();
      	btnLogin= new JButton("Registrar"); 
      	panelBot.add(btnLogin);
      	btnEntrar = new JButton("Entrar"); 
      	panelBot.add(btnEntrar);

      	//layout
      	
      	this.getContentPane().setLayout(new BorderLayout());  
      	
      	this.getContentPane().add(panelUsu,BorderLayout.PAGE_START);	//añadir el panel al otro panel de la ventana que la trae predeter.
      	panelUsu.setLayout(new FlowLayout());
      	
		this.getContentPane().add(panelCont,BorderLayout.CENTER);
      	panelCont.setLayout(new FlowLayout());
      	
		this.getContentPane().add(panelBot, BorderLayout.PAGE_END);
      	panelBot.setLayout(new FlowLayout());
        
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
			 JOptionPane.showMessageDialog(this,"Holi " + nick + "has iniciado sesión correctamente", "Login", JOptionPane.INFORMATION_MESSAGE);
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
		
//		try
//		{
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
//		}
		
//		catch (clsNickRepetido e) 
//		{
//			JOptionPane.showMessageDialog(this,e.getMessage());
//		} 
		
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

