package nl.roxit.demo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findByName(String name);
    Optional<Company> findByRegistrationNumber(String registrationNumber);
    List<Company> findByActiveTrue();
    List<Company> findByIndustry(String industry);
}
