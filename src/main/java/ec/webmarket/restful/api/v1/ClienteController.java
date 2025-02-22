package ec.webmarket.restful.api.v1;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ec.webmarket.restful.common.ApiConstants;
import ec.webmarket.restful.domain.Paciente;
import ec.webmarket.restful.dto.v1.ClienteDTO;
import ec.webmarket.restful.security.ApiResponseDTO;
import ec.webmarket.restful.service.crud.ClienteService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = { ApiConstants.URI_API_V1_CLIENTE })
public class ClienteController {

	@Autowired
	private ClienteService entityService;

	@GetMapping
	public ResponseEntity<?> getAll() {
		return new ResponseEntity<>(new ApiResponseDTO<>(true, entityService.findAll(new ClienteDTO())), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<?> create(@Valid @RequestBody ClienteDTO dto) {
	    try {
	        ClienteDTO createdDto = entityService.create(dto);
	        return new ResponseEntity<>(new ApiResponseDTO<>(true, createdDto), HttpStatus.CREATED);
	    } catch (Exception e) {
	        return new ResponseEntity<>(new ApiResponseDTO<>(false, "Error al crear el cliente: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	@PutMapping
	public ResponseEntity<?> update(@RequestBody ClienteDTO dto) {
		return new ResponseEntity<>(new ApiResponseDTO<>(true, entityService.update(dto)), HttpStatus.OK);
	}

	@GetMapping("/{id}/archivo/id")
	public ResponseEntity<?> getById(@Valid @PathVariable Long id) {
	    if (id == null) {
	        return new ResponseEntity<>(new ApiResponseDTO<>(false, "El ID no debe ser nulo"), HttpStatus.BAD_REQUEST);
	    }
	    ClienteDTO dto = new ClienteDTO();
	    dto.setId_cliente(id);
	    return new ResponseEntity<>(new ApiResponseDTO<>(true, entityService.find(dto)), HttpStatus.OK);
	}

	@GetMapping("/{fechaCreacion}/archivo/fecha-creacion")
	public ResponseEntity<?> getClientesByFechaCreacion(@PathVariable String fechaCreacion) {
		LocalDate fecha = LocalDate.parse(fechaCreacion); // return
		entityService.findByFechaCreacion(fecha);
		return new ResponseEntity<>(new ApiResponseDTO<>(true, entityService.findByFechaCreacion(fecha)),
				HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}/archivo/id")
	public ResponseEntity<?> deleteById(@Valid @PathVariable Long id) {
	    ClienteDTO dto = new ClienteDTO();
	    dto.setId_cliente(id);
	    // Llamar al servicio para eliminar el cliente
	    entityService.delete(dto);
	    return new ResponseEntity<>(new ApiResponseDTO<Void>(true, null), HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("/cedula/{cedula}")
	public ResponseEntity<?> getClientePorCedula(@PathVariable Long cedula) {
	    Optional<Paciente> cliente = entityService.findByCedula(cedula);
	    if (cliente.isPresent()) {
	        return new ResponseEntity<>(new ApiResponseDTO<>(true, cliente.get()), HttpStatus.OK);
	    } else {
	        return new ResponseEntity<>(new ApiResponseDTO<>(false, "No se encontró ningún cliente con la cédula proporcionada"), HttpStatus.NOT_FOUND
	        );
	    }
	}

}