package com.trunggame.dto;

import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TagDeleteDTO implements Serializable {
    private ArrayList<Long> ids;
}
