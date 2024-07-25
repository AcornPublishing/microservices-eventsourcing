package io.cosmos.assign;

import io.cosmos.assign.assign.Assign;
import io.cosmos.assign.assign.AssignBundle;
import io.cosmos.assign.assign.repository.AssignDao;
import io.cosmos.assign.customer.Customer;
import io.cosmos.assign.customer.repository.CustomerDao;
import io.cosmos.assign.distribute.Distributable;
import io.cosmos.assign.distribute.RoundRobinDistributor;
import io.cosmos.assign.rule.Filterables;
import io.cosmos.assign.rule.GenderRule;
import io.cosmos.assign.rule.Sortables;
import io.cosmos.assign.surveyor.Surveyor;
import io.cosmos.assign.surveyor.repository.SurveyorDao;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class AssignBundleTest {

    @Test
    public void assign() {
        //
        CustomerDao customerDao = new CustomerDao();
        SurveyorDao surveyorDao = new SurveyorDao();
        AssignDao assignDao = new AssignDao();

        AssignService assignService = new AssignService(customerDao, surveyorDao, assignDao);

        GenderRule rule = new GenderRule("M");
        Filterables filterables = new Filterables(rule);
        Sortables sortables = new Sortables(rule);
        Distributable distributable = new RoundRobinDistributor();

        assignService.assign(filterables, sortables, distributable);

        List<Assign> assigns = assignDao.selectAll();
        for (Assign assign: assigns) {
            System.out.println(assign.getCustomer().getName() + ", " + assign.getCustomer().getBirthday().getAge() + ", " + assign.getSurveyor().getId());
        }
    }
}
