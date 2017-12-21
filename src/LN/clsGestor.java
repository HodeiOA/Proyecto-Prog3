package LN;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

import COMUN.clsNickRepetido;
import LD.clsBD;

public class clsGestor
{
	public double porcentLeido(clsArchivo archi)
	{
		double retorno;
		retorno=archi.getUltimaPagLeida()/archi.getNumPags() *100;
		return retorno;
	}
	
	//m�todos para login
	public static void comprobarExistenciaUsuario(String nick, String contrase�a) 
    {
		boolean existe;
		boolean insert;
    	//leemos la base de datos, sacamos el nick y contrase�a y lo metemos en un arraylist
		HashSet <clsUsuario> usuarioBD = clsBD.LeerUsuarios();
		clsUsuario nuevoUsuario = new clsUsuario (nick, contrase�a);
		
		//Si lo introduce. da true, con lo cual no exist�a, por lo que retorno false
		//Si ya exist�a el add da false, nosotros devolvemos false.
		existe = !(usuarioBD.add(nuevoUsuario));
		if(existe)
		{
			//Lanzar excepci�n
		}
		else
		{
			//No existe, lo creamos
			insert=clsBD.InsertUsuario(contrase�a, nick);
			if(!insert)
			{
				//Excepci�n en la BD-En realidad se supone que esto nunca deber�a pasar, si pasa es culpa nuestra
			}
		}
    }
	//crear cliente throws clsNickRepetido()
	
	 
	
}
