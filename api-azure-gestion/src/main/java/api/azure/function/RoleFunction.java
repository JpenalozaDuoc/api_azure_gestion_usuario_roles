package api.azure.function;
import java.util.List;
import java.util.Optional;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import com.microsoft.azure.functions.annotation.BindingName;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;

import api.azure.model.Role;
import api.azure.service.RoleService;



public class RoleFunction {

    private final RoleService roleService = new RoleService();

    @FunctionName("createRole")
    public HttpResponseMessage createRole(
        @HttpTrigger(name = "req", methods = {HttpMethod.POST}, route = "roles") HttpRequestMessage<Optional<Role>> request,
        ExecutionContext context) {

        try {
            Role role = request.getBody().orElseThrow(() -> new IllegalArgumentException("Role data is required"));
            roleService.createRole(role);
            return request.createResponseBuilder(HttpStatus.CREATED).body("Role created successfully").build();
        } catch (Exception e) {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("Error creating role: " + e.getMessage()).build();
        }
    }

    @FunctionName("getAllRoles")
    public HttpResponseMessage getAllRoles(
        @HttpTrigger(name = "req", methods = {HttpMethod.GET}, route = "roles") HttpRequestMessage<Optional<String>> request,
        ExecutionContext context) {

        try {
            List<Role> roles = roleService.getAllRoles();
            return request.createResponseBuilder(HttpStatus.OK).body(roles).build();
        } catch (Exception e) {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("Error fetching roles: " + e.getMessage()).build();
        }
    }

    @FunctionName("updateRole")
    public HttpResponseMessage updateRole(
        @HttpTrigger(name = "req", methods = {HttpMethod.PUT}, route = "roles/{id}") HttpRequestMessage<Optional<Role>> request,
        @BindingName("id") String id, ExecutionContext context) {

        try {
            Role role = request.getBody().orElseThrow(() -> new IllegalArgumentException("Role data is required"));
            roleService.updateRole(Long.valueOf(id), role);
            return request.createResponseBuilder(HttpStatus.OK).body("Role updated successfully").build();
        } catch (Exception e) {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("Error updating role: " + e.getMessage()).build();
        }
    }

    @FunctionName("deleteRole")
    public HttpResponseMessage deleteRole(
        @HttpTrigger(name = "req", methods = {HttpMethod.DELETE}, route = "roles/{id}") HttpRequestMessage<Optional<String>> request,
        @BindingName("id") String id, ExecutionContext context) {

        try {
            roleService.deleteRole(Long.valueOf(id));
            return request.createResponseBuilder(HttpStatus.OK).body("Role deleted successfully").build();
        } catch (Exception e) {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("Error deleting role: " + e.getMessage()).build();
        }
    }
}
