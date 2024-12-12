package com.es.crmInmobiliaria.controller;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/propiedades")
public class PropiedadController {
    @GetMapping("/")
    public String getAll() {
        return "PropiedadController";
    }

    @GetMapping("/{id}")
    public String getById(@RequestParam String id) {
        return "PropiedadController";
    }

    @PostMapping("/")
    public String create() {
        return "PropiedadController";
    }

    @PutMapping("/{id}")
    public String update(@RequestParam String id) {
        return "PropiedadController";
    }

    @DeleteMapping("/{id}")
    public String delete(@RequestParam String id) {
        return "PropiedadController";
    }
}
