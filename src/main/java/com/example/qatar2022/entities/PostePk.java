package com.example.qatar2022.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class PostePk implements Serializable {

    @Column(name="joueur_id")
    private Long joueur_id;

    @Column(name="partie_id")
    private Long partie_id;


}
