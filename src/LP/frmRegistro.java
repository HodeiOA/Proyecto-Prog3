package LP;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

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

public class frmRegistro extends JDialog implements KeyListener
{
	private JTextField tfUsername;
	private JPasswordField pfPassword;
	private JLabel lbUsername;
	private JLabel lbPassword;
    private JButton btnLogin;
    private JButton btnEntrar;
    private JPanel panelUsuario;
    private JPanel panelContraseña;
    private JPanel panelBotonera;

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
        
        //panelUsuario
        panelUsuario= new JPanel();
        lbUsername = new JLabel("Nombre Usuario:");
        panelUsuario.add(lbUsername);
        
		tfUsername = new JTextField ();
		tfUsername.setSize(20, 10);
        panelUsuario.add(tfUsername);
        tfUsername.setColumns(10);
        
        tfUsername.addKeyListener(this);
        
        //panel contraseña
        panelContraseña= new JPanel();
        lbPassword = new JLabel("Contraseña:");
        panelContraseña.add(lbPassword);
        
		pfPassword = new JPasswordField ();
		pfPassword.setSize(20, 10);
        panelContraseña.add(pfPassword);
        pfPassword.setColumns(10);
        
        pfPassword.addKeyListener(this);
        
        //Botones
        panelBotonera= new JPanel();
        
      	btnLogin= new JButton("Registrar"); 
      	panelBotonera.add(btnLogin);
      	
      	btnLogin.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				Registro();
			}
		});
      	
      	btnEntrar = new JButton("Entrar"); 
      	panelBotonera.add(btnEntrar);
      	
      	btnEntrar.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				Entrar();
			}
		});

      	//layout
      	this.getContentPane().setLayout(new BorderLayout());  
      	
      	this.getContentPane().add(panelUsuario,BorderLayout.PAGE_START);	//añadir el panel al otro panel de la ventana que la trae predeter.
      	panelUsuario.setLayout(new FlowLayout());
      	
		this.getContentPane().add(panelContraseña,BorderLayout.CENTER);
      	panelContraseña.setLayout(new FlowLayout());
      	
		this.getContentPane().add(panelBotonera, BorderLayout.PAGE_END);
      	panelBotonera.setLayout(new FlowLayout());
 	}
	

	public void Entrar () 
	{
		String nick;
		String pass;
		HashSet <clsUsuario> usuarios=clsGestor.LeerUsuariosBD();
		
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
					//El nick está en la BD
					//Comprobar que la contraseña es correcta
					for(clsUsuario a: usuarios)
					{ 
						if(a.getNick().equals(nick))
						{
							//El nick existe
							if(a.getContraseña().equals(pass))
							{
								// La contraseña es la correcta
								JOptionPane.showMessageDialog(this,"Bienvenido " + nick + ".", "Usuario correcto", JOptionPane.INFORMATION_MESSAGE);
								 dispose();
							}
						}
					}
					//Repetimos el for, ya que si lo ponemos en un else arriba, solo muestra el mensaje para el primer usuario
					//Si la contraseña era correcta, no se muestra esto, ya que se cierra la ventana, por lo que esto solo pasa si la contraseña 
					//no es correcta
					for(clsUsuario a: usuarios)
					{
						if(a.getNick().equals(nick))
						{
							if(!(a.getContraseña().equals(pass)))
							{
								//la contraseña es incorrecta
								JOptionPane.showMessageDialog(this,"La contraseña introducida no concuerda con la del nick "+ nick + ". Por favor, vuelve a intentarlo", "Contraseña incorrecta", JOptionPane.ERROR_MESSAGE);
							}
						}
					}
				}
				catch (clsNickNoExiste e)
				{
					//El nick no coincide
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
	
	public void Registro() //En cada option pane, poner loggers, info y warning
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

	@Override
	public void keyTyped(KeyEvent e) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) 
	{
		 int key = e.getKeyCode();
		 
		  if (key == KeyEvent.VK_ENTER) 
		  {
			  //lo hace sólo si he pulsado enter en los textfields
			  if (e.getSource()==tfUsername||e.getSource()==pfPassword)
			  {
				  Entrar();
			  }
		  }	
		  if(e.getKeyCode()==KeyEvent.VK_ESCAPE)
		  {
			  //se hará siempre que le de a escape
             System.exit(0);
         }
	}

	@Override
	public void keyReleased(KeyEvent e) 
	{
		// TODO Auto-generated method stub
		
	}
}

