package com.trunggame.dto;

import javax.validation.constraints.NotBlank;


/**
 * @author congn kma
 * @since 7/12/2023
 */

public class LoginRequestDTO {
	@NotBlank
  private String username;

	@NotBlank
	private String password;

	private String newPassword;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
}
