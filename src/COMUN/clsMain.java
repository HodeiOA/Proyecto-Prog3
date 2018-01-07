 /**
  * Este programa consiste en un visor de archivos de extensión .PDF para su visualización y gestión. 
  * Se trata de un visor de archivos orientado a un uso similar a los de un libro electrónico, por lo que se hará una división entre 
  * libros y documentos para poder gestionarlos según su tipología. Así mismo, con cada uno de los archivos el usuario podrá controlar 
  * ciertos aspectos de su lectura, así como hacer y visualizar comentarios en cada una de las páginas de cada uno de los documentos.
  * @author NAGORE CELADA BELTRÁN, MAIDER CALZADA ALÁEZ y HODEI OLASKOAGA APEZETXEA
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
		
		//Esto tiene que ir aquí porque, de ir antes, habrá problemas con la BD
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
//		clsGestor.guardarComentario(2, "Yo tambiéeeeen :D", 1, 1);
//		clsGestor.guardarComentario(3, "Nah, yo paso :/", 1, 1);
//		clsGestor.guardarComentario(4, "Bórrame", 1, 1);
		
        clsComun.siguienteArchivo();
        clsComun.siguienteComentario(); 
	}
}
