package controller;

public class Suspender {
	private boolean suspendido; //false=hilo corriendo, true=hilo parado
	
	public synchronized void set(boolean b) { //Si se usa notify, notifyAll o wait, hay que poner synchronized. Si no lo pones no se queja, pero peta.
		this.suspendido=b;
		notifyAll();
	}
	
	public synchronized void waitForResume() throws InterruptedException{
		if(this.suspendido) {
			wait();
		}
	}

	public boolean getSuspendido() {
		return suspendido;
	}
}
