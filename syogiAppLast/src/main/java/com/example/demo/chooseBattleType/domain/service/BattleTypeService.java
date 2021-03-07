package com.example.demo.chooseBattleType.domain.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.chooseBattleType.domain.model.BattleType;
import com.example.demo.chooseBattleType.domain.repository.BattleTypeDao;

@Service
public class BattleTypeService{

	@Autowired
	BattleTypeDao dao;

	public List<BattleType> selectAll(){

		List<Map<String, Object>> list = dao.selectAll();

		List<BattleType> battleTypeList = new ArrayList<BattleType>();

		for(Map<String, Object> map : list) {

			BattleType battleType = new BattleType();

			battleType.setId((int)map.get("id"));
			battleType.setName((String)map.get("name"));

			battleTypeList.add(battleType);
		};

		return battleTypeList;
	}
}