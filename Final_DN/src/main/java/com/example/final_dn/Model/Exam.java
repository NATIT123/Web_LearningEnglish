package com.example.final_dn.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "exam")
public class Exam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    private String level; // 1: dễ, 2:trung bình, 3: khó


    private String image; // ảnh bài nghe nếu có

    private String part; // phần 1,2,3,4 ?


    private String audio; // audio bài nghe

    @Enumerated(EnumType.STRING)
    private Category category;

    @Transient
    private MultipartFile fileExcel;

    @Column(columnDefinition = "TEXT")
    private String script; // chi tiết bài nghe

    @OneToMany(mappedBy = "exam", cascade = CascadeType.ALL)
    List<Question> listQuestion;
}
