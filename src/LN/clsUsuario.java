package LN;
/**
 * Clase para la instanciación y tratamiento de objetos de tipo clsUsuario
 */
public class clsUsuario implements Comparable
{
	private String nick; //Identificativo
	private String contraseña;
	
	//Constructor	
	/**
	 * Este método sirve para crear objetos de tipo clsUsuario mediante los atributos pasados por parámetro
	 * @param nick
	 * @param contraseña
	 */
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
	
	/**
	 * Este método servirá para mostrar los objetos por pantalla
	 */
	@Override
	public String toString() 
	{
		return ("Nick: "+ nick +" Contraseña: "+contraseña);
	}
	/**
	 * Este método permite comparar un objeto de tipo clsUsuario con otro de su mismo tipo
	 * @param o será el objeto de tipo clsUsuario a comparar con el objto del que se llame al método
	 * @return devolverá un int que indicará si, según el criterio elegido, el objeto debería ir antes o después del otro objeto
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
	 * Este método permite generar un hashCode para los objetos clsUsuario
	 * @return result devolverá el hashCode del objeto según su nick
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
