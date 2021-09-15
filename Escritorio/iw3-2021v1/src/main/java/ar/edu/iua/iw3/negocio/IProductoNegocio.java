package ar.edu.iua.iw3.negocio;

import java.sql.Date;
import java.util.List;

import ar.edu.iua.iw3.modelo.Producto;
import ar.edu.iua.iw3.negocio.excepciones.EncontradoException;
import ar.edu.iua.iw3.negocio.excepciones.NegocioException;
import ar.edu.iua.iw3.negocio.excepciones.NoEncontradoException;

public interface IProductoNegocio {
	public List<Producto> listado() throws NegocioException;

	public Producto cargar(long id) throws NegocioException, NoEncontradoException;

	public Producto agregar(Producto producto) throws NegocioException, EncontradoException;

	public Producto modificar(Producto producto) throws NegocioException, NoEncontradoException, EncontradoException;

	public void eliminar(long id) throws NegocioException, NoEncontradoException;

	List<Producto> buscarPorPrecio(double p) throws NoEncontradoException, NegocioException;

	List<Producto> buscarPorDescripcion(String d) throws NoEncontradoException, NegocioException;

	List<Producto> buscarPrecioEntre(double p1, double p2) throws NoEncontradoException, NegocioException;

	List<Producto> buscarPorDescripcionOrderPrecio(String d) throws NoEncontradoException, NegocioException;

	List<Producto> buscarPorPrecioOrderDescripcion(double p) throws NoEncontradoException, NegocioException;
	
	//Obtener todos los productos cuya fecha de vencimiento no es nula (ayuda: se puede usar IsNotNull en el findBy)

	List<Producto> listarVencimientoNoNula () throws NegocioException;
	List<Producto> buscarPorFechaAfter(Date fecha) throws NegocioException, NoEncontradoException;
	List<Producto> buscar2EntreFechas(Date f1, Date f2) throws NegocioException, NoEncontradoException;
}
