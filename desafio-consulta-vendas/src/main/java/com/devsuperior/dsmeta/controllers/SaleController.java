package com.devsuperior.dsmeta.controllers;

import com.devsuperior.dsmeta.dto.SalesReportDTO;
import com.devsuperior.dsmeta.dto.SummarySaleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.services.SaleService;

import java.util.List;

@RestController
@RequestMapping(value = "/sales")
public class SaleController {

	@Autowired
	private SaleService service;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<SaleMinDTO> findById(@PathVariable Long id) {
		SaleMinDTO dto = service.findById(id);
		return ResponseEntity.ok(dto);
	}

	@GetMapping(value = "/report")
	public ResponseEntity<Page<SalesReportDTO>> getReport(
			@RequestParam(required = false, value = "minDate") String minDate,
			@RequestParam(required = false, value = "maxDate") String maxDate,
			@RequestParam(required = false, value = "name", defaultValue = "") String name,
			Pageable pageable) {
		Page<SalesReportDTO> page = service.getReports(minDate, maxDate, name, pageable);
		return ResponseEntity.ok(page);
	}

	@GetMapping(value = "/summary")
	public ResponseEntity<List<SummarySaleDTO>> getSummary(
			@RequestParam(required = false, value = "minDate") String minDate,
			@RequestParam(required = false, value = "maxDate") String maxDate) {
		List<SummarySaleDTO> listSummary = service.getSummary(minDate, maxDate);
		return ResponseEntity.ok(listSummary);
	}
}
