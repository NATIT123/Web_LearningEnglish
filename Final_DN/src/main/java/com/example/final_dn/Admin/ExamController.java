package com.example.final_dn.Admin;


import com.example.final_dn.Model.Category;
import com.example.final_dn.Model.Exam;
import com.example.final_dn.Service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class ExamController {

    @Autowired
    private ExamService examService;


    public static String UPLOAD_AUDIO = System.getProperty("user.dir") + "/src/main/resources/static/audio";
    public static String UPLOAD_IMAGES_LISTENING = System.getProperty("user.dir") + "/src/main/resources/static/img/exam/Listening";

    @PostMapping("/admin/examListening/add")
    public String processAddExam(@RequestParam("name") String name, @RequestParam("part") String part,
                                 @RequestParam("level") String level, @RequestParam("image") MultipartFile image,
                                 @RequestParam("audio") MultipartFile audio, @RequestParam("excel") MultipartFile excel,RedirectAttributes redirectAttributes
    ) {
        Exam exam = new Exam();
        exam.setCategory(Category.LISTENING);
        exam.setLevel(level);
        exam.setPart(part);
        exam.setName(name);
        exam.setFileExcel(excel);
        uploadFile(image,UPLOAD_IMAGES_LISTENING,redirectAttributes);
        uploadFile(audio,UPLOAD_AUDIO,redirectAttributes);
        exam.setAudio(audio.getOriginalFilename());
        exam.setImage(image.getOriginalFilename());
        Exam currentExam= examService.saveExam(exam);
        if(currentExam==null){
            return "redirect:/admin/listening?error";
        }
        return "redirect:/admin/listening?success";
    }

    public String uploadFile(MultipartFile file, String upload, RedirectAttributes redirectAttributes) {
        try {
            StringBuilder fileNames = new StringBuilder();
            Path fileNameAndPath = Paths.get(upload, file.getOriginalFilename());
            fileNames.append(file.getOriginalFilename());
            Files.write(fileNameAndPath, file.getBytes());
        } catch (IOException e) {
            // Handle the IOException here
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Failed to upload file: " + e.getMessage());
            return "redirect:/admin/listening?error";

        }
        return "";
    }

    @PostMapping("/admin/examListening/search")
    public String processSearch(Model model,@RequestParam("doKhoSearch") String level,
                                @RequestParam("partSearch") String part,RedirectAttributes redirectAttributes
                                ){
        model.addAttribute("listListening",examService._findByPartAndLevel(part,level));
        redirectAttributes.addFlashAttribute("message","Search Successfully");
        return "redirect:/admin/listening?success";
    }

    @PostMapping("/admin/examListening/searchAll")
    public String processSearchAll(Model model,@RequestParam("searchAll") String search,
                                   RedirectAttributes redirectAttributes){
        model.addAttribute("listListening",examService.findAllField(search));
        redirectAttributes.addFlashAttribute("message","Search Successfully");
        return "redirect:/admin/listening?success";
    }

    @PostMapping("/admin/examListening/delete/{id}")
    public String processDelete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes){
        examService.deleteExam(id);
        redirectAttributes.addFlashAttribute("message","Search Successfully");
        return "redirect:/admin/listening?success";
    }
}

