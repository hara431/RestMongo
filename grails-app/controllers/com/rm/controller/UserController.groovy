package com.rm.controller

import com.rm.domain.AuthToken;
import com.rm.domain.User;

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured;
import grails.rest.RestfulController;


class UserController extends RestfulController {
	
	//I tried with respond but it's not working.
	
	@Secured(['permitAll'])
	def getUser(){
		def resMap = [:]
		def jsonObj = JSON.parse(request)
		def userobj = User.findByUsername(jsonObj.username)
		def authObj = AuthToken.createCriteria().get{
			eq("username",jsonObj.username)
			order("id", "desc")
		}
		if(authObj.tokenValue == jsonObj.access_token){
			render userobj as JSON
		}else{
			resMap = ["message":"Invalid token","result":true];
			render resMap
		}
	}
	
	@Secured(['permitAll'])
	def logout(){
		def resMap = [:]
		def jsonObj = JSON.parse(request)
		def authObj = AuthToken.createCriteria().get{
			eq("username",jsonObj.username)
			order("id", "desc")
		}
		if(authObj.tokenValue == jsonObj.access_token){
			def authObjects = AuthToken.findAllByUsername(jsonObj.username)
			authObjects.each{ auth->
				auth.delete(flush:true)
			}
			resMap = ["message":"User logout succesfully","result":true];
		}else{
			resMap = ["message":"Invalid token","result":true];
		}
		render resMap
		
	}
}
