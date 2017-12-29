package LP;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Properties;
import java.util.logging.Level;
import java.util.regex.Pattern;

import javax.swing.AbstractButton;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.filechooser.FileNameExtensionFilter;


import LD.clsBD;
import LD.clsProperties;
import LN.clsArchivo;
import LN.clsComentario;
import LN.clsGestor;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.SystemColor;
import java.awt.FlowLayout;
import java.awt.Font;

public class frmPrincipal extends JFrame implements ActionListener, ChangeListener, ListChangeListener
{
	private static final AbstractButton AddDocumento = null;
	private int altura=0;
	private int anchura=0;
	private int x=0;
	private int y=0;
	private Dimension dim;
	private Toolkit mipantalla;
	
	//MENÚ
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
	private JMenuItem VerComentarios = new JMenuItem( "Exportar comentarios"); //Hacer que cuando se estén viendo los comentarios pase a poner ocultar comentarios
	// JMenu de sesión: cerrar sesión
	//(??)Buscar
	
	//Para el menú que se despliega a al hacer click derecho sobre un elemento de la lista
	JPopupMenu popup;
	//Paneles
	private JTabbedPane panelListas= new JTabbedPane(); //Panel de pestañas
	private static JPanel PLibros=new JPanel(); //Panel dentro de la pestaña libros
	private JPanel PDocum=new JPanel(); //Panel dentro de la pestaña documentos
	private clsPanelPDF PanelPDF = new clsPanelPDF();
	private JPanel Pinferior= new JPanel();
	
	//Botones
	JButton Banterior= new JButton("<<"); //mirar taller bicis
	JButton Bsiguiente= new JButton(">>"); 
	
	//Marcador de página, cambiante según los botones de arriba y/o el slider
	JTextArea numPag = new JTextArea ();
	String indicadorPaginas; //Cada vez que cambiemos la página, cambiaremos el String
	
	//Otros compontentes
	JProgressBar progreso=new JProgressBar();
	JSlider slider=new JSlider ();
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
	static JList ListLibros=new JList();
	static JList ListDoc=new JList();
	
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
	

	
	public frmPrincipal (String titulo)
	{
		ClavesPropiedades.add("anchura");
		ClavesPropiedades.add("altura");
		ClavesPropiedades.add("X");
		ClavesPropiedades.add("Y");
		setTitle(titulo);
		
		CargarDatos();
		//Leer el xml que guarda el tamaño de la ventana y meter los datos en altura y anchura
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
		
		//Menú
		//Construcción del menú
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
		Mcom.add(VerComentarios);
		
		//Layout para el panel
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		//Añadir los listModel
		ListLibros=new JList(modelLibros);
		ListDoc=new JList (modelDocumentos);
		
		//Panel Para libros/Documentos
	
		ImageIcon icon = null;
		getContentPane().add(panelListas, BorderLayout.WEST);
		
	
		PLibros.add(ListLibros);
		PLibros.add(AddLibro); 
		PDocum.add(ListDoc);
		PDocum.add(AddDoc);		

		//Le añadimos el Listener a AddLibros
		AddLibro.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				SeleccionarArchivo(true);
			}
		});
		
//		//Le añadimos el Listener a AddDocumento
//				AddDocumento.addActionListener(new ActionListener()
//				{
//					@Override
//					public void actionPerformed(ActionEvent e)
//					{
//						SeleccionarArchivo(false);
//					}
//				});

		panelListas.setPreferredSize(new Dimension(225, 40));
		panelListas.addTab("Libros",icon,PLibros,
                "Lista de libros agregados");
		panelListas.addTab("Documentos",icon,PDocum,
                "Lista de documentos agregados");
		
	
		//Panel para la visualización del PDF
		getContentPane().add(PanelPDF, BorderLayout.CENTER);
		Pinferior.setBackground(SystemColor.inactiveCaption);
		
		//Panel para manipular el PDF
		getContentPane().add(Pinferior, BorderLayout.SOUTH);
		//Preparar el textode los números de página
		indicadorPaginas = ""+ PanelPDF.getPagActual() +" / " + PanelPDF.PaginasTotal();
		numPag.setText(indicadorPaginas);
		numPag.setEditable(false);
		numPag.setBackground(SystemColor.inactiveCaption);
		//prepara el texto del slider
		//slider= new JSlider(JSlider.HORIZONTAL, 1, PanelPDF.PaginasTotal(), PanelPDF.getPagActual() );
		slider= new JSlider(JSlider.HORIZONTAL, 1, 3, 2 );
		slider.setPaintTicks(true);//las rayitas que marcan los números
		slider.setMajorTickSpacing(5); // de cuanto en cuanto los números en el slider
		slider.setMinorTickSpacing(1); //las rayitas de cuanto en cuanto
		slider.setPaintLabels(true); //si se ve los números del slider
		slider.setBackground(SystemColor.inactiveCaption);
		
		Pinferior.add(progreso);
		Pinferior.add(Banterior);
		Pinferior.add(slider);
		Pinferior.add(Bsiguiente);
		Pinferior.add(numPag);
		
		Pinferior.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		//Panel para comentarios
//		if(comentarios)
//		{
		//Aquí leeríamos los comentarios por página/Libro con uun for y crearíamos tantos TextArea y ScrolPane como hicieran falta	
			JButton EditC = new JButton ("editar"); //En su listerner, cambiar editable=true;
			
			Pcomentarios.setPreferredSize(new Dimension(225, 40));
			TextComent.setPreferredSize(new Dimension(200, 200));
			TextComent.setEditable(editable);
			Pcomentarios.setBackground(Color.gray);
				
			Pcomentarios.add(Scroll);
			this.getContentPane().add(Pcomentarios, BorderLayout.EAST);
			Pcomentarios.add(EditC);
			
			// Construccion del JPopupMenu para el click derecho
			popup = new JPopupMenu();
			popup.add(new JMenuItem("Detalles"));
			popup.add(new JMenuItem("Eliminar archivo"));

			
//		}
			
		this.addComponentListener(new ComponentListener()
			{
				@Override
				public void componentHidden(ComponentEvent arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void componentMoved(ComponentEvent arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void componentResized(ComponentEvent arg0)
				{
					AnchuraAltura[0]= Integer.toString(getHeight());
					AnchuraAltura[1]= Integer.toString(getWidth());
					clsProperties.CambiarPropiedades(misProps, AnchuraAltura, locationXY, ClavesPropiedades);
				}

				@Override
				public void componentShown(ComponentEvent arg0) {
					// TODO Auto-generated method stub
					
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
					// TODO Auto-generated method stub
					
				}
	
				@Override
				public void windowClosing(WindowEvent arg0) 
				{
					clsBD.close();
//					locationXY[0]=
//							x=Integer.parseInt((getLocation().getX()).round());
//					locationXY[1]=Double.toString(getLocation().getY());
//					clsProperties.CambiarPropiedades(misProps, AnchuraAltura, locationXY, ClavesPropiedades);
//					clsProperties.Guardarpropiedad(misProps);
				}
	
				@Override
				public void windowDeactivated(WindowEvent arg0)
				{
					// TODO Auto-generated method stub					
				}
	
				@Override
				public void windowDeiconified(WindowEvent arg0) 
				{
					// TODO Auto-generated method stub
				}
	
				@Override
				public void windowIconified(WindowEvent arg0) 
				{
					// TODO Auto-generated method stub
				}
	
				@Override
				public void windowOpened(WindowEvent arg0) {
					// TODO Auto-generated method stub
					
				}
				
			});
	}
	
	public void CrearComentarios()
	{
		HashSet <clsComentario> comentarios = clsGestor.LeerComentariosBD();
		for (clsComentario c: comentarios)
		{
			Pcomentarios.add(new JTextArea (c.getTexto()));
		}
	}
	
	/**
	 * En este método cargamos los datos, en este caso la lista de películas.
	 */
	public static void CargarDatos()
	{
		clsProperties.CargarProps(misProps);	
		HashArchivos = clsGestor.LeerArchivosBD();
		clsGestor.llenarLibrosDocum (HashArchivos, HashLibros, HashDocumentos);
		modelLibros= new modelArchivos(HashLibros);
		modelDocumentos= new modelArchivos(HashDocumentos);

		ListLibros.setModel(modelLibros);
		ListDoc.setModel(modelDocumentos);
	}
	
	
	/**
	 * Método para recoger/guardar un solo PDF con filtro de PDFs
	 */
	public void SeleccionarArchivo(boolean esLibro)
	{
		//Hacer distinto si es guardar o importar y si es libro o documento
		chooser = new JFileChooser();
//		//if(esguardar )
//		chooser.setApproveButtonText("Guardar");
//		// eslibro
//		chooser.setDialogTitle("Guardar cambios en el libro");
//		// es Doc
//		chooser.setDialogTitle("Guardar cambios en el documento");
//		//if(esImportar )
		chooser.setApproveButtonText("Importar");
		// eslibro
		chooser.setDialogTitle("Importar libro");
		// es Doc
		chooser.setDialogTitle("Importar documento");
		
		chooser.setAcceptAllFileFilterUsed(false); 
		
		//Filtrar extensiones de archivos
		//filtro= Pattern.compile( "\\*\\.pdf", Pattern.CASE_INSENSITIVE );
		FileNameExtensionFilter filtro = new FileNameExtensionFilter("*.PDF", "pdf");
		chooser.setFileFilter(filtro);
		
		int response = chooser.showOpenDialog(this);
		if(response == JFileChooser.APPROVE_OPTION)
		{
			path = chooser.getSelectedFile().getPath();
			PanelPDF.abrirPDF(path);
			// aquí habrá que lanzar una pantalla para EL RESTO DE ATRIBUTOS además de la ruta
			//Recoger el file
			//Para probarlo, creación de un clsArchivo Falso
			clsArchivo a = new clsArchivo ("Maider", "c", "Maider mola", "", 0, 0, 0, true, false, 0);
			HashArchivos.add(a);
//			modelLibros.cargarInfo(HashArchivos);
			CargarDatos();
			clsGestor.guardarArchivo(a.getNomAutor(), a.getApeAutor(), a.getCodArchivo(), a.getTitulo(), a.getRuta(), a.getNumPags(), a.getUltimaPagLeida(), a.getTiempo(), a.getLibroSi());
			//IMPORTANTE: si ya hay un file con el mismo nombre, le cambiamos el normbre a este último a "nombre (1)" o el número que sea
			//TODO: Recoger la carpeta hasta la que ha llegado -->¿Cómo?
		}
	}
	
	public void SeleccionarArchivosDeCarpeta(boolean esLibro)
	{
		String carp = ultimaCarpeta;  
		//Elegimos dónde abrir el chooser
		if (ultimaCarpeta==null) carp = System.getProperty("user.dir");
		File dirActual = new File( carp );
		chooser = new JFileChooser( dirActual );
		chooser.setApproveButtonText("Importar");
		//Elegimos qué chooser abrir
		if(esLibro)
		{
			chooser.setDialogTitle("Importar carpeta libros");
		}
		else
		{
			chooser.setDialogTitle("Importar carpeta documentos");
		}
		//Hacemos que solo pueda seleccionar carpetas
		chooser.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );
		
		int response = chooser.showOpenDialog(this);
		
		if (response == JFileChooser.APPROVE_OPTION)
		{
			//cambiamos el valor de ultimaCarpeta
			ultimaCarpeta=chooser.getSelectedFile().getPath();
			RecursividadCarpeta(ultimaCarpeta);
			//Creamos un nuevo archivo por cada pdf que haya en el directorio
			//Para ello, primero vemos si estamos en una carpeta, por lo que la primera vez siempre entrará			
		} 
	}
	
	public static void RecursividadCarpeta(String path)
	{
		File fic = new File (path);
		if (fic.isDirectory()) 
		{
			for( File f : fic.listFiles() ) 
			{
				/*Por cada fichero en Fic, volveremos a llamar a este método hasta llegar al interior de una carpeta en la que no haya
				*más, por lo que pasará por todos los ficheros en el interior de la carpeta
				**/
				RecursividadCarpeta(f.getPath());
			}
		} else
		{ 
			//En este caso recursivo, el caso en el que NO es una carpeta será el caso base
			//Es decir, el algoritmo recursivo lleará hasta la carpeta del fin de la ruta de carpetas 
			//En ese momento, añadirá todos los PDFs en su interior
			if (filtro.matcher(fic.getName()).matches() )
			{
				// Si cumple el patrón, se añade
				path = fic.getPath();
				// aquí habrá que lanzar una pantalla para EL RESTO DE ATRIBUTOS además de la ruta
				//Recoger el file
				//IMPORTANTE: si ya hay un file con el mismo nombre, le cambiamos el normbre a este último a "nokmbre (1)" o el número que sea
				//TODO: Recoger la carpeta hasta la que ha llegado -->¿Cómo?
			} 
		}
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changed(ObservableValue arg0, Object arg1, Object arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onChanged(Change arg0) {
		// TODO Auto-generated method stub
		
	}
}
