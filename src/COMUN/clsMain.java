package COMUN;

import LD.clsBD;
import LP.frmPrincipal;
import LP.frmRegistro;

public class clsMain 
{
	
	public static void main(String[] args) 
	{
		frmPrincipal frameP = new frmPrincipal("PDF Reader Deusto");
		frameP.setVisible(true);
		
		clsBD.initBD("BD Pdf reader");
		clsBD.crearTablaArcivo();
		clsBD.crearTablaComentario();
		clsBD.crearTablaUsuario();
		
		frmRegistro loginDlg = new frmRegistro(frameP);
        loginDlg.setVisible(true);
        
        clsComun.sigueinteArchivo();
	}

}
