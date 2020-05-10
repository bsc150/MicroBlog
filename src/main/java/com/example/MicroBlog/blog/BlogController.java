package com.example.MicroBlog.blog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BlogController {

    @Autowired
    private BlogService blogService;

    @RequestMapping("/blogs")
    public List<Blog> getAllBlogs() {
        return blogService.getAllBlogs();
    }

    @RequestMapping("/blogs/{id}")
    public Blog getBlog(@PathVariable int id) {
        return blogService.getBlog(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/blogs")
    public void addBlog(@RequestBody Blog blog) {
        blogService.addBlog(blog);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/blogs/{id}")
    public void updateBlog(@RequestBody Blog blog, @PathVariable int id) {
        blogService.updateBlog(id, blog);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/blogs/{id}")
    public void deleteBlog(@PathVariable int id) {
        blogService.deleteBlog(id);
    }

    @RequestMapping("/blogs/like/{id}")
    public void likeBlog(@PathVariable int id) {
        blogService.likeBlog(id);
    }

    @RequestMapping("/mostLiked")
    public List<Blog> getMostLikedBlogs() {
        return blogService.getMostLikedBlogs();
    }

    @RequestMapping("/mostRecentBlogs")
    public List<Blog> getMostRecentBlogs() {
        return blogService.getMostRecentBlogs();
    }
}
