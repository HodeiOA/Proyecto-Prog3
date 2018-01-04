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
	private static clsArchivo PDFabierto;
	private int PagActual = 1;
	int velocidadScroll = 15;
	int rotacion = 90;
	float escala = 2;
	float escalaActual;
	
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
		} 
		
		catch (PdfException e) 
		{
			//e.getMessage()
		}
		
		repaint();
	}
	
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
		}
	}
	
	public void SigPag()
	{
		//INFO: PagActual
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
				//e.getMessage()
			}
		}
		//INFO: PagActual
		// Si ponemos indicador de página, cambiar el número de la variable
	}
	
	public void PagAnt() 
	{
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
			
			PDFabierto.setUltimaPagLeida(PagActual);
		} 
		catch (PdfException e)
		{
			//e.getMessage()
		}
		//PagActual
	}
	
	public void rotar (int rotacionValor, int pagActual)
	{
	
		PDFdecoder.setPageParameters(escalaActual, PagActual, rotacionValor);
		PDFdecoder.invalidate();
	}
	
	public void CerrarPDF()
	{
		PDFabierto = null;
		PDFdecoder.closePdfFile();
		PDFdecoder.repaint();
	}

	public void zoom (float zoom, int pagActual, int rotacionValor)
	{
		escalaActual = escala*zoom;
		
		PDFdecoder.setPageParameters(escalaActual, pagActual,rotacionValor);
		PDFdecoder.invalidate();
	}

	public String getRuta()
	{
		return PDFabierto.getRuta();
	}
	
	public int getPaginasTotal()
	{
		return PDFdecoder.getPageCount();
	}
	
	public int getPagActual() 
	{
		return PagActual;
	}
	
	public clsArchivo getPDFabierto()
	{
		return PDFabierto;
	}
}
