package com.movieflix.Respository;

import com.movieflix.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRespository extends JpaRepository<Movie, Integer> {


}
