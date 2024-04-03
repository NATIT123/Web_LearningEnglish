package com.example.final_dn.Service;

import com.example.final_dn.Model.Category;
import com.example.final_dn.Model.Exam;
import com.example.final_dn.Model.Question;
import com.example.final_dn.Repository.ExamRepository;
import com.example.final_dn.Utils.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class ExamServiceImpl implements ExamService {

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private ExcelUtil excelUtil;

    @Override
    public Iterable<Exam> getAllExam() {
       return examRepository.findAll();
    }

    @Override
    public Optional<Exam> getExam(Long id) {
        return examRepository.findById(id);
    }

    @Override
    public void deleteExam(Long id) {
        examRepository.deleteById(id);
    }

    @Override
    public Exam saveExam(Exam exam) {
        List<Question> listQuestion=new ArrayList<>();
        Exam exam1=examRepository.save(exam);
        try {
            for (Question cauHoi : excelUtil
                    .getListCauHoiFromFileExcel(exam.getFileExcel().getInputStream())) {
                cauHoi.setExam(exam1);
                System.out.println(cauHoi.toString());
                listQuestion.add(questionService.saveQuestion(cauHoi, String.valueOf(exam.getCategory())));
            }
            exam1.setListQuestion(listQuestion);
            return exam1;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Exam> findByCategory(Category category) {
        return examRepository.findByCategory(category);
    }

    @Override
    public List<Exam> _findByPartAndLevel(String part, String level) {
        if(part.isEmpty()&&level.isEmpty()){
            return examRepository.findAll();
        }
        if(!part.isEmpty()&&!level.isEmpty()){
            return examRepository.findByPartAndLevel(part,level);
        }
        if(part.isEmpty()){
            return examRepository.findByLevel(level);
        }
        else{
            return examRepository.findByPart(part);
        }
    }

    @Override
    public List<Exam> findByPart(String part) {
        String _part=part.substring(6,7);
        return examRepository.findByPart(_part);
    }

    @Override
    public List<Exam> findByLevel(String level) {
        return examRepository.findByLevel(level);
    }

    @Override
    public List<Exam> findAllField(String search) {
        if(search.isEmpty()){
            return examRepository.findAll();
        }
        return examRepository.findAllField(search);
    }
}
