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
	
	//m�todos para login
	public static void comprobarExistenciaUsuario(String nick, String contrase�a) throws clsNickRepetido, clsNickNoExiste
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
