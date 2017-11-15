package LN;
/**
 * Clase para la instanciación y tratamiento de objetos de tipo clsUsuario
 *
 */
public class clsUsuario implements Comparable
{
	private String nick; //Identificativo
	private String contraseña;
	
	//Constructor	
	public clsUsuario (String nick, String contraseña)
	{
		this.nick=nick;
		this.contraseña=contraseña;
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

	public String getContraseña() 
	{
		return contraseña;
	}

	public void setContraseña(String contraseña) 
	{
		this.contraseña = contraseña;
	}
	

	//Otros métodos
	@Override
	public String toString() 
	{
		return ("Nick: "+ nick +" Contraseña: "+contraseña);
	}
	
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
