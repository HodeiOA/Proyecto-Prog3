package LP;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.HashSet;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;

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
/**
 * Ventana de diálogo para Login o registro
 */
public class frmRegistro extends JDialog implements KeyListener
{
	private static Logger logger = Logger.getLogger(frmRegistro.class.getName());
	private static Handler handlerPantalla;
	private static Handler handlerArchivo;
	
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
		
		InitLogs();
		
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
	
	/**
	 * Método para gestionar la entrada de un usuario en el programa. Se controlará si el nick y la contraseña del usuario coinciden con 
	 * los de alguno de los usuarios de la BD
	 */
	public static void InitLogs()
	{
		handlerPantalla = new StreamHandler( System.out, new SimpleFormatter() );
		handlerPantalla.setLevel(Level.ALL);
		logger.addHandler(handlerPantalla);
		
		try 
		{
			handlerArchivo = new FileHandler("frmRegistro.log.xml");
			handlerArchivo.setLevel(Level.WARNING);
			logger.addHandler(handlerArchivo);
		} 
		catch (SecurityException e) 
		{
			logger.log(Level.SEVERE, e.getMessage(), e);
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			logger.log(Level.SEVERE, e.getMessage(), e);
			e.printStackTrace();
		}
	}

	/**
	 * Método para gestionar la entrada de un usuario en el programa. Se controlará si el nick y la contraseña del usuario coinciden con 
	 * los de alguno de los usuarios de la BD
	 */
	public void Entrar () 
	{
		logger.log(Level.INFO, "Empezando inicio de sesión");
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
					logger.log(Level.INFO, "El nick está en la base de datos");
					logger.log(Level.INFO, "Procediendo a comprobar si la contraseña es correcta");
					for(clsUsuario a: usuarios)
					{ 
						if(a.getNick().equals(nick))
						{
							logger.log(Level.INFO, "El nick coincide");
							if(a.getContraseña().equals(pass))
							{
								logger.log(Level.INFO, "La contraseña coincide");
								JOptionPane.showMessageDialog(this,"Bienvenido " + nick + ".", "Usuario correcto", JOptionPane.INFORMATION_MESSAGE);
								frmPrincipal.nickUsuarioSesion=nick;
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
								logger.log(Level.INFO, "La contraseña no coincide");
								JOptionPane.showMessageDialog(this,"La contraseña introducida no concuerda con la del nick "+ nick + ". Por favor, vuelve a intentarlo", "Contraseña incorrecta", JOptionPane.ERROR_MESSAGE);
							}
						}
					}
				}
				catch (clsNickNoExiste e)
				{
					logger.log(Level.INFO, "El nick no coincide");
					JOptionPane.showMessageDialog(this, e.getMessage(), "Registro", JOptionPane.ERROR_MESSAGE);
				};
			 } 
			 else 
			 {
				 JOptionPane.showMessageDialog(this, "Usuario o contraseña no introducidos", "Registro", JOptionPane.ERROR_MESSAGE);
				 logger.log(Level.INFO, "Vaciando valores de los campos Username y Password");
			   tfUsername.setText("");
	           pfPassword.setText("");
			 }
	}
	
	/**
	 * Método para gestionar la entrada de un usuario en la BD, comprobando que su nick sea único
	 */
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
				} 
				catch 
				(clsNickRepetido e)
				{
					JOptionPane.showMessageDialog(this, e.getMessage(), "Registro", JOptionPane.ERROR_MESSAGE);
					logger.log(Level.WARNING, e.getMessage());
			
				}
				catch (clsNickNoExiste e)
				{
					//En este caso, aunque sea una excepción porque en otro caso es un error, que dé esta excepción es que todo va bien
					JOptionPane.showMessageDialog(this,"¡Registro exitoso!");
					logger.log(Level.INFO, "El nick no existe. Puede registrarse el usuario.");
					clsGestor.guardarUsuario(pass, nick);
				};
			 } 
			
			 else 
			 {
				 JOptionPane.showMessageDialog(this, "Usuario o contraseña no introducidos", "Registro", JOptionPane.ERROR_MESSAGE);
				 logger.log(Level.WARNING, "Usuario o contraseña no introducidos");
				 logger.log(Level.INFO, "Vaciando valores de los campos Username y Password");
			   tfUsername.setText("");
	           pfPassword.setText("");
			 }	
		
	}

	@Override
	public void keyTyped(KeyEvent e) 
	{
		
	}

	@Override
	/**
	 * Método para habilitar opciones de la pantalla de registro a través de teclado
	 */
	public void keyPressed(KeyEvent e) 
	{
		 int key = e.getKeyCode();
		 
		  if (key == KeyEvent.VK_ENTER) 
		  {
			  logger.log(Level.INFO, "Tecla Enter pulsada");
			  if (e.getSource()==tfUsername||e.getSource()==pfPassword)
			  {
				  Entrar();
			  }
		  }	
		  if(e.getKeyCode()==KeyEvent.VK_ESCAPE)
		  {
			  logger.log(Level.INFO, "Tecla Escape pulsada");
             System.exit(0);
         }
	}

	@Override
	public void keyReleased(KeyEvent e) 
	{
		
	}
}

