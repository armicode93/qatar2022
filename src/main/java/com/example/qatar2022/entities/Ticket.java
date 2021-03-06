package com.example.qatar2022.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@Table(name ="ticket")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long codeTicket;

    @ManyToOne
    private Reservation reservation;
}
