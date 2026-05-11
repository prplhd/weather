package ru.prplhd.weather.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/locations")
public class LocationSearchController {

    @GetMapping("/search")
    public String searchLocations() {
        return "search-results";
    }
}
