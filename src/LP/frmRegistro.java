package LP;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;

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

import COMUN.clsNickNoExiste;
import COMUN.clsNickRepetido;
import LD.clsBD;
import LN.clsGestor;
import LN.clsUsuario;

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
        btnLogin.setActionCommand("REGISTRAR");
        btnEntrar.addActionListener(this);
        btnEntrar.setActionCommand("ENTRAR");
 	}
	

	public void Entrar () 
	{
		String nick;
		String pass;
		HashSet <clsUsuario> usuarios=clsBD.LeerUsuarios();
		
		nick=tfUsername.getText();
		pass= String.valueOf(pfPassword.getPassword());		
		
			if (!(nick.equals("") || pass.equals(""))) 
			 {
				try
				{
					clsGestor.comprobarExistenciaUsuario(nick, pass);
					
				} 
				catch (clsNickRepetido e)
				{
					//Comprobar que la contraseña es correcta
					for(clsUsuario a: usuarios)
					{
						if(a.getNick().equals(nick))
						{
							if(a.getContraseña().equals(pass))
							{
								JOptionPane.showMessageDialog(this,"Holi " + nick + ". Has entrado correctamente", "Usuario correcto", JOptionPane.INFORMATION_MESSAGE);
								 dispose();
							}
							else
							{
								JOptionPane.showMessageDialog(this,"La contraseña introducida no concuerda con la del nick "+ nick + ". Por favor, vuelve a intentarlo", "Contraseña incorrecta", JOptionPane.ERROR_MESSAGE);
								
							}
						}
					}
					 
				}
				catch (clsNickNoExiste e)
				{
					JOptionPane.showMessageDialog(this, e.getMessage(), "Registro", JOptionPane.ERROR_MESSAGE);
				};
			 } 
			 else 
			 {
				 JOptionPane.showMessageDialog(this, "Usuario o contraseña no introducidos", "Registro", JOptionPane.ERROR_MESSAGE);
			   // reinicio username y password
			   tfUsername.setText("");
	           pfPassword.setText("");
			 }
	}
	public void Registro()
	{
		String nick;
		String pass;

		nick=tfUsername.getText();
		pass= String.valueOf(pfPassword.getPassword());		
		
			if (!(nick.equals("") || pass.equals(""))) 
			 {
				//Como estamos en registro, en este caso existe debe dar false, de lo contrario no puede registarse, pues ya hay alguien con su mismo nick
				try 
				{
					clsGestor.comprobarExistenciaUsuario(nick, pass);
					
				} catch (clsNickRepetido e)
				{
					JOptionPane.showMessageDialog(this, e.getMessage(), "Registro", JOptionPane.ERROR_MESSAGE);
			
				}
				catch (clsNickNoExiste e)
				{
					//En este caso, aunque sea una excepción porque en otro caso es un error, que dé esta excepción es que todo va bien
					JOptionPane.showMessageDialog(this,"¡Registro exitoso!");
					clsGestor.guardarUsuario(pass, nick);
				};
			 } 
			 else 
			 {
				 JOptionPane.showMessageDialog(this, "Usuario o contraseña no introducidos", "Registro", JOptionPane.ERROR_MESSAGE);
			   // reinicio username y password
			   tfUsername.setText("");
	           pfPassword.setText("");
			 }	
		
	}
	
	
	public void actionPerformed(ActionEvent arg0)
	{
		String comando = arg0.getActionCommand(); 
		
		switch(comando)
		{
			case ("ENTRAR") :
				Entrar();
				break;	
			case ("REGISTRAR") :
				Registro();
				break;	
		}		
	}
}

