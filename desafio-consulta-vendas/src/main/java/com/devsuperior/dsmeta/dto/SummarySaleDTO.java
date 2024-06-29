package com.devsuperior.dsmeta.dto;

import com.devsuperior.dsmeta.projections.SummarySaleProjection;

public class SummarySaleDTO {
    private String sellerName;
    private Double total;
    public SummarySaleDTO() {}
    public SummarySaleDTO(String sellerName, Double total) {
        this.sellerName = sellerName;
        this.total = total;
    }
    public SummarySaleDTO(SummarySaleProjection projection) {
        sellerName = projection.getSellerName();
        total = projection.getTotal();
    }

    public String getSellerName() {
        return sellerName;
    }

    public Double getTotal() {
        return total;
    }
}
