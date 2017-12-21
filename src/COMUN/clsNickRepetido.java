package COMUN;
/**
 * Esta clase es la excepci�n que se puede llegar a dar al intentar introducir dos usuarios iguales en el sistema
 *
 */
public class clsNickRepetido extends Exception
{
	private String nick;
	private String MENSAJE;
	
	public clsNickRepetido(String nick)
	{
		this.nick=nick;
	}
	
	public String getMessage() 
	{
		MENSAJE="El nick utilizado ("+ nick+ ") ya existe. Por favor, reg�strate con otro nick";
		return MENSAJE;
	}
}
