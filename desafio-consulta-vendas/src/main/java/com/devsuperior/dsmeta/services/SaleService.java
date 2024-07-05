package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.devsuperior.dsmeta.dto.SalesReportDTO;
import com.devsuperior.dsmeta.dto.SummarySaleDTO;
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
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	public List<SummarySaleDTO> getSummary(String minDate, String maxDate) {
		minDate = applyDefaultDateRange(minDate, maxDate)[0];
		maxDate = applyDefaultDateRange(minDate, maxDate)[1];

		List<SummarySaleProjection> summarySaleProjectionList = repository.getSummary(LocalDate.parse(minDate), LocalDate.parse(maxDate));
		List<SummarySaleDTO> summaryList = summarySaleProjectionList.stream().map(x -> new SummarySaleDTO(x)).collect(Collectors.toList());
		return summaryList;
	}

	public Page<SalesReportDTO> getReports(String minDate, String maxDate, String sellerName, Pageable pageable) {
		minDate = applyDefaultDateRange(minDate, maxDate)[0];
		maxDate = applyDefaultDateRange(minDate, maxDate)[1];

		Page<SalesReportDTO> page = repository.getReports(LocalDate.parse(minDate), LocalDate.parse(maxDate), sellerName, pageable);
		return page;
	}

	private String[] applyDefaultDateRange(String minDate, String maxDate) {
		if (maxDate == null) {
			maxDate =  LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault()).toString();
		}

		if (minDate == null) {
			minDate = LocalDate.parse(maxDate).minusYears(1L).toString();
		}
		return new String[] { minDate, maxDate };
	}
}
