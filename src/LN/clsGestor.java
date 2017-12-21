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
	
	//métodos para login
	public static void comprobarExistenciaUsuario(String nick, String contraseña) 
    {
		boolean existe;
		boolean insert;
    	//leemos la base de datos, sacamos el nick y contraseña y lo metemos en un arraylist
		HashSet <clsUsuario> usuarioBD = clsBD.LeerUsuarios();
		clsUsuario nuevoUsuario = new clsUsuario (nick, contraseña);
		
		//Si lo introduce. da true, con lo cual no existía, por lo que retorno false
		//Si ya existía el add da false, nosotros devolvemos false.
		existe = !(usuarioBD.add(nuevoUsuario));
		if(existe)
		{
			//Lanzar excepción
		}
		else
		{
			//No existe, lo creamos
			insert=clsBD.InsertUsuario(contraseña, nick);
			if(!insert)
			{
				//Excepción en la BD-En realidad se supone que esto nunca debería pasar, si pasa es culpa nuestra
			}
		}
    }
	//crear cliente throws clsNickRepetido()
	
	 
	
}
