package COMUN;
import java.io.IOException;
import java.util.HashSet;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;

import LN.clsArchivo;
import LN.clsComentario;
import LN.clsGestor;

/**
 * En esta clase es donde tendremos el m�todo para que el archivo y el comentario tengan un n�mero diferente en cada registro, 
 * y controlar� que en cada ejecuci�n sigan en memoria los ya creados anteriormente y a�adi� 1 al c�digo mayor.
 */
public class clsComun 
{	
	private static Logger logger = Logger.getLogger( clsComun.class.getName());
	static Handler handlerPantalla;
	static Handler handlerArchivo;
	
	/**
	 * M�todo para uso de loggers
	 */
	public static void InitLogs()
	{
		
		handlerPantalla = new StreamHandler( System.out, new SimpleFormatter() );
    	handlerPantalla.setLevel( Level.ALL );
    	logger.addHandler( handlerPantalla );
    	
    	try 
    	{
			handlerArchivo = new FileHandler ( "clsComun.log.xml");
			handlerArchivo.setLevel(Level.WARNING);
			logger.addHandler(handlerArchivo);
		}
    	catch (SecurityException e1) 
    	{
    		logger.log(Level.SEVERE, e1.getMessage(), e1);
		} 
    	catch (IOException e1)
    	{
			logger.log(Level.SEVERE, e1.getMessage(), e1);
			e1.printStackTrace();
		}
	}
	
	/**
	 * M�todo de generaci�n de c�digos de archivo
	 */
	public static void siguienteArchivo()
	{
		InitLogs();
		 
		HashSet <clsArchivo> listaArchivos = new HashSet();
		int codArchivo=0;
		
		listaArchivos = clsGestor.LeerArchivosBD();	
		
    	logger.log( Level.INFO, "empezando a leer los c�digos");

		for(clsArchivo aux: listaArchivos)
		{	    
        	logger.log( Level.INFO, "c�digo le�do:"+aux.getCodArchivo());
			
			if (codArchivo<aux.getCodArchivo())		
			{
	        	logger.log( Level.INFO, "c�digo m�s alto hasta el momento:"+aux.getCodArchivo());
				codArchivo=aux.getCodArchivo();
			}
		}
		
		codArchivo++;

		logger.log( Level.INFO, "C�digo asignado:" + codArchivo);
		
		clsArchivo.setSigCodArchivo(codArchivo);
		logger.log( Level.INFO, "fin lectura c�digos");
	}
	
	/**
	 * M�todo de generaci�n de c�digos de comentario
	 */
	public static void siguienteComentario()
	{
		InitLogs();
		
		HashSet <clsComentario> listaComentarios = new HashSet();
		int codComentario=0;
		
		listaComentarios = clsGestor.LeerComentariosBD();	
		
    	logger.log( Level.INFO, "empezando a leer los c�digos");
		
		for(clsComentario aux: listaComentarios)
		{
			
			logger.log( Level.INFO, "c�digo le�do:"+aux.getCodArchivo());
			
			if (codComentario<aux.getID())		
			{
	        	logger.log( Level.INFO, "c�digo m�s alto hasta el momento:"+aux.getCodArchivo());
				codComentario=aux.getID();
			}
		}
		
		codComentario++;
		
		logger.log( Level.INFO, "C�digo asignado:" + codComentario);
		clsComentario.setSigID(codComentario);
	}
}