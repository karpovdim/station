package by.karpov.repository;

import by.karpov.exeption.DbException;
import by.karpov.model.GasModel;
import by.karpov.util.ConnectionManager;
import lombok.extern.slf4j.Slf4j;
import org.intellij.lang.annotations.Language;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class GasModelDao implements Dao<GasModel>{
    @Language("SQL")
    private static final String SELECT_BY_ID = "select * from gas_model where id = ?";
    @Language("SQL")
    private static final String SELECT_ALL = "select * from  gas_model";
    @Language("SQL")
    private static final String INSERT = "insert into gas_model (gasType, price) VALUES(?, ?)";
    @Language("SQL")
    private static final String DELETE = "delete from gas_model where id = ?";
    @Language("SQL")
    private static final String UPDATE = "update gas_model set gasType=?, price=? where id = ?";

  // @Override
//    public Optional<GasModel> findById(int id) {
//        return Optional.empty();
//    }
    @Override
    public Optional<GasModel> findById(int id) {
        final Connection connection = ConnectionManager.open();
        try (final PreparedStatement st = connection.prepareStatement(SELECT_BY_ID)) {
            st.setInt(1, id);
            try (final ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(GasModel.builder()
                            .id(rs.getInt("id"))
                            .gasType(rs.getString("gasType"))
                            .price(rs.getDouble("price"))
                            .build());
                }
                return Optional.empty();
            }
        } catch (SQLException ex) {
            log.error("Error finding event", ex);
            throw new DbException(ex);
        }
    }

    @Override
    public List<GasModel> findAll() {
        final Connection connection = ConnectionManager.open();
        try (final PreparedStatement st = connection.prepareStatement(SELECT_ALL)) {
            try (final ResultSet rs = st.executeQuery()) {
                final ArrayList<GasModel> gasModels = new ArrayList<>();
                while (rs.next()) {
                    gasModels.add(GasModel.builder()
                            .id(rs.getInt("id"))
                            .gasType(rs.getString("gasType"))
                            .price(rs.getDouble("price"))
                            .build());
                }
                return gasModels;
            }
        } catch (SQLException ex) {
            log.error("Error finding all events", ex);
            throw new DbException(ex);
        }
    }

    @Override
    public GasModel create(GasModel gasModel) {
        final Connection connection = ConnectionManager.open();
        try (final PreparedStatement st = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1, gasModel.getGasType());
            st.setDouble(2, gasModel.getPrice());
            st.executeUpdate();
            try (final ResultSet keys = st.getGeneratedKeys()) {
                if (keys.next()) {
                    final int id = keys.getInt(1);
                    gasModel.setId(id);
                    return gasModel;
                }
                log.error("Error while creating task [{}]", gasModel);
                throw new DbException();
            }
        } catch (SQLException ex) {
            log.error("Error while creating task", ex);
            throw new DbException(ex);
        }
    }
    @Override
    public void delete(GasModel gasModel) {
        final Connection connection = ConnectionManager.open();
        try (final PreparedStatement st = connection.prepareStatement(DELETE)) {
            st.setInt(1, gasModel.getId());
            final int count = st.executeUpdate();
            if (count > 1) {
                log.error("Error while deleting task [{}]", gasModel);
                throw new DbException();
            }
        } catch (SQLException ex) {
            log.error("Error while deleting task", ex);
            throw new DbException(ex);
        }
    }
    @Override
    public GasModel update(GasModel gasModel) {
        final Connection connection = ConnectionManager.open();
        try (final PreparedStatement st = connection.prepareStatement(UPDATE)) {
            st.setString(1, gasModel.getGasType());
            st.setDouble(2, gasModel.getPrice());
            st.setInt(3, gasModel.getId());

            final int count = st.executeUpdate();
            if (count > 1) {
                log.error("Error while updating task [{}]", gasModel);
                throw new DbException();
            }
            return gasModel;
        } catch (SQLException ex) {
            log.error("Error while updating task", ex);
            throw new DbException(ex);
        }
    }
}

