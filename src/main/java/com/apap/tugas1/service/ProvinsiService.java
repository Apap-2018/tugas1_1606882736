package com.apap.tugas1.service;

import java.util.List;
import java.util.Optional;

import com.apap.tugas1.model.ProvinsiModel;

public interface ProvinsiService {
	Optional<ProvinsiModel> getProvinsiById(Long id);

	List<ProvinsiModel> getAllProvinsi();

	ProvinsiModel findById(Long id);

	List<ProvinsiModel> findAll();
}
