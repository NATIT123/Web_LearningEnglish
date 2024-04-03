package com.example.final_dn.Service;

import com.example.final_dn.Model.Category;
import com.example.final_dn.Model.Exam;

import java.util.List;
import java.util.Optional;

public interface ExamService {
    public Iterable<Exam> getAllExam();

    public Optional<Exam> getExam(Long id);


    public void deleteExam(Long id);

    public Exam saveExam(Exam examListening);

    public List<Exam> findByCategory(Category category);

    public List<Exam> _findByPartAndLevel(String part,String level);

    public List<Exam> findByPart(String part);

    public List<Exam> findByLevel(String level);
    public List<Exam> findAllField(String search);
}
