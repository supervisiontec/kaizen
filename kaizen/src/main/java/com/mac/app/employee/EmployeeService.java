/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.app.employee;

import com.mac.app.employee.model.Department;
import com.mac.app.employee.model.Employee;
import com.mac.app.kaizen.model.Mail;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Nidura Prageeth
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private JavaMailSender mailSender;

    private String EMPLOYEE_TYPE = "manager";
    private String EMPLOYEE_TYPE2 = "committee";

    public List<Employee> findByCompany(int company) {
        List<Employee> newList = new ArrayList<>();
        List<Employee> employees = employeeRepository.findByCompany(company);
        for (Employee employee : employees) {
            if (!employee.getType().equals("admin")) {
                newList.add(employee);
            }
        }
        return newList;
    }

    public Employee saveEmployee(Employee employee) {
        if (employee.getIndexNo() != null) {
            Employee findOne = employeeRepository.findOne(employee.getIndexNo());
            return employeeRepository.save(employee);
        } else {
            Employee user1 = employeeRepository.findByNameAndEpfNo(employee.getName(), employee.getEpfNo());
            if (user1 != null) {
                return null;
            } else {
                return employeeRepository.save(employee);
            }
        }
    }

    public void deleteEmployee(Integer indexNo) {
        employeeRepository.delete(indexNo);
    }

    // department methods
    public List<Department> findDepartmentByCompany(int company) {
        return departmentRepository.findByCompany(company);
    }

    public void deleteDepartment(Integer indexNo) {
        departmentRepository.delete(indexNo);
    }

    public Department saveDepartment(Department department) {
        return departmentRepository.save(department);
    }

    public void sendMail(Mail mail,int company) {
         System.out.println("start manager mail");
//            System.out.println(mail.getSubject());
//            System.out.println(mail.getMessage());
        //send email to all managers
        List<Employee> managers = employeeRepository.findByTypeAndCompany(EMPLOYEE_TYPE,company);
        for (Employee manager : managers) {
//            System.out.println(manager.getName() + "  " + manager.getEmail() +"  "+ manager.getType());
            if (manager.getEmail() != null) {
                try {
                    MimeMessagePreparator messagePreparator = mimeMessage -> {
                        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
                        messageHelper.setFrom("kaizencommittee1@gmail.com");
                        messageHelper.setTo(manager.getEmail());
//                        messageHelper.setTo("niduraprageeth@gmail.com");
                        messageHelper.setSubject(mail.getSubject());
                        messageHelper.setText(mail.getMessage());
                    };
                    mailSender.send(messagePreparator);
                } catch (MailException e) {
                    System.out.println(e);
                }
            }
        }

//        List<Employee> committeees = employeeRepository.findByTypeAndCompany(EMPLOYEE_TYPE2,company);
//        for (Employee committeee : committeees) {
//            if (committeee.getEmail() != null) {
//                try {
//                    MimeMessagePreparator messagePreparator = mimeMessage -> {
//                        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
//                        messageHelper.setFrom("kaizencommittee1@gmail.com");
//                        messageHelper.setTo(committeee.getEmail());
////                        messageHelper.setTo("niduraprageeth@gmail.com");
//                        messageHelper.setSubject("Kaizen Committee reminding mail");
//                        messageHelper.setText("Hi All,\n\n Please note that last month kaizens are now ready for the kaizen committee review,Appreciate\n your assistance in evaluating kaizens at Linea Aqua.\n\n Thanks,\n\n Kaizen admin");
//                    };
//                    mailSender.send(messagePreparator);
//                } catch (MailException e) {
//                    System.out.println(e);
//                }
//            }
//        }
    }

    public void sendMailCommittee(Mail mail,int company) {
        System.out.println("start committe mail");
        //send email to all committee
        List<Employee> committeees = employeeRepository.findByTypeAndCompany(EMPLOYEE_TYPE2,company);
        for (Employee committeee : committeees) {
//            System.out.println(committeee.getName() + "  " + committeee.getEmail() +"  "+ committeee.getType());
            if (committeee.getEmail() != null) {
                try {
                    MimeMessagePreparator messagePreparator = mimeMessage -> {
                        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
                        messageHelper.setFrom("kaizencommittee1@gmail.com");
                        messageHelper.setTo(committeee.getEmail());
//                        messageHelper.setTo("niduraprageeth@gmail.com");
                        messageHelper.setSubject(mail.getSubject());
                        messageHelper.setText(mail.getMessage());
                    };
                    mailSender.send(messagePreparator);
                } catch (MailException e) {
                    System.out.println(e);
                }
            }
        }
    }

}
