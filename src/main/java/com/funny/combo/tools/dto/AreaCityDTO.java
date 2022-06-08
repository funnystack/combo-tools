package com.funny.combo.tools.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AreaCityDTO {

    private Integer id;

    private Long cityId;

    private String cityName;

    private Integer provinceId;

    private String provinceName;

    private String similarNames;
}
