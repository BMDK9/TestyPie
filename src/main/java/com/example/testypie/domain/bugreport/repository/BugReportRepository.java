package com.example.testypie.domain.bugreport.repository;

import com.example.testypie.domain.bugreport.entity.BugReport;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BugReportRepository extends JpaRepository<BugReport, Long> {

    Page<BugReport> findAllByProductId(Long productId, Pageable pageable);

    Optional<BugReport> findByProductIdAndId(Long productId, Long bugReportId);
}
