package com.soroweb.board.vo;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name="member")
public class MemberVo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="MB_ID")
	private Long mbId;
	@Column(name ="MB_USER_ID")
	private String mbUserId;
	@Column(name ="MB_USER_PW")
	private String mbUserPw;
	@Column(name ="MB_NAME")
	private String mbName;
	@Column(name ="MB_EMAIL")
	private String mbEmail;
	@Column(name ="MB_TEL")
	private String mbTel;
	@Column(name ="MB_ENROL_DT")
	private LocalDateTime mbEnrolDt;
	@Column(name ="isaccountnonexpired")
	private int isaccountnonexpired;
	@Column(name ="isaccountnonlocked")
	private int isaccountnonlocked;
	@Column(name ="iscredentialsnonexpired")
	private int iscredentialsnonexpired;
	@Column(name ="isenabled")
	private int isenabled;
	
	@Builder
	public MemberVo( Long mbId, String mbUserId, String mbUserPw, String mbName, String mbEmail, String mbTel,
			int isaccountnonexpired, int isaccountnonlocked, int iscredentialsnonexpired, int isenabled) {
		this.mbId = mbId;
		this.mbUserId = mbUserId;
		this.mbUserPw = mbUserPw;
		this.mbName = mbName;
		this.mbEmail = mbEmail;
		this.mbTel = mbTel;
		this.isaccountnonexpired = isaccountnonexpired;
		this.isaccountnonlocked = isaccountnonlocked;
		this.iscredentialsnonexpired = iscredentialsnonexpired;
		this.isenabled = isenabled;
	}
}
