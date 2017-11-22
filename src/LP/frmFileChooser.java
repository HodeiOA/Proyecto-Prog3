package LP;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class frmFileChooser extends JFileChooser
{
	public frmFileChooser (File f)
	{
		setFileFilter( new FileNameExtensionFilter( "Ficheros lista de reproducción", "vpd" ) );
		setFileSelectionMode( JFileChooser.FILES_ONLY );
	}

}
