package LN;
/**
 * Esta clase sirve para la creaci�n de objetos de tipo clsComentario
 */
public class clsComentario implements Comparable
{
	private static int sigID;
	private int ID;
	private String Texto;
	private int codArchivo;
	private int numPagina;
	
	/**
	 * Este m�todo sirve para crear objetos de tipo clsComentario mediante los atributos pasados por par�metro
	 * @param texto
	 * @param codArchivo
	 * @param numPagina
	 * @param leerBD
	 * @param IDBD
	 */
	public clsComentario(String texto, int codArchivo, int numPagina, boolean leerBD, int IDBD)
	{
		Texto = texto;
		this.codArchivo = codArchivo;
		this.numPagina = numPagina;
		
		//Esto lo hacemos porque para la BD necesitamos crear comentarios el c�digo que leemos de la BD
				if(leerBD)
				{
					//leerBD=true, estamos usando el constructor para crear un comentario porque estamos ley�ndolo de la BD
					//le ponemos el c�digo le�do
					this.ID=IDBD;
				}
				else
				{
					//Estamos creando un comentario est�ndar, le ponemos el ID  secuencial.
					ID = sigID;
					sigID++;
				}		
	}

	//Getters y Setters
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
	
	/**
	 * Este m�todo permite comparar un objeto de tipo clsComentario con otro de su mismo tipo
	 * @param o ser� el objeto de tipo clsComentario a comparar con el objto del que se llame al m�todo
	 * @return devolver� un int que indicar� si, seg�n el criterio elegido, el objeto deber�a ir antes o despu�s del otro objeto
	 */
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
