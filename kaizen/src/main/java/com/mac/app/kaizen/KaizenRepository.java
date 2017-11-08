/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.app.kaizen;

import com.mac.app.kaizen.model.TKaizen;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Nidura Prageeth
 */
public interface KaizenRepository extends JpaRepository<TKaizen, Integer>{

    public List<TKaizen> findByEmployee(Integer indexNo);

    public List<TKaizen> findByCompany(int company);

    public List<TKaizen> findByEmployeeAndCompanyAndDescription(int employee, int company, String description);

    @Query(value = "select * from kaizen where year(kaizen.introduce_date) =:year and month(kaizen.introduce_date) =:month and kaizen.company = :company",nativeQuery = true)
    public List<TKaizen> findByDate(@Param("year") String year ,@Param("month") String month,@Param("company") int company);

    
}
