package com.apap.tugas1.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.repository.InstansiDb;

@Service
@Transactional
public class InstansiServiceImpl implements InstansiService{
	@Autowired
	private InstansiDb InstansiDb;

	@Override
	public Optional<InstansiModel> getIntansiById(Long id) {
		return InstansiDb.findById(id);
}

	@Override
	public List<InstansiModel> getAllInstansi() {
		return InstansiDb.findAll();
	}

	@Override
	public InstansiModel findById(Long id) {
		// TODO Auto-generated method stub
		return InstansiDb.findById(id).get();
	}
}
