package com.soro.esop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.soro.esop.entiry.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {    
}
