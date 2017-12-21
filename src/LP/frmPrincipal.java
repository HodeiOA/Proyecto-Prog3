package LP;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Properties;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.sun.javafx.scene.paint.GradientUtils.Point;

import LD.clsBD;
import LD.clsProperties;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.FlowLayout;

public class frmPrincipal extends JFrame implements ActionListener
{
	private int altura=0;
	private int anchura=0;
	private int x=0;
	private int y=0;
	private Dimension dim;
	private Toolkit mipantalla;
	
	//MENÚ
	private JMenuBar menuBar=new JMenuBar();
	private JMenu archivo=new JMenu("Archivo");
	private JMenu importar=new JMenu("Importar");
	private JMenuItem libro=new JMenuItem("Libro");
	private JMenuItem documento=new JMenuItem("Documento");
	private JMenu Mcom =new JMenu("Comentarios");
	private JMenuItem VerComentarios = new JMenuItem( "Ver comentarios"); //Hacer que cuando se estén viendo los comentarios pase a poner ocultar comentarios
	// JMenu de sesión: cerrar sesión
	//JMenu de guardar
	//(??)Buscar
	
	//Paneles
	private JTabbedPane panelListas= new JTabbedPane(); //Panel de pestañas
	private JPanel PLibros=new JPanel(); //Panel dentro de la pestaña libros
	private JPanel PDocum=new JPanel(); //Panel dentro de la pestaña documentos
	private JPanel PanelPDF=new JPanel();
	private JPanel Pinferior= new JPanel();
	
	//Botones
	JButton Banterior= new JButton("<<"); //mirar taller bicis
	JButton Bsiguiente= new JButton(">>"); 
	
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
	
	//Listas para libros/documentos
	JList ListLibros = new JList ();
	JList ListDoc = new JList ();
	
	//JFileChooser:
	private JFileChooser chooser;
	private String path;
	private Pattern filtro;
	
	//Para guardar propiedades
	Properties misProps=new Properties();
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
				
		archivo.add(importar);
		importar.add(libro);
				
		menuBar.add(Mcom);
		Mcom.add(VerComentarios);
		
		//Layout para el panel
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		//Panel Para libros/Documentos
	
		ImageIcon icon = null;
		getContentPane().add(panelListas, BorderLayout.WEST);
		
		PLibros.add(AddLibro);
		PLibros.add(ListLibros);
		PDocum.add(AddDoc);
		PDocum.add(ListDoc);
		
		//He puesto el tamaño a ojo porque si no el que me ponía de por sí era muy estrecho
		panelListas.setPreferredSize(new Dimension(225, 40));
		panelListas.addTab("Libros",icon,PLibros,
                "Lista de libros agregados");
		panelListas.addTab("Documentos",icon,PDocum,
                "Lista de documentos agregados");
		
	
		//Panel para la visualización del PDF
		PanelPDF.setBackground(Color.DARK_GRAY);
		getContentPane().add(PanelPDF, BorderLayout.CENTER);
		Pinferior.setBackground(SystemColor.inactiveCaption);
		
		//Panel para manipular el PDF
		getContentPane().add(Pinferior, BorderLayout.SOUTH);
		
		Pinferior.add(progreso);
		Pinferior.add(Banterior);
		Pinferior.add(slider);
		Pinferior.add(Bsiguiente);
		
		Pinferior.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		//Panel para comentarios
//		if(comentarios)
//		{
		//Aquí leeríamos los comentarios por página/Libro con uun for y crearíamos tantos TextArea y ScrolPane como hicienran falta	
			JButton EditC = new JButton ("editar"); //En su listerner, cambiar editable=true;
			
			Pcomentarios.setPreferredSize(new Dimension(225, 40));
			TextComent.setPreferredSize(new Dimension(200, 200));
			TextComent.setEditable(editable);
			Pcomentarios.setBackground(Color.gray);
				
			Pcomentarios.add(Scroll);
			this.getContentPane().add(Pcomentarios, BorderLayout.EAST);
			Pcomentarios.add(EditC);
			
//			SeleccionarArchivo();
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
					clsProperties.CargarProps(misProps);	
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
	
	/**
	 * Método para recoger/guardar un solo PDF con filtro de PDFs
	 */
	//Hacer uno para carpetas
	public void SeleccionarArchivo()
	{
		//Hacer distinto si es guardar o importar y si es libro o documento
		chooser = new JFileChooser();
		//if(esguardar )
		chooser.setApproveButtonText("Guardar");
		// eslibro
		chooser.setDialogTitle("Guardar cambios en el libro");
		// es Doc
		chooser.setDialogTitle("Guardar cambios en el documento");
		//if(esImportar )
		chooser.setApproveButtonText("Importar");
		// eslibro
		chooser.setDialogTitle("Importar libro");
		// es Doc
		chooser.setDialogTitle("Importar documento");
		
		//chooser.setAcceptAllFileFilterUsed(false); 
		
		//Filtrar extensiones de archivos
		//Pattern filtro= Pattern.compile( "\\*\\.pdf", Pattern.CASE_INSENSITIVE );
		FileNameExtensionFilter filtro = new FileNameExtensionFilter("*.PDF", "pdf");
		chooser.setFileFilter(filtro);
		
		int response = chooser.showOpenDialog(this);
		if(response == JFileChooser.APPROVE_OPTION)
		{
			path = chooser.getSelectedFile().toString();
			//Recoger el file
		}
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		// TODO Auto-generated method stub
		
	}
}
