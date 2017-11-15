package LP;

import static COMUN.clsCts.COMMAND_ALTACLIENTE;
import static COMUN.clsCts.COMMAND_ALTAEMPLEADO;
import static COMUN.clsCts.COMMAND_ALTAPELICULA;

import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
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
import javax.swing.JTextArea;
import javax.swing.JTextPane;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.FlowLayout;

public class frmPrincipal extends JFrame implements ActionListener
{
	private int altura=0;
	private int anchura=0;
	private Dimension dim;
	private Toolkit mipantalla;
	
	//MEN�
	private JMenuBar menuBar=new JMenuBar();
	private JMenu archivo=new JMenu("Archivo");
	private JMenu importar=new JMenu("Importar");
	private JMenuItem libro=new JMenuItem("Libro");
	private JMenuItem documento=new JMenuItem("Documento");
	private JMenu Mcom =new JMenu("Comentarios");
	private JMenuItem VerComentarios = new JMenuItem( "Ver comentarios"); //Hacer que cuando se est�n viendo los comentarios pase a poner ocultar comentarios
	
	//Paneles
	private JTabbedPane panelListas= new JTabbedPane(); //Panel de pesta�as
	private JPanel PLibros=new JPanel(); //Panel dentro de la pesta�a libros
	private JPanel PDocum=new JPanel(); //Panel dentro de la pesta�a documentos
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
	
	public frmPrincipal (String titulo)
	{
		setTitle(titulo);
		//Leer el xml que guarda el tama�o de la ventana y meter los datos en altura y anchura
		//Este if lo tendremos que hacer, pero lo comento hasta que podamos leer los valores
//		if(altura==0)
//		{
			mipantalla=Toolkit.getDefaultToolkit();
			dim=mipantalla.getScreenSize();
			altura=dim.height;
			anchura=dim.width;
//		}
		setSize(anchura, altura);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Men�
		//Construcci�n del men�
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
		
		//He puesto el tama�o a ojo porque si no el que me pon�a de por s� era muy estrecho
		panelListas.setPreferredSize(new Dimension(225, 40));
		panelListas.addTab("Libros",icon,PLibros,
                "Lista de libros agregados");
		panelListas.addTab("Documentos",icon,PDocum,
                "Lista de documentos agregados");
		
		
		//Panel para la visualizaci�n del PDF
		PanelPDF.setBackground(Color.DARK_GRAY);
		getContentPane().add(PanelPDF, BorderLayout.CENTER);
		Pinferior.setBackground(SystemColor.inactiveCaption);
		
		//Panel para manipular el PDF
		getContentPane().add(Pinferior, BorderLayout.SOUTH);
		
		//Pinferior.add(progreso);
		Pinferior.add(Banterior);
		Pinferior.add(slider);
		Pinferior.add(Bsiguiente);
		
		Pinferior.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		//Panel para comentarios
//		if(comentarios)
//		{
		//Aqu� leer�amos los comentarios por p�gina/Libro con uun for y crear�amos tantos TextArea y ScrolPane como hicienran falta	
			JButton EditC = new JButton ("editar"); //En su listerner, cambiar editable=true;
			
			Pcomentarios.setPreferredSize(new Dimension(225, 40));
			TextComent.setPreferredSize(new Dimension(200, 200));
			TextComent.setEditable(editable);
			Pcomentarios.setBackground(Color.gray);
				
			Pcomentarios.add(Scroll);
			this.getContentPane().add(Pcomentarios, BorderLayout.EAST);
			Pcomentarios.add(EditC);
//		}
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		// TODO Auto-generated method stub
		
	}
}
