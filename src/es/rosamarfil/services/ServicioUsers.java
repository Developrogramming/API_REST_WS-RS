package es.rosamarfil.services;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import es.rosamarfil.modelo.User;

@Path("/users")
public class ServicioUsers {

	private static List<User> listaUsuarios = new ArrayList<User>() {
		{
			add(new User("Rosa", "Marfil"));
			add(new User("Pepito", "Grillo"));
			add(new User("Manuela", "Lago"));
		}
	};

	
	/**
	 * URL: http://localhost:8080/API_REST_WS-RS/api/users
	 * @return Response list Users
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUsers() {
		return Response.ok(listaUsuarios).build();
	}

	/**
	 * URL: http://localhost:8080/API_REST_WS-RS/api/users/Rosa
	 * 
	 * @param name String
	 * @return Response
	 */
	@GET
	@Path("/{name}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserById(@PathParam("name") String name) {
		User found = null;
		for (int i = 0; i < listaUsuarios.size(); i++) {
			if (listaUsuarios.get(i).getName().equalsIgnoreCase(name)) {
				found = listaUsuarios.get(i);
			}
		}
		if (found == null) {
			return Response.status(Status.BAD_REQUEST).entity("User not found").build();
		} else {
			return Response.ok(found).build();
		}
	}

	/**
	 * URL: http://localhost:8080/API_REST_WS-RS/api/users/createUser Parameters in
	 * Postman: {"name":"Rosa3333","username":"Marfi3333l"}
	 * 
	 * @param User
	 * @return Response list NOTA: Si no existe el constructor vacío de User, da un
	 *         error y el userRequest viene null.
	 */
	@POST
	@Path("/createUser")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createUser(User userRequest) {

		this.listaUsuarios.add(userRequest);
		//return Response.status(Status.CREATED).build();
		return Response.ok(listaUsuarios).build();

	}

	/**
	 * URL: http://localhost:8080/API_REST_WS-RS/api/users/updateUser Parameters in
	 * Postman: {"name":"Rosa","username":"Marfil3333"}
	 * 
	 * @param User
	 * @return user modified NOTA: Si no existe el constructor vacío de User, da un
	 *         error y el userRequest viene null.
	 */
	@PUT
	@Path("/updateUser")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateUser(User userUpdate) {
		User found = null;
		for (int i = 0; i < listaUsuarios.size(); i++) {
			if (listaUsuarios.get(i).getName().equalsIgnoreCase(userUpdate.getName())) {
				found = listaUsuarios.get(i);
			}
		}

		if (found == null) {
			return Response.status(Status.BAD_REQUEST).entity("User not found").build();
		} else {
			found.setUsername(userUpdate.getUsername());
			return Response.ok(found).build();
		}
	}

	/**
	 * URL: http://localhost:8080/API_REST_WS-RS/api/users/deleteUser/Rosa
	 * 
	 * @param User
	 * @return Response
	 */
	@DELETE
	@Path("/deleteUser/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteUser(@PathParam("name") String name) {
		User found = null;
		for (int i = 0; i < listaUsuarios.size(); i++) {
			if (listaUsuarios.get(i).getName().equalsIgnoreCase(name)) {
				found = listaUsuarios.get(i);
				listaUsuarios.remove(found);
			}
		}

		if (found == null) {
			return Response.status(Status.BAD_REQUEST).entity("User not found").build();
		} else {
			return Response.ok(listaUsuarios).build();
		}
	}

}