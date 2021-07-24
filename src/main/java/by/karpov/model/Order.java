package by.karpov.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class Order implements BaseModel{
    private Integer id;
    private GasModel gasModel;
    private BigDecimal priceOrder;
}
