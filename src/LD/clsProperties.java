package LD;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Properties;


public class clsProperties
{
	public static void CargarProps(Properties misProps)
	{
		try 
		{
				misProps.loadFromXML( new FileInputStream( "propiedades.xml" ) );
		
		} catch (Exception e)
		{ 
			
		}
			
	}
	
	public static void CambiarPropiedades(Properties misProps, String [] AlturaAnchura, String[] locationXY, ArrayList <String> ClavePropiedades)
	{
		for(int i=0; i==ClavePropiedades.size(); i++ )
		{
			if(i<2)
			{
				misProps.setProperty(ClavePropiedades.get(i),AlturaAnchura[i] );
			}
			else
			{
				misProps.setProperty(ClavePropiedades.get(i),locationXY[i] );
			}
			
		}
		
	}
	public static void Guardarpropiedad(Properties misProps)
	{
			try
			{
				misProps.storeToXML( new PrintStream( "propiedades.xml" ), "Propiedades de Altura y anchura" );
			} catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

}
