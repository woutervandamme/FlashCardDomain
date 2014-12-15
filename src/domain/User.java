package domain;

public class User {

	
	private String email,name,pw;
	
	public User(String email, String name, String pw){
		
		setEmail(email);
		setName(name);
		setPw(pw);
		
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}
	
	@Override
	public String toString(){
		return "id: " + email;
	}
	/*
	 * 
	 * equals als naam en pw zelfde zijn
	 * 
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object user){
		
	   //misschien overkill, ma bon 
	   if (!(user instanceof User))
	            return false;
	   if (user == this)
	            return true;
	   User u = (User)user;
	   if (u.email.equals(email) && u.pw.equals(pw))
		   		return true;
	   
	   return false;
	}
	
	
}
