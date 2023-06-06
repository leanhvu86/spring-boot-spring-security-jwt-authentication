package com.trunggame.dto;

import com.trunggame.models.Banner;
import com.trunggame.models.GamePackage;
import com.trunggame.models.Post;
import lombok.*;

import java.util.List;


@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoadDataDTO {

    private List<GameInformationDTO> listGame;
    private List<Banner> banners;
    private List<Post> posts;
    private List<GameInformationDTO> newGames;
    private List<GamePackageDTO> topSale;
    private List<GamePackageDTO> newPackage;
    private List<GamePackageDTO> bestSale;
}
