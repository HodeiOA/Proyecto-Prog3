package LP;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
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
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;


import LD.clsBD;
import LD.clsProperties;
import LN.clsArchivo;
import LN.clsComentario;
import LN.clsGestor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.SystemColor;
import java.awt.FlowLayout;
import java.awt.Font;

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
	private Dimension dim;
	private Toolkit mipantalla;
	
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
	//(??)Buscar
	
	//Para el men� que se despliega a al hacer click derecho sobre un elemento de la lista
	JPopupMenu popup;
	JMenuItem Mdetalles = new JMenuItem("Informaci�n del archivo");
	JMenuItem Meliminar = new JMenuItem("Eliminar archivo");
	
	//Paneles
	private JTabbedPane panelListas= new JTabbedPane(); //Panel de pesta�as
	private static JPanel PLibros=new JPanel(); //Panel dentro de la pesta�a libros
	private JPanel PDocum=new JPanel(); //Panel dentro de la pesta�a documentos
	static clsPanelPDF PanelPDF = new clsPanelPDF();
	private JPanel Pinferior= new JPanel();
	
	//Botones
	JButton Banterior= new JButton("<<"); //mirar taller bicis
	JButton Bsiguiente= new JButton(">>"); 
	
	//Marcador de p�gina, cambiante seg�n los botones de arriba y/o el slider
	static JTextArea numPag = new JTextArea ();
	static String indicadorPaginas; //Cada vez que cambiemos la p�gina, cambiaremos el String
	
	//Otros compontentes
	JProgressBar progreso=new JProgressBar();
	static JSlider slider=new JSlider ();
	JButton AddLibro = new JButton("Importar libro");
	JButton AddDoc = new JButton ("Importar documento");
	
	//Comentarios
	private boolean comentarios = true;
	private JTextPane TextComent = new JTextPane(); 
	private JScrollPane Scroll = new JScrollPane(TextComent);
	private JPanel Pcomentarios= new JPanel();
	private boolean editable = false;
	

	//Para el Listmodel
	static HashSet <clsArchivo> HashArchivos = new HashSet();
	static HashSet <clsArchivo> HashLibros = new HashSet();
	static HashSet <clsArchivo> HashDocumentos = new HashSet();
	static modelArchivos modelLibros;
	static modelArchivos modelDocumentos;
	
	//Listas para libros/documentos
	static JList ListLibros;
	static JList ListDoc;
	
	//JFileChooser:
	private JFileChooser chooser;
	private String path;
	private String ultimaCarpeta;
	private static Pattern filtro;
	
	//Para guardar propiedades
	static Properties misProps=new Properties();
	ArrayList <String> ClavesPropiedades = new ArrayList();
	String[] AnchuraAltura = new String[2];
	String[] locationXY = new String[2];
	
	static 	int cont = 0;
	
	//Para saber si algo est� siendo o no mostrado en el panel del PDF
	static boolean PDFactivo=false;
	
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
//		anchura=Integer.parseInt(misProps.getProperty(ClavesPropiedades.get(0)));
//		altura=Integer.parseInt(misProps.getProperty(ClavesPropiedades.get(1)));
		if(altura==0)
		{
			mipantalla=Toolkit.getDefaultToolkit();
			dim=mipantalla.getScreenSize();
			altura=dim.height;
			anchura=dim.width;
		}
		setSize(anchura, altura);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
//		x=Integer.parseInt(misProps.getProperty(ClavesPropiedades.get(2)));
//		y=Integer.parseInt(misProps.getProperty(ClavesPropiedades.get(3)));
		if(x!=0)
		{
			setLocation(x, y);		
			
		}
		
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
		          clsArchivo seleccion = (clsArchivo) ListLibros.getSelectedValue(); 
			      System.out.println(seleccion.getRuta());
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
		
		PLibros.setLayout(new BoxLayout(PLibros, BoxLayout.PAGE_AXIS));
		PLibros.add(AddLibro); 
		PLibros.add(ListLibros);
		PDocum.add(AddDoc);	
		PDocum.add(ListDoc);

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

		panelListas.setPreferredSize(new Dimension(225, 40));
		panelListas.addTab("Libros",icon,PLibros,
                "Lista de libros agregados");
		panelListas.addTab("Documentos",icon,PDocum,
                "Lista de documentos agregados");
	
		//Panel para la visualizaci�n del PDF
		getContentPane().add(PanelPDF, BorderLayout.CENTER);
		Pinferior.setBackground(SystemColor.inactiveCaption);
		
		//Panel para manipular el PDF
		getContentPane().add(Pinferior, BorderLayout.SOUTH);
		
		//prepara el texto del slider
		slider= new JSlider(JSlider.HORIZONTAL );
		slider.setPaintLabels(true); //si se ve los n�meros del slider
		slider.setBackground(SystemColor.inactiveCaption);
		//A�adirle al slider el listener
		  slider.addChangeListener(new ChangeListener()
		  {
		      public void stateChanged(ChangeEvent event)
		      {
		        int value = slider.getValue();
		        //Mirar cu�l es la lista seleccionada y sacar el lemento seleccionado para MOSTRARCOMENTARIOS
		        PanelPDF.irAPag(value);
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
				ActualizarSliderYTexto();//Mirar cu�l es la lista seleccionada y sacar el lemento seleccionado para MOSTRARCOMENTARIOS
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
				ActualizarSliderYTexto();//Mirar cu�l es la lista seleccionada y sacar el lemento seleccionado para MOSTRARCOMENTARIOS
			}
			
		});
		Pinferior.add(numPag);
		
		Pinferior.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		//Panel para comentarios
//		if(comentarios)
//		{
		//Aqu� leer�amos los comentarios por p�gina/Libro con uun for y crear�amos tantos TextArea y ScrolPane como hicieran falta	
			JButton EditC = new JButton ("Editar"); //En su listerner, cambiar editable=true;
			EditC.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent arg0) 
				{
					editable=!editable;
					TextComent.setEditable(editable);
					if(EditC.getText().equals("Editar"))
					{
						EditC.setText("Guardar");
					}
					else
					{
						//Identificar cu�l es el archivo en curso del que se ha hecho el comentario
						for(clsArchivo a: HashArchivos)
						{
							if(PDFactivo)
							{
								String ruta = PanelPDF.getRuta();
								if(ruta.equals(a.getRuta()))
								{
									if(!(TextComent.getText().isEmpty()))
									{	
										//GuardarComentario
										clsArchivo archivoComent = a;
										clsComentario coment = new clsComentario(TextComent.getText(),archivoComent.getCodArchivo(), PanelPDF.getPagActual(), false, 0);
																			
										clsGestor.guardarComentario(coment.getID(), coment.getTexto(), coment.getCodArchivo(), coment.getNumPagina());
	
										//Crear de nuevo los comentarios
										MostrarComentarios(archivoComent);
									}
								}
							}

							TextComent.setText("");
						}
						EditC.setText("Editar");
					}
					
				}
				
			});
			Pcomentarios.setPreferredSize(new Dimension(225, 40));
			TextComent.setPreferredSize(new Dimension(200, 200));
			TextComent.setEditable(editable);
			Pcomentarios.setBackground(Color.gray);
				
			Pcomentarios.add(Scroll);
			this.getContentPane().add(Pcomentarios, BorderLayout.EAST);
			Pcomentarios.add(EditC);
			
			// Construccion del JPopupMenu para el click derecho
			popup = new JPopupMenu();
			popup.add(Mdetalles);
			popup.add(Meliminar);
			
			Mdetalles.addActionListener(this);
			Meliminar.addActionListener(this);
			Mdetalles.setActionCommand("DETALLES");
			Meliminar.setActionCommand("ELIMINAR");
								
//		}
			
		this.addComponentListener(new ComponentListener()
			{
				@Override
				public void componentHidden(ComponentEvent arg0) {}

				@Override
				public void componentMoved(ComponentEvent arg0) {}

				@Override
				public void componentResized(ComponentEvent arg0)
				{
					AnchuraAltura[0]= Integer.toString(getHeight());
					AnchuraAltura[1]= Integer.toString(getWidth());
					clsProperties.CambiarPropiedades(misProps, AnchuraAltura, locationXY, ClavesPropiedades);
				}
				@Override
				public void componentShown(ComponentEvent arg0) {}
			});
		this.addWindowListener(new WindowListener()		
			{
	
				@Override
				public void windowActivated(WindowEvent arg0) 
				{	}
	
				@Override
				public void windowClosed(WindowEvent arg0) 
				{}
	
				@Override
				public void windowClosing(WindowEvent arg0) 
				{
//					PanelPDF.GuardarDatosPDFAnterior(PanelPDF);
					clsBD.close();
//					locationXY[0]=
//							x=Integer.parseInt((getLocation().getX()).round());
//					locationXY[1]=Double.toString(getLocation().getY());
//					clsProperties.CambiarPropiedades(misProps, AnchuraAltura, locationXY, ClavesPropiedades);
//					clsProperties.Guardarpropiedad(misProps);
				}
	
				@Override
				public void windowDeactivated(WindowEvent arg0)
				{}
	
				@Override
				public void windowDeiconified(WindowEvent arg0) 
				{}
	
				@Override
				public void windowIconified(WindowEvent arg0) 
				{}
	
				@Override
				public void windowOpened(WindowEvent arg0) {}
				
			});
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
		} catch (IOException e1)
    	{
			logger.log(Level.SEVERE, e1.getMessage(), e1);
			e1.printStackTrace();
		}
	}
	public static void ActualizarSliderYTexto()
	{
		logger.log(Level.INFO, "Actualizando Slider y texto del panel inferior");
		//prepara el texto del slider
				slider.setMinimum(1);
				slider.setMaximum( PanelPDF.getPaginasTotal());
				slider.setValue( PanelPDF.getPagActual());
				slider.setPaintTicks(true);//las rayitas que marcan los n�meros
				slider.setMajorTickSpacing(PanelPDF.getPaginasTotal()-1); // de cuanto en cuanto los n�meros en el slider
				//slider.setMinorTickSpacing(PanelPDF.getPaginasTotal()/3); //las rayitas de cuanto en cuanto
				slider.setPaintLabels(true); //si se ven los n�meros del slider o no
				slider.setBackground(SystemColor.inactiveCaption);
				
				//Preparar el textode los n�meros de p�gina
				indicadorPaginas = ""+ PanelPDF.getPagActual() +" / " + PanelPDF.getPaginasTotal();
				numPag.setText(indicadorPaginas);
				numPag.setEditable(false);
				numPag.setBackground(SystemColor.inactiveCaption);
	}
	/**
	 * Abre el archivo seleccionado en la lista
	 * @param elegido
	 */
	public static void SeleccionListas(clsArchivo elegido)
	{
		PanelPDF.abrirPDF(elegido);
		PDFactivo=true;
		ActualizarSliderYTexto();
	}
	/**
	 * 
	 * @param a archivo del que vamos a mostrar sus comentrios
	 */
	public void MostrarComentarios(clsArchivo a)
	{
		int cont=0;
		HashSet <clsComentario> comentarios = clsGestor.LeerComentariosBD();
		
		logger.log(Level.INFO, "A�adiendo comentarios al panel de comentraios");
		for (clsComentario c: comentarios)
		{
			if(a.getCodArchivo()==c.getCodArchivo() && PanelPDF.getPagActual()==c.getNumPagina()) 
			{
				cont++;
				
				Pcomentarios.add(new JTextArea (c.getTexto()));
				logger.log(Level.INFO, "Comentario "+ c.getID() + " a�adido");
			}
		}
		logger.log(Level.INFO,"Comentarios a�adidos: "+cont);
	}
	
	/**
	 * En este m�todo cargamos los datos, en este caso la lista de pel�culas.
	 */
	public static void CargarDatos()
	{
		clsProperties.CargarProps(misProps);	
		HashArchivos = clsGestor.LeerArchivosBD();
		clsGestor.llenarLibrosDocum (HashArchivos, HashLibros, HashDocumentos);

		modelLibros = new modelArchivos(HashLibros);
		modelDocumentos = new modelArchivos(HashDocumentos);
	}
	
	
	/**
	 * M�todo para recoger/guardar un solo PDF con filtro de PDFs
	 */
	public void SeleccionarArchivo(boolean esLibro)
	{
		
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
		//filtro= Pattern.compile( "\\*\\.pdf", Pattern.CASE_INSENSITIVE );
		FileNameExtensionFilter filtro = new FileNameExtensionFilter("*.PDF", "pdf");
		chooser.setFileFilter(filtro);
		
		int response = chooser.showOpenDialog(this);
		if(response == JFileChooser.APPROVE_OPTION)
		{
			path = chooser.getSelectedFile().getPath();
			
			//Recoger el file
			//Para probarlo, creaci�n de un clsArchivo Falso
		
			String [] titulos = {"1", "2", "3"};
			clsArchivo a = new clsArchivo ("Maider", path, titulos [cont], "", 1, 1, 1, esLibro, false, 0);
			cont ++;
			HashArchivos.add(a);
			ComprobarCarpeta();
			CopiarArchivo(path, a);
			PanelPDF.abrirPDF(a);
			PDFactivo=true;
			ActualizarSliderYTexto();
			MostrarComentarios(a);
			CargarDatos();
				
			clsGestor.guardarArchivo(a.getNomAutor(), a.getApeAutor(), a.getCodArchivo(), a.getTitulo(), a.getRuta(), a.getNumPags(), a.getUltimaPagLeida(), a.getTiempo(), a.getLibroSi());
			//IMPORTANTE: si ya hay un file con el mismo nombre, le cambiamos el normbre a este �ltimo a "nombre (1)" o el n�mero que sea
			//TODO: Recoger la carpeta hasta la que ha llegado -->�C�mo?
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
			ComprobarCarpeta();
			RecursividadCarpeta(ultimaCarpeta);
			//Creamos un nuevo archivo por cada pdf que haya en el directorio
			//Para ello, primero vemos si estamos en una carpeta, por lo que la primera vez siempre entrar�			
		} 
	}
	
	public static void RecursividadCarpeta(String path)
	{
		File fic = new File (path);
		
		if (fic.isDirectory()) 
		{
			for( File f : fic.listFiles() ) 
			{
				/*Por cada fichero en Fic, volveremos a llamar a este m�todo hasta llegar al interior de una carpeta en la que no haya
				*m�s, por lo que pasar� por todos los ficheros en el interior de la carpeta
				**/
				RecursividadCarpeta(f.getPath());
			}
		} else
		{ 
			//En este caso recursivo, el caso en el que NO es una carpeta ser� el caso base
			//Es decir, el algoritmo recursivo llear� hasta la carpeta del fin de la ruta de carpetas 
			//En ese momento, a�adir� todos los PDFs en su interior
			if (filtro.matcher(fic.getName()).matches() )
			{
				// Si cumple el patr�n, se a�ade
				path = fic.getPath();
				// Llamar a m�todo de CopiarArchivo()
				// aqu� habr� que lanzar una pantalla para EL RESTO DE ATRIBUTOS adem�s de la ruta
				//Recoger el file
				//IMPORTANTE: si ya hay un file con el mismo nombre, le cambiamos el normbre a este �ltimo a "nokmbre (1)" o el n�mero que sea
				//TODO: Recoger la carpeta hasta la que ha llegado -->�C�mo?
			} 
		}
	}
	
	public static void ComprobarCarpeta()
	{
		File Data = new File(".\\Data");
		File Libros = new File(".\\Data\\Libros");
		File Documentos = new File(".\\Data\\Documentos");
		
		boolean data = Data.mkdir(); 
		boolean libros = Libros.mkdir();
		boolean docum = Documentos.mkdir();
		
		if(data) logger.log(Level.INFO, "Se caba de crear la carpeta 'Data'");
		if(libros) logger.log(Level.INFO, "Se caba de crear la carpeta 'Libros'");
		if(docum) logger.log(Level.INFO, "Se caba de crear la carpeta 'Documentos'");
		
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
		} else
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
		if(ListLibros.isSelectionEmpty())
		{
			borrar = (clsArchivo) ListDoc.getSelectedValue();
			logger.log(Level.INFO, "Borrando documento" );
		}
		else
		{
			borrar = (clsArchivo) ListLibros.getSelectedValue();
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
					CargarDatos();
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
	{}
	@Override
	public void valueChanged(ListSelectionEvent arg0)
	{}
}
