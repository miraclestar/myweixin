package com.miracle.secretra;

import java.sql.Timestamp;

public class MM {

	String uid, secret, comment;

	public void setOwn(int own) {
		this.own = own;
	}

	public void setCreate_dt(Timestamp create_dt) {
		this.create_dt = create_dt;
	}

	int own;
	Timestamp create_dt;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
