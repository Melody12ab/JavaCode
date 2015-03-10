package com.review;

public class bukebian {
	private final String detail;
	private final String postCode;
	public bukebian(){
		this.detail="";
		this.postCode="";
	}
	public bukebian(String detail,String postCode){
		this.detail=detail;
		this.postCode=postCode;
	}
	public String getDetail(){
		return this.detail;
	}
	public String getPostCode(){
		return this.postCode;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((detail == null) ? 0 : detail.hashCode());
		result = prime * result
				+ ((postCode == null) ? 0 : postCode.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		bukebian other = (bukebian) obj;
		if (detail == null) {
			if (other.detail != null)
				return false;
		} else if (!detail.equals(other.detail))
			return false;
		if (postCode == null) {
			if (other.postCode != null)
				return false;
		} else if (!postCode.equals(other.postCode))
			return false;
		return true;
	}
}
