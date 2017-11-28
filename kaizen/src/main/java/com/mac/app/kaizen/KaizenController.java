/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.app.kaizen;

import com.mac.app.document.model.Document;
import com.mac.app.kaizen.model.Mail;

import com.mac.app.kaizen.model.TKaizen;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Nidura Prageeth
 */
@RestController
@CrossOrigin
@RequestMapping("/api/kaizen")
public class KaizenController {

    @Autowired
    private KaizenService kaizenService;

    @RequestMapping(value = "/{company}", method = RequestMethod.GET)
    public List<TKaizen> allKaizen(@PathVariable int company) {
        return kaizenService.findByCompany(company);
    }

    @RequestMapping(value = "/department-kaizen/{indexNo}", method = RequestMethod.GET)
    public List<TKaizen> getKaizenByDepartment(@PathVariable Integer indexNo) {
        return kaizenService.getKaizenByDepartment(indexNo);
    }

    @RequestMapping(value = "/month-kaizen/{year}/{month}/{company}", method = RequestMethod.GET)
    public List<TKaizen> getKaizenByMonth(@PathVariable String year, @PathVariable String month, @PathVariable int company) {
        return kaizenService.getKaizenByMonth(year, month, company);
    }
//
//    @RequestMapping(value = "/department-month-kaizen/month/department/{year}/{month}/{department}/{company}", method = RequestMethod.GET)
//    public List<TKaizen> getKaizenByDepartmentAndMonth(@PathVariable String year, @PathVariable String month, @PathVariable int company,@PathVariable Integer department) {
//        return kaizenService.getKaizenByDepartmentAndMonth(year,month,company,department);
//    }

    @RequestMapping(value = "/employee-kaizen/{epfNo}", method = RequestMethod.GET)
    public List<TKaizen> getKaizenByEmployee(@PathVariable String epfNo) {
        return kaizenService.getKaizenByEmployee(epfNo);
    }

    @RequestMapping(value = "/save-kaizen", method = RequestMethod.POST)
    public TKaizen saveKaizen(@RequestBody TKaizen kaizen) {
        return kaizenService.saveKazen(kaizen);
    }

    @RequestMapping(value = "/delete-kaizen/{indexNo}", method = RequestMethod.POST)
    public void deleteKaizen(@PathVariable Integer indexNo, @RequestBody Mail mail) {
        kaizenService.deleteKaizen(indexNo, mail);
    }

    @RequestMapping(value = "/update-committee-kaizen", method = RequestMethod.POST)
    public TKaizen updateByCommittee(@RequestBody TKaizen kaizen) {
        return kaizenService.kaizenUpdateByCommittee(kaizen);
    }

    // send appreciation mail and kaizen save
    @RequestMapping(value = "/send-mail", method = RequestMethod.POST)
    public TKaizen sendEmail(@RequestBody Mail mail) {
        TKaizen kaisenMail = kaizenService.kaizenUpdateByManager(mail);
        return kaisenMail;
    }

    // send appreciation mail
    @RequestMapping(value = "/send-suggestion-mail/{indexNo}", method = RequestMethod.POST)
    public TKaizen sendSuggestionEmail(@RequestBody Mail mail, @PathVariable("indexNo") Integer indexNo) {
        return kaizenService.updateKaizenByIndex(mail, indexNo);
    }

    // send appreciation mail
    @RequestMapping(value = "/send-appreciation-mail/{indexNo}", method = RequestMethod.POST)
    public TKaizen sendAppreciationEmail(@RequestBody Mail mail, @PathVariable("indexNo") Integer indexNo) {
        return kaizenService.Appreciation(mail, indexNo);
    }
    
    @RequestMapping(value = "/defult-appreciation/{indexNo}", method = RequestMethod.GET)
    public void defultAppreciationEmail(@PathVariable("indexNo") Integer indexNo) {
        kaizenService.defultAppreciation(indexNo);
    }
    
}
