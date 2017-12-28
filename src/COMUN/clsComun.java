package COMUN;
import java.util.ArrayList;
import java.util.HashSet;

import LD.clsBD;
import LN.clsArchivo;
import LN.clsComentario;
import LN.clsGestor;

/**
 */
public class clsComun 
{
	/**
	 */
	public static void siguienteArchivo()
	{
		HashSet <clsArchivo> listaArchivos = new HashSet();
		int codArchivo=0;
		
		listaArchivos = clsBD.LeerArchivos();	//aqu� se le llama a la funci�n  
		
		for(clsArchivo aux: listaArchivos)
		{
			if (codArchivo<aux.getCodArchivo())		
			{
				codArchivo=aux.getCodArchivo();
			}
		}
		codArchivo++;
		clsArchivo.setSigCodArchivo(codArchivo);
	}
	
	public static void siguienteComentario()
	{
		HashSet <clsComentario> listaComentarios = new HashSet();
		int codComentario=0;
		
		listaComentarios = clsBD.LeerComentarios();	//aqu� se le llama a la funci�n  
		
		for(clsComentario aux: listaComentarios)
		{
			if (codComentario<aux.getID())		
			{
				codComentario=aux.getID();
			}
		}
		codComentario++;
		clsComentario.setSigID(codComentario);
	}
}