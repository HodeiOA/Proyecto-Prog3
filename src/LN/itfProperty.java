package LN;

public interface itfProperty
{
	
	/**
	 * Devuelve la propiedad solicitada por nombre de tipo String.
	 * @param propiedad nombre de la propiedad a devolver del objeto. 
	 */
	String 	getStringProperty	(String propiedad);
	
	/**
	 * Devuelve la propiedad solicitada por nombre de tipo Integer.
	 * @param propiedad nombre de la propiedad a devolver del objeto. 
	 */
	Integer getIntegerProperty	(String propiedad);
	
	/**
	 * Devuelve la propiedad solicitada por nombre de tipo Float.
	 * @param propiedad nombre de la propiedad a devolver del objeto. 
	 */
	Float 	getFloatProperty	(String propiedad);
	
	/**
	 * Devuelve la propiedad solicitada por nombre de tipo Double.
	 * @param propiedad nombre de la propiedad a devolver del objeto. 
	 */
	Double	getDoubleProperty	(String propiedad);
	
	/**
	 * Devuelve la propiedad solicitada por nombre de tipo char.
	 * @param propiedad nombre de la propiedad a devolver del objeto. 
	 */
	char	getCharProperty		(String propiedad);
}