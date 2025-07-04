package com.example.finanzas_back.service;

import com.example.finanzas_back.dto.BonoDto;
import com.example.finanzas_back.exception.ResourceNotFoundException;
import com.example.finanzas_back.exception.UnauthorizedAccessException;
import com.example.finanzas_back.model.Bono;
import com.example.finanzas_back.model.Usuario;
import com.example.finanzas_back.repository.BonoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BonoService {

    @Autowired
    private BonoRepository bonoRepository;

    @Autowired
    private UsuarioService usuarioService;

    public List<BonoDto> obtenerBonosPorUsuario(String username) {
        Usuario usuario = usuarioService.obtenerUsuarioEntity(username);
        List<Bono> bonos = bonoRepository.findByUsuarioOrderByFechaCreacionDesc(usuario);
        return bonos.stream()
                .map(BonoDto::new)
                .collect(Collectors.toList());
    }

    public BonoDto obtenerBonoPorId(Long id, String username) {
        Usuario usuario = usuarioService.obtenerUsuarioEntity(username);
        Bono bono = bonoRepository.findByIdAndUsuario(id, usuario)
                .orElseThrow(() -> new ResourceNotFoundException("Bono no encontrado con ID: " + id));
        return new BonoDto(bono);
    }

    public BonoDto crearBono(BonoDto bonoDto, String username) {
        Usuario usuario = usuarioService.obtenerUsuarioEntity(username);

        Bono bono = new Bono();
        mapearDtoAEntidad(bonoDto, bono);
        bono.setUsuario(usuario);

        Bono bonoGuardado = bonoRepository.save(bono);
        return new BonoDto(bonoGuardado);
    }

    public BonoDto actualizarBono(Long id, BonoDto bonoDto, String username) {
        Usuario usuario = usuarioService.obtenerUsuarioEntity(username);
        Bono bono = bonoRepository.findByIdAndUsuario(id, usuario)
                .orElseThrow(() -> new ResourceNotFoundException("Bono no encontrado con ID: " + id));

        mapearDtoAEntidad(bonoDto, bono);

        Bono bonoActualizado = bonoRepository.save(bono);
        return new BonoDto(bonoActualizado);
    }

    public void eliminarBono(Long id, String username) {
        Usuario usuario = usuarioService.obtenerUsuarioEntity(username);
        Bono bono = bonoRepository.findByIdAndUsuario(id, usuario)
                .orElseThrow(() -> new ResourceNotFoundException("Bono no encontrado con ID: " + id));

        bonoRepository.delete(bono);
    }

    public ResumenBonosDto obtenerResumenBonos(String username) {
        Usuario usuario = usuarioService.obtenerUsuarioEntity(username);

        long totalBonos = bonoRepository.countByUsuario(usuario);
        Double totalValorNominal = bonoRepository.sumValorNominalByUsuario(usuario);
        Double promedioTasa = bonoRepository.avgTasaInteresByUsuario(usuario);

        return new ResumenBonosDto(
                totalBonos,
                totalValorNominal != null ? totalValorNominal : 0.0,
                promedioTasa != null ? promedioTasa : 0.0
        );
    }

    private void mapearDtoAEntidad(BonoDto dto, Bono entidad) {
        entidad.setNombre(dto.getNombre());
        entidad.setValorNominal(dto.getValorNominal());
        entidad.setTasaInteres(dto.getTasaInteres());
        entidad.setTipoTasa(dto.getTipoTasa());
        entidad.setCapitalizacion(dto.getCapitalizacion());
        entidad.setPlazoTotal(dto.getPlazoTotal());
        entidad.setFrecuenciaPago(dto.getFrecuenciaPago());
        entidad.setMoneda(dto.getMoneda());
        entidad.setPlazoGraciaTotal(dto.getPlazoGraciaTotal());
        entidad.setPlazoGraciaParcial(dto.getPlazoGraciaParcial());
        entidad.setFechaEmision(dto.getFechaEmision());
    }

    public static class ResumenBonosDto {
        private long totalBonos;
        private double totalValorNominal;
        private double promedioTasa;

        public ResumenBonosDto(long totalBonos, double totalValorNominal, double promedioTasa) {
            this.totalBonos = totalBonos;
            this.totalValorNominal = totalValorNominal;
            this.promedioTasa = promedioTasa;
        }


        public long getTotalBonos() {
            return totalBonos;
        }

        public void setTotalBonos(long totalBonos) {
            this.totalBonos = totalBonos;
        }

        public double getTotalValorNominal() {
            return totalValorNominal;
        }

        public void setTotalValorNominal(double totalValorNominal) {
            this.totalValorNominal = totalValorNominal;
        }

        public double getPromedioTasa() {
            return promedioTasa;
        }

        public void setPromedioTasa(double promedioTasa) {
            this.promedioTasa = promedioTasa;
        }
    }
}