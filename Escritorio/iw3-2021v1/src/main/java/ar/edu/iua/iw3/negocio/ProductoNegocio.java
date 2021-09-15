package ar.edu.iua.iw3.negocio;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import ar.edu.iua.iw3.demo.perfiles.PruebaPerfilH2Mem;
import ar.edu.iua.iw3.modelo.Producto;
import ar.edu.iua.iw3.modelo.persistencia.ProductoRepository;
import ar.edu.iua.iw3.negocio.excepciones.EncontradoException;
import ar.edu.iua.iw3.negocio.excepciones.NegocioException;
import ar.edu.iua.iw3.negocio.excepciones.NoEncontradoException;

@Service
//@Configuration
public class ProductoNegocio implements IProductoNegocio {
	private Logger log = LoggerFactory.getLogger(ProductoNegocio.class);
	@Autowired
	private ProductoRepository productoDAO;

	// IoC
	// A y B
	// A tiene el control <-- programadora Date fecha=new Date()
	// B <- Spring

	@Override
	public List<Producto> listado() throws NegocioException {
		try {
			return productoDAO.findAll();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new NegocioException(e);
		}
	}
    
	@Override
	public Producto agregar(Producto producto) throws NegocioException, EncontradoException {
		try {
			List<Producto> exists = productoDAO.findByDescripcion(producto.getDescripcion());
			
			if (exists.size() != 0) {
				log.error("El producto cuyo id es: " + exists.get(0).getId() +" ya tiene esa "
						+ "descripción.");
				throw new EncontradoException("Ya existe con producto con la descripcion = " + 
						producto.getDescripcion());
			}
			
			cargar(producto.getId());
			throw new EncontradoException("Ya existe un producto con id=" + producto.getId());
		} catch (NoEncontradoException e) {
		}
		try {
			return productoDAO.save(producto);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new NegocioException(e);
		}

	}

	@Override
	public Producto cargar(long id) throws NegocioException, NoEncontradoException {
		Optional<Producto> o;
		try {
			o = productoDAO.findById(id);
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new NegocioException(e);
		}
		if (!o.isPresent()) {
			throw new NoEncontradoException("No se encuentra el producto con id=" + id);
		}
		return o.get();

	}

	@Override
	public Producto modificar(Producto producto) throws NegocioException, NoEncontradoException, EncontradoException {

		List<Producto> exists = productoDAO.findByDescripcion(producto.getDescripcion());
		if (exists.size() != 0) {
				if (exists.get(0).getId() != producto.getId()) {
					log.error("El producto cuyo id es: " + exists.get(0).getId() +" ya tiene esa "
							+ "descripción.");
					throw new EncontradoException("Ya existe con producto con la descripcion = " + 
					producto.getDescripcion());
				}
		}
		
		cargar(producto.getId());

		try {
			return productoDAO.save(producto);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new NegocioException(e);
		}
	}

	@Override
	public void eliminar(long id) throws NegocioException, NoEncontradoException {
		cargar(id);

		try {
			productoDAO.deleteById(id);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new NegocioException(e);
		}
	}
	
	@Override
	public List<Producto> buscarPorPrecio(double p) throws NoEncontradoException, NegocioException {
		
		if (productoDAO.findByPrecio(p).size() == 0) {
				throw new NoEncontradoException();
			}
		
		try {
			return productoDAO.findByPrecio(p);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new NegocioException(e);
		}
		
	}

	@Override
	public List<Producto> buscarPorDescripcion(String d) throws NoEncontradoException, NegocioException {
		
		if (productoDAO.findByDescripcion(d).size() == 0) {
			throw new NoEncontradoException();
		}
		
		try {
			return productoDAO.findByDescripcion(d);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new NegocioException(e);
		}
	}

	@Override
	public List<Producto> buscarPrecioEntre(double p1, double p2) throws NoEncontradoException, NegocioException {
	
		if (productoDAO.findByPrecioBetween(p1, p2).size() == 0) {
			throw new NoEncontradoException();
		}
		
		try {
			return productoDAO.findByPrecioBetween(p1, p2);
		} catch (Exception e) {
			throw new NegocioException(e);
		}
	}

	@Override
	public List<Producto> buscarPorDescripcionOrderPrecio(String d) throws NoEncontradoException, NegocioException {
		
		if (productoDAO.findByDescripcionOrderByPrecio(d).size() == 0) {
			throw new NoEncontradoException();
		}
		
		try {
			return productoDAO.findByDescripcionOrderByPrecio(d);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new NegocioException(e);
		}
	}

	@Override
	public List<Producto> buscarPorPrecioOrderDescripcion(double p) throws NoEncontradoException, NegocioException {

		if (productoDAO.findByPrecioOrderByDescripcionDesc(p).size() == 0) {
			throw new NoEncontradoException();
		}
		
		try {
			return productoDAO.findByPrecioOrderByDescripcionDesc(p);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new NegocioException(e);
		}
	}

	@Override
	public List<Producto> listarVencimientoNoNula() throws NegocioException {
		
		return productoDAO.findByFechaVencimientoIsNotNull();
	}

	@Override
	public List<Producto> buscarPorFechaAfter(Date fecha) throws NegocioException, NoEncontradoException {

		if (productoDAO.findByFechaVencimientoAfter(fecha).size() == 0) {
			throw new NoEncontradoException();
		}
		
		try {
			return productoDAO.findByFechaVencimientoAfter(fecha);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new NegocioException(e);
		}
	}

	@Override
	public List<Producto> buscar2EntreFechas(Date f1, Date f2) throws NegocioException, NoEncontradoException {
		if (productoDAO.findFirst2ByFechaVencimiento(f1, f2).size() == 0) {
			throw new NoEncontradoException();
		}
		
		try {
			return productoDAO.findFirst2ByFechaVencimiento(f1, f2);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new NegocioException(e);
		}
	}

	// @Bean
	// public IProductoNegocio getProductoNegocio() {
	// return new ProductoNegocio();
	// }

}
