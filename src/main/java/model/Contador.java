package model;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import controller.Suspender;
import javafx.beans.property.SimpleStringProperty;

public class Contador implements Runnable{

	private LocalTime localTime;
	private Suspender s;
	private DateTimeFormatter dtf;
	private SimpleStringProperty time; //Esto es un wrapper de String al cual le puedes añadir Listeners
	
	public Contador() {
		this.s=new Suspender();
		time=new SimpleStringProperty("00:00:00");
		this.dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
		this.localTime=LocalTime.parse(time.get(), dtf); 
		this.s.set(false);
	}
	
	@Override
	public void run() {
		while(true) {
			try {
				this.s.waitForResume();
				
				/*
				 * Le doy formato y lo guardo en una variable aparte porque el toString de localTime tiende a sacar el string en formato HH:mm 
				 * cuando la parte de los segundos es igual a :00 (se quedaba feo) 
				 */
				this.time.set(this.localTime.format(dtf));
				
				/*
				 * Sumo un segundo al localTime y me espero 1 segundo en el sleep en cada vuelta del while. El localTime se encarga gestionar la
				 * logica del tiempo (el paso de segundos a minutos) y de devolverme el String con el tiempo formateado.
				 */
				this.localTime=localTime.plusSeconds(1);
				
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				System.exit(0);
				e.printStackTrace();
			}
		}
	}
	
	public void resetTime() {
		this.time.set("00:00:00");
		this.localTime=LocalTime.parse(this.time.get(), this.dtf);
	}

	public SimpleStringProperty getTime() {
		return time;
	}

	public Suspender getS() {
		return s;
	}
}
