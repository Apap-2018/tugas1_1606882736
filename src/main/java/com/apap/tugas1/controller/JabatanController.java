package com.apap.tugas1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.JabatanPegawaiModel;
import com.apap.tugas1.service.JabatanPegawaiService;
import com.apap.tugas1.service.JabatanService;

@Controller
public class JabatanController {	
	@Autowired
	private JabatanService jabatanService;
	
	@Autowired
	private JabatanPegawaiService jabatanPegawaiService;
	
	@RequestMapping("/jabatan/tambah")
	private String addJabatan(Model model) {
		JabatanModel jabatan = new JabatanModel();
		model.addAttribute("message", "No");
		model.addAttribute("jabatan", jabatan);
		return "add-jabatan";
	}
	
	@RequestMapping(value="/jabatan/tambah", method=RequestMethod.POST)
	private String addJabatanSubmit(@ModelAttribute JabatanModel jabatan, Model model) {
		jabatanService.addJabatan(jabatan);
		model.addAttribute("jabatan", jabatan);
		model.addAttribute("message", "Success");
		return "add-jabatan";
	}
	
	@RequestMapping(value="/jabatan/view", method=RequestMethod.GET)
	private String viewJabatan(@RequestParam(name="jabatanId") String id, Model model) {
		JabatanModel jabatan = jabatanService.getJabatanById(Long.parseLong(id)).get();
		List<JabatanPegawaiModel> listPegawai = jabatanPegawaiService.getPegawaiById(jabatan.getId());
		double gaji =  jabatan.getGaji_pokok();
		model.addAttribute("gaji", (long)gaji);
		model.addAttribute("jumlah" , listPegawai.size());
		model.addAttribute("jabatan", jabatan);
		return "view-jabatan";
	}
	
	@RequestMapping(value = "/jabatan/ubah", method = RequestMethod.GET)
	private String updateJabatan(@RequestParam(name="idJabatan") String id, Model model) {
		JabatanModel jabatan = jabatanService.getJabatanById(Long.parseLong(id)).get();
		double gaji =  jabatan.getGaji_pokok();
		model.addAttribute("message", "No");
		model.addAttribute("gaji", gaji);
		model.addAttribute("jabatan" , jabatan);
		return "update-jabatan";
	}
	
	@RequestMapping(value = "/jabatan/ubah", method = RequestMethod.POST)
	private String updateJabatanSubmit(@ModelAttribute JabatanModel jabatan, String idJabatan, Model model) {
		jabatanService.updateJabatan(jabatan , Long.parseLong(idJabatan));
		JabatanModel jabatan1 = jabatanService.getJabatanById(Long.parseLong(idJabatan)).get();
		double gaji =  jabatan.getGaji_pokok();
		model.addAttribute("gaji", gaji);
		model.addAttribute("jabatan" , jabatan1);
		model.addAttribute("message", "Success");
		return "update-jabatan";
	}
	
	@RequestMapping(value = "/jabatan/hapus", method = RequestMethod.POST)
	private String deleteJabatan(String idJabatan, Model model) {
		JabatanModel jabatan = jabatanService.getJabatanById(Long.parseLong(idJabatan)).get();
		List<JabatanPegawaiModel> listPegawai = jabatanPegawaiService.getPegawaiById(Long.parseLong(idJabatan));
		String respon = "";
		String status = "";
		if(listPegawai.isEmpty()) {
			jabatanService.deleteJabatan(jabatan);
			respon = "Jabatan berhasil dihapus";
			status = "SUKSES!";
		}
		else {
			respon = "Jabatan tidak bisa dihapus";
			status = "GAGAL!";
		}
		model.addAttribute("status" , status);
		model.addAttribute("info", respon);
		return "notifications";
	}
	
	@RequestMapping(value = "/jabatan/viewall")
	private String viewAllJabatan(Model model) {
		List<JabatanModel> listJabatan = jabatanService.getAll();
		model.addAttribute("jabatan" , listJabatan);
		return "viewall-jabatan";
	}
}
