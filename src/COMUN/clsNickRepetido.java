package COMUN;
/**
 * Esta clase es la excepción que se puede llegar a dar al intentar introducir dos usuarios iguales en el sistema
 *
 */
public class clsNickRepetido extends Exception
{
	private final String MENSAJE="El Nick introducido pertenece a una persona ya registrada";
	private String nick;
	
	public clsNickRepetido(String nick)
	{
		this.nick=nick;
	}
	
	public String getMessage() 
	{
		return MENSAJE + "( "+nick+" )";
	}
}
