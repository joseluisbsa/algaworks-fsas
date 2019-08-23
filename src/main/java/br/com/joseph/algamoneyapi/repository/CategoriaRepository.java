package br.com.joseph.algamoneyapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.joseph.algamoneyapi.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long >{
	
	Optional <Categoria> findById(Long id);

}
