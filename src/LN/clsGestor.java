package LN;

public class clsGestor
{
	public double porcentLeido(clsArchivo archi)
	{
		double retorno;
		retorno=archi.getUltimaPagLeida()/archi.getNumPags() *100;
		return retorno;
	}
	
	//m�todos para login
	public static boolean comprobarExistencia(String username, String password) 
    {
		return false;	    	
    	//leemos la base de datos, sacamos el nick y contrase�a y lo metemos en un arraylist
    	//haces un for each y miramos si...
		
		
		//si est� entrando esperaremos que de true 
		//y si se est� registrando y todo va bien tambi�n fale
    }
	 
}
