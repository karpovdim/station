package by.karpov.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GasPumpModel implements BaseModel {
    private Integer id;
    private String namePump;
    private List<GasModel> gasModel;
}
