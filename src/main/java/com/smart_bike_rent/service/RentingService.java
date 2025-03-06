package com.smart_bike_rent.service;

import com.smart_bike_rent.dto.RentingDTO;
import com.smart_bike_rent.entity.client.Client;
import com.smart_bike_rent.entity.equipment.Equipment;
import com.smart_bike_rent.entity.renting.Renting;
import com.smart_bike_rent.exception.*;
import com.smart_bike_rent.repositories.ClientRepository;
import com.smart_bike_rent.repositories.EquipmentRepository;
import com.smart_bike_rent.repositories.RentingRepository;
import com.smart_bike_rent.request.CreateRentingRequest;
import com.smart_bike_rent.security.dto.UserDto;
import com.smart_bike_rent.security.service.UserAuthProvider;
import com.smart_bike_rent.security.service.UserServiceNew;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Slf4j
@Service
@RequiredArgsConstructor
public class RentingService {
    private final RentingRepository rentingRepository;
    private final ClientRepository clientRepository;
    private final EquipmentRepository equipmentRepository;
    private final UserServiceNew userService;
    private final UserAuthProvider userAuthProvider;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");


    public void createRenting(CreateRentingRequest createRentingRequest) {
        Optional<Client> optionalClient = clientRepository.findById(createRentingRequest.getIdClient());
        if (optionalClient.isPresent()) {
            Client client = optionalClient.get();
            // trzeba złapać klienta jesli jego id nie istnieje!!!
            for (Long idEquipment : createRentingRequest.getIdEquipment()) {
                Optional<Equipment> optionalEquipment = equipmentRepository.findById(idEquipment);
                if (optionalEquipment.isPresent()) {
                    Equipment equipment = optionalEquipment.get();

                    if (!equipment.isAvailable()) {
                        throw new EquipmentNotExists("Equipment with ID " + idEquipment + " is not available.");
                    }

                    // Mark the equipment as unavailable
                    equipment.setAvailable(false);
                    equipmentRepository.save(equipment); // Update the equipment status in the database

                    //Kreujemy wypozyczenie
                    Renting renting = Renting.builder()
                            .client(client)
                            .equipment(equipment)
                            .dateRenting(LocalDateTime.now())
                            .build();
                    rentingRepository.save(renting);
                } else {
                    throw new EquipmentNotExists("Equipment with id: " + idEquipment + " does not exist");
                }
            }
            }else{
                throw new ClientNotExistsException("Client with ID " + createRentingRequest.getIdClient() + " does not exist.");
            }
        }
        public Renting returnRenting(Long idRenting, Renting updateRenting, String token) {
        Optional<Renting> optionalRenting = rentingRepository.findById(idRenting);
        if (optionalRenting.isPresent()) {
            Renting renting = optionalRenting.get();
                Equipment equipment =optionalRenting.get().getEquipment();
            if (renting.getDateOfReturn() == null) {
                //ustal czas zdania sprzetu
                renting.setDateOfReturn(LocalDateTime.now());
                Long userId = userAuthProvider.extractUserId(token); // Pobierz userId z tokena
                UserDto user = userService.getUserById(userId); // Użycie metody do pobierania UserDto

                // Użyj preferencji naliczania dni wypożyczenia
                boolean useCalendarDay = user.isCalendar();

                //ustal ilość dni wypożyczenia (metoda gettimeDuration)
                renting.setDaysOfRental(getTimeDuration(renting, useCalendarDay));
                //wylicz cenę ostateczną za okres wypożyczenia
                Double calculatePriceOfDuration = equipment.getPriceEquipment() * getTimeDuration(renting, useCalendarDay);
                //zapisz cenę ostateczną dla obiektu
                renting.setPriceOfDuration(calculatePriceOfDuration);
                //zanacz sprzęt dostepny
                equipment.setAvailable(true);
                //zapisz wszystko w bazie
              return   rentingRepository.save(renting);
            }   throw new RentingAlreadyReturnException("Renting have rented");
        }        throw new RentingIsNotExistsException("Unable to find renting with id: "+ idRenting);
    }

    public Long getTimeDuration(Renting renting, boolean calendar) {
        LocalDateTime dateRenting = renting.getDateRenting();
        LocalDateTime dateOfReturn = (renting.getDateOfReturn() != null) ? renting.getDateOfReturn() : LocalDateTime.now();

        if (dateRenting == null) {
            throw new IllegalArgumentException("DateRenting must not be null");
        }

        if (calendar) {
            // Obliczanie czasu jako dni kalendarzowe
            LocalDate start = dateRenting.toLocalDate();
            LocalDate end = dateOfReturn.toLocalDate();
            Period period = Period.between(start, end);
            return (long) period.getDays() + 1;
        } else {
            // Obliczanie czasu jako doby 24-godzinne
            Duration duration = Duration.between(dateRenting, dateOfReturn);
            long seconds = duration.getSeconds();
            double hours = seconds / 3600.0;
            double days = hours / 24.0;
            return (long) Math.ceil(days);
        }
    }

    public List<RentingDTO> listRentings(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        return rentingRepository.findAllByOrderByDateRentingDesc().stream()
                .map(this::mapRentingToDTO)
                .collect(Collectors.toList());
    }
    public List<RentingDTO> findListRecentlyRenting(){
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        return rentingRepository.findAll().stream()
                .filter(renting -> renting.getDateOfReturn() == null)
                .map(this::mapRentingToDTO)
                .collect(Collectors.toList());
    }

    public Optional<RentingDTO> showRentingById(Long id) {
        Optional<Renting> renting = rentingRepository.findById(id);
        // Użyj mapRentingToDTO() do konwersji Renting na RentingDTO
        return renting.map(this::mapRentingToDTO);
    }

    public Double generateDailyRevenueReport(LocalDate date) {
        // Pobierz wszystkie transakcje zakończone w danym dniu
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.plusDays(1).atStartOfDay();

        List<Renting> rentalsForDay = rentingRepository.findByDateOfReturnBetween(startOfDay, endOfDay);
        // Sumowanie przychodów
        return rentalsForDay.stream()
                .mapToDouble(Renting::getPriceOfDuration)
                .sum();
    }
    public List<RentingDTO> findRentingsByIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return Collections.emptyList();
        }
        try {
            List<Renting> rentings = rentingRepository.findByIdRentingsIn(ids);
            return rentings.stream()
                    .map(this::mapRentingToDTO) // lub mapRentingToDTOWithFormattedDates, zależnie od implementacji
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error fetching rentings by IDs: {}", ids, e);
            throw e; // Przerzuć wyjątek na kontroler
        }
    }

    public List<RentingDTO> findRecentlyReturnedRentings() {
        LocalDateTime cutoffTime = LocalDateTime.now().minusMinutes(60);
        List<Renting> rentings = rentingRepository.findByDateOfReturnGreaterThan(cutoffTime);
        return rentings.stream()
                .map(this::mapRentingToDTO)
                .collect(Collectors.toList());
    }

    private RentingDTO mapRentingToDTO(Renting renting) {
        return new RentingDTO(
                renting.getIdRenting(),
                renting.getClient().getFirstName(),
                renting.getClient().getLastName(),
                renting.getClient().getIdentityCard(),
                renting.getClient().getPhoneNumber(),
                formatLocalDateTime(renting.getDateRenting()),
                renting.getEquipment().getNameEquipment(),
                renting.getEquipment().getPriceEquipment(),
                renting.getEquipment().getFrameNumber(),
                formatLocalDateTime(renting.getDateOfReturn()),
                renting.getPriceOfDuration(),
                renting.getDaysOfRental()
        );
    }


    private String formatLocalDateTime(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.format(formatter) : null;
    }
}
