package io.cosmos.assign;

import io.cosmos.assign.assign.Assign;
import io.cosmos.assign.assign.AssignBundle;
import io.cosmos.assign.assign.repository.AssignDao;
import io.cosmos.assign.customer.Customer;
import io.cosmos.assign.customer.repository.CustomerDao;
import io.cosmos.assign.distribute.Distributable;
import io.cosmos.assign.rule.Filterables;
import io.cosmos.assign.rule.Sortables;
import io.cosmos.assign.surveyor.Surveyor;
import io.cosmos.assign.surveyor.repository.SurveyorDao;

import java.util.List;

public class AssignService {

    private CustomerDao customerDao;
    private SurveyorDao surveyorDao;
    private AssignDao assignDao;

    public AssignService(CustomerDao customerDao, SurveyorDao surveyorDao, AssignDao assignDao) {
        //
        this.customerDao = customerDao;
        this.surveyorDao = surveyorDao;
        this.assignDao = assignDao;
    }

    public void assign(Filterables filterables, Sortables sortables, Distributable distributable) {
        //
        List<Customer> customers = this.customerDao.selectAll();
        List<Surveyor> surveyors = this.surveyorDao.selectAll();

        AssignBundle assignBundle = new AssignBundle(customers, surveyors);
        assignBundle.setFilterables(filterables);
        assignBundle.setSortables(sortables);
        assignBundle.setDistributable(distributable);
        List<Assign> assigns = assignBundle.assign();
        assignDao.insertAll(assigns);
    }
}
