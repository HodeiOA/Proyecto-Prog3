package LN;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

import COMUN.clsNickRepetido;

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
	
	//crear cliente throws clsNickRepetido()
	 
	public static HashSet<Object> LeerBD()
	{
		ArrayList<Comparable> TodoLoGuardado=new ArrayList <>();
		
		HashSet <Object> retorno = new HashSet <>();
		
		return retorno;
	}
}
