package LN;

public class clsCronometro extends Thread
{
	private boolean isRunning = true;
	private byte segundos = 00;
	
	@Override
	public void run() 
	{
		while(isRunning)
		{
			try 
			{
				Thread.sleep(1000);
			} 
			catch (InterruptedException e) 
			{
				//interrumpido
			}
			segundos++; //logger segundos
		}
	}
	
	public boolean isRunning()
	{
		return isRunning;
	}
	
	public void Play()
	{
		isRunning = true;
	}
	
	public void Pause()
	{
		isRunning = false; //pausado
	}
	
	public byte getSegundos()
	{
		return segundos; //segundos devueltos
	}
}
