# MicroBlog
MicroBlog is a simple blog service implemented in Java with Spring framework. 
The service provides RESTful JSON API and uses mysql as a database.
Blog has the following fields:
* id
* text - the blog's content
* numLikes - number of likes given to blog by users
* createdDate - date of blog creation

## API
The service runs on a default port:8080 and supports the following endpoints:
* **[GET]** localhost:8080/**blogs** - returns a list of all the blogs
* **[GET]** localhost:8080/**blogs/{id}** - returns a blog identified by id or displays an error message if no such blog.
* **[POST]** localhost:8080/**blogs** - adds a new blog to the system. Header should contain 'Content-Type: application/json' and Body
with format of { text: "Example" }. id is auto-incremented, createdDate is auto-generated and numLikes is set to 0.
* **[PUT]** localhost:8080/**blogs/{id}** - updates a blog identified by id or displays an error message if no such blog. Changes the text field only.
* **[DELETE]** localhost:8080/**blogs/{id}** - deletes a blog identified by id or displays an error message if no such blog.
* **[POST]** localhost:8080/**blogs/like/{id}** - likes a blog identified by id or displays an error message if no such blog.

Trending:
* **[GET]** localhost:8080/**mostLiked** - returns a list of top 10 most liked blogs. If there aren't blogs at all, will display an appropriate message.
* **[GET]** localhost:8080/**mostRecentBlogs** - returns a list of top 10 most recent blogs. If there aren't blogs at all, will display an appropriate message.

## Deployment
Running the command 'docker-compose up' will deploy both services (microblog and mysql) and define the needed network.
The jar file is located under /target and it can be rebuilt using maven.
