package LD;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Clase para almacenar de manera persistente ciertas propiedades del programa, en este caso, las dimensiones y la localización
 */
public class clsProperties
{
	/**
	 * Método para recuperar las propiedades almacenadas en ejecuciones anteriores. De no haberlas, se recuperarán los valores por defecto
	 * @param misProps Objeto de tipo Properties en el que se cargarán las propiedades
	 * @param ClavePropiedades ArrayList con los nombres de cada propiedad
	 */
	public static void CargarProps(Properties misProps,  ArrayList <String> ClavePropiedades)
	{
		try 
		{
				misProps.loadFromXML( new FileInputStream( "propiedades.xml" ) );
		
		} catch (Exception e)
		{ 
			//Valores por defecto
			for(int i=0; i <  ClavePropiedades.size(); i++)
			{
				misProps.setProperty(ClavePropiedades.get(i), ""+0);
			}

		}
			
	}
	
	/**
	 * Método para modificar el valor de las propiedades almacenadas en el objeto properties
	 * @param misProps Objeto de tipo Properties en el que cambiaremos las propiedades
	 * @param anchuraAltura Array con los valores de altura y anchura que se le establecerán al objeto con las propiedades
	 * @param locationXY  Array con los valores de x e y  que se le establecerán al objeto con las propiedades
	 * @param ClavePropiedades ArrayList con los nombres de cada propiedad
	 */
	public static void CambiarPropiedades(Properties misProps, int[] anchuraAltura, int[] locationXY, ArrayList <String> ClavePropiedades)
	{
		for( String clave: ClavePropiedades)
		{
			switch(clave)
			{
				case "anchura":
					misProps.setProperty(clave,""+anchuraAltura[0] );
					break;
				case "altura":
					misProps.setProperty(clave,""+anchuraAltura[1] );
					break;
				case "X":
					misProps.setProperty(clave,""+locationXY[0] );
					break;
				case "Y":
					misProps.setProperty(clave,""+locationXY[1] );
					break;
			}
		}
		
	}
	
	/**
	 * Método para guardar los cambios efectuados en el objeto de Properties en un archivo aparte para recuperarlos en ejecuciones
	 * @param misProps Objeto con las propiedades que se van a guardar
	 */
	public static void Guardarpropiedades(Properties misProps)
	{
			try
			{
				misProps.storeToXML( new PrintStream( "propiedades.xml" ), "Propiedades de altura, anchura y localización" );
			} catch (IOException e) 
			{
				
				e.printStackTrace();
			}
	}

}
