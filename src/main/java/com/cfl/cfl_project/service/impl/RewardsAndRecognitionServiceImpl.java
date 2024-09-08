package com.cfl.cfl_project.service.impl;

import com.cfl.cfl_project.model.Cfl;
import com.cfl.cfl_project.model.RewardsAndRecognition;
import com.cfl.cfl_project.repository.CflRepository;
import com.cfl.cfl_project.repository.RewardsAndRecognitionRepository;
import com.cfl.cfl_project.service.RewardsAndRecognitionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class RewardsAndRecognitionServiceImpl implements RewardsAndRecognitionService {

    @Autowired
    private RewardsAndRecognitionRepository rewardsAndRecognitionRepository;




//
//    @Override
//    public RewardsAndRecognition create(String rewardsPersonName, String messagedPersonName, String message, MultipartFile file) throws IOException {
//        RewardsAndRecognition rewardAndRecognition=new RewardsAndRecognition();
//        rewardAndRecognition.setMessage(message);
//        String name[]=rewardsPersonName.split(" ");
//        String firstName=name[0].substring(0,1).toUpperCase()+name[0].substring(1,name[0].length());
//        String secondName=name[1].substring(0,1).toUpperCase()+name[1].substring(1,name[1].length());
//        rewardAndRecognition.setRewardsPersonName(firstName+" "+secondName);
//        rewardAndRecognition.setMessagedPersonName(messagedPersonName);
//        rewardAndRecognition.setRewardImageName(file.getOriginalFilename());
//        rewardAndRecognition.setRewardImage(file.getBytes());
//        LocalDate date= LocalDate.now();
//        rewardAndRecognition.setDate(date);
//        rewardAndRecognition.setRewardRecognitionType("world-of-thanks");
//        return rewardsAndRecognitionRepository.save(rewardAndRecognition);
//    }



    @Override
    public RewardsAndRecognition create(String rewardsPersonName, String messagedPersonName, String message, MultipartFile file) throws IOException {
        RewardsAndRecognition rewardAndRecognition=new RewardsAndRecognition();
        rewardAndRecognition.setMessage(message);
        String name=rewardsPersonName;
//        String firstName=name[0].substring(0,1).toUpperCase()+name[0].substring(1,name[0].length());
//        String secondName=name[1].substring(0,1).toUpperCase()+name[1].substring(1,name[1].length());
//        rewardAndRecognition.setRewardsPersonName(firstName+" "+secondName);
        rewardAndRecognition.setRewardsPersonName(name);
        rewardAndRecognition.setMessagedPersonName(messagedPersonName);
        rewardAndRecognition.setRewardImageName(file.getOriginalFilename());
        rewardAndRecognition.setRewardImage(file.getBytes());
        LocalDate date= LocalDate.now();
        rewardAndRecognition.setDate(date);
        rewardAndRecognition.setRewardRecognitionType("world-of-thanks");
        return rewardsAndRecognitionRepository.save(rewardAndRecognition);
    }



//    @Override
//    public RewardsAndRecognition getByRewardsPersonName(String rewardsPersonName) {
//
//        List<RewardsAndRecognition>rewardsAndRecognitions=new ArrayList<>();
//        List<RewardsAndRecognition>rewardsAndRecognitionList=rewardsAndRecognitionRepository.findA(rewardsPersonName);
//        for(RewardsAndRecognition i:rewardsAndRecognitionList){
//            Long empId= Long.valueOf(i.getRewardsPersonName().split(" ")[0]);
//            Cfl cfl=cflRepository.findByEmpId(empId);
//            i.setFileData(cfl.getFileData());
//            rewardsAndRecognitions.add(i);
//        }
//        return rewardsAndRecognitions;
//    }

    @Autowired
    private CflRepository cflRepository;

//    @Override
//    public List<RewardsRecognitionDTO> getByRewardsAndRecognitionType(String rewardsAndRecognitionType) {
//        List<RewardsRecognitionDTO> rewardsRecognitionDTOList = new ArrayList<>();
//
//        System.out.println(rewardsAndRecognitionType+"rewardsAndRecognitionType");
//
//        List<RewardsAndRecognition> rewardsAndRecognitionsLists = rewardsAndRecognitionRepository.findByRewardRecognitionType(rewardsAndRecognitionType);
//        System.out.println(String.valueOf(rewardsAndRecognitionsLists));
//
//        for (RewardsAndRecognition rewardAndRecognition : rewardsAndRecognitionsLists) {
//            RewardsRecognitionDTO rewardsRecognitionDTO = new RewardsRecognitionDTO();
//
//            String[] nameParts = rewardAndRecognition.getRewardsPersonName().split("\\s+");
//            if (nameParts.length < 2) {
//                continue;
//            }
//
//            String firstName = nameParts[0].substring(0,1).toUpperCase()+nameParts[0].substring(1, nameParts[0].length());
//            log.info(firstName);
//            String lastName = nameParts[nameParts.length - 1].substring(0,1).toUpperCase()+ nameParts[nameParts.length - 1].substring(0).substring(1,nameParts[1].length());
//            log.info(lastName);
//            Cfl cflName = cflRepository.findByCflFirstNameAndCflLastName(firstName, lastName);
//            log.info(String.valueOf(cflName));
//
//            if (cflName != null) {
//                rewardsRecognitionDTO.setCfl(cflName);
//                rewardsRecognitionDTO.setRewardsAndRecognition(rewardAndRecognition);
//                rewardsRecognitionDTOList.add(rewardsRecognitionDTO);
//            }
//        }
//
//        return rewardsRecognitionDTOList;
//    }


    @Override
    public List<RewardsAndRecognition> getByRewardsAndRecognitionByType(String rewardsAndRecognitionType) {
        List<RewardsAndRecognition>rewardsAndRecognitions=new ArrayList<>();
        List<RewardsAndRecognition>rewardsAndRecognitionList= rewardsAndRecognitionRepository.findByRewardRecognitionType(rewardsAndRecognitionType);
        for(RewardsAndRecognition i:rewardsAndRecognitionList){
            Long empId= Long.valueOf(i.getRewardsPersonName().split(" ")[0]);
            Cfl cfl=cflRepository.findByEmpId(empId);
            i.setFileData(cfl.getFileData());
            rewardsAndRecognitions.add(i);
        }
        return rewardsAndRecognitions;
    }


    @Override
    public RewardsAndRecognition createBravo(String rewardsPersonName, String messagedPersonName, String message, MultipartFile file) throws IOException {
        RewardsAndRecognition rewardAndRecognition=new RewardsAndRecognition();
        rewardAndRecognition.setMessage(message);
        rewardAndRecognition.setRewardsPersonName(rewardsPersonName);
        rewardAndRecognition.setMessagedPersonName(messagedPersonName);
        rewardAndRecognition.setRewardImageName(file.getOriginalFilename());
        rewardAndRecognition.setRewardImage(file.getBytes());
        LocalDate date= LocalDate.now();
        rewardAndRecognition.setDate(date);
        rewardAndRecognition.setRewardRecognitionType("bravo");
        log.info("running");
        return rewardsAndRecognitionRepository.save(rewardAndRecognition);
    }

    @Override
    public List<String> listOfCfl(String year) {
        List<String>cflListInfo=new ArrayList<>();
        List<Cfl> cflList=cflRepository.findAllByYear(year);
        for(Cfl cfl:cflList){
            Long cflId=cfl.getEmpId();
            String cflName=cfl.getCflFirstName();
            String result=String.valueOf(cflId)+" "+cflName;
            cflListInfo.add(result);
        }
        return cflListInfo;
    }
}
