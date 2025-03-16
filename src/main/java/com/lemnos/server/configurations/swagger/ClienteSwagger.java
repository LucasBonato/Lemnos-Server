package com.lemnos.server.configurations.swagger;

import com.lemnos.server.exceptions.ExceptionResponse;
import com.lemnos.server.models.dtos.requests.ClienteRequest;
import com.lemnos.server.models.dtos.responses.ClienteResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.List;

@Tag(name = "Cliente", description = "User")
public interface ClienteSwagger extends SwaggerConfiguration{

    @Operation(description = "Fetch all users.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fetched all users successfully", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ClienteResponse.class)))),
            @ApiResponse(responseCode = "401", description = "Unauthorized, you are not authenticated", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "403", description = "Forbidden, you don't have the role to fetch the employees", content = @Content(schema = @Schema()))
    })
    @SecurityRequirement(name = "Authorization")
    ResponseEntity<List<ClienteResponse>> getAll();

    @Operation(description = "Fetch the user by its token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fetched the user successfully", content = @Content(schema = @Schema(implementation = ClienteResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized, you are not authenticated", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "403", description = "Forbidden, you don't have the role to fetch the employees", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "404", description = "Not Found, user not found", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
    })
    @SecurityRequirement(name = "Authorization")
    ResponseEntity<ClienteResponse> getOneByEmail(JwtAuthenticationToken token);

    @Operation(description = "Update one user with the optional fields.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated the user successfully", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "401", description = "Unauthorized, you are not authenticated", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "403", description = "Forbidden, you don't have the role to fetch the employees", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "404", description = "Not Found, user not found", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
    })
    @SecurityRequirement(name = "Authorization")
    ResponseEntity<Void> updateById(JwtAuthenticationToken token, ClienteRequest clienteRequest);

    @Operation(description = "Delete the user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleted the user successfully", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "401", description = "Unauthorized, you are not authenticated", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "403", description = "Forbidden, you don't have the role to fetch the employees", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "404", description = "Not Found, user not found", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
    })
    @SecurityRequirement(name = "Authorization")
    ResponseEntity<Void> deleteById(JwtAuthenticationToken token);
}
