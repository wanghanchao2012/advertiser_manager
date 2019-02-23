package com.emarbox.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class AvaiableAccountInfoDto {
	private Double avaiableAmount;
	private String corporateName;
	private Double accountAvaiableAmount;
}
