package com.rm.controller

import com.rm.domain.AuthToken;
import com.rm.domain.User;

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured;
import grails.rest.RestfulController;


class UserController extends RestfulController {
	
	@Secured(['permitAll'])
	def getUser(){
		println "came in to the getUser"
		def resMap = [:]
		def jsonObj = JSON.parse(request)
		println "jsonObj--->>"+jsonObj
		def userobj = User.findByUsername(jsonObj.username)
		println "userobj--->>>"+userobj
		def authObj = AuthToken.createCriteria().get{
			eq("username",jsonObj.username)
			order("id", "desc")
		}
		println "authObj--->>>"+authObj
		if(authObj.tokenValue == jsonObj.access_token){
			render userobj as JSON
		}else{
			resMap = ["message":"Invalid token","result":true];
			render resMap
		}
	}
	
	@Secured(['permitAll'])
	def logout(){
		println "camne in to the logout"
		def resMap = [:]
		def jsonObj = JSON.parse(request)
		println "jsonObj--->>"+jsonObj
		def authObj = AuthToken.createCriteria().get{
			eq("username",jsonObj.username)
			order("id", "desc")
		}
		if(authObj.tokenValue == jsonObj.access_token){
			def authObjects = AuthToken.findAllByUsername(jsonObj.username)
			println "authObjects--->>>"+authObjects
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
