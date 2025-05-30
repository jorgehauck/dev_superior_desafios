package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.devsuperior.dsmeta.dto.SalesReportDTO;
import com.devsuperior.dsmeta.dto.SummarySaleDTO;
import com.devsuperior.dsmeta.projections.SaleMinProjection;
import com.devsuperior.dsmeta.projections.SummarySaleProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;
	
	public SaleMinDTO findById(Long id) {
		Optional<SaleMinProjection> sale = repository.getSaleById(id);
		return new SaleMinDTO(sale.get());
	}

	public List<SummarySaleDTO> getSummary(String minDate, String maxDate) {
		String[] date = applyDefaultDateRange(minDate, maxDate);
		List<SummarySaleProjection> summarySaleProjectionList = repository.getSummary(LocalDate.parse(date[0]), LocalDate.parse(date[1]));
		List<SummarySaleDTO> summaryList = summarySaleProjectionList.stream().map(x -> new SummarySaleDTO(x)).collect(Collectors.toList());
		return summaryList;
	}

	public Page<SalesReportDTO> getReports(String minDate, String maxDate, String sellerName, Pageable pageable) {
		String[] date = applyDefaultDateRange(minDate, maxDate);

		Page<SalesReportDTO> page = repository.getReports(LocalDate.parse(date[0]), LocalDate.parse(date[1]), sellerName, pageable);
		return page;
	}

	private String[] applyDefaultDateRange(String minDate, String maxDate) {
		maxDate = maxDate == null ? LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault()).toString() : maxDate;
		minDate = minDate == null ? LocalDate.parse(maxDate).minusYears(1L).toString() : minDate;

		return new String[] { minDate, maxDate };
	}
}
