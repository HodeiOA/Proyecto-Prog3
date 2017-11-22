package LD;

import java.io.EOFException;
import java.io.File;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;


/**
 * Esta clase gestiona la escritura y lectura en los ficheros.
 *
 */
public  class clsDatos 
{
	private final String fic_cliente=".\\Data\\clientes.dat";
	private final String fic_empleado=".\\Data\\empleados.dat";
	private final String fic_pelicula=".\\Data\\peliculas.dat";
	private final String fic_compra=".\\Data\\compra.dat";
	private final String fic_venta=".\\Data\\ventas.dat";
	
	ObjectOutputStream oos;
	ObjectInputStream ois;
	//Para que cuando el fichero existe no escriba el Formato de cabecera
	AppendableObjectOutputStream aos;
	
	private String setFichero (String fichero) 	//le paso el enum llamado fichero aqu�
	{
		String rufic = null;	//RUFIC ES LA RUTA DEL FICHERO.
		return rufic;
	}

	public void ComenzarSave(String fichero)  //FICHERO ES EL NOMBRE DEL ENUMERADO
	{
		String ruta=setFichero(fichero);
		File fic =new File (ruta);
		//Esto no es crear el fichero en el disco, es un objeto de java
		
		if(fic.exists())
		{
			//Si existe, creamos un objeto de escritura de objetos a fichero
			try
			{
				//new FileOutputStream(fic,true) es el constructor de un FileOutputStream. Esto se podr�a haber hecho en 2 instrucciones, 
				//pero habr�a que crear otra variable
				//true=a�adir
				aos= new AppendableObjectOutputStream(new FileOutputStream(fic,true));
			} 
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			//Si no existe, creamos el fichero en el disco duro en la ruta indicada y despu�s escribe con ObjectOutput....
			try 
			{
//				fic.createNewFile();
				//fic--> no ponemos true por lo que no a�ade, escribe de cero
				oos= new ObjectOutputStream(new FileOutputStream(fic));
			} 
			catch (FileNotFoundException e)
			{
				e.printStackTrace();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public void Save(Serializable o) 
	{
		try
		{
			if(oos!=null)
			{
				oos.writeObject(o);
			}
			else
			{
				if(aos!=null)
				{
					aos.writeObject(o);
				}
			}
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
	}

	public void TerminarSave() 
	{
		try
		{
			if(oos!=null)
			{
				oos.close();
			}
			else 
			{
				if(aos!=null) aos.close();
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	
	public void ComenzarRead(String fichero) throws IOException
	{
		String ruta=setFichero(fichero);
		File fic;
		
		fic=new File(ruta);
		if(fic.exists())
		{
			try
			{
				ois=new ObjectInputStream(new FileInputStream(ruta));
			}
			catch(FileNotFoundException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public ArrayList<Serializable> Read() throws ClassNotFoundException 
	{
		ArrayList<Serializable> retorno= new ArrayList <Serializable>();
		Serializable o;

		try
		{
			while((o=(Serializable) ois.readObject())!=null)
			{
				retorno.add(o);
			}
			
		}
		catch (NullPointerException e)
		{
			//NullPointerException
		}
		catch (EOFException e)
		{
			
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		
	
		// TODO Auto-generated method stub
		return retorno;
	}


	public void TerminarRead() 
	{
		try
		{
			if(ois!=null)
			{
				ois.close();
			}
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
	}


	public void ResetFile(String fichero) throws IOException 
	{
		String ruta = setFichero(fichero);
		File fic = new File (ruta);
		fic.delete();
		
		
	}
	
}