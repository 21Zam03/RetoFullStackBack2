package com.zam.prueba.repository;

import com.zam.prueba.entity.EmployerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmployerRepository extends JpaRepository<EmployerEntity, Long> {
    @Query(value = """
        SELECT 
            e.employer_id, 
            e.employer_email, 
            e.employer_name, 
            e.employer_phone_number,
            a.area_name
        FROM 
            employer AS e
        INNER JOIN 
            area AS a
        ON 
            e.area_id = a.area_id
        WHERE 
            (:filterParameter IS NULL 
            OR e.employer_email LIKE CONCAT('%', :filterParameter, '%')
            OR e.employer_name LIKE CONCAT('%', :filterParameter, '%')
            OR a.area_name LIKE CONCAT('%', :filterParameter, '%'))
        """,
            countQuery = """
        SELECT 
            COUNT(*)
        FROM 
            employer AS e
        INNER JOIN 
            area AS a
        ON 
            e.area_id = a.area_id
        WHERE 
            (:filterParameter IS NULL 
            OR e.employer_email LIKE CONCAT('%', :filterParameter, '%')
            OR e.employer_name LIKE CONCAT('%', :filterParameter, '%')
            OR a.area_name LIKE CONCAT('%', :filterParameter, '%'))
        """,
            nativeQuery = true)
    public Page<Object[]> searchEmployees(@Param("filterParameter") String filterParameter, Pageable pageable,
                                          String sortField, String sortDirection);
    boolean existsByEmployerEmail(String employerEmail);
    boolean existsByEmployerEmailAndEmployerIdNot(String employerEmail, Long employerId);

}
