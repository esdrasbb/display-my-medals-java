package com.apc.resource;

import com.apc.service.MedalService;
import com.apc.transformer.JsonTransformer;

import static spark.Spark.*;

/**
 * Created by esdrasbb on 22/11/15.
 */
public class MedalResource {

    private static final String API_CONTEXT = "/api/v1";

    private final MedalService medalService;

    public MedalResource(MedalService medalService) {
        this.medalService = medalService;
        setupEndpoints();
    }

    private void setupEndpoints() {
        get(API_CONTEXT + "/medals", "application/json", (request, response)
                -> medalService.findMedals(), new JsonTransformer());

        get(API_CONTEXT + "/classes", "application/json", (request, response)
                -> medalService.findAllClasses(), new JsonTransformer());

        post(API_CONTEXT + "/classes", "application/json", (request, response) -> {
            medalService.createNewClass(request.body());
            response.status(201);
            return response;
        }, new JsonTransformer());

        get(API_CONTEXT + "/classes/:id", "application/json", (request, response)
                -> medalService.findClass(request.params(":id")), new JsonTransformer());

        put(API_CONTEXT + "/classes/:id", "application/json", (request, response)
                -> medalService.updateClass(request.params(":id"), request.body()), new JsonTransformer());

        get(API_CONTEXT + "/students", "application/json", (request, response)
                -> medalService.findAllStudents(), new JsonTransformer());

        post(API_CONTEXT + "/students", "application/json", (request, response) -> {
            medalService.createNewStudent(request.body());
            response.status(201);
            return response;
        }, new JsonTransformer());

        get(API_CONTEXT + "/students/:id", "application/json", (request, response)
                -> medalService.findStudent(request.params(":id")), new JsonTransformer());

        put(API_CONTEXT + "/students/:id", "application/json", (request, response)
                -> medalService.addClassToStudent(request.params(":id"), request.body()), new JsonTransformer());

        post(API_CONTEXT + "/class", "application/json", (request, response) -> {
            String studentId = request.body().substring(0, request.body().indexOf("#"));
            String classId = request.body().substring(request.body().indexOf("#") + 1, request.body().length());
            medalService.addClassToStudent(studentId, classId);
            response.status(201);
            return response;
        }, new JsonTransformer());

    }

}
