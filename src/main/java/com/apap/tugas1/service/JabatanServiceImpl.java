package com.apap.tugas1.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.repository.JabatanDb;

@Service
@Transactional
public class JabatanServiceImpl implements JabatanService{
	@Autowired
	private JabatanDb JabatanDb;
	
	@Override
	public Optional<JabatanModel> getJabatanById(Long id) {
		return JabatanDb.findById(id);
	}

	@Override
	public void addJabatan(JabatanModel jabatan) {
		JabatanDb.save(jabatan);
	}

	@Override
	public void deleteJabatan(JabatanModel jabatan) {
		JabatanDb.delete(jabatan);
	}

	@Override
	public List<JabatanModel> getAll() {
		return JabatanDb.findAll();
	}

	@Override
	public void updateJabatan(JabatanModel jabatan, Long id) {
		JabatanModel jabatan1 = JabatanDb.getOne(id);
		jabatan1.setNama(jabatan.getNama());
		jabatan1.setDeskripsi(jabatan.getDeskripsi());
		jabatan1.setGaji_pokok(jabatan.getGaji_pokok());
		JabatanDb.save(jabatan1);
	}

	@Override
	public JabatanModel findById(Long id) {
		// TODO Auto-generated method stub
		return JabatanDb.findById(id).get();
	}

	@Override
	public List<JabatanModel> findAll() {
		// TODO Auto-generated method stub
		return JabatanDb.findAll();
	}

}