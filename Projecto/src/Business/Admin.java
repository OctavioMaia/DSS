package Business;

public class Admin {

	private int idAdmin;
	private String pin;
	
	public Admin(int id,String pass){
		this.idAdmin=id;
		this.pin=pass;
	}
	
	public int getIdAdmin() {
        return idAdmin;
    }
	public String getPin() {
		return pin;
	}
	public void setIdAdmin(int x){
		this.idAdmin = x;
	}
	public void setPin(String pin){
		this.pin = pin;
	}
	
	
	public boolean verificarPin(String pin){
		return (this.pin.equals(pin));
	}

}
