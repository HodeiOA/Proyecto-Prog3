package LN;

public class clsCronometro implements Runnable
{
	private boolean isRunning = true;
	private byte segundos = 00;
	private byte minutos = 00;
	private byte horas = 00;
	
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
				
			}
			
			segundos++;
			
			if(segundos == 60)
			{
				minutos++;
				segundos = 00;
				
				if(minutos == 60)
				{
					horas++;
					minutos = 00;
				}
			}
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
		isRunning = false;
	}
	
	public byte getSegundos()
	{
		return segundos;
	}
	
	public byte getMinutos()
	{
		return minutos;
	}
	
	public byte getHoras()
	{
		return horas;
	}

}
