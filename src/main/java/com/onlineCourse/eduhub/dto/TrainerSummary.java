package com.onlineCourse.eduhub.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TrainerSummary {

	private Long id;
	private String name;
	private String description;
	private Double rating;
	private String imageUrl;
	
}
