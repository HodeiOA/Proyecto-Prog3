package COMUN;
import java.io.IOException;
import java.util.ArrayList;
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
 */
public class clsComun 
{
	/**Crearemos los códigos de los archivos y comentarios automáticamnete 
	 * desde el anterior añadiendole uno.
	 */
	
	private static Logger logger = Logger.getLogger( clsComun.class.getName());
	static Handler handlerPantalla;
	static Handler handlerArchivo;
	
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
	
	public static void siguienteArchivo()
	{
		InitLogs();
		 
		HashSet <clsArchivo> listaArchivos = new HashSet();
		int codArchivo=0;
		
		listaArchivos = clsGestor.LeerArchivosBD();	
		
    	logger.log( Level.INFO, "empezando a leer los códigos");

		for(clsArchivo aux: listaArchivos)
		{	    
        	logger.log( Level.INFO, "código leído:"+aux.getCodArchivo());
			
			if (codArchivo<aux.getCodArchivo())		
			{
	        	logger.log( Level.INFO, "código más alto hasta el momento:"+aux.getCodArchivo());
				codArchivo=aux.getCodArchivo();
			}
		}
		
		codArchivo++;

		logger.log( Level.INFO, "Código asignado:" + codArchivo);
		
		clsArchivo.setSigCodArchivo(codArchivo);
		logger.log( Level.INFO, "fin lectura códigos");
	}
	
	public static void siguienteComentario()
	{
		InitLogs();
		
		HashSet <clsComentario> listaComentarios = new HashSet();
		int codComentario=0;
		
		listaComentarios = clsGestor.LeerComentariosBD();	
		
    	logger.log( Level.INFO, "empezando a leer los códigos");
		
		for(clsComentario aux: listaComentarios)
		{
			
			logger.log( Level.INFO, "código leído:"+aux.getCodArchivo());
			
			if (codComentario<aux.getID())		
			{
	        	logger.log( Level.INFO, "código más alto hasta el momento:"+aux.getCodArchivo());
				codComentario=aux.getID();
			}
		}
		
		codComentario++;
		
		logger.log( Level.INFO, "Código asignado:" + codComentario);
		clsComentario.setSigID(codComentario);
	}
}