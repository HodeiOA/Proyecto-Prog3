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
				//interrumpido
			}
			
			segundos++;
			
			if(segundos == 60)
			{
				minutos++; //+1 minuto
				segundos = 00;
				
				if(minutos == 60)
				{
					horas++; //+1 hora
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
		isRunning = false; //pausado
	}
	
	public byte getSegundos()
	{
		return segundos; //segundos devueltos
	}
	
	public byte getMinutos()
	{
		return minutos; //minutos devueltos
	}
	
	public byte getHoras()
	{
		return horas; //horas devueltas
	}

}
