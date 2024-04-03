package com.example.final_dn.Admin;

import com.example.final_dn.Model.Grammar;
import com.example.final_dn.Service.GrammarService;
import com.example.final_dn.Service.HtmlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class GrammarController {


    @Autowired
    private GrammarService grammarService;

    @Autowired
    private HtmlService htmlService;

    public static String UPLOAD_GRAMMAR = System.getProperty("user.dir") + "/src/main/resources/static/img/grammar";

    @PostMapping("/admin/grammar/add")
    public String processAdd(@RequestParam("name") String name, @RequestParam("image") MultipartFile image,
                             @RequestParam("content") String content, RedirectAttributes redirectAttributes){
        Grammar grammar=new Grammar();
        System.out.println(content);
        grammar.setName(name);
        grammar.setContentMarkDown(content);
        grammar.setContentHTML(htmlService.markdownToHtml(content));
        uploadFile(image,UPLOAD_GRAMMAR,redirectAttributes);
        grammar.setImage(image.getOriginalFilename());
        Grammar currentGrammar=grammarService.saveGrammar(grammar);
        if(currentGrammar==null){
            redirectAttributes.addFlashAttribute("message","Add Failed");
            return "redirect:/admin/grammar/?error";
        }
        redirectAttributes.addFlashAttribute("message","Add Successfully");
        return "redirect:/admin/grammar/?success";
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
            return "redirect:/admin/grammar?error";

        }
        return "";
    }
}
