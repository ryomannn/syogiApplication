package com.example.demo.chooseBattleType.domain.repository;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface BattleTypeDao{

	public List<Map<String, Object>> selectAll()throws DataAccessException;
}