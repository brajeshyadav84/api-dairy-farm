package com.dairyFarm.dairyFarm.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    private Long id;
    private String name;
    private String image;
    private BigDecimal price;
    private BigDecimal quantity;
    private String unit;
    private Integer discount;
    private Integer count;
    private Boolean isSoldOut;
}
