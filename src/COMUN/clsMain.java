package COMUN;

import java.awt.FlowLayout;

import javax.swing.JFrame;

import LP.frmPrincipal;
import LP.frmRegistro;

public class clsMain 
{
	
	public static void main(String[] args) 
	{
		 final JFrame frame = new JFrame("Bienvenido, reg�strate o inicia sesi�n");

		 frmRegistro loginDlg = new frmRegistro(frame);
         loginDlg.setVisible(true);
         
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         frame.setSize(300, 100);
         frame.setLayout(new FlowLayout());
         frame.setVisible(true);
		
		frmPrincipal frameP = new frmPrincipal("PDF Reader Deusto");
		frameP.setVisible(true);
		
		

		
	}

}
