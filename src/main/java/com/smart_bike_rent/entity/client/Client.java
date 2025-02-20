package com.smart_bike_rent.entity.client;

import com.smart_bike_rent.dto.ClientDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import jakarta.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="client_id")
    private Long idClient;

    @Column(name ="first_name")
    private String firstName;
    @Column(name ="last_name")
    private String lastName;
    @Column(name ="identity_card")
    private String identityCard;
    @Column(name ="phone_number")
    private Integer phoneNumber;

 //   @OneToMany(mappedBy = "client")
 //   private List<Renting> rentingList;

    public ClientDTO mapClientToDTO (){return new ClientDTO(idClient, this.firstName, this.lastName, this.identityCard, this.phoneNumber);}
}
