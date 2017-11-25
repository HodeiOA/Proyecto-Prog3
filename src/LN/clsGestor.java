package LN;

public class clsGestor
{
	public double porcentLeido(clsArchivo archi)
	{
		double retorno;
		retorno=archi.getUltimaPagLeida()/archi.getNumPags() *100;
		return retorno;
	}
	
	//métodos para login
	public static boolean comprobarExistencia(String username, String password) 
    {
		return false;	    	
    	//leemos la base de datos, sacamos el nick y contraseña y lo metemos en un arraylist
    	//haces un for each y miramos si...
		
		
		//si está entrando esperaremos que de true 
		//y si se está registrando y todo va bien también fale
    }
	 
}
