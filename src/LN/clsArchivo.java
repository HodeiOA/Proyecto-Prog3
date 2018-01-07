package LN;

/**
 * Esta clase sirve para la creación de objetos de tipo clsArchivo
 */
public class clsArchivo implements Comparable
{

	private String nomAutor;
	private String apeAutor;
	private int codArchivo;	//el atributo identificativo
	private String titulo;
	private String ruta;	
	private int numPags;
	private int ultimaPagLeida;
	private int tiempo;	
	private boolean libroSi;
	private static int sigCodArchivo;
	private String nick;

	/**
	 * Este método sirve para crear objetos de tipo clsArchivo mediante los atributos pasados por parámetro
	 * @param nick
	 * @param nomAutor
	 * @param apeAutor
	 * @param titulo
	 * @param ruta
	 * @param numPags
	 * @param ultimaPagLeida
	 * @param tiempo, tiempo de lectura en  segundos
	 * @param libroSi
	 * @param leerBD
	 * @param IDBD
	 */
	public clsArchivo(String nick, String nomAutor, String apeAutor, String titulo, String ruta, 
			int numPags, int ultimaPagLeida, int tiempo, boolean libroSi, boolean leerBD, int IDBD) 
	{
		super();
		this.nick = nick;
		this.nomAutor = nomAutor;
		this.apeAutor = apeAutor;
		this.titulo = titulo;
		this.ruta = ruta;
		this.numPags = numPags;
		this.ultimaPagLeida = ultimaPagLeida;
		this.tiempo = tiempo;
		this.libroSi = libroSi;
		
		//Esto lo hacemos porque para la BD necesitamos crear archivos el código que leemos de la BD
		if(leerBD)
		{
			//leerBD=true, estamos usando el constructor para crear un archivo porque estamos leyéndolo de la BD
			//le ponemos el código leído
			this.codArchivo=IDBD;
		}
		else
		{
			//Estamos creando un archivo estándar, le ponemos el ID  secuencial.
			this.codArchivo = sigCodArchivo;
			sigCodArchivo ++;
		}
		
	}
	
	//Getters and Setters
	
	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getNomAutor() {
		return nomAutor;
	}
	public void setNomAutor(String nomAutor) {
		this.nomAutor = nomAutor;
	}
	public String getApeAutor() {
		return apeAutor;
	}
	public void setApeAutor(String apeAutor) {
		this.apeAutor = apeAutor;
	}
	
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getRuta() {
		return ruta;
	}
	public void setRuta(String ruta) {
		this.ruta = ruta;
	}
	public int getNumPags() {
		return numPags;
	}
	public void setNumPags(int numPags) {
		this.numPags = numPags;
	}
	public int getUltimaPagLeida() {
		return ultimaPagLeida;
	}
	public void setUltimaPagLeida(int ultimaPagLeida) {
		this.ultimaPagLeida = ultimaPagLeida;
	}
	public int getTiempo() {
		return tiempo;
	}
	public void setTiempo(int tiempo) {
		this.tiempo = tiempo;
	}
	public int getCodArchivo() {
		return codArchivo;
	}

	public void setCodArchivo(int codArchivo) {
		this.codArchivo = codArchivo;
	}
	
	public boolean getLibroSi() {
		return libroSi;
	}

	public void setLibroSi(boolean libroSi) {
		this.libroSi = libroSi;
	}
	public static int getSigCodArchivo() 
	{
		return sigCodArchivo;
	}

	public static void setSigCodArchivo(int sigCodArchivo) 
	{
		clsArchivo.sigCodArchivo = sigCodArchivo;
	}
	
	/**
	 * Este método permite generar un hashCode para los objetos clsArchivo
	 * @return result devolverá el hashCode del objeto según su codArchivo
	 */
	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + codArchivo;
		return result;
	}

	@Override
	public boolean equals(Object obj) 
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		clsArchivo other = (clsArchivo) obj;
		if (codArchivo != other.codArchivo)
			return false;
		return true;
	}
	
	/**
	 * Este método permite comparar un objeto de tipo clsArchivo con otro de su mismo tipo
	 * @param o será el objeto de tipo clsArchivo a comparar con el objto del que se llame al método
	 * @return devolverá un int que indicará si, según el criterio elegido, el objeto debería ir antes o después del otro objeto
	 */
	public int compareTo(Object o)	//devuelve un 1 o un 0
	{
		Integer a = this.getCodArchivo();
		
		clsArchivo b= (clsArchivo)o;
		
		if (o==null)
		{
			throw new NullPointerException();
		}
		
		if(o.getClass()!=this.getClass())
		{
			throw new ClassCastException();
		}
		
		return a.compareTo(b.getCodArchivo());
	}

	/**
	 * Este método servirá para mostrar los objetos por pantalla
	 */
	@Override
	public String toString() 
	{
		return this.titulo + ".  Tiempo de lectura: "+ this.tiempo;
	}
}
