package com.schoolnews.controller;

import com.schoolnews.dao.CategoryDao;
import com.schoolnews.javabean.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("CategoryServlet")
public class CategoryController {
    @Autowired
    private HttpServletRequest request;
    private CategoryDao categoryDao=new CategoryDao();

    @RequestMapping("/listforadmin")
    public String listforadmin(){
        List<Category> categoryList = categoryDao.queryAll();
        request.setAttribute("list", categoryList);
        return "listcategory";
    }
    @RequestMapping("/show")
    public String show(){
        List<Category> categoryList = categoryDao.queryAll();
        categoryList=categoryList.stream().filter(x->x.getState().equals("1")).collect(Collectors.toList());

        if(categoryList.size()>=5)
        {

            request.setAttribute("list", categoryDao.queryAll());
            request.setAttribute("msg","设置栏目显示失败，前台栏目最多显示5个");
            return "listcategory";
        }else {
            categoryDao.show(Integer.parseInt(request.getParameter("id")));
            return "redirect:listforadmin";
        }
    }
    @RequestMapping("/hidden")
    public String hidden(){

        categoryDao.hidden(Integer.parseInt(request.getParameter("id")));
        return "redirect:listforadmin";
    }
    @RequestMapping("/add")
    public String add(){
        String name=request.getParameter("name");
        String state=request.getParameter("state");
        Category category=new Category();
        category.setName(name);
        category.setState(state);
        categoryDao.save(category);
        return "redirect:listforadmin";
    }



}
