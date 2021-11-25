package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.Contador;

public class MainController implements Initializable {

	@FXML
	private TextField tCounter;
	@FXML
	private Button bStart;
	@FXML
	private Button bStop;
	@FXML
	private Button bRestart;

	private Contador c;
	private Thread t1;
	private SimpleStringProperty time;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		c = new Contador();
		t1 = new Thread(c);
		this.time=new SimpleStringProperty();
	}

	@FXML
	public void start(ActionEvent event) {
		/*
		 * Uso un Timer para pedir el tiempo al contador cada 100ms. Solo se configura la primera vez que arranca el programa,
		 * es decir, cuando el timerTask aun es null
		 */
		if(!t1.isAlive()) { //Compruebo si el hilo ha hecho el start
			t1.start();
			this.bStart.setText("Contando");
			this.bStart.setDisable(true);

			this.bStop.setDisable(false);
			
			this.time=this.c.getTime();
			
			/*
			 * Le añado un listener al simpleStringProperty el cual seteara el textField cuando el valor del
			 * simpleStringProperty cambie
			 */
			time.addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					tCounter.setText(newValue);
				}
			});
		}else {
			if(this.c.getS().getSuspendido()) {
				this.bStart.setText("Contando");
				this.bStart.setDisable(true);
				this.bStop.setDisable(false);
				
				this.c.getS().set(false);
			}
		}
	}

	@FXML
	public void stop(ActionEvent event) {
		boolean suspended=this.c.getS().getSuspendido();
		
		if(!suspended) {
			this.c.getS().set(true);
			suspended=this.c.getS().getSuspendido();
			this.bStart.setText("Detenido");
			this.bStop.setText("Continuar");
		}else {
			this.c.getS().set(false);			
			suspended=this.c.getS().getSuspendido();
			this.bStart.setText("Contando");
			this.bStop.setText("Para");
		}
	}
	
	@FXML
	public void restart(ActionEvent event) {
		//Si se le da a reiniciar mientras el contador esta contando, primero lo paro y despues lo reseteo
		if(!this.c.getS().getSuspendido())
		this.stop(null);
		
		this.bStop.setText("Para");
		this.bStop.setDisable(true);
		this.bStart.setText("Inicia");
		this.bStart.setDisable(false);
		
		this.c.resetTime();
	}
}
