package com.example.stratifytask.models;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor @ToString
@Table(name = "files")
public class FileInfo {
    @Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private LocalDate creationDate;
    private long size;
    private LocalDate uploadDate;

    public FileInfo(String name, LocalDate creationDate, long size, LocalDate uploadDate) {
        this.name = name;
        this.creationDate = creationDate;
        this.size = size;
        this.uploadDate = uploadDate;
    }
}
