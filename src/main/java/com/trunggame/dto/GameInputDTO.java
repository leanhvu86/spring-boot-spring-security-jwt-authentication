package com.trunggame.dto;


import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.trunggame.models.SmartTag;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.NonNull;


import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.Set;

@Data
@Builder
@Getter
@Setter

public class GameInputDTO {

    private Long id;

    @NonNull
    private String name;

    @NonNull
    private String description;

    @NonNull
    @JsonPropertyDescription("Các loại game khác nhau sẽ yêu cầu require các trường khác nhau")
    private String type;

    @JsonPropertyDescription("Độ ưu tiên của game: hot, new, sale off, gift, normal" +
            "Lưu tương ứng xuống databse là 1-hot, 2-new, 3- sall off, 4 - gift, 5 - best seller ... tuỳ các thánh định nghĩa")
    @Builder.Default
    private String gamePriority = "normal";

    @NonNull
    private Long categoryId;

    private String imageId;

    private String thumbnail;

    @NonNull
    private Long quantity;

    @NonNull
    private BigDecimal price;

    private BigDecimal promotionPrice;

    private BigDecimal promotionPercent;

    private String contentVi;

    private String contentEn;

    private String descriptionEn;

    private Set<String> tags;

    private String youtubeLink;

    private String marketType;

    private String companyName;

}
