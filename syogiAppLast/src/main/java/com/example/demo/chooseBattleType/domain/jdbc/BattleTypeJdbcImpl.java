package com.example.demo.chooseBattleType.domain.jdbc;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.chooseBattleType.domain.repository.BattleTypeDao;


@Repository
public class BattleTypeJdbcImpl implements BattleTypeDao{

	@Autowired
	JdbcTemplate jdbc;

	@Override
	public List<Map<String, Object>> selectAll() throws DataAccessException {

		String selectAllSQL = "select * from syogi.battleTypes";

		List<Map<String, Object>> list = jdbc.queryForList(selectAllSQL);

		return list;
	}

}