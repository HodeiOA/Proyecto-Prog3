package LN;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.logging.Level;

import org.jpedal.PdfDecoder;
import org.jpedal.exception.PdfException;

import COMUN.clsNickRepetido;
import COMUN.clsNickNoExiste;
import LD.clsBD;

/**
 * Esta clase gestionará los métodos necesarios para realizar las acciones solicitadas por las pantallas así como el puente
 * entre la lógica de datos y de presentación
 *
 */
public class clsGestor
{
	/**
	 * Método para el cálculo del porcentaje leído de un archivo en el momento de llamar al método 
	 * @param archi archivo del que se calculará el porcentaje de lectura
	 * @return devuelve un int con el valor del porcentaje leído
	 */
	public static int porcentLeido(clsArchivo archi)
	{
		int retorno;
		float aux;
		
		aux = (float) archi.getUltimaPagLeida()/archi.getNumPags();
		aux = aux*100;
		retorno = (int) aux; //INFO: archi.getUltimaPagLeida(), archi.getNumPags()
		
		return retorno; //retorno
	}
	
	/**
	  * Método para obtener el número de páginas de un archivo sin necesidad de abrirlo en la pantalla
	  * @param Ruta ruta del archivo cuya cantidad de páginas se quiere consultar
	  * @return devuelve un int con el número de páginas
	  * @throws PdfException Excepción que se puede llegar a lanzar en caso de que la ruta sea errónea
	  */
	public static int conseguirNumPags (String Ruta) throws PdfException
	{
		int retorno;
		PdfDecoder PDFdecoder;
		String ruta = Ruta;
		
		PDFdecoder = new PdfDecoder();
		
		PDFdecoder.openPdfFile(ruta);
		
		retorno = PDFdecoder.getPageCount();
		
		return retorno;
	}
	
	//métodos para login
	/**
	 * Método que comprueba si un usuario está o no ya en la base de datos
	 * @param nick
	 * @param contraseña
	 * @throws clsNickRepetido excepción que indica que el usuario sí que estaba en la base de datos
	 * @throws clsNickNoExiste excepción que indica que el usuario no estaba en la base de datos
	 */
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
	
	/**
	  * Método para llenar las listas de libros y documentos según su tipo (libro o documento) y el nick del usuario ciuyas listas se
	  * deben cargar
	  * @param nickUsuarioSesion nick del usuario cuyos archivos se están solicitando
	  * @param HashArchivos lista con todos los archivos de la base de datos
	  * @param HashLibros lista en la que se almacenarán los libros de la BD correspondientes al usuario
	  * @param HashDocumentos lista en la que se almacenarán los documentos de la BD correspondientes al usuario
	  */
	public static void llenarLibrosDocum(String nickUsuarioSesion, HashSet <clsArchivo> HashArchivos, HashSet <clsArchivo> HashLibros, HashSet <clsArchivo> HashDocumentos)
	{
		HashLibros.clear();
		HashDocumentos.clear();
		if( HashArchivos !=null)
		{
			for (clsArchivo a: HashArchivos )
			{				
				if(a.getNick().equals(nickUsuarioSesion))
				{
					if(a.getLibroSi())//INFO: libroSI
					{
						HashLibros.add(a); //añadido a libros
						System.out.println("libro a hash");
					}
					else
					{
						HashDocumentos.add(a); //añadido a documentos
						System.out.println("docum a hash");
					}
				}
			}
		}			
	}
	
	//Solo hacemos llamadas a BD, pero hacemos métodods en Gestor para mantener la estructura del programa
	
	//INSERTS
	/**
	 * Método para guardar un archivo en la BD. Se le pasarán los atributos
	 * @param nick
	 * @param nomAutor
	 * @param apeAutor
	 * @param codArchivo
	 * @param titulo
	 * @param ruta
	 * @param numPags
	 * @param ultimaPagLeida
	 * @param tiempo
	 * @param libroSi
	 */
	public static void guardarArchivo(String nick, String nomAutor, String apeAutor, int codArchivo, String titulo, String ruta, int numPags, int ultimaPagLeida, int tiempo,  boolean libroSi)
	{ 
		boolean exito;
		exito=clsBD.InsertArchivo(nick, nomAutor, apeAutor, codArchivo, titulo, ruta, numPags, ultimaPagLeida, tiempo, libroSi); //Cada vez que se cierre el pdf, cambiar ultimaPagleida y tiempo
		if(!exito)
		{
			//no se ha podido guardar
		}
	}
	
	/**
	 * Método para guardar un usuario en la BD. Se le pasarán sus atributos:
	 * @param contraseña
	 * @param nick
	 */
	public static void guardarUsuario(String contraseña, String nick)
	{
		boolean exito;
		exito=clsBD.InsertUsuario(contraseña, nick);
		if(!exito)
		{
			//no se ha podido guardar
		}
	}
	
	/**
	 * Método para guardar un comentario en la BD. Se le pasan sus atributos:
	 * @param ID
	 * @param texto
	 * @param codArchivo
	 * @param numPag
	 */
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
	 * Método para eliminar una línea de la tabla de la BD de alguno de los objetos de la BD.
	 * @param ident atributo identificativo del objeto a borrar
	 * @param tabla debe ser un valor de entre "ARCHIVO", "COMENTARIO" y "USUARIO". de lo contrario, no hará nada
	 */
	public static void BorrarObjetoBD(Object ident, String tabla)
	{
		clsBD.BorrarFila(ident, tabla);
	}
	
	//UPDATES
	/**
	 * Método para modificar uno de los archivos almacenados en la BD
	 * @param nuevo archivo con los atributos ya modificados
	 */
	public static void ModificarArchivo(clsArchivo nuevo)
	{
		clsBD.UpdateArchivo(nuevo.getNick(), nuevo.getNomAutor(), nuevo.getApeAutor(), nuevo.getCodArchivo(), nuevo.getTitulo(), nuevo.getRuta(), nuevo.getNumPags(), nuevo.getUltimaPagLeida(), nuevo.getTiempo(), nuevo.getLibroSi());
	}
		
	/**
	 * Método para modificar uno de los usuarios almacenados en la BD. Atributos ya modificados:
	 * @param contraseña
	 * @param nick
	 */
	public static void ModificarUsuario(String contraseña, String nick)
	{
		clsBD.UpdateUsuario(contraseña, nick);
	}
		
	/**
	 * Método para modificar uno de los comentarios almacenados en la BD. Atributos ya modificados:
	 * @param ID
	 * @param codArchivo
	 * @param texto
	 * @param numPag
	 */
	public static void ModificarComentario(int ID, int codArchivo, String texto, int numPag)
	{
		clsBD.UpdateComentario(ID, texto, codArchivo, numPag);
	}
	
	//Lecturas
	/**
	 * Método para obtener todos los archivos de la BD
	 * @return devuelve la lista con los archivos leídos
	 */
	public static HashSet <clsArchivo> LeerArchivosBD()
	{
		 HashSet <clsArchivo> retorno = clsBD.LeerArchivos();
		 return retorno;
	}
	
	/**
	 * Método para obtener todos los usuarios de la BD
	 * @return devuelve la lista con los usuarios leídos
	 */
	public static HashSet <clsUsuario> LeerUsuariosBD()
	{
		 HashSet <clsUsuario> retorno = clsBD.LeerUsuarios();
		 return retorno;
	}
	
	/**
	 * Método para obtener todos los cometarios de la BD
	 * @return devuelve la lista con los comentarios leídos
	 */
	public static HashSet <clsComentario> LeerComentariosBD()
	{
		HashSet <clsComentario> retorno = clsBD.LeerComentarios();
		return retorno;
	}
	
	/**
	 * Método para conseguir el título de un archivo a partir de una ruta
	 * @param Lista Lista con todos los archivos de la BD para poder comprobar si el título generado está o no repetido 
	 * (a través de otro método)
	 * @param Ruta Ruta a partir de la cual vamos a conseguir el título
	 * @return
	 */
	public static String RecogerTitulo(HashSet<clsArchivo> Lista, String Ruta) // Falta llamar desde los sitios que hace falta
	{
		String Nombre;
		String[] CorteBarras;
		String[] CortePunto;
		
		CorteBarras = Ruta.split("\\\\");
		
		Nombre = CorteBarras[CorteBarras.length-1];
		
		CortePunto = Nombre.split("\\.");
		
		Nombre = CortePunto[0];
		
		Nombre = ComprobarNombreRepetido(Lista, Nombre, 0);
		
		return Nombre;
	}
	
	/**
	 * Método para que, al crear un archivo, su ruta se modifique de la ruta de la que se importó a la que pasará a ocupar en la carpeta Data
	 * @param Archivo archivo cuya ruta se va a modificar
	 */
	public static void ModificarRuta(clsArchivo Archivo) // Falta llamar desde los sitios que hace falta
	{
		String NombreArchivo;
		Path FROM;
		Path TO;
		
		NombreArchivo = Archivo.getTitulo() + ".pdf";
		
		FROM = Paths.get(Archivo.getRuta());
		if(Archivo.getLibroSi())
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
	
	/**
	 * Método para comprobar si el título asignado a asignar a un archivo ya está en la BD y, si lo está, añadirle un número para diferenciarlo
	 * @param Lista Lista con todos los archivos cuyos títulos se van a comparar con el del archivo actual
	 * @param paramNombre t´tulo provisional del archivo
	 * @param Contador número que en el caso base se le asignará al título
	 * @return
	 */
	public static String ComprobarNombreRepetido(HashSet<clsArchivo> Lista, String paramNombre, int Contador)
	{
		String Nombre = paramNombre;
		
		if(Contador != 0)
		{
			Nombre = Nombre + " (" + Contador + ")";
		}
		
		for(clsArchivo aux: Lista)
		{
			if(aux.getTitulo().equals(Nombre))
				Nombre = ComprobarNombreRepetido(Lista, paramNombre, Contador+1);
		}
		
		return Nombre;
	}
	

	/**
	 * Método para comprobar si las carpetas en las que se van a almacenar los archivos que se vayan importando. En caso de que 
	 * no existan, se crearán
	 */
	public static void ComprobarCarpeta()
	{
		File Data = new File(".\\Data");
		File Libros = new File(".\\Data\\Libros");
		File Documentos = new File(".\\Data\\Documentos");
		
		boolean data = Data.mkdir(); 
		boolean libros = Libros.mkdir();
		boolean docum = Documentos.mkdir();
		
//		if(data) logger.log(Level.INFO, "Se caba de crear la carpeta 'Data'");
//		if(libros) logger.log(Level.INFO, "Se caba de crear la carpeta 'Libros'");
//		if(docum) logger.log(Level.INFO, "Se caba de crear la carpeta 'Documentos'");
	}
	
	/**
	 * Método para elimiar la ruta de un archivo una vez eliminado un archivo de la BD
	 * @param ruta
	 * @throws IOException
	 */
	public static void EliminarRuta(String ruta) throws IOException
	{
		Files.delete(Paths.get(ruta));
	}
}
