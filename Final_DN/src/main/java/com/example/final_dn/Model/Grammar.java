package com.example.final_dn.Model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "grammar")
public class Grammar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String image;

    @Column(columnDefinition = "TEXT",name="content_HTML")
    private String contentHTML;

    @Column(columnDefinition = "TEXT",name="content_MarkDown")
    private String contentMarkDown;
}
