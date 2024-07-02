package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import com.devsuperior.dsmeta.dto.SummarySaleDTO;
import com.devsuperior.dsmeta.projections.SummarySaleProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
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

	public List<SummarySaleDTO> getReport(String minDate, String maxDate) {
		if(!(minDate.isBlank() && maxDate.isBlank())) {
			List<SummarySaleProjection> list = repository.getReport(LocalDate.parse(minDate), LocalDate.parse(maxDate));
			List<SummarySaleDTO> listReports = list.stream().map(x -> new SummarySaleDTO(x)).toList();
			return listReports;
		}
		return null;
	}
}
