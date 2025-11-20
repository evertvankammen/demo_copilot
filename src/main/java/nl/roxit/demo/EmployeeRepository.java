package nl.roxit.demo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmail(String email);
    List<Employee> findByCompanyId(Long companyId);
    List<Employee> findByActiveTrue();
    List<Employee> findByDepartment(String department);
    List<Employee> findByCompanyIdAndActiveTrue(Long companyId);
    
    @Query("SELECT e FROM Employee e WHERE e.company.id = ?1 AND e.department = ?2")
    List<Employee> findByCompanyAndDepartment(Long companyId, String department);
    
    @Query("SELECT e FROM Employee e JOIN FETCH e.address WHERE e.id = ?1")
    Optional<Employee> findByIdWithAddress(Long id);
}
