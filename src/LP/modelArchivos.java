package LP;

import java.util.HashSet;

import javax.swing.DefaultListModel;

import LN.clsArchivo;

public class modelArchivos extends DefaultListModel<clsArchivo>
{
	HashSet <clsArchivo> ArchivosModel = new HashSet <clsArchivo>();
	
	public modelArchivos(HashSet<clsArchivo> lista)
	{
		ArchivosModel = lista;
	}
	
	public clsArchivo getElementAt(int index)
	{
		clsArchivo[] arrayArchivos = (clsArchivo[]) ArchivosModel.toArray();
		
		return arrayArchivos[index];
	}
	
	public int getSize()
	{
		return ArchivosModel.size();
	}
	
	public void addElement(clsArchivo element)
	{
		ArchivosModel.add(element);
		fireContentsChanged(this, ArchivosModel.size(), ArchivosModel.size());
	}
	
	public void setLista(HashSet<clsArchivo> lista)
	{
		ArchivosModel = lista;
		fireContentsChanged(this, ArchivosModel.size(), ArchivosModel.size());
	}
	

}
