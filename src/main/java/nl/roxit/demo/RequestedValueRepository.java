package nl.roxit.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestedValueRepository extends JpaRepository<RequestedValueEntity, Long> {
}
