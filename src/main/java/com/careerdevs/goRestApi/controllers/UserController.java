package com.careerdevs.goRestApi.controllers;

import com.careerdevs.goRestApi.models.UserModel;
import com.careerdevs.goRestApi.models.UserModelArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private Environment env;

    /* @GetMapping( "/{id}")

     //RestTemplates enable to request outside APIs
     public Object getUser(RestTemplate restTemplate, @PathVariable("id") String userId) {
         try {
             String url = "https://gorest.co.in/public/v2/users/" + userId;
             // getForObject making a request to receive json data
             }
         catch(Exception e){
             return "404 error. No user exists with id " +  userId;
     }

         }
         */
    @GetMapping("/{id}")
    public Object getUser(RestTemplate restTemplate, @PathVariable("id") String userId) {
        try {
            String url = "https://gorest.co.in/public/v2/users/" + userId;
            String token = env.getProperty("GOREST_TOKEN");
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(token);

            HttpEntity request = new HttpEntity(headers);

            return restTemplate.exchange(url, HttpMethod.GET, request, Object.class);
            //headers.set("Authorization", "Bearer" + token);
            //return " Successfully retrieved user: " + userId;
        } catch (Exception e) {
            return e.getMessage();
        }
    }
    //vid1 75
    //reponse entity <object> versus rest template.exchange  -> line 60

    @DeleteMapping("/{id}")

    //RestTemplates enable t request outside APIs
    public Object deleteUser(RestTemplate restTemplate, @PathVariable("id") String userId) {
        try {
            String url = "https://gorest.co.in/public/v2/users/" + userId;
            String token = env.getProperty("GOREST_TOKEN");

            //headers are used when couldnt use query parameters to send the token
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(token);
            HttpEntity request = new HttpEntity(headers);
            ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.DELETE, request, Object.class);
            //headers.set("Authorization", "Bearer" + token);

            //wht is line 64 doing
          //restTemplate.delete(url);
            return "Successfully deleted user: " + userId;
        } catch (Exception e) {
            return e.getMessage();
        }
    }


    @PostMapping("/")
    public Object postUser(

            //create query parameters
            @RequestParam String name,
            @RequestParam("email") String email,
            @RequestParam("gender") String gender,
            @RequestParam("status") String status,
            RestTemplate restTemplate

    ) {
        try {
            String url = "https://gorest.co.in/public/v2/users/";
            String token = env.getProperty("GOREST_TOKEN");
            url += "?access-token=" + token;
            UserModel newUser = new UserModel(name, email, gender , status);
            //HttpHeaders headers = new HttpHeaders();
            //headers.setBearerAuth(token);

            //create a request. A place to store user, a place to attach the requestBody to the request itself.
            HttpEntity<UserModel> request= new HttpEntity(newUser);

            //send a request through restTemplate through postForObject
            return restTemplate.postForEntity(url ,request, UserModel.class );
                                                            //a class to be serialized or decerialized

        } catch (Exception e) {
            System.out.println(e.getClass());
            return e.getMessage();
        }
}
    @PostMapping("/qp")
    public Object postUserQueryParam(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("gender") String gender,
            @RequestParam("status") String status,
            RestTemplate restTemplate

    ) {
        try {
            String url = "http://gorest.co.in/public/v2/users/";
            String token = env.getProperty("GOREST_TOKEN");
            url += "?access-token" + token;
            UserModel newUser = new UserModel(name, email, gender, status);

            //headers are used when couldnt use query parameters to send the token
            HttpHeaders headers = new HttpHeaders();

            headers.setBearerAuth(token);
            HttpEntity<UserModel> request = new HttpEntity<>(newUser);

            return restTemplate.postForEntity(url, request, UserModel.class);


        } catch (Exception exception) {
            System.out.println(exception.getClass());
            return exception.getMessage();

        }

}

    @GetMapping("/firstpage")
    public Object getFirstPage(RestTemplate restTemplate) {

        try {
            // we are going to start by requesting first page.
            String url = "https://gorest.co.in/public/v2/users/";

            //how is the UserModel[ working but the UserModelArray is not . How are we able to map whts inside UserModelArray with UserModel class

            ResponseEntity<UserModel[]> response = restTemplate.getForEntity(url, UserModel[].class);
                                                                  //getForEntity picks up all data in the header and returns a ResponseEntity
            UserModel[] firstPageUser = response.getBody();
            HttpHeaders responseHeaders = response.getHeaders();
            String totalPages= responseHeaders.get("X-Pagination-Pages").get(0);
            System.out.println("Total pages : " + totalPages);



            return new ResponseEntity<>(firstPageUser, HttpStatus.OK);
//            return new ResponseEntity<>(firstPageUser,HttpStatus.OK);

        } catch (Exception e) {
            System.out.println(e.getClass());
            System.out.println(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }
    @GetMapping("/page/{pageNum}")
    public Object getFirstPage(
            RestTemplate restTemplate,
            @PathVariable ("pageNum") String pageNumber){

        try {
            // we are going to start by requesting first page.
            String url = "https://gorest.co.in/public/v2/users?page=" + pageNumber;

            //how is the UserModel[ working but the UserModelArray is not . How are we able to map whts inside UserModelArray with UserModel class

            ResponseEntity<UserModel[]> response = restTemplate.getForEntity(url, UserModel[].class);
            //getForEntity picks up all data in the header and returns a ResponseEntity
            UserModel[] firstPageUser = response.getBody();
            HttpHeaders responseHeaders = response.getHeaders();
            String totalPages= responseHeaders.get("X-Pagination-Pages").get(0);
            System.out.println("Total pages : " + totalPages);



            return new ResponseEntity<>(firstPageUser, HttpStatus.OK);
//            return new ResponseEntity<>(firstPageUser,HttpStatus.OK);

        } catch (Exception e) {
            System.out.println(e.getClass());
            System.out.println(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }
}


//    //gorest pi part 2 20.00 to string
//difference between @RequestPram-
//@PathVariable-  and query parameter

//why do we create and use http headers
//.exchange method versus? (the other way?)
//why do we create and use http entity
//difference between compiling and serialization