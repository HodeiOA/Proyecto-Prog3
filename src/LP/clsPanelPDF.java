package LP;
import java.awt.Color;
import java.io.File;

import javax.swing.JScrollPane;

import org.jpedal.PdfDecoder;
import org.jpedal.exception.PdfException;

import LN.clsArchivo;

public class clsPanelPDF extends JScrollPane
{
	private PdfDecoder PDFdecoder;
	
	private int PagActual = 1;
	private String ruta;
	
	public clsPanelPDF()
	{
		PDFdecoder = new PdfDecoder();
		
		PDFdecoder.setBackground(Color.DARK_GRAY);
		this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.setViewportView(PDFdecoder);
	}
	
	public void abrirPDF(clsArchivo archivo)
	{
		PagActual = 1;
		ruta = archivo.getRuta();
		
		try {
			PDFdecoder.closePdfFile();
			PDFdecoder.openPdfFile(ruta);
			PDFdecoder.decodePage(PagActual);
			PDFdecoder.setPageParameters(2.8f, PagActual);
			PDFdecoder.invalidate();
		} catch (PdfException e) {}
		
		repaint();
	}
	
	public void SigPag()
	{
		if(ruta != null && PagActual < PDFdecoder.getPageCount())
		{
			PagActual++;
			try {
				PDFdecoder.decodePage(PagActual);
				PDFdecoder.invalidate();
				repaint();
			} catch (PdfException e) {}
		}
		// Si ponemos indicador de p�gina, cambiar el n�mero de la variable
	}
	
	public void PagAnt() 
	{
		if(ruta != null && PagActual > 1)
		{
			PagActual--;
			
			try {
				PDFdecoder.decodePage(PagActual);
				PDFdecoder.invalidate();
				repaint();
			} catch (PdfException e) {}
		
		}
		// Si ponemos indicador de p�gina, cambiar el n�mero de la variable
	}

	public int getPagActual() 
	{
		return PagActual;
	}

	public void irAPag(int nuevaPag) 
	{
		PagActual = nuevaPag;
		try {
			PDFdecoder.decodePage(PagActual);
			PDFdecoder.invalidate();
			repaint();
		} catch (PdfException e) {}
	}

	public String getRuta()
	{
		return ruta;
	}
	
	public int getPaginasTotal()
	{
		return PDFdecoder.getPageCount();
	}
	
}
