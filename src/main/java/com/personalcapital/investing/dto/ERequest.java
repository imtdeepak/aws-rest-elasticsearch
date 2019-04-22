package com.personalcapital.investing.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class ERequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7144320196694505848L;
	private String planName, sponsorName, sponsorState;

}
