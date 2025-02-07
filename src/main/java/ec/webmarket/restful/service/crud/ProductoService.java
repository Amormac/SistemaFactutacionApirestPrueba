package ec.webmarket.restful.service.crud;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.webmarket.restful.domain.Producto;
import ec.webmarket.restful.dto.v1.ProductoDTO;

import ec.webmarket.restful.persistence.ProductoRepository;
import ec.webmarket.restful.service.GenericCrudServiceImpl;

@Service
public class ProductoService extends GenericCrudServiceImpl<Producto, ProductoDTO> {

	@Autowired
	private ProductoRepository repository;

	private ModelMapper modelMapper = new ModelMapper();

	@Override
	public Optional<Producto> find(ProductoDTO dto) {
		return repository.findById(dto.getId_producto());
	}

	public List<Producto> findByNombre(String nombre) {
		return repository.findByNombre(nombre);
	}

	public List<Producto> findByFechaCreacion(LocalDate fechaCreacion) {
		return repository.findByFechaCreacion(fechaCreacion);
	}

	@Override
	public Producto mapToDomain(ProductoDTO dto) {
		return modelMapper.map(dto, Producto.class);
	}

	@Override
	public ProductoDTO mapToDto(Producto domain) {
		return modelMapper.map(domain, ProductoDTO.class);
	}

}