package com.example.finanzas_back.controller;

import com.example.finanzas_back.dto.BonoDto;
import com.example.finanzas_back.service.BonoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bonos")
public class BonoController {

    @Autowired
    private BonoService bonoService;

    @GetMapping
    public ResponseEntity<List<BonoDto>> obtenerBonos(Authentication authentication) {
        String username = authentication.getName();
        List<BonoDto> bonos = bonoService.obtenerBonosPorUsuario(username);
        return ResponseEntity.ok(bonos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerBonoPorId(@PathVariable Long id, Authentication authentication) {
        try {
            String username = authentication.getName();
            BonoDto bono = bonoService.obtenerBonoPorId(id, username);
            return ResponseEntity.ok(bono);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @PostMapping
    public ResponseEntity<?> crearBono(@Valid @RequestBody BonoDto bonoDto, Authentication authentication) {
        try {
            String username = authentication.getName();
            BonoDto bonoCreado = bonoService.crearBono(bonoDto, username);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Bono creado exitosamente");
            response.put("bono", bonoCreado);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarBono(@PathVariable Long id,
                                            @Valid @RequestBody BonoDto bonoDto,
                                            Authentication authentication) {
        try {
            String username = authentication.getName();
            BonoDto bonoActualizado = bonoService.actualizarBono(id, bonoDto, username);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Bono actualizado exitosamente");
            response.put("bono", bonoActualizado);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarBono(@PathVariable Long id, Authentication authentication) {
        try {
            String username = authentication.getName();
            bonoService.eliminarBono(id, username);

            Map<String, String> response = new HashMap<>();
            response.put("message", "Bono eliminado exitosamente");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/resumen")
    public ResponseEntity<?> obtenerResumenBonos(Authentication authentication) {
        try {
            String username = authentication.getName();
            BonoService.ResumenBonosDto resumen = bonoService.obtenerResumenBonos(username);
            return ResponseEntity.ok(resumen);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
}
