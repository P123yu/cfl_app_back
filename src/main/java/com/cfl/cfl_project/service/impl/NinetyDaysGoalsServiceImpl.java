//package com.cfl.cfl_project.service.impl;
//
//import com.cfl.cfl_project.model.NinetyDaysGoals;
//import com.cfl.cfl_project.model.SixtyDaysGoals;
//import com.cfl.cfl_project.model.ThirtyDaysGoals;
//import com.cfl.cfl_project.repository.NinetyDaysGoalsRepository;
//import com.cfl.cfl_project.repository.ThirtyDaysGoalsRepository;
//import com.cfl.cfl_project.service.NinetyDaysGoalsService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cache.annotation.Cacheable;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//public class NinetyDaysGoalsServiceImpl implements NinetyDaysGoalsService {
//    @Autowired
//    private NinetyDaysGoalsRepository ninetyDaysGoalsRepository;
//
//    //
//    public static String getQuarter() {
//        LocalDate date=LocalDate.now();
//        int month = date.getMonthValue();
//
//        if (month >= 1 && month <= 3) {
//            return "Q4";
//        } else if (month >= 4 && month <= 6) {
//            return "Q1";
//        } else if (month >= 7 && month <= 9) {
//            return "Q2";
//        } else if (month >= 10 && month <= 12) {
//            return "Q3";
//        } else {
//            throw new IllegalArgumentException("Invalid month: " + month);
//        }
//    }
//
//    @Override
//    public List<NinetyDaysGoals> createNinetyDaysGoals(Long empId, List<NinetyDaysGoals> ninetyDaysGoals) {
//        Long year= (long) LocalDate.now().getYear();
//        if(getQuarter().equals("Q4")){
//            year=year-1;
//        }
//
//        List<NinetyDaysGoals>ninetyDaysGoalsLists=new ArrayList<>();
//        for(NinetyDaysGoals i: ninetyDaysGoals){
//            NinetyDaysGoals ninetyDaysGoals1=new NinetyDaysGoals();
//            ninetyDaysGoals1.setEmpId(empId);
//            ninetyDaysGoals1.setStatus(i.getStatus());
//            ninetyDaysGoals1.setQuarter(getQuarter());
//            ninetyDaysGoals1.setGoal(i.getGoal());
//            ninetyDaysGoals1.setDeliverable(i.getDeliverable());
//            ninetyDaysGoals1.setWeightage(i.getWeightage());
//            ninetyDaysGoals1.setYear(year);
//            ninetyDaysGoalsLists.add(ninetyDaysGoals1);
//        }
//        System.out.println(ninetyDaysGoalsLists+"ninetyDaysGoalsLists");
//        return ninetyDaysGoalsRepository.saveAll(ninetyDaysGoalsLists);
//
//    }
//
//
//    @Override
//    public List<NinetyDaysGoals> updateNinetyDaysGoals(List<NinetyDaysGoals> ninetyDaysGoals) {
//        return ninetyDaysGoalsRepository.saveAll(ninetyDaysGoals.stream()
//                .peek(i-> i.setStatus("Approved")).collect(Collectors.toList()));
//    }
//
//    @Override
//  //  @Cacheable(value = "ninetyDaysGoals", key="'byEmpIdNinetyDaysGoals_' + #empId + '_quarterNinetyDaysGoals_' + #quarter")
//    public List<NinetyDaysGoals> getNinetyDaysGoalsByEmpIdAndQuarter(Long empId,String quarter) {
//        return ninetyDaysGoalsRepository.findByEmpIdAndQuarter(empId,quarter);
//    }
//}


package com.cfl.cfl_project.service.impl;

import com.cfl.cfl_project.model.Cfl;
import com.cfl.cfl_project.model.NinetyDaysGoals;
import com.cfl.cfl_project.model.SixtyDaysGoals;
import com.cfl.cfl_project.model.ThirtyDaysGoals;
import com.cfl.cfl_project.repository.CflRepository;
import com.cfl.cfl_project.repository.NinetyDaysGoalsRepository;
import com.cfl.cfl_project.repository.ThirtyDaysGoalsRepository;
import com.cfl.cfl_project.service.NinetyDaysGoalsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NinetyDaysGoalsServiceImpl implements NinetyDaysGoalsService {
    @Autowired
    private NinetyDaysGoalsRepository ninetyDaysGoalsRepository;

    //
    public static String getQuarter() {
        LocalDate date=LocalDate.now();
        int month = date.getMonthValue();

        if (month >= 1 && month <= 3) {
            return "Q4";
        } else if (month >= 4 && month <= 6) {
            return "Q1";
        } else if (month >= 7 && month <= 9) {
            return "Q2";
        } else if (month >= 10 && month <= 12) {
            return "Q3";
        } else {
            throw new IllegalArgumentException("Invalid month: " + month);
        }
    }

    @Autowired
    private CflRepository cflRepository;

    @Override
    public List<NinetyDaysGoals> createNinetyDaysGoals(Long empId, String quarter, List<NinetyDaysGoals> ninetyDaysGoals) {


        Cfl cfl=cflRepository.findByEmpId(empId);
        List<NinetyDaysGoals>ninetyDaysGoalsLists=new ArrayList<>();
        for(NinetyDaysGoals i: ninetyDaysGoals){
            NinetyDaysGoals ninetyDaysGoals1=new NinetyDaysGoals();
            ninetyDaysGoals1.setEmpId(empId);
            ninetyDaysGoals1.setStatus(i.getStatus());
            ninetyDaysGoals1.setQuarter(quarter);
            ninetyDaysGoals1.setGoal(i.getGoal());
            ninetyDaysGoals1.setDeliverable(i.getDeliverable());
            ninetyDaysGoals1.setWeightage(i.getWeightage());
            ninetyDaysGoals1.setYear(Long.valueOf(cfl.getYear()));
            ninetyDaysGoalsLists.add(ninetyDaysGoals1);
        }
        System.out.println(ninetyDaysGoalsLists+"ninetyDaysGoalsLists");
        return ninetyDaysGoalsRepository.saveAll(ninetyDaysGoalsLists);

    }


    @Override
    public List<NinetyDaysGoals> updateNinetyDaysGoals(List<NinetyDaysGoals> ninetyDaysGoals) {
        return ninetyDaysGoalsRepository.saveAll(ninetyDaysGoals.stream()
                .peek(i-> i.setStatus("Approved")).collect(Collectors.toList()));
    }

    @Override
    public List<NinetyDaysGoals> getNinetyDaysGoalsByEmpIdAndQuarter(Long empId,String quarter) {
        return ninetyDaysGoalsRepository.findByEmpIdAndQuarter(empId,quarter);
    }
}
