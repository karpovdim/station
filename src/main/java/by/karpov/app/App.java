package by.karpov.app;

import by.karpov.model.GasModel;
import by.karpov.repository.GasModelDao;
import by.karpov.service.GasModelService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App {
    public static void main(String[] args) {

        final GasModelService gasModelService = getService();

        var byId = gasModelService.findById(2);
        System.out.println(byId);

        gasModelService.delete(GasModel.builder()
                .id(3)
                .build());

        gasModelService.create(GasModel.builder()
                .gasType("AI-95")
                .price(1.95)
                .build());
        byId.setPrice(4.3);
        byId.setGasType("AI-92");
        var update = gasModelService.update(byId);

        System.out.println(update);
        var gasModels = gasModelService.findAll();
       // System.out.println(gasModels.toString());
    }



    private static GasModelService getService() {
        final GasModelDao gasModelDao = new GasModelDao();
        final GasModelService gasModelService = new GasModelService(gasModelDao);
        return gasModelService;
    }
}
