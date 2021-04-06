package com.soroweb.board.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class SiteConfigDto {

	private Long scIdx;
	private String scAdminMail;
	private String scInquiryToMail;
	private String scPrivacyKo;
	private String scPrivacyEn;
	private String scSmtpInquiry;
	private String scSmtpEstimate;
	private String scSmtpSample;
	
	@Builder
	public SiteConfigDto( Long scIdx, String scAdminMail, String scInquiryToMail, String scPrivacyKo, String scPrivacyEn ) {
		this.scIdx = scIdx;
		this.scAdminMail = scAdminMail;
		this.scInquiryToMail = scInquiryToMail;
		this.scPrivacyEn = scPrivacyEn;
		this.scPrivacyKo = scPrivacyKo;
	}
}
