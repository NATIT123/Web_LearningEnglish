package com.example.final_dn.Repository;

import com.example.final_dn.Model.Category;
import com.example.final_dn.Model.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExamRepository extends JpaRepository<Exam,Long> {

    public List<Exam> findByCategory(Category category);

    public List<Exam>findByPartAndLevel(String part,String level);

    public List<Exam>findByPart(String part);

    public List<Exam>findByLevel(String level);

    @Query("SELECT DISTINCT e FROM Exam e WHERE e.name LIKE concat('%',:search,'%')  OR e.level LIKE concat('%',:search,'%') OR e.part LIKE concat('%',:search,'%')")
    public List<Exam>findAllField(String search);

}
