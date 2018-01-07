package LP;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;

import javax.swing.JScrollPane;
import javax.swing.JViewport;

import org.jpedal.PdfDecoder;
import org.jpedal.exception.PdfException;

import LN.clsArchivo;
import LN.clsCronometro;
import LN.clsGestor;
/**
 * Clase de tipo JScrollPane para visualizar un PDF. Tiene los
 * métodos necesarios para interactuar con el PDF.
 * 
 * @author 
 *
 */
public class clsPanelPDF extends JScrollPane
{
	private static Logger logger = Logger.getLogger(clsPanelPDF.class.getName());
	private static Handler handlerPantalla;
	private static Handler handlerArchivo;
	
	private PdfDecoder PDFdecoder;
	private static clsArchivo PDFabierto;
	private int PagActual = 1;
	int velocidadScroll = 15;
	int rotacion = 90;
	float escala = 2;
	float escalaActual;
	
	static clsCronometro crono;
	
	/**
	 * Constructor que inicializa el PDFdecoder de la librería y el scroll.
	 */
	public clsPanelPDF()
	{
		InitLogs();
		logger.log(Level.INFO, "Iniciando el panel para el PDF");
		PDFdecoder = new PdfDecoder();
		
		this.getVerticalScrollBar().setUnitIncrement(velocidadScroll);
		
		PDFdecoder.setBackground(Color.DARK_GRAY);
		this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.setViewportView(PDFdecoder);
	}
	
	/**
	 * Inicia los Loggers y Handlers de la clase.
	 */
	public static void InitLogs()
	{
		handlerPantalla = new StreamHandler( System.out, new SimpleFormatter() );
		handlerPantalla.setLevel(Level.ALL);
		logger.addHandler(handlerPantalla);
		
		try 
		{
			handlerArchivo = new FileHandler("clsPanelPDF.log.xml");
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
	 * Permite abrir un archivo PDF. Si hubiera un PDF abierto
	 * anteriormente, guarda la información. También inicia el
	 * cronómetro.
	 * 
	 * @param archivo Recibe un objeto clsArchivo para abrirlo e interactuar con él posteriormente
	 */
	public void abrirPDF(clsArchivo archivo)
	{		
		try 
		{
			if(PDFabierto !=null)
			GuardarDatosPDFAnterior();
			
			PDFabierto = archivo;
			PagActual = archivo.getUltimaPagLeida();
			
			crono=new clsCronometro(); //Para asegurarnos de que vuelve a empezar desde cero
			crono.start();
			
			PDFdecoder.closePdfFile();
			PDFdecoder.openPdfFile(PDFabierto.getRuta());
			PDFdecoder.decodePage(PagActual);
			PDFdecoder.setPageParameters(escala, PagActual);
			PDFdecoder.invalidate();
			
			logger.log(Level.INFO, "Abriendo PDF de la ruta " + archivo.getRuta());
		} 
		
		catch (PdfException e) 
		{
			logger.log(Level.SEVERE, e.getMessage());
		}
		
		repaint();
	}
	
	/**
	 * Guarda en la base de datos la última página leída y el tiempo
	 * de lectura del PDF activo en el panel.
	 */
	public void GuardarDatosPDFAnterior()
	{
		String rutaAnterior = PDFabierto.getRuta();
		
		HashSet <clsArchivo> archivosEnBD = clsGestor.LeerArchivosBD();
		clsArchivo archivoAnterior = null;
		
		int tiempo=0; //En segundos
		int ultimaPagLeida;
		
		//Si al abrir el PDF había uno antes, actualizamos tiempo y PagActual
		if(rutaAnterior !=null)
		{
			for (clsArchivo a : archivosEnBD)
			{
				if(a.getRuta().equals(rutaAnterior))
				{
					archivoAnterior = a; //Guardamos el archivo que estaba abierto en la variable auxiliar
				}
			}
			crono.Pause();
			tiempo = archivoAnterior.getTiempo() + crono.getSegundos() ;
			ultimaPagLeida = getPagActual();
			//Le pasamos a modificar el archivo con los nuevos atributos
			archivoAnterior.setTiempo(tiempo);
			archivoAnterior.setUltimaPagLeida(ultimaPagLeida);
			
			clsGestor.ModificarArchivo(archivoAnterior);
			logger.log(Level.INFO, "Archivo modificado");
		}
	}
	
	/**
	 * Incrementa en 1 la página actual del PDF activo en el panel.
	 */
	public void SigPag()
	{
		logger.log(Level.INFO, "La página actual es: " + PagActual);
		
		if(PDFabierto.getRuta() != null && PagActual < PDFdecoder.getPageCount())
		{
			PagActual++;
			try 
			{
				PDFdecoder.decodePage(PagActual);
				PDFdecoder.invalidate();
				repaint();
				
				PDFabierto.setUltimaPagLeida(PagActual);
			}
			catch (PdfException e) 
			{
				logger.log(Level.SEVERE, e.getMessage());
			}
			logger.log(Level.INFO, "La página actual después del incremento es: " + PagActual);
		}
		
	}
	
	/**
	 * Decrementa en 1 la página actual del PDF activo en el panel.
	 */
	public void PagAnt() 
	{
		logger.log(Level.INFO, "La página actual es: " + PagActual);

		if(PDFabierto.getRuta() != null && PagActual > 1)
		{
			PagActual--;
			
			try 
			{
				PDFdecoder.decodePage(PagActual);
				PDFdecoder.invalidate();
				repaint();
				
				PDFabierto.setUltimaPagLeida(PagActual);
			} 
			catch (PdfException e) 
			{
				logger.log(Level.SEVERE, e.getMessage());
			}
			logger.log(Level.INFO, "La página actual después del decremento es: " + PagActual);
		}
	}

	/**
	 * Abre la hoja indicada mediante parámetro del PDF activo en el panel.
	 * @param nuevaPag recibe el número de la hoja que se quiere mostrar.
	 */
	public void irAPag(int nuevaPag) 
	{
		logger.log(Level.INFO, "La página actual es: " + PagActual);
		logger.log(Level.INFO, "La página a la que se quiere ir es: " + nuevaPag);
		
		PagActual = nuevaPag;
		try 
		{
			PDFdecoder.decodePage(PagActual);
			PDFdecoder.invalidate();
			repaint();
			
			PDFabierto.setUltimaPagLeida(PagActual);
		} 
		catch (PdfException e)
		{
			logger.log(Level.SEVERE, e.getMessage());
		}
		
		logger.log(Level.INFO, "La página actual después de la actualización es: " + PagActual);
	}
	
	/**
	 * Permite rotar el PDF activo en el panel.
	 * 
	 * @param rotacionValor el giro en grados que se desea rotar
	 * @param pagActual 
	 */
	public void rotar (int rotacionValor)
	{
		logger.log(Level.INFO, "PDF rotado " + rotacionValor + "º");
		
		PDFdecoder.setPageParameters(escalaActual, PagActual, rotacionValor);
		PDFdecoder.invalidate();
	}
	
	/**
	 * Cierra el PDF activo en el panel.
	 */
	public void CerrarPDF()
	{
		logger.log(Level.INFO, "Cerrado PDF con ruta " + PDFabierto.getRuta());
		
		PDFabierto = null;
		PDFdecoder.closePdfFile();
		PDFdecoder.repaint();
	}

	/**
	 * Permite hacer zoom, es decir, aumentar el escalado del PDF
	 * activo en el panel.
	 * 
	 * @param zoom el porcentaje de zoom que se desea aplicar
	 * @param pagActual ???????????
	 * @param rotacionValor ?????????
	 */
	public void zoom (float zoom, int rotacionValor)
	{
		logger.log(Level.INFO, "Aplicado zoom de " + zoom);
		
		escalaActual = escala*zoom;
		
		PDFdecoder.setPageParameters(escalaActual, PagActual,rotacionValor);
		PDFdecoder.invalidate();
	}

	/**
	 * Permite acceder a la ruta del PDF activo en el panel.
	 * 
	 * @return Devuelve un String que contiene la ruta
	 */
	public String getRuta()
	{
		logger.log(Level.INFO, "Ruta actual: " + PDFabierto.getRuta());
		return PDFabierto.getRuta();
	}
	
	/**
	 * Permite acceder al número total de páginas del PDF activo en el panel.
	 * 
	 * @return Devuelve un int con el número total de páginas
	 */
	public int getPaginasTotal()
	{
		logger.log(Level.INFO, "Páginas totales: " + PDFdecoder.getPageCount());
		
		return PDFdecoder.getPageCount();
	}
	
	/**
	 * Permite acceder al número de página actual del PDF activo en el panel.
	 * 
	 * @return Devuelve el número de página actual
	 */
	public int getPagActual() 
	{
		logger.log(Level.INFO, "Página actual: " + PagActual);
		
		return PagActual;
	}
	
	/**
	 * Permite acceder al objeto clsArchivo del PDF activo en el panel.
	 * 
	 * @return Devuelve el objeto clsArchivo
	 */
	public clsArchivo getPDFabierto()
	{
		logger.log(Level.INFO, "El PDF abierto es " + PDFabierto);
		
		return PDFabierto;
	}
}
