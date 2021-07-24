package by.karpov.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class LevelStock implements BaseModel {
    private Integer id;
    private GasModel gas;
    private BigDecimal amount;

}
