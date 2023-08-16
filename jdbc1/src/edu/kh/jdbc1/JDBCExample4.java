package edu.kh.jdbc1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.kh.jdbc1.model.vo.Employee;

public class JDBCExample4 {
	// 직급명, 급여를 입력 받아
	// 해당 직급에서 입력 받은 급여보다 많이 받는 사원의
	// 이름, 직급명, 급여, 연봉을 조회하여 출력
	
	// 단, 조회 결과가 없으면 "조회 결과 없음" 출력
	
	// 조회 결과가 있으면 아래와 같이 출력
	// 선동일 / 대표 / 8000000 / 96000000
	// 송중기 / 부장 / 6000000 / 72000000
	// ...
	
	public static void main (String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			
			System.out.print("직급명 입력 : ");
			String inputJobName = sc.nextLine();
			
			System.out.print("급여 입력 : ");
			int inputSalary = sc.nextInt();
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			String url = "jdbc:oracle:thin:@localhost:1521:XE";
			String user = "kh";
			String pw = "kh1234";	
			
			conn = DriverManager.getConnection(url, user, pw);
			
			String sql = "SELECT EMP_NAME, JOB_NAME, SALARY, SALARY * 12 ANNUAL_INCOME"
					+ " FROM EMPLOYEE"
					+ " JOIN JOB USING (JOB_CODE)"
					+ " WHERE JOB_NAME = '" + inputJobName + "'"
					+ " AND SALARY > " + inputSalary;
			
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(sql);
			
			List<Employee> list = new ArrayList<>();
			
			while(rs.next()) {
				
				Employee emp = new Employee(); // 기본 생성자로 Employee 객체 생성
				
				emp.setEmpName( rs.getString("이름") ); // 조회 시 컬럼명이 "이름"
				emp.setJobName( rs.getString("직급명") );
				emp.setSalary( rs.getInt("급여") );
				emp.setAnnualIncome( rs.getInt("연봉") );
				list.add(emp);
			}
			
			
			if(list.isEmpty()) {
				System.out.println("조회 결과 없음");
				
			} else {
				for(Employee emp : list) {
					System.out.println(emp);
				}
			}
				
			
		}catch(Exception e) {
			e.printStackTrace();	
		}finally {
			try {
				
				if(rs != null) rs.close();
				if(stmt != null) stmt.close();
				if(conn != null) conn.close();
				
			}catch(Exception e){
				e.printStackTrace();	
			}
		}
		
	}
	
	
}

