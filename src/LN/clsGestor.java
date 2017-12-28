package LN;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

import COMUN.clsNickRepetido;
import COMUN.clsNickNoExiste;
import LD.clsBD;

public class clsGestor
{
	public static double porcentLeido(clsArchivo archi)
	{
		double retorno;
		retorno=archi.getUltimaPagLeida()/archi.getNumPags() *100;
		return retorno;
	}
	
	//métodos para login
	public static void comprobarExistenciaUsuario(String nick, String contraseña) throws clsNickRepetido, clsNickNoExiste
    {
		boolean existe;
		boolean insert;
    	//leemos la base de datos, sacamos el nick y contraseña y lo metemos en un arraylist
		HashSet <clsUsuario> usuarioBD = clsBD.LeerUsuarios();
		clsUsuario nuevoUsuario = new clsUsuario (nick, contraseña);
		
		//Si lo introduce. da true, con lo cual no existía, por lo que retorno false
		//Si ya existía el add da false, nosotros devolvemos false.
		existe = !(usuarioBD.add(nuevoUsuario));
		if(existe)
		{
			clsNickRepetido repe=new clsNickRepetido(nick);
			throw repe;
		}
		else
		{
			clsNickNoExiste noExiste= new clsNickNoExiste(nick);
			throw noExiste;
			
		}
		
		//return existe;
    }
	
	public static void llenarLibrosDocum(HashSet <clsArchivo> HashArchivos, HashSet <clsArchivo> HashLibros, HashSet <clsArchivo> HashDocumentos)
	{
		for (clsArchivo a: HashArchivos )
		{
			if(a.getLibroSi())
			{
				HashLibros.add(a);
			}
			else
			{
				HashDocumentos.add(a);
			}
		}
	}
	
	//Solo hacemos llamadas a BD, pero hacemos métodods en Gestor para mantener la estructura del programa
	
	//INSERTS
	public static void guardarArchivo(String nomAutor, String apeAutor, int codArchivo, String titulo, String ruta, int numPags, int ultimaPagLeida, int tiempo,  boolean libroSi)
	{ 
		boolean exito;
		exito=clsBD.InsertArchivo(nomAutor, apeAutor, codArchivo, titulo, ruta, numPags, ultimaPagLeida, tiempo, libroSi); //Cada vez que se cierre el pdf, cambiar ultimaPagleida y tiempo
		if(!exito)
		{
			//no se ha podido guardar
		}
	}
	public static void guardarUsuario(String contraseña, String nick)
	{
		boolean exito;
		exito=clsBD.InsertUsuario(contraseña, nick);
		if(!exito)
		{
			//no se ha podido guardar
		}
	}
	public static void guardarComentario(int ID, String texto, int codArchivo, int numPag)
	{
		boolean exito;
		exito=clsBD.InsertComentario(ID, texto, codArchivo, numPag);
		if(!exito)
		{
			//no se ha podido guardar
		}
	}
	
	//DELETE
	/**
	 * 
	 * @param ident
	 * @param tabla debe ser un valor de entre "ARCHIVO", "COMENTARIO" y "USUARIO". de lo contrario, no hará nada
	 */
	public void BorrarObjetoBD(Object ident, String tabla)
	{
		clsBD.BorrarFila(ident, tabla);
	}
	
	//UPDATES
	public void ModificarArchivo()
	{
		
	}
	
	public void ModificarUsuario()
	{
		
	}
	
	public void ModificarComentario()
	{
		
	}
	
	//Lecturas
	public static HashSet <clsArchivo> LeerArchivosBD()
	{
		 HashSet <clsArchivo> retorno = clsBD.LeerArchivos();
		 return retorno;
	}
	public static HashSet <clsUsuario> LeerUsuariosBD()
	{
		 HashSet <clsUsuario> retorno = clsBD.LeerUsuarios();
		 return retorno;
	}
	public static HashSet <clsComentario> LeerComentariosBD()
	{
		HashSet <clsComentario> retorno = clsBD.LeerComentarios();
		return retorno;
	}
}
