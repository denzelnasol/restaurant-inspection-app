package com.group11.cmpt276_project.service.repository.impl.db;

import androidx.lifecycle.LiveData;

import com.group11.cmpt276_project.exception.RepositoryReadError;
import com.group11.cmpt276_project.exception.RepositoryWriteError;
import com.group11.cmpt276_project.service.dao.ViolationDao;
import com.group11.cmpt276_project.service.db.RestaurantDatabase;
import com.group11.cmpt276_project.service.model.Violation;
import com.group11.cmpt276_project.service.repository.IViolationRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoomViolationRepository implements IViolationRepository {

    private ViolationDao violationDao;
    private LiveData<List<Violation>> violations;

    public RoomViolationRepository(ViolationDao violationDao) {
        this.violationDao = violationDao;
        this.violations =  this.violationDao.getAllViolations();
    }

    @Override
    public LiveData<List<Violation>> getViolations() throws RepositoryReadError {
        return this.violations;
    }

    @Override
    public void saveViolations(List<Violation> violations) throws RepositoryWriteError {

        RestaurantDatabase.databaseWriteExecutor.execute(() -> {
            this.violationDao.insertOrUpdate(violations);
        });
    }
}
