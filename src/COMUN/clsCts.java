package COMUN;
/**
 * En esta clase tendremos todas las constantes a utilizar a lo largo del proyecto, así como un enumerado para 
 * acceder a los ficheros
 *
 */
public class clsCts 
{
	public static enum enFicDatos
	{
		FICHERO_DATOS_CLIENTE,
		FICHERO_DATOS_EMPLEADO,
		FICHERO_DATOS_PELICULA,
		FICHERO_DATOS_COMPRA,
		FICHERO_DATOS_VENTA;	
	}
	//Constantes para las propiedades
	//Persona:
	public final static String PROP_NOMBRE="NOMBRE";
	public final static String PROP_APELLIDO="APELLIDO";
	public final static String PROP_DNI="DNI";
	public final static String PROP_EDAD="EDAD";
	
	//Cliente:
	public final static String PROP_NUMSOCIO="NUMSOCIO";
	public final static String PROP_SOCIO="SOCIO";
	public final static String PROP_DESCUENTO="DESCUENTO";
	
	//Empleado:
	public final static String PROP_IDENT="IDENT";
	public final static String PROP_VENTAS="VENTAS";
	
	//Película
	public final static String  PROP_CODIGO = "CODIGO";
	public final static String  PROP_TITULO = "TITULO";
	public final static String  PROP_GENERO = "GENERO";
	public final static String  PROP_PLAZAS= "PLAZAS";
	public final static String  PROP_LIMEDAD= "LIMEDAD";
	public final static String  PROP_RECAUDADO= "RECAUDADO";
	public final static String  PROP_PRECIO= "PRECIO";
	public final static String  PROP_VISUALIZACIONES= "VISUALIZACIONES";
	
	//Ventas:
	public final static String PROP_DNIEMPLEADO= "DNIEMPLEADO";
	public final static String PROP_CODPELI= "CODPELI";
	
	//Compra:
	//codpeli
	public final static String PROP_DNICLIENTE= "DNICLIENTE";
	
	//Constantes para el menú:
	//Alta:
	public final static String COMMAND_ALTACLIENTE="ALTACLIENTE";
	public final static String COMMAND_ALTAEMPLEADO="ALTAEMPLEADO";
	public final static String COMMAND_ALTAPELICULA="ALTAPELICULA";
	//Modificar:
	public final static String COMMAND_MODCLIENTE="MODCLIENTE";
	public final static String COMMAND_MODEMPLEADO="MODEMPLEADO";
	public final static String COMMAND_MODPELICULA="MODPELICULA";
	//Venta:
	public final static String COMMAND_VENTA= "VENTA";
	//Gestión de datos:
	public final static String COMMAND_GESTEMP="GESTEMP";
	public final static String COMMMAND_GESTPELI="GESTPELI";
	//Ordenaciones:
	public final static String COMMAND_ORDDNI= "ORDDNI";
	public final static String COMMAND_EMPLEADOSMASMENOSVTAS= "EMPLEADOSMASMENOSVTAS";
	public final static String COMMAND_ORDVISUALIZACIONES="ORDVISUALIZACIONES";
	public final static String COMMAND_ORDRECAUDADO="ORDENRECAUDADO";
	
	//Para las ventanas:
	public final static String COMMAND_ACEPTAR="ACEPTAR";
	public final static String COMMAND_CANCELAR="CANCELAR";
	public final static String COMMAND_RADIOSI="SI";
	public final static String COMMAND_RADIONO="NO";
	//para Jlist:
	public final static String COMMAND_LISTASELEC="LISTASELEC";
	public final static String COMMAND_ELIMINAR="ELIMINAR";
	public final static String COMMAND_ACTUALIZAR="ACTUALIZAR";
	
	//Para la opción de borrado
	public final static String COMMAND_BORRARLIENTE="BORRARCLIENTE";
	public final static String COMMAND_BORRAREMPLEADO="BORRAREMPLEADO";
	public final static String COMMAND_BORRARPELICULA="BORRARPELICULA";
}