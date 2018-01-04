package LP;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;
import java.util.regex.Pattern;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.jpedal.exception.PdfException;

import LD.clsBD;
import LD.clsProperties;
import LN.clsArchivo;
import LN.clsComentario;
import LN.clsGestor;
import LP.frmPrincipal.hiloHabilitarBorrado;
import LP.frmPrincipal.hiloNick;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.SystemColor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;

public class frmPrincipal extends JFrame implements ActionListener, ChangeListener, ListSelectionListener
{
	//Logger
	private static Logger logger = Logger.getLogger( frmPrincipal.class.getName() );
	static Handler handlerPantalla;
	static Handler handlerArchivo;
	
	private int altura=0;
	private int anchura=0;
	private int x=0;
	private int y=0;
	private Dimension screenSize;
	private Toolkit mipantalla;
	
	public static String nickUsuarioSesion="";
	private hiloNick hiloNick;
	private hiloHabilitarBorrado hiloHabilitar;
	
	//MEN�
	private JMenuBar menuBar=new JMenuBar();
	private JMenu archivo=new JMenu("Archivo");
	private JMenu importarArchivo=new JMenu("Importar archivo");
	private JMenu importaCarpeta=new JMenu("Importar carpeta");
	private JMenuItem libro=new JMenuItem("Libro");
	private JMenuItem documento=new JMenuItem("Documento");
	private JMenuItem libroC=new JMenuItem("Libro");
	private JMenuItem documentoC=new JMenuItem("Documento");
	private JMenuItem BorrarArchivo= new JMenuItem ("Eliminar archivo");
	private JMenu Mcom =new JMenu("Comentarios");
	private JMenuItem ExportarComentarios = new JMenuItem( "Exportar comentarios"); //Hacer que cuando se est�n viendo los comentarios pase a poner ocultar comentarios
	// JMenu de sesi�n: cerrar sesi�n
		
	//Para el men� que se despliega a al hacer click derecho sobre un elemento de la lista
	JPopupMenu popup;
	JMenuItem Mdetalles = new JMenuItem("Informaci�n del archivo");
	JMenuItem Meliminar = new JMenuItem("Eliminar archivo");
	
	//Paneles
	private static JTabbedPane panelListas= new JTabbedPane(); //Panel de pesta�as
	private static JPanel PLibros=new JPanel(); //Panel dentro de la pesta�a libros
	private JPanel PDocum=new JPanel(); //Panel dentro de la pesta�a documentos
	static clsPanelPDF PanelPDF = new clsPanelPDF();
	private JPanel Pinferior= new JPanel();
	
	
	//Botones
	JButton Banterior= new JButton("<<"); //mirar taller bicis
	JButton Bsiguiente= new JButton(">>"); 
	JButton btnA�adir;

	//Labels
	JLabel lbNuevoComent;
	JLabel lbComentariosAntiguo;

	//Marcador de p�gina, cambiante seg�n los botones de arriba y/o el slider
	static JTextArea numPag = new JTextArea ();
	static String indicadorPaginas; //Cada vez que cambiemos la p�gina, cambiaremos el String
	
	//Otros compontentes
	static JProgressBar progreso=new JProgressBar();
	static JSlider slider=new JSlider ();
	JButton AddLibro = new JButton("Importar libro");
	JButton AddDoc = new JButton ("Importar documento");
	
	//Comentarios
	private boolean comentarios = true;
	private static JTextPane TextPaneComentarioNuevo = new JTextPane(); 
	private static JPanel PcomentarioNuevo= new JPanel();
	private static JPanel PcomentariosViejos= new JPanel();
	private JPanel Pcomentarios = new JPanel();
	private JScrollPane ScrollCViejos = new JScrollPane ();
	private static HashSet<clsComentario> HashComentarios = new HashSet<clsComentario>();
	private static ArrayList<JTextPane> listTextPanes = new ArrayList<JTextPane>();
	private static ArrayList<clsComentario> listComentariosVisibles = new ArrayList<clsComentario>();
	private static ArrayList<JLabel> listLabeles = new ArrayList<JLabel>();
	
	//Para el Listmodel
	static HashSet <clsArchivo> HashArchivos = new HashSet<clsArchivo>();
	static HashSet <clsArchivo> HashLibros = new HashSet<clsArchivo>();
	static HashSet <clsArchivo> HashDocumentos = new HashSet<clsArchivo>();
	static modelArchivos modelLibros;
	static modelArchivos modelDocumentos;
	
	//Listas para libros/documentos
	static JList ListLibros;
	static JList ListDoc;
	JScrollPane scrollListLibros = new JScrollPane();
	JScrollPane scrollListDoc = new JScrollPane();

	//JFileChooser:
	private JFileChooser chooser;
	private String path;
	private String ultimaCarpeta;
	private static Pattern filtro;
	
	//Para guardar propiedades
	static Properties misProps=new Properties();
	static ArrayList <String> ClavesPropiedades = new ArrayList();
	int[] AnchuraAltura = new int[2];
	int[] locationXY = new int[2];
	
	//Para saber si algo est� siendo o no mostrado en el panel del PDF
	static boolean PDFactivo=false;
	
	//para el zoom
	private static JLabel cantidadDeZoom;
    private static JButton minus;
    private static JButton plus;
    private static JSlider sliderZoom;

    private static final int MIN_ZOOM = 10;
    private static final int MAX_ZOOM = 200;
    private static final int DEFAULT_ZOOM = 100;

    private static final int MAJOR_ZOOM_SPACING = 50;
    private static final int MINOR_ZOOM_SPACING = 10;
    
    //para la rotaci�n
    private static JLabel lRotacion;
    private static JButton btnRotar;
    private static ImageIcon iRotar;
    private static int rotacionValor;
		
    
	public frmPrincipal (String titulo)
	{
		InitLogs();
		
		ClavesPropiedades.add("anchura");
		ClavesPropiedades.add("altura");
		ClavesPropiedades.add("X");
		ClavesPropiedades.add("Y");
		setTitle(titulo);
		
		CargarDatos();
		//Leer el xml que guarda el tama�o de la ventana y meter los datos en altura y anchura
		//Este if lo tendremos que hacer, pero lo comento hasta que podamos leer los valores
		anchura=Integer.parseInt(misProps.getProperty(ClavesPropiedades.get(0)));
		altura=Integer.parseInt(misProps.getProperty(ClavesPropiedades.get(1)));

		//tama�o pantalla
		mipantalla=Toolkit.getDefaultToolkit();
		screenSize=mipantalla.getScreenSize();
		
		if(altura==0 && anchura==0)
		{
			altura=screenSize.height;
			anchura=screenSize.width;
		}
		
		setSize(anchura, altura);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		x=Integer.parseInt(misProps.getProperty(ClavesPropiedades.get(2)));
		y=Integer.parseInt(misProps.getProperty(ClavesPropiedades.get(3)));
		
		setLocation(x, y);		
		
		//Men�
		//Construcci�n del men�
		setJMenuBar(menuBar);
		menuBar.add(archivo);
				
		archivo.add(importarArchivo);
		importarArchivo.add(libro);
		importarArchivo.add(documento);
		
		archivo.add(importaCarpeta);
		importaCarpeta.add(libroC);
		importaCarpeta.add(documentoC);
		
		archivo.add(BorrarArchivo);
		BorrarArchivo.setEnabled(false); //Esto lo cambiaremos cuando tenga algo seleccionado en la lista. Mientras tanto, false
		
		menuBar.add(Mcom);
		Mcom.add(ExportarComentarios);
		
		//Como son varios y son parecidos, los listeners del men� se har�n con Action Commands
		libro.setActionCommand("IMPORTAR_LIBRO");
		libro.addActionListener(this);
		
		documento.setActionCommand("IMPORTAR_DOCUMENTO");
		documento.addActionListener(this);
		
		libroC.setActionCommand("CARPETA_LIBRO");
		libroC.addActionListener(this);
		
		documentoC.setActionCommand("CARPETA_DOCUMENTO");
		documento.addActionListener(this);
		
		BorrarArchivo.setActionCommand("BORRAR");
		BorrarArchivo.addActionListener(this);
		
		ExportarComentarios.setActionCommand("EXPORTAR");
		ExportarComentarios.addActionListener(this);
		
		//Layout para el panel
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		//A�adir los listModel
		ListLibros=new JList(modelLibros);
		ListDoc=new JList (modelDocumentos);
		
		
		//A�adir los Listeners a las listas para la selecci�n del archivo
		MouseListener mouseListener = new MouseAdapter() 
		{
		    public void mouseClicked(MouseEvent e)
		    {
		        if (e.getClickCount() == 2)
		        {
		          clsArchivo seleccion = (clsArchivo) ((JList)e.getSource()).getSelectedValue(); 
			      logger.log(Level.INFO, "La ruta del archivo seleccionado: " + seleccion.getRuta());
		          SeleccionListas(seleccion);
		        }
		        BorrarArchivo.setEnabled(true);
		    }
		    
		  //Para el PoupUp
		    public void mousePressed(MouseEvent arg0)
		    {
              if (arg0.getButton() == MouseEvent.BUTTON3) //Bot�n derecho
              {
                    popup.show((JList) arg0.getSource(),arg0.getX(), arg0.getY());
                    int index = ((JList) arg0.getSource()).locationToIndex(arg0.getPoint());
                                          
                    ((JList) arg0.getSource()).setSelectedIndex(index);
              } 
            }
		};
		
		ListLibros.addMouseListener(mouseListener);
		ListDoc.addMouseListener(mouseListener);
			
		//Panel Para libros/Documentos
	
		ImageIcon icon = null;
		getContentPane().add(panelListas, BorderLayout.WEST);
		
		PLibros.setLayout(new BorderLayout(0, 0));
		
		PDocum.setLayout(new BorderLayout(0, 0));
		
		PLibros.add(AddLibro,BorderLayout.NORTH); 
		PLibros.add(ListLibros,BorderLayout.CENTER);
		
		PDocum.add(AddDoc,BorderLayout.NORTH); 	
		PDocum.add(ListDoc,BorderLayout.CENTER);
		
		scrollListLibros = new JScrollPane(PLibros);

		scrollListDoc = new JScrollPane(PDocum);
		
		//Le a�adimos el Listener a AddLibros
		AddLibro.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				SeleccionarArchivo(true);
			}
		});
		
//		//Le a�adimos el Listener a AddDocumento
				AddDoc.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						SeleccionarArchivo(false);
					}
				});

		panelListas.setPreferredSize(new Dimension(225, 40));	// esto a hodei le va?
		panelListas.addTab("Libros",icon,scrollListLibros, "Lista de libros agregados");
		panelListas.addTab("Documentos",icon,scrollListDoc, "Lista de documentos agregados");
	
		//Panel para la visualizaci�n del PDF
		getContentPane().add(PanelPDF, BorderLayout.CENTER);
		Pinferior.setBackground(SystemColor.inactiveCaption);
		
		//Panel para manipular el PDF
		getContentPane().add(Pinferior, BorderLayout.SOUTH);
		
		//prepara el texto del slider
		slider= new JSlider(JSlider.HORIZONTAL );
		slider.setPaintLabels(true); //si se ve los n�meros del slider
		slider.setBackground(SystemColor.inactiveCaption);
		slider.setMinimum(1);
		slider.setEnabled(PDFactivo); 	//false
		
		//A�adirle al slider el listener
		  slider.addChangeListener(new ChangeListener()
		  {
		      public void stateChanged(ChangeEvent event)
		      {
		        int value = slider.getValue();
		        //Mirar cu�l es la lista seleccionada y sacar el lemento seleccionado para MOSTRARCOMENTARIOS
		        PanelPDF.irAPag(value);
		        ActualizarComponentes();
		      }
		    });
		  
		Pinferior.add(progreso);
		Pinferior.add(Banterior);
		
		Banterior.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				PanelPDF.PagAnt();	
				ActualizarComponentes();//Mirar cu�l es la lista seleccionada y sacar el lemento seleccionado para MOSTRARCOMENTARIOS
			}
			
		});
		
		Pinferior.add(slider);
		Pinferior.add(Bsiguiente);
		
		Bsiguiente.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				PanelPDF.SigPag();	
				ActualizarComponentes();//Mirar cu�l es la lista seleccionada y sacar el lemento seleccionado para MOSTRARCOMENTARIOS
			}
		});
		
		Pinferior.add(numPag);
		Pinferior.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		
		//--------------Panel para comentarios-------------//
		
		double tama�oPanelC = screenSize.height/2.7;
		
		btnA�adir = new JButton ("A�adir");
		lbNuevoComent = new JLabel("Nuevo comentario");
		lbComentariosAntiguo = new JLabel("Comentarios anteriores");
		lbComentariosAntiguo.setBorder(new EmptyBorder(0,35,0,0));
		
		Pcomentarios.setPreferredSize(new Dimension(225,screenSize.height));
		Pcomentarios.setBackground(Color.WHITE);
		
		PcomentarioNuevo.setPreferredSize(new Dimension(225,(int)tama�oPanelC));
		PcomentarioNuevo.setBackground(Color.lightGray);
		
		TextPaneComentarioNuevo.setPreferredSize(new Dimension(200, 200));
		TextPaneComentarioNuevo.setEditable(PDFactivo);
		
		PcomentariosViejos.setPreferredSize(new Dimension(215,(int)(tama�oPanelC*1.15)));
		PcomentariosViejos.setBackground(Color.lightGray);
		
		ScrollCViejos = new JScrollPane(PcomentariosViejos);
		
		PcomentarioNuevo.add(lbNuevoComent);
		PcomentarioNuevo.add(TextPaneComentarioNuevo);
		PcomentarioNuevo.add(btnA�adir);
		
		ScrollCViejos.setPreferredSize(new Dimension(215,(int)(tama�oPanelC*1.15)));
		
		Pcomentarios.add(PcomentarioNuevo, BorderLayout.NORTH);
		Pcomentarios.add(lbComentariosAntiguo);
		Pcomentarios.add(ScrollCViejos, BorderLayout.SOUTH);
		
		this.getContentPane().add(Pcomentarios, BorderLayout.EAST);
			
		btnA�adir.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent arg0) 
				{	
					if(PDFactivo)
					{
						//Identificar cu�l es el archivo en curso del que se ha hecho el comentario
						for(clsArchivo a: HashArchivos)
						{
							if(a.equals(PanelPDF.getPDFabierto()))
							{
								if(!(TextPaneComentarioNuevo.getText().isEmpty()))
								{	
									//GuardarComentario
									clsComentario coment = new clsComentario(TextPaneComentarioNuevo.getText(),a.getCodArchivo(), PanelPDF.getPagActual(), false, 0);
																		
									clsGestor.guardarComentario(coment.getID(), coment.getTexto(), coment.getCodArchivo(), coment.getNumPagina());

									//Crear de nuevo los comentarios
									MostrarComentarios();
								}
							}
						}
						TextPaneComentarioNuevo.setText("");
					}
				}
			});
			
			// Construccion del JPopupMenu para el click derecho
			popup = new JPopupMenu();
			popup.add(Mdetalles);
			popup.add(Meliminar);
			
			Mdetalles.addActionListener(this);
			Meliminar.addActionListener(this);
			Mdetalles.setActionCommand("DETALLES");
			Meliminar.setActionCommand("ELIMINAR");					
				
			//zoomBar construcci�n + rotaci�n
			minus = new JButton("-");
	        plus = new JButton("+");
	        sliderZoom = new JSlider(MIN_ZOOM, MAX_ZOOM, DEFAULT_ZOOM);
	        
	        btnRotar = new JButton();
	        rotacionValor = 0;
			lRotacion = new JLabel("Rotar: "+ rotacionValor +"�"); 

	        sliderZoom.setMinorTickSpacing(MINOR_ZOOM_SPACING);
	        sliderZoom.setMajorTickSpacing(MAJOR_ZOOM_SPACING);
	        sliderZoom.setPaintTicks(true);
	        sliderZoom.setSnapToTicks(true);
	        
	        cantidadDeZoom = new JLabel(sliderZoom.getValue() + "%");
	        cantidadDeZoom.setBorder(BorderFactory.createEmptyBorder(0,(int) ((int)screenSize.width/4),0,0));
			
	        menuBar.add(cantidadDeZoom);
	        menuBar.add(minus);
	        menuBar.add(sliderZoom);
	        menuBar.add(plus);
	        
	    	//preparar el slider zoom y el bot�n de rotar
			sliderZoom.setEnabled(PDFactivo); 	//false
			plus.setEnabled(PDFactivo);
			minus.setEnabled(PDFactivo);
			btnRotar.setEnabled(PDFactivo);
	        
	        //a�ado sus listeners al zoom
	        
	        plus.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					sliderZoom.setValue(sliderZoom.getValue()+MINOR_ZOOM_SPACING);
					PanelPDF.zoom(sliderZoom.getValue()/100f, PanelPDF.getPagActual(),rotacionValor);
					System.out.println("GET VALUE SLIDERZOOM:" + sliderZoom.getValue());
				}   
				});
	        
	        minus.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					sliderZoom.setValue(sliderZoom.getValue()-MINOR_ZOOM_SPACING);
		            PanelPDF.zoom(sliderZoom.getValue()/100f, PanelPDF.getPagActual(),rotacionValor);
					System.out.println("GET VALUE SLIDERZOOM:" + sliderZoom.getValue());
				}   
				});
	        
	        sliderZoom.addChangeListener(new ChangeListener()
			{
				public void stateChanged(ChangeEvent e) 
				{
					if (sliderZoom.getValueIsAdjusting()) 
					{
			            PanelPDF.zoom(sliderZoom.getValue()/100f, PanelPDF.getPagActual(),rotacionValor);
						return;
			        }
			        cantidadDeZoom.setText(sliderZoom.getValue() + "%");			
			    }   
				});	
			
	        //a�adir icono al bot�n de girar
	      
			lRotacion.setBounds(20, 0, 100, 16);
	        lRotacion.setBorder(BorderFactory.createEmptyBorder(0,0,0,(int) ((int)screenSize.width/3)));
	        
	        try 
			{
				ImageIcon iconRotar = new ImageIcon(frmPrincipal.class.getResource ( "../images/rotar.png"));
				Image scaleImage = iconRotar.getImage().getScaledInstance(15, 16,Image.SCALE_DEFAULT);
				btnRotar.setIcon( new ImageIcon(scaleImage));
			} 
			catch (Exception e) 
			{
				//logger
				System.err.println( "Error en carga de recurso: rotar.png no encontrado" );
				e.printStackTrace();
			}
	    	
	        menuBar.add(btnRotar);
	        menuBar.add(lRotacion);
	        
	        //listeners
	        
	        btnRotar.addActionListener(new ActionListener()
	   			{
	   				public void actionPerformed(ActionEvent e)
	   				{
	   					rotacionValor = rotacionValor + 90;
	   						   					
	   					if(rotacionValor==360)
	   					{
	   						rotacionValor = 0;					
	   					}

	   					PanelPDF.rotar(rotacionValor, PanelPDF.getPagActual());
 	   					
	   					lRotacion.setText("Rotar: "+ rotacionValor +"�");	//si quito esto no funciona y no lo entiendo
	   				}   
	   				});
	              
		this.addComponentListener(new ComponentListener()
		{
			@Override
			public void componentHidden(ComponentEvent arg0) 
			{
				
			}

			@Override
			public void componentMoved(ComponentEvent arg0) 
			{
				
			}

			@Override
			public void componentResized(ComponentEvent arg0)
			{
				AnchuraAltura[1]= (int)(getHeight());
				AnchuraAltura[0]= (int)(getWidth());
				clsProperties.CambiarPropiedades(misProps, AnchuraAltura, locationXY, ClavesPropiedades);
			}
			
			@Override
			public void componentShown(ComponentEvent arg0) 
			{
				
			}
		});
		
		this.addWindowListener(new WindowListener()		
		{

			@Override
			public void windowActivated(WindowEvent arg0) 
			{	
				
			}

			@Override
			public void windowClosed(WindowEvent arg0) 
			{
				
			}

			@Override
			public void windowClosing(WindowEvent arg0) 
			{
				if(PDFactivo) PanelPDF.GuardarDatosPDFAnterior();
				clsBD.close();
					locationXY[0]=(int) (getLocation().getX());
					locationXY[1]=(int)(getLocation().getY());

					clsProperties.CambiarPropiedades(misProps, AnchuraAltura, locationXY, ClavesPropiedades);
					clsProperties.Guardarpropiedades(misProps);
			}

			@Override
			public void windowDeactivated(WindowEvent arg0)
			{
				
			}

			@Override
			public void windowDeiconified(WindowEvent arg0) 
			{
				
			}

			@Override
			public void windowIconified(WindowEvent arg0) 
			{
				
			}

			@Override
			public void windowOpened(WindowEvent arg0) 
			{
				
			}
		});
		hiloNick = new hiloNick();
		hiloHabilitar = new hiloHabilitarBorrado();
		
		hiloNick.start();
		hiloHabilitar.start();
	}
	
	public static void InitLogs()
	{
		//---gesti�n de Loggs:---			
		//Por pantalla mostraremos todos los niveles. Al xml solo SEVERE y WARNING
		handlerPantalla = new StreamHandler( System.out, new SimpleFormatter() );
    	handlerPantalla.setLevel( Level.ALL );
    	logger.addHandler( handlerPantalla );
    	
    	try 
    	{
			handlerArchivo = new FileHandler ( "frmPrincipal.log.xml");
			handlerArchivo.setLevel(Level.WARNING);
			logger.addHandler(handlerArchivo);
		}
    	catch (SecurityException e1) 
    	{
    		logger.log(Level.SEVERE, e1.getMessage(), e1);
			e1.printStackTrace();
		} 
    	catch (IOException e1)
    	{
			logger.log(Level.SEVERE, e1.getMessage(), e1);
			e1.printStackTrace();
		}
	}
	
	public static void ActualizarComponentes()
	{
		if(PDFactivo)
		{
			logger.log(Level.INFO, "Actualizando Slider y texto del panel inferior");
			
			//Prepara el texto del slider
			int max = PanelPDF.getPaginasTotal();
			int PaginasActual = PanelPDF.getPagActual();
					slider.setMaximum(max);
					slider.setValue(PaginasActual);
					slider.setPaintTicks(true);//las rayitas que marcan los n�meros
					slider.setMajorTickSpacing(PanelPDF.getPaginasTotal()-1); // de cuanto en cuanto los n�meros en el slider
					//slider.setMinorTickSpacing(PanelPDF.getPaginasTotal()/3); //las rayitas de cuanto en cuanto
					slider.setPaintLabels(true); //si se ven los n�meros del slider o no
					slider.setBackground(SystemColor.inactiveCaption);
					slider.setEnabled(PDFactivo);
					
			//Prepara el texto de los n�meros de p�gina
					indicadorPaginas = ""+ PanelPDF.getPagActual() +" / " + PanelPDF.getPaginasTotal();
					numPag.setText(indicadorPaginas);
					numPag.setEditable(false);
					numPag.setBackground(SystemColor.inactiveCaption);
					
			//Prepara el progress bar
					int intProgress = clsGestor.porcentLeido(PanelPDF.getPDFabierto());
					progreso.setStringPainted(true);
					progreso.setValue((intProgress));
					progreso.setString(intProgress + "%");
			
			//zoom
					sliderZoom.setEnabled(PDFactivo);
					plus.setEnabled(PDFactivo);
					minus.setEnabled(PDFactivo);
					cantidadDeZoom.setText(sliderZoom.getValue() + "%");
					
			//rotaci�n
					btnRotar.setEnabled(PDFactivo);
					lRotacion.setText("Rotar: "+ rotacionValor +"�");
					
					MostrarComentarios();
					
					TextPaneComentarioNuevo.setEditable(PDFactivo);
		}		
	}
	
	/**
	 * Abre el archivo seleccionado en la lista
	 * @param elegido
	 */
	public static void SeleccionListas(clsArchivo elegido)
	{
		PanelPDF.abrirPDF(elegido);
		rotacionValor = 0;
		sliderZoom.setValue(100);
		PDFactivo=true;
		ActualizarComponentes();
	}
	
	public static void MostrarComentarios()
	{
		int cont = 0;
		
		HashComentarios = clsGestor.LeerComentariosBD();
		listComentariosVisibles.clear();
		
		for(JTextPane auxPane: listTextPanes)
		{
			PcomentariosViejos.remove(auxPane);
		}
		for(JLabel auxLabel: listLabeles)
		{
			PcomentariosViejos.remove(auxLabel);
		}
		
		PcomentariosViejos.repaint();
		listTextPanes.clear();
		listLabeles.clear();
		
		ImageIcon iconoBorrar = new ImageIcon(frmPrincipal.class.getResource("../images/Borrar.png"));
		
		logger.log(Level.INFO, "A�adiendo comentarios al panel de comentarios");
		
		for(clsComentario c: HashComentarios)
		{
			if(c.getCodArchivo() == PanelPDF.getPDFabierto().getCodArchivo())
			{
				if(c.getNumPagina() == PanelPDF.getPagActual())
				{
					cont++;
					
					JLabel label = new JLabel(iconoBorrar);
					label.setBorder(new EmptyBorder(20,1,1,160));
					label.addMouseListener(new MouseListener()
							{

								@Override
								public void mouseClicked(MouseEvent e) 
								{
									int index = listLabeles.indexOf(e.getSource());
									
									PcomentariosViejos.remove(listTextPanes.get(index));
									PcomentariosViejos.remove(listLabeles.get(index));
									PcomentariosViejos.repaint();
									
									clsGestor.BorrarObjetoBD(listComentariosVisibles.get(index).getID(), "COMENTARIO");
									
									listTextPanes.remove(index);
									listLabeles.remove(index);
									listComentariosVisibles.remove(index);
								}

								@Override
								public void mousePressed(MouseEvent e) {}

								@Override
								public void mouseReleased(MouseEvent e) {}

								@Override
								public void mouseEntered(MouseEvent e) {}

								@Override
								public void mouseExited(MouseEvent e) {}
						
							});
					
					JTextPane pane = new JTextPane();
					pane.setPreferredSize(new Dimension(200, 200));
					pane.setEditable(true);
					pane.addFocusListener(new FocusListener()
							{
								@Override
								public void focusGained(FocusEvent e) {}
		
								@Override
								public void focusLost(FocusEvent e) 
								{
									JTextPane txtPane = (JTextPane) e.getComponent();
									String texto = txtPane.getText();
									int index = listTextPanes.indexOf(txtPane);
									
									clsComentario coment = listComentariosVisibles.get(index);
									coment.setTexto(texto);
									clsGestor.ModificarComentario(coment.getID(), coment.getCodArchivo(), coment.getTexto(), coment.getNumPagina());
								}
							});
					
					listLabeles.add(label);
					listTextPanes.add(pane);		
					listComentariosVisibles.add(c);
					
					pane.setText(c.getTexto());
					
					PcomentariosViejos.add(label);
					PcomentariosViejos.add(pane);
					
					logger.log(Level.INFO, "Comentario " + c.getID() + " a�adido");
				}
			}
		}
		PcomentariosViejos.repaint();
		logger.log(Level.INFO,"Comentarios a�adidos: " + cont);
	}
	
	/**
	 * En este m�todo cargamos los datos, en este caso la lista de pel�culas.
	 */
	public static void CargarDatos()
	{
		clsProperties.CargarProps(misProps, ClavesPropiedades);	
		HashArchivos = clsGestor.LeerArchivosBD();
		clsGestor.llenarLibrosDocum (nickUsuarioSesion, HashArchivos, HashLibros, HashDocumentos);

		modelLibros = new modelArchivos(HashLibros);
		modelDocumentos = new modelArchivos(HashDocumentos);
	}
	
	public static void ActualizarListas()
	{
		modelLibros.setLista(HashLibros);
		modelDocumentos.setLista(HashDocumentos);
	}
	
	/**
	 * M�todo para recoger/guardar un solo PDF con filtro de PDFs
	 */
	public void SeleccionarArchivo(boolean esLibro)
	{
		String Titulo;
		
		chooser = new JFileChooser();

		chooser.setApproveButtonText("Importar");
		
		if(esLibro)
		{
			logger.log(Level.INFO, "Seleccionando libro");
			chooser.setDialogTitle("Importar libro");
		}
		
		else
		{
			logger.log(Level.INFO, "Seleccionando documento");
			chooser.setDialogTitle("Importar documento");
		}
		
		chooser.setAcceptAllFileFilterUsed(false); 
		
		//Filtrar extensiones de archivos
		FileNameExtensionFilter filtro = new FileNameExtensionFilter("*.PDF", "pdf");
		chooser.setFileFilter(filtro);
		
		int response = chooser.showOpenDialog(this);
		
		if(response == JFileChooser.APPROVE_OPTION)
		{
			path = chooser.getSelectedFile().getPath();
		
			Titulo = clsGestor.RecogerTitulo(HashArchivos, path);
			
			clsArchivo nuevoArchivo;
			try 
			{
				nuevoArchivo = new clsArchivo (nickUsuarioSesion, "Autor", "Apellido", Titulo, path, clsGestor.conseguirNumPags(path), 1, 0, esLibro, false, 0);
				HashArchivos.add(nuevoArchivo);
				CopiarArchivo(path, nuevoArchivo);
				PanelPDF.abrirPDF(nuevoArchivo);
				
				rotacionValor = 0;
				sliderZoom.setValue(100);
				PDFactivo=true;
				
				ActualizarComponentes();
				MostrarComentarios();
				clsGestor.guardarArchivo(nuevoArchivo.getNick(), nuevoArchivo.getNomAutor(), nuevoArchivo.getApeAutor(), nuevoArchivo.getCodArchivo(), nuevoArchivo.getTitulo(), nuevoArchivo.getRuta(), nuevoArchivo.getNumPags(), nuevoArchivo.getUltimaPagLeida(), nuevoArchivo.getTiempo(), nuevoArchivo.getLibroSi());
				
				if(esLibro)
				{
					HashLibros.add(nuevoArchivo);
				} 
				else
				{
					HashDocumentos.add(nuevoArchivo);
				}
				
				ActualizarListas();
			} 
			catch (PdfException e) 
			{
				
			}
		}
	}
	
	public void SeleccionarArchivosDeCarpeta(boolean esLibro)
	{
		String carp = ultimaCarpeta;  
		
		//Elegimos d�nde abrir el chooser
		if (ultimaCarpeta==null) carp = System.getProperty("user.dir");
		File dirActual = new File( carp );
		chooser = new JFileChooser( dirActual );
		chooser.setApproveButtonText("Importar");
		
		//Elegimos qu� chooser abrir
		if(esLibro)
		{
			logger.log(Level.INFO, "Seleccionando carpeta de libros");
			chooser.setDialogTitle("Importar carpeta libros");
		}
		else
		{
			logger.log(Level.INFO, "Seleccionando carpeta de documentos");
			chooser.setDialogTitle("Importar carpeta documentos");
		}
		//Hacemos que solo pueda seleccionar carpetas
		chooser.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );
		
		int response = chooser.showOpenDialog(this);
		
		if (response == JFileChooser.APPROVE_OPTION)
		{
			//cambiamos el valor de ultimaCarpeta
			ultimaCarpeta=chooser.getSelectedFile().getPath();
			RecursividadCarpeta(ultimaCarpeta, esLibro);
			//Creamos un nuevo archivo por cada pdf que haya en el directorio
			//Para ello, primero vemos si estamos en una carpeta, por lo que la primera vez siempre entrar�			
		} 
		ActualizarListas();
	}
	
	public static void RecursividadCarpeta(String path, boolean esLibro)
	{
		File fic = new File (path);
		
		if (fic.isDirectory()) 
		{
			for( File f : fic.listFiles() ) 
			{
				/*Por cada fichero en Fic, volveremos a llamar a este m�todo hasta llegar al interior de una carpeta en la que no haya
				*m�s, por lo que pasar� por todos los ficheros en el interior de la carpeta
				**/
				RecursividadCarpeta(f.getPath(), esLibro);
			}
		} 
		else
		{ 
			//En este caso recursivo, el caso en el que NO es una carpeta ser� el caso base
			//Es decir, el algoritmo recursivo llear� hasta la carpeta del fin de la ruta de carpetas 
			//En ese momento, a�adir� todos los PDFs en su interior
			filtro = Pattern.compile(".*\\.pdf");
			if (filtro.matcher(fic.getName()).matches() )
			{
				// Si cumple el patr�n, se a�ade
				path = fic.getPath();
				String Titulo = clsGestor.RecogerTitulo(HashArchivos, path);
				try 
				{
					clsArchivo nuevoArchivo = new clsArchivo (nickUsuarioSesion, "Autor", "Apellido", Titulo, path, clsGestor.conseguirNumPags(path), 1, 0, esLibro, false, 0);
					HashArchivos.add(nuevoArchivo);
					clsGestor.llenarLibrosDocum(nickUsuarioSesion, HashArchivos, HashLibros, HashDocumentos);
					CopiarArchivo(path, nuevoArchivo);
					clsGestor.guardarArchivo(nuevoArchivo.getNick(), nuevoArchivo.getNomAutor(), nuevoArchivo.getApeAutor(), nuevoArchivo.getCodArchivo(), nuevoArchivo.getTitulo(), nuevoArchivo.getRuta(), nuevoArchivo.getNumPags(), nuevoArchivo.getUltimaPagLeida(), nuevoArchivo.getTiempo(), nuevoArchivo.getLibroSi());
				} 
				catch (PdfException e) 
				{
					
				}
			} 
		}
	}
	
	public static void CopiarArchivo(String DireccionOrigen, clsArchivo ArchivoDireccionDestino)
	{
		String NombreArchivo;
		Path FROM;
		Path TO;
		
		NombreArchivo = ArchivoDireccionDestino.getTitulo() + ".pdf";
		
		FROM = Paths.get(DireccionOrigen);
		if(ArchivoDireccionDestino.getLibroSi())
		{
			TO = Paths.get(".\\Data\\Libros");  
			logger.log(Level.INFO, "Copiando archivo "+ NombreArchivo + " a la carpeta '.\\Data\\Libros'" );
		} 
		else
		{
			TO = Paths.get(".\\Data\\Documentos");
			logger.log(Level.INFO, "Copiando archivo "+ NombreArchivo + " a la carpeta '.\\Data\\Documentos'" );
		}
		
		ArchivoDireccionDestino.setRuta(TO + "\\" + NombreArchivo);
		logger.log(Level.INFO, "Ruta final del archivo  "+ NombreArchivo + " = " +  ArchivoDireccionDestino.getRuta());
		
		try 
		{
			Files.copy(FROM, TO.resolve(NombreArchivo));
		} 
		catch (IOException e) 
		{
			logger.log(Level.WARNING, "Error al copiar: " + e.getMessage() );
		}
			logger.log(Level.INFO, "Archivo copiado" );
	}

	public static void BorrarArchivo()
	{
		clsArchivo borrar;
		
		//No nos hace falta controlar si est� o no seleccionado alguno ya que si no es as� la opci�n de men� est� inactiva.
		//Lo que necesitamos saber es de qu� lista se ha seleccionado
		if(panelListas.getSelectedIndex() == 1)
		{
			borrar = (clsArchivo) ListDoc.getSelectedValue();
			HashDocumentos.remove(borrar);
			logger.log(Level.INFO, "Borrando documento" );
		}
		else
		{
			borrar = (clsArchivo) ListLibros.getSelectedValue();
			HashLibros.remove(borrar);
			logger.log(Level.INFO, "Borrando libro" );
		}
		clsGestor.BorrarObjetoBD(borrar.getCodArchivo(), "ARCHIVO");
		logger.log(Level.INFO, "Archivo n� "+  borrar.getCodArchivo() + " borrado");
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		switch(e.getActionCommand())
		{
			case "IMPORTAR_LIBRO":
				SeleccionarArchivo(true);
				break;
				
			case "IMPORTAR_DOCUMENTO":
				SeleccionarArchivo(false);
				break;
				
			case "CARPETA_LIBRO":
				SeleccionarArchivosDeCarpeta(true);
				break;
				
			case "CARPETA_DOCUMENTO":
				SeleccionarArchivosDeCarpeta(false);
				break;
				
			case "BORRAR":
			case "ELIMINAR":
				int option;
				option = JOptionPane.showConfirmDialog(this, "�Est�s seguro de querer borrar este archivo de tu lista de archivos?", "Confirmar borrado",
				        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				
				if(option == JOptionPane.OK_OPTION)
				{
					BorrarArchivo();
					ActualizarListas();
					
					String ruta = PanelPDF.getPDFabierto().getRuta();
					
					if(panelListas.getSelectedIndex() == 0)
					{
						if(ListLibros.getSelectedValue().equals(PanelPDF.getPDFabierto()))
							PanelPDF.CerrarPDF();
					} 
					else
					{
						if(ListDoc.getSelectedValue().equals(PanelPDF.getPDFabierto()))
							PanelPDF.CerrarPDF();
					}
					try {
						clsGestor.EliminarRuta(ruta);
					} catch (IOException e1) {}
				}	
				break;
				
			case "DETALLES":
				clsArchivo archivoDatos;
				if(panelListas.getSelectedIndex()==1)
				{
					archivoDatos = (clsArchivo)ListDoc.getSelectedValue();
				}
				else
				{
					archivoDatos = (clsArchivo)ListLibros.getSelectedValue();
				}
				frmDatosArchivo datos = new frmDatosArchivo ( archivoDatos, this);
				datos.setVisible(true);
		}
	}

	@Override
	public void stateChanged(ChangeEvent arg0)
	{
		
	}
	
	@Override
	public void valueChanged(ListSelectionEvent arg0)
	{
		
	}
	/**
	 * Hilo para comprobar cu�ndo cambia el nick en frmRegistro y cargar las listas correspondientes a ese usuario
	 * @author ALUMNO
	 *
	 */
	public class hiloNick extends Thread 
	{
		@Override
		public void run()
		{
			while(nickUsuarioSesion.isEmpty()) //arriba lo igualamos a "" para que no nos d� problemas de null
			{
				logger.log(Level.INFO, "No se ha iniciado sesi�n");
				try
				{
					Thread.sleep(1000);
					
				} catch (InterruptedException e) 
				{
					
				}
			}
			if(!(nickUsuarioSesion.isEmpty()))
			{
				logger.log(Level.INFO, "Se ha iniciado sesi�n " + nickUsuarioSesion);
				clsGestor.llenarLibrosDocum(nickUsuarioSesion, HashArchivos, HashLibros, HashDocumentos);
				frmPrincipal.ActualizarListas();
				this.interrupt();
			}
		}
	}
	public class hiloHabilitarBorrado extends Thread 
	{
		@Override
		public void run()
		{
			while(true)
			{
				if(panelListas.getSelectedIndex() == 0 && (!ListLibros.isSelectionEmpty())) //Si la pesta�a es la de los libros y hay alguno seleccionado, habilitamos
				{					
					BorrarArchivo.setEnabled(true);
				}
				else if (panelListas.getSelectedIndex() == 1 && (!ListDoc.isSelectionEmpty()))//Si la pesta�a es la de los documentos y hay alguno seleccionado, habilitamos
				{
					BorrarArchivo.setEnabled(true);
				}
				else
				{
					BorrarArchivo.setEnabled(false); //En cualquier otro caso, deshabilitado
				}
				try 
				{
					Thread.sleep(1000);
				} catch (InterruptedException e) 
				{
					
				}
			}
		}
	}
}