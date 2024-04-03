package com.example.final_dn.Service;

import com.example.final_dn.Model.Question;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Optional;

public interface QuestionService {
    public Iterable<Question> getAllQuestion();

    public Optional<Question> getQuestion(Long id);


    public void deleteQuestion(Long id);

    public Question saveQuestion(Question examListening, String exam) throws IOException;
}
