package edu.kh.emp.model.dao;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import static edu.kh.emp.common.JDBCTemplate.*;
import edu.kh.emp.model.vo.Employee;

public class EmployeeDAO {

	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs = null;
	
	private Properties prop;
	
	public EmployeeDAO() {
		try {
			prop = new Properties();
			prop.loadFromXML(new FileInputStream("query.xml"));
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	/** 전체 사원 정보 조회 DAO
	 * @param conn
	 */
	public List<Employee> selectAll(Connection conn) throws Exception{
		
		// 결과 저장용 변수 선언
		List<Employee> empList = new ArrayList<Employee>();
		
		try {
			
			String sql = prop.getProperty("selectAll");
			
			// Statement 객체 생성
			
			stmt = conn.createStatement();
			
			// SQL을 수행 후 결과(ResultSet) 반환 받음
			rs = stmt.executeQuery(sql);
			
			// 조회 결과를 얻어와 한 행씩 접근하여
			// Employee 객체 생성 후 컬럼값 담기
			// -> List 추가
			while(rs.next()) {
				
				int empId = rs.getInt("EMP_ID");
				// EMP_ID 컬럼은 문자열 컬럼이지만
				// 저장된 값들이 모두 숫자 형태
				// -> DB에서 자동으로 형변환 진행해서 얻어옴
				
				String empName = rs.getString("EMP_NAME");
				String empNo = rs.getString("EMP_NO");
				String email = rs.getString("EMAIL");
				String phone = rs.getString("PHONE");
				String departmentTitle = rs.getString("DEPT_TITLE");
				String jobName = rs.getString("JOB_NAME");
				int salary = rs.getInt("SALARY");
				
				Employee emp = new Employee(empId, empName, empNo, 
						email, phone, departmentTitle, jobName, salary);
				
				empList.add(emp); // List 담기
			} // while문 종료
			
		} finally {
			close(stmt);
		}
		
		return empList;
		
	}
	
	
	/** 새로운 사원 추가 DAO
	 * @param conn
	 */
	public int insertEmployee(Connection conn, Employee emp) throws Exception{
		
			int result = 0;
		
		try {
			
			String sql = prop.getProperty("insertEmployee");
			// INSERT INTO EMPLOYEE
			// VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, SYSDATE, NULL, DEFAULT)
					
			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			// ? 에 알맞은 값 대입
			pstmt.setInt(1, emp.getEmpId());
			pstmt.setString(2, emp.getEmpName());
			pstmt.setString(3, emp.getEmpNo());
			pstmt.setString(4, emp.getEmail());
			pstmt.setString(5, emp.getPhone());
			pstmt.setString(6, emp.getDeptCode());
			pstmt.setString(7, emp.getJobCode());
			pstmt.setString(8, emp.getSalLevel());
			pstmt.setInt(9, emp.getSalary());
			pstmt.setDouble(10, emp.getBonus());
			pstmt.setInt(11, emp.getManagerId());
			
			result = pstmt.executeUpdate();
			
		} finally {
		
			close(pstmt);
		}
		
		return result;
		
	}
	
	
	/** 사번이 일치하는 사원 정보 조회 DAO
	 * @param conn
	 * @param empId
	 * @return emp
	 */
	public Employee selectEmpId(Connection conn, int empId) throws Exception{
		
		Employee emp = null;
		
		try {
			
			String sql = prop.getProperty("selectEmpId");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, empId);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				//int empId = rs.getInt("EMP_ID");
				
				String empName = rs.getString("EMP_NAME");
				String empNo = rs.getString("EMP_NO");
				String email = rs.getString("EMAIL");
				String phone = rs.getString("PHONE");
				String departmentTitle = rs.getString("DEPT_TITLE");
				String jobName = rs.getString("JOB_NAME");
				int salary = rs.getInt("SALARY");
				
				emp = new Employee(empId, empName, empNo, 
						email, phone, departmentTitle, jobName, salary);
				
			}
			
		} finally {
			close(stmt);
		}
		
		return emp;
		
		
	}
	
	/** 사번이 일치하는 사원 정보 수정 DAO
	 * @param conn
	 */
	public int updateEmployee(Connection conn, Employee emp) throws Exception{
		
		int result = 0;
		
		try {
			String sql = prop.getProperty("updateEmployee");
			
			pstmt = conn.prepareStatement(sql);
			
			
			pstmt.setString(1, emp.getEmpName());
			pstmt.setString(2, emp.getEmpNo());
			pstmt.setString(3, emp.getEmail());
			pstmt.setString(4, emp.getPhone());
			pstmt.setInt(5, emp.getEmpId());
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		
		return result;
		
	}

	
	/** 사번이 일치하는 사원 정보 삭제 DAO
	 * @param conn
	 */
	public int selectEmpId(Connection conn, Employee emp) throws Exception{
		
		int result = 0;
		
		try {
			String sql = prop.getProperty("deleteEmployee");
			
			pstmt = conn.prepareStatement(sql);
			
			
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	
	/** 사번이 일치하는 사원 정보 삭제 DAO
	 * @param conn
	 * @param empId
	 * @return result
	 */
	public int deleteEmployee(Connection conn, int empId) throws Exception{
		int result = 0;
		
		try {
			String sql = prop.getProperty("deleteEmployee");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, empId);
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	public int selectDeptEmp(Connection conn, String deptCode) {
		Employee emp = null;
		
		try {
			
			String sql = prop.getProperty("selectDeptEmp");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, deptCode);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				String empName = rs.getString("EMP_NAME");
				String empNo = rs.getString("EMP_NO");
				String email = rs.getString("EMAIL");
				String phone = rs.getString("PHONE");
				String departmentTitle = rs.getString("DEPT_TITLE");
				String jobName = rs.getString("JOB_NAME");
				int salary = rs.getInt("SALARY");
				
				emp = new Employee(deptCode, empName, empNo, 
						email, phone, departmentTitle, jobName, salary);
				
			}
			
		} finally {
			close(stmt);
		}
		
		return emp;
	}
	
}
