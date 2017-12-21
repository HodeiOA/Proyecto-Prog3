package LN;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

import COMUN.clsNickRepetido;
import COMUN.clsNickNoExiste;
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
	public static void comprobarExistenciaUsuario(String nick, String contraseña) throws clsNickRepetido, clsNickNoExiste
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
			clsNickRepetido repe=new clsNickRepetido(nick);
			throw repe;
		}
		else
		{
			clsNickNoExiste noExiste= new clsNickNoExiste(nick);
			throw noExiste;
			
		}
		
		//return existe;
    }
	
	 
	
}
