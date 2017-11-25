package COMUN;

import LP.frmPrincipal;
import LP.frmRegistro;

public class clsMain 
{
	
	public static void main(String[] args) 
	{
		frmPrincipal frameP = new frmPrincipal("PDF Reader Deusto");
		frameP.setVisible(true);
		
		frmRegistro loginDlg = new frmRegistro(frameP);
        loginDlg.setVisible(true);
	}

}
