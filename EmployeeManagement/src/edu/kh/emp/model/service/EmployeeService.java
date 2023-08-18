package edu.kh.emp.model.service;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import static edu.kh.emp.common.JDBCTemplate.*;
import edu.kh.emp.model.dao.EmployeeDAO;
import edu.kh.emp.model.vo.Employee;

public class EmployeeService {
	
	private EmployeeDAO dao = new EmployeeDAO();

	/** 전체 사원 정보 조회 서비스
	 * 
	 */
	public List<Employee> selectAll() throws Exception{
		Connection conn = getConnection();
		
		List<Employee> list = dao.selectAll(conn);
		
		close(conn);
		
		return list;
	}

	/** 사원 정보 추가 서비스
	 * @param emp
	 * @return result
	 */
	public int insertEmployee(Employee emp) throws Exception{
		Connection conn = getConnection();
		
		int result = dao.insertEmployee(conn, emp);
		
		if(result > 0) commit(conn);
		else 			rollback(conn);
		
		close(conn);
		
		return result;
	}

	/** 사번이 일치하는 사원 정보 조회
	 * 
	 * 
	 */
	public Employee selectEmpId(int empId) throws Exception{
		Connection conn = getConnection();
		
		Employee emp = dao.selectEmpId(conn, empId);
		
		close(conn);
		
		return emp;
	}
	
	/** 사번이 일치하는 사원 정보 수정
	 * @param emp
	 * @return result
	 */
	public int updateEmployee(Employee emp) throws Exception{
		Connection conn = getConnection();
		
		int result = dao.updateEmployee(conn, emp);
		
		if(result > 0) commit(conn);
		else 			rollback(conn);
		
		close(conn);
		
		return result;
	}
	
	/** 사번이 일치하는 사원 정보 삭제 서비스
	 * @param empId
	 * @return result
	 */
	public int deleteEmployee(int empId) throws Exception{
		
		Connection conn = getConnection();
		
		int result = dao.deleteEmployee(conn, empId);
		
		if(result > 0) commit(conn);
		else			rollback(conn);
		
		close(conn);
		
		return result;
	}

	/** 입력 받은 부서와 일치하는 모든 사원 정보 조회 서비스
	 * @param deptCode
<<<<<<< HEAD
	 * @return empList 
	 */
	public List<Employee> selectDeptEmp(String deptCode) throws Exception {
		Connection conn = getConnection();
=======
	 * @return empList
	 */
	public Employee selectDeptEmp(String deptCode) {
		
		Connection conn = getConnection();
		
>>>>>>> 8d38a23768ce69ae14c2cf6cd09ae809b08d1042
		List<Employee> empList = dao.selectDeptEmp(conn, deptCode);
        close(conn);
        return empList;
	}
	
	/** 입력 받은 급여 이상을 받는 모든 사원 정보 조회 서비스
	 * @param minSalary
	 * @return empList
	 */
	public List<Employee> selectSalaryEmp(int minSalary) throws Exception {
        Connection conn = getConnection();
        List<Employee> empList = dao.selectSalaryEmp(conn, minSalary);
        close(conn);
        return empList;
    }
	
	/** 부서별 급여 합 전체 조회 서비스
	 * @return deptSalaryMap
	 */
	public Map<String, Integer> selectDeptTotalSalary() throws Exception {
        Connection conn = getConnection();
        Map<String, Integer> deptSalaryMap = dao.selectDeptTotalSalary(conn);
        close(conn);
        return deptSalaryMap;
    }
	
	/** 주민등록번호가 일치하는 사원 정보 조회 서비스
	 * @param empNo
	 * @return emp
	 */
	public Employee selectByEmpNo(String empNo) throws Exception {
        Connection conn = getConnection();
        Employee emp = dao.selectByEmpNo(conn, empNo);
        close(conn);
        return emp;
    }
	
	/** 직급별 급여 평균 조회 서비스
	 * @return jobAvgSalaryMap
	 */
	public Map<String, Double> selectJobAvgSalary() throws Exception {
        Connection conn = getConnection();
        Map<String, Double> jobAvgSalaryMap = dao.selectJobAvgSalary(conn);
        close(conn);
        return jobAvgSalaryMap;
    }
 

<<<<<<< HEAD
}
=======
}
>>>>>>> 8d38a23768ce69ae14c2cf6cd09ae809b08d1042
