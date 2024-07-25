package io.cosmos.assign.surveyor.repository;

import io.cosmos.assign.surveyor.Surveyor;

import java.util.ArrayList;
import java.util.List;

public class SurveyorDao {
    //
    List<Surveyor> surveyors = new ArrayList<>();

    public SurveyorDao() {
        //
        Surveyor surveyor1 = new Surveyor("S1", "조사원#1");
        Surveyor surveyor2 = new Surveyor("S2", "조사원#2");

        surveyors.add(surveyor1);
        surveyors.add(surveyor2);
    }
    public List<Surveyor> selectAll() {
        //
        return surveyors;
    }
}
