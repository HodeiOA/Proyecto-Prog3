 /**
  * Este programa consiste en un visor de archivos de extensi�n .PDF para su visualizaci�n y gesti�n. 
  * Se trata de un visor de archivos orientado a un uso similar a los de un libro electr�nico, por lo que se har� una divisi�n entre 
  * libros y documentos para poder gestionarlos seg�n su tipolog�a. As� mismo, con cada uno de los archivos el usuario podr� controlar 
  * ciertos aspectos de su lectura, as� como hacer y visualizar comentarios en cada una de las p�ginas de cada uno de los documentos.
  * @author NAGORE CELADA BELTR�N, MAIDER CALZADA AL�EZ y HODEI OLASKOAGA APEZETXEA
  * @version 1.0
**/

package COMUN;

import javax.swing.SwingUtilities;

import LD.clsBD;
import LN.clsGestor;
import LP.frmPrincipal;
import LP.frmRegistro;

public class clsMain 
{
	public static void main(String[] args) 
	{
		clsBD.initBD("BD Pdf reader");
		clsBD.crearTablaArchivo();
		clsBD.crearTablaComentario();
		clsBD.crearTablaUsuario();
		
		clsGestor.ComprobarCarpeta();
		
		//Esto tiene que ir aqu� porque, de ir antes, habr� problemas con la BD
		SwingUtilities.invokeLater(new Runnable()
		{
		    public void run() 
		    {
		    	frmPrincipal frameP = new frmPrincipal("PDF Reader Deusto");
				frameP.setVisible(true);
				frmRegistro loginDlg = new frmRegistro(frameP);
		        loginDlg.setVisible(true);
		    }
		  });
	        
//		clsGestor.guardarComentario(1, "Heyyy, soy un comentariooo!!", 1, 1);
//		clsGestor.guardarComentario(2, "Yo tambi�eeeen :D", 1, 1);
//		clsGestor.guardarComentario(3, "Nah, yo paso :/", 1, 1);
//		clsGestor.guardarComentario(4, "B�rrame", 1, 1);
		
        clsComun.siguienteArchivo();
        clsComun.siguienteComentario(); 
	}
}
