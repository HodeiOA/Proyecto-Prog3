package LP;

import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

import LN.clsArchivo;

public class modelArchivos extends DefaultListModel <clsArchivo>
{
	private String[] info;
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
		ArchivosModel=HashArchivos;
		cargarInfo(HashArchivos);
	}
	
	public void cargarInfo(HashSet <clsArchivo> HashArchivos )
	{
		filas=HashArchivos.size();
		
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

			String algo = ""+titulo +", " + nomAutor +", "+ apeAutor +"páginas: " + numPags + " tiempo: " +tiempo;
			System.out.println(algo);
//			this.addElement(algo);
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
	public clsArchivo getElementAt(int index) 
	{
		// TODO Auto-generated method stub
		Object[] a= ArchivosModel.toArray();
		return (clsArchivo) a[index];
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
//	@Override
//	public void addElement(Object element) 
//	{
//		// TODO Auto-generated method stub
//		super.addElement(element);
//	}
	

}
