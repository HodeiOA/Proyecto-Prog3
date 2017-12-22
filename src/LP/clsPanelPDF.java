package LP;
import javax.swing.JScrollPane;

import org.jpedal.PdfDecoder;
import org.jpedal.exception.PdfException;

public class clsPanelPDF extends JScrollPane
{
	private PdfDecoder PDFdecoder;
	
	private int PagActual = 1;
	private String ruta;
	
	public clsPanelPDF()
	{
		PDFdecoder = new PdfDecoder(true);
		
		this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.setViewportView(PDFdecoder);
	}
	
	public void abrirPDF(String Ruta)
	{
		PagActual = 1;
		ruta = Ruta;
		
		try {
			PDFdecoder.closePdfFile();
			PDFdecoder.openPdfFile(ruta);
			PDFdecoder.decodePage(PagActual);
			PDFdecoder.setPageParameters(2.8f,1);
			PDFdecoder.invalidate();
		} catch (PdfException e) {}
		
		repaint();
	}
	
	public void SigPag()
	{
		if(ruta != null && PagActual < PDFdecoder.getPageCount())
		{
			PagActual += 1;
			try {
				PDFdecoder.decodePage(PagActual);
				PDFdecoder.invalidate();
				repaint();
			} catch (PdfException e) {}
		}
		// Si ponemos indicador de página, cambiar el número de la variable
	}
	
	public void PagAnt() 
	{
		if(ruta != null && PagActual > 1)
		{
			PagActual -= 1;
			
			try {
				PDFdecoder.decodePage(PagActual);
				PDFdecoder.invalidate();
				repaint();
			} catch (PdfException e) {}
		
		}
		// Si ponemos indicador de página, cambiar el número de la variable
	}
}
