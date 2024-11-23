package jsb.ep4api.repositories;

import jsb.ep4api.entities.MovieMonthlyReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieMonthlyReportRepository extends JpaRepository<MovieMonthlyReport, Long>, JpaSpecificationExecutor<MovieMonthlyReport> {
}
