package by.karpov.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GasModel implements BaseModel {
    private Integer id;
   private String gasType;
   private Double price;

}
