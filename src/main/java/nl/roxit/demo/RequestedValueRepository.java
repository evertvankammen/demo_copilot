package nl.roxit.demo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RequestedValueRepository extends JpaRepository<RequestedValueEntity, Long> {
    Optional<RequestedValueEntity> findByNameAndActiveTrue(String name);
    List<RequestedValueEntity> findByNameOrderByVersionDesc(String name);
    Page<RequestedValueEntity> findByNameOrderByVersionDesc(String name, Pageable pageable);
    Optional<RequestedValueEntity> findByNameAndVersion(String name, int version);
}
