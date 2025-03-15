package com.dairyFarm.dairyFarm.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse {
    private String categoryName;
    private List<ProductResponse> categoryData;
}
