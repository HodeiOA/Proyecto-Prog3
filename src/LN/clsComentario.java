package LN;

public class clsComentario implements Comparable
{
	private static int sigID;
	private int ID;
	private String Texto;
	private int codArchivo;
	private int numPagina;
	
	public clsComentario(String texto, int codArchivo, int numPagina, boolean leerBD, int IDBD)
	{
		Texto = texto;
		this.codArchivo = codArchivo;
		this.numPagina = numPagina;
		
		//Esto lo hacemos porque para la BD necesitamos crear comentarios el código que leemos de la BD
				if(leerBD)
				{
					//leerBD=true, estamos usando el constructor para crear un comentario porque estamos leyéndolo de la BD
					//le ponemos el código leído
					this.ID=IDBD;
				}
				else
				{
					//Estamos creando un comentario estándar, le ponemos el ID  secuencial.
					ID = sigID;
					sigID++;
				}		
	}

	public static int getSigID() 
	{
		return sigID;
	}
	
	public static void setSigID(int sigID)
	{
		sigID = sigID;
	}

	public int getID() 
	{
		return ID;
	}

	public String getTexto() 
	{
		return Texto;
	}

	public void setTexto(String texto) 
	{
		Texto = texto;
	}
	
	public int getCodArchivo() 
	{
		return codArchivo;
	}

	public int getNumPagina() 
	{
		return numPagina;
	}

	@Override
	public int compareTo(Object o) 
	{
		Integer a = this.ID;
		
		if(o == null)
		{
			throw new NullPointerException();
		}
		
		if(o.getClass() != this.getClass())
		{
			throw new ClassCastException();
		}
		
		return a.compareTo(((clsComentario) o).getID());
	}
	
	@Override
	public int hashCode() 
	{
		return ID;
	}
}
