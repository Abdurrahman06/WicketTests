package org.abdurrahman.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table
public class YevmiyeMaster implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "slipid", nullable = false, precision = 12, scale = 0)
	private Long slipid;

//	@Column(name = "yevno", nullable = false, precision = 12, scale = 0)
//	private Long yevno;

	@Column(name = "yevno", nullable = false)
	private int yevno;
	
	@Column(name = "ilgilininAdi", length = 250)
	private String ilgilininAdi;
	
	@Column(name = "hazirlayaninAdi", length = 250)
	private String hazirlayaninAdi;
	
	@Column(name = "aciklama", length = 250)
	private String aciklama;
		
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "yevmiyeMaster", orphanRemoval = true,cascade = CascadeType.ALL)
	private List<Yevmiye> yevmiyeList = new ArrayList<Yevmiye>(0);
	

	public String getIlgilininAdi() {
		return ilgilininAdi;
	}

	public void setIlgilininAdi(String ilgilininAdi) {
		this.ilgilininAdi = ilgilininAdi;
	}

	public String getHazirlayaninAdi() {
		return hazirlayaninAdi;
	}

	public void setHazirlayaninAdi(String hazirlayaninAdi) {
		this.hazirlayaninAdi = hazirlayaninAdi;
	}

	public String getAciklama() {
		return aciklama;
	}

	public void setAciklama(String aciklama) {
		this.aciklama = aciklama;
	}
	
	public int getyevno() {
		return this.yevno;
	}

	public void setyevno(int yevno) {
		this.yevno = yevno;
	}

//	public Long getYevno() {
//		return yevno;
//	}
//
//	public void setYevno(Long yevno) {
//		this.yevno = yevno;
//	}

	public List<Yevmiye> getYevmiyeList() {
		return yevmiyeList;
	}

	public void setYevmiyeList(List<Yevmiye> yevmiyeList) {
		this.yevmiyeList = yevmiyeList;
	}

	public Long getslipid() {
		return slipid;
	}

	public void setslipid(Long slipid) {
		this.slipid = slipid;
	}
}
