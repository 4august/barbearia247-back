package com.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collection;
import java.util.List;

@Entity(name = "usuario")
@Table(name = "usuario")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Inheritance(strategy = InheritanceType.JOINED)
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuarioID")
    private Long id;
    @JsonIgnore
    private String senha;

    private String username;

    private UsuarioRole role;

    public Usuario( String senha, String usernameEmail, UsuarioRole role) {
        this.senha = new BCryptPasswordEncoder().encode(senha);
        this.username = usernameEmail;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role == UsuarioRole.Admin)
            return List.of(
                    new SimpleGrantedAuthority("ROLE_ADMIN"),
                    new SimpleGrantedAuthority("ROLE_BARBEARIA"),
                    new SimpleGrantedAuthority("ROLE_USER")
            );
        if (this.role == UsuarioRole.Barbearia)
            return List.of(new SimpleGrantedAuthority("ROLE_BARBEARIA"));
        else return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return senha;
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return username;
    }


    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }

    /*@OneToOne(mappedBy = "barbearia", cascade = CascadeType.PERSIST, orphanRemoval = true)
    @JsonIgnoreProperties("barbearia")
    private Endereco endereco;

    @OneToMany(mappedBy = "barbearia", cascade = CascadeType.PERSIST, orphanRemoval = true)
    @JsonIgnoreProperties("barbearia")
    private List<Contato> contatos;

    @OneToMany(mappedBy = "barbearia", cascade = CascadeType.PERSIST, orphanRemoval = true)
    @JsonIgnoreProperties("barbearia")
    private List<Funcionario> funcionarios;

    @OneToMany(mappedBy = "barbearia", cascade = CascadeType.PERSIST, orphanRemoval = true)
    @JsonIgnoreProperties("barbearia")
    private List<Servico> servicos;


    public Usuario(BarbeariaDTO data) {
        this.cnpj = data.cnpj();
        this.email = data.email();
        this.senha = new BCryptPasswordEncoder().encode(data.senha());
        this.nome = data.nome();
        this.logo = data.logo();
        this.banner = data.banner();
    }*/
}