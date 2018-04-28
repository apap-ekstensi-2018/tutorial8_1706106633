package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.model.StudentModel;
import com.example.service.StudentService;

@Controller
public class StudentController
{
    @Autowired
    StudentService studentDAO;

    
    @RequestMapping("/login")
    public String login () {
     return "login";
    }

    
    @RequestMapping("/")
    public String index (Model model)
    {
        model.addAttribute ("title", "Home");
        return "index";
    }


    @RequestMapping("/student/add")
    public String add (Model model)
    {
        model.addAttribute ("title", "Tambah Mahasiswa");
        return "form-add";
    }


    @RequestMapping("/student/add/submit")
    public String addSubmit (
    		Model model,
            @RequestParam(value = "npm", required = false) String npm,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "gpa", required = false) double gpa)
    {
        model.addAttribute ("title", "Sukses Tambah");
        StudentModel student = new StudentModel (npm, name, gpa);
        studentDAO.addStudent (student);

        return "success-add";
    }


    @RequestMapping("/student/view")
    public String view (Model model,
            @RequestParam(value = "npm", required = false) String npm)
    {
        StudentModel student = studentDAO.selectStudent (npm);

        if (student != null) {
            model.addAttribute ("title", "Tampil Mahasiswa");
            model.addAttribute ("student", student);
            return "view";
        } else {
            model.addAttribute ("title", "Gagal Tampil Mahasiswa");
            model.addAttribute ("npm", npm);
            return "not-found";
        }
    }


    @RequestMapping("/student/view/{npm}")
    public String viewPath (Model model,
            @PathVariable(value = "npm") String npm)
    {
        StudentModel student = studentDAO.selectStudent (npm);

        if (student != null) {
            model.addAttribute ("student", student);
            model.addAttribute ("title", "Tampil Mahasiswa");
            return "view";
        } else {
            model.addAttribute ("title", "Gagal Tampil Mahasiswa");
            model.addAttribute ("npm", npm);
            return "not-found";
        }
    }


    @RequestMapping("/student/viewall")
    public String view (Model model)
    {
        List<StudentModel> students = studentDAO.selectAllStudents ();
        model.addAttribute ("students", students);
        model.addAttribute ("title", "Tampil Semua Data");

        return "viewall";
    }


    @RequestMapping("/student/delete/{npm}")
    public String delete (Model model, @PathVariable(value = "npm") String npm)
    {

        StudentModel student = studentDAO.selectStudent (npm);

        if (student != null) {
            model.addAttribute ("title", "Hapus Mahasiswa");
            studentDAO.deleteStudent (npm);
            return "delete";
        } else {
            model.addAttribute ("title", "Hapus Mahasiswa");
            model.addAttribute ("npm", npm);
            return "not-found";
        }
    }

    
    @RequestMapping("/student/update/{npm}")
    public String update (Model model, @PathVariable(value = "npm") String npm)
    {

        StudentModel student = studentDAO.selectStudent (npm);

        if (student != null) {
            model.addAttribute ("title", "Update Mahasiswa");
            model.addAttribute ("student", student);
            return "form-update";
        } else {
            model.addAttribute ("title", "Update Mahasiswa");
            model.addAttribute ("npm", npm);
            return "not-found";
        }
    }
    
//    @RequestMapping(value = "/student/update/submit", method =  RequestMethod.POST)
//    public String updateSubmit (
//            @RequestParam(value = "npm", required = false) String npm,
//            @RequestParam(value = "name", required = false) String name,
//            @RequestParam(value = "gpa", required = false) double gpa)
//    {
//        StudentModel student = new StudentModel (npm, name, gpa);
//        studentDAO.updateStudent (student);
//
//        return "success-update";
//    }


//    @GetMapping(value = "/student/update/submit")
//    @PostMapping(value = "/student/update/submit")
    @RequestMapping(value = "/student/update/submit", method =  RequestMethod.POST)
    public String updateSubmit(Model model, @ModelAttribute StudentModel student, BindingResult bindingResult){
    	
    	if(bindingResult.hasErrors())
    		return "errorForm";
    		
        studentDAO.updateStudent (student);

        model.addAttribute ("title", "Update Mahasiswa");
        return "success-update";
    }

//    @RequestMapping(value = "/student/update/submit", method =  RequestMethod.POST)
//    public String updateSubmit(@ModelAttribute StudentModel student){
//    	    		
//        studentDAO.updateStudent (student);
//
//        return "success-update";
//    }
    
}
