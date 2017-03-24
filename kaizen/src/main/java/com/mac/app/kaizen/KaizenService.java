/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.app.kaizen;

import com.mac.app.document.DocumentRepository;
import com.mac.app.document.model.Document;
import com.mac.app.employee.EmployeeRepository;
import com.mac.app.employee.model.Employee;
import com.mac.app.kaizen.model.TKaizen;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Nidura Prageeth
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class KaizenService {

    @Autowired
    private KaizenRepository kaizenRepository;

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    private static final String KAIZEN_PENDING = "PENDING";
    private static final String MANAGER_VIEW = "MANAGER_VIEW";
    private static final String COMMITTEE_VIEW = "COMMITTEE_VIEW";
    private static final String EMPLOYEE_COMPLETE = "EMPLOYEE_COMPLETE";
    private static final String MANAGER_COMPLETE = "MANAGER_COMPLETE";

    public List<TKaizen> allKaisen() {
        return kaizenRepository.findAll();
    }

    public List<TKaizen> getKaizenByDepartment(Integer index) {
        List<TKaizen> kaizen = new ArrayList<>();

        List<Employee> empList = employeeRepository.findByDepartmentIndexNo(index);

        for (Employee employee : empList) {
            kaizen = kaizenRepository.findByEmployee(employee.getIndexNo());
        }
        return kaizen;
    }

    public TKaizen saveKazen(TKaizen kaizen) {
        kaizen.setIntroduceDate(new Date());
        kaizen.setReviewStatus(KAIZEN_PENDING);
        kaizen.setEmployeeComplete(EMPLOYEE_COMPLETE);

        return kaizenRepository.save(kaizen);

        //save image into database
//        File file = new File("D:\\mypic.jpg");
//        byte[] bFile = new byte[(int) file.length()];
//
//        try {
//            FileInputStream fileInputStream = new FileInputStream(file);
//            //convert file into array of bytes
//            fileInputStream.read(bFile);
//            fileInputStream.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        Document document = new Document();
//        document.setPath(bFile);
//        document.setKaizen(kaizen1.getIndexNo());
//
//        documentRepository.save(document);
//
//        return kaizen1;
    }

    public TKaizen kaizenUpdateByManager(TKaizen kaizen) {
        TKaizen kaizen1 = kaizenRepository.findOne(kaizen.getIndexNo());

        kaizen1.setManagerCost(kaizen.getManagerCost());
        kaizen1.setManagerCreativity(kaizen.getManagerCreativity());
        kaizen1.setManagerQuality(kaizen.getManagerQuality());
        kaizen1.setManagerSafety(kaizen.getManagerSafety());
        kaizen1.setManagerUtilization(kaizen.getManagerUtilization());
        kaizen1.setReviewStatus(MANAGER_VIEW);
        kaizen1.setManagerComplete(MANAGER_COMPLETE);
        return kaizenRepository.save(kaizen1);
    }

    public TKaizen kaizenUpdateByCommittee(TKaizen kaizen) {
        TKaizen kaizen1 = kaizenRepository.findOne(kaizen.getIndexNo());

        kaizen1.setCommitteeCost(kaizen.getCommitteeCost());
        kaizen1.setCommitteeCreativity(kaizen.getCommitteeCreativity());
        kaizen1.setCommitteeQuality(kaizen.getCommitteeQuality());
        kaizen1.setCommitteeSafety(kaizen.getCommitteeSafety());
        kaizen1.setCommitteeUtilization(kaizen.getCommitteeUtilization());
        kaizen1.setReviewStatus(COMMITTEE_VIEW);
        return kaizenRepository.save(kaizen1);
    }
}
