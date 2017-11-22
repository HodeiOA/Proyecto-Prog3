package COMUN;

import java.io.File;
import LP.frmFileChooser;

public class clsMain 
{

	public static void main(String[] args) 
	{
//		frmPrincipal frmPrinci = new frmPrincipal("titulo");
//		frmPrinci.setVisible(true);
		File j= null;
		frmFileChooser fc= new frmFileChooser(j);
		fc.setVisible(true);
	}

}
