package com.apap.tugas1.repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.PegawaiModel;

@Repository
public interface PegawaiDb extends JpaRepository<PegawaiModel, Long>{
	List<PegawaiModel> findByInstansi(InstansiModel Instansi);
	
	Optional<PegawaiModel> findByNip(String Nip);

	List<PegawaiModel> findByInstansiOrderByTanggallahirAsc(InstansiModel instansi);
	
	List<PegawaiModel> findByTanggallahirAndTahunmasukAndInstansi(Date tanggallahir, String tahunMasuk, InstansiModel instansi);
}
