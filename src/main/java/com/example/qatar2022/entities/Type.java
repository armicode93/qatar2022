package com.example.qatar2022.entities;


import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name="type")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Type {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idType;

    private String typeBut;

    /*

    @OneToMany(mappedBy = "type")
    private List <Goal> goal = new ArrayList<>();

     */



}
