package com.example.final_dn.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Arrays;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Question")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String numberId;
    private String question;

    private String image;

    @Column(columnDefinition ="TEXT")
    private String script;

    private String res1;
    private String res2;
    private String res3;
    private String res4;
    private String correctRes;

    @Column(columnDefinition = "TEXT",name = "_explain")
    private String explain;

    @Transient
    @JsonIgnore
    private byte[] photoData;

    @ManyToOne
    @JoinColumn(name = "QuestionId")
    private Exam exam;

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", numberId='" + numberId + '\'' +
                ", question='" + question + '\'' +
                ", image='" + image + '\'' +
                ", script='" + script + '\'' +
                ", res1='" + res1 + '\'' +
                ", res2='" + res2 + '\'' +
                ", res3='" + res3 + '\'' +
                ", res4='" + res4 + '\'' +
                ", correctRes='" + correctRes + '\'' +
                ", explain='" + explain + '\'' +
                ", photoData=" + Arrays.toString(photoData) +
                ", exam=" + exam +
                '}';
    }
}
