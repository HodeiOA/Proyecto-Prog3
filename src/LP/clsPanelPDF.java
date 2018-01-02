package LP;
import java.awt.Color;
import java.io.File;
import java.util.HashSet;

import javax.swing.JScrollPane;
import javax.swing.JViewport;

import org.jpedal.PdfDecoder;
import org.jpedal.exception.PdfException;

import LN.clsArchivo;
import LN.clsCronometro;
import LN.clsGestor;

public class clsPanelPDF extends JScrollPane
{
	private PdfDecoder PDFdecoder;
	
	private int PagActual = 1;
	private String ruta;
	int velocidadScroll = 15;
	int nuevaRotacion = 90;
	float escala = 2;
	
	static clsCronometro crono;
	
	public clsPanelPDF()
	{
		PDFdecoder = new PdfDecoder();
		
		this.getVerticalScrollBar().setUnitIncrement(velocidadScroll); //Esto habrá que cambiarlo de el ZOOM: Si se amplia, irá más rápido. Si se aleja, más despacio
		
		PDFdecoder.setBackground(Color.DARK_GRAY);
		this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.setViewportView(PDFdecoder);
	}
	
	public void abrirPDF(clsArchivo archivo)
	{		
		try 
		{
			this.GuardarDatosPDFAnterior(this);
			ruta = archivo.getRuta();
			this.irAPag(archivo.getUltimaPagLeida());
			crono=new clsCronometro(); //Para asegurarnos de que vuelve a empezar desde cero
			crono.run();
			crono.Play();
			
			PDFdecoder.closePdfFile();
			PDFdecoder.openPdfFile(ruta);
			irAPag(archivo.getUltimaPagLeida()); //Le abrimos el archivo desde la última página que leyó
			PDFdecoder.decodePage(PagActual);
			PDFdecoder.setPageParameters(escala, PagActual);
			PDFdecoder.invalidate();
		} 
		
		catch (PdfException e) 
		{
			//e.getMessage()
		}
		
		repaint();
	}
	
	public static void GuardarDatosPDFAnterior(clsPanelPDF panelPDF)
	{
		String rutaAnterior = panelPDF.getRuta();
		int horas;
		int miin;
		int seg;
		
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
//			tiempo = archivoAnterior.getTiempo() + crono.getSegundos() ;
			ultimaPagLeida = panelPDF.getPagActual();
			//Le pasamos a modificar el archivo con los nuevos atributos
			archivoAnterior.setTiempo(tiempo);
			archivoAnterior.setUltimaPagLeida(ultimaPagLeida);
			
			clsGestor.ModificarArchivo(archivoAnterior);
		}
	}
	
	public void SigPag()
	{
		//INFO: PagActual
		if(ruta != null && PagActual < PDFdecoder.getPageCount())
		{
			PagActual++;
			try 
			{
				PDFdecoder.decodePage(PagActual);
				PDFdecoder.invalidate();
				repaint();
			}
			catch (PdfException e) 
			{
				//e.getMessage()
			}
		}
		//INFO: PagActual
		// Si ponemos indicador de página, cambiar el número de la variable
	}
	
	public void PagAnt() 
	{
		if(ruta != null && PagActual > 1)
		{
			PagActual--;
			
			try 
			{
				PDFdecoder.decodePage(PagActual);
				PDFdecoder.invalidate();
				repaint();
			} 
			catch (PdfException e) 
			{
				//e.getMessage()
			}
		
		}
		// Si ponemos indicador de página, cambiar el número de la variable
	}

	public void irAPag(int nuevaPag) 
	{
		//PagActual
		//nuevaPag
		PagActual = nuevaPag;
		try 
		{
			PDFdecoder.decodePage(PagActual);
			PDFdecoder.invalidate();
			repaint();
		} 
		catch (PdfException e)
		{
			//e.getMessage()
		}
		//PagActual
	}
	
	/*
     * Set the page's rotation
     * A value of 90 will set the rotation to 90º
     */
	public void setDisplayRotation (int nuevaRotacion)
	{
//		rotate(Math.toRadians(nuevaRotacion));
	}

	/*
     * Initialise panel and set size to fit PDF page with rotation set to the default
     * To keep existing scaling setting set scaling value to -1
     */
	public void setPageParameters(float zoom, int pagActual)
	{
		PDFdecoder.setPageParameters(escala*zoom, pagActual);
		PDFdecoder.invalidate();
	}

	public String getRuta()
	{
		return ruta;
	}
	
	public int getPaginasTotal()
	{
		return PDFdecoder.getPageCount();
	}
	
	public int getPagActual() 
	{
		return PagActual;
	}
}
