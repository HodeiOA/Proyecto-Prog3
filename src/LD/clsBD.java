package LD;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
//import java.util.logging.FileHandler;
//import java.util.logging.Handler;
//import java.util.logging.Level;
//import java.util.logging.SimpleFormatter;
//import java.util.logging.StreamHandler;
import java.util.logging.*;


import javax.swing.JOptionPane;

import com.sun.istack.internal.logging.Logger;

import LN.clsArchivo;
import LN.clsComentario;
import LN.clsUsuario;

/**
 * Clase para la gestión de los datos de objetos principales de la aplicación mediante una base de datos relacionada
 */
public class clsBD
{
	private static Logger logger = Logger.getLogger(clsBD.class);
	
	private static Connection connection = null;
	private static Statement statement = null;
	private static ResultSet rs=null;
	
	
//	static Handler h = new StreamHandler(System.out, new SimpleFormatter());


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
			//SEVERE: e.getMessage()
			logger.log( Level.SEVERE, e.getMessage(), e );

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
			//SEVERE: e.getMessage()
			logger.log( Level.SEVERE, e.getMessage(), e );

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
	public static void crearTablaArchivo() 
	{
		if (statement==null) return;
		try
		{ 
			//INFO: Creando tabla
			logger.log( Level.INFO, "Creando tabla");
			
			statement.executeUpdate("create table fichero_archivo " +
					"("
					//+ "foreign key(nick) references fichero_usuario(nick),"
					+ " nick string,"
					+ " nomAutor string,"
					+ " apeAutor string,"
					+ " codArchivo int, "
					+ "titulo string,"
					+ " ruta string,"
					+ " numPags int,"
					+ " ultimaPagLeida int,"
					+ "tiempo int,"
					+ "libroSi boolean, "
					+ "foreign key(nick) references fichero_usuario(nick),"
					+ " primary key(codArchivo)"
					+ ")");
			
			//Tabla creada
			logger.log( Level.INFO, "Tabla creada");

		} 
		
		catch (SQLException e) 
		{
			logger.log( Level.INFO, "La tabla ya estaba creada"+e.getMessage(), e );
			// Si hay excepción es que la tabla ya existía (lo cual es correcto). No la creamos y listo  
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
			logger.log( Level.INFO, "La tabla ya estaba creada"+e.getMessage(), e );
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
			statement.executeUpdate("create table fichero_comentario " +
				"( ID int, Texto string, codArchivo int, numPag int, primary key(ID)"
				+ ", foreign key (codArchivo) references fichero_archivo(codArchivo"
				+ ")");

		} 
		
		catch (SQLException e) 
		{
			logger.log( Level.INFO, "La tabla ya estaba creada"+e.getMessage(), e );  
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
	public static boolean InsertArchivo (String nick, String nomAutor, String apeAutor, int codArchivo, String titulo, String ruta, int numPags, int ultimaPagLeida, int tiempo,  boolean libroSi)
	{
				try 
				{
					//Insertando
					logger.log( Level.INFO, "Insertando");
					
					String sentSQL = "insert into fichero_archivo values(" +
							"'" + nick + "', " +
							"'" + nomAutor + "', " +
							"'" + apeAutor + "', " +
							"'" + codArchivo + "', " +
							"'" + titulo + "', " +
							"'" + ruta + "', " +
							"'" + numPags + "', " +
							"'" + ultimaPagLeida + "', " +
							"'" + tiempo + "', " +
							"'" + libroSi + "')";
					
					//insertado
	            	logger.log( Level.INFO, "insertado");
	            	
					int val = statement.executeUpdate( sentSQL );
					if (val!=1) return false; //Error, no se ha insertado
					return true;
				} 
				catch (SQLException e) 
				{
					logger.log( Level.WARNING, e.getMessage(), e );
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
		try 
		{
			String sentSQL = "insert into fichero_usuario values(" +
					"'" + nick + "', " +
					"'" + contraseña + "')";
			int val = statement.executeUpdate( sentSQL );
			if (val!=1) return false; 
			return true;
		} 
		catch (SQLException e) 
		{
			logger.log( Level.WARNING, e.getMessage(), e );
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
		try 
		{
			String sentSQL = "insert into fichero_comentario values(" +
					"'" + ID + "', " +
					"'" + texto + "', " +
					"'" + codArchivo + "', " +
					"'" + numPag + "')";
			int val = statement.executeUpdate( sentSQL );
			if (val!=1) return false;  
			return true;
		} 
		
		catch (SQLException e) 
		{
			logger.log( Level.WARNING, e.getMessage(), e );
			return false;
		}

	}
	//Delete
	//Borra filas de uno en uno, por su identificativo
	/**
	 * Borra una fila de la tabla indicada en el parámetro "tabla" 
	 * @param ident atributo identificativo del objeto que tenemos que eliminar
	 * @param tabla indica de qué tabla vamos a tener que eliminar el registro: "ARCHIVO", "USUARIO" o "COMENTARIO"
	 * @return devuelve un boolean indicando si la operación se ha podido hacer o no
	 */
	public static boolean BorrarFila (Object ident, String tabla)
	{	
    	logger.log( Level.INFO, tabla);

		switch(tabla)
		{
			case "ARCHIVO":
				try 
				{
					//Borrando
					int codArchivo=(Integer)ident;
					String sentSQL = "DELETE FROM fichero_archivo WHERE codArchivo = "+codArchivo;
					int val = statement.executeUpdate( sentSQL );
					if (val!=1) return false;   //Error de borrado
					return true;//Borrado correctamente
				}
				catch (SQLException e) 
				{
					logger.log( Level.WARNING, e.getMessage(), e );
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
					logger.log( Level.WARNING, e.getMessage(), e );
					return false;
				}
				
			case "COMENTARIO":
				try 
				{
					int ID=(Integer)ident;
					String sentSQL = "DELETE FROM fichero_comentario WHERE ID = "+ID;
					int val = statement.executeUpdate( sentSQL );
					if (val!=1) return false; 
					return true;
				}
				catch (SQLException e) 
				{
					logger.log( Level.WARNING, e.getMessage(), e );
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
	public static boolean UpdateArchivo (String nick, String nomAutor, String apeAutor, int codArchivo, String titulo, String ruta, int numPags, int ultimaPagLeida, int tiempo,  boolean libroSi)
	{
				try 
				{
	            	logger.log( Level.INFO, "Modificando");

					String sentSQL = "update fichero_archivo set "+
							"nick = '" + nick + "', " +
							 "nomAutor = '" + nomAutor + "', " 
							 + "apeAutor = '" + apeAutor + "', " +
							 "titulo = '"+ titulo  + "', " +
							"ruta = '"+ruta + "', " +
						    "numPags = "+ numPags + ", " +
							"ultimaPagLeida = " + ultimaPagLeida + ", " +
							"tiempo = " + tiempo + ", " +
							"libroSi = '"+libroSi +
							"' where codArchivo = "+ codArchivo + "";
					System.out.println( sentSQL );  // (Quitar) para ver lo que se hace
					int val = statement.executeUpdate( sentSQL );
					if (val!=1) return false; //fallo
					// Se tiene que añadir 1 - error si no
					return true; //bien
				} 
				catch (SQLException e) 
				{
					logger.log( Level.WARNING, e.getMessage(), e );
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
		try 
		{
			String sentSQL = "update fichero_usuario set " +
					 "contraseña = '"+ contraseña + "'" +
					"where nick= '" + nick + "'";
			System.out.println( sentSQL );  // (Quitar) para ver lo que se hace
			int val = statement.executeUpdate( sentSQL );
			if (val!=1) return false;  // Se tiene que añadir 1 - error si no
			return true;
		} 
		
		catch (SQLException e) 
		{
			logger.log( Level.WARNING, e.getMessage(), e );
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
		try 
		{
			String sentSQL = "update fichero_comentario set " +
					"ID=" + ID + ", " +
					 "texto= '" + texto + "', " +
					 "codArchivo =" + codArchivo + ", " +
					 "numPag = " + numPag + 
					 " where ID = "+ ID +"";
			int val = statement.executeUpdate( sentSQL );
			return true;
		} 
		catch (SQLException e) 
		{
			logger.log( Level.WARNING, e.getMessage(), e );
			return false;
		}

	}
	
	//Drops
	/**
	 * Borra una tabla
	 * @param tabla indica qué tabla borra: "ARCHIVO", "USUARIO" o "COMENTARIO"
	 * @return indica si la acción se ha podido realizar o no
	 */
	public static boolean DropTable(String tabla)
	{
		//info: tabla
    	logger.log( Level.INFO, tabla);

		switch (tabla)
		{
			case "ARCHIVO":
				try
				{
//					System.out.println("borrar tabla archivo");
					String sentSQL = "drop table fichero_archivo";
//					System.out.println(sentSQL);
					int val = statement.executeUpdate( sentSQL );
					return true;
				} 
				catch (SQLException e) 
				{
					logger.log( Level.WARNING, e.getMessage(), e );
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
					logger.log( Level.WARNING, e.getMessage(), e );
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
					logger.log( Level.WARNING, e.getMessage(), e );
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
				String sentSQL = "SELECT * FROM fichero_archivo";
				rs=statement.executeQuery( sentSQL );
				if(rs!=null)
				{
					while (rs.next())
					{ 
						//clsArchivo.ToString()
						 clsArchivo archivo = new clsArchivo(
								 rs.getString("nick"), rs.getString("nomAutor"), rs.getString("apeAutor"), rs.getString("titulo"),
								 rs.getString("ruta"), rs.getInt("numPags"), rs.getInt("ultimaPagLeida"), rs.getInt("tiempo"),
								 Boolean.parseBoolean(rs.getString("libroSi")), true, rs.getInt("codArchivo"));
						retorno.add(archivo);
					}
					rs.close();	
				}
					
				return retorno;
			}
			catch (SQLException e) 
			{
				logger.log( Level.WARNING, e.getMessage(), e );
				retorno= new HashSet <clsArchivo>();
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
				rs=statement.executeQuery( sentSQL );
				if(rs!=null)
				{
					while (rs.next())
					{ 
						 clsUsuario usuario = new clsUsuario(
								 rs.getString("nick"), rs.getString("contraseña"));				
						
						retorno.add(usuario);
					}
					rs.close();
				}
	
				return retorno;
			}
			catch (SQLException e) 
			{
				logger.log( Level.WARNING, e.getMessage(), e );
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
					String sentSQL = "select * from fichero_comentario";
					rs=statement.executeQuery( sentSQL );
					if(rs!=null)
					{
						while (rs.next())
						{ 
							//String texto, int codArchivo, int numPagina
							clsComentario comentario = new clsComentario(
									 rs.getString("texto"), rs.getInt("codArchivo"), rs.getInt("numPag"), true, rs.getInt("ID"));				
							retorno.add(comentario);
						}
						rs.close();
					}
					return retorno;
				}
				catch (SQLException e) 
				{
					logger.log( Level.WARNING, e.getMessage(), e );
					return null;
				}	
	}
}