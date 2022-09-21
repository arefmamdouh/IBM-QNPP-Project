package com.chatbot.pojo;
 
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import com.chatbot.dao.DatabaseOperations;

import net.bytebuddy.asm.Advice.This;
 
@ManagedBean
public class Student implements java.io.Serializable {
 
    private int id;
    private String name;
    private String department;
	private List<Student> studentsList;
	public static DatabaseOperations dbObj;
    private static final long serialVersionUID = 1L;
 
    public Student() {
    	studentsList = new ArrayList<>();
    }
    
	public Student(int id) {
        this.id = id;
    }
 
    public Student(int id, String name, String department) {
        this.id = id;
        this.name = name;
        this.department = department;
    }
    
    public int getId() {
        return this.id;
    }
 
    public void setId(int id) {
        this.id = id;
    }
 
    public String getName() {
        return this.name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
    public String getDepartment() {
        return this.department;
    }
 
    public void setDepartment(String department) {
        this.department = department;
    }
 
    public List<Student> getStudentsList() {
    	return studentsList;
    }
    
    public void setStudentsList(List<Student> studentsList) {
    	this.studentsList = studentsList;
    }
    
    public void loadStudents() {
    	studentsList.clear();
    	dbObj = new DatabaseOperations();
    	studentsList = dbObj.getStudents();
    	System.out.println("Retrieved data from DB");
    	FacesContext.getCurrentInstance().getExternalContext().getSessionMap().clear();
    }
    
    public void saveStudentRecord() {
        System.out.println("Calling saveStudentRecord() Method To Save Student Record");
        dbObj = new DatabaseOperations();
        dbObj.addStudentRecord(this);
    }
    
    public void updateStudentDetails() {
        System.out.println("Calling updateStudentDetails() Method To Update Student Record");
        dbObj = new DatabaseOperations();       
        dbObj.updateStudentRecord(this);
    }
 
    public void deleteStudentRecord() {
        System.out.println("Calling deleteStudentRecord() Method To Delete Student Record");
        dbObj = new DatabaseOperations();
        dbObj.deleteStudentInDb(id);
    }
    
    public void validateString(FacesContext context, UIComponent component, Object value)throws ValidatorException {
        String dataString = value.toString();
        try {
            int intValue = Integer.parseInt(dataString);
            FacesMessage message = new FacesMessage("Invalid Input");
            throw new ValidatorException(message);
        } catch (NumberFormatException e) {
            return;
        }
    }
    
    public void validateID(FacesContext context, UIComponent component, Object value)throws ValidatorException {
        String dataString = value.toString();
        try {
            int intValue = Integer.parseInt(dataString);
            if(intValue <=0) {
            	FacesMessage message = new FacesMessage("Invalid ID");
            	throw new ValidatorException(message);
            }
        } catch (NumberFormatException e) {
        	FacesMessage message = new FacesMessage("Invalid ID");
            throw new ValidatorException(message); 
        }
    } 
}
