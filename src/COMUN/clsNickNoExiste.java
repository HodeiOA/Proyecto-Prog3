package COMUN;

public class clsNickNoExiste extends Exception
{
	private String nick;
	private String MENSAJE;
	
	public clsNickNoExiste(String nick)
	{
		this.nick=nick;
	}
	
	public String getMessage() 
	{
		MENSAJE="El nick introducido ("+ nick+ ") es incorrecto. Por favor, entra con otro nick";
		return MENSAJE;
	}
}
