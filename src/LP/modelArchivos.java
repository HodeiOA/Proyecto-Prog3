package LP;

import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.DefaultListModel;

import LN.clsArchivo;

/**
 * Clase de tipo DefaultListModel de solo objetos clsArchivo.
 * 
 * @author
 *
 */
public class modelArchivos extends DefaultListModel<clsArchivo>
{
	HashSet<clsArchivo> ArchivosModel = new HashSet<clsArchivo>();
	
	/**
	 * El constructor inicializa el atributo HashSet del ListModel.
	 * 
	 * @param lista recibe la lista que contendrá el ListModel
	 */
	public modelArchivos(HashSet<clsArchivo> lista)
	{
		ArchivosModel = lista;
	}
	
	/**
	 * Devuelve el objeto que se encuentra en el índice especificado.
	 * 
	 * @param index la posición del elemento
	 * @return Devuelve un objeto de tipo clsArchivo
	 */
	public clsArchivo getElementAt(int index)
	{
		ArrayList<clsArchivo> arrayArchivos = new ArrayList<clsArchivo>();;
		
		for(clsArchivo aux: ArchivosModel)
		{
			arrayArchivos.add(aux);
		}
		
		return arrayArchivos.get(index);
	}
	
	/**
	 * Permite acceder al número total de elementos de la lista
	 * 
	 * @return Devuelve un int que contiene el número de elementos
	 */
	public int getSize()
	{
		return ArchivosModel.size();
	}
	
	/**
	 * Permite añadir un elemento a la lista
	 * 
	 * @param element recibe el objeto clsArchivo que debe añadir
	 */
	public void addElement(clsArchivo element)
	{
		ArchivosModel.add(element);
		fireContentsChanged(this, ArchivosModel.size(), ArchivosModel.size());
	}
	
	/**
	 * Permite actualizar el atributo HashSet del ListModel. 
	 * 
	 * @param lista recibe la nueva lista que se quiere introducir
	 */
	public void setLista(HashSet<clsArchivo> lista)
	{
		ArchivosModel = lista;
		fireContentsChanged(this, ArchivosModel.size(), ArchivosModel.size());
	}
}
