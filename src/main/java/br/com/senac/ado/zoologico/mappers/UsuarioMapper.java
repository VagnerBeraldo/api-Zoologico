package br.com.senac.ado.zoologico.mappers;

import br.com.senac.ado.zoologico.dto.Usuario.UsuarioDTO;
import br.com.senac.ado.zoologico.entity.Usuario;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    Usuario toEntity(UsuarioDTO dto);
    UsuarioDTO toDto(Usuario entity);
    List<UsuarioDTO> toList(List<Usuario> entity);
}
