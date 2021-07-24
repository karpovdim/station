package by.karpov.service;

import by.karpov.exeption.NotFoundException;
import by.karpov.model.GasModel;
import by.karpov.repository.Dao;

import java.util.List;

public class GasModelService implements Service<GasModel> {

    private final Dao<GasModel> gasModelDao;

    public GasModelService(Dao<GasModel> gasModelDao) {
        this.gasModelDao = gasModelDao;
    }
//        return taskDao.findById(id).orElseThrow(() -> new NotFoundException(id));

    @Override
    public GasModel findById(int id) {
        return gasModelDao.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    @Override
    public List<GasModel> findAll() {
        return gasModelDao.findAll();
    }

    @Override
    public GasModel create(GasModel gasModel) {
        return gasModelDao.create(gasModel);
    }

    @Override
    public GasModel update(GasModel gasModel) {

        return gasModelDao.update(gasModel);
    }

    @Override
    public void delete(GasModel gasModel) {
        gasModelDao.delete(gasModel);


    }
}
