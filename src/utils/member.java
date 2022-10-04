package utils;

public class member {
	private int id;
	String grade;
	private String account, password,realname;
	
	
	public member(int id,String account, String realname,int grade) {
		super();
		this.id = id;
		this.account = account;
		this.realname = realname;
	}
	
	public int getId() {
		return id;
	}


	public String getAccount() {
		return account;
	}


	public String getRealname() {
		return realname;
	}


	public void setRealname(String realname) {
		this.realname = realname;
	}
	
	public String getGrade() {
		return grade;
	}
}
