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
	        
        clsComun.siguienteArchivo();
        clsComun.siguienteComentario(); 
	}
}
