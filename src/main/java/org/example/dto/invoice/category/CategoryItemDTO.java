package org.example.dto.invoice.category;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CategoryItemDTO {
    private Long id;
    private String name;
    private String image;
    private String description;
    private LocalDateTime creationTime;
}