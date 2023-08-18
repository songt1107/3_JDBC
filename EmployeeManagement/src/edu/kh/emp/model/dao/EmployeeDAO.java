package edu.kh.emp.model.dao;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
			prop.loadFromXML( new FileInputStream("query.xml") );
			
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
						email, phone, departmentTitle, jobName, salary );
				
				empList.add(emp); // List 담기
			
			} // while문 종료
			
			
		} finally {
			
			close(stmt);
			
		}
		
		// 결과 반환
		return empList;
		
	}

	/** 사원 정보 추가 DAO
	 * @param conn
	 * @param emp
	 * @return result
	 */
	public int insertEmployee(Connection conn, Employee emp) throws Exception{
		
		int result = 0;
		
		try {
			
			// SQL 작성
			String sql = prop.getProperty("insertEmployee");
			// INSERT INTO EMPLOYEE VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, SYSDATE, NULL, DEFAULT)
			
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
				
				//int empId2 = rs.getInt("EMP_ID");
				String empName = rs.getString("EMP_NAME");
				String empNo = rs.getString("EMP_NO");
				String email = rs.getString("EMAIL");
				String phone = rs.getString("PHONE");
				String departmentTitle = rs.getString("DEPT_TITLE");
				String jobName = rs.getString("JOB_NAME");
				int salary = rs.getInt("SALARY");
				
				emp = new Employee(empId, empName, empNo, email,
								phone, departmentTitle, jobName, salary
						);
					
			}
				
		} finally {
			close(pstmt);
		}
		
		return emp;
	}

	/** 사번이 일치하는 사원 정보 수정 DAO
	 * @param conn
	 * @param emp
	 * @return result
	 */
	public int updateEmployee(Connection conn, Employee emp) throws Exception{
		
		int result = 0;
		
		try {
			String sql = prop.getProperty("updateEmployee");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, emp.getEmail());
			pstmt.setString(2, emp.getPhone());
			pstmt.setInt(3, emp.getSalary());
			pstmt.setInt(4, emp.getEmpId());
			
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

	/** 입력 받은 부서와 일치하는 모든 사원 정보 조회 DAO
	 * @param conn
	 * @param departmentTitle
	 * @return
	 */
	public List<Employee> selectDeptEmp(Connection conn, String departmentTitle) throws Exception{
		
		List<Employee> empList = new ArrayList<Employee>();
		
		try {
			
			String sql = prop.getProperty("selectDeptEmp");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, departmentTitle);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				int empId = rs.getInt("EMP_ID"); 
				String empName = rs.getString("EMP_NAME");
				String empNo = rs.getString("EMP_NO");
				String email = rs.getString("EMAIL");
				String phone = rs.getString("PHONE");
				//String departmentTitle_1 = rs.getString("DEPT_TITLE");
				String jobName = rs.getString("JOB_NAME");
				int salary = rs.getInt("SALARY");
				
				Employee emp = new Employee(empId, empName, empNo, email, 
								phone, departmentTitle, jobName, salary);
				
				empList.add(emp);
			}
			
		} finally {
			close(pstmt);
		}
		
		return empList;
	}

	/** 입력 받은 급여 이상을 받는 모든 사원 정보 조회 DAO
	 * @param conn
	 * @param salary
	 * @return
	 */
	public List<Employee> selectSalaryEmp(Connection conn, int salary) throws Exception{
		List<Employee> empList = new ArrayList<Employee>();
		

		try {
			
			String sql = prop.getProperty("selectSalaryEmp");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, salary);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				int empId = rs.getInt("EMP_ID"); 
				String empName = rs.getString("EMP_NAME");
				String empNo = rs.getString("EMP_NO");
				String email = rs.getString("EMAIL");
				String phone = rs.getString("PHONE");
				String departmentTitle = rs.getString("DEPT_TITLE");
				String jobName = rs.getString("JOB_NAME");
				int selectSalary = rs.getInt("SALARY");
				
				Employee emp = new Employee(empId, empName, empNo, email, 
								phone, departmentTitle, jobName, selectSalary);
				
				empList.add(emp);
			}
			
		} finally {
			close(pstmt);
		}
		
		return empList;
	}

	/** 부서별 급여 합 전체 조회 DAO
	 * @param conn
	 * @return
	 */
	public Map<String, Integer> selectDeptTotalSalary(Connection conn) throws Exception {
		
		Map<String , Integer> map = new LinkedHashMap<String, Integer>();
		// LinkedHashMap : key 순서가 유지되는 HashMap (ORDER BY 절 정렬 결과 그대로 저장함)
		
		try {
			String sql = prop.getProperty("selectDeptTotalSalary");
			
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				String deptCode = rs.getString("DEPT_CODE");
				int total = rs.getInt("TOTAL");
				
				map.put(deptCode, total);
			}	
			
		} finally {
			close(stmt);
		}
		
		return map;
	}

	/** 주민등록 번호가 일치하는 사원 정보 조회 DAO
	 * @param conn
	 * @param empNo
	 * @return
	 */
	public Employee selectEmpNo(Connection conn, String empNo) throws Exception{
		
		// 결과 저장용 변수 선언
		Employee emp = null;
		
		try {
			String sql = prop.getProperty("selectEmpNo");
			
			pstmt = conn.prepareStatement(sql);
			
			// ? 에 알맞은값 대입
			pstmt.setString(1, empNo);
			
			// SQL 수행 후 결과 반환
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				int empId = rs.getInt("EMP_ID"); 
				String empName = rs.getString("EMP_NAME");
				//String empNo = rs.getString("EMP_NO");
				String email = rs.getString("EMAIL");
				String phone = rs.getString("PHONE");
				String departmentTitle = rs.getString("DEPT_TITLE");
				String jobName = rs.getString("JOB_NAME");
				int salary = rs.getInt("SALARY");
				
				emp = new Employee(empId, empName, empNo, email, 
								phone, departmentTitle, jobName, salary);
			}
			
		}finally {
			close(pstmt);
		}
		
		return emp;
	}

	/** 직급별 급여 평균 조회 DAO
	 * @param conn
	 * @return
	 */
	public Map<String, Double> selectJobAvgSalary(Connection conn) throws Exception{
		
		Map<String, Double> map = new LinkedHashMap<>();

		try{
			String sql = prop.getProperty("selectJobAvgSalary");
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				String jobName = rs.getString("JOB_NAME");
				double average = rs.getDouble("AVERAGE");
				
				map.put(jobName, average);
			}
			
		}finally {
			close(stmt);
		}
		
		return map;
	}
	
	
}
