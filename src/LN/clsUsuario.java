package LN;
/**
 * Clase para la instanciaci�n y tratamiento de objetos de tipo clsUsuario
 */
public class clsUsuario implements Comparable
{
	private String nick; //Identificativo
	private String contrase�a;
	
	//Constructor	
	/**
	 * Este m�todo sirve para crear objetos de tipo clsUsuario mediante los atributos pasados por par�metro
	 * @param nick
	 * @param contrase�a
	 */
	public clsUsuario (String nick, String contrase�a)
	{
		this.nick=nick;
		this.contrase�a=contrase�a;
	}

	//Getters/Setters
	public String getNick() 
	{
		return nick;
	}

	public void setNick(String nick) 
	{
		this.nick = nick;
	}

	public String getContrase�a() 
	{
		return contrase�a;
	}

	public void setContrase�a(String contrase�a) 
	{
		this.contrase�a = contrase�a;
	}
	
	/**
	 * Este m�todo servir� para mostrar los objetos por pantalla
	 */
	@Override
	public String toString() 
	{
		return ("Nick: "+ nick +" Contrase�a: "+contrase�a);
	}
	/**
	 * Este m�todo permite comparar un objeto de tipo clsUsuario con otro de su mismo tipo
	 * @param o ser� el objeto de tipo clsUsuario a comparar con el objto del que se llame al m�todo
	 * @return devolver� un int que indicar� si, seg�n el criterio elegido, el objeto deber�a ir antes o despu�s del otro objeto
	 */
	@Override
	public int compareTo(Object o) 
	{
		String a = this.getNick();
		
		if (o==null)
		{
			throw new NullPointerException();
		}
		
		if(o.getClass()!=this.getClass())
		{
			throw new ClassCastException();
		}
		
		return a.compareTo(((clsUsuario)o).getNick());
	}	
	
	/**
	 * Este m�todo permite generar un hashCode para los objetos clsUsuario
	 * @return result devolver� el hashCode del objeto seg�n su nick
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nick == null) ? 0 : nick.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) 
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		clsUsuario other = (clsUsuario) obj;
		if (nick == null)
		{
			if (other.nick != null)
				return false;
		}
		else if (!nick.equals(other.nick))
			return false;
		return true;
	}

	
}
