package com.santosjhony.demo.park.api.repository;

import com.santosjhony.demo.park.api.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);
    @Query("SELECT u.role FROM Usuario u WHERE u.username LIKE :username")
    Usuario.Role findRoleByUsername(@Param("username") String username);

}
