package com.apap.tugas1.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.repository.ProvinsiDb;

@Service
@Transactional
public class ProvinsiServiceImpl implements ProvinsiService{
	@Autowired
	private ProvinsiDb provinsiDb;

	@Override
	public Optional<ProvinsiModel> getProvinsiById(Long id) {
		return provinsiDb.findById(id);
	}

	@Override
	public List<ProvinsiModel> getAllProvinsi() {
		return provinsiDb.findAll();
	}

	@Override
	public ProvinsiModel findById(Long id) {
		// TODO Auto-generated method stub
		return provinsiDb.findById(id).get();
	}

	@Override
	public List<ProvinsiModel> findAll() {
		// TODO Auto-generated method stub
		return provinsiDb.findAll();
	}
}
