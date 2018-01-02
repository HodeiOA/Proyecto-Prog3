package LP;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import LN.clsArchivo;
import LN.clsGestor;

public class frmDatosArchivo extends JDialog
{
	JButton ButEditar = new JButton("Editar");
	
	JTextField Ttitulo;
	JTextField Tnombre;
	JTextField Tapellido;
	JTextField Truta;
	JTextField TnumPags;
	JTextField Tprogreso;
	JTextField Ttiempo;
	
	JLabel Ltitulo;
	JLabel Lnombre;
	JLabel Lapellido;
	JLabel LTipo;
	JLabel Lruta;
	JLabel LnumPags;
	JLabel Lprogreso;
	JLabel Ltiempo;
	
	//Para el tipo de archivo, RadioButtons:
	ButtonGroup radioLibroSi = new ButtonGroup();
	JRadioButton RadioDoc = new JRadioButton();
	JRadioButton RadioLibro = new JRadioButton();
	
	JPanel Pbotones = new JPanel();
	//Posición
	int x;
	int y;
	
	//Tiempo
	int horas;
	int min;
	int seg;
	
	String H;
	String M;
	String S;
	
	public frmDatosArchivo( clsArchivo archivo, JFrame frame)
	{
		super(frame, "Datos del archivo nº" + archivo.getCodArchivo() +"", true);
		
		//Tamaño y posición de la pantalla:
		 this.setSize(400, 500);
	     x=frame.getWidth()/3;
	     y=frame.getHeight()/4;
	     this.setLocation(x, y);
	     setResizable(false);
	      
	     //Layout del panel principal
	     this.getContentPane().setLayout(null);  
		
		//Dar texto a los labels
		Ltitulo = new JLabel("Título: ");
		Ltitulo.setBounds(10, 10, 41, 50);
		Lnombre = new JLabel("Nombre del autor: ");
		Lapellido = new JLabel("Apellido del autor: ");
		LTipo = new JLabel("Tipo de archivo");
		Lruta = new JLabel("Ruta: ");
		LnumPags = new JLabel ("Número de páginas: ");
		Lprogreso = new JLabel("Progreso de lectura: ");
		Ltiempo = new JLabel("Tiempo de lectura: ");
		
		//Dar texto a los TextFields
		Ttitulo = new JTextField(archivo.getTitulo());
		Tnombre = new JTextField(archivo.getNomAutor());
		Tapellido = new JTextField(archivo.getApeAutor());
			//En el caso del Tipo, gestionamos los radioButtons
			RadioLibro.setText("libro");
			RadioLibro.setSelected(archivo.getLibroSi());
			RadioDoc.setText("documento");
			RadioDoc.setSelected(!(archivo.getLibroSi()));
			radioLibroSi.add(RadioLibro);
			radioLibroSi.add(RadioDoc);
		Truta = new JTextField(archivo.getRuta());
		TnumPags = new JTextField(""+ archivo.getNumPags() + "");
		Tprogreso = new JTextField("" + clsGestor.porcentLeido(archivo) + " %");
			horas = archivo.getTiempo()/360;
			min = (archivo.getTiempo() - horas * 360) / 60;
			seg = (archivo.getTiempo() - min * 60);
			if(horas < 10)
			{
				H = "0" + horas;
			}
			else
			{
				H = ""+horas;
			}
			
			if(min < 10)
			{
				M = "0" + min;
			}
			else
			{
				M = "" + min;
			}
			
			if(seg < 10)
			{
				S = "0" + seg;
			}
			else
			{
				S = "" + seg;
			}
		Ttiempo = new JTextField(H +" : "+ M +" : "+ S);
		
		//Solo cambiarán estos
		Ttitulo.setEditable(false);
		Tnombre.setEditable(false);
		Tapellido.setEditable(false);
		RadioLibro.setEnabled(false);
		RadioDoc.setEnabled(false);
		Ttiempo.setEditable(false);
		//Estos siempres serán false
		Truta.setEditable(false);
		TnumPags.setEditable(false);
		Tprogreso.setEditable(false);
		
		Pbotones.setBounds(10, 417, 379, 44);
		Pbotones.add(ButEditar);
	
		this.getContentPane().add(Pbotones);
		
		this.getContentPane().add(Ltitulo);
		this.getContentPane().add(Ttitulo);
		this.getContentPane().add(Lnombre);
		this.getContentPane().add(Tnombre);
		this.getContentPane().add(Lapellido);
		this.getContentPane().add(Tapellido);
		this.getContentPane().add(LTipo);
		this.getContentPane().add(RadioLibro);
		this.getContentPane().add(RadioDoc);
		this.getContentPane().add(Lruta);
		this.getContentPane().add(Truta);
		this.getContentPane().add(LnumPags);
		this.getContentPane().add(TnumPags);
		this.getContentPane().add(Lprogreso);
		this.getContentPane().add(Tprogreso);
		this.getContentPane().add(Ltiempo);
		this.getContentPane().add(Ttiempo);
		
		//Decidir la posición de los Labels
		int cont=1;
		Ltitulo.setBounds(15, 10, 120, 50);
		Lnombre.setBounds(15, Ltitulo.getHeight()*cont+10, Ltitulo.getWidth(), Ltitulo.getHeight());
		cont ++;		
		Lapellido.setBounds(15, Lnombre.getHeight()*cont +10, Ltitulo.getWidth(), Ltitulo.getHeight());
		cont ++;
		LTipo.setBounds(15, Ltitulo.getHeight()*cont+10, Ltitulo.getWidth(), Ltitulo.getHeight());
		cont ++;
		Lruta.setBounds(15, Lapellido.getHeight()*cont+10, Ltitulo.getWidth(), Ltitulo.getHeight());
		cont ++;
		LnumPags.setBounds(15, Lapellido.getHeight()*cont+10, Ltitulo.getWidth(), Ltitulo.getHeight());
		cont ++;
		Lprogreso.setBounds(15, Lapellido.getHeight()*cont+10, Ltitulo.getWidth(), Ltitulo.getHeight());
		cont ++;
		Ltiempo.setBounds(15, Lapellido.getHeight()*cont+10, Ltitulo.getWidth(), Ltitulo.getHeight());
		
		//Decidir la posición de los TextFields
		cont = 0;
		Ttitulo.setBounds(Ltitulo.getWidth()+20, 10, 250, 50);
		cont++;
		Tnombre.setBounds(Ttitulo.getX(), Ttitulo.getHeight()*cont+10, Ttitulo.getWidth(), Ttitulo.getHeight());
		cont++;
		Tapellido.setBounds(Ttitulo.getX(), Ttitulo.getHeight()*cont+10, Ttitulo.getWidth(), Ttitulo.getHeight());
		cont++;
		RadioLibro.setBounds(Ttitulo.getX(), Ttitulo.getHeight()*cont+10, Ttitulo.getWidth()/2, Ttitulo.getHeight());
		RadioDoc.setBounds(Ttitulo.getX()+ RadioLibro.getWidth(), Ttitulo.getHeight()*cont+10, Ttitulo.getWidth()/2, Ttitulo.getHeight());
		cont ++;
		Truta.setBounds(Ttitulo.getX(), Ttitulo.getHeight()*cont+10, Ttitulo.getWidth(), Ttitulo.getHeight());
		cont++;
		TnumPags.setBounds(Ttitulo.getX(), Ttitulo.getHeight()*cont+10, Ttitulo.getWidth(), Ttitulo.getHeight());
		cont++;
		Tprogreso.setBounds(Ttitulo.getX(), Ttitulo.getHeight()*cont+10, Ttitulo.getWidth(), Ttitulo.getHeight());
		cont ++;
		Ttiempo.setBounds(Ttitulo.getX(), Ttitulo.getHeight()*cont+10, Ttitulo.getWidth(), Ttitulo.getHeight());
		
		//Funcionalidad del botón
		ButEditar.addActionListener(new ActionListener()
		{			
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				if(ButEditar.getText().equals("Editar"))
				{
					Ttitulo.setEditable(true);
					Tnombre.setEditable(true);
					Tapellido.setEditable(true);
					RadioLibro.setEnabled(true);
					RadioDoc.setEnabled(true);
					ButEditar.setText("Guardar");
				}
				else
				{
					Ttitulo.setEditable(false);
					Tnombre.setEditable(false);
					Tapellido.setEditable(false);
					RadioLibro.setEnabled(false);
					RadioDoc.setEnabled(false);
					
					archivo.setTitulo(Ttitulo.getText());
					archivo.setNomAutor(Tnombre.getText());
					archivo.setApeAutor(Tapellido.getText());
					archivo.setLibroSi(RadioLibro.isSelected());//No nos hace falta mirar radioDoc porque al estar en el ButtonGroup si uno es cierto el otro es falso
					//IMPORTANTE: Si cambia la selección de libroSi, habrá que cambria, además, su ruta, moviéndolo a la carpeta que le corresponda
					frmPrincipal.ComprobarCarpeta();
					frmPrincipal.CopiarArchivo(archivo.getRuta(), archivo);
					clsGestor.ModificarArchivo(archivo);
					Truta.setText(archivo.getRuta());
					ButEditar.setText("Editar");
				}
			}
		});
	}
}
