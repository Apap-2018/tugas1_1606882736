package com.apap.tugas1.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "pegawai")
public class PegawaiModel implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Size(max = 255)
	@Column(name = "nip", nullable = false, unique = true)
	private String nip;
	
	@NotNull
	@Size(max = 255)
	@Column(name = "nama", nullable = false)
	private String nama;
	
	@NotNull
	@Size(max = 255)
	@Column(name = "tempat_lahir", nullable = false)
	private String tempatlahir;
	
	@NotNull
	@Column(name = "tanggal_lahir", nullable = false)
	private Date tanggallahir;
	
	@NotNull
	@Size(max = 255)
	@Column(name = "tahun_masuk", nullable = false)
	private String tahunmasuk;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_instansi", referencedColumnName = "id", nullable = false)
	@OnDelete(action = OnDeleteAction.NO_ACTION)
	@JsonIgnore
	private InstansiModel instansi;
	
	@OneToMany(mappedBy = "pegawai", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	private List<JabatanPegawaiModel> listJabatanPegawai;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNip() {
		return nip;
	}

	public void setNip(String nip) {
		this.nip = nip;
	}

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public String getTempatlahir() {
		return tempatlahir;
	}

	public void setTempatlahir(String tempatlahir) {
		this.tempatlahir = tempatlahir;
	}

	public String getTahunmasuk() {
		return tahunmasuk;
	}

	public void setTahunmasuk(String tahunmasuk) {
		this.tahunmasuk = tahunmasuk;
	}

	public InstansiModel getInstansi() {
		return instansi;
	}

	public void setInstansi(InstansiModel instansi) {
		this.instansi = instansi;
	}

	public Date getTanggallahir() {
		return tanggallahir;
	}

	public void setTanggallahir(Date tanggallahir) {
		this.tanggallahir = tanggallahir;
	}

	public List<JabatanPegawaiModel> getListJabatanPegawai() {
		return listJabatanPegawai;
	}

	public void setListJabatanPegawai(List<JabatanPegawaiModel> listJabatanPegawai) {
		this.listJabatanPegawai = listJabatanPegawai;
	}
	
}
