package LD;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;

import javax.swing.JOptionPane;

import LN.clsArchivo;
import LN.clsComentario;
import LN.clsUsuario;

public class clsBD
{
	private static Connection connection = null;
	private static Statement statement = null;
	private static ResultSet rs=null;

	/** Inicializa una BD SQLITE y devuelve una conexión con ella. Debe llamarse a este 
	 * método antes que ningún otro, y debe devolver no null para poder seguir trabajando con la BD.
	 * @param nombreBD	Nombre de fichero de la base de datos
	 * @return	Conexión con la base de datos indicada. Si hay algún error, se devuelve null
	 */
	public static Connection initBD ( String nombreBD ) 
	{
		try
		{
		    Class.forName("org.sqlite.JDBC");
		    connection = DriverManager.getConnection("jdbc:sqlite:" + nombreBD );
			statement = connection.createStatement();
			statement.setQueryTimeout(30);  // poner timeout 30 msg
		    return connection;
		} 
		catch (ClassNotFoundException | SQLException e) 
		{
			JOptionPane.showMessageDialog( null, "Error de conexión!! No se ha podido conectar con " + nombreBD , "ERROR", JOptionPane.ERROR_MESSAGE );
			System.out.println( "Error de conexión!! No se ha podido conectar con " + nombreBD );
			return null;
		}
	}
	
	/** Cierra la conexión con la Base de Datos
	 */
	public static void close() 
	{
		try 
		{
			statement.close();
			connection.close();
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	/** Devuelve la conexión si ha sido establecida previamente (#initBD()).
	 * @return	Conexión con la BD, null si no se ha establecido correctamente.
	 */
	public static Connection getConnection() 
	{
		return connection;
	}
	
	/** Devuelve una sentencia para trabajar con la BD,
	 * si la conexión si ha sido establecida previamente (#initBD()).
	 * @return	Sentencia de trabajo con la BD, null si no se ha establecido correctamente.
	 */
	public static Statement getStatement() 
	{
		return statement;
	}
	
	
	//Crear tablas 
	
	/** Crea una tabla de archivos en una base de datos, si no existía ya.
	 * Debe haberse inicializado la conexión correctamente.
	 */
	public static void crearTablaArcivo() 
	{
		if (statement==null) return;
		try
		{ 
			statement.executeUpdate("create table fichero_archivo " +
					"(nick string, nomAutor string, apeAutor string, codArchivo int, "
					+ "titulo string, ruta string, numPags int, ultimaPagLeida int,tiempo int,libroSi boolean, "
					+ "foreign key(nick) references fichero_usuario(nick), primary key(codArchivo)"
					+ ")");

		} catch (SQLException e) 
		{
			// Si hay excepción es que la tabla ya existía (lo cual es correcto). No la creamos y listo
			// e.printStackTrace();  
		}
	}
	
	/** Crea una tabla de usuarios en una base de datos, si no existía ya.
	 * Debe haberse inicializado la conexión correctamente.
	 */
	public static void crearTablaUsuario() 
	{
		if (statement==null) return;
		try
		{
			statement.executeUpdate("create table fichero_usuario " +
				"(nick string, contraseña string, primary key (nick))");
		} 
		catch (SQLException e)
		{
			// Si hay excepción es que la tabla ya existía (lo cual es correcto)
			// e.printStackTrace();  
		}
	}

	/** Crea una tabla de comentario en una base de datos, si no existía ya.
	 * Debe haberse inicializado la conexión correctamente.
	 */
	public static void crearTablaComentario() 
	{
		if (statement==null) return;
		try
		{
			statement.executeUpdate("create table fichero_cometario " +
				"( ID int, Texto string, codArchivo int, numPag int, primary key(ID), foreign key (codArchivo) references fichero_archivo(codArchivo)");

		} catch (SQLException e) 
		{
			// Si hay excepción es que la tabla ya existía (lo cual es correcto)
			// e.printStackTrace();  
		}
	}
	
	//Hacer inserts aquí
	/**
	 * Método para introducir un nuevo archivo en la BD. Para crearlo, le pasamos los atributos necesarios para crear un nuevo
	 * archivo y se los pasamos en una sentencia "INSERT"
	 * @param nomAutor
	 * @param apeAutor
	 * @param codArchivo
	 * @param titulo
	 * @param ruta
	 * @param numPags
	 * @param ultimaPagLeida
	 * @param tiempo
	 * @param libroSi
	 * @return devolverá un boolean para indicar si el insert se ha podido o no hacer
	 */
	public static boolean InsertArchivo (String nomAutor, String apeAutor, int codArchivo, String titulo, String ruta, int numPags, int ultimaPagLeida, int tiempo,  boolean libroSi)
	{
				try {
					String sentSQL = "insert into fichero_archivo values(" +
							"'" + nomAutor + "', " +
							"'" + apeAutor + "', " +
							"'" + codArchivo + "', " +
							"'" + titulo + "', " +
							"'" + ruta + "', " +
							"'" + numPags + "', " +
							"'" + ultimaPagLeida + "', " +
							"'" + tiempo + "', " +
							"'" + libroSi + "')";
					int val = statement.executeUpdate( sentSQL );
					if (val!=1) return false; 
					return true;
				} catch (SQLException e) {
					e.printStackTrace();
					return false;
				}

	}
	
	/**
	 *  Método para introducir un nuevo usuario en la BD. Para crearlo, le pasamos los atributos necesarios para crear un nuevo
	 * usuario y se los pasamos en una sentencia "INSERT"
	 * @param contraseña
	 * @param nick
	 * @return devuelve un boolean indicando si el INSERT se ha podido hacer o no
	 */
	public static boolean InsertUsuario (String contraseña, String nick)
	{
		try {
			String sentSQL = "insert into fichero_usuario values(" +
					"'" + contraseña + "', " +
					"'" + nick + "')";
			int val = statement.executeUpdate( sentSQL );
			if (val!=1) return false; 
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 *  Método para introducir un nuevo comentario en la BD. Para crearlo, le pasamos los atributos necesarios para crear un nuevo
	 * comentario y se los pasamos en una sentencia "INSERT"
	 * @param ID
	 * @param texto
	 * @param codArchivo
	 * @param numPag
	 * @return devuelve un boolean indicando si el INSERT se ha podido hacer o no
	 */
	public static boolean InsertComentario (int ID, String texto, int codArchivo, int numPag)
	{
		try {
			String sentSQL = "insert into fichero_comentario values(" +
					"'" + ID + "', " +
					"'" + texto + "', " +
					"'" + codArchivo + "', " +
					"'" + numPag + "')";
			int val = statement.executeUpdate( sentSQL );
			if (val!=1) return false;  
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

	}
	//Delete
	//Borra filas de uno en uno, por su identificativo
	/**
	 * Borra una fila de la tabla indicada en el parámetro "tabla" 
	 * @param ident atributo identificativo del objeto que tenemos que eliminar
	 * @param tabla indica de qué tabla vamos a tener que eliminar el registro
	 * @return devuelve un boolean indicando si la operación se ha podido hacer o no
	 */
	public static boolean BorrarFila (Object ident, String tabla)
	{
		
		switch(tabla)
		{
			case "ARCHIVO":
				try 
				{
					int codArchivo=(Integer)ident;
					String sentSQL = "DELETE FROM fichero_archivo WHERE codArchivo = "+codArchivo;
					int val = statement.executeUpdate( sentSQL );
					if (val!=1) return false;  
					return true;
				}
				catch (SQLException e) 
				{
					e.printStackTrace();
					return false;
				}
				
			case "USUARIO":
				try 
				{
					String sentSQL = "DELETE FROM fichero_usuario WHERE nick = "+ident;
					int val = statement.executeUpdate( sentSQL );
					if (val!=1) return false; 
					return true;
				}
				catch (SQLException e) 
				{
					e.printStackTrace();
					return false;
				}
				
			case "COMENTARIO":
				try 
				{
					int ID=(Integer)ident;
					String sentSQL = "DELETE FROM fichero_COMENTARIO WHERE ID = "+ID;
					int val = statement.executeUpdate( sentSQL );
					if (val!=1) return false; 
					return true;
				}
				catch (SQLException e) 
				{
					e.printStackTrace();
					return false;
				}
				
			default:
				return false;
		}
	}
	//Update
	/**
	 * Modifica un archivo de la BD
	 * Para ello, le pasamos todos los atributos (los que cambian y los que no) para que el método sirva para todos los casos
	 * @param nomAutor
	 * @param apeAutor
	 * @param codArchivo
	 * @param titulo
	 * @param ruta
	 * @param numPags
	 * @param ultimaPagLeida
	 * @param tiempo
	 * @param libroSi
	 * @return indica si se ha realizado con éxito
	 */ 
	public static boolean UpdateArchivo (String nomAutor, String apeAutor, int codArchivo, String titulo, String ruta, int numPags, int ultimaPagLeida, int tiempo,  boolean libroSi)
	{
				try {
					String sentSQL = "update fichero_archivo set "+
							"'" + "nomAutor =" + nomAutor + "', " +
							"'" + "apeAutor =" + apeAutor + "', " +
							"'" + "titulo = "+ titulo  + "', " +
							"'" + "ruta = "+ruta + "', " +
							"'" + "numPags = "+ numPags + "', " +
							"'" + "ultimaPagLeida =" + ultimaPagLeida + "', " +
							"'" + "tiempo =" + tiempo + "', " +
							"'" + "libroSi ="+libroSi +
							"where codArchivo = "+ codArchivo + "')";
					System.out.println( sentSQL );  // (Quitar) para ver lo que se hace
					int val = statement.executeUpdate( sentSQL );
					if (val!=1) return false;  // Se tiene que añadir 1 - error si no
					return true;
				} catch (SQLException e) 
				{
					e.printStackTrace();
					return false;
				}

	}
	/**
	 * Modificar un usuario de la BD
	 *  Para ello, le pasamos todos los atributos (los que cambian y los que no) para que el método sirva para todos los casos
	 * @param contraseña
	 * @param nick
	 * @return indic si ha tenido éxito
	 */
	public static boolean UpdateUsuario (String contraseña, String nick)
	{
		try {
			String sentSQL = "update fichero_usuario set" +
					"'" + "contraseña ="+ contraseña + 
					"where nick=" + nick + "')";
			System.out.println( sentSQL );  // (Quitar) para ver lo que se hace
			int val = statement.executeUpdate( sentSQL );
			if (val!=1) return false;  // Se tiene que añadir 1 - error si no
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * Modificar un comentario de la BD
	 *  Para ello, le pasamos todos los atributos (los que cambian y los que no) para que el método sirva para todos los casos
	 * @param ID
	 * @param texto
	 * @param codArchivo
	 * @param numPag
	 * @return indica si ha tenido éxito o no
	 */
	public static boolean UpdateComentario (int ID, String texto, int codArchivo, int numPag)
	{
		try {
			String sentSQL = "insert into fichero_comentario values(" +
					"'" + ID + "', " +
					"'" + texto + "', " +
					"'" + codArchivo + "', " +
					"'" + numPag + "')";
			int val = statement.executeUpdate( sentSQL );
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

	}
	
	//Drops
	/**
	 * Borra una tabla
	 * @param tabla indica qué tabla borra
	 * @return indica si la acción se ha podido realizar o no
	 */
	public static boolean DropTable(String tabla)
	{
		switch (tabla)
		{
			case "ARCHIVO":
				try
				{
					String sentSQL = "drop table fichero_archivo";
					int val = statement.executeUpdate( sentSQL );
					return true;
				} 
				catch (SQLException e) 
				{
					e.printStackTrace();
					return false;
				}
			
			case "USUARIO":
				try
				{
					String sentSQL = "drop table fichero_usuario";
					int val = statement.executeUpdate( sentSQL );
					return true;
				} 
				catch (SQLException e) 
				{
					e.printStackTrace();
					return false;
				}
				
			case "COMENTARIO":
				try
				{
					String sentSQL = "drop table fichero_comentario";
					int val = statement.executeUpdate( sentSQL );
					return true;
				} 
				catch (SQLException e) 
				{
					e.printStackTrace();
					return false;
				}
			default: 
				return false;
				
		}
	}
	/**
	 * Lectura de archivos de la BD
	 * @return Devuelve la lista de archivos guardados en la BD hasta ahora
	 */
	public static HashSet <clsArchivo> LeerArchivos()
	{		
		 HashSet <clsArchivo> retorno=new  HashSet <clsArchivo>();
				try 
				{
					String sentSQL = "select * from fichero_archivo";
					rs = statement.executeQuery( sentSQL );
					while (rs.next())
					{ 
						 clsArchivo archivo = new clsArchivo(
								 rs.getString("nomAutor"), rs.getString("apeAutor"), rs.getString("titulo"),
								 rs.getString("ruta"), rs.getInt("numPags"), rs.getInt("ultimaPagLeida"), rs.getInt("tiempo"),
								 rs.getBoolean("libroSi"), true, rs.getInt("codArchivo"));				
						rs.close();
						retorno.add(archivo);
					}
					return retorno;
				}
				catch (SQLException e) 
				{
					e.printStackTrace();
					return null;
				}	
	}
	
	/**
	 * Lectura de usuarios de la BD
	 * @return Devuelve la lista de usuarios guardados en la BD hasta ahora
	 */
	public static HashSet <clsUsuario> LeerUsuarios()
	{		
		 HashSet <clsUsuario> retorno=new  HashSet <clsUsuario>();
				try 
				{
					String sentSQL = "select * from fichero_usuario";
					rs = statement.executeQuery( sentSQL );
					while (rs.next())
					{ 
						 clsUsuario usuario = new clsUsuario(
								 rs.getString("nick"), rs.getString("contraseña"));				
						rs.close();
						retorno.add(usuario);
					}
					return retorno;
				}
				catch (SQLException e) 
				{
					e.printStackTrace();
					return null;
				}	
	}
	
	/**
	 * Lectura de comentarios de la BD
	 * @return Devuelve la lista de comentarios guardados en la BD hasta ahora
	 */
	public static HashSet <clsComentario> LeerComentarios()
	{		
		 HashSet <clsComentario> retorno=new  HashSet <clsComentario>();
				try 
				{
					String sentSQL = "select * from fichero_coemntario";
					rs = statement.executeQuery( sentSQL );
					while (rs.next())
					{ 
						//String texto, int codArchivo, int numPagina
						clsComentario comentario = new clsComentario(
								 rs.getString("texto"), rs.getInt("codArchivo"), rs.getInt("numPagina"), true, rs.getInt("ID"));				
						rs.close();
						retorno.add(comentario);
					}
					return retorno;
				}
				catch (SQLException e) 
				{
					e.printStackTrace();
					return null;
				}	
	}
}
