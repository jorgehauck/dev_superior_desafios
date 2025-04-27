package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SalesReportDTO;
import com.devsuperior.dsmeta.projections.SaleMinProjection;
import com.devsuperior.dsmeta.projections.SummarySaleProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query(nativeQuery = true, value = "SELECT tb_sales.id, tb_sales.amount, tb_sales.date FROM tb_sales WHERE tb_sales.id = :id")
    Optional<SaleMinProjection> getSaleById(Long id);
	
    @Query(nativeQuery = true, value = "SELECT tb_seller.name AS sellerName, SUM(tb_sales.amount) AS total " +
            "FROM tb_seller " +
            "INNER JOIN tb_sales ON tb_sales.seller_id = tb_seller.id " +
            "WHERE tb_sales.date BETWEEN :minDate AND :maxDate " +
            "GROUP BY tb_seller.name")
    List<SummarySaleProjection> getSummary(LocalDate minDate, LocalDate maxDate);

    @Query("SELECT new com.devsuperior.dsmeta.dto.SalesReportDTO(sale.id, CAST(sale.date AS string), sale.amount, sale.seller.name) " +
            "FROM Sale sale " +
            "WHERE sale.date BETWEEN :minDate AND :maxDate " +
            "AND UPPER(sale.seller.name) LIKE UPPER(CONCAT('%', :name, '%'))")
    Page<SalesReportDTO> getReports(LocalDate minDate, LocalDate maxDate, String name, Pageable pageable);
}
