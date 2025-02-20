package com.smart_bike_rent.entity.renting;

import com.smart_bike_rent.entity.client.Client;
import com.smart_bike_rent.entity.equipment.Equipment;
import com.smart_bike_rent.security.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rental")
public class Renting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "renting_id")
    private Long idRenting;

    @CreationTimestamp
    @Column(name ="date_renting")
    private LocalDateTime dateRenting;
    @Column (name = "date_return")
    private LocalDateTime dateOfReturn;
    @Column(name = "price_of_duration")
    private Double priceOfDuration;
    @Column(name ="days_of_rental")
    private Long daysOfRental;

    @ManyToOne
    @JoinColumn(name = "equipments_equipment_id")
    private Equipment equipment;
    @ManyToOne
    @JoinColumn(name = "clients_client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "upuser_user_id")
    private User user;

}
