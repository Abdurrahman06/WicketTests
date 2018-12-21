package org.abdurrahman.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table
public class Yevmiye implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, precision = 19, scale = 0)
	private Long id;

	@Column(name = "hesapKod", length = 40)
	private String hesapKod;

	@Column(name = "borc")
	private BigDecimal borc;

	@Column(name = "alacak")
	private BigDecimal alacak;

	@JoinColumn(name = "HesapKodId")
	@ManyToOne(cascade = CascadeType.ALL, optional = true)
	private HesapPlan hesapPlan;

	@JoinColumn(name = "slipid")
	@ManyToOne(cascade = CascadeType.ALL, optional = true)
	private YevmiyeMaster yevmiyeMaster;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getHesapKod() {
		return hesapKod;
	}

	public void setHesapKod(String hesapKod) {
		this.hesapKod = hesapKod;
	}

	public BigDecimal getBorc() {
		return borc;
	}

	public void setBorc(BigDecimal borc) {
		this.borc = borc;
	}

	public BigDecimal getAlacak() {
		return alacak;
	}

	public void setAlacak(BigDecimal alacak) {
		this.alacak = alacak;
	}

	public YevmiyeMaster getYevmiyeMaster() {
		return yevmiyeMaster;
	}

	public void setYevmiyeMaster(YevmiyeMaster yevmiyeMaster) {
		this.yevmiyeMaster = yevmiyeMaster;
	}
}