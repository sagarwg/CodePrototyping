package com.mindcraft.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockMainWsDto{

    private Integer id;

    private String tickerSymbol;

    private String companyName;

    private String exchange;

    private String sector;

    private Double currentPrice;
    
    private Double tradeInPrice;

    private Double marketCap;

    private Long volume;

    private Double peRatio;

    private Double eps;

    private Double dividendYield;

    private java.sql.Timestamp lastUpdated;
}
