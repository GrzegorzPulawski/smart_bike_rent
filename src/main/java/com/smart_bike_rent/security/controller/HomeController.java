package com.smart_bike_rent.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "Witaj w aplikacji"; // Upewnij się, że plik index.html jest w katalogu static
    }
    @GetMapping("/favicon.ico")
    public void favicon() {
        // Możemy zwrócić pustą odpowiedź lub skomentować ten metodę, aby nie powodowała błędów.
    }
}
