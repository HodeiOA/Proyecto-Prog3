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
		
		listaArchivos = clsGestor.LeerArchivosBD();	
		// Logger de info: empieza a leer los c�digos
		for(clsArchivo aux: listaArchivos)
		{
			// c�digo le�do = aux.getCodArchivo()
			if (codArchivo<aux.getCodArchivo())		
			{
				//c�digo m�s alto hasta el moemnto codArchivo
				codArchivo=aux.getCodArchivo();
			}
		}
		codArchivo++;
		//C�digo asignado
		clsArchivo.setSigCodArchivo(codArchivo);
	}
	
	public static void siguienteComentario()
	{
		HashSet <clsComentario> listaComentarios = new HashSet();
		int codComentario=0;
		
		listaComentarios = clsGestor.LeerComentariosBD();	
		
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