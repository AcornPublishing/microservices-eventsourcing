package io.cosmos.assign;

import java.util.List;

public class AssignService {

    private CustomerDao customerDao;
    private SurveyorDao surveyorDao;
    private AssignDao assignDao;

    public void assign(Filterables filterables, Sortables sortables, Distributable distributable) {
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
