package com.lemnos.server.configurations.swagger;

import com.lemnos.server.models.dtos.requests.AlterarStatusRequest;
import com.lemnos.server.models.dtos.requests.PedidoRequest;
import com.lemnos.server.models.dtos.responses.PedidoResponse;
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

@Tag(name = "Pedido", description = "Order")
public interface PedidoSwagger extends SwaggerConfiguration {

    @Operation(description = "Fetch all orders from an users by its token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fetched all orders successfully", content = @Content(array = @ArraySchema(schema = @Schema(implementation = PedidoResponse.class)))),
            @ApiResponse(responseCode = "401", description = "Unauthorized, you are not authenticated", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "403", description = "Forbidden, you don't have the role to fetch the orders", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "404", description = "Not Found, user not found", content = @Content(schema = @Schema()))
    })
    @SecurityRequirement(name = "Authorization")
    ResponseEntity<List<PedidoResponse>> getAllByEmail(JwtAuthenticationToken token);

    @Operation(description = "Fetch one order by id from an user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fetched the order successfully", content = @Content(array = @ArraySchema(schema = @Schema(implementation = PedidoResponse.class)))),
            @ApiResponse(responseCode = "401", description = "Unauthorized, you are not authenticated", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "403", description = "Forbidden, you don't have the role to fetch the employees", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "404", description = "Not found, Order not found", content = @Content(schema = @Schema()))
    })
    @SecurityRequirement(name = "Authorization")
    ResponseEntity<PedidoResponse> getOneById(Integer id);

    @Operation(description = "Register a new order.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registered an order successfully", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "401", description = "Unauthorized, you are not authenticated", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "403", description = "Forbidden, you don't have the role to register a new order", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "404", description = "Not Found, user not found", content = @Content(schema = @Schema()))
    })
    @SecurityRequirement(name = "Authorization")
    ResponseEntity<Void> novoPedido(PedidoRequest pedidoRequest, JwtAuthenticationToken token);

    @Operation(description = "Update the status of an order, just hitting the endpoint update to the next status.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated the status successfully", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "401", description = "Unauthorized, you are not authenticated", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "403", description = "Forbidden, you don't have the role to update the order", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "404", description = "Not Found, Order or User not found", content = @Content(schema = @Schema()))
    })
    @SecurityRequirement(name = "Authorization")
    ResponseEntity<Void> alterarStatus(AlterarStatusRequest request);
}
