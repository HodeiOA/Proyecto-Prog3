package COMUN;

import LD.clsBD;
import LP.frmPrincipal;
import LP.frmRegistro;

public class clsMain 
{
	
	public static void main(String[] args) 
	{
		clsBD.initBD("BD Pdf reader");
		clsBD.crearTablaArcivo();
		clsBD.crearTablaComentario();
		clsBD.crearTablaUsuario();
		
		//Esto tiene que ir aquí porque, de ir antes, habrá problemas con la BD
		frmPrincipal frameP = new frmPrincipal("PDF Reader Deusto");
		frameP.setVisible(true);
		
		frmRegistro loginDlg = new frmRegistro(frameP);
       loginDlg.setVisible(true);
        
        clsComun.sigueinteArchivo();
	}

}
