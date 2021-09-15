package ar.edu.iua.iw3.web;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.iua.iw3.modelo.Producto;
import ar.edu.iua.iw3.negocio.IProductoNegocio;
import ar.edu.iua.iw3.negocio.excepciones.EncontradoException;
import ar.edu.iua.iw3.negocio.excepciones.NegocioException;
import ar.edu.iua.iw3.negocio.excepciones.NoEncontradoException;

@RestController
public class ProductosRestController {

	@Autowired
	private IProductoNegocio productoNegocio;

	// curl http://localhost:8080/productos
	
	@GetMapping(value="/productos")
	public ResponseEntity<List<Producto>> listado() {
		try {
			return new ResponseEntity<List<Producto>>(productoNegocio.listado(), HttpStatus.OK);
		} catch (NegocioException e) {
			return new ResponseEntity<List<Producto>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// curl http://localhost:8080/productos/1
	
	@GetMapping(value="/productos/{id}")
	public ResponseEntity<Producto> cargar(@PathVariable("id") long id) {
		try {
			return new ResponseEntity<Producto>(productoNegocio.cargar(id), HttpStatus.OK);
		} catch (NegocioException e) {
			return new ResponseEntity<Producto>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NoEncontradoException e) {
			return new ResponseEntity<Producto>(HttpStatus.NOT_FOUND);
		}
	}

	//curl -X POST  http://localhost:8080/productos -H "Content-Type: application/json" -d '{"id":2,"descripcion":"Leche","enStock":false,"precio":104.7,"rubro":{"id":1,"rubro":"Alimentos"},"descripcionExtendida":"Se trata de leche larga vida"}'
	
	@PostMapping(value="/productos")
	public ResponseEntity<String> agregar(@RequestBody Producto producto) {
		try {
			Producto respuesta=productoNegocio.agregar(producto);
			HttpHeaders responseHeaders=new HttpHeaders();
			responseHeaders.set("location", "/productos/"+respuesta.getId());
			return new ResponseEntity<String>(responseHeaders, HttpStatus.CREATED);
		} catch (NegocioException e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (EncontradoException e) {
			return new ResponseEntity<String>(HttpStatus.FOUND);
		}
	}
	
	// curl -X PUT  http://localhost:8080/productos -H "Content-Type: application/json" -d '{"id":2,"descripcion":"Leche","enStock":false,"precio":55,"rubro":{"id":1,"rubro":"Alimentos"},"descripcionExtendida":"Se trata de leche larga vida"}' -v
	@PutMapping(value="/productos")
	public ResponseEntity<String> modificar(@RequestBody Producto producto) throws EncontradoException {
		try {
			productoNegocio.modificar(producto);
			return new ResponseEntity<String>(HttpStatus.OK);
		} catch (NegocioException e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NoEncontradoException e) {
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
	}
	
	// curl -X DELETE http://localhost:8080/productos/11 -v
	
	@DeleteMapping(value="/productos/{id}")
	public ResponseEntity<String> eliminar(@PathVariable("id") long id) {
		try {
			productoNegocio.eliminar(id);
			return new ResponseEntity<String>(HttpStatus.OK);
		} catch (NegocioException e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NoEncontradoException e) {
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping(value="/productos/precio/{precio}")
	public ResponseEntity<List<Producto>> buscarPorPrecio (@PathVariable("precio") double p) throws NoEncontradoException, NegocioException {
		try {
			return new ResponseEntity<List<Producto>>(productoNegocio.buscarPorPrecio(p), HttpStatus.OK);
		} catch (NoEncontradoException e) {
			return new ResponseEntity<List<Producto>>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping(value="/productos/descripcion/{d}")
	public ResponseEntity<List<Producto>> buscarPorDescripcion (@PathVariable("d") String d) throws NoEncontradoException, NegocioException {
		try {
			return new ResponseEntity<List<Producto>>(productoNegocio.buscarPorDescripcion(d), HttpStatus.OK);
		} catch (NoEncontradoException e) {
			return new ResponseEntity<List<Producto>>(HttpStatus.NOT_FOUND); //cómo devuelvo una lista vacía
			//que al mismo tiempo diga not found/ o era 404??
		}
	}
	
	@GetMapping(value="/productos/descripcion/ordenada/{d}")
	public ResponseEntity<List<Producto>> buscarPorDescripcionOrderPrecio (@PathVariable("d") String d) throws NoEncontradoException, NegocioException {
		try {
			return new ResponseEntity<List<Producto>>(productoNegocio.buscarPorDescripcionOrderPrecio(d), HttpStatus.OK);
		} catch (NoEncontradoException e) {
			return new ResponseEntity<List<Producto>>(HttpStatus.NOT_FOUND); //cómo devuelvo una lista vacía
			//que al mismo tiempo diga not found/ o era 404??
		}
	}
	
	@GetMapping(value="/productos/precio/{p1}/y/{p2}")
	public ResponseEntity<List<Producto>> buscarPrecioEntre (@PathVariable("p1") double p1, @PathVariable("p2") double p2) throws NoEncontradoException, NegocioException {
		try {
			return new ResponseEntity<List<Producto>>(productoNegocio.buscarPrecioEntre(p1, p2), HttpStatus.OK);
		} catch (NoEncontradoException e) {
			return new ResponseEntity<List<Producto>>(HttpStatus.NOT_FOUND); //cómo devuelvo una lista vacía
			//que al mismo tiempo diga not found/ o era 404??
		}
	}
	
	@GetMapping(value="/productos/precio/ordenada/{p}")
	public ResponseEntity<List<Producto>> buscarPorPrecioOrderDescripcion (@PathVariable("p") double p) throws NoEncontradoException, NegocioException {
		try {
			return new ResponseEntity<List<Producto>>(productoNegocio.buscarPorPrecioOrderDescripcion(p), HttpStatus.OK);
		} catch (NoEncontradoException e) {
			return new ResponseEntity<List<Producto>>(HttpStatus.NOT_FOUND); //cómo devuelvo una lista vacía
			//que al mismo tiempo diga not found/ o era 404??
		}
	}
	
	@GetMapping(value="/productos/vencimiento-no-null")
	public ResponseEntity<List<Producto>> listarVencimientoNoNula() throws NegocioException {

			return new ResponseEntity<List<Producto>>(productoNegocio.listarVencimientoNoNula(), HttpStatus.OK);

	}
	
	@GetMapping(value="/productos/after-vencimiento/{f}")
	public ResponseEntity<List<Producto>> buscarPorFechaAfter (@PathVariable("f") Date f) throws NoEncontradoException, NegocioException {
		try {
			return new ResponseEntity<List<Producto>>(productoNegocio.buscarPorFechaAfter(f), HttpStatus.OK);
		} catch (NoEncontradoException e) {
			return new ResponseEntity<List<Producto>>(HttpStatus.NOT_FOUND);
		}
		
	}
	
	@GetMapping(value="/productos/entre-fechas/{f1}/y/{f2}")
	public ResponseEntity<List<Producto>> buscar2EntreFechas (@PathVariable("f1") Date f1, @PathVariable("f2") Date f2) throws NoEncontradoException, NegocioException {
		try {
			return new ResponseEntity<List<Producto>>(productoNegocio.buscar2EntreFechas(f1, f2), HttpStatus.OK);
		} catch (NoEncontradoException e) {
			return new ResponseEntity<List<Producto>>(HttpStatus.NOT_FOUND);
		}
	}
	
}
