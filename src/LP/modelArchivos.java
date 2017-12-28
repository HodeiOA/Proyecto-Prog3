package LP;

import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.AbstractListModel;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

import LN.clsArchivo;

public class modelArchivos extends AbstractListModel implements ListModel
{
	private Object[][] info;
	HashSet <clsArchivo> ArchivosModel = new HashSet <clsArchivo>();
	private int filas;
	private int contador=0;
	
	/**
	 * Este método llamará al constructor de la clase padre y al método de cargar la información
	 * @param emp le pasamos el arraylist de empleados.
	 */
	public modelArchivos(HashSet <clsArchivo> HashArchivos)
	{
		super();	
		cargarInfo(HashArchivos);
		ArchivosModel=HashArchivos;
	}
	public void cargarInfo(HashSet <clsArchivo> HashArchivos )
	{
		filas=HashArchivos.size();
		info = new Object[filas][];
		
		for(clsArchivo a: HashArchivos)
		{
			HashArchivos.add(a);
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
	public int getSize() 
	{
		// TODO Auto-generated method stub
		return contador;
	}

	@Override
	public Object getElementAt(int index) 
	{
		// TODO Auto-generated method stub
		Object[] a= ArchivosModel.toArray();
		return a[index];
	}

	@Override
	public void addListDataListener(ListDataListener l)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		// TODO Auto-generated method stub
		
	}

}
