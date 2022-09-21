package com.chatbot.dao;
 
import java.util.List;

import javax.faces.context.FacesContext;

import org.apache.catalina.valves.StuckThreadDetectionValve;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
 
import com.chatbot.pojo.Student;
import com.chatbot.util.HibernateUtil;
 
public class DatabaseOperations {
 
    private static Transaction transObj;
    private static Session sessionObj = HibernateUtil.getSessionFactory().openSession();

    public List<Student> getStudents() {
    	transObj = sessionObj.beginTransaction();
    	List<Student> particularStuDObj = (List<Student>)sessionObj.createQuery("SELECT a FROM Student a").getResultList();
        transObj.commit(); 
        return particularStuDObj;
    }
    
    public Student fetchStudentById(int studentId) {
    	transObj = sessionObj.beginTransaction();
    	Student particularStuDObj = new Student();
    	Query queryObj = sessionObj.createQuery("from Student where id= :student_id").setInteger("student_id", studentId);          
        particularStuDObj = (Student)queryObj.uniqueResult();
        transObj.commit(); 
        return particularStuDObj;
    }
    
    public void addStudentRecord(Student studentObj) {
    	FacesContext.getCurrentInstance().getExternalContext().getSessionMap().clear();
    	try {
	    	transObj = sessionObj.beginTransaction();
	    	sessionObj.save(studentObj);
			transObj.commit();
    		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("enteredIdFind", "Empty Field");
    	} catch (Exception e) {
    		 e.printStackTrace();
		}
    }
      
    public void updateStudentRecord(Student updateStudentObj) {
    	FacesContext.getCurrentInstance().getExternalContext().getSessionMap().clear();
    	Student particularStuDObj = fetchStudentById(updateStudentObj.getId());
        try {
        	if(particularStuDObj != null) {
	        	transObj = sessionObj.beginTransaction();
	        	particularStuDObj.setName(updateStudentObj.getName());
	        	particularStuDObj.setDepartment(updateStudentObj.getDepartment());
	        	transObj.commit();
        		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("enteredIdFind", "Empty Field");
        	}
        	else {
             	FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("findStudentById", "Success");
        	}
        } catch(Exception exceptionObj){
            exceptionObj.printStackTrace();
        }
    }
 
    public void deleteStudentInDb(int delStudentId) {
    	FacesContext.getCurrentInstance().getExternalContext().getSessionMap().clear();
        try {
        	Student particularStuDObj = fetchStudentById(delStudentId);
        	 if(particularStuDObj != null) {
	            transObj = sessionObj.beginTransaction();
	            Student studId = (Student)sessionObj.load(Student.class, new Integer(delStudentId));
	            sessionObj.delete(studId);
	            transObj.commit();
        		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("enteredIdFind", "Empty Field");
        	}
        	 else {
              	FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("findStudentById", "Success");
         	}
        } catch (Exception exceptionObj) {
            exceptionObj.printStackTrace();
        }
    }
}