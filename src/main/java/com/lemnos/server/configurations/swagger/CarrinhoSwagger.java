package com.lemnos.server.configurations.swagger;

import com.lemnos.server.exceptions.ExceptionResponse;
import com.lemnos.server.models.dtos.requests.CarrinhoRequest;
import com.lemnos.server.models.dtos.responses.CarrinhoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

@Tag(name = "Carrinho", description = "Cart")
public interface CarrinhoSwagger extends SwaggerConfiguration{

    @Operation(description = "Fetch the Cart of an User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fetched the cart successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CarrinhoResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized, you are not authenticated", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "403", description = "Forbidden, you don't have the role to fetch the cart", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "404", description = "Not Found, User not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class)))
    })
    @SecurityRequirement(name = "Authorization")
    ResponseEntity<CarrinhoResponse> getCarrinho(JwtAuthenticationToken token);

    @Operation(description = "Fetch the quantity of items inside the user cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fetched the quantity successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Integer.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized, you are not authenticated", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "403", description = "Forbidden, you don't have the role to fetch the cart", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "404", description = "Not Found, User not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class)))
    })
    @SecurityRequirement(name = "Authorization")
    ResponseEntity<Integer> quantidadeProdutos(JwtAuthenticationToken token);

    @Operation(description = "Add an item in the user cart.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Added the item successfully", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "401", description = "Unauthorized, you are not authenticated", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "403", description = "Forbidden, you don't have the role to add an item from cart", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "404", description = "Not Found, User, Cart or item not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class)))
    })
    @SecurityRequirement(name = "Authorization")
    ResponseEntity<Void> adicionarProduto(JwtAuthenticationToken token, CarrinhoRequest carrinhoRequest);

    @Operation(description = "Remove an item in the user cart.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Removed the item successfully", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "401", description = "Unauthorized, you are not authenticated", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "403", description = "Forbidden, you don't have the role to remove an item from cart", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "404", description = "Not Found, User, Cart or item not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class)))
    })
    @SecurityRequirement(name = "Authorization")
    ResponseEntity<Void> removerProduto(JwtAuthenticationToken token, CarrinhoRequest carrinhoRequest);

    @Operation(description = "This endpoint clear the user cart.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cleared the cart successfully", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "401", description = "Unauthorized, you are not authenticated", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "403", description = "Forbidden, you don't have the role to clear the cart", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "404", description = "Not Found, User or Cart not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class)))
    })
    @SecurityRequirement(name = "Authorization")
    ResponseEntity<Void> removerTodosProdutos(JwtAuthenticationToken token);
}