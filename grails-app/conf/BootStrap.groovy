import javax.jws.soap.SOAPBinding.Use;

import com.rm.domain.User

class BootStrap {

    def init = { servletContext ->
		
		if(User.list().isEmpty()){
			def userIns = new User(username: 'Haranath', password: 'Password@1')
			if(userIns.save(flush:true)){
				println "user saved"	
			}else{
				println "user not saved"
			}
		}
    }
    def destroy = {
    }
}
