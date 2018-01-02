package LN;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
		retorno=archi.getUltimaPagLeida()/archi.getNumPags() *100; //INFO: archi.getUltimaPagLeida(), archi.getNumPags()
		return retorno; //retorno
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
			//WARNING: existe
		}
		else
		{
			clsNickNoExiste noExiste= new clsNickNoExiste(nick);
			throw noExiste;
			//WARNING: no existe
		}
    }
	
	public static void llenarLibrosDocum(HashSet <clsArchivo> HashArchivos, HashSet <clsArchivo> HashLibros, HashSet <clsArchivo> HashDocumentos)
	{
		if( HashArchivos !=null)
		{
			for (clsArchivo a: HashArchivos )
			{
				if(a.getLibroSi())//INFO: libroSI
				{
					HashLibros.add(a); //añadido a libros
				}
				else
				{
					HashDocumentos.add(a); //añadido a documentos
				}
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
			//Poner Logger
		}
	}
	
	//DELETE
	/**
	 * 
	 * @param ident
	 * @param tabla debe ser un valor de entre "ARCHIVO", "COMENTARIO" y "USUARIO". de lo contrario, no hará nada
	 */
	public static void BorrarObjetoBD(Object ident, String tabla)
	{
		clsBD.BorrarFila(ident, tabla);
	}
	
	//UPDATES
	public static void ModificarArchivo(clsArchivo nuevo)
	{
		clsBD.UpdateArchivo(nuevo.getNomAutor(), nuevo.getApeAutor(), nuevo.getCodArchivo(), nuevo.getTitulo(), nuevo.getRuta(), nuevo.getNumPags(), nuevo.getUltimaPagLeida(), nuevo.getTiempo(), nuevo.getLibroSi());
	}
	
	public static void ModificarUsuario()
	{
		
	}
	
	public static void ModificarComentario()
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
	
	public static String RecogerTitulo(String Ruta) // Falta llamar desde los sitios que hace falta
	{
		String aux;
		String[] CorteBarras;
		String[] CortePunto;
		
		CorteBarras = Ruta.split("\\\\");
		
		aux = CorteBarras[CorteBarras.length-1];
		
		CortePunto = aux.split("\\.");
		
		aux = CortePunto[0];
		
		System.out.println(aux);
		
		return aux;
	}
	
	public static void ModificarRuta(clsArchivo Archivo) // Falta llamar desde los sitios que hace falta
	{
		String NombreArchivo;
		Path FROM;
		Path TO;
		
		NombreArchivo = Archivo.getTitulo() + ".pdf";
		
		FROM = Paths.get(Archivo.getRuta());
		if(Archivo.getRuta().contains("Libro"))
		{
			TO = Paths.get(".\\Data\\Libros");
		} else
		{
			TO = Paths.get(".\\Data\\Documentos");
		}
		
		Archivo.setRuta(TO + "\\" + NombreArchivo);
		
		try {
			Files.move(FROM, TO.resolve(NombreArchivo));
		} catch (IOException e) {}
	}
	
	public static void ComprobarNombreRepetido(HashSet<clsArchivo> Lista, clsArchivo Archivo, int Contador)
	{
		String Nombre = Archivo.getTitulo();
		
		for(clsArchivo aux: Lista)
		{
			if(Contador != 0)
			{
				Nombre = Nombre + " (" + Contador + ")";
			}
			
			if(aux.getTitulo().equals(Nombre))
				ComprobarNombreRepetido(Lista, Archivo, Contador+1);
		}
		
		Archivo.setTitulo(Nombre);
		ModificarRuta(Archivo);
	}
}
