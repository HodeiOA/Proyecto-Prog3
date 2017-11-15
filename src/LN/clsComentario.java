package LN;

public class clsComentario implements Comparable
{
	private static int SigID;
	private int ID;
	private String Texto;
	private int codArchivo;
	private int numPagina;
	
	public clsComentario(String texto, int codArchivo, int numPagina)
	{
		ID = SigID;
		Texto = texto;
		SigID++;
		this.codArchivo = codArchivo;
		this.numPagina = numPagina;
	}

	public static int getSigID() 
	{
		return SigID;
	}
	
	public static void setSigID(int sigID)
	{
		SigID = sigID;
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
