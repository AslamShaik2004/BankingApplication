package com.abc.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Model {
	private String name;
	private String custid;
	private int accno;
	private String pwd;
	private int bal;
	private String email;
	private int raccno;
	
	private Connection con;
	private PreparedStatement pstmt;
	private ResultSet res;
	public ArrayList al = new ArrayList();
	public ArrayList sal = new ArrayList();
	public ArrayList ral = new ArrayList();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCustid() {
		return custid;
	}
	public void setCustid(String custid) {
		this.custid = custid;
	}
	public int getAccno() {
		return accno;
	}
	public void setAccno(int accno) {
		this.accno = accno;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public int getBal() {
		return bal;
	}
	public void setBal(int bal) {
		this.bal = bal;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public int getRaccno() {
		return raccno;
	}
	public void setRaccno(int raccno) {
		this.raccno = raccno;
	}
	
	public Model() throws Exception {
		Class.forName("com.mysql.cj.jdbc.Driver");//Loading the driver
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BankApplication", "root", "root");
		System.out.println("Loading the driver and establishing the connection is completed");
	}
	
	
	public boolean register() throws SQLException {
		String s = "insert into ABCBank values(?,?,?,?,?,?)";//Incomplete query
		pstmt = con.prepareStatement(s);
		pstmt.setString(1, name);
		pstmt.setString(2, custid);
		pstmt.setInt(3, accno);
		pstmt.setString(4, pwd);
		pstmt.setInt(5, bal);
		pstmt.setString(6, email);
		
		int x = pstmt.executeUpdate();
		
		if(x>0) {
			return true;
		}
		
		return false;
	}
	public boolean checkDuplicateUser() {
	    boolean status = false;
	    try {
	        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BankApplication", "root", "root");
	        PreparedStatement ps = con.prepareStatement("SELECT * FROM ABCBank WHERE custid = ? OR accno = ?");
	        ps.setString(1, custid);
	        ps.setInt(2, accno);

	        ResultSet rs = ps.executeQuery();
	        if (rs.next()) {
	            status = true;  // user already exists
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return status;
	}

	public boolean login() throws SQLException {
		String s = "select * from ABCBank where custid=? and password=?";
		pstmt = con.prepareStatement(s);
		pstmt.setString(1, custid);
		pstmt.setString(2, pwd);
		
		ResultSet res = pstmt.executeQuery();
		
		while(res.next()==true) {
			accno = res.getInt("ACCNO");
			return true;
		}
		return false;
	}
	public boolean checkBalance() throws SQLException {
		String s = "select balance from ABCBank where accno=?";
		pstmt = con.prepareStatement(s);
		pstmt.setInt(1, accno);
		ResultSet res = pstmt.executeQuery();
		while(res.next()==true) {
			bal = res.getInt("BALANCE");
			return true;
		}
		
		return false;
	}
	public boolean changePwd() throws SQLException {
		String s = "update ABCBank set password=? where accno=?";
		pstmt = con.prepareStatement(s);
		pstmt.setString(1, pwd);
		pstmt.setInt(2, accno);
		int x = pstmt.executeUpdate();
		if(x>0) {
			return true;
		}
		return false;
	}
	public boolean transfer() throws SQLException {
	    con.setAutoCommit(false); // Start transaction

	    // Step 1: Check if receiver account exists
	    String s1 = "SELECT * FROM ABCBank WHERE accno = ?";
	    PreparedStatement ps1 = con.prepareStatement(s1);
	    ps1.setInt(1, raccno);
	    ResultSet rs1 = ps1.executeQuery();
	    if (!rs1.next()) {
	        System.out.println("Receiver account doesn't exist");
	        return false;
	    }

	    // Step 2: Check sender's balance
	    String sCheck = "SELECT balance FROM ABCBank WHERE accno = ?";
	    PreparedStatement psCheck = con.prepareStatement(sCheck);
	    psCheck.setInt(1, accno);
	    ResultSet rsCheck = psCheck.executeQuery();
	    if (!rsCheck.next()) {
	        System.out.println("Sender account doesn't exist");
	        return false;
	    }
	    int senderBal = rsCheck.getInt("balance");
	    if (senderBal < bal) {
	        System.out.println("Insufficient balance");
	        return false;
	    }

	    // Step 3: Deduct from sender
	    String s2 = "UPDATE ABCBank SET balance = balance - ? WHERE accno = ?";
	    PreparedStatement ps2 = con.prepareStatement(s2);
	    ps2.setInt(1, bal);
	    ps2.setInt(2, accno);
	    int updateSender = ps2.executeUpdate();

	    // Step 4: Add to receiver
	    String s3 = "UPDATE ABCBank SET balance = balance + ? WHERE accno = ?";
	    PreparedStatement ps3 = con.prepareStatement(s3);
	    ps3.setInt(1, bal);
	    ps3.setInt(2, raccno);
	    int updateReceiver = ps3.executeUpdate();

	    // Step 5: Insert into GetStatement
	    String s4 = "INSERT INTO GetStatement (accno, raccno, balance) VALUES (?, ?, ?)";
	    PreparedStatement ps4 = con.prepareStatement(s4);
	    ps4.setInt(1, accno);
	    ps4.setInt(2, raccno);
	    ps4.setInt(3, bal);
	    int insertStmt = ps4.executeUpdate();

	    if (updateSender > 0 && updateReceiver > 0 && insertStmt > 0) {
	        con.commit();
	        return true;
	    } else {
	        con.rollback();
	        return false;
	    }
	}

	public ArrayList getStatement() throws SQLException {
		String s = "select * from GetStatement where accno=?";
		pstmt = con.prepareStatement(s);
		pstmt.setInt(1, accno);
		res = pstmt.executeQuery();
		
		while(res.next()==true) {
			sal.add(res.getInt("ACCNO"));
			ral.add(res.getInt("RACCNO"));
			al.add(res.getInt("BALANCE"));
		}
		
		return al;
	}
	public boolean applyLoan() throws SQLException {
		String s = "select * from ABCBank where accno=?";
		pstmt = con.prepareStatement(s);
		pstmt.setInt(1, accno);
		res = pstmt.executeQuery();
		while(res.next()==true) {
			name = res.getString("NAME");
			email = res.getString("EMAIL");
			return true;
		}
		return false;
	}
	
	
}
