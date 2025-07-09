package com.example.finanzas_back.service;

import com.example.finanzas_back.dto.UsuarioRegistroDto;
import com.example.finanzas_back.dto.UsuarioResponseDto;
import com.example.finanzas_back.exception.DuplicateResourceException;
import com.example.finanzas_back.exception.ResourceNotFoundException;
import com.example.finanzas_back.model.Usuario;
import com.example.finanzas_back.repository.UsuarioRepository;
import com.example.finanzas_back.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy; // Importa Lazy
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, @Lazy PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsernameAndActivo(username, true)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));
        return new CustomUserDetails(usuario);
    }

    public UsuarioResponseDto registrarUsuario(UsuarioRegistroDto registroDto) {
        if (usuarioRepository.existsByUsername(registroDto.getUsername())) {
            throw new DuplicateResourceException("El nombre de usuario ya está en uso");
        }
        if (usuarioRepository.existsByEmail(registroDto.getEmail())) {
            throw new DuplicateResourceException("El email ya está en uso");
        }
        Usuario usuario = new Usuario();
        usuario.setUsername(registroDto.getUsername());
        usuario.setNombre(registroDto.getNombre());
        usuario.setEmail(registroDto.getEmail());
        usuario.setPassword(passwordEncoder.encode(registroDto.getPassword()));
        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        return new UsuarioResponseDto(usuarioGuardado);
    }

    public UsuarioResponseDto obtenerUsuarioPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + id));
        return new UsuarioResponseDto(usuario);
    }

    public UsuarioResponseDto obtenerUsuarioPorUsername(String username) {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado: " + username));
        return new UsuarioResponseDto(usuario);
    }

    public Usuario obtenerUsuarioEntity(String username) {
        return usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado: " + username));
    }

    public UsuarioResponseDto actualizarPerfil(String username, UsuarioRegistroDto actualizacionDto) {
        Usuario usuario = obtenerUsuarioEntity(username);
        if (!usuario.getEmail().equals(actualizacionDto.getEmail()) &&
                usuarioRepository.existsByEmail(actualizacionDto.getEmail())) {
            throw new DuplicateResourceException("El email ya está en uso");
        }
        usuario.setNombre(actualizacionDto.getNombre());
        usuario.setEmail(actualizacionDto.getEmail());
        if (actualizacionDto.getPassword() != null && !actualizacionDto.getPassword().isEmpty()) {
            usuario.setPassword(passwordEncoder.encode(actualizacionDto.getPassword()));
        }
        Usuario usuarioActualizado = usuarioRepository.save(usuario);
        return new UsuarioResponseDto(usuarioActualizado);
    }
}