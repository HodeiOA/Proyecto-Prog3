package LN;

public class clsComentario implements Comparable
{
	private static int sigID;
	private int ID;
	private String Texto;
	private int codArchivo;
	private int numPagina;
	
	public clsComentario(String texto, int codArchivo, int numPagina)
	{
		ID = sigID;
		Texto = texto;
		sigID++;
		this.codArchivo = codArchivo;
		this.numPagina = numPagina;
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
	
	
}
