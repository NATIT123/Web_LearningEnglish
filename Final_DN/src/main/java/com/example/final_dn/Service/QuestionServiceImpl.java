package com.example.final_dn.Service;

import com.example.final_dn.Model.Question;
import com.example.final_dn.Repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;




@Service
public class QuestionServiceImpl implements QuestionService {
    public static String UPLOAD_IMAGES_LISTENING = System.getProperty("user.dir") + "/src/main/resources/static/img/question/Listening";

    public static String UPLOAD_IMAGES_READING = System.getProperty("user.dir") + "/src/main/resources/static/img/question/Reading";

    @Autowired
    private QuestionRepository questionRepository;
    @Override
    public Iterable<Question> getAllQuestion() {
        return questionRepository.findAll();
    }

    @Override
    public Optional<Question> getQuestion(Long id) {
        return questionRepository.findById(id);
    }

    @Override
    public void deleteQuestion(Long id) {
        questionRepository.deleteById(id);
    }

    @Override
    public Question saveQuestion(Question _question,String exam) throws IOException {
        Question question=questionRepository.save(_question);
        Path path=null;
        if (question.getPhotoData() != null) {
            if(exam.equals("LISTENING")){
                String src="/cauHoiBaiNgheId=" + question.getId() + ".png";
               _question.setImage(src);
               path=Paths.get(UPLOAD_IMAGES_LISTENING+src);
                }
            else if(exam.equals("READING")){
                String src="/cauHoiBaiDocId=" + question.getId() + ".png";
                path=Paths.get(UPLOAD_IMAGES_READING+src);
                _question.setImage("/cauHoiBaiDocId=" + question.getId() + ".png");
            }
            Files.write(path,_question.getPhotoData());
        }
        return questionRepository.save(_question) ;
    }

}
