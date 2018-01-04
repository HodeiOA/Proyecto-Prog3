package LD;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Properties;


public class clsProperties
{
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
	
	public static void Guardarpropiedades(Properties misProps)
	{
			try
			{
				misProps.storeToXML( new PrintStream( "propiedades.xml" ), "Propiedades de Altura y anchura" );
			} catch (IOException e) 
			{
				
				e.printStackTrace();
			}
		
	}

}
