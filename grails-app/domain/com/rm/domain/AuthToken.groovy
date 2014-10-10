package com.rm.domain

class AuthToken {
	
	static mapWith = "mongo"
	
	String id
	String tokenValue
	String username

    static constraints = {
		
    }
	static mapping = {  version false  }
}
