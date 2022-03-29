package com.careerdevs.goRestApi.controllers;

import com.careerdevs.goRestApi.models.UserModel;
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
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @DeleteMapping("/{id}")

    //RestTemplates enable t request outside APIs
    public Object deleteUser(RestTemplate restTemplate, @PathVariable("id") String userId) {
        try {
            String url = "https://gorest.co.in/public/v2/users/" + userId;
            String token = env.getProperty("GOREST_TOKEN");
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(token);
            HttpEntity request = new HttpEntity(headers);
            ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.DELETE, request, Object.class);
            //headers.set("Authorization", "Bearer" + token);


            restTemplate.delete(url);
            return "Successfully deleted user" + userId;
        } catch (Exception e) {
            return e.getMessage();
        }
    }


    @PostMapping("/qp")
    public Object postUser(
            @RequestParam("name") String name,
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
            HttpEntity<UserModel> request= new HttpEntity<>(newUser);
            return restTemplate.postForEntity(url ,request, UserModel.class );

        } catch (Exception e) {
            System.out.println(e.getClass());
            return e.getMessage();
        }
}



}


    //gorest pi part 2 20.00 to string