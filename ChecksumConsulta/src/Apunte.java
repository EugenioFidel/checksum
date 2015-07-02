
public class Apunte {
	String fichero;
	String chk;
	String cod;
	
	public Apunte(String fichero, String chk, String cod) {
		super();
		this.fichero = fichero;
		this.chk = chk;
		this.cod = cod;
	}
	public String getFichero() {
		return fichero;
	}
	public void setFichero(String fichero) {
		this.fichero = fichero;
	}
	public String getChk() {
		return chk;
	}
	public void setChk(String chk) {
		this.chk = chk;
	}
	public String getCod() {
		return cod;
	}
	public void setCod(String cod) {
		this.cod = cod;
	}
}
