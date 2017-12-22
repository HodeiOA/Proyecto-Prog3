package LP;

import java.util.HashSet;

import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

import LN.clsArchivo;

public class modelArchivos implements ListModel
{
	private Object[][] info;
	private int filas;
	private int contador=0;
	
	public void cargarInfo(HashSet <clsArchivo> HashArchivos )
	{
		filas=HashArchivos.size();
		info = new Object[filas][];
		
		for(clsArchivo a: HashArchivos)
		{
			String titulo = a.getTitulo();
			String ruta=a.getRuta();
			String apeAutor=a.getApeAutor();
			String nomAutor=a.getNomAutor();
			int codArchivo=a.getCodArchivo();
			int numPags=a.getNumPags();	
			int ultimaPagLeida = a.getUltimaPagLeida();
			int tiempo= a.getTiempo();	

			Object [] filas = {titulo, ruta, nomAutor, apeAutor, codArchivo, numPags, ultimaPagLeida, tiempo};
			info[contador]=filas;
			contador++;
		}
	}
	
	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getElementAt(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		// TODO Auto-generated method stub
		
	}

}
