package by.karpov.repository;

import by.karpov.exeption.DbException;
import by.karpov.model.BaseModel;
import by.karpov.util.ConnectionManager;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.List;
import java.util.Optional;

@Slf4j
public abstract class AbstractDao<T extends BaseModel> implements Dao<T> {
    private final Class<T> type;


    @Override
    public Optional<T> findById(int id) {
        final Connection connection = ConnectionManager.open();
        try (final PreparedStatement st = connection.prepareStatement(getSqlSelectById())) {
            st.setInt(1, id);
            return findById(st);
        } catch (SQLException ex) {
            log.error("Error finding {} with id=[{}]", type.getSimpleName(), id, ex);
            throw new DbException(ex);
        }
    }

    @Override
    public List<T> findAll() {
        final Connection connection = ConnectionManager.open();
        try (final PreparedStatement st = connection.prepareStatement(getSqlSelectAll())) {
            try (final ResultSet rs = st.executeQuery()) {
                return findAll(rs);
            }
        } catch (SQLException ex) {
            log.error("Error finding all {}s", type.getSimpleName(), ex);
            throw new DbException(ex);
        }
    }

    @Override
    public T create(T t) {
        final Connection connection = ConnectionManager.open();
        try (final PreparedStatement st = connection.prepareStatement(getSqlInsert(), Statement.RETURN_GENERATED_KEYS)) {
            return create(st, t);
        } catch (SQLException ex) {
            log.error("Error while creating {}=[{}]", type.getSimpleName(), t, ex);
            throw new DbException(ex);
        }
    }

    @Override
    public void delete(T t) {
        final Connection connection = ConnectionManager.open();
        try (final PreparedStatement st = connection.prepareStatement(getSqlDelete())) {
            st.setInt(1, t.getId());
            final int count = st.executeUpdate();
            if (count > 1) {
                log.error("Error while deleting {} [{}]", type.getSimpleName(), t);
                throw new DbException();
            }
        } catch (SQLException ex) {
            log.error("Error while deleting {} with id=[{}]", type.getSimpleName(), t.getId(), ex);
            throw new DbException(ex);
        }
    }

    @Override
    public T update(T t) {
        final Connection connection = ConnectionManager.open();
        try (final PreparedStatement st = connection.prepareStatement(getSqlUpdate())) {
            return update(st, t);
        } catch (SQLException ex) {
            log.error("Error while updating {}=[{}]", type.getSimpleName(), t, ex);
            throw new DbException(ex);
        }
    }

    protected abstract String getSqlSelectById();

    protected abstract String getSqlSelectAll();

    protected abstract String getSqlInsert();

    protected abstract String getSqlDelete();

    protected abstract String getSqlUpdate();

    protected abstract Optional<T> findById(PreparedStatement st) throws SQLException;

    protected abstract List<T> findAll(ResultSet rs) throws SQLException;

    protected abstract T update(PreparedStatement st, T t) throws SQLException;

    protected abstract T create(PreparedStatement st, T t) throws SQLException;

    public AbstractDao(Class<T> type) {
        this.type = type;
    }

}
