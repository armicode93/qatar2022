package com.example.qatar2022.entities;

import javax.persistence.*;
import lombok.*;

@Entity
@Data
@Table(name = "ticket")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long codeTicket;

  @Lob private byte[] barCode;

  @ManyToOne
  @JoinColumn(name = "reservation_code_reservation")
  private Reservation reservation;
}
