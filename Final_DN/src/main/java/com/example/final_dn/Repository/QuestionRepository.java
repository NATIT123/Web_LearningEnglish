package com.example.final_dn.Repository;

import com.example.final_dn.Model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question,Long> {
}
